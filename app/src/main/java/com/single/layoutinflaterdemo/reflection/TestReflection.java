package com.single.layoutinflaterdemo.reflection;

import com.single.layoutinflaterdemo.Common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflection {
    public static void main(String[] args) {
        try {
            //生成指定的class类
            Class clz = Class.forName("com.single.layoutinflaterdemo.reflection.Reflection");
            clz.asSubclass(Common.class);
            //生成指定的后早起
            Constructor appleConstructor1 = clz.getDeclaredConstructor(String.class);
            appleConstructor1.setAccessible(true);
            Object target = appleConstructor1.newInstance("123");
            //==============方法调用
            //指定要调用方法的参数类型
            Class<?>[] params = new Class[]{int.class, String.class};
            Method handleMethod = clz.getDeclaredMethod("privateMethod");
            handleMethod.setAccessible(true);
            //args传入我们的参数值
            Object invoke = handleMethod.invoke(target);
            //====================
            //获取所有的参数
            Field[] allParams = clz.getDeclaredFields();
            for (int i = 0; i < allParams.length; i++) {
                Field param = allParams[i];
                param.setAccessible(true);
                String name = param.getName();
                Object value = param.get(target);
                System.out.println("name:" + name + ";value:" + value);
            }
            //获取参数的值

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
