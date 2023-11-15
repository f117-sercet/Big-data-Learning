package com.designPattern.decorator;

import lombok.Data;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 8:30
 */
@Data
public abstract class Drink {

   public String  desc;
   public float  price = 0.0f;



   // 计算价格
   public abstract float cost();

}
