package com.atguigu.stack;

import java.util.Stack;

/**
 * @author gxl
 * @description 链表实现栈
 * @createDate 2022/7/23 15:39
 */
public class LinkedListStackDemo {
    public static void main(String[] args) {
        LinkedListStack stack = new LinkedListStack();

        stack.push(1);
        stack.push(2);

        try {
            System.out.println("出栈数据："+stack.pop());
            System.out.println("出栈数据："+stack.pop());
            System.out.println("出栈数据："+stack.pop());
        } catch (Exception e) {
            e.printStackTrace();
        }

        stack.list();
    }
}

/**
 * 定义一个LinkedListStack 单向链表表示栈
 */
class LinkedListStack {

    // 定义头节点
    private StackNode head = new StackNode(0);

    // 判空
    public boolean isEmpty() {
        return head.getNext() == null;
    }

    // 入栈
    public void push(int value) {
        // 定义辅助节点
        StackNode current = head;

        while (true) {
            if(current.getNext() == null) {
                StackNode node = new StackNode(value);
                current.setNext(node);
                break;
            }

            current = current.getNext();
        }
    }

    // 出栈
    public int pop() {

        if(isEmpty()) {
            throw new RuntimeException("栈空");
        }

        // 定义辅助节点
        StackNode current = head.getNext();
        // 定义辅助节点，始终指向current的前一个节点
        StackNode top = head;

        while (true) {
            if(current.getNext() == null) { // current为栈顶，执行出栈
                int value = current.getData();
                top.setNext(null);
                return value;
            }

            current = current.getNext();
            top = top.getNext();
        }
    }

    // 遍历所有数据
    public void list() {
        if(isEmpty()) {
            System.out.println("栈空");
            return;
        }

        StackNode current = head.getNext();
        Stack<StackNode> stack = new Stack<>();

        while (current != null) {

            stack.push(current);
            current = current.getNext();
        }

        while (!stack.isEmpty()) {
            System.out.println("数据："+stack.pop().getData());
        }
    }
}

/**
 * 定义栈节点
 */
class StackNode {

    private StackNode next;

    private int data;

    public StackNode(int data) {
        this.data = data;
    }

    public StackNode getNext() {
        return next;
    }

    public void setNext(StackNode next) {
        this.next = next;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StackNode{" +
                "next=" + next +
                ", data=" + data +
                '}';
    }
}
