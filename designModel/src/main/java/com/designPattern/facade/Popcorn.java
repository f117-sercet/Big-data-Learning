package com.designPattern.facade;

import lombok.Getter;





/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/20 17:06
 */

public class Popcorn {
    @Getter
    private static  Popcorn instance = new Popcorn();

    public void on (){
        System.out.println("popcorn on");
    }

    public void off (){

        System.out.println("popcorn on");
    }
}








