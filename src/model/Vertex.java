package model;
import java.util.ArrayList;
public class Vertex<T extends Comparable<T>> {

    private T value;
    double x;
    double y;
    private ArrayList<Vertex<T>> adjacentVertices;
    private int color, dInit, dEnd;
    private Vertex<T> predecessor;


    public Vertex(T value, double x, double y) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.adjacentVertices = new ArrayList<>();
    }

    public void setValue(T value) {
        this.value = value;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public T getValue() {
        return value;
    }

    public int getColor() {
        return color;
    }

    public int getDInit() {
        return dInit;
    }

    public Vertex<T> getPredecessor() {
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

    public void setPredecessor(Vertex<T> predecessor) {
        this.predecessor = predecessor;
    }

    public boolean addAdjacent(Vertex<T> vertex){
        if (!searchAdjacent(vertex)){
            adjacentVertices.add(vertex);
            return true;
        }
        return false;
    }

    public boolean searchAdjacent(Vertex<T> vertex){
        for (Vertex<T> u : adjacentVertices) {
            if (u.equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Vertex<T>> getAdjacentVertices() {
        return adjacentVertices;
    }

    public void addConnection(Vertex<T> vertex){
        adjacentVertices.add(vertex);
    }
}