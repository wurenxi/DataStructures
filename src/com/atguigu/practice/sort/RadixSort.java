package com.atguigu.practice.sort;

import java.util.Arrays;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/6 9:24
 */
public class RadixSort {
    public static void main(String[] args) {
//        int[] arr = new int[8];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
//        }

        int[] arr = {-1, 0, 1000, 53, -87, -96, -2, 76, -5, 10001, 11, -9};

        radixSort(arr);

        System.out.println(Arrays.toString(arr));
    }

    public static void radixSort(int[] arr) {

    }
}
