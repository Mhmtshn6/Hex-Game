package com.example.HexGamee;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Hexagon {
    private Polygon hexagon;
    private boolean isEmpty;
    private int row;
    private int col;
    private String color;

    public Hexagon(int size, int row, int col) {
        hexagon = new Polygon();
        createHexagon(size);
        isEmpty = true;
        this.row = row;
        this.col = col;
        this.color = "none";
    }

    private void createHexagon(int size) {
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            double x = size * Math.cos(angle);
            double y = size * Math.sin(angle);
            hexagon.getPoints().addAll(x, y);
        }
        hexagon.setFill(Color.LIGHTGRAY);
        hexagon.setStroke(Color.BLACK);
        hexagon.setRotate(60);
    }

    public Polygon getHexagon() {
        return hexagon;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setColor(String color) {
        if (color.equals("red")) {
            hexagon.setFill(Color.RED);
        } else if (color.equals("blue")) {
            hexagon.setFill(Color.BLUE);
        }
        isEmpty = false;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
