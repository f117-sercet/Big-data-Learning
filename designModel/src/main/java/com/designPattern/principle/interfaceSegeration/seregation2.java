package com.designPattern.principle.interfaceSegeration;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/4 18:14
 */
public class seregation2 {

    public static void main(String[] args) {

        B2 b2 = new B2();
        b2.operation1();
        A1 a2 = new A1();
        a2.operation1();

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

class A1 implements interface1,interface2{

    @Override
    public void operation1() {
        System.out.println("123");

    }

    @Override
    public void operation2() {
        System.out.println("456");

    }

    @Override
    public void operation3() {
        System.out.println("689");
    }
}
class B2 implements interface2,interface1 {

    @Override
    public void operation1() {
        System.out.println("B");
    }

    @Override
    public void operation2() {
        System.out.println("B");
    }

    @Override
    public void operation3() {

        System.out.println("B");
    }
}
class C2 implements interface2,interface3 {
    @Override
    public void operation2() {
        System.out.println("C");
    }

    @Override
    public void operation3() {
        System.out.println("C");
    }

    @Override
    public void operation5() {
        System.out.println("C");
    }

    @Override
    public void operation4() {
        System.out.println("C");
    }
}
class D2 implements interface2,interface3{
    @Override
    public void operation2() {
        System.out.println("D");
    }

    @Override
    public void operation3() {
        System.out.println("D");
    }

    @Override
    public void operation5() {
        System.out.println("D");
    }

    @Override
    public void operation4() {
        System.out.println("D");
    }
}
