package com.csu.tankbattle.util;

import java.util.Random;

public class RandomNumber
{
    public static int[][] getRandomNumber()
    {
        int[][] array = new int[16][18]; // 创建40x40数组
        Random random = new Random();

        // 定义概率分界点
        double p1 = 0.28;   // 概率1的阈值
        double p2 = p1 + 0.08; // 概率2的阈值
        double p3 = p2 + 0.08; // 概率3的阈值
        double p4 = p3 + 0.06; // 概率4的阈值
        double p0 = 1.0; // 剩下的概率为0

        // 填充数组
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 18; j++) {
                double rand = random.nextDouble(); // 生成一个0到1之间的随机数
                if (rand < p1) {
                    array[i][j] = 1;
                } else if (rand < p2) {
                    array[i][j] = 2;
                } else if (rand < p3) {
                    array[i][j] = 3;
                } else if (rand < p4) {
                    array[i][j] = 4;
                } else {
                    array[i][j] = 0;
                }
            }
        }


        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }

        return array;
    }
}
