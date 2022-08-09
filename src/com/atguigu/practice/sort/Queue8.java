package com.atguigu.practice.sort;

/**
 * @author gxl
 * @description
 * @createDate 2022/7/27 20:23
 */
public class Queue8 {

    int max = 8;

    int[] array = new int[max];

    static int num = 0;

    public static void main(String[] args) {
        Queue8 queue8 = new Queue8();
        queue8.check(0);
        System.out.println("总共有"+num+"解法");
    }

    public void check(int n) {
        if(n == 8) {
            print();
            return;
        }

        for (int i = 0; i < max; i++) {
            array[n] = i;
            if(judge(n)) {
                check(n + 1);
            }
        }
    }


    public boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            if(array[n] == array[i] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }

        return true;
    }

    public void print() {
        num++;
        for (int element : array) {
            System.out.print(element + " ");
        }

        System.out.println();
    }
}
