package model;

public abstract class IPriorityQueue<T extends Comparable<T>> {
    public abstract T heapMinimum();
    public abstract T heapExtractMin();
    public abstract void heapDecreaseKey(int i, T key);
    public abstract void minHeapInsert(T key);
}
