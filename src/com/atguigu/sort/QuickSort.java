package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/2 15:08
 */
public class QuickSort {
    public static void main(String[] args) {
//        int[] arr = {-9, 78, 0, 23, -567, 70, -1, 900, 5461};

        // 创建要给8w个随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        long startTime = System.currentTimeMillis();
        quickSort(arr, 0, arr.length-1);
        long endTime = System.currentTimeMillis(); // 32 毫秒 18 毫秒
        System.out.println("---costTime: "+(endTime - startTime)+" 毫秒");

//        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int left, int right) {
        int l = left; // 左下标
        int r = right; // 右下标
        // pivot 中轴值
        int pivot = arr[(right + left) / 2];
        int tmp; // 临时变量，作为交换时使用

        // while循环的目的是让比pivot值小的放到左边
        // 比pivot值大的放到右边
        while (l < r) {
            // 在pivot的左边一直找，找到大于等于pivot值，才退出
            while (arr[l] < pivot) {
                l++;
            }

            // 在pivot的右边一直找，找到小于等于pivot值，才退出
            while (arr[r] > pivot) {
                r--;
            }

            // 如果l >= r说明pivot的左右两边的值，已经按照左边全部是小于等于pivot值
            // 右边全部是大于等于pivot值
            if(l >= r) {
                break;
            }

            // 交换
            tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;

            // 如果交换完后，发现这个arr[l] == pivot值。r--，前移
            if(arr[l] == pivot) {
                r--;
            }

            // 如果交换完后，发现这个arr[r] == pivot值。l++，后移
            if(arr[r] == pivot) {
                l++;
            }
        }

        // 如果 l == r，必须l++，r--，否则栈溢出
//        if(l == r) {
            l++;
            r--;
//        }

        // 向左递归
        if(left < r) {
            quickSort(arr, left, r);
        }

        // 向右递归
        if(right > l) {
            quickSort(arr, l, right);
        }
    }
}
