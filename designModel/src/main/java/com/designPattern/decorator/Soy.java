package com.designPattern.decorator;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 11:30
 */
public class Soy extends Decorator {
    public Soy(Drink obj) {
        super(obj);
        setDesc("豆浆");
        setPrice(1f);
    }
}
