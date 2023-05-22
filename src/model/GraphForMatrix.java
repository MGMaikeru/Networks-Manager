package model;


import java.util.ArrayList;

public class GraphForMatrix <T, K extends Comparable<K>>{
    private int numVertices;
    private int[][] adjMatrix;
    private ArrayList<Vertex1> vertices;
    private boolean isDirected;

    public GraphForMatrix (boolean isDirected) {
        this.isDirected = isDirected;
        vertices = new ArrayList<>();
        numVertices = vertices.size();
        adjMatrix = new int[numVertices][numVertices];

    }

    public void addVertex(K key, T value) {
        Vertex1 vertex = new Vertex1(value, key);
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
        Integer srcIdx = searchVertex(srcKey);
        Integer destIdx = searchVertex(destKey);

        if (srcIdx != -1 && destIdx != -1) {
            Vertex1 srcVertex = vertices.get(srcIdx);
            Vertex1 destVertex = vertices.get(destIdx);
            srcVertex.addEdge(new Edge(srcVertex, destVertex, 1));

            if (isDirected){
                destVertex.addEdge(new Edge(srcVertex, destVertex, 1));
            }
            adjMatrix[srcIdx][destIdx] = 1;
            adjMatrix[destIdx][srcIdx] = 1;
        }
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
        Integer srcIdx = searchVertex(srcKey);
        Integer destIdx = searchVertex(destKey);

        if (srcIdx != -1 && destIdx != -1) {
            Vertex1 srcVertex = vertices.get(srcIdx);
            Vertex1 destVertex = vertices.get(destIdx);
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


}
