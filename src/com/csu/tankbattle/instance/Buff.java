package com.csu.tankbattle.instance;

import com.csu.tankbattle.model.UnMoveModel;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Buff extends UnMoveModel {
    public int num=1;

    public Buff(int type, int x, int y)
    {
        super();
        switch(type)
        {
            case 5:
                super.setImage(new Image("/images/game/healthBuff.png"));
                super.setX(y * 49 + 183);
                super.setY(x * 49);
                System.out.println("血包" + x + " " + y);
                break;
            case 6:
                super.setImage(new Image("/images/game/speedBuff.png"));
                super.setX(y * 49 + 183);
                super.setY(x * 49);
                System.out.println("减速" + x + " " + y);
                break;
            case 7:
                super.setImage(new Image("/images/game/positionBuff.gif"));
                super.setX(y * 49 + 183);
                super.setY(x * 49);
                System.out.println("传送" + x + " " + y);
                break;
            case 8:
                super.setImage(new Image("/images/game/moveDeBuff.png"));
                super.setX(y * 49+ 183);
                super.setY(x *49);
                System.out.println("停" + x + " " + y);
                break;
        }
    }

}
