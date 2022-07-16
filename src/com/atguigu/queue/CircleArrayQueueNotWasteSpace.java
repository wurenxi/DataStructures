package com.atguigu.queue;

import java.util.Scanner;

/**
 * @author gxl
 * @description
 * @createDate 2022/7/16 11:31
 */
public class CircleArrayQueueNotWasteSpace {
    public static void main(String[] args) {
        System.out.println("测试数组模拟不浪费空间的环形队列的案例");
        // 创建一个环形队列
        CircleArrayNotWasteSpace queue = new CircleArrayNotWasteSpace(4); // 设置4，其队列的最大有效数字为3
        char key = ' '; // 接收用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        // 输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0); // 接收一个字符
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("输入一个数字");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g': // 取出数据
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':   // 查看队列头的数据
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':   // 退出
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出");
    }
}

class CircleArrayNotWasteSpace {

    private int maxSize; // 表示数组的最大容量

    private int front; // 队列头

    private int rear; // 队列尾

    private int flag; // 指向rear指针的前一个位置，即刚添加的数据

    private int[] arr; // 该数组用于存放数据，模拟队列

    public CircleArrayNotWasteSpace(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
    }

    // 判断队列是否满
    public boolean isFull() {
        return rear == front && flag != rear;
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return rear == front && rear == flag;
    }

    // 添加数据到队列
    public void addQueue(int n) {
        // 判断队列是否满
        if(isFull()) {
            System.out.println("队列满，不能加入数据");
            return;
        }

        // 直接将数据加入
        arr[rear] = n;
        // 将rear后移，这里必须考虑取模
        flag = rear;
        rear = (rear + 1) % maxSize;
    }

    // 获取队列的数据，数据出队列
    public int getQueue() {
        // 判断队列是否空
        if(isEmpty()) {
            // 通过抛出异常
            throw new RuntimeException("队列空，不能取数据");
        }

        // 这里需要分析出 front是指向队列的第一个元素
        // 1.先把front 对应的值保存到一个临时变量
        // 2.将front后移
        // 3.将临时保存的变量返回
        int value = arr[front];
        if(front == flag) { // 取到最后一个值了，最终front==flag==rear
            front = (front + 1) % maxSize;
            flag = (flag + 1) % maxSize;
        }else{
            front = (front + 1) % maxSize;
        }
        return value;
    }

    public void showQueue() {
        // 遍历
        if(isEmpty()) {
            System.out.println("队列空的，没有数据");
            return;
        }

        // 思路：从front开始遍历，遍历多少个元素
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    public int size() {
        if((flag+1)%maxSize == front) {
            return maxSize;
        }else {
            return (flag + maxSize - front + 1) % maxSize;
        }
    }

    public int headQueue() {
        // 判断
        if(isEmpty()) {
            throw new RuntimeException("队列空的，没有数据");
        }

        return arr[front];
    }
}
