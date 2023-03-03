package com.gongnaixiao.sofa.account.service;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.alipay.sofa.tracer.plugin.flexible.annotations.Tracer;
import com.gongnaixiao.sofa.account.config.DynamicDataSourceContextHolder;
import com.gongnaixiao.sofa.account.config.MultiDataSourceUtils;
import com.gongnaixiao.sofa.account.entity.Account;
import com.gongnaixiao.sofa.account.entity.AccountExample;
import com.gongnaixiao.sofa.account.facade.api.AcctOpenService;
import com.gongnaixiao.sofa.account.mapper.AccountMapper;
import com.gongnaixiao.sofa.account.mapper.ext.AccountExtMapper;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@SofaService(bindings = {@SofaServiceBinding(bindingType = "bolt")})
public class AcctOpenServiceImpl implements AcctOpenService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AcctOpenServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountExtMapper accountExtMapper;

    @Override
    @Tracer
    public Boolean initAccounts(String magicNumber) {
        DynamicDataSourceContextHolder.setDataSourceKey(MultiDataSourceUtils.getByMagic(magicNumber));
        LOGGER.info("当前 XID: {}", RootContext.getXID());

        validateMagicNumber(magicNumber);

        int workerCount = 10;

        ExecutorService threadPool = Executors.newFixedThreadPool(workerCount);

        // 初始化一万条数据，格式为：00~99 + magicNumber + 00~99
        for (int i = 0; i < workerCount; i++) {
            int start = i * 10;
            int end = start + 9;
            threadPool.execute(() -> {
                try {
                    DynamicDataSourceContextHolder.setDataSourceKey(MultiDataSourceUtils.getByMagic(magicNumber));
                    batchInsertAccounts(start, end, magicNumber);
                    LOGGER.info("init accounts between [{},{}] success", start, end);
                } catch (Exception e) {
                    LOGGER.error("init accounts between [{},{}] failed: {}", start, end, e.getMessage(), e);
                }
            });
        }

        return true;
    }
    private void batchInsertAccounts(int start, int end, String magicNumber) {
        List<Account> accounts = new ArrayList<Account>();
        for (int i = start; i <= end; i++) {
            String prefix = end < 10 ? ("0" + i) : String.valueOf(i);
            for (int j = 0; j < 100; j++) {
                String suffix = j < 10 ? "0" + j : String.valueOf(j);
                Account account = new Account();
                account.setAccountNo(prefix + magicNumber + suffix);
                account.setBalance(new BigDecimal(10000));
                account.setFreezeAmount(BigDecimal.ZERO);
                account.setUnreachAmount(BigDecimal.ZERO);
                accounts.add(account);
            }
        }
        accountExtMapper.batchInsertAccounts(accounts);
    }

    private void validateMagicNumber(String magicNumber) {
        if (null == magicNumber) {
            throw new RuntimeException("Magic number is null!");
        }

        if (magicNumber.length() != 4) {
            throw new RuntimeException("Length of magic number must equal 4!");
        }

        if (!isNumeric(magicNumber)) {
            throw new RuntimeException("Magic number must be numbers only!");
        }

        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andAccountNoEqualTo("00" + magicNumber + "00");
        List<Account> accounts = accountMapper.selectByExample(accountExample);

        if (!CollectionUtils.isEmpty(accounts)) {
            throw new RuntimeException("Magic number has existed already!");
        }
    }

    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str);

        return match.matches();
    }

}