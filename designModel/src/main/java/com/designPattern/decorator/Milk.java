package com.designPattern.decorator;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 11:29
 */
public class Milk extends Decorator {
    public Milk(Drink obj) {
        super(obj);
        setDesc("牛奶");
        setPrice(5f);
    }
}
