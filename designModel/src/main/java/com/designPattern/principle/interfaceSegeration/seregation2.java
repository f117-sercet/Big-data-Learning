package com.designPattern.principle.interfaceSegeration;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/4 18:14
 */
public class seregation2 {

    public static void main(String[] args) {

    }
}

interface interface1{
    void operation1();
}
interface interface2{
    void operation2();
    void operation3();
}
interface interface3{
    void operation5();
    void operation4();
}

class A1 {
    public void depend1(interface1 i) {
        i.operation1();
    }
    public void depend2(interface2 i,interface3 j) {
        i.operation2();
        j.operation4();
    }
}
