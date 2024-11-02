package com.csu.tankbattle.instance;

import com.csu.tankbattle.scene.OverScene;
import com.csu.tankbattle.util.Integrator;
import com.csu.tankbattle.util.SoundUtil;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.util.Random;

public class Enemy extends Tank {
    private double targetX;
    private double targetY;
    public static int score;

    @Override
    public void fire(KeyCode keyCode) {
        if(isDead) return;
        if(keyCode == KeyCode.SPACE) {
            if(bullets.size()>=20){
                bullets.remove(0);
            }
            String path = "/images/game/fire.png";
            bullets.add(new Bullet(this, path));
//            SoundUtil.playSoundEffect("/sound/sound effect/boom.mp3");
        }
    }

    public Enemy(Image i, double x, double y, double health, int score) {
        super(i,x,y,health);
        this.score = score;
        Random random = new Random();
        targetX = random.nextInt(1060);
        targetY = random.nextInt(780);
    }


    public void move() {
        if(isDead) return;
        Random random = new Random();
        if(Math.sqrt((getX()-targetX)*(getX()-targetX)+(getY()-targetY)*(getY()-targetY)) < 5) {
            targetX = random.nextInt(1060);
            targetY = random.nextInt(780);
        }
        else {
            double xCorrect = Math.abs(targetX - getX()) / Math.sqrt((getX()-targetX)*(getX()-targetX)+(getY()-targetY)*(getY()-targetY));
            double yCorrect = Math.abs(targetY - getY()) / Math.sqrt((getX()-targetX)*(getX()-targetX)+(getY()-targetY)*(getY()-targetY));

            if(targetX > getX()) {
                setX((int)(getX() + getSpeed()/2 * xCorrect));
            }
            else {
                setX((int)(getX() - getSpeed()/2 * xCorrect));
            }

            if(targetY > getY()) {
                setY((int)(getY() + getSpeed()/2 * yCorrect));
            }
            else {
                setY((int)(getY() - getSpeed()/2 * yCorrect));
            }
        }
    }

    public void die() throws IOException {
        isDead = true;
        bullets.clear();
        Integrator.getInstance().addScore(score);
        Integrator.getInstance().setResult("you win!");
        new OverScene();
        this.setImage(new Image("/images/game/TankDie.jpg"));
       //SoundUtil.playSoundEffect("/sound/sound effect/victory.mp3");
    }
}
