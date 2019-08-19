package com.single.proxy.bus;

/**
 * 国外的工厂
 */
public interface ForeignFactory {
    //生产发动机
    void produceEngine();

    //生产变速箱
    void productGearbox();

    //生产底盘
    void productChassis();

}
