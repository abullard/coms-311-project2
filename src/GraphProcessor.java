import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Jack Meyer (jacmeyer@iastate.edu)
 * Austin Bullard
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

    private int numVertices;
    private GraphSet g;
    private GraphSet reverse;
    private HashSet<String> revhs;
    private HashSet<String> orderhs;
    private HashSet<String> scchs;
    private ArrayList<HashSet<String>> scc;
    private HashSet<String> n;
    private int counter;
    private ArrayList<Timer> times;

    /**
     * Creates efficient data structures
     *
     * @param graphData holds the absolute path of a file that stores a directed graph.
     */
    public GraphProcessor(String graphData) throws FileNotFoundException {
        g = new GraphSet();
        File file = new File(graphData);
        Scanner scan = new Scanner(file);

        //obtain number of vertices, this is a required first line of graphData
        numVertices = scan.nextInt();
        //advance the cursor 1 line
        scan.nextLine();

        //Parse each line of the text file
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            lineParser(line);
        }

        //Compute the SCC of the graph
        computeSCC();
    }

    /**
     * Private helper method for parsing lines of a text file.
     * @param line
     *  the line to be parsed
     */
    private void lineParser(String line) {
        Scanner scan = new Scanner(line);

        //grab both verticies from the line
        String fromVertex, toVertex;
        fromVertex = scan.next();
        toVertex = scan.next();

        //add the vertex to the graph
        g.addVertex(fromVertex);
        //add the edge between both vertices to the graph
        g.addEdge(fromVertex, toVertex);
    }

    /**
     * Computes the Strongly Connected Components for a given graph
     */
    private void computeSCC() {
        //computer order of vertices
        computeOrder();
        //unmark every vertex of V
        scchs = new HashSet<>();
        scc = new ArrayList<>();

        for (int i = times.size() - 1; i >= 0; i--) {
            Timer t = times.get(i);
            String v = t.getVertex();
            if(!scchs.contains(v)){
                //reusing revhs
                revhs = new HashSet<>();
                SccDFS(t.getVertex());
                scc.add(revhs);
            }
        }
    }

    /**
     *  Input G = (V, E).
     *  Compute Gr = (V, Er).
     *  Unmark every vertex of V .
     *  counter = 0;
     *  if v is unmarked, Call FinishDFS(Gr, v)
     *
     * Computes the order of vertices of a graph
     * @return
     *  A list of vertices
     */
    private void computeOrder() {
        //construct ArrayList for order of finish times
        times = new ArrayList<>();
        //construct HashSet for marking vertices (unmarks all vertices)
        orderhs = new HashSet<>();

        //reverse the graph
        reverse = new GraphSet();
        revGraph(g.getAdjecencyMatrix());
        counter = 0;

        //get the adjacency matrix for the reverse Graph
        HashMap<String, HashSet<String>> hm = reverse.getAdjecencyMatrix();
        //create iterator for elements in adjacency matrix
        Iterator iter = hm.keySet().iterator();

        while(iter.hasNext()) {
            String v = (String) iter.next();
            if(!orderhs.contains(v)) {
                finishDFS(v);
            }
        }
    }

    /**
     * Reverses all edges of a Graph
     * @param hm
     *  adjacency matrix for all vertices of graph
     * @return
     *  the reversed graph
     */
    private void revGraph(HashMap<String, HashSet<String>> hm) {
        //HashSet for marking vertices as visited
        revhs = new HashSet<>();

        //Creating iterator soley to get first vertex
        Iterator iter = hm.entrySet().iterator();

        //obtain the key, value map entry
        Map.Entry pair = (Map.Entry) iter.next();

        //get the key, or vertex
        String v = (String) pair.getKey();

        //get the values, or outward edges
        HashSet<String> set = findListofEdges(v, false);

        //reverse the vertices outward edges
        reverseVertex(v, set);
    }

    /**
     * Recursively reverses each edge of each vertex
     * @param v
     *  vertex
     * @param set
     *  list of outward edges from v
     */
    private void reverseVertex(String v, HashSet<String> set) {
        if(!revhs.contains(v)) {
            //mark v as visited
            revhs.add(v);
            reverse.addVertex(v);
            n = new HashSet<>();
            for (String temp : set) {
                //if edge to vertex from v has not been visited
                if (!n.contains(temp)) {
                    //add it to Set n
                    n.add(temp);
                    //add the reversed edge to the reversed graph
                    reverse.addVertex(temp);
                    reverse.addEdge(temp, v);
                }
            }
            for (String temp : n) {
                HashSet<String> nextEdges = findListofEdges(temp, false);
                reverseVertex(temp, nextEdges);
            }
        }
    }

    /**
     * Function finds list of edges for given vertex
     * @param v
     *  given vertex
     * @return
     *  list of outwards edges of given vertex
     */
    private HashSet<String> findListofEdges(String v, boolean rev) {
        if(rev) {
            HashMap<String, HashSet<String>> hm = reverse.getAdjecencyMatrix();
            HashSet<String> set = new HashSet<>();
            if(hm.containsKey(v)) {
                set = hm.get(v);
            }

            return set;
        } else {
            HashMap<String, HashSet<String>> hm = g.getAdjecencyMatrix();
            HashSet<String> set = new HashSet<>();
            if(hm.containsKey(v)) {
                set = hm.get(v);
            }

            return set;
        }
    }

    /**
     * Finds the finish time of all vertices in the graph
     * @param v
     */
    private void finishDFS(String v) {
        //mark vertex
        orderhs.add(v);

        //for every u such that <v, u> /in E
        for(String temp : findListofEdges(v, true)) {
            //if u is unmarked, DFS(G, u)
            if(!orderhs.contains(temp)) {
                finishDFS(temp);
            }
        }
        //increment time
        counter++;

        //FinishTime[v] = counter
        Timer vertex = new Timer(counter, v);
        times.add(vertex);
    }

    /**
     * Strongly connected Components DFS
     * @param v
     *  Vertex
     */
    private void SccDFS(String v) {
        scchs.add(v);
        revhs.add(v);

        for(String temp : findListofEdges(v, false)) {
            if(!scchs.contains(temp)) {
                SccDFS(temp);
            }
        }
    }

    /**
     * returns the out degree of v
     *
     * @param v
     *
     * @return the out degree of v
     */
    public int outDegree(String v) {
        HashMap<String, HashSet<String>> map = g.getAdjecencyMatrix();
        if(map.containsKey(v)){
            return map.get(v).size();
        }

        return -1;
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
        for(HashSet<String> temp : scc) {
            if(temp.contains(u) && temp.contains(v)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return all of the vertices that belong to the same Strongly Connected Component of v (including v).
     * @param v
     *  a vertex v
     */
    public ArrayList<String> componentVertices(String v) {
        ArrayList<String> list = new ArrayList<>();

        for(HashSet<String> temp : scc) {
            if(temp.contains(v)) {
                Iterator iter = temp.iterator();
                while(iter.hasNext()) {
                    list.add((String) iter.next());
                }
            }
        }

        return list;
    }

    /**
     * Returns the size of the largest component
     * @return
     *  the size of the largest component
     */
    public int largestComponent() {
        int max = 0;
        for(HashSet<String> temp : scc) {
            if(temp.size() > max) {
                max = temp.size();
            }
        }

        return max;
    }

    /**
     * Returns the number of strongly connected components
     * @return
     *  the number of strongly connected components
     */
    public int numComponents() {
        return scc.size();
    }

    /**
     * Calculates the BFS path from u to v
     * @param u
     *  starting vertex
     * @param v
     *  ending vertex
     * @return
     *  ArrayList of strings, bfs path from u to v
     */
    public ArrayList<String> bfsPath(String u, String v) {
        HashMap<String, String> map = new HashMap<>();
        LinkedList<String> q = new LinkedList<>();
        ArrayList<String> list = new ArrayList<>();

        q.add(u);
        map.put(u, null);

        while(!q.isEmpty()) {
            String current = q.getFirst();
            q.removeFirst();
            if(current.equals(v)) {
                while(map.get(current) != null) {
                    list.add(current);
                    current = map.get(current);
                }
                list.add(current);
            } else {
                for(String temp : findListofEdges(current, false)) {
                    q.add(temp);
                    map.put(temp, current);
                }
            }
        }
        return reverseList(list);
    }

    /**
     * Reverses an ArrayList<String>
     * @param list
     *  the list to be reversed
     * @return
     *  the reversed list
     */
    private ArrayList<String> reverseList(ArrayList<String> list) {
        ArrayList<String> arr = new ArrayList<>();
        for(int i = list.size() - 1; i >= 0; i--) {
            arr.add(list.get(i));
        }
        return arr;
    }
}
