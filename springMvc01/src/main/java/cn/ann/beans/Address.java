package cn.ann.beans;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-4 17:00
 */
public class Address {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                '}';
    }
}
