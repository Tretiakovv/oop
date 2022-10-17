package ru.nsu.fit.tretyakov;

import java.util.*;

/**
 * Graph interface allows user to implement simple methods of adding,
 * deleting edges, creating array of vertices or edges from the Graph
 * and checking Graph state: how many vertices or edges in the Graph,
 * is the Graph empty or not.
 * @param <T> is the type of object that will be stored in the Graph.
 */
public interface Graph<T>{

    /**
     * This method adds required vertex to the Graph. Cannot add vertex, that is
     * already in the Graph.
     * @param newVertex is the vertex which will be added to the Graph.
     */
    public void addVertex(Vertex<T> newVertex);

    /**
     * This method adds a collection of vertices to the Graph.
     * @param collection is the required collection of vertices
     *                   which will be added to the Graph.
     */
    public void addVertices(Collection<Vertex<T>> collection);

    /**
     * This method deletes a vertex from the Graph. It doesn't throw an exception
     * when vertex isn't in the Graph. All incident edges with this vertex will be
     * deleted too.
     * @param vertex is the required vertex that will be deleted from the Graph.
     * @return true if vertex has been deleted successfully.
     */
    public boolean deleteVertex(Vertex<T> vertex);

    /**
     * This method deletes all vertices in the required collection from the Graph.
     * @param collection is the required collection of vertices which will
     *                   be deleted from the graph.
     * @return true if all vertices in the collection have been deleted successfully.
     */
    public boolean deleteAllVertices(Collection<Vertex<T>> collection);

    /**
     * This method creates an array of vertices from the Graph.
     * @return array of all not-null vertices in the Graph.
     */
    public Vertex<T>[] toVertexArray();

    /**
     * This method allows user to know number of vertices in the Graph.
     * @return number of not-null vertices in the Graph.
     */
    public int vertexCount();

    /**
     * This method adds the required edge to the Graph.
     * @param edge is the required edge which will be added to the Graph.
     */
    public void addEdge(Edge<T> edge);

    /**
     * This method adds the required collection of edges to the Graph.
     * @param collection is the required collection of the edges which will be added
     *                   to the Graph.
     */
    public void addEdges(Collection<Edge<T>> collection);

    /**
     * This method deletes the edge from the graph.
     * @param edge is the required edge that will be deleted from the graph.
     * @return true if the edge is successfully deleted from the Graph.
     * Otherwise, return false.
     */
    public boolean deleteEdge(Edge<T> edge);

    /**
     * This method deletes the required collection of the edges from the Graph.
     * @param collection is the required collection of the edges.
     * @return true if all edges in this collection have been deleted.
     * Otherwise, return false.
     */
    public boolean deleteAllEdges(Collection<Edge<T>> collection);

    /**
     * This function creates an array of edges from the Graph.
     * @return created array of edges from the Graph.
     */
    public Edge<T>[] toEdgeArray();

    /**
     * This method allows user to know number of edges in the Graph.
     * @return number of edges in the Graph.
     */
    public int edgesCount();

    /**
     * This method allows user to know is the Graph empty or not.
     * @return true if the Graph doesn't contain any vertex.
     * Otherwise, return false.
     */
    public boolean isEmpty();

    /**
     * Algorithm that determines the shortest paths from current
     * vertex to other vertices.
     * @param vertex is the required vertex
     * @return list of all shortest paths to the specified vertex,
     * that is sorted by increasing distance of vertices.
     */
    public List<Vertex<T>> dijkstra(Vertex<T> vertex);
}
