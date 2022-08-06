package com.atguigu.practice;

import java.util.Arrays;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/4 10:23
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = new int[8];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        int[] tmp = new int[arr.length];

        System.out.println(Arrays.toString(arr));
    }
}
