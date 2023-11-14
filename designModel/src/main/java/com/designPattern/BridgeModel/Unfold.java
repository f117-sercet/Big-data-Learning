package com.designPattern.BridgeModel;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/14 9:07
 */
public class Unfold extends Phone {
    public Unfold(Brand brand) {
        super(brand);
    }

    @Override
    protected void open() {
        super.open();
        System.out.println("折叠2");
    }
}
