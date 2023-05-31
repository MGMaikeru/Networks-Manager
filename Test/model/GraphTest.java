package model;
import exception.EmptyFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    private GraphList<String> graphList;
    private GraphForMatrix<String, String> matrixGraph;

    public void setup1(){
        this.graphList = new GraphList(false);
    }

    public void setup2(){
        this.graphList = new GraphList(true);
    }

    @Test
    public void verifyEdgeConnection1(){
        //The adjacency of the edge in a directed graph is checked.
        setup1();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addEdge(1, 2, 3);
        graphList.addEdge(1, 3, 5);
        graphList.addEdge(3, 2, 1);
        assertEquals("These vertexes are already connected", graphList.addEdge(1, 2, 3));
    }

    @Test
    public void verifyEdgeConnection2(){
        //The adjacency of the edge in a directed graph is checked.
        setup2();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addEdge(1, 2, 1);
        graphList.addEdge(1, 3, 2);
        graphList.addEdge(3, 2, 3);
        assertTrue(graphList.searchSpecificAdjacent(1, 2));
        assertFalse(graphList.searchSpecificAdjacent(2, 1));
    }

    @Test
    public void verifyEdgeConnection3(){
        //Trying to add a loop in an undirected graph
        setup1();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addEdge(1, 2, 4);
        graphList.addEdge(1, 3, 6);
        graphList.addEdge(3, 2, 1);
        assertEquals("You cannot add a loop in an undirected graph!", graphList.addEdge(1, 1, 3));
    }

    @Test
    public void verifyAdjacent1(){
        //Directed graph connections are checked for correctness
        setup2();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addEdge(1, 2, 8);
        graphList.addEdge(2, 3, 1);
        graphList.addEdge(3, 1, 2);
        assertEquals(2, graphList.getAdjacent(1).get(0));
        assertEquals(3, graphList.getAdjacent(2).get(0));
        assertEquals(1, graphList.getAdjacent(3).get(0));
    }

    @Test
    public void verifyAdjacent2(){
        //Adjacency of a single vertex is checked
        setup1();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addVertex(4);
        graphList.addEdge(1, 2, 0);
        graphList.addEdge(1, 3, 1);
        graphList.addEdge(2, 3,4 );
        assertTrue(graphList.getAdjacent(4).isEmpty());

    }

    @Test
    public void verifyAdjacent3(){
        //A node that does not exist in the graph. Verify that the method returns null.
        setup1();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addEdge(1, 2, 3);
        graphList.addEdge(1, 3, 1);
        graphList.addEdge(2, 3, 2);
        assertNull(graphList.getAdjacent(4));
    }

    @Test
    public void bsfTest1(){
        // It is verified that the distances with respect to I correspond to the graph.
        setup1();
        graphList.addVertex("A");
        graphList.addVertex("B");
        graphList.addVertex("C");
        graphList.addVertex("D");
        graphList.addVertex("E");
        graphList.addVertex("F");
        graphList.addVertex("G");
        graphList.addVertex("H");
        graphList.addVertex("I");
        graphList.addVertex("J");
        graphList.addEdge("A", "C", 5);
        graphList.addEdge("E", "A", 1);
        graphList.addEdge("I", "E", 7);
        graphList.addEdge("I", "B", 4);
        graphList.addEdge("E", "J", 1);
        graphList.addEdge("J", "G", 12);
        graphList.addEdge("G", "C", 6);
        graphList.addEdge("C", "F", 9);
        graphList.addEdge("C", "D", 11);
        graphList.addEdge("F", "D", 17);
        graphList.addEdge("D", "H", 10);
        String expected = "A 2 away.\n" +
                "B 1 away.\n" +
                "C 3 away.\n" +
                "D 4 away.\n" +
                "E 1 away.\n" +
                "F 4 away.\n" +
                "G 3 away.\n" +
                "H 5 away.\n" +
                "I 0 away.\n" +
                "J 2 away.\n";
        try {
            assertEquals(expected, graphList.bfs("I"));
        } catch (EmptyFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void bsfTest2(){
        // A graph with no connections, the distance between vertices is expected to be 0.
        setup1();
        graphList.addVertex("A");
        graphList.addVertex("B");
        graphList.addVertex("C");
        graphList.addVertex("D");
        graphList.addVertex("E");
        graphList.addVertex("F");
        graphList.addVertex("G");
        graphList.addVertex("H");
        String expected = "A 0 away.\n" +
                "B 0 away.\n" +
                "C 0 away.\n" +
                "D 0 away.\n" +
                "E 0 away.\n" +
                "F 0 away.\n" +
                "G 0 away.\n" +
                "H 0 away.\n";
        try {
            assertEquals(expected, graphList.bfs("C"));
        } catch (EmptyFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void bsfTest3(){
        //BSF is started with a value that is not in the network
        setup2();
        graphList.addVertex("A");
        graphList.addVertex("B");
        graphList.addVertex("C");
        graphList.addVertex("D");
        graphList.addVertex("E");
        graphList.addVertex("F");
        graphList.addVertex("G");
        graphList.addVertex("H");
        graphList.addEdge("G", "A", 3);
        graphList.addEdge("C", "D", 2);
        graphList.addEdge("E", "H", 1);
        try {
            assertEquals("This value don't exist in the graph", graphList.bfs("z"));
        } catch (EmptyFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void dsfTest1(){
        //It is verified that the distances with respect to I correspond to the graph.
        setup2();
        graphList.addVertex("O");
        graphList.addVertex("P");
        graphList.addVertex("Q");
        graphList.addVertex("R");
        graphList.addVertex("S");
        graphList.addVertex("T");
        graphList.addVertex("U");
        graphList.addVertex("V");
        graphList.addVertex("W");
        graphList.addVertex("X");
        graphList.addVertex("Y");
        graphList.addVertex("Z");
        graphList.addEdge("P", "Q", 1);
        graphList.addEdge("Q", "W", 2);
        graphList.addEdge("W", "P", 3);
        graphList.addEdge("O", "Y", 4);
        graphList.addEdge("O", "T", 5);
        graphList.addEdge("O", "S", 6);
        graphList.addEdge("Y", "T", 7);
        graphList.addEdge("T", "X", 8);
        graphList.addEdge("X", "S", 9);
        graphList.addEdge("R", "X", 10);
        graphList.addEdge("R", "Z", 11);
        graphList.addEdge("V", "S", 12);
        graphList.addEdge("V", "R", 13);
        String expected = "O d: 1 f: 10\n" + "P d: 11 f: 16\n" + "Q d: 12 f: 15\n" +
                "R d: 17 f: 20\n" + "S d: 5 f: 6\n" + "T d: 3 f: 8\n" + "U d: 21 f: 22\n" +
                "V d: 23 f: 24\n" + "W d: 13 f: 14\n" + "X d: 4 f: 7\n" + "Y d: 2 f: 9\n" +
                "Z d: 18 f: 19\n";
        graphList.dfs();
        assertEquals(expected, graphList.checkDfs());

    }

    @Test
    public void dsfTest2(){
        //We have a graph with no connections, D and F are expected to increase by 1 at each vertex.
        setup2();
        graphList.addVertex("A");
        graphList.addVertex("B");
        graphList.addVertex("C");
        graphList.addVertex("D");
        graphList.addVertex("E");
        graphList.addVertex("F");
        graphList.addVertex("G");
        graphList.addVertex("H");
        String expected = "A d: 1 f: 2\n" + "B d: 3 f: 4\n" + "C d: 5 f: 6\n" + "D d: 7 f: 8\n" +
                "E d: 9 f: 10\n" + "F d: 11 f: 12\n" + "G d: 13 f: 14\n" + "H d: 15 f: 16\n";
        graphList.dfs();
        assertEquals(expected, graphList.checkDfs());
    }

    @Test
    public void dsfTest3(){
        //Cyclic directed graph is used, check that the D and F of each vertex prove it.
        setup2();
        graphList.addVertex("A");
        graphList.addVertex("B");
        graphList.addVertex("C");
        graphList.addVertex("D");
        graphList.addVertex("E");
        graphList.addVertex("F");

        graphList.addEdge("A", "B", 1);
        graphList.addEdge("B", "C", 2);
        graphList.addEdge("C", "D", 3);
        graphList.addEdge("D", "E", 4);
        graphList.addEdge("E", "F", 5);
        graphList.addEdge("F", "A", 6);
        String expected = "A d: 1 f: 12\n" + "B d: 2 f: 11\n" + "C d: 3 f: 10\n" + "D d: 4 f: 9\n" +
                "E d: 5 f: 8\n" + "F d: 6 f: 7\n";
        graphList.dfs();
        assertEquals(expected, graphList.checkDfs());
    }
}