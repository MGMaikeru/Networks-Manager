package model;

import exception.EmptyFieldException;

public class Queue<T> extends IQueue<T> {

    private Node1<T> front;
    private Node1<T> last;
    private int size;

    public Queue() {
        this.front = null;
        this.last = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.front == null;
    }

    @Override
    public T getFront() throws EmptyFieldException{
        if (front == null) {
            throw new EmptyFieldException();
        }
        return front.getValue();
    }

    public T getLast() {
        return last.getValue();
    }

    public void setFront(Node1<T> front) {
        this.front = front;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void enqueue(Node1<T> newNode){
        if (this.front == null) {
            this.front = newNode;
            this.size++;
            return;
        } else if(this.front != null && this.last == null){
            this.last =newNode;
            this.front.setBack(this.last);
            this.size++;
            return;
        }else {
            newNode.setNext(this.last);
            this.last = newNode;
            this.last.getNext().setBack(this.last);
            this.size++;
        }
    }

    @Override
    public T dequeue() throws EmptyFieldException{
        if (isEmpty()) {
            throw new EmptyFieldException();
        }
        Node1<T> toReturn = this.front;
        if (front.getBack() == null){
            this.front = null;
            return toReturn.getValue();
        }

        front.getBack().setNext(null);
        this.front = front.getBack();
        size--;
        return toReturn.getValue();
    }

    public Node1<T> dequeue2() throws EmptyFieldException{
        if (isEmpty()) { // Si la cola está vacía, no se puede hacer desencolar
            throw new EmptyFieldException();
        }
        Node1<T> toReturn = this.front;
        this.front = front.getBack(); // El nuevo frente será el siguiente elemento de la cola
        this.front.setNext(new Node1<>(null)); //Se coloca el siguientye null para realizar la desconexion
        size--; // Se reduce el tamaño de la cola
        return toReturn;
    }
}

