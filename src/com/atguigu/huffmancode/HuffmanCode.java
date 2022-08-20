package com.atguigu.huffmancode;

import java.util.*;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/20 12:30
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String content = "i like java do you like Hadoop ? I don't like hadoop";

        byte[] huffmanCodesBytes = null;
        int huffmanBinaryCodesLength = 0;
        for (Map.Entry<byte[], Integer> entry : huffmanZip(content.getBytes()).entrySet()) {
            huffmanCodesBytes = entry.getKey();
            huffmanBinaryCodesLength = entry.getValue();
        }
        System.out.println("压缩后的结果是：" + Arrays.toString(huffmanCodesBytes));
        assert huffmanCodesBytes != null;
        System.out.println("长度为" + huffmanCodesBytes.length);

        // 将数据进行解压（解码）
//        System.out.println(byteToBitString(false, (byte) -88));
        byte[] sourceBytes = decode(huffmanCodes, huffmanCodesBytes, huffmanBinaryCodesLength);
        System.out.println("原来的字符串 = " + new String(sourceBytes));

/*      分步过程
        byte[] contentBytes = content.getBytes();

//        System.out.println(contentBytes.length); // 40

        List<Node> nodes = getNodes(contentBytes);
//        System.out.println("nodes = " + nodes);

        Node root = createHuffmanTree(nodes);
//        System.out.println("前序遍历");
//        preOrder(root);

        // 测试是否生成了对应的哈夫曼编码
//        getCodes(root, "", stringBuilder);
//        System.out.println("生成的哈夫曼编码表：" + HuffmanCode.huffmanCodes);
        Map<Byte, String> huffmanCodes = getCodes(root);
        System.out.println("生成的哈夫曼编码表：" + huffmanCodes);

        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
        System.out.println("huffmanCodeBytes" + Arrays.toString(huffmanCodeBytes)); // 17

        // 发送huffmanCodeBytes数组*/


    }

    // 完成数据的解压
    // 思路
    // 1.将huffmanCodeBytes重新转成哈夫曼二进制编码字符串
    // 2.对照哈夫曼编码映射，转成对应的字符

    /**
     * 完成对压缩数据的解码
     * @param huffmanCodes 哈夫曼编码表
     * @param huffmanBytes 哈夫曼编码得到的字节数组
     * @return 原始字符串对应的数组
     */
    private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes, int length) {
        // 1.先得到huffmanBytes对应的二进制的字符串
        StringBuilder builder = new StringBuilder();
        // 将byte数组转成二进制的字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            // 判断是不是最后一个字节
            // !!! 最后一个字节为负数，也需要截取8位
            boolean flag = (i == huffmanBytes.length - 1 && huffmanBytes[i] >= 0);
            // !!! 最后一个字节可能0开头，需拿二进制编码的总长度进行最后一个字节的判断
            builder.append(byteToBitString(!flag, huffmanBytes[i], length));
        }

//        System.out.println("哈夫曼字节数组对应的二进制字符串 = " + builder);

        // 把字符串按照指定的哈夫曼编码进行解码
        // 把哈夫曼编码表进行调整，因为反向查询 a->100 100->a
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        // 创建集合，存放byte
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < builder.length();) {
            int count = 1;
            boolean flag = true;
            Byte b = null;

            while (flag) {
                String key = builder.substring(i, i + count);
                b = map.get(key);
                if(b == null) { // 没有匹配到
                    count++;
                }else{
                    flag = false;
                }
            }
            list.add(b);
            i += count; // 直接移动到count
        }

        // 把list中的数据放入到byte[]并返回
        byte[] b = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }

        return b;
    }

    /**
     * 将一个byte转成一个二进制的字符串
     * @param flag 标识是否需要补高位，如果true，表示需要补高位，如果是false表示不补
     * @param b 传入的byte
     * @param length 哈夫曼字节数组对应的二进制字符串长度
     * @return 时该b对应的二进制的字符串（补码）
     */
    private static String byteToBitString(boolean flag, byte b, int length) {
        // 使用变量保存b
        int tmp = b; // 将b转成int
        // 如果b是正数，要补高位
        if(flag) {
            tmp |= 256; // 按位于256 => 1 0000 0000 | 0000 0001 => 1 0000 0001
        }

        String str = Integer.toBinaryString(tmp); // 返回的int类型的补码

        if(flag) {
            return str.substring(str.length() - 8);
        }else {
            if(str.length() < length % 8) {
                for (int i = 0; i < length % 8 - str.length(); i++) {
                    str = "0" + str;
                }
                return str;
            }

            return str;
        }
    }

    /**
     * 将前面的方法封装起来，便于调用
     * @param bytes 原始字符串对应的字节数组
     * @return 返回经过哈夫曼编码处理后的字节数组（压缩后的数组）
     */
