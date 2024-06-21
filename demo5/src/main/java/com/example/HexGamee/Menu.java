package com.example.HexGamee;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu {
    private BorderPane menuPane;
    private Stage stage;

    public Menu(Stage stage) {
        this.stage = stage;
        createMenu();
    }

    private void createMenu() {
        menuPane = new BorderPane();

        Label title = new Label("HEX-GAME");
        title.setStyle("-fx-font-size: 55px; -fx-font-weight: Bold; -fx-background-color: Blue;");

        ToggleGroup sizeGroup = new ToggleGroup();
        RadioButton size5x5 = new RadioButton("5x5");
        size5x5.setToggleGroup(sizeGroup);
        size5x5.setSelected(true);
        RadioButton size11x11 = new RadioButton("11x11");
        size11x11.setToggleGroup(sizeGroup);
        RadioButton size17x17 = new RadioButton("17x17");
        size17x17.setToggleGroup(sizeGroup);

        HBox sizeSelector = new HBox(20, size5x5, size11x11, size17x17);
        sizeSelector.setPadding(new Insets(10));
        sizeSelector.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            RadioButton selectedSize = (RadioButton) sizeGroup.getSelectedToggle();
            startGame(selectedSize.getText());
        });

        HBox startButtonBox = new HBox(startButton);
        startButtonBox.setPadding(new Insets(20));
        startButtonBox.setAlignment(Pos.CENTER);

        VBox menuContent = new VBox(20, title, sizeSelector, startButtonBox);
        menuContent.setAlignment(Pos.CENTER);

        menuPane.setCenter(menuContent);
    }

    private void startGame(String size) {
        int boardSize = Integer.parseInt(size.split("x")[0]);
        GameModel hexBoard = new GameModel(boardSize, stage);

        BorderPane gameRoot = new BorderPane();
        gameRoot.setCenter(hexBoard.getHexGrid());

        Button restartButton = new Button("Restart Game");
        restartButton.setOnAction(e -> restartGame());

        HBox restartButtonBox = new HBox(restartButton);
        restartButtonBox.setPadding(new Insets(10));
        restartButtonBox.setAlignment(Pos.CENTER);
        gameRoot.setBottom(restartButtonBox);

        VBox leftBox = new VBox();
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(10));

        VBox rightBox = new VBox();
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(10));

        hexBoard.setTurnLabels(leftBox, rightBox);

        gameRoot.setLeft(leftBox);
        gameRoot.setRight(rightBox);

        Scene gameScene = new Scene(gameRoot, 800, 800);
        stage.setScene(gameScene);
        stage.widthProperty().addListener((obs, oldVal, newVal) -> hexBoard.centerHexGrid());
        stage.heightProperty().addListener((obs, oldVal, newVal) -> hexBoard.centerHexGrid());
    }

    private void restartGame() {
        Menu menuController = new Menu(stage);
        Scene menuScene = new Scene(menuController.getMenuPane(), 800, 600);
        stage.setScene(menuScene);
    }

    public BorderPane getMenuPane() {
        return menuPane;
    }
}
