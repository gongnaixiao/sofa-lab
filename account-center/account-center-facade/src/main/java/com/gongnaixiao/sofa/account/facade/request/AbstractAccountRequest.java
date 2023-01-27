package com.gongnaixiao.sofa.account.facade.request;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class AbstractAccountRequest implements Serializable {

    /** 序列号 */
    private static final long serialVersionUID = -5681817694181854590L;

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}