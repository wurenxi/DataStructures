package com.atguigu.sort;

import java.util.Arrays;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/3 10:08
 */
public class MergeSort {
    public static void main(String[] args) {
//        int[] arr = {8, 4, 5, 7, 1, 3, 6, 2};

        // 创建要给8w个随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }
        int[] tmp = new int[arr.length]; // 归并排序需要一个额外空间

        long startTime = System.currentTimeMillis();
        mergeSort(arr, 0, arr.length - 1, tmp);
        long endTime = System.currentTimeMillis();
        System.out.println("---costTime: "+(endTime - startTime)+" 毫秒"); // 23 毫秒 26 毫秒

//        System.out.println(Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr, int left, int right, int[] tmp) {
        if(left < right) {
            int mid = (left + right) / 2; // 中间索引
            // 向左递归进行分解
            mergeSort(arr, left, mid, tmp);
            // 向右递归进行分解
            mergeSort(arr, mid+1, right, tmp);

            // 到合并
            merge(arr, left, mid, right, tmp);
        }
    }

    /**
     * 合并的方法
     * @param arr 排序的原始数组
     * @param left 左边有序序列的初始索引
     * @param mid 中间索引
     * @param right 右边索引
     * @param tmp 中转（临时）数组
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] tmp) {
        int i = left; // 初始化i，左边有序序列的初始索引
        int j = mid + 1; // 初始化j，右边有序序列的初始索引
        int t = 0; // 指向tmp数组的当前索引

        // （一）
        // 先把左右两边（有序）的数据按照规则填充到tmp数组
        // 直到左右两边的有序序列，有一边处理完毕为止
        while (i <= mid && j <= right) {
            // 如果左边的有序序列的当前元素，小于等于右边有序序列的当前元素
            // 即将左边的当前元素，填充到tmp数组
            // 后移
            if(arr[i] <= arr[j]) {
                tmp[t] = arr[i];
                t += 1;
                i += 1;
            }else { // 反之，将右边有序序列的当前元素，填充到tmp数组
                tmp[t] = arr[j];
                t++;
                j++;
            }
        }

        // （二）
        // 把有剩余数据的一边的数据依次全部填充到tmp去
        while (i <= mid) { // 左边的有序序列还有剩余的元素，就全部填充到tmp
            tmp[t++] = arr[i++];
        }

        while (j <= right) { // 右边的有序序列还有剩余的元素，就全部填充到tmp
            tmp[t++] = arr[j++];
        }

        // （三）
        // 将tmp数组的元素拷贝到arr
        // 注意，并不是每次都拷贝所有
        t = 0;
        int tmpLeft = left;
        // 第一次合并tmpLeft=0, right=1   第二次合并tmpLeft=2, right=3
        // 最后一次tmpLeft = 0 right = 7
        while (tmpLeft <= right) {
            arr[tmpLeft] = tmp[t];
            t++;
            tmpLeft++;
        }
    }
}
