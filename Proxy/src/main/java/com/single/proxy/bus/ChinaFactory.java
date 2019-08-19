package com.single.proxy.bus;

/**
 * 国内进行组装的工厂
 */
public class ChinaFactory implements ForeignFactory {
    private AudiQ5 audiQ5;

    public ChinaFactory(AudiQ5 audiQ5) {
        this.audiQ5 = audiQ5;
    }

    @Override
    public void produceEngine() {
        audiQ5.produceEngine();
        System.out.println("国内进行组装发动机");
    }

    @Override
    public void productGearbox() {
        audiQ5.productGearbox();
        System.out.println("国内进行组装变速箱");
    }

    @Override
    public void productChassis() {
        audiQ5.productChassis();
        System.out.println("国内进行组装底盘");
    }
}
