package com.designPattern.singleton.LazyManSingleton;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/9/1 15:07
 */
public class singleton {
    public static void main(String[] args) {

        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();
        //System.out.println(instance1 == instance);


    }
}

class Singleton{
    private static Singleton instance;
    private Singleton (){}

    // 提供给一个静态的公有方法，当使用到该方法时，才会创建instance
    // 即懒汉式
    
    public static Singleton getInstance(){
        
       if(instance == null){
            instance = new Singleton();

        }
        return instance;
    }
}
