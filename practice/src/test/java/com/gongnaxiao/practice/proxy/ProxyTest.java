package com.gongnaxiao.practice.proxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk中的Proxy只能为接口生成代理类，如果你想给某个类创建代理类，那么Proxy是无能为力的，此时需要我们用到下面要说的cglib了。
 * Proxy类中提供的几个常用的静态方法大家需要掌握
 * 通过Proxy创建代理对象，当调用代理对象任意方法时候，会被InvocationHandler接口中的invoke方法进行处理，这个接口内容是关键
 */
public class ProxyTest {
    @Test
    public void testProxy1() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //1.获取接口对应的代理类
        Class<IService> proxyClass = (Class<IService>) Proxy.getProxyClass(IService.class.getClassLoader(), IService.class);
        //2.创建代理类的处理器
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            ServiceAImpl serviceA = new ServiceAImpl();

            System.out.println("proxy前调用" + method.getName());
            method.invoke(serviceA, args);
            System.out.println("proxy后调用" + method.getName());

            return null;
        };
        //3.创建代理实例
        IService iService = proxyClass.getConstructor(InvocationHandler.class).newInstance(invocationHandler);

        //4.调用代理方法
        iService.m1();
        iService.m2();
        iService.m3();
    }

    @Test
    public void testProxy2() {
        IService service = (IService) Proxy.newProxyInstance(IService.class.getClassLoader(), new Class[]{IService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ServiceBImpl serviceB = new ServiceBImpl();

                System.out.println("proxy前调用" + method.getName());
                method.invoke(serviceB, args);
                System.out.println("proxy后调用" + method.getName());

                return null;
            }
        });

        service.m1();
        service.m2();
        service.m3();
    }
}
