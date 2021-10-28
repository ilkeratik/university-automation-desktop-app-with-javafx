package com.Teacher;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import com.SceneBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherMangWindow implements Initializable {
    @FXML
    private Pane mainMenuPane;
    @FXML
    private JFXHamburger menuHam;
    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(menuHam);
        burgerTask.setRate(1);
        burgerTask.play();
        menuHam.setOnMouseClicked((e)->{
            burgerTask.setRate(burgerTask.getRate()*-1);
            burgerTask.play();
            if (borderPane.getLeft() == null){
                borderPane.setLeft(this.mainMenuPane);
            }
            else{
                borderPane.setLeft(null);
            }
        });
    }

    public void goUserUpdate() throws IOException {
        Parent root = SceneBuilder.getInstance().sceneLoader("UpdateDetail");
        SubScene sc = new SubScene(root,980,667);
        borderPane.setCenter(sc);
    }
    public void goHome() throws IOException {
        Parent root = SceneBuilder.getInstance().sceneLoader("Home");
        SubScene sc = new SubScene(root,980,667);
        borderPane.setCenter(sc);
    }
    public void goTeacherDersYonetimi() throws IOException {
        Parent root = SceneBuilder.getInstance().sceneLoader("TeacherDersYonetimi");
        SubScene sc = new SubScene(root,980,667);
        borderPane.setCenter(sc);
    }
    public void goTeacherDersKayit() throws IOException {
        Parent root = SceneBuilder.getInstance().sceneLoader("TeacherDersKayit");
        SubScene sc = new SubScene(root,980,667);
        borderPane.setCenter(sc);
    }
    public void goDuyurular() throws IOException{
        Parent root = SceneBuilder.getInstance().sceneLoader("Duyurular");
        SubScene sc = new SubScene(root,980,667);
        borderPane.setCenter(sc);
    }
}
