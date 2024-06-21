package com.example.HexGamee;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class GameModel {
    private static final int HEX_SIZE = 20;
    private Pane hexGrid;
    private Stage stage;
    private int size;
    private boolean isRedTurn = true;
    private Label redLabel;
    private Label blueLabel;
    private Hexagon[][] hexagon;

    public GameModel(int size, Stage stage) {
        this.size = size;
        this.stage = stage;
        hexGrid = new Pane();
        hexagon = new Hexagon[size][size];
        createBoard(size);
        centerHexGrid();
    }

    private void createBoard(int size) {
        double hexHeight = HEX_SIZE * Math.sqrt(3);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Hexagon hex = new Hexagon(HEX_SIZE, row, col);
                hexagon[row][col] = hex;
                double x = col * (HEX_SIZE * 1.5);
                double y = row * hexHeight + (col % 2) * (hexHeight / 2);
                hex.getHexagon().setTranslateX(x);
                hex.getHexagon().setTranslateY(y);
                hexGrid.getChildren().add(hex.getHexagon());

                if (row == 0 || row == size - 1) {
                    hex.getHexagon().setStroke(Color.RED);
                }
                if (col == 0 || col == size - 1) {
                    hex.getHexagon().setStroke(Color.BLUE);
                }
                if ((row == 0 && col == 0) || (row == 0 && col == size - 1) ||
                        (row == size - 1 && col == 0) || (row == size - 1 && col == size - 1)) {
                    hex.getHexagon().setStroke(Color.BLACK);
                }

                hex.getHexagon().setOnMouseClicked(event -> handleCellClick(hex));
            }
        }
    }

    private void handleCellClick(Hexagon hex) {
        if (hex.isEmpty()) {
            if (isRedTurn) {
                hex.setColor("red");
                if (checkWin("red")) {
                    showWinner("Red");
                }
            } else {
                hex.setColor("blue");
                if (checkWin("blue")) {
                    showWinner("Blue");
                }
            }
            isRedTurn = !isRedTurn;
            updateTurnLabels();
        }
    }

    private boolean checkWin(String color) {
        Set<Hexagon> visited = new HashSet<>();
        if (color.equals("red")) {
            for (int col = 0; col < size; col++) {
                if (hexagon[0][col].getColor().equals("red") && dfs(hexagon[0][col], "red", visited)) {
                    return true;
                }
            }
        } else if (color.equals("blue")) {
            for (int row = 0; row < size; row++) {
                if (hexagon[row][0].getColor().equals("blue") && dfs(hexagon[row][0], "blue", visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(Hexagon cell, String color, Set<Hexagon> visited) {
        if (visited.contains(cell)) {
            return false;
        }
        visited.add(cell);
        if (color.equals("red") && cell.getRow() == size - 1) {
            return true;
        }
        if (color.equals("blue") && cell.getCol() == size - 1) {
            return true;
        }
        for (Hexagon neighbor : getNeighbors(cell)) {
            if (neighbor.getColor().equals(color) && dfs(neighbor, color, visited)) {
                return true;
            }
        }
        return false;
    }

    private Set<Hexagon> getNeighbors(Hexagon cell) {
        Set<Hexagon> neighbors = new HashSet<>();
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, -1}
        };
        for (int[] dir : directions) {
            int newRow = cell.getRow() + dir[0];
            int newCol = cell.getCol() + dir[1];
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                neighbors.add(hexagon[newRow][newCol]);
            }
        }
        return neighbors;
    }

    private void showWinner(String color) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Finish GAME");
        alert.setHeaderText(null);
        alert.setContentText(color + " Wins!");

        ButtonType playAgainButton = new ButtonType("Restart Game ");
        ButtonType closeButton = new ButtonType("Exit");

        alert.getButtonTypes().setAll(playAgainButton, closeButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == playAgainButton) {
                restartGame();
            } else {
                stage.close();
            }
        });
    }

    private void restartGame() {
        Menu menuController = new Menu(stage);
        Scene menuScene = new Scene(menuController.getMenuPane(), 800, 600);
        stage.setScene(menuScene);
    }

    public void setTurnLabels(VBox leftBox, VBox rightBox) {
        redLabel = new Label("Red Player");
        redLabel.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-padding: 10;");
        blueLabel = new Label("Blue Player");
        blueLabel.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-padding: 10;");

        updateTurnLabels();

        leftBox.getChildren().add(redLabel);
        rightBox.getChildren().add(blueLabel);
    }

    private void updateTurnLabels() {
        if (isRedTurn) {
            redLabel.setVisible(true);
            blueLabel.setVisible(false);
        } else {
            redLabel.setVisible(false);
            blueLabel.setVisible(true);
        }
    }

    public void centerHexGrid() {
        double hexGridWidth = size * HEX_SIZE * 1.5 + HEX_SIZE * 0.5;
        double hexGridHeight = size * HEX_SIZE * Math.sqrt(3);
        hexGrid.setTranslateX((stage.getWidth() - hexGridWidth) / 2);
        hexGrid.setTranslateY((stage.getHeight() - hexGridHeight) / 2);
    }

    public Pane getHexGrid() {
        return hexGrid;
    }
}
