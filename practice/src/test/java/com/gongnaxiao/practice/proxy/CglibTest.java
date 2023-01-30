package com.gongnaxiao.practice.proxy;

import com.gongnaxiao.practice.cglib.CostTimeProxy;
import com.gongnaxiao.practice.cglib.Service1Impl;
import com.gongnaxiao.practice.cglib.Service4Impl;
import com.gongnaxiao.practice.cglib.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * jdk动态代理只能为接口创建代理，使用上有局限性。实际的场景中我们的类不一定有接口，此时如果我们想为普通的类也实现代理功能，
 * 我们就需要用到cglib来实现了。
 * <p>
 * cglib是一个强大、高性能的字节码生成库，它用于在运行时扩展Java类和实现接口；
 * 本质上它是通过动态的生成一个子类去覆盖所要代理的类（非final修饰的类和方法）。
 * Enhancer可能是CGLIB中最常用的一个类，和jdk中的Proxy不同的是，Enhancer既能够代理普通的class，也能够代理接口。
 * Enhancer创建一个被代理对象的子类并且拦截所有的方法调用（包括从Object中继承的toString和hashCode方法）。
 * Enhancer不能够拦截final方法，例如Object.getClass()方法，这是由于Java final方法语义决定的。
 * 基于同样的道理，Enhancer也不能对final类进行代理操作。
 */
public class CglibTest {
    //拦截所有方法（MethodInterceptor）
    @Test
    public void testService() {
        //1.创建Enhancer对象
        Enhancer enhancer = new Enhancer();
        //2.通过setSuprClass来设置父类型，即需要给那个类创建代理类
        enhancer.setSuperclass(ServiceImpl.class);
        //3.设置回调 当调用代理对象的任何方法的时候，都会被MethodInterceptor接口的invoke方法处理
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("代理前处理 方法" + method.getName());
                //调用被代理类的方法
                Object result = methodProxy.invokeSuper(o, objects);
                return result;
            }
        });
        //4.获取代理对象
        ServiceImpl proxy = (ServiceImpl) enhancer.create();
        //5.调用代理对象的方法
        proxy.m1();
        proxy.m2();
    }

    //拦截所有方法并返回固定值（FixedValue）
    @Test
    public void testService2() {
        //1.创建Enhancer对象
        Enhancer enhancer = new Enhancer();
        //2.通过setSuprClass来设置父类型，即需要给那个类创建代理类
        enhancer.setSuperclass(Service1Impl.class);
        //3.设置回调 当调用代理对象的任何方法的时候，都会被MethodInterceptor接口的invoke方法处理
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "路人甲";
            }
        });
        //4.获取代理对象
        Service1Impl proxy = (Service1Impl) enhancer.create();
        //5.调用代理对象的方法
        System.out.println(proxy.m1());
        System.out.println(proxy.m2());
    }

    //不同的方法使用不同的拦截器（CallbackFilter）
    @Test
    public void testService4() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Service4Impl.class);

        Callback[] callBacks = {
                //拦击insert方法
                new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        long startTime = System.nanoTime();
                        Object result = methodProxy.invokeSuper(o, objects);
                        long endTime = System.nanoTime();
                        System.out.println(method + "耗时(纳秒):" + (endTime - startTime));

                        return result;
                    }
                },
                //拦截get方法
                new FixedValue() {
                    @Override
                    public Object loadObject() throws Exception {
                        return "固定值";
                    }
                }
        };
        enhancer.setCallbacks(callBacks);
        //设置过滤器callbackFilter
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                //获取当前调用的方法名
                String name = method.getName();
                //insert开头的返回callbacks第一个callback对象处理当前方法
                return name.startsWith("insert") ? 0 : 1;
            }
        });
        Service4Impl o = (Service4Impl) enhancer.create();

        o.insert1();
        o.insert2();
        System.out.println(o.get1());
        System.out.println(o.get2());
    }

    //callhelper
    @Test
    public void testService5() {
        Enhancer enhancer = new Enhancer();
        //创建2个Callback
        Callback costTimeCallback = (MethodInterceptor) (Object o, Method method, Object[] objects, MethodProxy methodProxy) -> {
            long starTime = System.nanoTime();
            Object result = methodProxy.invokeSuper(o, objects);
            long endTime = System.nanoTime();
            System.out.println(method + "，耗时(纳秒):" + (endTime - starTime));
            return result;
        };
        //下面这个用来拦截所有get开头的方法，返回固定值的
        Callback fixdValueCallback = (FixedValue) () -> "路人甲Java";
        CallbackHelper callbackHelper = new CallbackHelper(Service4Impl.class, null) {
            @Override
            protected Object getCallback(Method method) {
                return method.getName().startsWith("insert") ? costTimeCallback : fixdValueCallback;
            }
        };
        enhancer.setSuperclass(Service4Impl.class);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        enhancer.setCallbackFilter(callbackHelper);
        Service4Impl o = (Service4Impl) enhancer.create();

        o.insert1();
        o.insert2();
        System.out.println(o.get1());
        System.out.println(o.get2());
    }

    @Test
    public void testCostTimeProxy() {
        ServiceImpl proxy = CostTimeProxy.createProxy(new ServiceImpl());
        proxy.m1();
        proxy.m2();
        Service1Impl proxy1 = CostTimeProxy.createProxy(new Service1Impl());
        proxy1.m1();
        proxy1.m2();
    }
}
