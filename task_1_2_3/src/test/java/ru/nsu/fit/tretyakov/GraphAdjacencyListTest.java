package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GraphAdjacencyListTest {

    // checking reading from the file
    @Test
    public void readFromTheFile() throws FileNotFoundException {

        File file = new File("./src/test/java/ru/nsu/fit/tretyakov/GraphAdjList.txt");
        GraphParser gp = new GraphParser(file);

        Graph<Integer> integerGraph = gp.parseGraph();
        assertEquals(3, integerGraph.toEdgeArray().length);
        assertEquals(3, integerGraph.toVertexArray().length);
    }

    // checking setter of the vertex data
    @Test
    public void vertexSetDataTest() {

        Vertex<Integer> vertex = new Vertex<>(1);

        vertex.setData(2);
        assertEquals(vertex.getData(), 2);

        vertex.setDistance((float) 2);
        assertEquals(vertex.getDistance(), (float) 2);
    }

    // checking setter of the edge ingoing / outgoing vertex / weight
    @Test
    public void ingoingVertexSetTest() {

        Vertex<Integer> in = new Vertex<>(1);
        Vertex<Integer> out = new Vertex<>(2);
        Edge<Integer> edge = new Edge<>(in, out);

        edge.setIngoingVertex(out);
        edge.setOutgoingVertex(in);
        edge.setWeight(2);

        assertEquals(edge.getIngoingVertex(), out);
        assertEquals(edge.getOutgoingVertex(), in);
        assertEquals(edge.getWeight(), 2);
    }

    // checking Constructor / addEdge / toArray functions
    @SuppressWarnings("unchecked")
    @Test
    public void graphToVertexArrayTest() {

        Vertex<Integer>[] vertices = new Vertex[]{new Vertex<>(1), new Vertex<>(2),
                new Vertex<>(3), new Vertex<>(4), new Vertex<>(5)};

        List<Vertex<Integer>> list = Stream.of(vertices).collect(Collectors.toCollection(ArrayList::new));
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(list, true);

        testGraph.addVertex(new Vertex<>(6));

        List<Integer> verticesData = List.of(new Integer[]{1, 2, 3, 4, 5, 6});

        // testing addVertex function
        for (var elem : testGraph.toVertexArray()) {
            assertTrue(verticesData.contains(elem.getData()));
        }
    }

    // checking NullPointerException when adding null vertex
    @Test
    public void nullVertexAddingTest() {
        assertThrows(NullPointerException.class, () -> {
            Vertex<Integer> v = null;
            Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(v, true);
        });
    }

    // checking NPE when deleting null vertex
    @Test
    public void nullVertexDeletingTest() {
        assertThrows(NullPointerException.class, () -> {
            Vertex<Integer> v = new Vertex<>(1);
            Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(v, true);
            testGraph.deleteVertex(null);
        });
    }

    // checking IllegalStateException when duplicating vertices
    @Test
    public void duplicatedVerticesTest() {
        Vertex<Integer> start = new Vertex<>(1);
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(start, true);

        assertThrows(IllegalStateException.class, () -> {
            testGraph.addVertex(start);
        });
    }

    // checking toEdgeArray method
    @Test
    public void addEdgeTest() {

        Vertex<Integer>[] vertices = new Vertex[]{new Vertex<>(1), new Vertex<>(2),
                new Vertex<>(3), new Vertex<>(4), new Vertex<>(5)};

        List<Vertex<Integer>> list = Stream.of(vertices).collect(Collectors.toCollection(ArrayList::new));
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(list, true);

        testGraph.addEdge(new Edge<>(vertices[0], vertices[1]));
        testGraph.addEdge(new Edge<>(vertices[1], vertices[3]));

        assertEquals(2, testGraph.toEdgeArray().length);
    }

    // checking NPE when adding null edge
    @Test
    public void nullEdgeAddingTest() {
        Vertex<Integer> start = new Vertex<>(1);
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(start, true);

        assertThrows(NullPointerException.class, () -> {
            testGraph.addEdge(null);
        });
    }

    // checking NPE when adding an edge with null vertices
    @Test
    public void nullVerticesInEdgeTest() {
        Vertex<Integer> start = new Vertex<>(1);
        Edge<Integer> e = new Edge<>(start, null);
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(start, true);

        assertThrows(NullPointerException.class, () -> {
            testGraph.addEdge(e);
        });
    }

    // checking adding edge whose vertices not in the Graph
    @Test
    public void verticesNotInTheGraphTest() {
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(new Vertex<>(1), true);
        Edge<Integer> e = new Edge<>(new Vertex<>(2), new Vertex<>(3));
        testGraph.addEdge(e);
        assertEquals(testGraph.toEdgeArray()[0], e);
    }

    // checking adding edge to the undirected Graph
    @Test
    public void addingEdgesToUndirectedGraph() {
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(new Vertex<>(1), false);
        Vertex<Integer> v1 = new Vertex(2);
        Vertex<Integer> v2 = new Vertex<>(3);
        Edge<Integer> e = new Edge<>(v1, v2);
        testGraph.addEdge(e);
        assertThrows(IllegalStateException.class, () -> {
            testGraph.addEdge(new Edge<>(v2, v1));
        });
    }

    // checking NPE when deleting null edge
    @Test
    public void deleteNullEdgeTest() {
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(new Vertex<>(1), false);
        assertThrows(NullPointerException.class, () -> {
            testGraph.deleteEdge(null);
        });
    }

    // checking NPE when deleting an edge whose vertices are null
    @Test
    public void deleteEdgeWithNullVertices() {
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(new Vertex<>(1), false);
        Edge<Integer> e = new Edge<>(new Vertex<>(1), null);
        assertThrows(NullPointerException.class, () -> {
            testGraph.deleteEdge(e);
        });
    }

    // checking deleting edge from undirected Graph
    @Test
    public void deleteEdgeFromUndirectedGraph() {
        List<Vertex<Integer>> listOfVertices = List.of(new Vertex<>(1), new Vertex<>(2));
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(listOfVertices, false);
        testGraph.addEdge(new Edge<>(listOfVertices.get(0), listOfVertices.get(1)));
        testGraph.deleteEdge(new Edge<>(listOfVertices.get(0), listOfVertices.get(1)));
        assertEquals(testGraph.edgesCount(), 0);
    }

    // checking IllegalStateException when duplicating edges
    @Test
    public void duplicatedEdgesTest() {
        Vertex<Integer>[] vertices = new Vertex[]{new Vertex<>(1), new Vertex<>(2)};

        List<Vertex<Integer>> list = Stream.of(vertices).collect(Collectors.toCollection(ArrayList::new));
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(list, true);

        testGraph.addEdge(new Edge<>(vertices[0], vertices[1]));

        assertThrows(IllegalStateException.class, () -> {
            testGraph.addEdge(new Edge<>(vertices[0], vertices[1]));
        });
    }

    // checking addVertices method
    @Test
    public void multiplyAddingVertices() {
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(new Vertex<>(1), true);
        List<Vertex<Integer>> vertexList = List.of(new Vertex<>(2), new Vertex<>(3));

        testGraph.addVertices(vertexList);
        List<Integer> verticesData = List.of(new Integer[]{1, 2, 3});

        for (var elem : testGraph.toVertexArray()) {
            assertTrue(verticesData.contains(elem.getData()));
        }
    }

    // checking addEdges method
    @Test
    public void multiplyAddingEdges() {
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(new Vertex<>(1), true);
        List<Vertex<Integer>> vertexList = List.of(new Vertex<>(2), new Vertex<>(3));
        List<Edge<Integer>> edgeList = List.of(new Edge<>(vertexList.get(0), vertexList.get(1)), new Edge<>(vertexList.get(1), vertexList.get(0)));
        testGraph.addVertices(vertexList);
        testGraph.addEdges(edgeList);

        int counter = 0;
        for (var edge : testGraph.toEdgeArray()) {
            System.out.printf("%d edge: ingoing vertex - %d, outgoing vertex - %d\n", counter++, edge.getIngoingVertex().getData(), edge.getOutgoingVertex().getData());
            assertTrue(List.of(2, 3).contains(edge.getIngoingVertex().getData()));
            assertTrue(List.of(2, 3).contains(edge.getOutgoingVertex().getData()));
        }
    }

    // checking deleteVertex / deleteAllVertices methods
    @Test
    public void deleteVerticesTest() {

        Vertex<Integer>[] vertices = new Vertex[]{new Vertex<>(1), new Vertex<>(2), new Vertex<>(3),
                new Vertex(4), new Vertex<>(5)};

        List<Vertex<Integer>> list = Stream.of(vertices).collect(Collectors.toCollection(ArrayList::new));
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(list, true);

        List<Edge<Integer>> edgeList = List.of(new Edge<>(vertices[0], vertices[1]), new Edge<>(vertices[1], vertices[0]),
                new Edge<>(vertices[0], vertices[2]), new Edge<>(vertices[1], vertices[2]));
        testGraph.addEdges(edgeList);

        testGraph.deleteVertex(vertices[0]);
        testGraph.deleteVertex(new Vertex<>(6));

        testGraph.deleteAllVertices(Stream.of(vertices[1], vertices[2], vertices[3]).
                collect(Collectors.toCollection(ArrayList::new)));

        assertEquals(1, testGraph.vertexCount());
        assertEquals(testGraph.toVertexArray()[0].getData(), 5);
    }

    // checking deleteEdge / deleteAllEdges methods
    @Test
    public void deleteEdgesTest() {
        Vertex<Integer>[] vertices = new Vertex[]{new Vertex<>(1), new Vertex<>(2), new Vertex<>(3)};

        List<Vertex<Integer>> list = Stream.of(vertices).collect(Collectors.toCollection(ArrayList::new));
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(list, true);
        List<Edge<Integer>> edgeList = List.of(new Edge<>(vertices[0], vertices[1]), new Edge<>(vertices[1], vertices[0]),
                new Edge<>(vertices[0], vertices[2]), new Edge<>(vertices[1], vertices[2]));

        testGraph.addEdges(edgeList);
        testGraph.deleteEdge(edgeList.get(0));
        testGraph.deleteEdge(new Edge<>(vertices[0], vertices[0]));
        testGraph.deleteAllEdges(Stream.of(edgeList.get(1), edgeList.get(2)).
                collect(Collectors.toCollection(ArrayList::new)));

        assertEquals(1, testGraph.edgesCount());
        assertEquals(testGraph.toEdgeArray()[0].getIngoingVertex().getData(), 2);
        assertEquals(testGraph.toEdgeArray()[0].getOutgoingVertex().getData(), 3);
    }

    //checking isEmpty is false
    @Test
    public void isEmptyIsFalseTest() {
        Vertex<Integer>[] vertices = new Vertex[]{new Vertex<>(1), new Vertex<>(2), new Vertex<>(3)};

        List<Vertex<Integer>> list = Stream.of(vertices).collect(Collectors.toCollection(ArrayList::new));
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(list, true);
        assertFalse(testGraph.isEmpty());
    }

    @Test
    public void isEmptyIsTrueTest() {
        Vertex<Integer> start = new Vertex<>(1);
        Graph<Integer> testGraph = new GraphAdjacencyList<Integer>(start, true);
        testGraph.deleteVertex(start);
        assertTrue(testGraph.isEmpty());
    }

    @Test
    public void shortestPathUndirectedGraphTest() {
        Graph<Integer> graph = new GraphAdjacencyList<Integer>(false);
        List<Vertex<Integer>> vertexList = List.of(new Vertex<>(1), new Vertex<>(2), new Vertex<>(3));
        List<Edge<Integer>> edgeList = List.of(new Edge<>(vertexList.get(0), vertexList.get(1)));
        graph.addEdges(edgeList);
        assertNull(graph.dijkstra(vertexList.get(0)));
    }

    // checking dijkstra for the directedGraph
    @Test
    public void shortestPathDirectedGraphTest() {

        Graph<String> graph = new GraphAdjacencyList<>(true);

        List<Vertex<String>> vertexList = List.of(new Vertex<>("A"),
                new Vertex<>("B"), new Vertex<>("C"),
                new Vertex<>("D"), new Vertex<>("E"));

        graph.addVertices(vertexList);

        List<Edge<String>> edgeList = List.of(
                new Edge<>(vertexList.get(0), vertexList.get(1), 7),
                new Edge<>(vertexList.get(0), vertexList.get(2), 2),
                new Edge<>(vertexList.get(0), vertexList.get(3), 18),
                new Edge<>(vertexList.get(0), vertexList.get(4), 20),
                new Edge<>(vertexList.get(1), vertexList.get(3), 6),
                new Edge<>(vertexList.get(2), vertexList.get(3), 10),
                new Edge<>(vertexList.get(3), vertexList.get(4), 2));

        graph.addEdges(edgeList);

        List<Vertex<String>> vertices = graph.dijkstra(vertexList.get(0));

        for (var vertex : vertices) {
            System.out.printf("%s(%d) ", vertex.getData(), vertex.getDistance().intValue());
        }

    }

    // checking Dijkstra for disconnected vertices
    @Test
    public void shortestPathDirectedDisconnectGraphTest() {

        Graph<String> graph = new GraphAdjacencyList<>(true);

        List<Vertex<String>> vertexList = List.of(new Vertex<>("A"),
                new Vertex<>("B"), new Vertex<>("C"),
                new Vertex<>("D"), new Vertex<>("E"));

        graph.addVertices(vertexList);

        List<Edge<String>> edgeList = List.of(
                new Edge<>(vertexList.get(0), vertexList.get(1), 7),
                new Edge<>(vertexList.get(0), vertexList.get(2), 2),
                new Edge<>(vertexList.get(0), vertexList.get(3), 18));

        graph.addEdges(edgeList);

        List<Vertex<String>> vertices = graph.dijkstra(vertexList.get(0));

        for (var vertex : vertices) {
            System.out.printf("%s(%.0f) ", vertex.getData(), vertex.getDistance());
        }
    }

    // checking converting graph types
    @Test
    public void convertToGAL() {

        Graph<String> graphAdjacencyMatrix = new GraphAdjacencyMatrix<>(true);

        List<Vertex<String>> vertexList = List.of(new Vertex<>("A"),
                new Vertex<>("B"), new Vertex<>("C"),
                new Vertex<>("D"), new Vertex<>("E"));

        graphAdjacencyMatrix.addVertices(vertexList);

        List<Edge<String>> edgeList = List.of(
                new Edge<>(vertexList.get(0), vertexList.get(1), 7),
                new Edge<>(vertexList.get(0), vertexList.get(2), 2),
                new Edge<>(vertexList.get(0), vertexList.get(3), 18));

        graphAdjacencyMatrix.addEdges(edgeList);

        Graph<String> graphAdjacencyList = new GraphAdjacencyList<String>(graphAdjacencyMatrix, true);

        assertEquals(graphAdjacencyList.toEdgeArray().length, 3);
        assertEquals(graphAdjacencyList.toVertexArray().length, 5);
    }
}
