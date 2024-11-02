package com.csu.tankbattle.instance;

import com.csu.tankbattle.model.MoveModel;
import com.csu.tankbattle.scene.*;
import com.csu.tankbattle.util.Integrator;
import com.csu.tankbattle.util.SoundUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javafx.util.Duration;

public class Tank extends MoveModel {
    private String id;
    private double health;
    private double speed;
    protected ArrayList<Bullet> bullets = new ArrayList<>();
    protected boolean isDead;
    public Tank() {}

    public Tank(Image i, double x, double y, double health) {

        super(i,x,y);
        setDirection(0);
        setAngle(0);
        this.health = health;
        this.speed = 5;
        isDead = false;
    }
    public double getSpeed() {
        return speed;
    }
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void fire(KeyCode keyCode) {
        if(isDead) return;
        if(keyCode == KeyCode.SPACE) {
            String path = "/images/game/bullet.png";
            bullets.add(new Bullet(this, path));
            SoundUtil.playSoundEffect("/sound/sound effect/boom.mp3");
            if(bullets.size() > 10) {
                bullets.remove(0);
            }
        }
    }

    public void fire1(KeyCode keyCode) {
        if(isDead) return;
        if(keyCode == KeyCode.J){
            String path = "/images/game/bullet.png";
            bullets.add(new Bullet(this, path));
            SoundUtil.playSoundEffect("/sound/sound effect/boom.mp3");
            if(bullets.size() > 10) {
                bullets.remove(0);
            }
        }
    }

    public void move(KeyCode keyCode) throws IOException {
        if(isDead) return;
        if(keyCode.equals(KeyCode.UP)) {
            double angle = getDirection() / 360 * 2 * Math.PI;
            double tx = this.getX() + speed * Math.cos(angle);
            double ty = this.getY() - speed * Math.sin(angle);
            if(!ifHittingWall(PVEScene.map, tx, ty)) {
                this.setX(this.getX() + speed * Math.cos(angle));
                this.setY(this.getY() - speed * Math.sin(angle));
            }
        } else if (keyCode.equals(KeyCode.DOWN)) {
            double angle = getDirection() / 360 * 2 * Math.PI;
            double tx = this.getX() - speed * Math.cos(angle);
            double ty = this.getY() + speed * Math.sin(angle);
            if(!ifHittingWall(PVEScene.map, tx, ty)) {
                this.setX(this.getX() - speed * Math.cos(angle));
                this.setY(this.getY() + speed * Math.sin(angle));
            }
        }
    }
    public void move1(KeyCode keyCode) throws IOException {
        if(isDead) return;
        if(keyCode.equals(KeyCode.UP)) {
            double angle = getDirection() / 360 * 2 * Math.PI;
            double tx = this.getX() + speed * Math.cos(angle);
            double ty = this.getY() - speed * Math.sin(angle);
            if(!ifHittingWall(PVPScene.map, tx, ty)) {
                this.setX(this.getX() + speed * Math.cos(angle));
                this.setY(this.getY() - speed * Math.sin(angle));
            }
        } else if (keyCode.equals(KeyCode.DOWN)) {
            double angle = getDirection() / 360 * 2 * Math.PI;
            double tx = this.getX() - speed * Math.cos(angle);
            double ty = this.getY() + speed * Math.sin(angle);
            if(!ifHittingWall(PVPScene.map, tx, ty)) {
                this.setX(this.getX() - speed * Math.cos(angle));
                this.setY(this.getY() + speed * Math.sin(angle));
            }
        }
    }
    public void move2(KeyCode keyCode) throws IOException {
        if(isDead) return;
        if(keyCode.equals(KeyCode.W)) {
            double angle = getDirection() / 360 * 2 * Math.PI;
            double tx = this.getX() + speed * Math.cos(angle);
            double ty = this.getY() - speed * Math.sin(angle);
            if(!ifHittingWall(PVPScene.map, tx, ty)) {
                this.setX(this.getX() + speed * Math.cos(angle));
                this.setY(this.getY() - speed * Math.sin(angle));
            }
        } else if (keyCode.equals(KeyCode.S)) {
            double angle = getDirection() / 360 * 2 * Math.PI;
            double tx = this.getX() - speed * Math.cos(angle);
            double ty = this.getY() + speed * Math.sin(angle);
            if(!ifHittingWall(PVPScene.map, tx, ty)) {
                this.setX(this.getX() - speed * Math.cos(angle));
                this.setY(this.getY() + speed * Math.sin(angle));
            }
        }
    }

