package com.single.layoutinflaterdemo.bus;

import android.os.Looper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    static volatile EventBus sInstance;
    Finder mFinder;
    //CopyOnWriteArrayList一般用在读和写同时存在的情况下使用
    //键存的是要提供注解的类的class对象，值存的要被调用的方法，类信息
    public Map<Class<?>, CopyOnWriteArrayList<Subscriber>> mSubscriberMap;
    //通过handler发送消息
    PostHandler mPostHandler;

    private EventBus() {
        mFinder = new NameBasedFinder();
        mSubscriberMap = new HashMap<>();
        mPostHandler = new PostHandler(Looper.getMainLooper(), this);
    }

    /**
     * 得到一个单例的上下文对象
     *
     * @return
     */
    public static EventBus getDefault() {
        if (sInstance == null) {
            synchronized (EventBus.class) {
                if (sInstance == null) {
                    sInstance = new EventBus();
                }
            }
        }
        return sInstance;
    }

    public void register(Object subscriber) {
        /**
         * 该处是关键，找到该类下面有onEvent注解的方法
         */
        List<Method> methods = mFinder.findSubscriber(subscriber.getClass());
        if (methods == null || methods.size() < 1) {
            return;
        }
        CopyOnWriteArrayList<Subscriber> subscribers = mSubscriberMap.get(subscriber.getClass());
        if (subscribers == null) {
            subscribers = new CopyOnWriteArrayList<>();
            /**
             * 该map用来存储onevent开头的方法的第一个参数为key和
             * subscribers的集合
             */
            mSubscriberMap.put(subscriber.getClass(), subscribers);
        }
        for (Method method : methods) {
            /**
             * 封装了该类和该类带有onevent的方法
             */
            Subscriber newSubscriber = new Subscriber(subscriber, method);
            subscribers.add(newSubscriber);
        }
    }

    public void unregister(Object subscriber) {
        CopyOnWriteArrayList<Subscriber> subscribers = mSubscriberMap.remove(subscriber.getClass());
        if (subscribers != null) {
            for (Subscriber s : subscribers) {
                s.mMethod = null;
                s.mSubscriber = null;
            }
        }
    }

    public void post(Object event) {
        mPostHandler.enqueue(event);
    }
}
