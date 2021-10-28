package com.Teacher;

import javafx.beans.property.SimpleStringProperty;

public class TeacherLessonListModel {

    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty department;
    private final SimpleStringProperty year;

    public TeacherLessonListModel(String id, String department, String name, String year){
        this.id = new SimpleStringProperty(id);
        this.department = new SimpleStringProperty(department);
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleStringProperty(year.substring(0,4));

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

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getYear() {
        return year.get();
    }

    public SimpleStringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }


}
