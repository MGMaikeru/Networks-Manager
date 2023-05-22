package model;

public class Node1<T> {

    private T value;

    private Node1<T> next;
    private Node1<T> back;
    public Node1(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }


    @Override
    public String toString(){
        return "My value is: " + value;
    }

    public Node1<T> getNext() {
        return next;
    }

    public void setNext(Node1<T> next) {
        this.next = next;
    }

    public void setBack(Node1<T> back) {
        this.back = back;
    }

    public Node1<T> getBack() {
        return back;
    }
}
