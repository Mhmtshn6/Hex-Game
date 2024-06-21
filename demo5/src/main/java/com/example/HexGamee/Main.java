package com.example.HexGamee;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Menu menuController = new Menu(stage);
        Scene menuScene = new Scene(menuController.getMenuPane(), 800, 600);
        stage.setTitle("Hex Game");
        stage.setScene(menuScene);
        stage.show();
    }
}
