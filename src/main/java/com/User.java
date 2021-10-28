package com;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String picture;
    private String faculty;
    private String department;
    private String scene;
    public User(String id, String name, String email, String password,
                String picture, String faculty, String department, String scene) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.faculty = faculty;
        this.department = department;
        this.scene = scene;
    }

    public String getScene(){
        return scene;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
