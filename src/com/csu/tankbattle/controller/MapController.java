package com.csu.tankbattle.controller;

import com.csu.tankbattle.instance.Tank;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MapController
{
    @FXML
    private GridPane gridPane;  // 这是FXML中的GridPane

    @FXML
    private ProgressBar healthBar1;

    @FXML
    private ProgressBar healthBar2;

    @FXML
    private ProgressBar healthBar3;

    @FXML
    private ProgressBar healthBar4;

    private int wallCount = 0;
    private int grassCount = 0;
    private int waterCount = 0;
    private int stoneCount = 0;

    public void initialize()
    {
        healthBar1.setProgress(1);

        healthBar1.setStyle(
                "-fx-accent: #C00000;" +
                "-fx-padding: 0;"
        );

        healthBar2.setProgress(1);

        healthBar2.setStyle(
                "-fx-accent: #C00000;" +
                "-fx-padding: 0;"
        );

        healthBar3.setProgress(1);

        healthBar3.setStyle(
                "-fx-accent: #C00000;" +
                "-fx-padding: 0;"
        );

        healthBar4.setProgress(1);

        healthBar4.setStyle(
                "-fx-accent: #C00000;" +
                "-fx-padding: 0;"
        );
    }

    // 公共的drawMap方法，接受一个二维数组作为参数
    public void drawMap(int[][] map)
    {
        gridPane.getChildren().clear(); // 清除已有内容

        for (int row = 0; row < map.length; row++)
        {
            for (int col = 0; col < map[row].length; col++)
            {
                int value = map[row][col];
                ImageView imageView = null;

                switch (value)
                {
                    case 1:
                        imageView = createImageView("/images/map/Wall.png", "wall" + wallCount);
                        wallCount++;
                        break;
                    case 2:
                        imageView = createImageView("/images/map/Grass.png", "grass" + grassCount);
                        grassCount++;
                        break;
                    case 3:
                        imageView = createImageView("/images/map/Water.png", "water" + waterCount);
                        waterCount++;
                        break;
                    case 4:
                        imageView = createImageView("/images/map/Stone.png", "stone" + stoneCount);
                        stoneCount++;
                        break;
                    case 0:
                        // 不插入ImageView
                        continue;
                }

                // 将imageView放到GridPane中对应的位置
                if (imageView != null)
                {
                    gridPane.add(imageView, col, row);
                }
            }
        }
    }

    // 创建ImageView的方法
    private ImageView createImageView(String imagePath, String imageViewName)
    {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitWidth(48.5);
        imageView.setFitHeight(48.5);
        imageView.setId(imageViewName);
        return imageView;
    }

    public void beAttacked()
    {

    }

    public void deleteItem(int row, int col)
    {
//        System.out.println(row + "--" + col);

        for (Node node : gridPane.getChildren()) {
            // 检查节点是否在目标的行和列
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null &&
                    GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {

                // 确保节点是一个ImageView
                if (node instanceof ImageView) {
                    // 设置新的图片4
                    Image newImage = new Image("/images/map/None.png");
                    ((ImageView) node).setImage(newImage);
                    break;  // 找到后可以退出循环
                }
            }
        }
    }

    public void changeHealth1(Tank tank)
    {
        healthBar1.setProgress(tank.getHealth()/100.0);
    }
    public void changeHealth2(Tank tank)
    {
        healthBar2.setProgress(tank.getHealth()/100.0);
    }
}
