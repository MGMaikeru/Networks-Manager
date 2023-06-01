package model;
import exception.EmptyFieldException;

import java.util.ArrayList;
import java.util.List;

public class GraphList<T extends Comparable<T>> extends Graph<T> {
    private final ArrayList<Vertex> vertices;
    private final boolean isDirected;
    private int time;

    public GraphList(boolean isDirected) {
        this.isDirected = isDirected;
        this.vertices = new ArrayList<>();
    }

    @Override
    public void addVertex(T value, double x, double y) {
        if (searchVertex(value) == null) {
            vertices.add(new Vertex(value, x, y));
        }
    }

    @Override
    public String addEdge(T origin, T destination) {
        Vertex<T> originVertex = searchVertex(origin);
        Vertex<T> destinationVertex = searchVertex(destination);

        if (origin.equals(destination) && !isDirected){
            return "No se puede añadir un bucle en un grafo no digido!";
        }

        if (originVertex != null && destinationVertex != null) {
            boolean isAdded = originVertex.addAdjacent(destinationVertex);
            if (isAdded &&!isDirected){
                destinationVertex.addAdjacent(originVertex);
            }else{
                return "Estos vertices ya estan conectados";
            }
        }

        return "Vertices conectados exitosamente";
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

}
