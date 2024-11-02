package com.csu.tankbattle.controller;

import com.csu.tankbattle.Manager;
import com.csu.tankbattle.util.DBUtil;
import com.csu.tankbattle.util.Integrator;
import com.sun.javafx.text.TextLine;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class LoginController {

    @FXML
    private ImageView btLogin;
    @FXML
    private PasswordField textPassword;
    @FXML
    private ImageView btRegister;
    @FXML
    private TextField textUsername;

    @FXML
    void buttonEnter(MouseEvent event) {
        ImageView target = (ImageView) (event.getTarget());
        target.setOpacity(0.5);
    }

    @FXML
    void buttonExit(MouseEvent event) {
        ImageView target = (ImageView) (event.getTarget());
        target.setOpacity(1);
    }

    @FXML
    void loginClicked(MouseEvent event) {
        String username = textUsername.getText();
        String password = textPassword.getText();

        if(exist(username,password)) {
            Manager.getInstance().IntoIndex();
            Integrator.getInstance().setUsername(username);
        } else {
            if(DBUtil.query(username).isEmpty()) Manager.getInstance().IntoRegister();
            else RegisterController.warn("√‹¬Î¥ÌŒÛ",false);
        }
    }

    @FXML
    void registerClicked(MouseEvent event) {
        Manager.getInstance().IntoRegister();
    }


    public static boolean exist(String username,String password) {
        ArrayList<String> query = DBUtil.query(username);
        if(query.isEmpty()) return false;
        if(!password.equals(query.get(2))) return false;
        return true;
    }
}