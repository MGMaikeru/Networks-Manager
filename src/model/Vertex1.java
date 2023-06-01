package model;
import java.util.ArrayList;
public class Vertex1<T, K extends Comparable<K>> {

    private T value;
    private K key;
    private ArrayList<Edge> edges;
    private int color, dInit, dEnd;
    private Vertex1<T, K> predecessor;
    private int distance;


    public Vertex1(T value, K key) {
        this.value = value;
        this.key = key;
        this.edges = new ArrayList<>();
        this.distance = Integer.MAX_VALUE;
    }

    public T getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }

    public int getColor() {
        return color;
    }

    public int getDInit() {
        return dInit;
    }

    public Vertex1 getPredecessor() {
        return predecessor;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDInit(int distance) {
        this.dInit = distance;
    }

    public int getDEnd() {
        return dEnd;
    }

    public void setDEnd(int dEnd) {
        this.dEnd = dEnd;
    }

    public void setPredecessor(Vertex1 predecessor) {
        this.predecessor = predecessor;
    }

    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void removeEdge(Vertex1 destVertex){
        for (int i = 0; i <edges.size(); i++){
            if (edges.get(i).getFinalVertex().equals(destVertex)){
                edges.remove(i);
            }
        }
    }

    /*public boolean addAdjacent(Vertex1 vertex){
        if (!searchAdjacent(vertex)){
            adjacentVertices.add(vertex);
            return true;
        }
        return false;
    }

    /*public boolean searchAdjacent(Vertex<T> vertex){
        for (Vertex1 u : adjacentVertices) {
            if (u.equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Vertex1> getAdjacentVertices() {
        return adjacentVertices;
    }*/

    public void addConnection(Edge edge){
        edges.add(edge);
    }
}