    package model;
    import exception.EmptyFieldException;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.PriorityQueue;

    public class GraphList<T extends Comparable<T>> extends Graph<T> {
        private final ArrayList<Vertex> vertices;
        private final boolean isDirected;
        private int time;

        public GraphList(boolean isDirected) {
            this.isDirected = isDirected;
            this.vertices = new ArrayList<>();
        }

        @Override
        public void addVertex(T value) {
            if (searchVertex(value) == null) {
                vertices.add(new Vertex(value));
            }
        }

        @Override
        public String addEdge(T origin, T destination, double weight) {
            Vertex<T> originVertex = searchVertex(origin);
            Vertex<T> destinationVertex = searchVertex(destination);

            if (origin.equals(destination) && !isDirected){
                return "You cannot add a loop in an undirected graph!";
            }

            if (originVertex != null && destinationVertex != null) {
                boolean isAdded = originVertex.addAdjacent(destinationVertex, weight);
                if (isAdded &&!isDirected){
                    destinationVertex.addAdjacent(originVertex, weight);
                }else{
                    return "These vertexes are already connected";
                }
            }

            return "Vertices successfully connected";
        }

        @Override
        public List<T> getAdjacent(T value) {
            Vertex<T> vertex = searchVertex(value);
            List<T> adjacent = new ArrayList<>();
            if (vertex != null) {
                for (Vertex<T> connection : vertex.getAdjacentVertices()) {
                    adjacent.add(connection.getValue());
                }
                return adjacent;
            }
            return null;
        }

        @Override
        public String bfs(T value) throws EmptyFieldException {
            Vertex<T> toSearch = searchVertex(value);
            Queue<Vertex<T>> queue= new Queue<>();
            String msg = "";
            if (toSearch == null){
                return "This value don't exist in the graph";
            }
            for (Vertex<T> vertex: vertices) {
                vertex.setColor(0);
                vertex.setPredecessor(null);
                vertex.setDInit(0);
            }
            toSearch.setColor(1);
            queue.enqueue(new Node1<>(toSearch));
            while (!queue.isEmpty()){
                Vertex<T> u = queue.dequeue();

                for (Vertex v: u.getAdjacentVertices()) {
                    if (v.getColor() == 0) {
                        v.setColor(1);
                        v.setDInit(u.getDInit() + 1);
                        v.setPredecessor(u);
                        queue.enqueue(new Node1<>(v));
                    }
                }
                u.setColor(2);
            }
            for (Vertex vertex: vertices) {
                msg += vertex.getValue() + " " + vertex.getDInit() + " away.\n";
            }
            return msg;
        }

        @Override
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

        public boolean searchSpecificAdjacent(T value1, T value2){
            Vertex<T> originVertex = searchVertex(value1);
            Vertex<T> destinationVertex = searchVertex(value2);
            return originVertex.searchAdjacent(destinationVertex);
        }

        public Vertex<T> searchVertex(T valor) {
            for (Vertex<T> vertex : vertices) {
                if (vertex.getValue().equals(valor)) {
                    return vertex;
                }
            }
            return null;
        }

        public void dijkstra(T startKey) {
            Vertex<T> initialVertex = searchVertex(startKey);

            if (initialVertex == null) {
                System.out.println("The start vertex does not exist in the graph.");
                return;
            }

            double[] distances = new double[vertices.size()];
            Arrays.fill(distances, Double.MAX_VALUE);
            distances[vertices.indexOf(initialVertex)] = 0.0;

            PriorityQueue<Vertex<T>> queue = new PriorityQueue<>((v1, v2) -> (int) (distances[vertices.indexOf(v1)] - distances[vertices.indexOf(v2)]));
            queue.add(initialVertex);

            while (!queue.isEmpty()) {
                Vertex<T> current = queue.poll();

                for (Edge<T> edge : current.getEdges()) {
                    Vertex<T> neighbor = edge.getFinalVertex();
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
        }


        public void prim(T startKey) {
            Vertex<T> startVertex = searchVertex(startKey);

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
                Vertex<T> u = vertices.get(uIdx);

                for (Edge<T> edge : u.getEdges()) {
                    Vertex<T> v = edge.getFinalVertex();
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
                    Vertex<T> u = vertices.get(pred[i]);
                    Vertex<T> v = vertices.get(i);

                    System.out.println(u.getValue() + " goes to " + v.getValue() + " : " + key[i]);
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
    }
