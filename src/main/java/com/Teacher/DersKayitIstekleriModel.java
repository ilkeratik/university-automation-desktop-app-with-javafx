package com.Teacher;

import javafx.beans.property.SimpleStringProperty;

public class DersKayitIstekleriModel {
        private final SimpleStringProperty id;
        private final SimpleStringProperty student;
        private final SimpleStringProperty state;

        DersKayitIstekleriModel(String id, String student, int state) {
            this.id = new SimpleStringProperty(id);
            this.student = new SimpleStringProperty(student);
            if(state == 1) {
                this.state = new SimpleStringProperty("Onaylanmis");
            }else{
                this.state = new SimpleStringProperty("Onaylanmamis");
            }
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

        public String getStudent() {
            return student.get();
        }

        public SimpleStringProperty studentProperty() {
            return student;
        }

        public void setStudent(String student) {
            this.student.set(student);
        }

        public String getState() {
            return state.get();
        }

        public SimpleStringProperty stateProperty() {
            return state;
        }

        public void setState(String state) {
            this.state.set(state);
        }
}

