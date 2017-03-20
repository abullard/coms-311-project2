import java.util.ArrayList;

/**
 * @author Jack Meyer (jacmeyer@iastate.edu)
 *
 * Will read a graph stored ina  file, implements Strongly Connected Components (SCC) algroithm, buid appropriate data
 * structures to answer the following queries efficiently:
 *      - out degree of a vertex
 *      - whether two vertices are in the same SCC
 *      - number of SCC's of the graph
 *      - size of the largest component
 *      - given a vertex v, find all verticies that belong to the same SCC as V
 *      - find shortest (BSF) path from a vertex V to U
 *
 */
public class GraphProcessor {

    /**
     * Creates efficient data structures
     *
     * @param graphData holds the absolute path of a file that stores a directed graph.
     */
    public GraphProcessor(String graphData) {

    }

    /**
     * returns the out degree of v
     *
     * @param v
     *
     * @return the out degree of v
     */
    public int outDegree(String v) {

        // TODO find the out degree of v

        return 0;
    }

    /**
     * Returns true if u and v belong to the same SCC; otherwise returns false
     *
     * @param u a vertex u
     * @param v a vertex v
     *
     * @return true if u and v belong to the same SCC
     *         false otherwise
     */
    public boolean sameComponent(String u, String v) {

        // TODO find if u and v belong to the same SCC

        return true;
    }

    /**
     * Return all of the verticies that belong to the same Strongly Connected Component of v (including v).
     *
     * @param v a vertex v
     */
    public ArrayList<String> componentVerticies(String v) {
        ArrayList<String> stronglyConnectedComponents = new ArrayList<>();

        // TODO find all of the verticies that belong to the same SCC of v

        return stronglyConnectedComponents;
    }

    /**
     * Returns the size of the largest component
     *
     * @return the size of the largest component
     */
    public int largestComponent() {

        // TODO find the size of the largest component

        return 0;
    }

    /**
     * Returns the number of strongly connected components
     *
     * @return the number of strongly connected components
     */
    public int numComponents() {

        // TODO find the number of strongly connected components

        return 0;
    }
}
