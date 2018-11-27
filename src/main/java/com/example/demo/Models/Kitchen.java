package com.example.demo.Models;

public class Kitchen extends User{

    private String name;
    private String address;
    private String description;
    private boolean verified = false;

    public Kitchen(String name, String address, String description, boolean verified) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.verified = verified;
    }

    public Kitchen(int id, String username, String password, int role, String name, String address, String description, boolean verified) {
        super(id, username, password, role);
        this.name = name;
        this.address = address;
        this.description = description;
        this.verified = verified;
        super.setRole(2);
    }

    public Kitchen() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "Kitchen{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", verified=" + verified +
                '}';
    }
}
