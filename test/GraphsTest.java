import exception.EmptyFieldException;
import model.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GraphsTest {
    private GraphList<String,String> adjacentGraphList;
    private GraphForMatrix<String,String> matrixGraph;

    public void setupStage1(){
        this.adjacentGraphList = new GraphList<>(false);
        this.matrixGraph = new GraphForMatrix<>(false);
    }

    public void setupStage2(){
        this.adjacentGraphList = new GraphList<>(true);
        this.matrixGraph = new GraphForMatrix<>(true);
    }

    public void setupStage3(){
        this.adjacentGraphList = new GraphList<>(false);
        this.matrixGraph = new GraphForMatrix<>(false);
    }

    @Test
    public void verifyAddedVertex1(){
        setupStage1();

        adjacentGraphList.addVertex("A", "A");
        adjacentGraphList.addVertex("B", "B");
        adjacentGraphList.addVertex("C", "C");


        List<String> expectedVertices = new ArrayList<>();
        expectedVertices.add("A");
        expectedVertices.add("B");
        expectedVertices.add("C");
        List<String> actualVertices = new ArrayList<>();
        for (Vertex1<String, String> vertex : adjacentGraphList.getVertices()) {
            actualVertices.add(vertex.getKey());
        }

        assertEquals(expectedVertices, actualVertices);
    }

    @Test
    public void verifyAddedVertex2(){
        setupStage1();

        matrixGraph.addVertex("A", "A");
        matrixGraph.addVertex("B", "B");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            matrixGraph.addVertex("", "C");
        });

        String expectedMessage = "There can't be vertex with null key";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void verifyAddedEdges1(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A");
        adjacentGraphList.addVertex("B", "B");
        adjacentGraphList.addVertex("C", "C");

        String result1 = adjacentGraphList.addEdge("A", "B", 1);
        String result2 = adjacentGraphList.addEdge("A", "C", 1);

        assertEquals("Vertices successfully connected", result1);
        assertEquals("Vertices successfully connected", result2);
        assertTrue(adjacentGraphList.getAdjacent("A").contains("B"));
        assertTrue(adjacentGraphList.getAdjacent("A").contains("C"));
    }

    @Test
    public void verifyAddedEdges2(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A");
        adjacentGraphList.addVertex("B", "B");
        adjacentGraphList.addVertex("C", "C");

        String result1 = adjacentGraphList.addEdge("A", "B", 1);
        String result2 = adjacentGraphList.addEdge("A", "C", 1);
        String result3 = adjacentGraphList.addEdge("B", "C", 1);
        String result4 = adjacentGraphList.addEdge("A", "C", 1);

        assertEquals("These vertices are already connected", result4);
        assertTrue(adjacentGraphList.getAdjacent("A").contains("B"));
        assertTrue(adjacentGraphList.getAdjacent("A").contains("C"));
    }

    @Test
    public void verifyAddedEdges3(){
        setupStage3();
        adjacentGraphList.addVertex("A", "A");
        adjacentGraphList.addVertex("B", "B");
        adjacentGraphList.addVertex("C", "C");

        String result1 = adjacentGraphList.addEdge("A", "B", 1);
        String result2 = adjacentGraphList.addEdge("A", "C", 1);
        String result3 = adjacentGraphList.addEdge("B", "C", 1);
        String result4 = adjacentGraphList.addEdge("B", "B", 1);

        assertEquals("You cannot add a loop in an undirected graph!", result4);
    }

    @Test
    public void verifyBFSMethod1(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A");
        adjacentGraphList.addVertex("B", "B");
        adjacentGraphList.addVertex("C", "C");
        adjacentGraphList.addVertex("D", "D");
        adjacentGraphList.addVertex("E", "E");

        adjacentGraphList.addEdge("A", "B", 1);
        adjacentGraphList.addEdge("B", "C", 1);
        adjacentGraphList.addEdge("C", "D", 1);
        adjacentGraphList.addEdge("D", "E", 1);

        try {
            System.out.println(adjacentGraphList.bfs("A"));
            assertTrue(adjacentGraphList.bfs("A").contains("B"));
            assertTrue(adjacentGraphList.bfs("A").contains("C"));
            assertTrue(adjacentGraphList.bfs("A").contains("D"));
            assertTrue(adjacentGraphList.bfs("A").contains("E"));
        } catch (EmptyFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verifyBFSMethod2(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A");
        adjacentGraphList.addVertex("B", "B");
        adjacentGraphList.addVertex("C", "C");
        adjacentGraphList.addVertex("D", "D");
        adjacentGraphList.addVertex("E", "E");

        try {
            assertTrue(adjacentGraphList.bfs("A").contains(""));
        } catch (EmptyFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verifyBFSMethod3(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A");
        adjacentGraphList.addVertex("B", "B");
        adjacentGraphList.addVertex("C", "C");
        adjacentGraphList.addVertex("D", "D");
        adjacentGraphList.addVertex("E", "E");
        adjacentGraphList.addVertex("F", "F");
        adjacentGraphList.addVertex("G", "G");
        adjacentGraphList.addVertex("H", "H");
        adjacentGraphList.addVertex("I", "I");
        adjacentGraphList.addVertex("J", "J");
        adjacentGraphList.addVertex("K", "K");

        adjacentGraphList.addEdge("A", "A", 1);
        adjacentGraphList.addEdge("A", "B", 1);
        adjacentGraphList.addEdge("A", "C", 1);
        adjacentGraphList.addEdge("B", "C", 1);
        adjacentGraphList.addEdge("B", "D", 1);
        adjacentGraphList.addEdge("C", "D", 1);
        adjacentGraphList.addEdge("D", "J", 1);
        adjacentGraphList.addEdge("J", "E", 1);
        adjacentGraphList.addEdge("J", "H", 1);

        adjacentGraphList.dijkstra("A","B");
    }

    @Test
    public void verifyDijkstraMethod1(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A");
        adjacentGraphList.addVertex("B", "B");
        adjacentGraphList.addVertex("C", "C");
        adjacentGraphList.addVertex("D", "D");
        adjacentGraphList.addVertex("E", "E");
        adjacentGraphList.addVertex("F", "F");

        adjacentGraphList.addEdge("A", "B", 1);
        adjacentGraphList.addEdge("A", "C", 1);
        adjacentGraphList.addEdge("B", "D", 1);
        adjacentGraphList.addEdge("B", "F", 1);
        adjacentGraphList.addEdge("C", "D", 1);
        adjacentGraphList.addEdge("C", "E", 1);
        adjacentGraphList.addEdge("D", "E", 1);
        adjacentGraphList.addEdge("D", "F", 1);
        adjacentGraphList.addEdge("E", "F", 1);

        assertEquals("A -> B -> F", adjacentGraphList.dijkstra("A","F"));
    }

    @Test
    public void verifyDijkstraMethod2(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A");
        adjacentGraphList.addVertex("B", "B");
        adjacentGraphList.addVertex("C", "C");
        adjacentGraphList.addVertex("D", "D");
        adjacentGraphList.addVertex("E", "E");
        adjacentGraphList.addVertex("F", "F");

        adjacentGraphList.addEdge("A", "B", 1);
        adjacentGraphList.addEdge("A", "C", 1);
        adjacentGraphList.addEdge("B", "D", 1);
        adjacentGraphList.addEdge("C", "D", 1);
        adjacentGraphList.addEdge("D", "A", 1);
        adjacentGraphList.addEdge("E", "F", 1);

        assertEquals("There is no path from  leading vertex A to F.", adjacentGraphList.dijkstra("A","F"));
    }

    @Test
    public void verifyDijkstraMethod3(){
        setupStage1();
        adjacentGraphList.addVertex("P", "P");
        adjacentGraphList.addVertex("Q", "Q");
        adjacentGraphList.addVertex("R", "R");
        adjacentGraphList.addVertex("S", "S");
        adjacentGraphList.addVertex("T", "T");

        adjacentGraphList.addEdge("P", "Q", 2);
        adjacentGraphList.addEdge("P", "R", 3);
        adjacentGraphList.addEdge("Q", "S", 4);
        adjacentGraphList.addEdge("R", "S", 1);
        adjacentGraphList.addEdge("S", "T", 5);

        assertEquals("P -> R -> S -> T", adjacentGraphList.dijkstra("P","T"));
    }

    @Test
    public void verifyPrimMethod1() {
        setupStage1();
        adjacentGraphList.addVertex("A", "A");
        adjacentGraphList.addVertex("B", "B");
        adjacentGraphList.addVertex("C", "C");
        adjacentGraphList.addVertex("D", "D");
        adjacentGraphList.addVertex("E", "E");
        adjacentGraphList.addVertex("F", "F");

        adjacentGraphList.addEdge("A", "B", 5);
        adjacentGraphList.addEdge("A", "C", 3);
        adjacentGraphList.addEdge("B", "C", 2);
        adjacentGraphList.addEdge("B", "D", 4);
        adjacentGraphList.addEdge("C", "D", 1);

        String expected = "A - C, C - D, B - C, B - D, ";
        String result = adjacentGraphList.prim("C");

        assertEquals(expected, result);
    }

    @Test
    public void verifyPrimMethod2(){
        setupStage2();
        adjacentGraphList.addVertex("P", "P");
        adjacentGraphList.addVertex("Q", "Q");
        adjacentGraphList.addVertex("R", "R");
        adjacentGraphList.addVertex("S", "S");
        adjacentGraphList.addVertex("T", "T");

        adjacentGraphList.addEdge("P", "Q", 3);
        adjacentGraphList.addEdge("Q", "R", 8);
        adjacentGraphList.addEdge("S", "R", 10);
        adjacentGraphList.addEdge("T", "R", 7);


        String expected = "The graph is not connected.";//A pesar de que lo pase, no esta bien
        //Tambien hay que cambiar el prim para agregar esta excepción UnsupportedOperationException
        String result = adjacentGraphList.prim("R");

        assertEquals(expected, result);
    }

    @Test
    public void verifyPrimMethod3(){
        setupStage2();
        adjacentGraphList.addVertex("P", "P");
        adjacentGraphList.addVertex("Q", "Q");
        adjacentGraphList.addVertex("R", "R");
        adjacentGraphList.addVertex("S", "S");
        adjacentGraphList.addVertex("T", "T");

        adjacentGraphList.addEdge("P", "Q", 4);
        adjacentGraphList.addEdge("Q", "R", 3);
        adjacentGraphList.addEdge("R", "S", 2);
        adjacentGraphList.addEdge("S", "T", 6);
        adjacentGraphList.addEdge("U", "T", 4);
        adjacentGraphList.addEdge("U", "U", 5);


        String expected = "R - S, S - T, T U - T, Q - R, ";
        String result = adjacentGraphList.prim("R");

        assertEquals(expected, result);
    }

}
