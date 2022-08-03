package com.atguigu.practice;

import java.util.Arrays;

/**
 * @author gxl
 * @description 快排练习
 * @createDate 2022/8/3 9:24
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[8];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        quickSort(arr, 0, arr.length-1);

        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int [] arr, int begin, int end) {

    }
}
