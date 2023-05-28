package model;

public class Edge<T extends Comparable <T>>{
    private Vertex1 srcVertex;
    private Vertex1 destVertex;
    private Vertex<T> startVertex;
    private Vertex<T> finalVertex;

    private double weight;
    //Edge for GraphMatrix
    public Edge(Vertex1 initialVertex, Vertex1 finalVertex, double weight) {
        this.srcVertex = initialVertex;
        this.destVertex = finalVertex;
        this.weight = weight;
    }
    //Edge for GraphList
    public Edge(Vertex<T> startVertex, Vertex<T> finalVertex, double weight) {
        this.startVertex = startVertex;
        this.finalVertex = finalVertex;
        this.weight = weight;
    }

    public Vertex1 getInitialVertex() {
        return srcVertex;
    }

    public Vertex1 getDestinationVertex() {
        return destVertex;
    }

    public Vertex<T> getStartVertex() {
        return startVertex;
    }

    public Vertex<T> getFinalVertex() {
        return finalVertex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
