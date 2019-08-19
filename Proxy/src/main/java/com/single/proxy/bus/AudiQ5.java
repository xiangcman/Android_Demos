package com.single.proxy.bus;

/**
 * 奥迪的车子
 */
public class AudiQ5 implements ForeignFactory {
    @Override
    public void produceEngine() {
        System.out.println("德国生产的发动机");
    }

    @Override
    public void productGearbox() {
        System.out.println("德国生产的变速箱");
    }

    @Override
    public void productChassis() {
        System.out.println("德国生产的底盘");
    }
}
