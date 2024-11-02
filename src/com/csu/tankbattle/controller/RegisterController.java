package com.csu.tankbattle.controller;

import com.csu.tankbattle.Manager;
import com.csu.tankbattle.util.DBUtil;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

public class RegisterController {

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
    void registerClicked(MouseEvent event) {
        String username = textUsername.getText();
        String password = textPassword.getText();

        //密码包含数字和字母
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(password.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }

        if(username.isEmpty() && password.isEmpty()){


            warn("账号密码不得为空",false);
        } else if(password.length() < 3){
            warn("密码长度不得小于3",false);
        } else if(!(isDigit && isLetter)){
            warn("密码必须是数字和字母的组合",false);
        }
        else if(!DBUtil.query(username).isEmpty()){
            warn("该账号已存在",false);
        }
        else{
            DBUtil.insert(username,password);
            warn("注册成功,请重新登陆",true);
        }

        Manager.getInstance().IntoLogin();
    }

    //发出提示
    public static void warn(String message,boolean flag) {


//        JLabel label = new JLabel(message);
//        Font currentFont = label.getFont();
//        Font newFont = currentFont.deriveFont(Font.BOLD, 20);
//        label.setFont(newFont);

        // 创建一个Label
        Label label = new Label(message);

        // 设置Label的字体为Arial，大小为20
        label.setFont(new Font("SimHei", 20));

        // 将Label添加到StackPane中
        StackPane root = new StackPane();
        root.getChildren().add(label);


        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        label.setText(message);



        //设置图标和标题
        if(flag){
            javafx.scene.image.Image image = new Image("/images/home/R-R.png");
            ImageView imageView = new ImageView(image);
            HBox hBox = new HBox(10,imageView,label);
            hBox.setAlignment(Pos.CENTER);

            stage.setTitle("SUCCESS");
            stage.getIcons().add(new javafx.scene.image.Image("/images/home/happy.jpg"));
            Scene scene = new Scene(hBox,400,130);

            stage.setScene(scene);
        } else {
            javafx.scene.image.Image image = new Image("/images/home/R-C.jpg");
            ImageView imageView = new ImageView(image);
            stage.setTitle("WARNING");
            stage.getIcons().add(new javafx.scene.image.Image("/images/home/cry.jpg"));
            HBox hBox = new HBox(10,imageView,label);
            hBox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(hBox,400,130);
            stage.setScene(scene);
        }


        stage.showAndWait();
    }
}

