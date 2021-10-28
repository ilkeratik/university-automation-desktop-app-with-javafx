package com.Teacher;

import javafx.beans.property.SimpleStringProperty;

public class TeacherNotGirmeModel {
    private final SimpleStringProperty id;
    private final SimpleStringProperty not;

    public TeacherNotGirmeModel(String id, String not){
        this.id = new SimpleStringProperty(id);
        this.not = new SimpleStringProperty(not);
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

    public String getNot() {
        return not.get();
    }

    public SimpleStringProperty notProperty() {
        return not;
    }

    public void setNot(String not) {
        this.not.set(not);
    }

}
