package com.atguigu.recursion;

/**
 * @author gxl
 * @description
 * @createDate 2022/7/26 11:23
 */
public class RecursionTest {
    public static void main(String[] args) {
        // 通过打印问题，回顾递归调用机制
//        test(4);

        System.out.println("res = " + factorial(2));
    }

    // 打印问题
    public static void test(int n) {
        if(n > 2) {
            test(n - 1);
        }
        System.out.println("n=" + n);
    }

    // 阶乘问题
    public static int factorial(int n) {
        if(n == 1) {
            return 1;
        }else {
            return factorial(n - 1) * n;
        }
    }
}