//    private static byte[] huffmanZip(byte[] bytes) {
    private static Map<byte[], Integer> huffmanZip(byte[] bytes) {
        List<Node> nodes = getNodes(bytes);
        // 创建哈夫曼树
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        // 生成对应的哈夫曼编码（根据哈夫曼树）
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        // 根据生成的哈夫曼编码，压缩得到压缩后的哈夫曼编码字节数组
        return zip(bytes, huffmanCodes);
    }

    /**
     * 将字符串对应的byte[]数组，通过生成的哈夫曼编码表，返回一个哈夫曼编码压缩后的byte数组
     * @param bytes 原始的字符串对应的byte数组
     * @param huffmanCodes 生成的哈夫曼编码map
     * @return 哈夫曼编码后的byte数组 => 追加返回值：哈夫曼二进制编码长度
     * huffmanCodeBytes[0] = 10101000（补码） => byte [推导 10101000 => 10100111 => 11011000（原码） => -88]
     */
//    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
    private static Map<byte[], Integer> zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        Map<byte[], Integer> map = new HashMap<>();
        // 1.利用huffmanCodes将bytes转成哈夫曼二进制编码
        StringBuilder builder = new StringBuilder();
        // 遍历bytes数组
        for (byte b : bytes) {
            builder.append(huffmanCodes.get(b));
        }

//        System.out.println("哈夫曼字节数组对应的二进制字符串 = " + builder);

        // 将哈夫曼二进制编码转成byte数组
        // 统计返回 byte[] huffmanCodeBytes 长度
        int len = builder.length() % 8 == 0 ? builder.length() / 8 : builder.length() / 8 + 1;
        // 创建存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0; // 记录是第几个byte
        for (int i = 0; i < builder.length(); i += 8) { // 每8位对应一个byte，所以步长 +8
            String strByte;
            if(i + 8 > builder.length()) { // 不够8位
                strByte = builder.substring(i);
            }else{
                strByte = builder.substring(i, i + 8);
            }
            // 将strByte转成一个byte，放入到huffmanCodeBytes
            huffmanCodeBytes[index++] = (byte) Integer.parseInt(strByte, 2);
        }

//        return huffmanCodeBytes;
        map.put(huffmanCodeBytes, builder.toString().length());
        return map;
    }

    // 生成哈夫曼树对应的哈夫曼编码
    // 思路：
    // 1.将哈夫曼编码表存放在Map<Byte, String>中
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    // 2.在生成哈夫曼编码表时，需要去拼接路径，定义一个StringBuilder存储某个叶子节点的路径
    static StringBuilder stringBuilder = new StringBuilder();

    // 为了调用方便，重载getCodes
    private static Map<Byte, String> getCodes(Node root) {
        if(root == null) {
            return null;
        }

        // 处理root的左子树
        getCodes(root.left, "0", stringBuilder);
        // 处理root的右子树
        getCodes(root.right, "1", stringBuilder);

        return huffmanCodes;
    }

    /**
     * 功能：将传入的node节点的所有叶子节点的哈夫曼编码得到，并放入到huffmanCodes集合
     * @param node  传入节点
     * @param code  路径：左子节点为0，右子节点为1
     * @param stringBuilder 拼接路径
     */
    private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        // 将code加入到stringbuilder
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        stringBuilder2.append(code);
        if(node != null) {
            // 判断当前node是否是叶子节点
            if(node.data == null) { // 非叶子节点
                // 递归处理
                // 向左
                getCodes(node.left, "0", stringBuilder2);
                // 向右
                getCodes(node.right, "1", stringBuilder2);
            }else{ // 说明是一个叶子节点
                // 表示找到某个叶子节点
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }
        }
    }

    // 前序遍历的方法
    private static void preOrder(Node root) {
        if(root != null) {
            root.preOrder();
        }else {
            System.out.println("哈夫曼树为空");
        }
    }

    /**
     * @param bytes 接收字节数组
     * @return 返回的就是 List 形式
     */
    private static List<Node> getNodes(byte[] bytes) {
        // 1.创建一个ArrayList
        List<Node> nodes = new ArrayList<>();

        // 遍历bytes，统计每一个byte出现的次数 -> map
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if(count == null) { // map还没有这个字符数据
                counts.put(b, 1);
            }else {
                counts.put(b, count + 1);
            }
        }

        // 把每一个键值对转成一个Node对象，并加入到nodes集合
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }

        return nodes;
    }

    // 通过List，创建哈夫曼树
    public static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            // 1.排序
            Collections.sort(nodes);

            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            // 创建一棵新的二叉树，它的根节点没有data，只有权值
            Node parent = new Node(null, leftNode.weight + rightNode.weight);

            parent.left = leftNode;
            parent.right = rightNode;

            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parent);
        }

        return nodes.get(0);
    }
}

// 创建Node，带数据和权值
class Node implements Comparable<Node>{

    Byte data; // 存放数据本身，比如'a' => 97

    int weight; // 权值，表示字符出现的次数

    Node left;

    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    /**
     * 从小到大排序
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

    // 前序遍历
    public void preOrder() {
        System.out.println(this);

        if(this.left != null) {
            this.left.preOrder();
        }

        if(this.right != null) {
            this.right.preOrder();
        }
    }
}
