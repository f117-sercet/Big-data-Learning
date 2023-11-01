package com.BuilderConstructor;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/10/31 8:40
 */
public class client {

    public static void main(String[] args) {
        CommonHouse commonHouse = new CommonHouse();
        HouseDirector houseDirector = new HouseDirector(commonHouse);
        House house = houseDirector.constructorHouse();
        System.out.println(house);

    }
}
