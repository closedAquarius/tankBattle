package com.csu.tankbattle.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundUtil {
    /**
     * ��Ч���ţ��Զ�ɾ��.
     * @param soundEffectFile ��Ч��url
     */
    public static void playSoundEffect(String soundEffectFile) {

        Media sound = new Media(SoundUtil.class.getResource(soundEffectFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        // ����Ч������Ϻ��Զ�������Դ
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.dispose());

        // ������Ч
        mediaPlayer.play();
    }

    /**
     * �������ֲ���
     * @param bgmEffectFile ����ı������ֵ�ַ
     * @param mediaPlayer �������ֲ�����
     */
    public static void playBgm(String bgmEffectFile, MediaPlayer mediaPlayer){

        Media bgm = new Media(SoundUtil.class.getResource(bgmEffectFile).toExternalForm());
        mediaPlayer = new MediaPlayer(bgm);

        //�����ֲ�����Ϻ�ѭ������
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        //��������
        mediaPlayer.play();
    }

    /**
     * ֹͣ������
     * @param mediaPlayer �������ֲ�����
     */
    public static void stopBgm(MediaPlayer mediaPlayer){
        mediaPlayer.stop();
        mediaPlayer.dispose();
        mediaPlayer = null;
    }


}
