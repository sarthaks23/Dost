package com.sanjyog.www.tabbedacttest.model;



/**
 * Created by Sarthak on 1/6/2016.
 */
public class User {
    private String username, email, password;

    public User(){

    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }


}
