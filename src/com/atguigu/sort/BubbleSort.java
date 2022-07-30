package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author gxl
 * @description
 * @createDate 2022/7/29 10:27
 */
public class BubbleSort {
    public static void main(String[] args) {
//        int[] arr = {3, 9, -1, 10, 20};
//        int[] arr = {-1, 3, 9, 10, 20};

        // 测试冒泡排序的速度 O(n²)，给8w个数据，测试
        // 创建要给8w个随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        long startTime = System.currentTimeMillis();
        // 测试冒泡排序花费时间
        bubbleSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println("---costTime: "+(endTime - startTime)+" 毫秒"); // 11051 毫秒
    }

    // 将冒泡排序算法，封装成一个方法
    public static void bubbleSort(int[] arr) {
        int temp = 0; // 临时变量
        boolean flag = false; // 标识变量，表示是否进行过交换
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 如果前面的数比后面的大，则交换
                if(arr[j] > arr[j+1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            if(!flag) { // 在一趟排序中，一次交换都没有发生过
                break;
            }else {
                flag = false; // 重置flag，进行下次判断
            }

//            System.out.println("第"+(i+1)+"趟排序后的数组");
//            System.out.println(Arrays.toString(arr));
        }

//        System.out.println("排序后的数组：" + Arrays.toString(arr));
    }
}
