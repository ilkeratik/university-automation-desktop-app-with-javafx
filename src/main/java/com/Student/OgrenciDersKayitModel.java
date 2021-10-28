package com.Student;

import javafx.beans.property.SimpleStringProperty;

public class OgrenciDersKayitModel {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty dept;
    private final SimpleStringProperty teacher;
    private final SimpleStringProperty sinif;
    private final SimpleStringProperty donem;
    private final SimpleStringProperty zs;
    private final SimpleStringProperty credit;
    private final SimpleStringProperty akts;

    public OgrenciDersKayitModel(String id, String name, String dept, String teacher, String sinif, String donem,
                                 String zs, String credit, String akts) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.dept = new SimpleStringProperty(dept);
        this.teacher = new SimpleStringProperty(teacher);
        this.sinif = new SimpleStringProperty(sinif);
        this.donem = new SimpleStringProperty(donem);
        this.zs = new SimpleStringProperty(zs);
        this.credit = new SimpleStringProperty(credit);
        this.akts = new SimpleStringProperty(akts);
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

    public String getTeacher() {
        return teacher.get();
    }

    public SimpleStringProperty teacherProperty() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher.set(teacher);
    }

    public String getSinif() {
        return sinif.get();
    }

    public SimpleStringProperty sinifProperty() {
        return sinif;
    }

    public void setSinif(String sinif) {
        this.sinif.set(sinif);
    }

    public String getDonem() {
        return donem.get();
    }

    public SimpleStringProperty donemProperty() {
        return donem;
    }

    public void setDonem(String donem) {
        this.donem.set(donem);
    }

    public String getZs() {
        return zs.get();
    }

    public SimpleStringProperty zsProperty() {
        return zs;
    }

    public void setZs(String zs) {
        this.zs.set(zs);
    }

    public String getCredit() {
        return credit.get();
    }

    public SimpleStringProperty creditProperty() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit.set(credit);
    }

    public String getAkts() {
        return akts.get();
    }

    public SimpleStringProperty aktsProperty() {
        return akts;
    }

    public void setAkts(String akts) {
        this.akts.set(akts);
    }
}
