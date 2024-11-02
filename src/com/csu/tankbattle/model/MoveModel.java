package com.csu.tankbattle.model;

import javafx.scene.image.Image;

public class MoveModel extends Model{
    private double speed;
    private double direction;
    private double angle;

    public MoveModel() {}
    public MoveModel(Image image, double x, double y) {
        super(image,x,y);
    }

    public double getDirection() {
        return direction;
    }
    public void setDirection(double direction) {
        this.direction = direction;
    }
    public double getAngle() {
        return angle;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }
}
