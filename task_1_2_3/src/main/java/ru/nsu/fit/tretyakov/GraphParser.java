package ru.nsu.fit.tretyakov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Parser-class of the Graph. Reads data from file and creates Graph interface,
 * that contains integer objects in its vertices.
 */

public class GraphParser {

    private File file;

    /**
     * Constructor of the GraphParser that sets the file address.
     * @param file is the file that will be parsed.
     */
    public GraphParser(File file) {
        this.file = file;
    }

    private void parse(Graph<Integer> graph)
            throws FileNotFoundException {

        Set<String> stringVertices = new HashSet<>();
        Set<String> stringEdges = new HashSet<>();
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {

            String curLine = sc.nextLine();
            List<String> tokens = List.of(curLine.split(" "));

            if (tokens.size() > 1) {

                // adding vertices
                String name = tokens.get(0);
                if (name.contains("v") && !stringVertices.contains(name)) {
                    stringVertices.add(name);

                    graph.addVertex(new Vertex<>(Integer.parseInt(tokens.get(2))));
                }

                // adding edges
                else if (name.contains("e") && !stringEdges.contains(name)) {

                    List<String> edgeData = List.of(tokens.get(2).split(","));
                    stringEdges.add(name);

                    Integer inValue = Integer.parseInt(edgeData.get(0));
                    Integer outValue = Integer.parseInt(edgeData.get(1));

                    Vertex<Integer> in = null, out = null;
                    boolean findIn = false, findOut = false;

                    // finding in and out vertices
                    for (var vertex : graph.toVertexArray()) {
                        if (findOut && findIn) break;
                        if (vertex.getData().equals(inValue)) {
                            in = vertex;
                            findIn = true;
                        } else if (vertex.getData().equals(outValue)) {
                            out = vertex;
                            findOut = true;
                        }
                    }

                    int weight = 1;

                    if (edgeData.size() == 3) {
                        weight = Integer.parseInt(edgeData.get(2));
                    }

                    Edge<Integer> edge = new Edge<>(in, out, weight);
                    graph.addEdge(edge);
                }
            }
        }
    }

    /**
     * @return graph interface that was read from file.
     * @throws FileNotFoundException if file doesn't exist.
     */
    public Graph<Integer> parseGraph()
            throws FileNotFoundException {

        Scanner sc = new Scanner(file);
        Graph<Integer> graph = null;

        if (sc.hasNextLine()) {

            String type = sc.nextLine();

            switch (type){
                case ("adjList"):
                    graph = new GraphAdjacencyList<Integer>(true);
                    break;
                case ("adjMatrix"):
                    graph = new GraphAdjacencyMatrix<Integer>(true);
                    break;
                case ("incMatrix"):
                    graph = new GraphIncidentMatrix<Integer>(true);
                    break;
            }
            parse(graph);
        }
        return graph;
    }
}
