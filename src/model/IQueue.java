package model;

import exception.EmptyFieldException;

public abstract class IQueue<T> {

    public abstract T getFront() throws EmptyFieldException;

    public abstract void enqueue(Node1<T> newNode);

    public abstract T dequeue() throws EmptyFieldException;
}