    public void move3(KeyCode keyCode) throws IOException {
        if(isDead) return;
        if(keyCode.equals(KeyCode.UP)) {
            double angle = getDirection() / 360 * 2 * Math.PI;
            double tx = this.getX() + speed * Math.cos(angle);
            double ty = this.getY() - speed * Math.sin(angle);
            if(!ifHittingWall(PVPBuffScene.map, tx, ty)) {
                this.setX(this.getX() + speed * Math.cos(angle));
                this.setY(this.getY() - speed * Math.sin(angle));
                switch(ifHittingBuff(PVPBuffScene.map, getX(), getY()))
                {
                    case 5:
                        this.setHealth(100);
                        //PVPBuffScene.map[(int)(getY()/48.25)][(int)((getX()-183)/48.25)] = 0;
                        PVPBuffScene.getController().changeHealth1(this);
                        //this.destroyBuff(PVPBuffScene.map,(int)(getY()/48.25),(int)((getX()-183)/48.25),1);
                        break;
                    case 6:
                        this.setSpeed(100);
                        //PVPBuffScene.map[(int)(getY()/48.25)][(int)((getX()-183)/48.25)] = 0;
                        //this.destroyBuff(PVPBuffScene.map,(int)(getY()/48.25),(int)((getX()-183)/48.25),1);
                        break;
                    case 7:
                        int x1;
                        int y1;
                        //实例化
                        while (true){
                            x1=(int)(Math.random()*16);
                            y1=(int)(Math.random()*18);
                            if(PVPBuffScene.map[x1][y1]==0)
                            {
                                break;
                            }
                        }
                        this.setX(x1 * 48.25 + 183);
                        this.setY(y1 * 48.25);
                        break;
                    case 8:
                        this.setSpeed(0);
//                        Timeline timeline = new Timeline(
//                                new KeyFrame(Duration.seconds(5), event -> {
//                                    // 在5秒后将速度设置为5
//                                    this.setSpeed(5);
//                                })
//                        );
                        //PVPBuffScene.map[(int)(getY()/48.25)][(int)((getX()-183)/48.25)] = 0;
                        //this.destroyBuff(PVPBuffScene.map,(int)(getY()/48.25),(int)((getX()-183)/48.25),1);
                        break;
                    default:
                        break;
                }
            }
        } else if (keyCode.equals(KeyCode.DOWN)) {
            double angle = getDirection() / 360 * 2 * Math.PI;
            double tx = this.getX() - speed * Math.cos(angle);
            double ty = this.getY() + speed * Math.sin(angle);
            if(!ifHittingWall(PVPBuffScene.map, tx, ty)) {
                this.setX(this.getX() - speed * Math.cos(angle));
                this.setY(this.getY() + speed * Math.sin(angle));
            }
        }
    }
    public void move4(KeyCode keyCode) throws IOException {
        if(isDead) return;
        if(keyCode.equals(KeyCode.W)) {
            double angle = getDirection() / 360 * 2 * Math.PI;
            double tx = this.getX() + speed * Math.cos(angle);
            double ty = this.getY() - speed * Math.sin(angle);
            if(!ifHittingWall(PVPBuffScene.map, tx, ty)) {
                this.setX(this.getX() + speed * Math.cos(angle));
                this.setY(this.getY() - speed * Math.sin(angle));
                switch(ifHittingBuff(PVPBuffScene.map, getX(), getY()))
                {
                    case 5:
                        this.setHealth(100);
                        //PVPBuffScene.map[(int)(getY()/48.25)][(int)((getX()-183)/48.25)] = 0;
                        PVPBuffScene.getController().changeHealth1(this);
                        //this.destroyBuff(PVPBuffScene.map,(int)(getY()/48.25),(int)((getX()-183)/48.25),1);
                        break;
                    case 6:
                        this.setSpeed(100);
                        //PVPBuffScene.map[(int)(getY()/48.25)][(int)((getX()-183)/48.25)] = 0;
                        //this.destroyBuff(PVPBuffScene.map,(int)(getY()/48.25),(int)((getX()-183)/48.25),1);
                        break;
                    case 7:
                        int x1;
                        int y1;
                        //实例化
                        while (true){
                            x1=(int)(Math.random()*16);
                            y1=(int)(Math.random()*18);
                            if(PVPBuffScene.map[x1][y1]==0)
                            {
                                break;
                            }
                        }
                        this.setX(x1 * 48.25 + 183);
                        this.setY(y1 * 48.25);
                        //PVPBuffScene.map[(int)(getY()/48.25)][(int)((getX()-183)/48.25)] = 0;
                        //this.destroyBuff(PVPBuffScene.map,(int)(getY()/48.25),(int)((getX()-183)/48.25),1);
                        break;
                    case 8:
                        this.setSpeed(0);
//                        Timeline timeline = new Timeline(
//                                new KeyFrame(Duration.seconds(5), event -> {
//                                    // 在5秒后将速度设置为5
//                                    this.setSpeed(5);
//                                })
//                        );
                        //PVPBuffScene.map[(int)(getY()/48.25)][(int)((getX()-183)/48.25)] = 0;
                        //this.destroyBuff(PVPBuffScene.map,(int)(getY()/48.25),(int)((getX()-183)/48.25),1);
                        break;
                    default:
                        break;
                }
            }
        } else if (keyCode.equals(KeyCode.S)) {
            double angle = getDirection() / 360 * 2 * Math.PI;
            double tx = this.getX() - speed * Math.cos(angle);
            double ty = this.getY() + speed * Math.sin(angle);
            if(!ifHittingWall(PVPBuffScene.map, tx, ty)) {
                this.setX(this.getX() - speed * Math.cos(angle));
                this.setY(this.getY() + speed * Math.sin(angle));
            }
        }
    }

 

