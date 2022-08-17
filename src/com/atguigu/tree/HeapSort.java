package com.atguigu.tree;

import java.util.Arrays;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/17 9:57
 */
public class HeapSort {
    public static void main(String[] args) {
        // 要求将数组进行升序排序
        int[] arr = {4, 6, 8, 5, 9, -1, 90, 89, 56, -99};

        // 创建要给8w个随机的数组
//        int[] arr = new int[8000000];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = (int)(Math.random() * 8000000); // 生成一个[0, 8000000) 数
//        }

//        long startTime = System.currentTimeMillis();
        heapSort(arr);
//        long endTime = System.currentTimeMillis();
//        System.out.println("---costTime: "+(endTime - startTime)+" 毫秒"); // 800w数据 2040 毫秒 2335 毫秒

        System.out.println(Arrays.toString(arr));
    }

    // 编写一个堆排序的方法
    public static void heapSort(int[] arr) {
        int tmp = 0;

        System.out.println("堆排序");

        // 分步完成
//        adjustHeap(arr, 1, arr.length);
//        System.out.println("第一次" + Arrays.toString(arr)); // {4, 9, 8, 5, 6}
//
//        adjustHeap(arr, 0, arr.length);
//        System.out.println("第二次" + Arrays.toString(arr)); // {9, 6, 8, 5, 4}

        // 最终代码
        // 将无需序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
        for (int i = arr.length / 2 - 1; i >= 0 ; i--) {
            adjustHeap(arr, i, arr.length);
        }

        /*
        将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端
        重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序。
         */
        for (int j = arr.length - 1; j > 0; j--) {
            // 交换
            tmp = arr[j];
            arr[j] = arr[0];
            arr[0] = tmp;
            adjustHeap(arr, 0, j);
        }
    }

    /**
     * 将一个数组（二叉树），调整成一个大顶堆
     * 功能：完成将以i对应的非叶子节点的树调整成大顶堆
     * 举例：int[] arr = {4, 6, 8, 5, 9}; => i = 1 => adjustHeap => 得到 {4, 9, 8, 5, 6}
     * 如果我们再次调用 adjustHeap 传入的是 i = 0 =》 得到 {4,9,8,5,6} => {9, 6, 8, 5, 4}
     * @param arr 待调整的数组
     * @param i 表示非叶子节点在数组中的索引
     * @param length 对多少个元素进行调整 length在逐渐的减少
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        int tmp = arr[i]; // 先取出当前元素的值，保存在一个临时变量
        // 开始调整
        // 说明
        // 1.k = i*2+1 k是i节点的左子节点
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            if(k + 1 < length && arr[k] < arr[k + 1]) { // 存在右子节点
                k++; // k指向右子节点
            }
            if(arr[k] > tmp) { // 如果子节点大于父节点
                arr[i] = arr[k]; // 把较大的值赋给当前节点
                i = k; //!!! i 指向 k，继续循环比较
            }else {
                break; //
            }
        }
        // 当for循环结束后，已经将以i为父节点的树最大值放在了最顶
        arr[i] = tmp; // 将tmp值放到调整后的位置
    }
}
