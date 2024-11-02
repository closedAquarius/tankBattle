package com.csu.tankbattle.controller;


import com.csu.tankbattle.Manager;
import com.csu.tankbattle.scene.OverScene;
import com.csu.tankbattle.util.DBUtil;
import com.csu.tankbattle.util.Integrator;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class OverController {
    @FXML
    private Button back;
    @FXML
    private Label result;

    @FXML
    private Label score;

    @FXML
    private ImageView backGround;

    @FXML
    private Label record;


    @FXML
    private void initialize() {
        // 初始化代码
        result.setText(Integrator.getInstance().getResult());
        score.setText(Integrator.getInstance().getScore()+"");
        ArrayList<String> results = DBUtil.query(Integrator.getInstance().getUsername());
        record.setText(results.get(3));
        if(results.get(3)==null|| Integrator.getInstance().getScore()>=Integer.parseInt(results.get(3))){
            DBUtil.update("UPDATE users SET score=? WHERE account=?",Integrator.getInstance().getScore(),Integrator.getInstance().getUsername());
        }
    }

    @FXML
    void  backTo(MouseEvent event) {
        Manager.getInstance().IntoIndex();
        OverScene.close();
    }

}
