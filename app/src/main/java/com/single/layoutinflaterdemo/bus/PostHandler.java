package com.single.layoutinflaterdemo.bus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.CopyOnWriteArrayList;

public class PostHandler extends Handler {
    final EventBus mEventBus;

    public PostHandler(Looper looper, EventBus EventBus) {
        super(looper);
        mEventBus = EventBus;
    }

    @Override
    public void handleMessage(Message msg) {
        CopyOnWriteArrayList<Subscriber> subscribers = mEventBus.mSubscriberMap.get(msg.obj.getClass());
        for (Subscriber subscriber : subscribers) {
            subscriber.mMethod.setAccessible(true);
            try {
                /**
                 * 第二个参数是方法的参数，第-个参数是该类的对象                 
                 */
                subscriber.mMethod.invoke(subscriber.mSubscriber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void enqueue(Object event) {
        Message message = obtainMessage();
        message.obj = event;
        sendMessage(message);
    }

}