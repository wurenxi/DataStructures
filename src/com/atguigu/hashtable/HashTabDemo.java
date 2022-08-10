package com.atguigu.hashtable;

import java.util.Scanner;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/10 11:03
 */
public class HashTabDemo {
    public static void main(String[] args) {
        // 创建哈希表
        HashTab hashTab = new HashTab(7);

        // 写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add：添加雇员");
            System.out.println("list：显示雇员");
            System.out.println("find：查找雇员");
            System.out.println("exit：退出系统");

            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入ID");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    // 创建雇员
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的id");
                    id = scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
            }
        }

    }
}

/**
 * 创建HashTab 管理多条链表
 */
class HashTab {
    private EmpLinkedList[] empLinkedListArray;

    private int size; // 表示有多少条链表

    // 构造器
    public HashTab(int size) {
        this.size = size;
        // 初始化empLinkedListArray
        empLinkedListArray = new EmpLinkedList[size];
        // 分别初始化每个链表
        for (int i = 0; i < empLinkedListArray.length; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    // 添加雇员
    public void add(Emp emp) {
        // 根据员工的id，得到该员工应当添加到哪条链表
        int empLinkedListNo = hashFun(emp.id);
        // 将emp添加到对应的链表中
        empLinkedListArray[empLinkedListNo].add(emp);
    }

    // 遍历所有链表，遍历hashtab
    public void list() {
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i);
        }
    }

    // 根据输入的id，查找雇员
    public void findEmpById(int id) {
        // 使用散列函数确定到哪条链表查找
        int empLinkedListNO = hashFun(id);
        Emp emp = empLinkedListArray[empLinkedListNO].findEmpById(id);
        if(emp != null) {
            System.out.println("在第"+(empLinkedListNO+1)+"条链表中找到雇员 id = " + id);
        }else {
            System.out.println("在哈希表中没有找到该雇员");
        }
    }

    // 编写散列函数，使用一个简单取模法
    public int hashFun(int id) {
        return id % size;
    }
}

/**
 * 雇员
 */
class Emp {
    public int id;

    public String name;

    public Emp next; // next默认为空

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }


}

/**
 * 创建EmpLinkedList，表示链表
 */
class EmpLinkedList {

    // 头指针，指向第一个雇员，因此我们这个链表的head是直接指向第一个Emp
    private Emp head; // 默认NULL

    // 添加雇员到链表
    // 说明
    // 1.假定，当添加雇员时，id是自增长，即id的分配总是从小到大
    //  因此我们将该雇员直接加入到本链表的最后即可
    public void add(Emp emp) {
        // 如果是添加第一个雇员
        if(head == null) {
            head = emp;
            return;
        }

        // 如果不是添加第一个雇员，则使用一个辅助的指针，帮助定位到最后
        Emp curEmp = head;
        while (curEmp.next != null) {
            curEmp = curEmp.next; // 后移
        }

        // 直接将emp加入链表
        curEmp.next = emp;
    }

    /**
     * 遍历链表的雇员信息
     */
    public void list(int no) {
        if(head == null) { // 说明链表为空
            System.out.println("第"+(no+1)+"链表为空");
            return;
        }

        System.out.print("第"+(no+1)+"链表的信息为：");
        Emp curEmp = head; // 辅助指针
        while (curEmp != null) {
            System.out.print(" => id="+head.id + " name=" + head.name + "\t");
            curEmp = curEmp.next;
        }
        System.out.println();
    }

    // 根据id查找雇员
    // 如果查找到，就返回emp，如果没有找到，返回null
    public Emp findEmpById(int id) {
        // 判断链表是否为空
        if(head == null) {
            System.out.println("链表空");
            return null;
        }

        // 辅助指针
        Emp curEmp = head;
        // 找到
        // 这是curEmp就指向要查找的雇员
        while (curEmp.id != id) {
            // 退出
            if (curEmp.next == null) { // 说明遍历当前链表没有找到该雇员
                return null;
            }
            curEmp = curEmp.next; // 后移
        }

        return curEmp;
    }
}
