package com.designPattern.BridgeModel;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/14 8:58
 */
public abstract class Phone {

   private Brand brand;

   public Phone(Brand brand) {
       this.brand = brand;
   }
   protected void open() {
       this.brand.call();
   }

}
