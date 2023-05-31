package model;

import exception.EmptyFieldException;

import java.util.List;

public abstract class Graph<T, K extends Comparable<K>> {
    public abstract void addVertex(K key, T value);
    public abstract void addEdge(K srcKey, K destKey, double weight);
    public abstract String getAdjacent();
    public abstract String bfs(K key) throws EmptyFieldException;
    public abstract void dijkstra(K startKey);
    public abstract void prim(K startKey);
}
