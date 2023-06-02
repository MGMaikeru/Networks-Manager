package model;
import exception.EmptyFieldException;
import java.util.*;

public class GraphList<T extends Comparable<T>,K extends Comparable <K>> extends Graph<T,K> {
    private final ArrayList<Vertex1> vertices;
    private final boolean isDirected;
    private int time;

    public GraphList(boolean isDirected) {
            this.isDirected = isDirected;
            this.vertices = new ArrayList<>();
    }

    @Override
    public void addVertex(K key, T value) {
        try {
            if (key.equals("")){
                throw new RuntimeException("There can't be vertex with null key");
            }else {
                if (searchVertex(key) == -1) {
                    vertices.add(new Vertex1(value,key));

                } else if (searchVertex(key) > 1) {
                    System.out.println("Vertex Key already exist");
                }
            }
        }catch (RuntimeException e){
            throw e;
        }
    }

    public ArrayList<Vertex1> getVertices(){
        return  this.vertices;
    }

    private int searchVertex(K key) {
        int foundedVertexInx = -1;
        for (int i= 0; i<vertices.size(); i++){
            if (vertices.get(i).getKey().equals(key)){
                foundedVertexInx = i;
            }
        }
        return foundedVertexInx;
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

    @Override
    public String addEdge(K srcKey, K destKey, double weight) {
        int srcIndex = searchVertex(srcKey);
        int destIndex = searchVertex(destKey);

        if (srcIndex == destIndex){
            return "You cannot add a loop in an undirected graph!";
        }

        if (srcIndex == -1 || destIndex == -1) {
            return "One or both vertices do not exist";
        }

        Vertex1<T, K> srcVertex = vertices.get(srcIndex);
        Vertex1<T, K> destVertex = vertices.get(destIndex);

        if (srcVertex.searchAdjacent(destVertex)) {
            return "These vertices are already connected";
        }

        Edge<T> edge = new Edge<>(srcVertex, destVertex, weight);
        srcVertex.addEdge(edge);
        srcVertex.addAdjacent(destVertex, weight);

        if (!isDirected) {
            Edge<T> reverseEdge = new Edge<>(destVertex, srcVertex, weight);
            destVertex.addEdge(reverseEdge);
            destVertex.addAdjacent(srcVertex, weight);
        }

        return "Vertices successfully connected";
    }

    @Override
    public String bfs(K key) throws EmptyFieldException {
        Vertex1<T,K> toSearch = vertices.get(searchVertex(key));
        Queue<Vertex1<T,K>> queue= new Queue<>();
        String msg = "";

        if (toSearch == null){
            return "This value don't exist in the graph";
        }

        for (Vertex1<T,K> vertex: vertices) {
            vertex.setColor(0);
            vertex.setPredecessor(null);
            vertex.setDInit(0);
        }

        toSearch.setColor(1);
        queue.enqueue(new Node1<>(toSearch));

        while (!queue.isEmpty()){
            Vertex1<T,K> u = queue.dequeue();
            for (Vertex1 v: u.getAdjacentVertices()) {
                if (v.getColor() == 0) {
                    v.setColor(1);
                    v.setDInit(u.getDInit() + 1);
                    v.setPredecessor(u);
                    queue.enqueue(new Node1<>(v));
                }
            }
            u.setColor(2);
        }
        for (Vertex1 vertex: vertices) {
            msg += vertex.getKey() + " " + vertex.getDInit() + " away.\n";
        }
        return msg;
    }

    @Override
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

            for (Edge<T> edge : current.getEdges()) {
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
    }

    @Override
    public String prim(K startKey) {
        Vertex1<T, K> startVertex = vertices.get(searchVertex(startKey));

        if (startVertex == null) {
            return "The start vertex does not exist in the graph.";
        }

        if (isDirected){
            return "Prim Method does not apply on Directed Graph";
        }else {
            PriorityQueue<Edge<T>> queue = new PriorityQueue<>((e1, e2) -> (int) (e1.getWeight() - e2.getWeight()));
            ArrayList<Vertex1<T, K>> visitedVertices = new ArrayList<>();
            StringBuilder result = new StringBuilder();

            visitedVertices.add(startVertex);

            for (Edge<T> edge : startVertex.getEdges()) {
                queue.add(edge);
            }

            while (!queue.isEmpty()) {
                Edge<T> minEdge = queue.poll();
                Vertex1<T, K> srcVertex = minEdge.getInitialVertex();
                Vertex1<T, K> destVertex = minEdge.getDestinationVertex();

                if (!visitedVertices.contains(destVertex)) {
                    visitedVertices.add(destVertex);
                    result.append(srcVertex.getKey()).append(" - ").append(destVertex.getKey()).append(", ");

                    for (Edge<T> edge : destVertex.getEdges()) {
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
    }

    public boolean searchSpecificAdjacent(K value1, K value2){
        Vertex1<T, K> originVertex = vertices.get(searchVertex(value1));
        Vertex1<T,K> destinationVertex = vertices.get(searchVertex(value2));
        return originVertex.searchAdjacent(destinationVertex);
    }
}

