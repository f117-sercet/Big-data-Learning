package com.designPattern.decorator;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 11:31
 */
public class CoffeBar {

    public static void main(String[] args) {
        Drink longBlack = new LongBlack();
        System.out.println("单品"+longBlack.getDesc()+"价格"+longBlack.getPrice());

        //  加入牛奶
        longBlack  = new Milk(longBlack);
        System.out.println(longBlack);
    }
}
