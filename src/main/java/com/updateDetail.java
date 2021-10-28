package com;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.Student.Student;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class updateDetail implements Initializable {
    @FXML
    private ComboBox imagesBox;
    @FXML
    private TextField userName;
    @FXML
    private TextField userEmail;
    @FXML
    private TextField userPassword;
    @FXML
    private Label updateState;
    private User user;

    public updateDetail() { //constructor for fxml
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "icon",
                        "cloud",
                        "chat",
                        "content",
                        "browser",
                        "software"
                );
        imagesBox.setItems(options);
        user = LoginController.getInstance().getUser();
        userName.setText(user.getName());
        userEmail.setText(user.getEmail());
        imagesBox.getSelectionModel().selectFirst();
        userPassword.setText(user.getPassword());
    }

    public void updateUser() {
        updateUser(userName.getText(), userEmail.getText(), imagesBox.getSelectionModel().getSelectedItem().toString(), userPassword.getText());
    }

    public void updateUser(String name, String email, String picture, String password) {
        try {
            Connection con = DB_Connector.getInstance().getConnection();
            PreparedStatement stmt;
            if (user instanceof Student) {
                stmt = con.prepareStatement("UPDATE university.student SET email = ?, name=?, picture= ?, password=? WHERE id=?");

            } else {
                stmt = con.prepareStatement("UPDATE university.teacher SET email = ?, name=?, picture= ?, password=? WHERE id=?");
            }
            user.setName(name);
            user.setEmail(email);
            user.setPicture(picture);
            user.setPassword(password);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPicture());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getId());
            stmt.executeUpdate();
            updateState.setText("Basari ile guncellendi!");
        } catch (SQLException throwables) {
            updateState.setText("Bir hata olustu..");
            throwables.printStackTrace();
        }
    }


}


