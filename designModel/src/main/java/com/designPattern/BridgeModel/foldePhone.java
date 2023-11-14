package com.designPattern.BridgeModel;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/14 9:04
 */
public class foldePhone extends Phone {
    public foldePhone(Brand brand) {
        super(brand);
    }

    @Override
    protected void open() {
        super.open();
        System.out.println("折叠1");
    }
}
