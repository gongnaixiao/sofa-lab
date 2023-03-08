package com.gongnaixiao.sofa.account.config;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import io.seata.common.exception.FrameworkException;
import io.seata.common.util.ReflectionUtil;
import io.seata.rm.DefaultResourceManager;
import io.seata.rm.tcc.TCCResource;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import io.seata.rm.tcc.interceptor.ActionContextUtil;
import io.seata.rm.tcc.remoting.RemotingDesc;
import io.seata.rm.tcc.remoting.RemotingParser;
import io.seata.rm.tcc.remoting.parser.DefaultRemotingParser;
import io.seata.spring.tcc.TccAnnotationProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SeataAutoConfig {
    @Bean
    TccAnnotationProcessor tccAnnotationProcessor() {
        return new TccAnnotationProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                //do registerResource
                registerResource(bean, beanName);

                process(bean, beanName, SofaReference.class);
                return bean;
            }

            protected boolean parserRemotingServiceInfo(Object bean, String beanName) {
                RemotingParser remotingParser = DefaultRemotingParser.get().isRemoting(bean, beanName);
                if (remotingParser != null) {
                    return DefaultRemotingParser.get().parserRemotingServiceInfo(bean, beanName, remotingParser) != null;
                }
                return false;
            }

            public void registerResource(Object bean, String beanName) {
                boolean isRemotingBean = parserRemotingServiceInfo(bean, beanName);
                if (!isRemotingBean) {
                    return;
                }
                boolean isService = DefaultRemotingParser.get().isService(bean, beanName);
                if (!isService) {
                    return;
                }

                RemotingDesc remotingDesc = DefaultRemotingParser.get().getRemotingBeanDesc(beanName);

                try {
                    //service bean, registry resource
                    Class<?> serviceClass = remotingDesc.getServiceClass();
                    Set<Method> methods = new HashSet<>(Arrays.asList(serviceClass.getMethods()));
                    Set<Class<?>> interfaceClasses = ReflectionUtil.getInterfaces(serviceClass);
                    if (interfaceClasses != null) {
                        for (Class<?> interClass : interfaceClasses) {
                            methods.addAll(Arrays.asList(interClass.getMethods()));
                        }
                    }
                    Object targetBean = remotingDesc.getTargetBean();
                    for (Method m : methods) {
                        TwoPhaseBusinessAction twoPhaseBusinessAction = m.getAnnotation(TwoPhaseBusinessAction.class);
                        if (twoPhaseBusinessAction != null) {
                            TCCResource tccResource = new TCCResource();
                            tccResource.setActionName(twoPhaseBusinessAction.name());
                            tccResource.setTargetBean(targetBean);
                            tccResource.setPrepareMethod(m);
                            tccResource.setCommitMethodName(twoPhaseBusinessAction.commitMethod());
                            tccResource.setCommitMethod(serviceClass.getMethod(twoPhaseBusinessAction.commitMethod(),
                                    twoPhaseBusinessAction.commitArgsClasses()));
                            tccResource.setRollbackMethodName(twoPhaseBusinessAction.rollbackMethod());
                            tccResource.setRollbackMethod(serviceClass.getMethod(twoPhaseBusinessAction.rollbackMethod(),
                                    twoPhaseBusinessAction.rollbackArgsClasses()));
                            // set argsClasses
                            tccResource.setCommitArgsClasses(twoPhaseBusinessAction.commitArgsClasses());
                            tccResource.setRollbackArgsClasses(twoPhaseBusinessAction.rollbackArgsClasses());
                            // set phase two method's keys
                            tccResource.setPhaseTwoCommitKeys(this.getTwoPhaseArgs(tccResource.getCommitMethod(),
                                    twoPhaseBusinessAction.commitArgsClasses()));
                            tccResource.setPhaseTwoRollbackKeys(this.getTwoPhaseArgs(tccResource.getRollbackMethod(),
                                    twoPhaseBusinessAction.rollbackArgsClasses()));
                            //registry tcc resource
                            DefaultResourceManager.get().registerResource(tccResource);
                        }
                    }
                } catch (Throwable t) {
                    throw new FrameworkException(t, "parser remoting service error");
                }
            }

            protected String[] getTwoPhaseArgs(Method method, Class<?>[] argsClasses) {
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                String[] keys = new String[parameterAnnotations.length];
                /*
                 * get parameter's key
                 * if method's parameter list is like
                 * (BusinessActionContext, @BusinessActionContextParameter("a") A a, @BusinessActionContextParameter("b") B b)
                 * the keys will be [null, a, b]
                 */
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    for (int j = 0; j < parameterAnnotations[i].length; j++) {
                        if (parameterAnnotations[i][j] instanceof BusinessActionContextParameter) {
                            BusinessActionContextParameter param = (BusinessActionContextParameter) parameterAnnotations[i][j];
                            String key = ActionContextUtil.getParamNameFromAnnotation(param);
                            keys[i] = key;
                            break;
                        }
                    }
                    if (keys[i] == null && !(argsClasses[i].equals(BusinessActionContext.class))) {
                        throw new IllegalArgumentException("non-BusinessActionContext parameter should use annotation " +
                                "BusinessActionContextParameter");
                    }
                }
                return keys;
            }
        };
    }

}