    public void turn(KeyCode keyCode) {
        if(isDead) return;
        switch (keyCode) {
            case LEFT:
                setDirection(getDirection()+15);break;
            case RIGHT:
                setDirection(getDirection()-15);break;
        }
        switch (keyCode) {
            case LEFT:
                setAngle(getAngle()-15);break;
            case RIGHT:
                setAngle(getAngle()+15);break;
        }
        setDirection(getDirection()%360);
        setAngle(getAngle()%360);
    }
    public void turn1(KeyCode keyCode) {
        if(isDead) return;
        switch (keyCode) {
            case A:
                setDirection(getDirection()+15);break;
            case D:
                setDirection(getDirection()-15);break;
        }
        switch (keyCode) {
            case A:
                setAngle(getAngle()-15);break;
            case D:
                setAngle(getAngle()+15);break;
        }
        setDirection(getDirection()%360);
        setAngle(getAngle()%360);
    }

    public void hurt(Bullet bullet, int m,int i) throws IOException {
        if(isDead) return;
        health -= bullet.bang(this);
        if(this.health <= 0) die(m,i);
        switch (m){
            case 1:switch (i){
                case 1:PVPScene.getController().changeHealth1(this);break;
                case 2:PVPScene.getController().changeHealth2(this);break;
            }
            case 2:switch (i){
                case 1:PVPScene.getController().changeHealth1(this);break;
                case 2:PVPScene.getController().changeHealth2(this);break;
            }
                case 3:switch (i){
                    case 1:
                        PVPBuffScene.getController().changeHealth1(this);break;
                    case 2:PVPBuffScene.getController().changeHealth2(this);break;
                }
                    case 4:switch (i){
                        case 1:PVEScene.getController().changeHealth1(this);break;
                        case 2:PVEScene.getController().changeHealth2(this);break;
                    }
        }


    }

//    public void hurt(Hole hole) throws IOException {
//        if(isDead) return;
//        this.health = 0;
//        die();
//    }

    public void die(int m,int i) throws IOException {
        isDead = true;
        bullets.clear();
        this.setImage(new Image("/images/game/TankDie.png"));
        switch (m){
            case 4:
                switch (i){
                    case 1: Integrator.getInstance().setResult("you are dead");
                        new OverScene();
                    case 2:        Integrator.getInstance().addScore(Enemy.score);
                        Integrator.getInstance().setResult("you win!");
                        new OverScene();
                }
            case 1: switch (i){
                case 1: Integrator.getInstance().setResult("P2 win!");
                    new OverScene();
                case 2: Integrator.getInstance().setResult("P1 win!");
                    new OverScene();
            }
            case 2:switch (i){
                case 1: Integrator.getInstance().setResult("P2 win!");
                    new OverScene();
                case 2: Integrator.getInstance().setResult("P1 win!");
                    new OverScene();
            }
            case 3:switch (i){
                case 1: Integrator.getInstance().setResult("P2 win!");
                new OverScene();
                case 2: Integrator.getInstance().setResult("P1 win!");
                new OverScene();
            }
        }


        SoundUtil.playSoundEffect("/sound/sound effect/boom.mp3");
    }


