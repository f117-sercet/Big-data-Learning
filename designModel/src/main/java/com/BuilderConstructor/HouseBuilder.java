package com.BuilderConstructor;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/10/30 18:26
 */
// 抽象的建造者
public abstract class HouseBuilder {

    protected House house = new House();

    // 抽象方法(子类实现)
    public abstract void buildBasic();
    public abstract void buildWalls();
    public abstract void Roots();


    // 返回
    public  House buildHouse() {

        return house;
    }


}
