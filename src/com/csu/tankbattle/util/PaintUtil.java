package com.csu.tankbattle.util;

import com.csu.tankbattle.instance.Tank;
import com.csu.tankbattle.model.MoveModel;
import com.csu.tankbattle.model.UnMoveModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class PaintUtil {
    private Canvas canvas;
    private GraphicsContext context;

    public PaintUtil() {
        canvas = new Canvas(1060,780);
        context = canvas.getGraphicsContext2D();
    }

    public void paint(MoveModel moveModel) {
        // 计算旋转中心
        double centerX = moveModel.getX() + moveModel.getImage().getWidth() / 2;
        double centerY = moveModel.getY() + moveModel.getImage().getHeight() / 2;

        // 将原点移动到旋转中心
        context.save(); // 保存当前状态
        context.translate(centerX, centerY);
        context.rotate(moveModel.getAngle());
        context.drawImage(moveModel.getImage(), -moveModel.getImage().getWidth() / 2, -moveModel.getImage().getHeight() / 2);
        context.restore(); // 恢复到之前保存的状态

    }

    // 新增的 paint 方法，接受一个 Tank 对象和一个 Pane 对象
    public void paint(Tank tank, Pane pane) {
        Image image = tank.getImage();
        double x = tank.getX();
        double y = tank.getY();
        pane.getChildren().add(new javafx.scene.image.ImageView(image));
        pane.getChildren().get(pane.getChildren().size() - 1).setLayoutX(x);
        pane.getChildren().get(pane.getChildren().size() - 1).setLayoutY(y);
    }
    public void paint(UnMoveModel moveModel) {
        // 计算旋转中心
        double centerX = moveModel.getX() + moveModel.getImage().getWidth() / 2;
        double centerY = moveModel.getY() + moveModel.getImage().getHeight() / 2;

        // 将原点移动到旋转中心
        context.save(); // 保存当前状态
        context.translate(centerX, centerY);
        context.drawImage(moveModel.getImage(), -moveModel.getImage().getWidth() / 2, -moveModel.getImage().getHeight() / 2);
        context.restore(); // 恢复到之前保存的状态
    }

    public void clear() {
        context.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    public Pane getPane() {
        clear();
        return new Pane(canvas);
    }

    public void render() {}
}
