package com.designPattern.principle.inverse;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/7 11:30
 */
public class DependencyInversionTest2 {
    public static void main(String[] args) {

        new Person1().receive(new Emails());

    }

}
abstract class IReceiver{
 public String getInfo() {
        return null;
    }
    public String setInfoWeChat(String info) {
     return  "微信消息";
    }
}

class Emails extends IReceiver{
    @Override
    public String getInfo() {
        return "电子邮件信息:hello world";
    }
}
class Person1 {
    public void receive(IReceiver receiver){
        System.out.println(receiver.getInfo());
    }
}
