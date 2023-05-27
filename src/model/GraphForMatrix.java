package model;


import exception.EmptyFieldException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

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

    public void addVertex(K key, T value) {
        Vertex1<T, K> vertex = new Vertex1<T, K>(value, key);
        int newIndex = vertices.size();
        numVertices ++;
        vertices.add(vertex);

        int[][] newAdjMatrix = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices - 1; i++) {
            System.arraycopy(adjMatrix[i], 0, newAdjMatrix[i], 0, numVertices - 1);
        }

        adjMatrix = newAdjMatrix;
    }

    public void addEdge(K srcKey, K destKey, double weight) {
        int srcIdx = searchVertex(srcKey);
        int destIdx = searchVertex(destKey);

        if (srcIdx != -1 && destIdx != -1) {
            Vertex1<T, K> srcVertex = vertices.get(srcIdx);
            Vertex1<T, K> destVertex = vertices.get(destIdx);
            srcVertex.addEdge(new Edge(srcVertex, destVertex, weight));

            if (isDirected){
                destVertex.addEdge(new Edge(srcVertex, destVertex, weight));
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

    /*public void dijkstra(K key){
        ArrayList<Double> dist = new ArrayList<>();
        ArrayList<String> prev = new ArrayList<>();
        int index = searchVertex(key);
        Vertex1<T, K> source = vertices.get(index);
        source.setDistance(0);
        Heap queue = new Heap();

        for (Vertex1<T, K> vertex: vertices) {
            if (!vertex.equals(source)){
                vertex.setDistance(Double.MAX_VALUE);
            }
            vertex.setPredecessor(null);
            queue.minHeapInsert(vertex);
        }
        System.out.println(queue.printArray());
        while (!queue.isEmpty()){
            Vertex1<T, K> u = (Vertex1<T, K>) (queue.heapExtractMin());
            for (Edge edge: u.getEdges()) {
                double alt = u.getDistance() + edge.getWeight();
                if (alt < edge.getFinalVertex().getDistance()){
                    edge.getFinalVertex().setDistance(alt);
                    edge.getFinalVertex().setPredecessor(u);
                    queue.minHeapify(0);
                }
            }
        }

        for (Vertex1 vertex:vertices) {
            System.out.println(vertex.getValue() + "Distance: " + vertex.getDistance());
        }

    }*/

    public void dijkstra(K startKey) {
        int startIdx = searchVertex(startKey);

        if (startIdx == -1) {
            System.out.println("The start vertex does not exist in the graph.");
            return;
        }

        double[] distances = new double[numVertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startIdx] = 0;

        PriorityQueue<Vertex1<T, K>> queue = new PriorityQueue<>((v1, v2) -> (int) (distances[vertices.indexOf(v1)] - distances[vertices.indexOf(v2)]));
        queue.add(vertices.get(startIdx));

        while (!queue.isEmpty()) {
            Vertex1<T, K> current = queue.poll();

            for (Edge edge : current.getEdges()) {
                Vertex1<T, K> neighbor = edge.getFinalVertex();
                int neighborIdx = vertices.indexOf(neighbor);
                double weight = edge.getWeight();

                double newDistance = distances[startIdx] + weight;
                if (newDistance < distances[neighborIdx]) {
                    distances[neighborIdx] = newDistance;
                    neighbor.setPredecessor(current);

                    // Update the priority of the neighbor in the priority queue
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Print the shortest distances from the start vertex to all other vertices
        System.out.println("Shortest distances from vertex " + startKey + ":");
        for (int i = 0; i < numVertices; i++) {
            System.out.println(vertices.get(i).getKey() + ": " + distances[i]);
        }
    }

}
