package model;
import java.util.ArrayList;
public class Vertex1<T, K extends Comparable<K>> implements Comparable<Vertex1> {

    private T value;
    private K key;
    private ArrayList<Edge> edges;
    private ArrayList<Vertex1<T,K>> adjacentVertices;
    private int color, dInit, dEnd;
    private Vertex1<T, K> predecessor;
    private double distance;
    private double x, y;


    public Vertex1(T value, K key, double x, double y) {
        this.value = value;
        this.key = key;
        this.edges = new ArrayList<>();
        this.adjacentVertices = new ArrayList<>();
        this.distance = Integer.MAX_VALUE;
        this.x = x;
        this.y = y;
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

    public void setValue(T value) {
        this.value = value;
    }

    public void setKey(K key) {
        this.key = key;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void removeEdge(Vertex1 destVertex){
        for (int i = 0; i <edges.size(); i++){
            if (edges.get(i).getDestinationVertex().equals(destVertex)){
                edges.remove(i);
            }
        }
    }

    public boolean addAdjacent(Vertex1<T,K> vertex, double weight){
        if (!searchAdjacent(vertex)){
            adjacentVertices.add(vertex);
            edges.add(new Edge<>(this,vertex,weight));
            return true;
        }
        return false;
    }

    public boolean searchAdjacent(Vertex1<T,K> vertex){
        for (Vertex1 u : adjacentVertices) {
            if (u.equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Vertex1<T, K>> getAdjacentVertices() {
        return adjacentVertices;
    }

    public void addConnection(Edge edge){
        edges.add(edge);
    }

    @Override
    public int compareTo(Vertex1 o) {
        return (int)(distance-o.getDistance());
    }
}