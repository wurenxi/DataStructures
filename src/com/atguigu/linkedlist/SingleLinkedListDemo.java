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
//        singleLinkedList
//                .add(hero1)
//                .add(hero4)
//                .add(hero2)
//                .add(hero3);

        singleLinkedList
                .addByOrder(hero1)
                .addByOrder(hero4)
                .addByOrder(hero2)
                .addByOrder(hero3);

        singleLinkedList.list();

        // 测试修改节点的代码
        HeroNode newHeroNode = new HeroNode(2, "小卢", "玉麒麟~~");
        singleLinkedList.update(newHeroNode);

        // 显示一把
        System.out.println("修改后的链表情况：");
        singleLinkedList.list();

        // 删除一个节点
        // tips: 被删除的节点不会被垃圾回收，比如这里的no=1，有局部变量hero1指向，而且还是强引用
        // 根据可达性分析算法，该节点虽被断开，程序运行期间，该节点也不会被回收
        singleLinkedList.del(1);
        singleLinkedList.del(4);
        System.out.println("删除后的链表情况：");
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

    // 第二种方法在添加英雄时，根据排名将英雄插入到指定位置
    // 如果有这个排名，则添加失败，并给出提示
    public SingleLinkedList addByOrder(HeroNode heroNode) {
        // 因为头节点不能动，因此我们仍然通过一个辅助指针（变量）来帮助找到添加的位置
        // 因为单链表，所以我们找的tmp是位于添加位置的前一个节点，否则插入不了
        HeroNode tmp = head;
        boolean flag = false; // flag标志添加的编号是否存在，默认为false
        while (true) {
            if(tmp.next == null) { // 说明tmp已经在链表的最后
                break;
            }

            if(tmp.next.no > heroNode.no) { // 位置找到了，就在tmp的后面插入
                break;
            }else if(tmp.next.no == heroNode.no) { // 说明希望添加的heroNode的编号已经存在
                flag = true; // 说明编号存在
                break;
            }

            tmp = tmp.next; // 后移，遍历当前链表
        }

        // 判断flag的值
        if(flag) { // 不能添加，说明编号存在
            System.out.printf("准备插入的英雄的编号 %d 已经存在了，不能加入\n", heroNode.no);
        }else{
            // 插入到链表中, tmp的后面
            heroNode.next = tmp.next;
            tmp.next = heroNode;
        }

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

    /**
     * 修改节点的信息，根据no编号来修改，即no编号不能改
     * 说明
     * 1.根据newHeroNode的no来修改即可
     */
    public void update(HeroNode newHeroNode) {
        // 判断是否为空
        if(head.next == null) {
            System.out.println("链表为空");
            return;
        }

        // 找到需要修改的节点，根据no编号
        // 定义一个辅助变量
        HeroNode tmp = head.next;
        boolean flag = false; // 表示是否找到该节点
        while (true) {
            if(tmp == null) {
                break; // 已经遍历完这个链表了
            }

            if(tmp.no == newHeroNode.no) {
                // 找到
                flag = true;
                break;
            }

            tmp = tmp.next;
        }

        // 根据flag判断是否找到要修改的节点
        if(flag) {
            tmp.name = newHeroNode.name;
            tmp.nickname = newHeroNode.nickname;
        }else{ // 没有找到
            System.out.printf("没有找到 编号 %d 的节点，不能修改\n", newHeroNode.no);
        }
    }

    /**
     * 删除节点
     * 思路
     * 1.head不能动，因此我们需要一个tmp辅助节点找到待删除节点的前一个节点
     * 2.说明我们在比较时，是tmp.next.no和需要修改的节点的no比较
     */
    public void del(int no) {
        HeroNode tmp = head;
        boolean flag = false; // 标志是否找到待删除节点的
        while (tmp.next != null) {
            if(tmp.next.no == no) {
                // 找到待删除节点的前一个节点tmp
                flag = true;
                break;
            }

            tmp = tmp.next; // tmp后移，遍历
        }

        // 判断flag
        if(flag) { // 找到
            // 可以删除
            tmp.next = tmp.next.next;
        }else{
            System.out.printf("要删除的 %d 节点不存在", no);
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
