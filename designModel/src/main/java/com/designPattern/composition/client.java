package com.designPattern.composition;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 15:38
 */
public class client {

    public static void main(String[] args) {

        organizationComponent university = new University("1", "2");

        organizationComponent c1 = new Colleage("计算机学院", "计算机学院");
        organizationComponent c2 = new Colleage("信息工程", "信息工程学院");
        c1.add(new Department("通信工程","这玩意儿还行"));

        university.add(c1);
        university.add(c2);

        university.print();

    }
}
