package com.atguigu.practice;

import java.util.Arrays;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/1 12:19
 */
public class ShellSort {
    public static void main(String[] args) {
//        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};

        int[] arr = new int[8];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        shellSort(arr);

        System.out.println(Arrays.toString(arr));
    }

    public static void shellSort(int[] arr) {
        for(int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int tmpIndex = i;
                int tmpValue = arr[i];

                while (tmpIndex - gap >= 0 && arr[tmpIndex - gap] > tmpValue) {
                    arr[tmpIndex] = arr[tmpIndex - gap];
                    arr[tmpIndex - gap] = tmpValue;
                    tmpIndex -= gap;
                }

                arr[tmpIndex] = tmpValue;
            }
        }
    }
}
