package com.designPattern.principle.singleResponsibility;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/4 11:49
 */
public class SingleResponsibility {

    public static void main(String[] args) {


        Vehicle vehicle = new Vehicle();
        vehicle.run("wb");
        vehicle.run("wv");

    }


}

    // 交通工具类
  // 违反单一职责原则
    class Vehicle {
        public void run (String vehicle){
            System.out.println(vehicle+"在公路上运行");
        }
    }

