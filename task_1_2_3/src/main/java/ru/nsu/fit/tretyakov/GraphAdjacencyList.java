package ru.nsu.fit.tretyakov;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Adjacency List is one of the useful graph-structures, because this structure
 * allows user easily delete and add edges and vertices. By this implementation
 * graph can be directed / undirected. Edges can also have weight.
 *
 * @param <T> is the required type of object that will be stored in the Graph
 */
public class GraphAdjacencyList<T> implements Graph<T> {

    /**
     * This HashMap contains collection of entries, whose first element
     * is the vertex of the Graph and the second element is the list of all
     * adjacent vertices of that vertex.
     */
    private HashMap<Vertex<T>, ArrayList<Vertex<T>>> adjacencyList;
    /**
     * List of all edges in the Graph. Useful to quickly get edge weight
     * and create array of all edges in the Graph.
     */
    private ArrayList<Edge<T>> edgesList;
    private Integer vertices, edges;
    private boolean directed;

    /**
     * GraphAdjacencyList constructor. Creates HashMap (that is implementing
     * matrix-structure) of a single vertex.
     *
     * @param vertex     is the initial vertex that will be stored in the Graph.
     * @param isDirected equals true if user wants to Graph be directed.
     *                   Otherwise, Graph will be undirected.
     */
    public GraphAdjacencyList(Vertex<T> vertex, boolean isDirected) {
        this(isDirected);
        addVertex(vertex);
    }

    /**
     * Another Graph constructor that creates the Graph from the required collection of vertices.
     *
     * @param vertexCollection is the collection of vertex which will be stored in the Graph.
     * @param isDirected       equals true if user wants to Graph be directed.
     *                         Otherwise, Graph will be undirected.
     */
    public GraphAdjacencyList(Collection<Vertex<T>> vertexCollection, boolean isDirected) {
        this(isDirected);
        addVertices(vertexCollection);
    }

    /**
     * Constructor that converts GraphAdjacencyMatrix / GraphIncidentMatrix
     * to GraphAdjacencyList.
     *
     * @param graph      is the GraphAdjacencyMatrix class
     * @param isDirected equals true, if graph is directed.
     *                   Otherwise, equals false.
     */
    public GraphAdjacencyList(Graph<T> graph, boolean isDirected) {
        this(isDirected);
        addVertices(List.of(graph.toVertexArray()));
        addEdges(List.of(graph.toEdgeArray()));
    }

    /**
     * Default constructor. Sets the initial state of the Graph.
     * In the beginning number of vertices and edges are both equals to zero.
     *
     * @param isDirected is the type of the Graph: directed (only one certain edge
     *                   will be stored in the matrix) or undirected (edge-in-out and
     *                   edge-out-in will be stored in the matrix).
     */
    public GraphAdjacencyList(boolean isDirected) {
        this.adjacencyList = new HashMap<>();
        this.edgesList = new ArrayList<>();
        this.vertices = this.edges = 0;
        this.directed = isDirected;
    }

    /**
     * This method adds required vertex to the Graph. Cannot add vertex, that is
     * already in the Graph.
     *
     * @param newVertex is the vertex which will be added to the Graph.
     * @throws IllegalStateException if user is trying to add vertex which is already containing
     *                               in the HashMap.
     * @throws NullPointerException  if vertex is equals to null.
     */
    @Override
    public void addVertex(Vertex<T> newVertex)
            throws IllegalStateException, NullPointerException {

        if (newVertex == null) {
            throw new NullPointerException("Vertex cannot be null");
        }
        if (adjacencyList.get(newVertex) != null) {
            throw new IllegalStateException("Vertex is already exist");
        }
        adjacencyList.put(newVertex, new ArrayList<Vertex<T>>());
        this.vertices++;
    }

    /**
     * This method adds a collection of vertices to the Graph.
     *
     * @param collection is the required collection of vertices
     *                   which will be added to the Graph.
     * @throws IllegalStateException
     * @throws NullPointerException
     */
    @Override
    public void addVertices(Collection<Vertex<T>> collection)
            throws IllegalStateException, NullPointerException {
        for (var vertex : collection) {
            addVertex(vertex);
        }
    }

