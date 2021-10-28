package com;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneBuilder.getInstance(primaryStage).sceneSwitcher("Login");
    }

    public static void main(String[] args) {
        launch(args);
    }//settings
}
