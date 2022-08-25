package com.atguigu.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/24 9:19
 */
public class RandomNumGenerator {

    static final int MAX_TIMES = 15;

    static final Set<Integer> set = new HashSet<>(MAX_TIMES);

    public static void main(String[] args) {
        int i = 0;
        while (set.size() < MAX_TIMES) {
            set.add((int)(Math.random() * 100 +1));
        }

        System.out.println(set);
    }
}
