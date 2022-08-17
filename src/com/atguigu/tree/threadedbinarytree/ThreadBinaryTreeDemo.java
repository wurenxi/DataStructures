package com.atguigu.tree.threadedbinarytree;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/15 12:07
 */
public class ThreadBinaryTreeDemo {
    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "tom");
        HeroNode node2 = new HeroNode(3, "jack");
        HeroNode node3 = new HeroNode(6, "smith");
        HeroNode node4 = new HeroNode(8, "mary");
        HeroNode node5 = new HeroNode(10, "king");
        HeroNode node6 = new HeroNode(14, "dim");

        // 二叉树（目前手动创建）
        root.setLeft(node2);
        node2.setParent(root);
        root.setRight(node3);
        node3.setParent(root);
        node2.setLeft(node4);
        node4.setParent(node2);
        node2.setRight(node5);
        node5.setParent(node2);
        node3.setLeft(node6);
        node6.setParent(node3);

        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
        threadedBinaryTree.setRoot(root);
//        threadedBinaryTree.threadedNodes();
        // 前序线索化二叉树
//        threadedBinaryTree.preThreadedNodes();
        // 后续线索化二叉树
        threadedBinaryTree.postThreadedNodes();

        // 测试线索化，以10号节点测试
//        HeroNode leftNode = node5.getLeft();
//        System.out.println("10号节点的前驱节点是 = " + leftNode);
//        System.out.println("10号节点的后继节点是 = " + node5.getRight());

        // 测试前序线索化，以8号节点测试
//        HeroNode leftNode = node4.getLeft();
//        System.out.println("8号节点的前驱节点是 = " + leftNode);
//        System.out.println("8号节点的后继节点是 = " + node4.getRight());

        // 测试后续线索化，以10号节点测试
        HeroNode leftNode = node5.getLeft();
        System.out.println("10号节点的前驱节点是 = " + leftNode);
        System.out.println("10号节点的后继节点是 = " + node5.getRight());

//        System.out.println("使用线索化的方式遍历 线索化二叉树");
//        threadedBinaryTree.threadedList();
//        System.out.println("使用线索化的前序遍历 线索化二叉树");
//        threadedBinaryTree.preThreadedList();
        System.out.println("使用线索化的后序遍历 线索化二叉树");
        threadedBinaryTree.postThreadedList();
    }
}

/**
 * 实现了线索化功能的二叉树
 */
class ThreadedBinaryTree {
    private HeroNode root;

    // 为了实现线索化，需要创建一个指向当前节点的前驱节点的指针
    // 在递归进行线索化时，pre总是保留前一个节点
    private HeroNode pre;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    public void threadedNodes() {
        pre = null;
        this.threadedNodes(root);
    }

    public void preThreadedNodes() {
        pre = null;
        this.preThreadedNodes(root);
    }

    public void postThreadedNodes() {
        pre = null;
        this.postThreadedNodes(root);
    }

    // 前序遍历线索化二叉树的方法
    public void preThreadedList() {
        // 定义一个变量，存储当前遍历的节点，从root开始
        HeroNode node = root;
        while (node != null) {
            // 打印当前这个节点
            System.out.println(node);

            // 循环的找到leftType = 1的节点，第一个找到的就是8节点
            // node随着遍历而变化，当leftType == 1 时，说明该节点是按照线索化
            // 处理后的有效节点
            while (node.getLeftType() == 0) {
                node = node.getLeft();
                System.out.println(node);
            }

            // 如果当前节点的右指针指向的是后继节点，就一直输出
            while (node.getRightType() == 1) {
                // 获取到当前节点的后继节点
                node = node.getRight();
                System.out.println(node);
            }

            node = node.getRight();
        }
    }

    // 遍历线索化二叉树的方法
    public void threadedList() {
        // 定义一个变量，存储当前遍历的节点，从root开始
        HeroNode node = root;
        while (node != null) {
            // 循环的找到leftType = 1的节点，第一个找到的就是8节点
            // node随着遍历而变化，当leftType == 1 时，说明该节点是按照线索化
            // 处理后的有效节点
            while (node.getLeftType() == 0) {
                node = node.getLeft();
            }

            // 打印当前这个节点
            System.out.println(node);

            // 如果当前节点的右指针指向的是后继节点，就一直输出
            while (node.getRightType() == 1) {
                // 获取到当前节点的后继节点
                node = node.getRight();
                System.out.println(node);
            }

            // 替换这个遍历的节点
            node = node.getRight();
        }
    }

