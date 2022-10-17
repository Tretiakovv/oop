package ru.nsu.fit.tretyakov;

import static java.lang.Float.POSITIVE_INFINITY;

/**
 * This class is the vertex of the Graph. Contains data and allows user
 * to get data from the vertex, set data to the vertex and get / set distance
 * from the current vertex in the Dijkstra algorithm.
 * @param <T> is the required type of the object that
 *           will be stored in the Vertex as data.
 */
public class Vertex<T>{
    private T data;
    private Float distance;

    /**
     * Constructor of the vertex. Sets data to the vertex.
     * @param data is the required data that will be stored in
     *             the vertex.
     */
    public Vertex(T data) {
        this.data = data;
        this.distance = (float)0;
    }

    protected Float getDistance() {
        return distance;
    }

    protected void setDistance(Float distance) {
        this.distance = distance;
    }

    /**
     * Getter of the data in vertex.
     * @return data
     */
    public T getData() {
        return data;
    }

    /**
     * Setter of the data in vertex.
     * @param data is the data that will be set in
     *             the vertex.
     */
    public void setData(T data) {
        this.data = data;
    }
}
