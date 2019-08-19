package com.single.layoutinflaterdemo.apt;

import android.app.Activity;

import java.lang.reflect.Method;

public class BinderViewTools {
    public static void init(Activity activity) {
        Class clazz = activity.getClass();
        try {
            Class<?> bindClass = Class.forName(clazz.getName() + "_ViewBinding");
            Method bind = bindClass.getMethod("bindView", clazz);
            bind.invoke(bindClass.newInstance(), activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}