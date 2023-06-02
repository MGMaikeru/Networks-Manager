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
    public void addVertex(K key, T value, double x, double y) {
        if (searchVertex(key) == -1) {
            vertices.add(new Vertex1(value,key, x, y));

        } else if (searchVertex(key) > 1) {
            System.out.println("Vertex Key already exist");
        }
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
        int origin = searchVertex(srcKey);
        int destination = searchVertex(destKey);

        if (origin != -1 && destination != -1){
            if (origin == destination && !isDirected){
                return "You cannot add a loop in an undirected graph!";
            }else {
                Vertex1<T, K> srcVertex = vertices.get(origin);
                Vertex1<T, K> destVertex = vertices.get(destination);

                srcVertex.addEdge(new Edge(srcVertex, destVertex, weight));
                boolean isAdded = srcVertex.addAdjacent(destVertex, weight);

                if (isDirected && isAdded){
                    destVertex.addAdjacent(srcVertex, weight);
                }else{
                    return "These vertexes are already connected";
                }
                return  "Vertices successfully connected";
            }
        }else {
            return  "Vertex does not exist";
        }
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

    /*@Override
    public void dijkstra(K startKey) {
        Vertex1<T, K> initialVertex = vertices.get(searchVertex(startKey));
        if (initialVertex == null) {
            System.out.println("The start vertex does not exist in the graph.");
            return;
        }

        double[] distances = new double[vertices.size()];
        Arrays.fill(distances, Double.MAX_VALUE);
        distances[vertices.indexOf(initialVertex)] = 0.0;

        PriorityQueue<Vertex1<T,K>> queue = new PriorityQueue<>((v1, v2) -> (int) (distances[vertices.indexOf(v1)] - distances[vertices.indexOf(v2)]));
        queue.add(initialVertex);

        while (!queue.isEmpty()) {
            Vertex1<T, K> current = queue.poll();

            for (Edge<T> edge : current.getEdges()) {
                Vertex1<T,K> neighbor = edge.getDestinationVertex();
                double weight = edge.getWeight();

                if (weight < distances[vertices.indexOf(neighbor)]) {
                    distances[vertices.indexOf(neighbor)] = weight;
                    neighbor.setPredecessor(current);

                    // Update the priority of the neighbor in the priority queue
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Print the shortest distances from the start vertex to all other vertices
        System.out.println("Shortest distances from vertex " + startKey + ":");
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println(vertices.get(i).getValue() + ": " + distances[i]);
        }
    }*/
    @Override
    public void dijkstra(K startKey, K endKey) {
        Vertex1<T, K> startVertex = vertices.get(searchVertex(startKey));
        Vertex1<T, K> endVertex = vertices.get(searchVertex(endKey));

        if (startVertex == null || endVertex == null) {
            System.out.println("The start or end vertex does not exist in the graph.");
            return;
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
            System.out.println("There is no path from vertex " + startKey + " to vertex " + endKey + ".");
            return;
        }

        for (int i = 0; i < path.size(); i++) {
            Vertex1<T, K> vertex = path.get(i);
            System.out.print(vertex.getKey());
            if (i != path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    @Override
    public void prim(K startKey) {
        Vertex1<T,K> startVertex = vertices.get(searchVertex(startKey));

        if (startVertex == null) {
            System.out.println("The start vertex does not exist in the graph.");
            return;
        }

        int numVertices = vertices.size();
        boolean[] visited = new boolean[numVertices];
        double[] key = new double[numVertices];
        Arrays.fill(key, Double.MAX_VALUE);
        key[vertices.indexOf(startVertex)] = 0.0;

        int[] pred = new int[numVertices];
        Arrays.fill(pred, -1);

        double totalCost = 0.0;

        for (int i = 0; i < numVertices; i++) {
            int uIdx = findMinKeyVertex(key, visited);
            if (uIdx == -1) {
                break;
            }

            visited[uIdx] = true;
            Vertex1<T,K> u = vertices.get(uIdx);

            for (Edge<T> edge : u.getEdges()) {
                Vertex1<T,K> v = edge.getDestinationVertex();
                int vIdx = vertices.indexOf(v);

                double weight = edge.getWeight();

                if (!visited[vIdx] && weight < key[vIdx]) {
                    key[vIdx] = weight;
                    pred[vIdx] = uIdx;
                }
            }
        }

        System.out.println("Minimum Spanning Tree:");
        for (int i = 0; i < numVertices; i++) {
            if (pred[i] != -1) {
                Vertex1<T,K> u = vertices.get(pred[i]);
                Vertex1<T,K> v = vertices.get(i);

                System.out.println(u.getKey() + " goes to " + v.getKey() + " : " + key[i]);
                totalCost += key[i];
            }
        }
        System.out.println("Total: " + totalCost);
    }
    private int findMinKeyVertex(double[] key, boolean[] visited) {
        double minKey = Integer.MAX_VALUE;
        int minIdx = -1;

        for (int i = 0; i < vertices.size(); i++) {
            if (!visited[i] && key[i] < minKey) {
                minKey = key[i];
                minIdx = i;
            }
        }

        return minIdx;
    }

    public boolean searchSpecificAdjacent(K value1, K value2){
        Vertex1<T, K> originVertex = vertices.get(searchVertex(value1));
        Vertex1<T,K> destinationVertex = vertices.get(searchVertex(value2));
        return originVertex.searchAdjacent(destinationVertex);
    }
}


    /*@Override
       public void dfs(){
           String msg = "";
           for (Vertex<T> vertex: vertices) {
               vertex.setColor(0);
               vertex.setPredecessor(null);
               vertex.setDInit(0);
            }
           this.time = 0;
           for (Vertex<T> vertex: vertices) {
               if (vertex.getColor() == 0)
                   dfs(vertex);
           }
       }
      private void dfs(Vertex<T> vertex){
           time ++;
           vertex.setDInit(time);
           vertex.setColor(1);
           for (Vertex<T> v: vertex.getAdjacentVertices()) {
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
            for (Vertex vertex: vertices) {
                msg += vertex.getValue() + " d: " + vertex.getDInit() + " f: " + vertex.getDEnd() + "\n";
            }
            return msg;
        }
*/
