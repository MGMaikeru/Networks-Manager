package model;

public class Edge {
    private Vertex1 srcVertex;
    private Vertex1 destVertex;
    private double weight;

    public Edge(Vertex1 initialVertex, Vertex1 finalVertex, double weight) {
        this.srcVertex = initialVertex;
        this.destVertex = finalVertex;
        this.weight = weight;
    }

    public Vertex1 getInitialVertex() {
        return srcVertex;
    }

    public Vertex1 getFinalVertex() {
        return destVertex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
