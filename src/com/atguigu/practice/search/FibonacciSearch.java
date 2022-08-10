package com.atguigu.practice.search;

import java.util.Arrays;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/10 9:33
 */
public class FibonacciSearch {

    public static final int MAX_SIZE = 20;

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};

        System.out.println("index = " + fibonacciSearch(arr, 1234555));
    }

    public static int[] fib() {
        int[] f = new int[MAX_SIZE];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < f.length; i++) {
            f[i] = f[i-1] + f[i-2];
        }

        return f;
    }

    public static int fibonacciSearch(int[] arr, int findVal) {
        return -1;
    }
}
