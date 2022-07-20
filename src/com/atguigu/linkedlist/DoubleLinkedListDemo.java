package com.atguigu.linkedlist;

/**
 * @author gxl
 * @description
 * @createDate 2022/7/20 9:43
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        // 测试
        System.out.println("双向链表的测试");
        // 先创建节点
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(6, "林冲", "豹子头");
        HeroNode2 hero4 = new HeroNode2(4, "公孙胜", "入云龙");
        // 创建一个双向链表对象
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
//        doubleLinkedList.add(hero1)
//                .add(hero2)
//                .add(hero3)
//                .add(hero4);

        doubleLinkedList.addByOrder(hero1)
                        .addByOrder(hero2)
                        .addByOrder(hero3)
                        .addByOrder(hero4);

        doubleLinkedList.list();

        // 修改
//        HeroNode2 newHeroNode = new HeroNode2(4, "林冲", "豹子头");
//        doubleLinkedList.update(newHeroNode);
//        System.out.println("修改后的链表情况：");
//        doubleLinkedList.list();

        // 删除
//        doubleLinkedList.del(6);
//        System.out.println("删除后的链表情况：");
//        doubleLinkedList.list();
    }
}

// 创建一个双向链表的类
class DoubleLinkedList {
    // 先初始化一个头节点，头节点不用动，不存放具体的数据
    private HeroNode2 head = new HeroNode2(0, "", "");

    // 返回头节点
    public HeroNode2 getHead() {
        return head;
    }

    // 显示链表[遍历]
    public void list() {
        // 判断链表是否为空
        if(head.next == null) {
            System.out.println("链表为空");
            return;
        }

        // 因为头节点不能动，因此需要一个辅助变量来遍历
        HeroNode2 tmp = head.next;
        // 判断是否到链表最后
        while (tmp != null) {
            // 输出节点信息
            System.out.println(tmp);
            // 将tmp后移，一定小心
            tmp = tmp.next;
        }
    }

    /**
     * 添加一个节点到双向链表的最后
     * @param heroNode
     * @return
     */
    public DoubleLinkedList add(HeroNode2 heroNode) {
        // 因为head节点不能动，因此我们需要一个辅助变量 tmp
        HeroNode2 tmp = head;
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
        // 形成一个双向链表
        tmp.next = heroNode;
        heroNode.pre = tmp;

        return this;
    }

    /**
     * 修改一个节点的内容，可以看到双向链表的节点内容修改和单向链表一样
     * 只是节点类型改成 HeroNode2
     * @param newHeroNode
     */
    public void update(HeroNode2 newHeroNode) {
        // 判断是否为空
        if(head.next == null) {
            System.out.println("链表为空");
            return;
        }

        // 找到需要修改的节点，根据no编号
        // 定义一个辅助变量
        HeroNode2 tmp = head.next;
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
     * 从双向链表中删除一个节点
     * 说明：
     * 1.对于双向链表，我们可以直接找到要删除的这个节点
     * 2.找到后，自我删除即可
     */
    public void del(int no) {

        // 判断当前链表是否为空
        if(head.next == null) {
            System.out.println("链表为空，无法删除");
            return;
        }

        HeroNode2 tmp = head.next;
        boolean flag = false; // 标志是否找到待删除节点的
        while (tmp != null) {
            if(tmp.no == no) {
                // 找到待删除节点tmp
                flag = true;
                break;
            }

            tmp = tmp.next; // tmp后移，遍历
        }

        // 判断flag
        if(flag) { // 找到
            // 可以删除
            tmp.pre.next = tmp.next;
            // 这里代码有问题
            // 如果是最后一个节点，就不需要执行下面这句话，否则出现空指针
            if(tmp.next != null) {
                tmp.next.pre = tmp.pre;
            }
        }else{
            System.out.printf("要删除的 %d 节点不存在", no);
        }
    }

    public DoubleLinkedList addByOrder(HeroNode2 heroNode) {
        // 因为头节点不能动，因此我们仍然通过一个辅助指针（变量）来帮助找到添加的位置
        HeroNode2 tmp = head;
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
            heroNode.pre = tmp;
            if(tmp.next != null) {
                tmp.next.pre = heroNode;
            }
            tmp.next = heroNode;
        }

        return this;
    }
}

/**
 * 定义HeroNode，每个HeroNode对象就是一个节点
 */
class HeroNode2 {
    public int no;

    public String name;

    public String nickname;

    public HeroNode2 next; // 指向下一个节点，默认为null

    public HeroNode2 pre; // 指向前一个节点，默认为null

    // 构造器
    public HeroNode2(int hNo, String hName, String hNickname) {
        this.no = hNo;
        this.name = hName;
        this.nickname = hNickname;
    }

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
