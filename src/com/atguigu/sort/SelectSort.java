package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author gxl
 * @description 简单选择排序
 * @createDate 2022/7/30 10:30
 */
public class SelectSort {
    public static void main(String[] args) {

//        int[] arr = {101,34,119,1,-1,90,123};

        // 创建要给8w个随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

//        System.out.println("排序前数组：" + Arrays.toString(arr));

        long startTime = System.currentTimeMillis();
        // 选择排序时间复杂度是O(n²)
        selectSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println("---costTime: "+(endTime - startTime)+" 毫秒"); // ---costTime: 5563 毫秒 3666 毫秒

//        System.out.println("排序后数组：" + Arrays.toString(arr));
    }

    private static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];

            for (int j = i + 1; j < arr.length; j++) {
                if(min > arr[j]) {
                    min = arr[j];
                    minIndex = j;
                }
            }

            if(minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
    }
}
