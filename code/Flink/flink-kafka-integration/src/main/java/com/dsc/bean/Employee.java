package com.dsc.bean;

import java.sql.Date;

/**
 * @Author:estic
 * @Date: 2021/9/1 9:33
 */
public class Employee {
    private String name;
    private int age;
    private Date birthday;

    Employee(){}

    public Employee(String name, int age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
