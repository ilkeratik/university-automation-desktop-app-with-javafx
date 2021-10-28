package com.Teacher;

import javafx.beans.property.SimpleStringProperty;

public class IstenenDersListesiModel {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty sinif;
    private final SimpleStringProperty akts;
    private final SimpleStringProperty credit;

    IstenenDersListesiModel(String id, String name, String sinif, String akts, String credit) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.sinif = new SimpleStringProperty(sinif);
        this.akts = new SimpleStringProperty(akts);
        this.credit = new SimpleStringProperty(credit);
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

    public String getCredit() {
        return credit.get();
    }

    public SimpleStringProperty creditProperty() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit.set(credit);
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

    public String getSinif() {
        return sinif.get();
    }

    public SimpleStringProperty sinifProperty() {
        return sinif;
    }

    public void setSinif(String sinif) {
        this.sinif.set(sinif);
    }


}
