package com.atguigu.avl;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/23 9:16
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
//        int[] arr = {4, 3, 6, 5, 7, 8};
//        int[] arr = {10, 12, 8, 9, 7, 6};
        int[] arr = {10, 11, 7, 6, 8, 9};
        // 创建一个AVLTree对象
        AVLTree avlTree = new AVLTree();
        // 添加节点
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }

        // 遍历
        System.out.println("中序遍历");
        avlTree.infixOrder();

        System.out.println("平衡处理后");
        System.out.println("树的高度 = " + avlTree.getRoot().height());
        System.out.println("树的左子树高度 = " + avlTree.getRoot().leftHeight());
        System.out.println("树的右子树高度 = " + avlTree.getRoot().rightHeight());
        System.out.println("当前的根节点 = " + avlTree.getRoot());
        // 遍历
        System.out.println("中序遍历");
        avlTree.infixOrder();
    }
}

// 创建AVLTree
class AVLTree {
    private Node root;

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

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
 * 创建Node节点
 */
class Node {
    int val;

    Node left;

    Node right;

    public Node(int val) {
        this.val = val;
    }

    // 返回左子树的高度
    public int leftHeight() {
        if(left == null) {
            return 0;
        }

        return left.height();
    }

    // 返回右子树的高度
    public int rightHeight() {
        if(right == null) {
            return 0;
        }

        return right.height();
    }

    // 返回当前节点的高度，以该节点为根节点的树的高度
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    // 左旋转
    private void leftRotate() {
        // 创建新的节点，以当前根节点的值
        Node newNode = new Node(val);
        // 把新的节点的左子树设置成当前节点的左子树
        newNode.left = left;
        // 把新的节点的右子树设置成当前节点的右子树的左子树
        newNode.right = right.left;
        // 把当前节点的值替换成右子节点的值
        val = right.val;
        // 把当前节点的右子树设置成右子节点的右子树
        right = right.right;
        // 把当前节点的左子树（左子节点）设置成新的节点
        left = newNode;
    }

    // 右旋转
    private void rightRotate() {
        Node newNode = new Node(val);
        newNode.right = right;
        newNode.left = left.right;
        val = left.val;
        left = left.left;
        right = newNode;
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

        // 当添加完一个节点后，如果右子树的高度 - 左子树的高度 > 1 ===> 左旋转
        if(rightHeight() - leftHeight() > 1) {
            // 如果它的右子树的左子树高度大于它的右子树的高度
            if(right != null && right.rightHeight() > right.leftHeight()) {
                // 对当前节点的左节点（左子树）-> 左旋转
                right.rightRotate();
            }

            // 左旋转
            leftRotate();
        }

        // 当添加完一个节点后，如果左子树的高度 - 右子树的高度 > 1 ===> 右旋转
        if(leftHeight() - rightHeight() > 1) {
            // 如果它的左子树的右子树高度大于它的左子树的高度
            if(left != null && left.rightHeight() > left.leftHeight()) {
                // 对当前节点的左节点（左子树）-> 左旋转
                left.leftRotate();
            }

            // 右旋转
            rightRotate();
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
