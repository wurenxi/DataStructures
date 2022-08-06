package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author gxl
 * @description 基数排序
 * 解决思路：https://blog.csdn.net/SpringLsL/article/details/120720802
 * 如果有负数，所有数减去最小的负数，然后进行基数排序
 * 排好的数组再加上负数
 * @createDate 2022/8/4 14:39
 */
public class RadixSort {
    public static void main(String[] args) {
//        int[] arr = {53, 3, 542, 748, 14, 234};

        // 80000000 * 11 * 4 / 1024 / 1024 / 1024 = 3.3G
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        long startTime = System.currentTimeMillis();
        // 目前代码不能处理负数的情况
        radixSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println("---costTime: "+(endTime - startTime)+" 毫秒"); // 66 毫秒 79 毫秒

//        System.out.println(Arrays.toString(arr));
    }

    /**
     * 基数排序
     * @param arr
     */
    public static void radixSort(int[] arr) {

        // 1.得到数组中最大的数的位数
        int max = arr[0]; // 假设第一个数就是最大数
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] > max) {
                max = arr[i];
            }
        }
        // 得到最大数是几位数
        int maxLength = String.valueOf(max).length();

        // 定义一个二维数组，表示10个桶，每个桶就是一个一维数组
        // 说明
        // 1.二维数组包含10个一维数组
        // 2.为了防止在放入数的时候，数据溢出，则每个一维数组（桶），大小定为arr.length
        // 3.很明确，基数排序是使用空间换时间的经典算法
        int[][] bucket = new int[10][arr.length];

        // 为了记录每个桶中，实际存放了多少数据，我们定义一个一维数组来记录各个桶的每次放入的数据个数
        // 比如：bucketElementCounts[0]记录的就是bucket[0]桶的放入数据的个数
        int[] bucketElementCounts = new int[10];

        // 这里使用循环将代码处理
        for (int i = 0; i < maxLength; i++) {
            // 第i+1轮（针对每个元素的个位进行排序处理）
            for (int j = 0; j < arr.length; j++) {
                // 取出每个元素的个数
                int digitOfElement = arr[j] / (int) Math.pow(10, i) % 10;
                // 放入到对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }

            // 按照这个桶的顺序（一维数组的下标依次取出数据，放入原来数组）
            int index = 0;
            // 遍历每个桶，并将桶中的数据，放入到原数组
            for(int k=0; k<bucketElementCounts.length; k++) {
                // 如果桶中有数据，我们才放入原数组
                if(bucketElementCounts[k] != 0) {
                    // 循环该桶（第k个桶，即第k个一维数组），放入
                    for(int l = 0; l < bucketElementCounts[k]; l++) {
                        // 取出元素放入到arr中
                        arr[index++] = bucket[k][l];
                    }
                }
                // 第i+1轮处理后，需要将每个bucketElementCounts[k] = 0
                bucketElementCounts[k] = 0;
            }
        }


    }
}
