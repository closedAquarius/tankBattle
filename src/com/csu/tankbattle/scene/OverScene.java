package com.csu.tankbattle.scene;

import com.csu.tankbattle.util.DBUtil;
import com.csu.tankbattle.util.Integrator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class OverScene {
    private static Stage stage = new Stage();
    public OverScene() {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/fxml/SettlePage.fxml"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("结算页面");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    public static void close()
    {
        stage.close();
    }
}
