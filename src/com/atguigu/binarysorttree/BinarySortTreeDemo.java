package com.atguigu.binarysorttree;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/22 8:45
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};

        BinarySortTree binarySortTree = new BinarySortTree();
        // 循环添加节点到二叉排序树
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        // 中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树");
        binarySortTree.infixOrder(); // 1 2 3 5 7 9 10 12

        // 测试删除叶子节点
        binarySortTree.delNode(2);
        binarySortTree.delNode(5);
        binarySortTree.delNode(9);
        binarySortTree.delNode(12);
        binarySortTree.delNode(7);
        binarySortTree.delNode(3);
        binarySortTree.delNode(10);
        binarySortTree.delNode(1);
        System.out.println("删除节点后");
        binarySortTree.infixOrder();
    }
}

// 二叉排序树
class BinarySortTree {
    private Node root;

    // 删除节点
    public void delNode(int val) {
        if(root == null) {
            return;
        }

        // 1.找到要删除的节点
        Node targetNode = search(val);
        // 如果没有找到要删除节点
        if(targetNode == null) {
            return;
        }

        // 如果targetNode为root节点，同时二叉排序树只有1个节点
        if(root.left == null && root.right == null) {
            root = null;
            return;
        }

        // 去查找targetNode的父节点
        Node parent = searchParent(val);
        // 如果要删除的节点是叶子节点
        if(targetNode.left == null && targetNode.right == null) {
            // 判断targetNode是父节点的左子节点还是右子节点
            if(parent.left != null && parent.left.val == val) {
                parent.left = null;
            }else if(parent.right != null && parent.right.val == val) {
                parent.right = null;
            }
        }else if(targetNode.left != null && targetNode.right != null) {
            int minVal = delRightTreeMin(targetNode.right);
            targetNode.val = minVal;
        }else { // 删除只有一棵子树的情况
            // 如果要删除的节点是左子节点
            if(targetNode.left != null) {
                // parent为null，代表root节点
                if(parent == null) {
                    root = targetNode.left;
                    return;
                }

                // 如果targetNode是parent的左子节点
                if(parent.left.val == val) {
                    parent.left = targetNode.left;
                }else{
                    parent.right = targetNode.left;
                }
            }else {
                // parent为null，代表root节点
                if(parent == null) {
                    root = targetNode.right;
                    return;
                }

                if(parent.left.val == val) {
                    parent.left = targetNode.right;
                }else {
                    parent.right = targetNode.right;
                }
            }
        }
    }

    /**
     * 右子树找最小值，左子树找最大值
     * @param node 传入的节点（当做二叉排序树的根节点）
     * @return 返回是以node为根节点的二叉排序树的最小节点的值
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        // 循环的查找左节点，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }

        // 这时target就指向了最小节点
        // 删除最小节点
        delNode(target.val);
        return target.val;
    }

    // 查找要删除节点的父节点
    public Node searchParent(int val) {
        if(root == null) {
            return null;
        }

        return root.searchParent(val);
    }

    // 查找要删除的节点
    public Node search(int val) {
        if(root == null) {
            return null;
        }

        return root.search(val);
    }

    // 添加节点的方法
    public void add(Node node) {
        if(root == null) {
            root = node; // 如果root为空则直接让root指向node
        }else {
            root.add(node);
        }
    }

    // 中序遍历
    public void infixOrder() {
        if(root != null) {
            root.infixOrder();
        }else {
            System.out.println("当前二叉排序树为空，不能遍历");
        }
    }
}

/**
 * Node节点
 */
class Node {
    int val;

    Node left;

    Node right;

    public Node(int val) {
        this.val = val;
    }

    /**
     * 查找要删除的节点
     * @param val 希望删除的节点的值
     * @return 如果找到返回该节点，否则返回null
     */
    public Node search(int val) {
        if(val == this.val) { // 找到就是该节点
            return this;
        }else if(val < this.val) { // 如果查找的值小于当前节点，向左子树递归查找
            // 如果左子节点为空
            if(this.left == null) {
                return null;
            }

            return this.left.search(val);
        }else{ // 如果查找的值不小于当前节点，向右子树递归查找
            if(this.right == null) {
                return null;
            }

            return this.right.search(val);
        }
    }

    /**
     * 查找要删除节点的父节点
     * @param val 要找到的节点的值
     * @return 返回是要删除的节点的父节点，如果没有就返回null
     */
    public Node searchParent(int val) {
        // 如果当前节点就是要删除的节点的父节点，就返回
        if((this.left != null && this.left.val == val) ||
            (this.right != null && this.right.val == val)) {
            return this;
        }else {
            // 如果查找的值小于当前节点的值，并且当前节点的左子节点不为空
            if(val < this.val && this.left != null) {
                return this.left.searchParent(val); // 向左遍历查找
            }else if(val >= this.val && this.right != null) {
                return this.right.searchParent(val);
            }else {
                return null; // 没有找到父节点
            }
        }
    }

    // 添加节点
    // 递归形式添加节点，需要满足二叉排序树的要求
    public void add(Node node) {
        if(node == null) {
            return;
        }

        // 判断传入的节点的值与当前子树的根节点的值关系
        if(node.val < this.val) {
            // 如果当前节点左子节点为空
            if(this.left == null) {
                this.left = node;
            }else{
                // 递归的向左子树添加
                this.left.add(node);
            }
        }else { // 添加的节点的值大于等于当前节点的值
            if(this.right == null) {
                this.right = node;
            }else {
                // 递归向右子树添加
                this.right.add(node);
            }
        }
    }

    // 中序遍历
    public void infixOrder() {
        if(this.left != null) {
            this.left.infixOrder();
        }

        System.out.println(this);

        if(this.right != null) {
            this.right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "val=" + val +
                '}';
    }
}
