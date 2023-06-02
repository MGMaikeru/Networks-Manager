package model;


import exception.EmptyFieldException;

import java.util.ArrayList;

public class GraphForMatrix <T, K extends Comparable<K>>{
    private int numVertices;
    private int[][] adjMatrix;
    private ArrayList<Vertex1<T, K>> vertices;
    private boolean isDirected;
    private int time;

    public GraphForMatrix (boolean isDirected) {
        this.isDirected = isDirected;
        vertices = new ArrayList<>();
        numVertices = vertices.size();
        adjMatrix = new int[numVertices][numVertices];

    }

    public void addVertex(K key, T value, double x, double y) {
        Vertex1<T, K> vertex = new Vertex1<T, K>(value, key, x, y);
        int newIndex = vertices.size();
        numVertices ++;
        vertices.add(vertex);

        int[][] newAdjMatrix = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices - 1; i++) {
            System.arraycopy(adjMatrix[i], 0, newAdjMatrix[i], 0, numVertices - 1);
        }

        adjMatrix = newAdjMatrix;
    }

    public void addEdge(K srcKey, K destKey) {
        int srcIdx = searchVertex(srcKey);
        int destIdx = searchVertex(destKey);

        if (srcIdx != -1 && destIdx != -1) {
            Vertex1<T, K> srcVertex = vertices.get(srcIdx);
            Vertex1<T, K> destVertex = vertices.get(destIdx);
            srcVertex.addEdge(new Edge(srcVertex, destVertex, 1));

            if (isDirected){
                destVertex.addEdge(new Edge(srcVertex, destVertex, 1));
            }
            adjMatrix[srcIdx][destIdx] = 1;
            adjMatrix[destIdx][srcIdx] = 1;
        }
    }

    public ArrayList<Vertex1<T, K>> getVertices() {
        return vertices;
    }

    public int searchVertex(K key){
        int foundedVertexInx = -1;
        for (int i= 0; i<vertices.size(); i++){
            if (vertices.get(i).getKey().equals(key)){
                foundedVertexInx = i;
            }
        }
        return foundedVertexInx;
    }

    public void removeEdge(K srcKey, K destKey) {
        int srcIdx = searchVertex(srcKey);
        int destIdx = searchVertex(destKey);

        if (srcIdx != -1 && destIdx != -1) {
            Vertex1<T, K> srcVertex = vertices.get(srcIdx);
            Vertex1<T, K> destVertex = vertices.get(destIdx);
            srcVertex.removeEdge(destVertex);

            if (isDirected){
                destVertex.removeEdge(srcVertex);
            }
            adjMatrix[srcIdx][destIdx] = 0;
            adjMatrix[destIdx][srcIdx] = 0;
        }
    }

    public void printGraph() {
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String bfs(K key) throws EmptyFieldException {
        Vertex1<T, K> toSearch = vertices.get(searchVertex(key));
        Queue<Vertex1<T, K>> queue= new Queue<>();
        String msg = "";
        if (toSearch == null){
            return "This value don't exist in the graph";
        }
        for (Vertex1<T, K> vertex: vertices) {
            vertex.setColor(0);
            vertex.setPredecessor(null);
            vertex.setDInit(0);
        }
        toSearch.setColor(1);
        queue.enqueue(new Node1<>(toSearch));
        while (!queue.isEmpty()){
            Vertex1<T, K> u = queue.dequeue();

            for (Edge e: u.getEdges()) {
                Vertex1<T, K> v = e.getFinalVertex();
                if (v.getColor() == 0) {
                    v.setColor(1);
                    v.setDInit(u.getDInit() + 1);
                    v.setPredecessor(u);
                    queue.enqueue(new Node1<>(v));
                }
            }
            u.setColor(2);
        }
        for (Vertex1<T, K> vertex: vertices) {
            msg += vertex.getValue() + " " + vertex.getDInit() + " away.\n";
        }
        return msg;
    }

    public void dfs(){
        String msg = "";
        for (Vertex1<T, K> vertex: vertices) {
            vertex.setColor(0);
            vertex.setPredecessor(null);
            vertex.setDInit(0);

        }
        this.time = 0;
        for (Vertex1<T, K> vertex: vertices) {
            if (vertex.getColor() == 0)
                dfs(vertex);
        }
    }

    private void dfs(Vertex1<T, K> vertex){
        time ++;
        vertex.setDInit(time);
        vertex.setColor(1);
        for (Edge edge: vertex.getEdges()) {
            Vertex1<T, K> v = edge.getFinalVertex();
            if (v.getColor() == 0) {
                v.setPredecessor(vertex);
                dfs(v);
            }
        }
        vertex.setColor(2);
        time++;
        vertex.setDEnd(time);
    }

    public String checkDfs(){
        String msg = "";
        for (Vertex1<T, K> vertex: vertices) {
            msg += vertex.getValue() + " d: " + vertex.getDInit() + " f: " + vertex.getDEnd() + "\n";
        }
        return msg;
    }

    public void floyd(){
        int[][] floydMatrix = new int[numVertices][numVertices];
        for (int i = 0; i<numVertices; i++){
            for (int j = 0; i<numVertices; i++){
                floydMatrix[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    public void dijkstra(K key){
        int index = searchVertex(key);
        vertices.get(index);

    }
}
