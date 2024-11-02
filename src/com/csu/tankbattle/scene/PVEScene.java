package com.csu.tankbattle.scene;

import com.csu.tankbattle.controller.MapController;
import com.csu.tankbattle.instance.Bullet;
import com.csu.tankbattle.instance.Enemy;
import com.csu.tankbattle.instance.Tank;
import com.csu.tankbattle.util.PaintUtil;
import com.csu.tankbattle.util.RandomNumber;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PVEScene {
    public static Tank localTank;

    public static int[][] map;

    private static MapController controller;

    public PVEScene(Stage stage) {
        PaintUtil mainPen = new PaintUtil();
        Pane pane = mainPen.getPane();
        stage.getScene().setRoot(pane);

        // 绘制地图
        Pane mapPane;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Map.fxml"));
        try {
            mapPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 获取Controller实例
        controller = loader.getController();

        map = RandomNumber.getRandomNumber();

        // 调用Controller的drawMap方法
        controller.drawMap(map);
        pane.getChildren().add(0, mapPane);

        int x1,x2;
        int y1,y2;
        //实例化
        while (true){
            x1=(int)(Math.random()*16);
            y1=(int)(Math.random()*18);
            x2=(int)(Math.random()*16);
            y2=(int)(Math.random()*18);
            if(map[x1][y1]==0&&map[x2][y2]==0&&!(x1==x2&&y1==y2)){
                break;
            }
        }
        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
        localTank = new Tank(new Image("/images/game/Tank1.png"),(y1)*48.25+183,(x1)*48.25,100);
        Enemy enemy = new Enemy(new Image("/images/game/Boss.gif"),(y2)*48.25+183,(x2)*48.25,100,20);

        mainPen.paint(localTank);

        //事件响应，信息传递
        stage.getScene().setOnKeyPressed(event -> {
            localTank.fire(event.getCode());
            localTank.turn(event.getCode());
            try {
                localTank.move(event.getCode());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            enemy.fire(KeyCode.SPACE);

        });

        AtomicInteger i= new AtomicInteger();
        //刷新界面
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            mainPen.clear();
            mainPen.paint(localTank);
            enemy.turn(KeyCode.LEFT);
            i.getAndIncrement();
            if(i.get() %3==0){
                enemy.fire(KeyCode.SPACE);
            }
            if(!localTank.getBullets().isEmpty()) {
                List<Bullet> bulletCopy = new ArrayList<>(localTank.getBullets());
                for(Bullet bullet: bulletCopy) {
                    bullet.move();
                    try {
                        bullet.judge(enemy,4,2);
                        bullet.judgeWall(map,localTank,controller);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    mainPen.paint(bullet);
                }
            }
            enemy.move();
            if(!enemy.getBullets().isEmpty()) {
                List<Bullet> bulletCopy = new ArrayList<>(enemy.getBullets());
                for(Bullet bullet: bulletCopy) {
                    if(bullet.isBulletAlive()){
                        bullet.move();
                        try {
                            bullet.judge(localTank,4,1);
                            bullet.judgeWall(map,enemy,controller);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        mainPen.paint(bullet);
                    }
                }
            }
            mainPen.paint(enemy);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static MapController getController() {
        return controller;
    }
}