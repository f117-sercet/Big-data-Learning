package com.designPattern.principle.inverse;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/7 11:25
 */
public class DependencyInvsersion {

    public static void main(String[] args) {
        Person person = new Person();
        person.recieve(new Email());
    }
}
    class Email{
        public String getInfo(){
            return "电子邮件信息:hello,world";
        }
    }

    // 1.简单，容想到
   // 2.增加相应的方法
        class Person {
        public void recieve(Email email){
            System.out.println(email.getInfo());
        }
    }
