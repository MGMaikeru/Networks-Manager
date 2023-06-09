package model;


import exception.EmptyFieldException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class GraphForMatrix <T, K extends Comparable<K>> extends Graph<T, K> {
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

    /*public double calculateAttenuation(Vertex1<Node, String> initialNode, Vertex1<Node, String> destinationNode, double distance){
        double frequency = (initialNode.getValue().getBandWith() + destinationNode.getValue().getBandWith())/2;
        return 20*Math.log10(distance)+20*Math.log10(frequency)+20*Math.log10((4*Math.PI)/(3*Math.pow(10, 8)));
    }*/

    @Override
    public void addVertex(K key, T value, double x, double y) {
        try {
            if (key.equals("")){
                throw new RuntimeException("There can't be vertex with null key");
            }else {
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
        }catch (RuntimeException e){
            throw e;
        }
    }

    public String addEdge(K srcKey, K destKey, double weight) {
        int srcIdx = searchVertex(srcKey);
        int destIdx = searchVertex(destKey);

        if (srcIdx == destIdx){
            return "You cannot add a loop in an undirected graph!";
        }

        if (srcIdx == -1 || destIdx == -1) {
            return "One or both vertices do not exist";
        }

        if (srcIdx != -1 && destIdx != -1) {

            Vertex1<T, K> srcVertex = vertices.get(srcIdx);
            Vertex1<T, K> destVertex = vertices.get(destIdx);

            if (srcVertex.searchAdjacent(destVertex)){
                return "These vertices are already connected";
            }

            srcVertex.addEdge(new Edge(srcVertex, destVertex, weight));
            adjMatrix[srcIdx][destIdx] = 1;
            srcVertex.addAdjacent(destVertex, weight);

            if (isDirected) {
                destVertex.addEdge(new Edge(destVertex, srcVertex, weight));
                adjMatrix[destIdx][srcIdx] = 1;
                destVertex.addAdjacent(srcVertex, weight);
            }
            return "Vertices successfully connected";
        }

        return "Vertex does not exist";
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

    @Override
    public String getAdjacent(K key) {
        Vertex1<T,K> vertex = vertices.get(searchVertex(key));
        String adjacent = "";
        if (vertex != null) {
            for (Vertex1<T,K> connection : vertex.getAdjacentVertices()) {
                adjacent += connection.getKey() + " ";
            }
            return adjacent.trim();
        }else {
            return "This Vertex doesn't have adjacent vertexes";
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
                Vertex1<T, K> v = e.getDestinationVertex();
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
            msg += vertex.getKey() + " " + vertex.getDInit() + " away.\n";
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
            Vertex1<T, K> v = edge.getDestinationVertex();
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

    /*@Override
    public String dijkstra(K startKey, K endKey) {
        Vertex1<T, K> startVertex = vertices.get(searchVertex(startKey));
        Vertex1<T, K> endVertex = vertices.get(searchVertex(endKey));

        if (startVertex == null || endVertex == null) {
            return "The start or end vertex does not exist in the graph.";
        }

        double[] distances = new double[vertices.size()];
        Arrays.fill(distances, Double.MAX_VALUE);
        distances[vertices.indexOf(startVertex)] = 0.0;

        PriorityQueue<Vertex1<T,K>> queue = new PriorityQueue<>((v1, v2) -> (int) (distances[vertices.indexOf(v1)] - distances[vertices.indexOf(v2)]));
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Vertex1<T, K> current = queue.poll();

            if (current == endVertex) {
                break;
            }

            for (Edge<K> edge : current.getEdges()) {
                Vertex1<T,K> neighbor = edge.getDestinationVertex();
                double weight = edge.getWeight();

                double newDistance = distances[vertices.indexOf(current)] + weight;
                if (newDistance < distances[vertices.indexOf(neighbor)]) {
                    distances[vertices.indexOf(neighbor)] = newDistance;
                    neighbor.setPredecessor(current);

                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        List<Vertex1<T, K>> path = new ArrayList<>();
        Vertex1<T, K> currentVertex = endVertex;
        while (currentVertex != null) {
            path.add(0, currentVertex);
            currentVertex = currentVertex.getPredecessor();
        }

        if (path.get(0) != startVertex) {
            return "There is no path from  leading vertex " + startKey + " to " + endKey + ".";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            Vertex1<T, K> vertex = path.get(i);
            result.append(vertex.getKey());
            if (i != path.size() - 1) {
                result.append(" -> ");
            }
        }

        return result.toString();
    }*/

    @Override
    public String dijkstra(K startKey, K endKey) {
        int startIdx = searchVertex(startKey);

        if (startIdx == -1) {
            return "The start vertex does not exist in the graph.";
        }

        double[] distances = new double[numVertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startIdx] = 0;

        PriorityQueue<Vertex1<T, K>> queue = new PriorityQueue<>((v1, v2) -> (int) (distances[vertices.indexOf(v1)] - distances[vertices.indexOf(v2)]));
        queue.add(vertices.get(startIdx));

        while (!queue.isEmpty()) {
            Vertex1<T, K> current = queue.poll();

            for (Edge edge : current.getEdges()) {
                Vertex1<T, K> neighbor = edge.getDestinationVertex();
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

        StringBuilder result = new StringBuilder();
        result.append("Shortest distances from vertex ").append(startKey).append(":\n");
        for (int i = 0; i < numVertices; i++) {
            result.append(vertices.get(i).getKey()).append(": ").append(distances[i]).append("\n");
        }

        return result.toString();
    }


    @Override
    public String prim(K startKey) {
        int startIdx = searchVertex(startKey);

        if (startIdx == -1) {
            return "The start vertex does not exist in the graph.";
        }

        boolean[] visited = new boolean[numVertices];
        double[] key = new double[numVertices];
        Arrays.fill(key, Integer.MAX_VALUE);
        key[startIdx] = 0;

        int[] pred = new int[numVertices];
        Arrays.fill(pred, -1);

        double totalCost = 0.0;

        for (int i = 0; i < numVertices; i++) {
            int uIdx = findMinKeyVertex(key, visited);
            if (uIdx == -1) {
                break;
            }

            visited[uIdx] = true;

            for (Edge edge : vertices.get(uIdx).getEdges()) {
                Vertex1<T, K> v = edge.getDestinationVertex();
                int vIdx = vertices.indexOf(v);

                double weight = edge.getWeight();

                if (!visited[vIdx] && weight < key[vIdx]) {
                    key[vIdx] = weight;
                    pred[vIdx] = uIdx;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Minimum Spanning Tree:\n");
        for (int i = 0; i < numVertices; i++) {
            if (pred[i] != -1) {
                Vertex1<T, K> u = vertices.get(pred[i]);
                Vertex1<T, K> v = vertices.get(i);

                result.append(u.getValue())
                        .append(" goes to ")
                        .append(v.getValue())
                        .append(" : ")
                        .append(key[i])
                        .append("\n");
            }
        }

        for (double cost : key) {
            if (cost != Integer.MAX_VALUE) {
                totalCost += cost;
            }
        }

        result.append("Total: ").append(totalCost);

        return result.toString();
    }

    /*@Override
    public String prim(K startKey) {
        Vertex1<T, K> startVertex = vertices.get(searchVertex(startKey));

        if (startVertex == null) {
            return "The start vertex does not exist in the graph.";
        }

        if (isDirected){
            return "Prim Method does not apply on Directed Graph";
        }else {
            PriorityQueue<Edge<K>> queue = new PriorityQueue<>((e1, e2) -> (int) (e1.getWeight() - e2.getWeight()));
            ArrayList<Vertex1<T, K>> visitedVertices = new ArrayList<>();
            StringBuilder result = new StringBuilder();

            visitedVertices.add(startVertex);

            for (Edge<K> edge : startVertex.getEdges()) {
                queue.add(edge);
            }

            while (!queue.isEmpty()) {
                Edge<K> minEdge = queue.poll();
                Vertex1<T, K> srcVertex = minEdge.getInitialVertex();
                Vertex1<T, K> destVertex = minEdge.getDestinationVertex();

                if (!visitedVertices.contains(destVertex)) {
                    visitedVertices.add(destVertex);
                    result.append(srcVertex.getKey()).append(" - ").append(destVertex.getKey()).append(", ");

                    for (Edge<K> edge : destVertex.getEdges()) {
                        if (!visitedVertices.contains(edge.getDestinationVertex())) {
                            queue.add(edge);
                        }
                    }
                }
            }

            if (visitedVertices.size() != vertices.size()) {
                return  "The graph is not connected.";
            }
            return result.toString();
        }
    }*/

    private int findMinKeyVertex(double[] key, boolean[] visited) {
        double minKey = Integer.MAX_VALUE;
        int minIdx = -1;

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && key[i] < minKey) {
                minKey = key[i];
                minIdx = i;
            }
        }

        return minIdx;
    }

    public ArrayList<Vertex1<T, K>> getVertices() {
        return this.vertices;
    }
}
