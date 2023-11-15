package com.designPattern.decorator;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 11:20
 */
public class Decorator extends Drink
{

    public Decorator(Drink obj){

        this.obj = obj;
    }
    private Drink obj;
    @Override
    public float cost() {
        return super.getPrice() +obj.cost() ;
    }

    @Override
    public String getDesc() {
        return super.desc+" " + super.getPrice() + " " + obj.getDesc();
    }
}
