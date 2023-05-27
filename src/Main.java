import exception.EmptyFieldException;
import model.*;

public class Main {
    private GraphList graph;
    private GraphForMatrix graph2;
    private Heap<Integer> priorityQueue;
    private Vertex1[] vertices;
    public Main(){
        graph = new GraphList(true);
        graph2 = new GraphForMatrix<>(false);
        priorityQueue = new Heap<>();
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.graph2.addVertex("a", "a");
        m.graph2.addVertex("b", "b");
        m.graph2.addVertex("c", "c");
        m.graph2.addVertex("d", "d");
        m.graph2.addVertex("e", "e");
        m.graph2.addVertex("z", "z");
        m.graph2.addEdge("a", "c", 2);
        m.graph2.addEdge("c", "b", 1);
        m.graph2.addEdge("d", "e", 2);
        m.graph2.addEdge("c", "d", 8);
        m.graph2.addEdge("e", "z", 3);
        m.graph2.addEdge("a", "b", 4);
        m.graph2.addEdge("a", "d", 5);
        m.graph2.addEdge("d", "z", 6);
        m.graph2.addEdge("c", "e", 10);
        //m.graph2.printGraph();
        m.graph2.dijkstra("a");
        /*m.priorityQueue.minHeapInsert(4);
        m.priorityQueue.minHeapInsert(2);
        m.priorityQueue.minHeapInsert(7);
        m.priorityQueue.minHeapInsert(5);
        m.priorityQueue.minHeapInsert(1);
        System.out.println(m.priorityQueue.printArray());*/
    }
}
