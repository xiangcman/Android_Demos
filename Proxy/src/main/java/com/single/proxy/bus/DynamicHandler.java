package com.single.proxy.bus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicHandler implements InvocationHandler {
    private AudiQ5 audiQ5;

    public DynamicHandler(AudiQ5 audiQ5) {
        this.audiQ5 = audiQ5;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Object invoke = method.invoke(audiQ5, objects);
        String name = method.getName();
        if (name.equals("produceEngine")) {
            System.out.println("国内进行组装发动机");
        } else if (name.equals("productGearbox")) {
            System.out.println("国内进行组装变速箱");
        } else if (name.equals("productChassis")) {
            System.out.println("国内进行组装底盘");
        }
        return invoke;
    }
}
