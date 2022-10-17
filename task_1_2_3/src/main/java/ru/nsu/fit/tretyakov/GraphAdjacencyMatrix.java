package ru.nsu.fit.tretyakov;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is the adjacency-matrix to store undirected / directed graph.
 * This class implements parametrized Graph interface, which allows user to
 * add, delete edges, create array, that contains of vertices or edges,
 * and also determine number of vertices and edges in the graph.
 * <b>NOTE.</b> All edges are unweighted. This class can't store duplicated
 * vertices and edges.
 *
 * @param <T> is the required type of the stored object.
 */
public class GraphAdjacencyMatrix<T> implements Graph<T> {
    private HashMap<Vertex<T>, HashMap<Vertex<T>, Integer>> adjacencyMatrix;
    private Integer vertices, edges;
    private boolean isDirected;

    /**
     * GraphAdjacencyMatrix constructor. Creates HashMap (that is implementing
     * matrix-structure) of a single vertex.
     *
     * @param vertex is the required initial vertex of the graph.
     */
    public GraphAdjacencyMatrix(Vertex<T> vertex, boolean isDirected) {
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
    public GraphAdjacencyMatrix(Collection<Vertex<T>> vertexCollection, boolean isDirected)
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
    public GraphAdjacencyMatrix(boolean isDirected) {
        this.isDirected = isDirected;
        this.adjacencyMatrix = new HashMap<>();
        this.vertices = this.edges = 0;
    }

    /**
     * This method adds required vertex to the Graph. Cannot add vertex, that is
     * already in the Graph.
     *
     * @param newVertex is the vertex which will be added to the Graph.
     * @throws IllegalStateException if user is trying to add vertex which is already containing
     *                               in the HashMap.
     */
    @Override
    public void addVertex(Vertex<T> newVertex) throws IllegalStateException {

        if (adjacencyMatrix.get(newVertex) != null) {
            throw new IllegalStateException("Vertex is already exist");
        }

        this.vertices++;
        adjacencyMatrix.put(newVertex, new HashMap<Vertex<T>, Integer>());

        // adding newVertex edges as 0 to the rest of vertices
        for (var keys : adjacencyMatrix.keySet()) {
            adjacencyMatrix.get(keys).put(newVertex, 0);
        }

        // adding 0 to the current vertex column
        for (var key : adjacencyMatrix.keySet()) {
            adjacencyMatrix.get(newVertex).put(key, 0);
        }
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
     * deleted too.
     *
     * @param vertex is the required vertex that will be deleted from the Graph.
     * @return true if vertex has been deleted successfully.
     */
    @Override
    public boolean deleteVertex(Vertex<T> vertex) {
        if (adjacencyMatrix.get(vertex) == null) {
            return false;
        } else {
            // deleting incident edges
            for (var key : adjacencyMatrix.keySet()) {
                if (adjacencyMatrix.get(key).get(vertex) != 0) {
                    adjacencyMatrix.get(key).replace(vertex, 0);
                }
            }
            adjacencyMatrix.remove(vertex);
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
        Set<Vertex<T>> vertices = this.adjacencyMatrix.keySet();
        return vertices.toArray(Vertex[]::new);
    }

    /**
     * This method allows user to know number of vertices in the Graph.
     *
     * @return number of not-null vertices in the Graph.
     */
    @Override
    public int vertexCount() {
        return vertices;
    }

    /**
     * This method adds the required edge to the Graph.
     *
     * @param edge is the required edge which will be added to the Graph.
     * @throws IllegalStateException if the required edge is already in the Graph.
     */
    @Override
    public void addEdge(Edge<T> edge) throws IllegalStateException {

        var in = edge.getIngoingVertex();
        var out = edge.getOutgoingVertex();

        if (adjacencyMatrix.get(in) == null) addVertex(in);
        if (adjacencyMatrix.get(out) == null) addVertex(out);

        var newColumn = adjacencyMatrix.get(in);
        if (newColumn.get(out) != 0) {
            throw new IllegalStateException("Edge is already exist");
        } else {
            newColumn.replace(out, edge.getWeight());
            adjacencyMatrix.replace(in, newColumn);
            this.edges++;
        }
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

        Vertex<T> in = edge.getIngoingVertex();
        Vertex<T> out = edge.getOutgoingVertex();

        if (adjacencyMatrix.get(in).get(out) != 0) {
            adjacencyMatrix.get(in).replace(out, 0);
            this.edges--;
        } else return false;
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
        Set<Edge<T>> edgeSet = new HashSet<>();
        for (var in : adjacencyMatrix.keySet()) {
            for (var out : adjacencyMatrix.get(in).keySet()) {
                if (adjacencyMatrix.get(in).get(out) != 0) {
                    Edge<T> edge = new Edge<>(in, out, adjacencyMatrix.get(in).get(out));
                    edgeSet.add(edge);
                }
            }
        }
        return edgeSet.toArray(Edge[]::new);
    }

    /**
     * This method allows user to know number of edges in the Graph.
     *
     * @return number of edges in the Graph.
     */
    @Override
    public int edgesCount() {
        return edges;
    }

    /**
     * This method allows user to know is the Graph empty or not.
     *
     * @return true if the Graph doesn't contain any vertex.
     * Otherwise, return false.
     */
    @Override
    public boolean isEmpty() {
        return vertexCount() == 0;
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

        for (var curVertex : adjacencyMatrix.keySet()) {
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

            for (var adjVertex : adjacencyMatrix.get(minVertex).keySet()) {

                // checking existence of the edge between vertices

                if (adjacencyMatrix.get(minVertex).get(adjVertex) != 0) {
                    var dist = minVertex.getDistance() +
                            adjacencyMatrix.get(minVertex).get(adjVertex);

                    if (!settledSet.contains(adjVertex) && adjVertex.getDistance() > dist) {
                        adjVertex.setDistance(dist);
                        unsettledSet.add(adjVertex);
                    }
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
