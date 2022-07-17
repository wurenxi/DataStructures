package com.atguigu.linkedlist;

/**
 * @author gxl
 * @description
 * @createDate 2022/7/17 10:10
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        // 进行测试
        // 先创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "公孙胜", "入云龙");

        // 创建一个链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList
                .add(hero1)
                .add(hero4)
                .add(hero2)
                .add(hero3);

        // 显示一把
        singleLinkedList.list();
    }
}

/**
 * 定义SingleLinkedList 管理英雄
 */
class SingleLinkedList {
    // 先初始化一个头节点，头节点不用动，不存放具体的数据
    private HeroNode head = new HeroNode(0, "", "");

    // 添加节点到单向链表
    // 思路：当不考虑编号的顺序时
    // 1.找到当前链表的最后节点
    // 2.将最后这个节点的next指向新的节点
    public SingleLinkedList add(HeroNode heroNode) {
        // 因为head节点不能动，因此我们需要一个辅助变量 tmp
        HeroNode tmp = head;
        // 遍历链表，找到最后
        while (true) {
            // 找到链表的最后
            if(tmp.next == null) {

                break;
            }

            // 如果没有找到最后，将tmp后移
            tmp = tmp.next;
        }

        // 当退出while循环时，tmp指向了链表的最后
        // 将最后这个节点的next指向新的节点
        tmp.next = heroNode;

        return this;
    }

    // 显示链表[遍历]
    public void list() {
        // 判断链表是否为空
        if(head.next == null) {
            System.out.println("链表为空");
            return;
        }

        // 因为头节点不能动，因此需要一个辅助变量来遍历
        HeroNode tmp = head.next;
        // 判断是否到链表最后
        while (tmp != null) {
            // 输出节点信息
            System.out.println(tmp);
            // 将tmp后移，一定小心
            tmp = tmp.next;
        }
    }
}

/**
 * 定义HeroNode，每个HeroNode对象就是一个节点
 */
class HeroNode {
    public int no;

    public String name;

    public String nickname;

    public HeroNode next; // 指向下一个节点

    // 构造器
    public HeroNode(int hNo, String hName, String hNickname) {
        this.no = hNo;
        this.name = hName;
        this.nickname = hNickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
