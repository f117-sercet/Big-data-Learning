package com.designPattern.facade;

import lombok.Getter;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/20 17:04
 */
public class DVDPlayer {

    @Getter
    private static  DVDPlayer instance = new DVDPlayer();

    public void on (){
        System.out.println("go on");
    }

    public void off (){

        System.out.println("bad on");
    }
}
