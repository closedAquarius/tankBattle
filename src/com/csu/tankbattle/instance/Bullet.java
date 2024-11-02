package com.csu.tankbattle.instance;

import com.csu.tankbattle.controller.MapController;
import com.csu.tankbattle.model.MoveModel;
import javafx.scene.image.Image;

import java.io.IOException;

public class Bullet extends MoveModel {
    private Tank owner;
    private int id;
    private double speed;
    private double attack;

    private boolean isBulletAlive = true;

    public Tank getOwner() {
        return owner;
    }

    public void setOwner(Tank owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public boolean isBulletAlive() {
        return isBulletAlive;
    }

    public void setBulletAlive(boolean bulletAlive) {
        isBulletAlive = bulletAlive;
    }

    public Bullet() {}

    public Bullet(Tank tank) {
        owner = tank;
        id = tank.getBullets().size();
        setDirection(tank.getDirection());
        setAngle(tank.getAngle());
        attack = 20;
        speed = tank.getSpeed() + 1.0;
        this.setImage(new Image("/images/game/bullet.png"));
        this.setX(tank.getX() + 5);
        this.setY(tank.getY());
    }

    public Bullet(Tank tank, String path) {
        owner = tank;
        id = tank.getBullets().size();
        setDirection(tank.getDirection());
        setAngle(tank.getAngle());
        attack = 20;
        speed = tank.getSpeed() + 1.0;
        this.setImage(new Image(path));
        this.setX(tank.getX() + 5);
        this.setY(tank.getY());
    }


    public void move() {
        double angle = getDirection() / 360 * 2 * Math.PI;
        this.setX(this.getX() + speed * Math.cos(angle));
        this.setY(this.getY() - speed * Math.sin(angle));
    }

    public void judge(Tank tank,int m,int i) throws IOException {
        if(Math.sqrt((getX()-tank.getX())*(getX()-tank.getX())+(getY()-tank.getY())*(getY()-tank.getY())) < 25) {
            tank.hurt(this,m,i);
        }
    }
    public void judgeWall(int[][] map, Tank tank, MapController controller) throws IOException {

        if((this.getX()-183)/48.25>=18||(int)((this.getY())/48.25)>=16||(this.getX()-183)/48.25<0||(int)((this.getY())/48.25)<0){
            return;
        }
        switch ((map[(int)((this.getY())/48.25)][(int)((this.getX()-183)/48.25)])){
            case 1:
                controller.deleteItem((int)((this.getY())/48.25), (int)((this.getX()-183)/48.25));
                map[(int)((this.getY())/48.25)][(int)((this.getX()-183)/48.25)] = 0;
                break;
            case 2:this.speed=tank.getSpeed()+1.0;
                break;
            case 3:this.speed=tank.getSpeed();
                break;
            case 4:this.speed=tank.getSpeed()+1.0;
                this.setDirection(-(90-this.getDirection()));
                break;
        }
    }

    public double bang(Tank tank) {
        if(owner.equals(tank)) return 0;
        owner.getBullets().remove(this);
        return this.attack;
    }


    @Override
    public String toString() {
        return getX() + ":" + getY() + ":" + getAngle();
    }

    public static Bullet toBullet(String s,Tank tank) {
        Bullet result =  new Bullet(tank);
        String[] info = s.split(":");
        result.setX(Double.parseDouble(info[0]));
        result.setY(Double.parseDouble(info[1]));
        result.setAngle(Double.parseDouble(info[2]));

        return result;
    }
}
