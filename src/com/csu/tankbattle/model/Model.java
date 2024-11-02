package com.csu.tankbattle.model;

import javafx.scene.image.Image;

public class Model {
    private Image gridElement;
    private double x;
    private double y;


    public Model() {}
    public Model(Image image, double x, double y) {
        gridElement = image;
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return gridElement;
    }
    public void setImage(Image i) {
        gridElement = i;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

}
