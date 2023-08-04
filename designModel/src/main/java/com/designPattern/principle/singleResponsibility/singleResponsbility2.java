package com.designPattern.principle.singleResponsibility;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/4 14:54
 */
public class singleResponsbility2 {

    public static void main(String[] args) {

        Vehicle2 vehicle2 = new Vehicle2();
        vehicle2.run("汽车");
        vehicle2.runAir("飞机");
        vehicle2.runWater("轮船");

    }
}
//没有对原来的类做大的修改，只是增加了方法
//在方法级别上遵守了单一职责模式
class Vehicle2 {
    public void run (String vehicle){
        System.out.println(vehicle+"在公路上运行");
    }

    public void runAir (String vehicle){
        System.out.println(vehicle+"在天上跑");
    }
    public void runWater (String vehicle){
        System.out.println(vehicle+"在水里游");
    }

}

