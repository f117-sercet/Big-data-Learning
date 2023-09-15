package com.designPattern.prototype;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/9/15 9:38
 */
public class prototypeTest {

    public static void main(String[] args) {

        User user = new User();
        user.setAge(141);
        user.setUsername("123");
        User clone = (User) user.clone();
        System.out.println(clone.hashCode());
        System.out.println(user.hashCode());

    }
}
