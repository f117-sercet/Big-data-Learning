package com.designPattern.singleton.LazyManSingleton;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/9/4 17:53
 */
public class singletoType2 {

    public static void main(String[] args) {

        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();
        //System.out.println(instance1 == instance);


    }
}

class Singletons {
    private static Singletons instance;

    private Singletons() {
    }

    // 提供给一个静态的公有方法，当使用到该方法时，才会创建instance
    // 即懒汉式

    public static Singletons getInstance() {

        if (instance == null) {
            // 同步代码块
            synchronized (Singletons.class) {
                instance = new Singletons();

            }
        }
        return instance;
    }
}
