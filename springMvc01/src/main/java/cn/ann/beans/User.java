package cn.ann.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-4 16:54
 */
public class User {
    private String name;
    private Integer age;
    private Date birthday;

    private Address address;
    private List<User> userList;
    private Map<String, User> userMap;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", address=" + address +
                ", userList=" + userList +
                ", userMap=" + userMap +
                '}';
    }
}
