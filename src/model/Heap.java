package model;

import java.util.Collections;
import java.util.ArrayList;

public class Heap<T extends Comparable<T>> extends IPriorityQueue<T> {

    private int heapSize;
    private ArrayList<T> array;

    public Heap(){
        this.array = new ArrayList<>();
    }

    public void insertElement(T object){
     this.array.add(object);
    }

    /*@Override
    public T heapMaximum(){
        return array.get(0);
    }*/

    @Override
    public T heapMinimum(){
        return array.get(0);
    }

    public String printArray(){
        String list = "";
        for (int i = 0; i<array.size();i++){
            list += (i+1) + ") " +array.get(i).toString() + "\n";
        }
        return list;
    }

    public ArrayList<T> getArray(){
        return array;
    }

    @Override
    public T heapExtractMin(){
        if (array.size() < 1) {
            System.out.println("heap underflow");
        }
        T min = array.get(0);
        array.set(0, array.get(array.size()-1));
        array.remove(array.size()-1);
        minHeapify(0);
        return min;
    }

    @Override
    public void heapDecreaseKey(int i, T key){
        if (key.compareTo( array.get(i) ) < 0 ) {
            System.out.println("New key is smaller than current key");
        }
        array.set(i, key);

        while (i > 0 && array.get( (int)father(i) ).compareTo(array.get(i)) > 0){
            Collections.swap(array, i, (int)father(i));
            i = (int)father(i);
        }
    }

    @Override
    public void minHeapInsert(T key){
        array.add(key);
        heapDecreaseKey(array.size()-1, key);
    }

    public void heapSort(){
        buildMinHeap();
        for (int i = array.size(); i>=1; i--) {
            Collections.swap(array, 0, i - 1);
            reduceHeapSize();
            minHeapify(0);
        }
    }

    public void buildMinHeap(){
        this.heapSize = array.size()-1;
        for (int i = (int)Math.floor(array.size()/2); i>=1; i--){
            minHeapify(i-1);
        }
    }

    public void reduceHeapSize(){
        this.heapSize--;
    }

    public void minHeapify(int index){
        int l = left(index);
        int r = right(index);
        int shortest = 0;

        if (l >= heapSize && array.get(l).compareTo(array.get(index))< 0) {
            shortest = l;
        }else{
            shortest = index;
        }

        if (r >= heapSize && array.get(r).compareTo(array.get(shortest))< 0) {
            shortest = r;
        }
        if (shortest != index) {
            Collections.swap(array, index, shortest);
            minHeapify(shortest);
        }
        return;
    }

    public int getHeapSize(){
        int size = 0;
        for (int i = array.size();i>= 0; i--){
            if (father(i) != 0) {
                if (array.get((int)father(i)-1).compareTo(array.get(i-1)) >= 0){
                    size += 1;
                } else{
                    size = 0;
                }
            }

        }
        return size+1;
    }

    public int right(int index){
        return index*2+2;
    }

    public int left(int index){
        return index*2+1;
    }

    public double father(int index){
        return Math.floor(index/2);
    }


}
