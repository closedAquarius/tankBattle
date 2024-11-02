package com.csu.tankbattle.scene;

import com.csu.tankbattle.controller.MapController;
import com.csu.tankbattle.instance.Buff;
import com.csu.tankbattle.instance.Bullet;
import com.csu.tankbattle.instance.Tank;
import com.csu.tankbattle.util.PaintUtil;
import com.csu.tankbattle.util.RandomNumber;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PVPBuffScene {
    public static Tank localTank1;
    public static Tank localTank2;

    public static int[][] map;
    private static MapController controller;
    private int timeCounter = 0;

    // 道具
    private static Buff[] healthBuff = new Buff[3];
    private static Buff[] speedBuff = new Buff[3];
    private static Buff[] positionBuff = new Buff[2];
    private static Buff[] moveDeBuff = new Buff[4];

    public PVPBuffScene(Stage stage, int mode) {
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
                    localTank1.move3(event.getCode());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Platform.runLater(()->{
                localTank2.fire1(event.getCode());
                localTank2.turn1(event.getCode());
                try {
                    localTank2.move4(event.getCode());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        //刷新界面
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if(timeCounter%200 == 0){
                generateBuff();
            }
            timeCounter++;
            mainPen.clear();
            mainPen.paint(localTank1);
            mainPen.paint(localTank2);
            for(int i = 0; i<healthBuff.length; i++){
                mainPen.paint(healthBuff[i]);
            }
            for(int i = 0; i<speedBuff.length; i++){
                mainPen.paint(speedBuff[i]);
            }
            for(int i = 0; i<positionBuff.length; i++){
                mainPen.paint(positionBuff[i]);
            }
            for(int i = 0; i<moveDeBuff.length; i++){
                mainPen.paint(moveDeBuff[i]);
            }

            if(!localTank1.getBullets().isEmpty()) {
                List<Bullet> bulletCopy = new ArrayList<>(localTank1.getBullets());
                for(Bullet bullet: bulletCopy) {
                    bullet.move();
                    try {
                        bullet.judge(localTank2,3,2);
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
                            bullet.judge(localTank1,3,1);
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


    public void generateBuff()
    {
        // 清除之前的道具
        for(int i = 0;i<map.length;i++)
        {
            for(int j = 0;j<map[i].length;j++)
            {
                if(map[i][j]==5 || map[i][j]==6 || map[i][j]==7 || map[i][j]==8){
                    map[i][j]=0;
                }
            }
        }

        // 生成新的道具
        int numberOfBuff = 0;
        while(numberOfBuff<=11)
        {
            int x = (int)(Math.random() * 16);
            int y = (int)(Math.random() * 16);
            if(map[x][y]==0){
                numberOfBuff++;

                if(1 <= numberOfBuff && numberOfBuff <= 3)
                {
                    map[x][y]=5;
                    healthBuff[numberOfBuff - 1] = new Buff(5, x, y);
                }
                else if(4 <= numberOfBuff && numberOfBuff <= 6)
                {
                    map[x][y]=6;
                    speedBuff[numberOfBuff - 4] = new Buff(6, x, y);
                }
                else if(7 <= numberOfBuff && numberOfBuff <= 8)
                {
                    map[x][y]=7;
                    positionBuff[numberOfBuff - 7] = new Buff(7, x, y);
                }
                else
                {
                    map[x][y]=8;
                    moveDeBuff[numberOfBuff - 9] = new Buff(8, x, y);
                }
            }
        }
    }

    public static Buff[] getMoveDeBuff() {
        return moveDeBuff;
    }

    public static Buff[] getPositionBuff() {
        return positionBuff;
    }

    public static Buff[] getSpeedBuff() {
        return speedBuff;
    }

    public static Buff[] getHealthBuff() {
        return healthBuff;
    }
}
