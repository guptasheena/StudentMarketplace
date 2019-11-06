package com.cmpe277.studentmarketplace;

public class User {
    private int id;
    private String first_name, last_name, address, email, phone, password;

    public User(){}

    public User (int id, String first_name, String last_name, String email, String password, String address, String phone) {
        this.id = id;
        this.first_name = first_name;
        this.last_name= last_name;
        this.address = address;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
