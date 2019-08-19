package com.single.layoutinflaterdemo.bus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NameBasedFinder implements Finder {
    /**
     * @param subscriber
     * @return
     */
    @Override
    public List<Method> findSubscriber(Class<?> subscriber) {
        List<Method> methods = new ArrayList<>();
        //先找到类下面的所有方法
        for (Method method : subscriber.getDeclaredMethods()) {
            method.setAccessible(true);
            //这个就是找方法的注解关键
            if (method.isAnnotationPresent(onEvent.class)) {
                methods.add(method);
            }
        }
        return methods;
    }
}
