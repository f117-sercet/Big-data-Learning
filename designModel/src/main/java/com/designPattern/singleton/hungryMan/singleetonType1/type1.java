package com.designPattern.singleton.hungryMan.singleetonType1;

import org.junit.Assert;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/9/1 10:41
 */
public class type1 {

    public static void main(String[] args) {

        SignleTon1 instance1 = SignleTon1.getInstance();
        SignleTon1 instance2 = SignleTon1.getInstance();
        Assert.assertEquals(instance1, instance2);
        System.out.println(instance1 == instance2);
        //System.out.println(instance1.description);
        //System.out.println(instance2.description);

    }
}

    // 饿汉式
    class SignleTon1 {

    String name;
    String description;

        // 1.构造器私有化
        private SignleTon1() {}
        private SignleTon1(String name,String description){

            this.description =description;
            this.name = name;

        }


    //本类内部创建对象实例
    private final static SignleTon1 instance = new SignleTon1("123","123");

    // 3.对外提供一个共有的静态方法，返回实例对象

  public static SignleTon1 getInstance() {
        return instance;
    }
}