    // 前序遍历线索化二叉树的方法
    public void postThreadedList() {
        // 定义一个变量，存储当前遍历的节点，从root开始
        HeroNode node = root;
        while (node != null) {
            // 循环的找到leftType = 1的节点，第一个找到的就是8节点
            // node随着遍历而变化，当leftType == 1 时，说明该节点是按照线索化
            // 处理后的有效节点
            while (node.getLeftType() == 0) {
                node = node.getLeft();
            }

            // 如果当前节点的右指针指向的是后继节点，就一直输出
            while (node.getRightType() == 1) {
                // 获取到当前节点的后继节点
                System.out.println(node);
                pre = node;
                node = node.getRight();
            }

            while (node != null && node.getRight() == pre) {
                System.out.println(node);
                pre = node;
                node = node.getParent();
            }
            if (node != null && node.getRightType() == 0) {
                node = node.getRight();
            }
        }
    }

    /**
     * 编写对二叉树进行前序线索化的方法
     * @param node 当前需要线索化的节点
     */
    public void preThreadedNodes(HeroNode node) {
        // 如果 node == null，不能线索化
        if(node == null) {
            return;
        }

        // (2) 线索化当前节点
        // 处理当前节点的前驱节点
        if(node.getLeft() == null) {
            // 让当前节点的左指针指向前驱节点
            node.setLeft(pre);
            // 修改当前节点的左指针的类型，指向的前驱节点
            node.setLeftType(1);
        }
        // 处理后继节点
        if(pre != null && pre.getRight() == null) {
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            // 修改前驱节点右指针类型
            pre.setRightType(1);
        }
        // !!! 每处理一个节点后，让当前节点是下一个节点的前驱节点
        pre = node;

        // (2) 线索化左子树
        if(node.getLeftType() == 0) {
            preThreadedNodes(node.getLeft());
        }

        // (3) 线索化右子树
        if(node.getRightType() == 0) {
            preThreadedNodes(node.getRight());
        }
    }

    /**
     * 编写对二叉树进行中序线索化的方法
     * @param node 当前需要线索化的节点
     */
    public void threadedNodes(HeroNode node) {
        // 如果 node == null，不能线索化
        if(node == null) {
            return;
        }

        // (1) 先线索化左子树
        threadedNodes(node.getLeft());
        // (2) 线索化当前节点
        // 处理当前节点的前驱节点
        if(node.getLeft() == null) {
            // 让当前节点的左指针指向前驱节点
            node.setLeft(pre);
            // 修改当前节点的左指针的类型，指向的前驱节点
            node.setLeftType(1);
        }
        // 处理后继节点
        if(pre != null && pre.getRight() == null) {
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            // 修改前驱节点右指针类型
            pre.setRightType(1);
        }
        // !!! 每处理一个节点后，让当前节点是下一个节点的前驱节点
        pre = node;

        // (3) 线索化右子树
        threadedNodes(node.getRight());
    }

    /**
     * 编写对二叉树进行后序线索化的方法
     * @param node 当前需要线索化的节点
     */
    public void postThreadedNodes(HeroNode node) {
        // 如果 node == null，不能线索化
        if(node == null) {
            return;
        }

        // (1) 线索化左子树
        if(node.getLeftType() == 0) {
            postThreadedNodes(node.getLeft());
        }

        // (2) 线索化右子树
        if(node.getRightType() == 0) {
            postThreadedNodes(node.getRight());
        }

        // (3) 线索化当前节点
        // 处理当前节点的前驱节点
        if(node.getLeft() == null) {
            // 让当前节点的左指针指向前驱节点
            node.setLeft(pre);
            // 修改当前节点的左指针的类型，指向的前驱节点
            node.setLeftType(1);
        }
        // 处理后继节点
        if(pre != null && pre.getRight() == null) {
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            // 修改前驱节点右指针类型
            pre.setRightType(1);
        }
        // !!! 每处理一个节点后，让当前节点是下一个节点的前驱节点
        pre = node;
    }

