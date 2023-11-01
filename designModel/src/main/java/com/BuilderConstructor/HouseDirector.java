package com.BuilderConstructor;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/10/31 8:36
 */

// 指挥者
public class HouseDirector {

    HouseBuilder houseBuilder = null;

    // 构造器传入
    public HouseDirector(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    // 通过setter

    public void setHouseBuilder(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;

    }

   public House constructorHouse() {
        houseBuilder.buildBasic();
        houseBuilder.buildWalls();
        houseBuilder.Roots();
        return houseBuilder.buildHouse();
   }
}
