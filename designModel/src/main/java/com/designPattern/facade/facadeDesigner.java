package com.designPattern.facade;

import lombok.*;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/20 17:11
 */
@Data
public class facadeDesigner {

    private DVDPlayer dvdPlayer;
    private Projector projector;
    private Popcorn popup;

    public facadeDesigner() {
        super();
        this.dvdPlayer = DVDPlayer.getInstance();
        this.projector = Projector.getInstance();
        this.popup = Popcorn.getInstance();
    }

    public void Ready(){
        dvdPlayer.on();
        popup.on();
        projector.on();
        projector.screenUp();

    }

    public void onclose(){
        dvdPlayer.off();
        popup.off();
        projector.off();
    }

}
