package model;

public class Main {
    private GraphList graph;
    private GraphForMatrix graph2;
    private Vertex1[] vertices;
    public Main(){
        graph = new GraphList(true);
        graph2 = new GraphForMatrix<>(false);
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.graph2.addVertex(0, 0);
        m.graph2.addVertex(1, 1);
        m.graph2.addVertex(2, 2);
        m.graph2.addVertex(3, 3);
        m.graph2.addVertex(4, 4);
        m.graph2.addVertex(5, 5);
        m.graph2.addVertex(6, 6);
        m.graph2.addVertex(7, 7);
        m.graph2.addEdge(1 , 2);;
        m.graph2.addEdge(0 , 1);
        m.graph2.addEdge(0 , 2);
        m.graph2.addEdge(0 , 3);
        m.graph2.addEdge(2, 4);
        m.graph2.addEdge(2, 6);
        m.graph2.addEdge(4, 6);
        m.graph2.addEdge(4, 7);
        m.graph2.addEdge(5, 7);
        m.graph2.addEdge(6, 7);
        m.graph2.printGraph();
    }
}
