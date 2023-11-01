package com.BuilderConstructor;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/10/30 18:29
 */
public class CommonHouse extends HouseBuilder {
    @Override
    public void buildBasic() {
        System.out.println("1");
    }

    @Override
    public void buildWalls() {
        System.out.println("2");
    }

    @Override
    public void Roots() {

        System.out.println(3);
    }
}
