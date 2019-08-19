package com.single.layoutinflaterdemo.bus;

import java.lang.reflect.Method;
import java.util.List;

public interface Finder {
    List<Method> findSubscriber(Class<?> subscriber);
}