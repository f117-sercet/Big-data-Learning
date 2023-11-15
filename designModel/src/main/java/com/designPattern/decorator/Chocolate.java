package com.designPattern.decorator;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 11:24
 */
public class Chocolate extends Decorator{
    public Chocolate(Drink obj) {
        super(obj);
        setDesc("调味品");
        setPrice(4555f);
    }
}
