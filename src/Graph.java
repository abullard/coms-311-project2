import java.util.*;

/**
 * Created by jack on 3/15/17.
 */
public class Graph {

    private HashMap<String, ArrayList<String>> adjecencyMatrix = new HashMap<>();

    private Set<String> visitedSet = new HashSet<>();

    private List<String> vertices = new ArrayList<>();

    public Graph() {

    }

    public void addVertex(String vertex) {

        this.vertices.add(vertex);
        this.adjecencyMatrix.put(vertex, new ArrayList<>());
        this.visitedSet.add(vertex);

    }

    public void addEdge(String fromVertex, String toVertex) {

        // protect against circular references
        if(!fromVertex.equals(toVertex)) {
            List<String> edges = this.adjecencyMatrix.get(fromVertex);

            // if the vertex doesn't exit, make it, and add the edge
            if(edges == null) {
                this.adjecencyMatrix.put(fromVertex, new ArrayList<>());
                this.adjecencyMatrix.get(fromVertex).add(toVertex);
            }

            else {
                edges.add(toVertex);
            }

        }
    }

    public void addAllEdges(String fromVertex, List<String> toVertexList) {
        List<String> edges = this.adjecencyMatrix.get(fromVertex);

        // if the vertex doesn't exit, make it, and add the edge
        if(edges == null) {
            this.adjecencyMatrix.put(fromVertex, new ArrayList<>(toVertexList));
        }

        else {
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
}
