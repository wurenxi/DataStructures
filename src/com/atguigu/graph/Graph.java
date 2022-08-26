package com.atguigu.graph;

import java.util.*;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/25 8:23
 */
public class Graph {

    private List<String> vertexList; // 存储顶点集合

    private int[][] edges; // 存储图的邻接矩阵

    private int numOfEdges; // 边的数目

    // 定义给数组boolean[]，记录某个节点是否被访问
    private boolean[] isVisited;

    public Graph(int n) {
        // 初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        isVisited = new boolean[n];
    }

    /**
     * 得到第一个邻接节点的下标w
     * @param index
     * @return 如果存在就返回对应的下标，否则返回-1
     */
    public int getFirstNeighbor(int index) {
        for (int j = 0; j < vertexList.size(); j++) {
            if(edges[index][j] > 0) {
                return j;
            }
        }

        return -1;
    }

    // 根据前一个邻接节点的下标来获取下一个邻接节点
    public int getNextNeighbor(int v1, int v2) {
        for(int j = v2 + 1; j < vertexList.size(); j++) {
            if(edges[v1][j] > 0) {
                return j;
            }
        }

        return -1;
    }

    /**
     * 深度优先遍历算法
     * 第一次就是0
     */
    private void dfs(boolean[] isVisited, int i) {
        // 我们访问该节点
        System.out.print(getValByIndex(i) + "->");
        // 将节点设置为已经访问过
        isVisited[i] = true;
        // 查找节点i的第一个邻接节点w
        int w = getFirstNeighbor(i);
        while (w != -1) { // 有
            if(!isVisited[w]) {
                dfs(isVisited, w);
            }

            // 如果w节点已经被访问过
            w = getNextNeighbor(i, w);
        }
    }

    // 对dfs进行重载，遍历所有的节点，并进行dfs
    public void dfs() {
        // 遍历所有的节点，进行dfs【回溯】
        for (int i = 0; i < getNumOfVertex(); i++) {
            if(!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
    }

    // 对一个节点进行广度优先遍历的方法
    private void bfs(boolean[] isVisited, int i) {
        int u; // 头节点对应的下标
        int w; // 邻接节点的下标
        // 队列，记录节点访问的顺序
        // 访问节点，输出节点信息
        System.out.print(getValByIndex(i) + "=>");
        // 标记为已访问
        isVisited[i] = true;
        // 将节点加入队列
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(i);
        while (!queue.isEmpty()) {
            // 取出队列的头节点下标
            u = queue.removeFirst();
            // 得到第一个邻接节点的下标w
            w = getFirstNeighbor(u);
            while (w != -1) {
                if(!isVisited[w]) {
                    System.out.print(getValByIndex(w) + "=>");
                    // 标记已经访问
                    isVisited[w] = true;
                    // 入队列
                    queue.addLast(w);
                }

                // 已u为前驱点，找w后面的下一个邻接点
                w = getNextNeighbor(u, w); // 体现广度优先
            }
        }
    }

    // 遍历所有的节点，就进行广度优先搜索
    private void bfs() {
        for (int i = 0; i < getNumOfVertex(); i++) {
            if(!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }

    // 图中常用的方法
    // 1.返回节点的个数
    public int getNumOfVertex() {
        return vertexList.size();
    }

    // 2.得到边的数目
    public int getNumOfEdges() {
        return numOfEdges;
    }

    // 3.返回节点i（下标）对应的数据
    public String getValByIndex(int i) {
        return vertexList.get(i);
    }

    // 4.返回v1和v2的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    // 5.显示图对应的矩阵
    public void showGraph() {
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }
    }

    // 插入节点
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1 顶点的下标 "A" - "B" => "B"->1
     * @param v2 第二个顶点的下标
     * @param weight
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    public static void main(String[] args) {
        // 测试图是否创建成功
        int n = 5; // 5个节点
        String[] VertexVal = {"A","B","C","D","E"};
        // 创建图对象
        Graph graph = new Graph(n);
        // 循环添加顶点
        for (String vertex : VertexVal) {
            graph.insertVertex(vertex);
        }

        // 添加边
        // A-B A-C B-C B-D B-E
        graph.insertEdge(0, 1, 1); // A-B
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);

        // 展示邻接矩阵
        graph.showGraph();

        // 深度遍历
//        System.out.println("深度遍历");
//        graph.dfs(); // A->B->C->D->E

        System.out.println("\n广度优先遍历");
        graph.bfs();
    }
}
