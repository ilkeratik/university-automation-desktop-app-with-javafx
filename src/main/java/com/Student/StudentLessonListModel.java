package com.Student;

import javafx.beans.property.SimpleStringProperty;

public class StudentLessonListModel {

    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty vize;
    private final SimpleStringProperty finalN;
    private final SimpleStringProperty average;
    private final SimpleStringProperty grade;
    private final SimpleStringProperty state;

    public StudentLessonListModel(String id, String name, String vize,
                           String finalN, String average, String grade, String state) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.vize = new SimpleStringProperty(vize);
        this.finalN = new SimpleStringProperty(finalN);
        this.average = new SimpleStringProperty(average);
        this.grade = new SimpleStringProperty(grade);
        this.state = new SimpleStringProperty(state);

    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getVize() {
        return vize.get();
    }

    public SimpleStringProperty vizeProperty() {
        return vize;
    }

    public String getFinalN() {
        return finalN.get();
    }

    public SimpleStringProperty finalNProperty() {
        return finalN;
    }

    public String getAverage() {
        return average.get();
    }

    public SimpleStringProperty averageProperty() {
        return average;
    }

    public String getGrade() {
        return grade.get();
    }

    public SimpleStringProperty gradeProperty() {
        return grade;
    }

    public String getState() {
        return state.get();
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }
}
