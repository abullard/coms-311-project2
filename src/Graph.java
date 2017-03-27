import java.util.*;

/**
<<<<<<< HEAD
 * Created by jack on 3/15/17.
 * Austin Bullard
=======
 * @author Jack Meyer (jacmeyer@iastate.edu)
 *
 * A representation of a graph structure
>>>>>>> f5a42ec8825ba22455b0db1cb3d3bd38ded57a40
 */
public class Graph {

    private HashMap<String, ArrayList<String>> adjecencyMatrix = new HashMap<>();
    private List<String> vertices = new ArrayList<>();

    public Graph() {}

    /**
     * Adds a vertex to a graph and ensures that the vertex is not already in the graph
     * @param vertex the vertex to add
     */
    public void addVertex(String vertex) {
        if(!vertices.contains(vertex)) {
            this.vertices.add(vertex);
            this.adjecencyMatrix.put(vertex, new ArrayList<>());
        }
    }

    /**
     * Adds an edge to a graph, if the vertex's don't exist, it will make a new vertex
     * @param fromVertex the from vertex
     * @param toVertex the to vertex
     */
    public void addEdge(String fromVertex, String toVertex) {
        // protect against circular references
        if(!fromVertex.equals(toVertex)) {
            List<String> edges = this.adjecencyMatrix.get(fromVertex);

            // if the vertex doesn't exit, make it, and add the edge
            if(edges == null) {
                this.adjecencyMatrix.put(fromVertex, new ArrayList<>());
                this.adjecencyMatrix.get(fromVertex).add(toVertex);
            } else {
                edges.add(toVertex);
            }
        }
    }

    /**
     * Adds all edges from a list
     * @param fromVertex the from vertex
     * @param toVertexList the to vertex list
     */
    public void addAllEdges(String fromVertex, List<String> toVertexList) {
        List<String> edges = this.adjecencyMatrix.get(fromVertex);

        // if the vertex doesn't exit, make it, and add the edge
        if(edges == null) {
            this.adjecencyMatrix.put(fromVertex, new ArrayList<>(toVertexList));
        } else {
            edges.addAll(toVertexList);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < vertices.size(); i++) {
            List<String> edges = new ArrayList<>(this.adjecencyMatrix.get(vertices.get(i)));
            for (int j = 0; j < edges.size(); j++) {
                builder.append(vertices.get(i) + " " + edges.get(j) + "\n");
            }
        }
        return builder.toString();
    }

    public HashMap<String, ArrayList<String>> getAdjecencyMatrix() {
        return adjecencyMatrix;
    }

    public void setAdjecencyMatrix(HashMap<String, ArrayList<String>> adjecencyMatrix) {
        this.adjecencyMatrix = adjecencyMatrix;
    }

    public List<String> getVertices() {
        return vertices;
    }

    public void setVertices(List<String> vertices) {
        this.vertices = vertices;
    }
}
