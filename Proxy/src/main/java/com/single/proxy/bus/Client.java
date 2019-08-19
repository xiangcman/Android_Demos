package com.single.proxy.bus;

import com.single.proxy.ProxyUtils;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        AudiQ5 audiQ5 = new AudiQ5();
        //静态代理
//        ChinaFactory chinaFactory = new ChinaFactory(audiQ5);
//        chinaFactory.produceEngine();
//        chinaFactory.productGearbox();
//        chinaFactory.productChassis();

        //动态代理
        //拿到被代理类的类加载器，关于类加载器其实就是能执行类的class文件，
        // 也就是说有了类的加载器能实现代理类的class文件生成
        ClassLoader classLoader = audiQ5.getClass().getClassLoader();
        //找到被代理类的所有接口类，因为代理类需要知道被代理类实现了那些接口，继而实现接口中的所有方法
        Class[] interfaces = audiQ5.getClass().getInterfaces();
        //此处就是我们要处理的被代理类的逻辑
        DynamicHandler proxyHandler = new DynamicHandler(audiQ5);
        //通过Proxy.newProxyInstance生成代理类对象
//        Object newProxyInstance = Proxy.newProxyInstance(classLoader, interfaces, proxyHandler);
        Object newProxyInstance = Proxy.newProxyInstance(classLoader, new Class[]{ForeignFactory.class}, proxyHandler);
        //因为代理类是根据接口来的，所以直接可以拿来强转
        ForeignFactory foreignFactory = (ForeignFactory) newProxyInstance;
        foreignFactory.produceEngine();
        foreignFactory.productGearbox();
        foreignFactory.productChassis();

        ProxyUtils.generateClassFile(audiQ5.getClass(), "DynamicChinaFactory");
    }
}
