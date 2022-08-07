package com.atguigu.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gxl
 * @description
 * 注意：使用二分查找的前提是该数组是有序的
 * @createDate 2022/8/7 9:53
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1000, 1000, 1234};

//        int resIndex = binarySearch(arr, 0, arr.length, -1);
//        System.out.println("resIndex：" + resIndex);

        List<Integer> resIndexList = binarySearch2(arr, 0, arr.length, 1000);
        System.out.println("resIndexList = " + resIndexList);
    }

    /**
     * 二分查找算法
     * @param arr 数组
     * @param left 左边的索引
     * @param right 右边的索引
     * @param findVal 要查找的值
     * @return 如果找到就返回下标，如果没有找到，就返回-1
     */
    public static int binarySearch(int[] arr, int left, int right, int findVal) {
        if(left > right) {
            return -1;
        }else {
            int mid = (left + right) / 2;
            int midVal = arr[mid];

            if(findVal > midVal) { // 向右递归
                return binarySearch(arr, mid + 1, right, findVal);
            }else if(findVal < midVal) { // 向左递归
                return binarySearch(arr, left, mid - 1, findVal);
            }else{
                return mid;
            }
        }
    }

    /**
     * 完成一个课后思考题
     * {1, 8, 10, 89, 1000, 1000, 1234}
     * 当一个有序数组中有多个相同的数值时，如何将所有的数值都查找到，比如这里的1000
     *
     * 思路分析
     *  1.在找到mid索引值时，不要马上返回
     *  2.向mid索引值的左边扫描，将所有满足1000的元素的下标，加入到集合ArrayList
     *  3.向mid索引值的右边扫描，将所有满足1000的元素的下标，加入到集合ArrayList
     *  4.将ArrayList返回即可
     */
    public static List<Integer> binarySearch2(int[] arr, int left, int right, int findVal) {
        if(left > right) {
            return new ArrayList<Integer>();
        }else {
            int mid = (left + right) / 2;
            int midVal = arr[mid];

            if(findVal > midVal) { // 向右递归
                return binarySearch2(arr, mid + 1, right, findVal);
            }else if(findVal < midVal) { // 向左递归
                return binarySearch2(arr, left, mid - 1, findVal);
            }else{
                List<Integer> resIndexList = new ArrayList<>();
                // 向mid索引值的左边扫描，将所有满足1000的元素的下标，加入到集合ArrayList
                int tmp = mid - 1;
                while (true) {
                    if(tmp < 0 || arr[tmp] != findVal) { // 退出
                        break;
                    }

                    // 否则，就把tmp放入resIndexList
                    resIndexList.add(tmp);
                    tmp--; // tmp左移
                }

                resIndexList.add(mid); //

                tmp = mid + 1;
                while (true) {
                    if(tmp > arr.length - 1 || arr[tmp] != findVal) { // 退出
                        break;
                    }

                    // 否则，就把tmp放入resIndexList
                    resIndexList.add(tmp);
                    tmp++; // tmp左移
                }

                return resIndexList;
            }
        }
    }
}
