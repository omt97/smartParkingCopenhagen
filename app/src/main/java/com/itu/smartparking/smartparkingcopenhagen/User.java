package com.itu.smartparking.smartparkingcopenhagen;

import java.util.UUID;

public class User {

    private String user;
    private String password;
    private int admin;

    public User(String user, String password, int admin) {
        this.user = user;
        this.password = password;
        this.admin = admin;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
