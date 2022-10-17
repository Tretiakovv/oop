package ru.nsu.fit.tretyakov;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is the incident-matrix to store <b>directed</b> graph.
 * This class implements parametrized Graph interface, which allows user to
 * add, delete edges, create array, that contains of vertices or edges,
 * and also determine number of vertices and edges in the graph.
 * <b>NOTE.</b> All edges are unweighted. This class can't store duplicated
 * vertices and edges.
 *
 * @param <T> is the required type of the stored object.
 */
public class GraphIncidentMatrix<T> implements Graph<T> {
    private HashMap<Vertex<T>, ArrayList<Edge<T>>> incidentMatrix;
    private Integer vertices, edges;
    private boolean isDirected;

    /**
     * GraphIncidentMatrix constructor. Creates HashMap (that is implementing
     * matrix-structure) of a single vertex.
     *
     * @param vertex is the required initial vertex of the graph.
     */
    public GraphIncidentMatrix(Vertex<T> vertex, boolean isDirected) {
        this(isDirected);
        addVertex(vertex);
    }

    /**
     * GraphAdjacencyMatrix constructor. Creates HashMap of all elements in the required collection.
     *
     * @param vertexCollection is the collection of vertex which will be stored in the Graph.
     * @throws IllegalStateException if user is trying to add vertex which is already containing
     *                               in the HashMap.
     */
    public GraphIncidentMatrix(Collection<Vertex<T>> vertexCollection, boolean isDirected)
            throws IllegalStateException {
        this(isDirected);
        addVertices(vertexCollection);
    }

    /**
     * General constructor of the Graph.
     *
     * @param isDirected is the type of the Graph: directed (only one certain edge
     *                   will be stored in the matrix) or undirected (edge-in-out and
     *                   edge-out-in will be stored in the matrix).
     */
    public GraphIncidentMatrix(boolean isDirected) {
        this.incidentMatrix = new HashMap<>();
        this.vertices = this.edges = 0;
        this.isDirected = isDirected;
    }

    /**
     * This method adds required vertex to the Graph. Cannot add vertex, that is
     * already in the Graph. <b>NOTE.</b> This method is faster than the same method
     * in GraphAdjacencyMatrix class because in GraphIncidentMatrix vertices are
     * connected with edges straightly.
     *
     * @param newVertex is the vertex which will be added to the Graph.
     * @throws IllegalStateException if user is trying to add vertex which is already containing
     *                               in the HashMap.
     */
    @Override
    public void addVertex(Vertex<T> newVertex)
            throws IllegalStateException {

        if (incidentMatrix.get(newVertex) != null) {
            throw new IllegalStateException("Vertex is already exist");
        }
        this.vertices++;
        incidentMatrix.put(newVertex, new ArrayList<>());
    }

    /**
     * This method adds a collection of vertices to the Graph.
     *
     * @param collection is the required collection of vertices
     *                   which will be added to the Graph.
     */
    @Override
    public void addVertices(Collection<Vertex<T>> collection) {
        for (var vertex : collection) {
            addVertex(vertex);
        }
    }

