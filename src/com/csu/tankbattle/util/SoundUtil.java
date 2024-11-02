package com.csu.tankbattle.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundUtil {
    /**
     * 音效播放，自动删除.
     * @param soundEffectFile 音效的url
     */
    public static void playSoundEffect(String soundEffectFile) {

        Media sound = new Media(SoundUtil.class.getResource(soundEffectFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        // 当音效播放完毕后自动清理资源
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.dispose());

        // 播放音效
        mediaPlayer.play();
    }

    /**
     * 背景音乐播放
     * @param bgmEffectFile 传入的背景音乐地址
     * @param mediaPlayer 背景音乐播放器
     */
    public static void playBgm(String bgmEffectFile, MediaPlayer mediaPlayer){

        Media bgm = new Media(SoundUtil.class.getResource(bgmEffectFile).toExternalForm());
        mediaPlayer = new MediaPlayer(bgm);

        //当音乐播放完毕后循环播放
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        //播放音乐
        mediaPlayer.play();
    }

    /**
     * 停止并清理
     * @param mediaPlayer 背景音乐播放器
     */
    public static void stopBgm(MediaPlayer mediaPlayer){
        mediaPlayer.stop();
        mediaPlayer.dispose();
        mediaPlayer = null;
    }


}
