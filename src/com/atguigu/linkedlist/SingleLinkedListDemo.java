package com.atguigu.linkedlist;

import java.util.Stack;

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
        HeroNode hero3 = new HeroNode(6, "林冲", "豹子头");
        HeroNode hero4 = new HeroNode(4, "公孙胜", "入云龙");

        // 创建一个链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        SingleLinkedList singleLinkedList2 = new SingleLinkedList();


//        singleLinkedList
//                .add(hero1)
//                .add(hero4)
//                .add(hero2)
//                .add(hero3);
//
//        // 测试一下单链表的反转功能
//        System.out.println("原来链表的情况：");
//        singleLinkedList.list();
////        singleLinkedList.reverseList();
////        System.out.println("反转单链表：");
////        singleLinkedList.list();
//
//        // 测试逆序打印单链表
//        System.out.println("测试逆序打印单链表(没有改变原来链表):");
//        singleLinkedList.reversePrint();

        singleLinkedList
                .addByOrder(hero4)
                .addByOrder(hero1)
                .addByOrder(hero2)
                .addByOrder(hero3);

        singleLinkedList2
                .add(new HeroNode(3, "吴用", "智多星"))
                .add(new HeroNode(5, "关胜", "大刀"))
                .add(new HeroNode(7, "秦明", "霹雳火"))
                .add(new HeroNode(8, "呼延灼", "双鞭"));

        // 测试两个有序单链表合并
        singleLinkedList.list();
        System.out.println("=======================");
        singleLinkedList2.list();
        System.out.println("-----------------------");
        singleLinkedList.combineOtherLinkedList(singleLinkedList2);
        singleLinkedList.list();
//
//        // 测试修改节点的代码
//        HeroNode newHeroNode = new HeroNode(2, "小卢", "玉麒麟~~");
//        singleLinkedList.update(newHeroNode);
//
//        // 显示一把
//        System.out.println("修改后的链表情况：");
//        singleLinkedList.list();
//
//        // 删除一个节点
//        // tips: 被删除的节点不会被垃圾回收，比如这里的no=1，有局部变量hero1指向，而且还是强引用
//        // 根据可达性分析算法，该节点虽被断开，程序运行期间，该节点也不会被回收
//        singleLinkedList.del(1);
//        singleLinkedList.del(4);
//        System.out.println("删除后的链表情况：");
//        singleLinkedList.list();
//
//        // 测试单链表中有效节点的个数
//        System.out.println("有效的节点个数："+singleLinkedList.getLength()); // 2
//
//        // 测试是否得到了倒数第K个节点
//        HeroNode res = singleLinkedList.findLastIndexNode(1);
//        System.out.println("res=" + res);
    }
}

/**
 * 定义SingleLinkedList 管理英雄
 */
class SingleLinkedList {
    // 先初始化一个头节点，头节点不用动，不存放具体的数据
    private HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
        return head;
    }

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

    /**
     * 方法：获取到单链表的节点的个数（如果是带头节点的链表，需求不统计头节点）
     * head 链表的头节点
     * @return 返回的就是有效节点的个数
     */
    public int getLength() {
        if(head.next == null) { // 空链表
            return 0;
        }

        int length = 0;
        // 定义一个辅助的变量，这里没有统计头节点
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next;
        }

        return length;
    }

    /**
     * 查找单链表中的倒数第k个节点【新浪面试题】
     * 思路：
     * 1.编写一个方法，接收一个index
     * 2.index 表示是倒数第index个节点
     * 3.先把链表从头到尾遍历一遍，得到链表的总的长度 getLength
     * 4.得到size后，我们从链表的第一个开始遍历（size-index）个，就可以得到
     * 5.如果找到了，返回该节点，否则返回空
     */
    public HeroNode findLastIndexNode(int index) {
        // 判断如果链表为空，返回null
        if(head.next == null) {
            return null;
        }

        // 第一次遍历得到链表的长度（节点个数）
        int size = getLength();
        // 第二次遍历 size-index 位置，就是我们倒数的第K个节点
        // 先做一个index的校验
        if(index <= 0 || index > size) {
            return null;
        }

        // 定义辅助变量，for循环定位到倒数的index
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }

        return cur;
    }

    /**
     * 将单链表反转
     */
    public void reverseList() {
        // 如果当前链表为空，或只有一个节点，无需反转，直接返回
        if(head.next == null || head.next.next == null) {
            return;
        }

        // 定义一个辅助的指针（变量），帮助我们遍历原来的链表
        HeroNode cur = head.next;
        HeroNode next = null; // 指向当前节点[cur]的下一个节点
        HeroNode reverseHead = new HeroNode(0, "", "");
        // 遍历原来的链表，并每遍历一个节点，将其取出，并放在新的链表reverseHead的最前端
        while (cur != null) {
            next = cur.next; // 先暂时保存当前节点的下一个节点，因为后面需要使用
            cur.next = reverseHead.next; // 将cur的一下个节点指向新的链表的最前端
            reverseHead.next = cur; // 将cur 连接到新的链表上
            cur = next; // 让 cur 后移
        }
        // 将head.next 指向 reverseHead.next，实现单链表的反转
        head.next = reverseHead.next;
    }

    /**
     * 利用栈这个数据结构，将各个节点压入到栈中，然后利用栈的先进后出的特点，实现逆序打印的效果
     */
    public void reversePrint() {
        if(head.next == null) {
            return; // 空链表，无法打印
        }

        // 创建要给一个栈，将各个节点压入栈
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        // 将链表中所有节点压入栈
        while (cur != null) {
            stack.push(cur);
            cur = cur.next; // cur后移，这样就可以压入下一个节点
        }
        // 将栈中节点进行打印，pop出栈
        while (stack.size() > 0) {
            System.out.println(stack.pop()); // stack的特点是先进后出
        }
    }

    /**
     * 合并两个有序的单链表，合并之后的链表仍然有序
     */
    public void combineOtherLinkedList(SingleLinkedList otherLinkedList) {
        if(head.next == null && otherLinkedList.head.next == null) {
            throw new RuntimeException("请提供任意一个非空单链表");
        }
        if(head.next == null) {
            head = otherLinkedList.head;
            return;
        }
        if(otherLinkedList.head.next == null) {
            return;
        }

        HeroNode needInsertNode = otherLinkedList.head.next;
        HeroNode tmp = needInsertNode.next;

        while (true) {

            HeroNode cur = head;
            while (true) {
                if(cur.next == null) {
                    cur.next = needInsertNode;
                    break;
                }

                if(cur.next.no > needInsertNode.no) {
                    needInsertNode.next = cur.next;
                    cur.next = needInsertNode;
                    break;
                }else if(cur.next.no == needInsertNode.no) {
                    throw new RuntimeException("插入ID重复，插入失败");
                }

                cur = cur.next;
            }

            needInsertNode = tmp;
            tmp = tmp.next;

            if(tmp == null) {
                break;
            }
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
