package com.single.layoutinflaterdemo.reflection;

import com.single.layoutinflaterdemo.Common;

public class Reflection extends Common {
    private String params1;
    private int params2;
    private long params3;

    public Reflection() {
        System.out.println("调用了Reflection类的无参数构造器");
    }

    public Reflection(String params1) {
        this.params1 = params1;
        System.out.println("调用了Reflection类的params1参数构造器====>params1:" + params1);
    }

    private Reflection(int params2) {
        this.params2 = params2;
        System.out.println("调用了Reflection类的params2参数构造器====>params2:" + params2);
    }

    public Reflection(long params3) {
        this.params3 = params3;
        System.out.println("调用了Reflection类的params3参数构造器====>params3:" + params3);
    }

    public int handleMethod(int a, String b) {
        try {
            System.out.println("调用了两个参数的handleMethod方法");
            int intB = Integer.parseInt(b);
            return a + intB;
        } catch (NumberFormatException e) {
        }
        return a;
    }

    public void handleMethod() {
        System.out.println("调用了无参的handleMethod方法");
    }

    public int handleMethod(int a) {
        System.out.println("调用了一个参数的handleMethod方法");
        return a;
    }

    public void handleMethod(String params1) {
        this.params1 = params1;
        System.out.println("调用了一个参数的handleMethod方法====>params1:" + params1);
    }

    private void privateMethod() {
        System.out.println("调用了私有的privateMethod方法");
    }

    protected void protectedMethod() {
        System.out.println("调用了私有的protectedMethod方法");
    }
}
