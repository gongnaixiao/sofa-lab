package com.gongnaxiao.practice.proxy;

public class ServiceAImpl implements IService{
    @Override
    public void m1() {
        System.out.println("serviceA 调用方法m1");
    }

    @Override
    public void m2() {
        System.out.println("serviceA 调用方法m2");
    }

    @Override
    public void m3() {
        System.out.println("serviceA 调用方法m3");
    }
}