    /**
     * This method deletes a vertex from the Graph. It doesn't throw an exception
     * when vertex isn't in the Graph. All incident edges with this vertex will be
     * deleted too.
     *
     * @param vertex is the required vertex that will be deleted from the Graph.
     * @return true if vertex has been deleted successfully.
     * @throws NullPointerException if deleting vertex is equals to null.
     */
    @Override
    public boolean deleteVertex(Vertex<T> vertex)
            throws NullPointerException {

        if (vertex == null) {
            throw new NullPointerException("Vertex cannot be null");
        }
        if (adjacencyList.get(vertex) == null) {
            return false;
        }

        // deleting incident edges with this vertex
        for (var curVertex : adjacencyList.keySet()) {
            if (!curVertex.equals(vertex)) {
                for (var adjVertex : adjacencyList.get(curVertex)) {
                    if (adjVertex.equals(vertex)) {
                        deleteEdge(new Edge<>(curVertex, adjVertex));
                        deleteEdge(new Edge<>(adjVertex, curVertex));
                    }
                }
            }
        }
        // deleting edges in the edgesList
        this.edgesList.removeIf(edge -> edge.getOutgoingVertex().equals(vertex)
                || edge.getIngoingVertex().equals(vertex));

        adjacencyList.remove(vertex);
        this.vertices--;
        return true;
    }

    /**
     * This method deletes all vertices in the required collection from the Graph.
     *
     * @param collection is the required collection of vertices which will
     *                   be deleted from the graph.
     * @return true if all vertices in the collection have been deleted successfully.
     * @throws NullPointerException
     */
    @Override
    public boolean deleteAllVertices(Collection<Vertex<T>> collection)
            throws NullPointerException {

        boolean allDeleted = true;
        for (var vertex : collection) {
            allDeleted &= deleteVertex(vertex);
        }
        return allDeleted;
    }

    /**
     * This method creates an array of vertices from the Graph.
     *
     * @return array of all not-null vertices in the Graph.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Vertex<T>[] toVertexArray() {
        return adjacencyList.keySet().toArray(Vertex[]::new);
    }

    /**
     * This method allows user to know number of vertices in the Graph.
     *
     * @return number of not-null vertices in the Graph.
     */
    @Override
    public int vertexCount() {
        return this.vertices;
    }

    /**
     * This method adds the required edge to the Graph.
     *
     * @param edge is the required edge which will be added to the Graph.
     * @throws IllegalStateException if the required edge is already in the Graph.
     * @throws NullPointerException  if edge is equals to null or one of vertices that is contained
     *                               in the required edge.
     */
    @Override
    public void addEdge(Edge<T> edge)
            throws IllegalStateException, NullPointerException {

        if (edge == null) {
            throw new NullPointerException("Edge cannot be null");
        }

        var in = edge.getIngoingVertex();
        var out = edge.getOutgoingVertex();

        if (in == null || out == null) {
            throw new NullPointerException("Vertices in the edge cannot be null");
        }

        // checking duplicated edge
        if (adjacencyList.get(in) != null && adjacencyList.get(out) != null) {
            if (adjacencyList.get(in).contains(out)) {
                throw new IllegalStateException("Edge is already exist");
            }
        }

        // adding vertices which are not in the Graph
        else {
            if (adjacencyList.get(in) == null) addVertex(in);
            if (adjacencyList.get(out) == null) addVertex(out);
        }

        if (this.directed) adjacencyList.get(in).add(out);
        else {
            adjacencyList.get(in).add(out);
            adjacencyList.get(out).add(in);
        }
        this.edges++;
        edgesList.add(edge);
    }

    /**
     * This method adds the required collection of edges to the Graph.
     *
     * @param collection is the required collection of the edges which will be added
     *                   to the Graph.
     * @throws IllegalStateException
     * @throws NullPointerException
     */
    @Override
    public void addEdges(Collection<Edge<T>> collection)
            throws IllegalStateException, NullPointerException {
        for (var edge : collection) {
            addEdge(edge);
        }
    }

