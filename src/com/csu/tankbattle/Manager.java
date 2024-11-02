package com.csu.tankbattle;

import com.csu.tankbattle.scene.PVPBuffScene;
import com.csu.tankbattle.scene.PVPScene;
import com.csu.tankbattle.scene.PVEScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Manager {
    public static final int WIDTH = 592;
    public static final int HEIGHT = 589;

    private static Manager manager = new Manager();
    private Stage stage;

    private Manager() {}

    public static Manager getInstance() {
        return manager;
    }

    /**
     * 初始化
     * @param stage 总舞台
     */
    public void init(Stage stage) {
        try {
            this.stage = stage;
            //加载登录界面
            Pane pane = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
            stage.setScene(new Scene(pane));

            stage.setResizable(false);
            stage.setTitle("TankBattle");

            stage.setWidth(WIDTH);
            stage.setHeight(HEIGHT);

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void IntoLogin() {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.getScene().setRoot(pane);
    }

    /**
     * 切换到注册界面
     */
    public void IntoRegister() {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/fxml/RegisterPage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.getScene().setRoot(pane);
    }

    /**
     * 切换到菜单界面
     */
    public void IntoIndex() {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
            stage.setWidth(1080);
            stage.setHeight(810);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.getScene().setRoot(pane);
    }


    /**
     * 切换到游戏界面
     */
    public void IntoGame(int mode) {
        switch (mode) {
            case 1:
                new PVPScene(stage,1);break;
            case 2:
                new PVPScene(stage,2);break;
            case 3:
                new PVPBuffScene(stage,3);break;
            case 4:
                new PVEScene(stage);break;
        }

    }
}
