package com.BuilderConstructor;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/10/31 8:35
 */
public class higherBuilder extends HouseBuilder {
    @Override
    public void buildBasic() {
        System.out.println("高楼");
    }

    @Override
    public void buildWalls() {
        System.out.println("高楼1");
    }

    @Override
    public void Roots() {
        System.out.println("高楼2");

    }
}