    @Override
    public String toString() {
        try {
            return Inet4Address.getLocalHost().getHostAddress().toString() + ";" + getX() + ";" + getY() + ";" +
                    getAngle() + ";" + health + ";" + isDead + ";" + getBullets(). toString();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static Tank toTank(String s) {
        Tank result = new Tank();
        String[] info = s.split(";");

        result.setX(Double.parseDouble(info[1]));
        result.setY(Double.parseDouble(info[2]));
        result.setAngle(Double.parseDouble(info[3]));
        result.setHealth(Double.parseDouble(info[4]));
        result.setDead(Boolean.getBoolean(info[5]));

        if(info.length > 6) {
            String[] bulletsInfo = info[6].split("[\\[\\],]");
            for(int i = 6; i < info.length; i++) {
                result.getBullets().add(Bullet.toBullet(bulletsInfo[i-6],result));
            }
        }

        return result;
    }

    public boolean ifHittingWall(int[][] map, double x, double y) throws IOException {
        if(((int)((x-183)/48.25) >= 16 || (int)(y/48.25) >= 18 || (int)((x-183)/48.25) <0 || (int)(y/48.25) <0))
        {
            return true;
        }
        switch ((map[(int)(y/48.25)][(int)((x-183)/48.25)])){
            case 0:
                this.speed = 5;
                return false;
            case 1:
                return true;
            case 2:
                this.speed = 5;
                return false;
            case 3:
                if(this.speed>=1){
                    this.speed=this.getSpeed()-0.5;
                }
                return false;
            case 4:
                return true;
        }
        return false;
    }

    public int ifHittingBuff(int[][] map, double x, double y) throws IOException
    {
        if(((int)((x-183)/48.25) >= 16 || (int)(y/48.25) >= 18 || (int)((x-183)/48.25) <0 || (int)(y/48.25) <0))
        {
            return 0;
        }
        switch ((map[(int)(y/48.25)][(int)((x-183)/48.25)])){
            case 5:
                this.speed = 5;
                return 5;
            case 6:
                return 6;
            case 7:
                this.speed = 5;
                return 7;
            case 8:
                if(this.speed>=1){
                    this.speed=this.getSpeed()-0.5;
                }
                return 8;
        }
        return 0;
    }

//    public void destroyBuff(int[][] map, int x, int y, int i)
//    {
//        ////int x1 = (int)((x - 183) / 49 + 1);
//        //int y1 = (int)(y / 49 + 1);
//
//        int x2 = x * 49 + 183;
//        int y2 = y * 49;
//
//        System.out.println("cangmangdetianyashiwodeai:" + x2 + " " + y2);
//
//        switch(i)
//        {
//            case 1:
//                Buff[] healthBuff = PVPBuffScene.getHealthBuff();
//                for(int j = 0; j < healthBuff.length; j++)
//                {
//                    if(Math.abs(healthBuff[j].getX() - x2) < 1e-6 && Math.abs(healthBuff[j].getY() - y2) < 1e-6)
//                    {
//                        healthBuff[j].setImage(new Image("/images/game/healthBuffNone.png"));
//                    }
//                }
//            case 2:
//                Buff[] speedBuff = PVPBuffScene.getSpeedBuff();
//                for(int j = 0; j < speedBuff.length; j++)
//                {
//                    if(Math.abs(speedBuff[j].getX() - x2) < 1e-6 && Math.abs(speedBuff[j].getY() - y2) < 1e-6)
//                    {
//                        speedBuff[j].setImage(new Image("/images/game/healthBuffNone.png"));
//                    }
//                }
//            case 3:
//                Buff[] positionBuff = PVPBuffScene.getPositionBuff();
//                for(int j = 0; j < positionBuff.length; j++)
//                {
//                    if(Math.abs(positionBuff[j].getX() - x2) < 1e-6 && Math.abs(positionBuff[j].getY() - y2) < 1e-6)
//                    {
//                        positionBuff[j].setImage(new Image("/images/game/positionBuffNone.png"));
//                    }
//                }
//            case 4:
//                Buff[] moveDeBuff = PVPBuffScene.getMoveDeBuff();
//                for(int j = 0; j < moveDeBuff.length; j++)
//                {
//                    if(Math.abs(moveDeBuff[j].getX() - x2) < 1e-6 && Math.abs(moveDeBuff[j].getY() - y2) < 1e-6)
//                    {
//                        moveDeBuff[j].setImage(new Image("/images/game/healthBuffNone.png"));
//                    }
//                }
//        }
//    }
}
