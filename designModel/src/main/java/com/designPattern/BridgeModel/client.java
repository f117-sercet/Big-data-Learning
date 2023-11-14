package com.designPattern.BridgeModel;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/14 9:07
 */
public class client {

    public static void main(String[] args) {

        foldePhone phone1 = new foldePhone(new xiaomi());
        phone1.open();
    }
}
