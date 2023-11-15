package com.designPattern.decorator;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 8:33
 */
public class Coffe extends Drink {
    @Override
    public float cost() {
        return super.getPrice();
    }
}
