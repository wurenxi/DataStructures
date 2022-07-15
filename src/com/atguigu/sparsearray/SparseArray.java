package com.atguigu.sparsearray;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gxl
 * @description
 * @createDate 2022/7/15 9:11
 */
public class SparseArray {
    public static void main(String[] args) throws Exception {
        // 创建一个原始的二维数组 11 * 11
        // 0: 表示没有棋子，1: 表示黑子，2: 表示蓝子
        int[][] chessArr1 = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;
        // 输出原始的二维数组
        System.out.println("原始的二维数组：");
        for (int[] row : chessArr1) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        // 将二维数组 转稀疏数组的思想
        // 1.先遍历二维数组，得到非0数据的个数
        int sum = 0;
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if(chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }
//        System.out.println(sum);

        // 2.创建对应的稀疏数组
        int[][] sparseArr = new int[sum+1][3];
        // 给稀疏数组赋值
        sparseArr[0][0] = chessArr1.length;
        sparseArr[0][1] = chessArr1[0].length;
        sparseArr[0][2] = sum;

        // 遍历二维数组，将非0的值存放到 sparseArr稀疏数组中
        int count = 0;  // count 用来记录是第几个非0数据
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if(chessArr1[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                }
            }
        }
        
        // 输出稀疏数组的形式
        System.out.println();
        System.out.println("得到稀疏数组为：");
        for (int[] row : sparseArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        /*
        课后练习：
        1.将稀疏数组保存到磁盘上，比如 map.data
        2.恢复原来的数组时，读取map.data进行恢复
         */
        // 1.将稀疏数组保存到磁盘上，比如 map.data
        FileOutputStream fos = new FileOutputStream("./map.data");
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        for (int i = 0; i < sparseArr.length; i++) {
            for (int j = 0; j < sparseArr[i].length; j++) {
                if(i == sparseArr.length-1 && j == sparseArr[i].length -1) {
                    osw.write(String.valueOf(sparseArr[i][j]));
                }else{
                    osw.write(sparseArr[i][j]+",");
                }
            }
        }

        // 流用完一定要关闭，关闭前会将内存中数据刷到磁盘中
        osw.close();
        fos.close();

        // 2.恢复原来的数组时，读取map.data进行恢复
        FileInputStream fis = new FileInputStream("./map.data");
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        int len = 0;
        char[] buff = new char[1024];
        StringBuilder stringData = new StringBuilder();
        while ((len = isr.read(buff)) != -1) {
            stringData.append(new String(buff, 0, len));
        }

        // 流用完一定要关闭
        isr.close();
        fis.close();

        String[] stringDataArray = stringData.toString().split(",");
        int[][] sparseArr2 = new int[stringDataArray.length/3][3];
        count = 0;
        for (int i = 0; i < sparseArr2.length; i++) {
            for (int j = 0; j < sparseArr2[i].length; j++) {
                sparseArr2[i][j] = Integer.parseInt(stringDataArray[count++]);
            }
        }

        // 将稀疏数组 --> 恢复成 原始的二维数组
        /*
        1.先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        2.再读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可。
         */

        // 1.先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
//        int[][] chessArr2 = new int[sparseArr[0][0]][sparseArr[0][1]];
        int[][] chessArr2 = new int[sparseArr2[0][0]][sparseArr[0][1]];

        // 2.再读取稀疏数组后几行的数据（从第二行开始），并赋给 原始的二维数组 即可
        for (int i = 1; i < sparseArr.length; i++) {
            chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        // 输出恢复后的二维数组
        System.out.println();
        System.out.println("恢复后的二维数组");

        for (int[] row : chessArr2) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }
}
