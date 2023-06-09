package model;

import exception.EmptyFieldException;

import java.util.List;

public abstract class Graph<T, K extends Comparable<K>> {
    public abstract void addVertex(K key, T value, double x, double y);
    public abstract String addEdge(K srcKey, K destKey, double weight);
    public abstract String getAdjacent(K key);
    public abstract String bfs(K key) throws EmptyFieldException;
    public abstract String dijkstra(K startKey,  K endKey);
    public abstract String prim(K startKey);
}