    /**
     * This method deletes the edge from the graph.
     *
     * @param edge is the required edge that will be deleted from the graph.
     * @return true if the edge is successfully deleted from the Graph.
     * Otherwise, return false.
     * @throws NullPointerException if edge is equals to null or one of vertices that is contained
     *                              in the required edge.
     */
    @Override
    public boolean deleteEdge(Edge<T> edge)
            throws NullPointerException {

        if (edge == null) {
            throw new NullPointerException("Edge cannot be null");
        }

        // detecting existence of the edge

        var in = edge.getIngoingVertex();
        var out = edge.getOutgoingVertex();

        if (in == null || out == null) {
            throw new NullPointerException("Edge cannot contains null vertices");
        }
        if (adjacencyList.get(in).contains(out)) {
            if (this.directed) adjacencyList.get(in).remove(out);
            else {
                adjacencyList.get(in).remove(out);
                adjacencyList.get(out).remove(in);
            }
            this.edges--;
            edgesList.remove(edge);
            return true;
        } else return false;
    }

    /**
     * This method deletes the required collection of the edges from the Graph.
     *
     * @param collection is the required collection of the edges.
     * @return true if all edges in this collection have been deleted.
     * Otherwise, return false.
     * @throws NullPointerException
     */
    @Override
    public boolean deleteAllEdges(Collection<Edge<T>> collection)
            throws NullPointerException {

        boolean allDeleted = true;
        for (var edge : collection) {
            allDeleted &= deleteEdge(edge);
        }
        return allDeleted;
    }

    /**
     * This function creates an array of edges from the Graph.
     *
     * @return created array of edges from the Graph.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Edge<T>[] toEdgeArray() {
        return edgesList.toArray(Edge[]::new);
    }

    /**
     * This method allows user to know number of edges in the Graph.
     *
     * @return number of edges in the Graph.
     */
    @Override
    public int edgesCount() {
        return this.edges;
    }

    /**
     * This method allows user to know is the Graph empty or not.
     *
     * @return true if the Graph doesn't contain any vertex.
     * Otherwise, return false.
     */
    @Override
    public boolean isEmpty() {
        return this.vertices == 0;
    }

    /**
     * Algorithm that determines the shortest paths from current
     * vertex to other vertices.
     *
     * @param vertex is the required vertex
     * @return list of all shortest paths to the specified vertex,
     * that is sorted by increasing distance of vertices.
     */
    @Override
    public List<Vertex<T>> dijkstra(Vertex<T> vertex) {

        if (!directed) return null;

        // initial state
        for (var curVertex : toVertexArray()) {
            curVertex.setDistance(Float.POSITIVE_INFINITY);
        }

        Set<Vertex<T>> unsettledSet = new HashSet<>();
        Set<Vertex<T>> settledSet = new HashSet<>();

        vertex.setDistance((float) 0);
        unsettledSet.add(vertex);

        // relaxing vertices distance
        while (!unsettledSet.isEmpty()) {

            Vertex<T> minVertex = getMinVertex(unsettledSet);
            unsettledSet.remove(minVertex);
            settledSet.add(minVertex);

            for (var adjVertex : adjacencyList.get(minVertex)) {

                var dist = minVertex.getDistance() + findEdge(minVertex, adjVertex).getWeight();

                if (!settledSet.contains(adjVertex) && adjVertex.getDistance() > dist) {
                    adjVertex.setDistance(dist);
                    unsettledSet.add(adjVertex);
                }
            }
        }

        // adding vertices that haven't been checked
        for (var restVertex : toVertexArray()) {
            if (!settledSet.contains(restVertex)) {
                settledSet.add(restVertex);
            }
        }

        // sorted list of the vertices
        List<Vertex<T>> result = settledSet.stream().sorted(
                        (v1, v2) -> v1.getDistance().compareTo(v2.getDistance())).
                collect(Collectors.toCollection(ArrayList::new));

        return result;
    }

    private Edge<T> findEdge(Vertex<T> in, Vertex<T> out) {

        Edge<T> resEdge = null;

        for (var edge : edgesList) {
            if (edge.getIngoingVertex().equals(in)
                    && edge.getOutgoingVertex().equals(out)) {
                resEdge = edge;
                break;
            }
        }

        return resEdge;
    }

    private Vertex<T> getMinVertex(Set<Vertex<T>> unsettledSet) {

        Float minValue = Float.POSITIVE_INFINITY;
        Vertex<T> minVertex = null;

        for (var vertex : unsettledSet) {
            if (vertex.getDistance() < minValue) {
                minVertex = vertex;
                minValue = vertex.getDistance();
            }
        }

        return minVertex;
    }
}
