package com.designPattern.facade;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/20 17:11
 */
public class client {

    public static void main(String[] args) {

        facadeDesigner facadeDesigner = new facadeDesigner();
        facadeDesigner.Ready();
        facadeDesigner.onclose();
    }
}
