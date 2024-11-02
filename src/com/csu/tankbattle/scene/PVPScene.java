package com.csu.tankbattle.scene;

import com.csu.tankbattle.controller.MapController;
import com.csu.tankbattle.instance.Bullet;
import com.csu.tankbattle.instance.Enemy;
import com.csu.tankbattle.instance.Tank;
import com.csu.tankbattle.util.PaintUtil;
import com.csu.tankbattle.util.RandomNumber;
import com.sun.media.jfxmediaimpl.HostUtils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PVPScene {
    public static Tank localTank1;
    public static Tank localTank2;

    public static int[][] map;
    private static MapController controller;
    public PVPScene(Stage stage, int mode) {
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
        localTank1 = new Tank(new Image("/images/game/Tank1.png"),(y1)*48.25+183,(x1)*48.25,100);
        localTank2 = new Tank(new Image("/images/game/Tank1.png"),(y2)*48.25+183,(x2)*48.25,100);

        mainPen.paint(localTank1);
        mainPen.paint(localTank2);


        //事件响应，信息传递
        stage.getScene().setOnKeyPressed(event -> {
            Platform.runLater(()->{
                localTank1.fire(event.getCode());
                localTank1.turn(event.getCode());
                try {
                    localTank1.move1(event.getCode());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Platform.runLater(()->{
                localTank2.fire1(event.getCode());
                localTank2.turn1(event.getCode());
                try {
                    localTank2.move2(event.getCode());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        //刷新界面
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            mainPen.clear();
            mainPen.paint(localTank1);
            mainPen.paint(localTank2);
            if(!localTank1.getBullets().isEmpty()) {
                List<Bullet> bulletCopy = new ArrayList<>(localTank1.getBullets());
                for(Bullet bullet: bulletCopy) {
                    bullet.move();
                    try {
                        bullet.judge(localTank2,1,2);
                        bullet.judgeWall(map,localTank1,controller);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    mainPen.paint(bullet);
                }
            }
            if(!localTank2.getBullets().isEmpty()) {
                List<Bullet> bulletCopy = new ArrayList<>(localTank2.getBullets());
                for(Bullet bullet: bulletCopy) {
                    if(bullet.isBulletAlive()){
                        bullet.move();
                        try {
                            bullet.judge(localTank1,1,1);
                            bullet.judgeWall(map,localTank2,controller);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        mainPen.paint(bullet);
                    }
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public static MapController getController() {
        return controller;
    }
}
