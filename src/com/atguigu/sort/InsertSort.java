package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author gxl
 * @description
 * @createDate 2022/7/31 19:08
 */
public class InsertSort {
    public static void main(String[] args) {
//        int[] arr = {101, 34, 119, 1, -1, 89};

        // 测试冒泡排序的速度 O(n²)，给8w个数据，测试
        // 创建要给8w个随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        long startTime = System.currentTimeMillis();
        insertSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println("---costTime: "+(endTime - startTime)+" 毫秒"); // 755 毫秒 1012 毫秒

//        System.out.println(Arrays.toString(arr));
    }

    /**
     * 插入排序
     * @param arr
     */
    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            // 定义待插入的数
            int insertVal = arr[i];
            int insertIndex = i - 1; // 即arr[i]的前面这个数的下标

            // 给insertVal找到插入的位置
            // 说明
            // 1.insertIndex >= 0保证在给insertVal找插入位置，不越界
            // 2.insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置
            // 3.就需要将arr[insertIndex]后移
            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            if(insertIndex + 1 != i) {
                // 当退出while循环时，说明插入的位置找到，insertIndex + 1
                arr[insertIndex + 1] = insertVal;
            }
        }
    }
}