    /**
     * This method deletes a vertex from the Graph. It doesn't throw an exception
     * when vertex isn't in the Graph. All incident edges with this vertex will be
     * deleted too. <b>NOTE.</b> This method is slower than the same method in
     * GraphAdjacencyMatrix.
     *
     * @param vertex is the required vertex that will be deleted from the Graph.
     * @return true if vertex has been deleted successfully.
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean deleteVertex(Vertex<T> vertex) {

        if (incidentMatrix.get(vertex) == null) {
            return false;
        } else {
            for (var edge : incidentMatrix.get(vertex)) {
                deleteEdge(edge);
            }
            incidentMatrix.remove(vertex);
            this.vertices--;
        }
        return true;
    }

    /**
     * This method deletes all vertices in the required collection from the Graph.
     *
     * @param collection is the required collection of vertices which will
     *                   be deleted from the graph.
     * @return true if all vertices in the collection have been deleted successfully.
     */
    @Override
    public boolean deleteAllVertices(Collection<Vertex<T>> collection) {
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
        return incidentMatrix.keySet().toArray(Vertex[]::new);
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
     * <b>NOTE.</b> This method is slower than the same method in
     * GraphAdjacencyMatrix because edges in this class are objects, not an enum-type.
     *
     * @param edge is the required edge which will be added to the Graph.
     * @throws IllegalStateException if the required edge is already in the Graph.
     */
    @Override
    public void addEdge(Edge<T> edge) throws IllegalStateException {

        var in = edge.getIngoingVertex();
        var out = edge.getOutgoingVertex();

        if (incidentMatrix.get(in) == null) addVertex(in);
        if (incidentMatrix.get(out) == null) addVertex(out);

        // checking duplicated edge
        for (var incEdge : incidentMatrix.get(in)) {
            if (incEdge.getOutgoingVertex().equals(out)) {
                throw new IllegalStateException("Edge is already exist");
            }
        }

        incidentMatrix.get(in).add(edge);
        if (!isDirected) {
            Edge<T> backwardEdge = new Edge<>(out, in, edge.getWeight());
            incidentMatrix.get(out).add(backwardEdge);
        }

        this.edges++;
    }

    /**
     * This method adds the required collection of edges to the Graph.
     *
     * @param collection is the required collection of the edges which will be added
     *                   to the Graph.
     */
    @Override
    public void addEdges(Collection<Edge<T>> collection) {
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
     */
    @Override
    public boolean deleteEdge(Edge<T> edge) {

        if (!incidentMatrix.get(edge.getIngoingVertex()).contains(edge)) {
            return false;
        }

        var in = edge.getIngoingVertex();
        var out = edge.getOutgoingVertex();

        incidentMatrix.get(in).remove(edge);
        if (!isDirected) {
            incidentMatrix.get(out).removeIf(
                    backwardEdge -> backwardEdge.getOutgoingVertex().equals(in));
        }

        this.edges--;
        return true;
    }

    /**
     * This method deletes the required collection of the edges from the Graph.
     *
     * @param collection is the required collection of the edges.
     * @return true if all edges in this collection have been deleted.
     * Otherwise, return false.
     */
    @Override
    public boolean deleteAllEdges(Collection<Edge<T>> collection) {
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
        List<Edge<T>> edgeList = new ArrayList<>();
        for (var vertex : toVertexArray()) {
            for (var edge : incidentMatrix.get(vertex)) {
                if (!edgeList.contains(edge)) {
                    edgeList.add(edge);
                }
            }
        }
        return edgeList.toArray(Edge[]::new);
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

        if (!isDirected) return null;

        for (var curVertex : incidentMatrix.keySet()) {
            curVertex.setDistance(Float.POSITIVE_INFINITY);
        }

        Set<Vertex<T>> unsettledSet = new HashSet<>();
        Set<Vertex<T>> settledSet = new HashSet<>();

        vertex.setDistance((float) 0);
        unsettledSet.add(vertex);

        while (!unsettledSet.isEmpty()) {

            Vertex<T> minVertex = getMinVertex(unsettledSet);
            unsettledSet.remove(minVertex);
            settledSet.add(minVertex);

            for (var incEdge : incidentMatrix.get(minVertex)) {

                var dist = minVertex.getDistance() + incEdge.getWeight();
                var out = incEdge.getOutgoingVertex();

                if (!settledSet.contains(out) && out.getDistance() > dist) {
                    out.setDistance(dist);
                    unsettledSet.add(out);
                }
            }
        }

        for (var restVertex : toVertexArray()) {
            if (!settledSet.contains(restVertex)) {
                settledSet.add(restVertex);
            }
        }

        List<Vertex<T>> result = settledSet.stream().sorted(
                        (v1, v2) -> v1.getDistance().compareTo(v2.getDistance())).
                collect(Collectors.toCollection(ArrayList::new));

        return result;
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
