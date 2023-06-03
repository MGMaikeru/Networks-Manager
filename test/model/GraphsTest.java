package model;

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

        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);


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

        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            adjacentGraphList.addVertex("", "C", 1, 1);
        });

        String expectedMessage = "There can't be vertex with null key";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void verifyAddedEdges1(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);

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
        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);

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
        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);

        String result1 = adjacentGraphList.addEdge("A", "B", 1);
        String result2 = adjacentGraphList.addEdge("A", "C", 1);
        String result3 = adjacentGraphList.addEdge("B", "C", 1);
        String result4 = adjacentGraphList.addEdge("B", "B", 1);

        assertEquals("You cannot add a loop in an undirected graph!", result4);
    }

    @Test
    public void verifyBFSMethod1(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);
        adjacentGraphList.addVertex("D", "D", 1, 1);
        adjacentGraphList.addVertex("E", "E", 1, 1);

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
        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);
        adjacentGraphList.addVertex("D", "D", 1, 1);
        adjacentGraphList.addVertex("E", "E", 1, 1);

        try {
            assertTrue(adjacentGraphList.bfs("A").contains(""));
        } catch (EmptyFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verifyBFSMethod3(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);
        adjacentGraphList.addVertex("D", "D", 1, 1);
        adjacentGraphList.addVertex("E", "E", 1, 1);
        adjacentGraphList.addVertex("F", "F", 1, 1);
        adjacentGraphList.addVertex("G", "G", 1, 1);
        adjacentGraphList.addVertex("H", "H", 1, 1);
        adjacentGraphList.addVertex("I", "I", 1, 1);
        adjacentGraphList.addVertex("J", "J", 1, 1);
        adjacentGraphList.addVertex("K", "K", 1, 1);

        adjacentGraphList.addEdge("A", "A", 1);
        adjacentGraphList.addEdge("A", "B", 1);
        adjacentGraphList.addEdge("A", "C", 1);
        adjacentGraphList.addEdge("B", "C", 1);
        adjacentGraphList.addEdge("B", "D", 1);
        adjacentGraphList.addEdge("C", "D", 1);
        adjacentGraphList.addEdge("D", "J", 1);
        adjacentGraphList.addEdge("J", "E", 1);
        adjacentGraphList.addEdge("J", "H", 1);

        try {
            assertTrue(adjacentGraphList.bfs("D").contains("A"));
            assertTrue(adjacentGraphList.bfs("D").contains("B"));
            assertTrue(adjacentGraphList.bfs("D").contains("C"));
            assertTrue(adjacentGraphList.bfs("D").contains("D"));
            assertTrue(adjacentGraphList.bfs("D").contains("E"));
        } catch (EmptyFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verifyDijkstraMethod1(){
        setupStage1();
        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);
        adjacentGraphList.addVertex("D", "D", 1, 1);
        adjacentGraphList.addVertex("E", "E", 1, 1);
        adjacentGraphList.addVertex("F", "F", 1, 1);

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
        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);
        adjacentGraphList.addVertex("D", "D", 1, 1);
        adjacentGraphList.addVertex("E", "E", 1, 1);
        adjacentGraphList.addVertex("F", "F", 1, 1);

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
        adjacentGraphList.addVertex("P", "P", 1, 1);
        adjacentGraphList.addVertex("Q", "Q", 1, 1);
        adjacentGraphList.addVertex("R", "R", 1, 1);
        adjacentGraphList.addVertex("S", "S", 1, 1);
        adjacentGraphList.addVertex("T", "T", 1, 1);

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
        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);
        adjacentGraphList.addVertex("D", "D", 1, 1);

        adjacentGraphList.addEdge("A", "B", 5);
        adjacentGraphList.addEdge("A", "C", 3);
        adjacentGraphList.addEdge("B", "C", 2);
        adjacentGraphList.addEdge("B", "D", 4);
        adjacentGraphList.addEdge("C", "D", 1);

        String expected = "A - C, C - D, C - B, ";
        String result = adjacentGraphList.prim("A");

        assertEquals(expected, result);
    }

    @Test
    public void verifyPrimMethod2(){
        setupStage2();
        adjacentGraphList.addVertex("P", "P", 1, 1);
        adjacentGraphList.addVertex("Q", "Q", 1, 1);
        adjacentGraphList.addVertex("R", "R", 1, 1);
        adjacentGraphList.addVertex("S", "S", 1, 1);
        adjacentGraphList.addVertex("T", "T", 1, 1);

        adjacentGraphList.addEdge("P", "Q", 3);
        adjacentGraphList.addEdge("Q", "R", 8);
        adjacentGraphList.addEdge("S", "R", 10);
        adjacentGraphList.addEdge("T", "R", 7);


        String expected = "Prim Method does not apply on Directed Graph";
        String result = adjacentGraphList.prim("R");

        assertEquals(expected, result);
    }

    @Test
    public void verifyPrimMethod3(){
        setupStage3();
        adjacentGraphList.addVertex("A", "A", 1, 1);
        adjacentGraphList.addVertex("B", "B", 1, 1);
        adjacentGraphList.addVertex("C", "C", 1, 1);
        adjacentGraphList.addVertex("D", "D", 1, 1);
        adjacentGraphList.addVertex("E", "E", 1, 1);
        adjacentGraphList.addVertex("F", "F", 1, 1);
        adjacentGraphList.addVertex("G", "G", 1, 1);
        adjacentGraphList.addVertex("H", "H", 1, 1);
        adjacentGraphList.addVertex("I", "I", 1, 1);
        adjacentGraphList.addVertex("J", "J", 1, 1);
        adjacentGraphList.addVertex("K", "K", 1, 1);
        adjacentGraphList.addVertex("L", "L", 1, 1);
        adjacentGraphList.addVertex("M", "M", 1, 1);
        adjacentGraphList.addVertex("N", "N", 1, 1);
        adjacentGraphList.addVertex("O", "O", 1, 1);


        adjacentGraphList.addEdge("A", "B", 5);
        adjacentGraphList.addEdge("A", "C", 2);
        adjacentGraphList.addEdge("B", "D", 4);
        adjacentGraphList.addEdge("B", "E", 3);
        adjacentGraphList.addEdge("C", "F", 6);
        adjacentGraphList.addEdge("C", "G", 1);
        adjacentGraphList.addEdge("D", "H", 8);
        adjacentGraphList.addEdge("D", "I", 9);
        adjacentGraphList.addEdge("E", "J", 7);
        adjacentGraphList.addEdge("E", "K", 6);
        adjacentGraphList.addEdge("F", "L", 3);
        adjacentGraphList.addEdge("G", "M", 5);
        adjacentGraphList.addEdge("H", "N", 2);
        adjacentGraphList.addEdge("I", "O", 4);
        adjacentGraphList.addEdge("J", "K", 2);


        String expected = "C - G, C - A, A - B, B - E, B - D, G - M, E - K, K - J, C - F, F - L, D - H, H - N, D - I, I - O, ";
        String result = adjacentGraphList.prim("C");

        assertEquals(expected, result);
    }

}
