package cn.ann.beans;

import java.io.Serializable;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-7 11:50
 */
public class User implements Serializable {
    private static final long serialVersionUID = -8891741332118707583L;

    private String name;
    private Integer age;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
