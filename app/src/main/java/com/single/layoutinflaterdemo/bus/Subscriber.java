package com.single.layoutinflaterdemo.bus;

import java.lang.reflect.Method;

public class Subscriber {
    public Object mSubscriber;
    public Method mMethod;
    Class<?> mEventType;

    public Subscriber(Object subscriber, Method method) {
        mSubscriber = subscriber;
        mMethod = method;
//        mEventType = method.getParameterTypes()[0];
        mEventType = subscriber.getClass();
    }
}
