package com.example.oneapptorulethemall;

public class User {
    String name;
    int age;
    String email;
    String password;
    String permission;

    //ctrl + insert creates this constructor automatically
    public User(String name, int age, String email, String password, String permission) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.permission = permission;

    }
    public User() {

    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPermission() {
        return permission;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
