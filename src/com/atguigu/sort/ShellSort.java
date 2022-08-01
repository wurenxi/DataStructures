package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/1 9:25
 */
public class ShellSort {
    public static void main(String[] args) {
//        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};

        // 创建要给8w个随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        long startTime = System.currentTimeMillis();
//        shellSort(arr); // 12568 毫秒 9354 毫秒
        shellSort2(arr); // 16 毫秒 19 毫秒
        long endTime = System.currentTimeMillis();
        System.out.println("---costTime: "+(endTime - startTime)+" 毫秒");

//        System.out.println(Arrays.toString(arr));

    }

    // 希尔排序时，对有序序列在插入时采用交换法
    public static void shellSort(int[] arr) {

        int tmp = 0;
        // 使用循环处理
        for(int gap = arr.length / 2; gap > 0; gap /= 2) { // 分组
            for(int i = gap; i < arr.length; i++) {
                // 遍历各组中所有的元素（共gap组），步长gap
                for(int j = i - gap; j >= 0; j -= gap) {
                    // 如果当前元素大于加上步长后的那个元素，交换
                    if(arr[j] > arr[j + gap]) {
                        tmp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = tmp;
                    }
                }
            }
        }
    }

    // 对交换式的希尔排序进行优化 -> 移位法
    public static void shellSort2(int[] arr) {
        // 增量gap，并逐步的缩小增量
        for(int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 从第gap个元素
            for (int i = gap; i < arr.length ; i++) {
                int j = i;
                int tmp = arr[j];
//                if(arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && tmp < arr[j - gap]) {
                        // 移动
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    // 当退出while后，tmp找到了插入的位置
                    arr[j] = tmp;
//                }
            }
        }
    }
}
