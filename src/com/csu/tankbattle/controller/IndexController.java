package com.csu.tankbattle.controller;

import com.csu.tankbattle.Manager;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.media.Media;

public class IndexController
{
    @FXML
    private StackPane stackPane;  // 绑定 FXML 中的 StackPane
    @FXML
    private Pane leftPane;

    @FXML
    private Pane mode1;
    @FXML
    private Pane mode2;
    @FXML
    private Pane mode3;
    @FXML
    private Pane mode4;

    @FXML
    private Slider soundSlider;
    @FXML
    private ImageView sound;

    private MediaPlayer mediaPlayer;
    private boolean isMuted = true; // 用于跟踪音量是否关闭

    @FXML
    private ImageView deployment1;
    @FXML
    private ImageView deployment2;
    @FXML
    private ImageView deployment3;

    private int currentModeIndex = 0;  // 当前显示的模式索引


    @FXML
    public void initialize()
    {
        // 初始化时，确保显示第一个模式
        stackPane.getChildren().get(0).setVisible(true);

        // 隐藏其他模式
        for (int i = 1; i < stackPane.getChildren().size(); i++) {
            stackPane.getChildren().get(i).setVisible(false);
        }

        // 背景音乐设置
        // 加载本地音频文件
        String audioFilePath = getClass().getResource("/sound/music/bgm2.mp3").toExternalForm();
        Media media = new Media(audioFilePath);
        mediaPlayer = new MediaPlayer(media);

        // 为 ImageView 添加点击事件监听器
        sound.setOnMouseClicked(event -> toggleMute());

        // 监听 Slider 的值变化事件，调整 MediaPlayer 的音量
        soundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!isMuted) {
                mediaPlayer.setVolume(newValue.doubleValue() / 100); // 将 Slider 的值转换为 0 到 1 之间的浮点数
            }
        });

        // 设置初始音量
        mediaPlayer.setVolume(soundSlider.getValue() / 100);

        // 开始播放音频
        mediaPlayer.play();
    }

    @FXML
    void ChangeMode(MouseEvent event)
    {
        // 获取当前模式和下一个模式
        Pane currentPane = (Pane) stackPane.getChildren().get(currentModeIndex);
        int nextModeIndex = (currentModeIndex + 1) % stackPane.getChildren().size();
        Pane nextPane = (Pane) stackPane.getChildren().get(nextModeIndex);

        // 设置下一个模式的位置在右侧不可见区域
        nextPane.setTranslateX(stackPane.getWidth());
        nextPane.setVisible(true);

        // 确保最左边的Pane在最上层，避免被遮挡
        leftPane.toFront();

        // 创建当前页面滑出动画（向左滑出）
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(500), currentPane);
        slideOut.setToX(-stackPane.getWidth());

        // 创建下一个页面滑入动画（从右侧滑入）
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), nextPane);
        slideIn.setToX(0);  // 移动到原始位置

        // 在当前页面滑出完成后，隐藏它
        slideOut.setOnFinished(finishEvent ->
        {
            currentPane.setVisible(false);
            currentPane.setTranslateX(0); // 重置位置
        });

        // 开始动画
        slideOut.play();
        slideIn.play();

        // 更新当前模式索引
        currentModeIndex = nextModeIndex;
    }

    @FXML
    void StartEnterd(MouseEvent event)
    {
        ((Node) event.getSource()).setCursor(Cursor.HAND);
        Node source = (Node) event.getSource();
        source.setOpacity(0.8);
    }

    @FXML
    void StartExited(MouseEvent event)
    {
        ((Node) event.getSource()).setCursor(Cursor.DEFAULT);
        Node source = (Node) event.getSource();
        source.setOpacity(1);
    }


    @FXML
    void startClicked(MouseEvent event) {
        for (int i = 0; i < 4; i++) {
            if(stackPane.getChildren().get(i).isVisible())
                Manager.getInstance().IntoGame(i+1);
        }
    }

    @FXML
    void soundClicked(MouseEvent event)
    {
        toggleMute();
    }

    // 切换静音状态
    private void toggleMute()
    {
        // 音量图标文件路径
        String playIconPath = getClass().getResource("/images/index/SoundOn.png").toExternalForm();
        String muteIconPath = getClass().getResource("/images/index/SoundOff.png").toExternalForm();

        isMuted = !isMuted; // 切换静音状态

        if (isMuted) {
            // 如果静音，将音量设置为0，并更改图标
            mediaPlayer.setVolume(0);
            sound.setImage(new Image(muteIconPath));
        } else {
            // 如果取消静音，恢复音量，并更改图标
            mediaPlayer.setVolume(soundSlider.getValue() / 100);
            sound.setImage(new Image(playIconPath));
        }
    }
}

