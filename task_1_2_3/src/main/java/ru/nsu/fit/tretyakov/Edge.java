package ru.nsu.fit.tretyakov;

/**
 * This class is the edge of the Graph. Allows user to set and get
 * ingoing and outgoing vertices and weight.
 * @param <T> is the required type of the object that
 *            will be stored in the Vertex as data.
 */
public class Edge<T> {
    private Vertex<T> ingoingVertex;
    private Vertex<T> outgoingVertex;
    private Integer weight;

    /**
     * Constructor of the edge. Sets vertices and weight of the edge.
     * @param ingoingVertex is the ingoing vertex of the edge.
     * @param outgoingVertex is the outgoing vertex of the edge.
     * @param weight is the required weight of the edge.
     */
    public Edge(Vertex<T> ingoingVertex, Vertex<T> outgoingVertex, Integer weight) {
        this.ingoingVertex = ingoingVertex;
        this.outgoingVertex = outgoingVertex;
        this.weight = weight;
    }

    /**
     * Another constructor of the edge that won't require weight.
     * @param ingoingVertex is the ingoing vertex of the edge.
     * @param outgoingVertex is the outgoing vertex of the edge.
     */
    public Edge(Vertex<T> ingoingVertex, Vertex<T> outgoingVertex) {
        this.ingoingVertex = ingoingVertex;
        this.outgoingVertex = outgoingVertex;
        this.weight = 1;
    }

    /**
     * Getter of the weight of the edge.
     * @return weight of the edge.
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Setter of the weight of the edge.
     * @param weight is the required weight of the edge.
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * Getter of the ingoing vertex of the edge.
     * @return ingoing vertex of the edge.
     */
    public Vertex<T> getIngoingVertex() {
        return ingoingVertex;
    }

    /**
     * Setter of the ingoing vertex of the edge.
     * @param ingoingVertex is the required ingoing vertex of the edge.
     */
    public void setIngoingVertex(Vertex<T> ingoingVertex) {
        this.ingoingVertex = ingoingVertex;
    }

    /**
     * Getter of the outgoing vertex of the edge.
     * @return outgoing vertex of the edge.
     */
    public Vertex<T> getOutgoingVertex() {
        return outgoingVertex;
    }

    /**
     * Setter of the outgoing vertex of the edge.
     * @param outgoingVertex is the required outgoing
     *                       vertex of the edge.
     */
    public void setOutgoingVertex(Vertex<T> outgoingVertex) {
        this.outgoingVertex = outgoingVertex;
    }
}
