package com;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
public class SceneBuilder {
    private Stage stage;
    private static SceneBuilder instance;

    private SceneBuilder(Stage stage){
        this.stage = stage;
        Image icon = new Image("/img/icon.png");
        stage.setTitle("Deneme APP");
        stage.getIcons().add(icon);
    }
    public static SceneBuilder getInstance(Stage stage){
        if (instance == null) {
            instance = new SceneBuilder(stage);
        }
        return instance;
    }
    public static SceneBuilder getInstance(){
        return instance;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public Stage getStage(){
        return stage;
    }

    public void sceneSwitcher(String target) throws IOException {
        Parent root = sceneLoader(target);
        Scene scene = new Scene(root);
        String css = getClass().getResource("../views/application.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    public Parent sceneLoader(String target) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/"+target+".fxml"));
        return root;
    }
}
