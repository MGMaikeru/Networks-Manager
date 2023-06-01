package model;

import exception.EmptyFieldException;

import java.util.List;

public abstract class Graph<T extends Comparable<T>> {
    public abstract void addVertex(T value, double x, double y);
    public abstract String addEdge(T origin, T destination);
    public abstract List<T> getAdjacent(T value);
    public abstract String bfs(T value) throws EmptyFieldException;
    public abstract void dfs();

}
