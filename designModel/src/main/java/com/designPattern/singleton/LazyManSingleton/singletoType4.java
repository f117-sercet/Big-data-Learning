package com.designPattern.singleton.LazyManSingleton;

import sun.security.jca.GetInstance;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/9/5 10:38
 */
public class singletoType4 {

    public static void main(String[] args) {

        Singleton4 instance = Singleton4.getInstance();
        Singleton4 instance1 =Singleton4.getInstance();
        System.out.println(instance1 == instance);


    }
}

// 推荐使用
class Singleton4 {
    private static volatile Singleton4 instance;

    private Singleton4() {
    }

    private static class SingletonGetInstance {

        private static final Singleton4 Instance = new Singleton4();
    }

    //
    public static Singleton4 getInstance() {

        return SingletonGetInstance.Instance;
    }
}