    // 删除节点
    public void delNode(int no) {
        if(root != null) {
            // 如果只有一个root节点，这里需要立即判断root是不是就是要删除的节点
            if(root.getNo() == no) {
                root = null;
            }else {
                // 递归删除
                root.delNode(no);
            }
        }else {
            System.out.println("空树，不能删除");
        }
    }

    // 前序遍历
    public void preOrder() {
        if(this.root != null) {
            this.root.preOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 中序遍历
    public void infixOrder() {
        if(this.root != null) {
            this.root.infixOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 后序遍历
    public void postOrder() {
        if(this.root != null) {
            this.root.postOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 前序遍历查找
    public HeroNode preOrderSearch(int no) {
        if(this.root != null) {
            return root.preOrderSearch(no);
        }

        return null;
    }

    // 中序遍历查找
    public HeroNode infixOrderSearch(int no) {
        if(this.root != null) {
            return root.infixOrderSearch(no);
        }

        return null;
    }

    // 后序遍历查找
    public HeroNode postOrderSearch(int no) {
        if(this.root != null) {
            return root.postOrderSearch(no);
        }

        return null;
    }
}

class HeroNode {
    private int no;

    private String name;

    private HeroNode left;

    private HeroNode right;

    private HeroNode parent;

    // 说明
    // 1.如果leftType == 0 表示指向的是左子树，如果 1 则表示指向前驱节点
    // 2.如果rightType == 0 表示指向是右子树，如果 1 表示指向后继节点
    private int leftType;

    private int rightType;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public HeroNode getParent() {
        return parent;
    }

    public void setParent(HeroNode parent) {
        this.parent = parent;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    // 递归删除节点
    // 1.如果删除的节点是叶子节点，则删除该节点
    // 2.如果删除的节点是非叶子节点，则删除该子树
    public void delNode(int no) {
        if (this.left != null && this.left.no == no) {
            this.left = null;
            return;
        }

        if (this.right != null && this.right.no == no) {
            this.right = null;
            return;
        }

        if (this.left != null) {
            this.left.delNode(no);
        }

        if (this.right != null) {
            this.right.delNode(no);
        }
    }

    // 前序遍历的方法
    public void preOrder() {
        System.out.println(this); // 先输出父节点
        // 递归向左子树前序遍历
        if (this.left != null) {
            this.left.preOrder();
        }

        // 递归向右子树前序遍历
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    // 中序遍历
    public void infixOrder() {
        // 递归向左子树中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }

        // 输出父节点
        System.out.println(this);

        // 递归向右子树前序遍历
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    // 后序遍历
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }

        if (this.right != null) {
            this.right.postOrder();
        }

        System.out.println(this);
    }

    /**
     * 前序遍历查找
     *
     * @param no 查找no
     * @return 如果找到就返回该Node，如果没有找到返回null
     */
    public HeroNode preOrderSearch(int no) {
        System.out.println("进入前序查找");
        // 比较当前节点是不是
        if (this.no == no) {
            return this;
        }

        // 1.则判断当前节点的左子节点是否为空，如果不为空，则递归前序查找
        // 2.如果左递归前序查找，找到节点，则返回
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.preOrderSearch(no);
        }

        if (resNode != null) {
            return resNode;
        }

        if (this.right != null) {
            resNode = this.right.preOrderSearch(no);
        }

        return resNode;
    }

    /**
     * 中序遍历查找
     *
     * @param no 查找no
     * @return 如果找到就返回该Node，如果没有找到返回null
     */
    public HeroNode infixOrderSearch(int no) {
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.infixOrderSearch(no);
        }

        if (resNode != null) {
            return resNode;
        }

        System.out.println("进入中序查找");
        // 比较当前节点是不是
        if (this.no == no) {
            return this;
        }

        if (this.right != null) {
            resNode = this.right.infixOrderSearch(no);
        }

        return resNode;
    }

    /**
     * 后序遍历查找
     *
     * @param no 查找no
     * @return 如果找到就返回该Node，如果没有找到返回null
     */
    public HeroNode postOrderSearch(int no) {
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.postOrderSearch(no);
        }

        if (resNode != null) {
            return resNode;
        }

        if (this.right != null) {
            resNode = this.right.postOrderSearch(no);
        }

        if (resNode != null) {
            return resNode;
        }

        System.out.println("进入后序查找");
        // 比较当前节点是不是
        if (this.no == no) {
            return this;
        }

        return null;
    }
}

