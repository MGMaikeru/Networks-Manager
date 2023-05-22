package model;
import exception.EmptyFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphListTest {
    private GraphList graphList;

    public void setup1(){
        this.graphList = new GraphList(false);
    }

    public void setup2(){
        this.graphList = new GraphList(true);
    }

    @Test
    public void verifyEdgeConnection1(){
        //Se intenta agregar una arista que ya existe
        setup1();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addEdge(1, 2);
        graphList.addEdge(1, 3);
        graphList.addEdge(3, 2);
        assertEquals("Estos vertices ya estan conectados", graphList.addEdge(1, 2));
    }

    @Test
    public void verifyEdgeConnection2(){
        //Se comprueba que la adyacencia de la arista en un grafo dirgido
        setup2();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addEdge(1, 2);
        graphList.addEdge(1, 3);
        graphList.addEdge(3, 2);
        assertTrue(graphList.searchSpecificAdjacent(1, 2));
        assertFalse(graphList.searchSpecificAdjacent(2, 1));
    }

    @Test
    public void verifyEdgeConnection3(){
        //Se intenta agregar un bucle en un grafo no dirigido
        setup1();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addEdge(1, 2);
        graphList.addEdge(1, 3);
        graphList.addEdge(3, 2);
        assertEquals("No se puede añadir un bucle en un grafo no digido!", graphList.addEdge(1, 1));
    }

    @Test
    public void verifyAdjacent1(){
        //Se comprueba que las conexiones de el grafo dirigido sean correctas
        setup2();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addEdge(1, 2);
        graphList.addEdge(2, 3);
        graphList.addEdge(3, 1);
        assertEquals(2, graphList.getAdjacent(1).get(0));
        assertEquals(3, graphList.getAdjacent(2).get(0));
        assertEquals(1, graphList.getAdjacent(3).get(0));
    }

    @Test
    public void verifyAdjacent2(){
        //Se comprueba la adyacencia de un vertice aislado
        setup1();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addVertex(4);
        graphList.addEdge(1, 2);
        graphList.addEdge(1, 3);
        graphList.addEdge(2, 3);
        assertTrue(graphList.getAdjacent(4).isEmpty());

    }

    @Test
    public void verifyAdjacent3(){
        //Un nodo que no existe en el grafo. Verifica que el método retorne null.
        setup1();
        graphList.addVertex(1);
        graphList.addVertex(2);
        graphList.addVertex(3);
        graphList.addEdge(1, 2);
        graphList.addEdge(1, 3);
        graphList.addEdge(2, 3);
        assertNull(graphList.getAdjacent(4));
    }

    @Test
    public void bsfTest1(){
        //Se verifica que las distancias respecto a I correspondan al grafo
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
        graphList.addEdge("A", "C");
        graphList.addEdge("E", "A");
        graphList.addEdge("I", "E");
        graphList.addEdge("I", "B");
        graphList.addEdge("E", "J");
        graphList.addEdge("J", "G");
        graphList.addEdge("G", "C");
        graphList.addEdge("C", "F");
        graphList.addEdge("C", "D");
        graphList.addEdge("F", "D");
        graphList.addEdge("D", "H");
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
        //Se tiene un grafo sin conexiones, se espera que la discancias entre los vertices sea 0
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
        //Se inica el bsf con un valor que no esta en el grafo
        setup2();
        graphList.addVertex("A");
        graphList.addVertex("B");
        graphList.addVertex("C");
        graphList.addVertex("D");
        graphList.addVertex("E");
        graphList.addVertex("F");
        graphList.addVertex("G");
        graphList.addVertex("H");
        graphList.addEdge("G", "A");
        graphList.addEdge("C", "D");
        graphList.addEdge("E", "H");
        try {
            assertEquals("This value don't exist in the graph", graphList.bfs("z"));
        } catch (EmptyFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void dsfTest1(){
        //Se verifica que las distancias respecto a I correspondan al grafo
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
        graphList.addEdge("P", "Q");
        graphList.addEdge("Q", "W");
        graphList.addEdge("W", "P");
        graphList.addEdge("O", "Y");
        graphList.addEdge("O", "T");
        graphList.addEdge("O", "S");
        graphList.addEdge("Y", "T");
        graphList.addEdge("T", "X");
        graphList.addEdge("X", "S");
        graphList.addEdge("R", "X");
        graphList.addEdge("R", "Z");
        graphList.addEdge("V", "S");
        graphList.addEdge("V", "R");
        String expected = "O d: 1 f: 10\n" + "P d: 11 f: 16\n" + "Q d: 12 f: 15\n" +
                "R d: 17 f: 20\n" + "S d: 5 f: 6\n" + "T d: 3 f: 8\n" + "U d: 21 f: 22\n" +
                "V d: 23 f: 24\n" + "W d: 13 f: 14\n" + "X d: 4 f: 7\n" + "Y d: 2 f: 9\n" +
                "Z d: 18 f: 19\n";
        graphList.dfs();
        assertEquals(expected, graphList.checkDfs());

    }

    @Test
    public void dsfTest2(){
        //Se tiene un grafo sin conexiones, se espera que D y F aumenten en 1 en cada vertice
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
        //Se usa un grafo dirigido ciclico, se comprueba que los D y F de cada vertice lo demuestren
        setup2();
        graphList.addVertex("A");
        graphList.addVertex("B");
        graphList.addVertex("C");
        graphList.addVertex("D");
        graphList.addVertex("E");
        graphList.addVertex("F");

        graphList.addEdge("A", "B");
        graphList.addEdge("B", "C");
        graphList.addEdge("C", "D");
        graphList.addEdge("D", "E");
        graphList.addEdge("E", "F");
        graphList.addEdge("F", "A");
        String expected = "A d: 1 f: 12\n" + "B d: 2 f: 11\n" + "C d: 3 f: 10\n" + "D d: 4 f: 9\n" +
                "E d: 5 f: 8\n" + "F d: 6 f: 7\n";
        graphList.dfs();
        assertEquals(expected, graphList.checkDfs());
    }
}