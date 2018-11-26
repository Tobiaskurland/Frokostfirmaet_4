package com.example.demo.Models;

public class Admin extends User {

    public Admin() {

    }

    public Admin(String username, String password, int role) {
        super(username, password, role);
        super.setRole(1);
    }

    @Override
    public String toString() {
        return "Admin{}";
    }
}
