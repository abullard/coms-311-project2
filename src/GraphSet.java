import java.util.*;

/**
 * Created by jack on 3/15/17.
 * Austin Bullard
 */
public class GraphSet {
    private HashMap<String, HashSet<String>> adjecencyMatrix = new HashMap<>();
    private List<String> vertices = new ArrayList<>();

    public GraphSet() {}

    public void addVertex(String vertex) {
        if(!vertices.contains(vertex)) {
            this.vertices.add(vertex);
            this.adjecencyMatrix.put(vertex, new HashSet<>());
        }
    }

    public void addEdge(String fromVertex, String toVertex) {
        // protect against circular references
        if(!fromVertex.equals(toVertex)) {
            HashSet<String> edges = this.adjecencyMatrix.get(fromVertex);

            // if the vertex doesn't exit, make it, and add the edge
            if(edges == null) {
                this.adjecencyMatrix.put(fromVertex, new HashSet<>());
                this.adjecencyMatrix.get(fromVertex).add(toVertex);
            } else {
                edges.add(toVertex);
            }
        }
    }

    public void addAllEdges(String fromVertex, List<String> toVertexList) {
        HashSet<String> edges = this.adjecencyMatrix.get(fromVertex);

        // if the vertex doesn't exit, make it, and add the edge
        if(edges == null) {
            this.adjecencyMatrix.put(fromVertex, new HashSet<>(toVertexList));
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

    public HashMap<String, HashSet<String>> getAdjecencyMatrix() {
        return adjecencyMatrix;
    }

    public void setAdjecencyMatrix(HashMap<String, HashSet<String>> adjecencyMatrix) {
        this.adjecencyMatrix = adjecencyMatrix;
    }

    public List<String> getVertices() {
        return vertices;
    }

    public void setVertices(List<String> vertices) {
        this.vertices = vertices;
    }
}
