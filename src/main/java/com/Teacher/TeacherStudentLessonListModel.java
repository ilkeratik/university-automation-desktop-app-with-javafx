package com.Teacher;

import javafx.beans.property.SimpleStringProperty;

public class TeacherStudentLessonListModel {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty dept;
    private final SimpleStringProperty classN;

    public TeacherStudentLessonListModel(String id ,String name, String dept, String classN){
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.dept = new SimpleStringProperty(dept);
        this.classN = new SimpleStringProperty(classN);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDept() {
        return dept.get();
    }

    public SimpleStringProperty deptProperty() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept.set(dept);
    }

    public String getClassN() {
        return classN.get();
    }

    public SimpleStringProperty classNProperty() {
        return classN;
    }

    public void setClassN(String classN) {
        this.classN.set(classN);
    }
}
