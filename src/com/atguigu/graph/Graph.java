package com.atguigu.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author gxl
 * @description
 * @createDate 2022/8/25 8:23
 */
public class Graph {

    private List<String> vertexList; // 存储顶点集合

    private int[][] edges; // 存储图的邻接矩阵

    private int numOfEdges; // 边的数目

    public Graph(int n) {
        // 初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
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
    }
}
