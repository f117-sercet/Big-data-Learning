package com.designPattern.composition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 15:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Colleage extends organizationComponent {

    //List 存放department
    List<organizationComponent> organizationComponents =new ArrayList<>();
    public  Colleage(String name,String des){
        super(name, des);
    }

    @Override
    protected void add(organizationComponent org) {
        organizationComponents.add(org);
    }


    @Override
    protected void remove(organizationComponent org) {
        organizationComponents.remove(org);
    }

    @Override
    public String getDes() {
        return super.getDes();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    //输出东西
    @Override
    protected void print() {
        System.out.println(getName());

        organizationComponents.forEach(organizationComponent::print);
    }
}
