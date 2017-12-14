package com.jackzhang.zoo.crud;
import java.io.Serializable;

public class User implements Serializable {

    private Integer userId;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}