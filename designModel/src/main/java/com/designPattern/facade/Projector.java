package com.designPattern.facade;

import lombok.Getter;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/20 17:08
 */
public class Projector {

    @Getter
    private static  Projector instance = new Projector();

    public void on (){
        System.out.println("Projector on");
    }

    public void off (){

        System.out.println("Projector on");
    }
   public void screenUp(){
        System.out.println("screen up");
    }}
