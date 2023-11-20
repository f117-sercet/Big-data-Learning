package com.designPattern.composition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 15:33
 */
@Data
@NoArgsConstructor
public class Department extends organizationComponent {


    public Department (String name, String des){
    super(name, des);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDes() {
        return super.getDes();
    }

    @Override
    protected void print() {

        System.out.println(getName());
    }
}
