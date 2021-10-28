package com;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.Student.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    @FXML
    private Label grade;
    @FXML
    private Label regDate;
    @FXML
    private Label danisman;
    @FXML
    private ImageView pictureHome;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Label faculty;
    @FXML
    private Label department;
    @FXML
    private HBox optional1;
    @FXML
    private HBox optional2;
    @FXML
    private HBox optional3;
    @FXML
    private VBox userv;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = LoginController.getInstance().getUser();
        System.out.println(user.getClass().getName());
        name.setText(user.getName());
        email.setText(user.getEmail());
        faculty.setText(user.getFaculty());
        department.setText(user.getDepartment());
        javafx.scene.image.Image image = new javafx.scene.image.Image(
                getClass().getResource("../img/"+user.getPicture()+".png").toExternalForm());
        pictureHome.setImage(image);
        if(user instanceof Student){
            grade.setText(((Student) user).getCurrentGrade());
            regDate.setText(((Student) user).getRegistryDate());
            danisman.setText(((Student) user).getDanisman());
        }
        else{
            userv.getChildren().removeAll(optional1,optional2,optional3); //removing student related fields
        }
    }
    public Home(){
    }
}
