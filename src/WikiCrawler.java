import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jack Meyer (jacmeyer@iastate.edu)
 */
public class WikiCrawler {

    private static final String BASE_URL = "https://en.wikipedia.org";

    private String seedUrl;

    private int max;

    private String fileName;

    private Queue<String> toProcessQueue = new ArrayDeque<>();

    private Set<String> visitedSet = new HashSet<>();

    private int connectionCount = 0;


    public WikiCrawler(String seedUrl, int max, String fileName) {
        this.seedUrl = seedUrl;
        this.max = max;
        this.fileName = fileName;
    }

    /**
     * Extracts the links from the document.
     *
     * Extract only wiki links (form /wiki/XXXX)
     *
     * Only extract links that appear after the first occurrence of the paragraph html tag
     *
     * The order in which the links in the returned array list must be exactly the same order in which they appear in
     * the doc
     * @param doc a string which represents the contents of a .html file
     *
     * @return a list of strings that are the links in the file
     */
    public ArrayList<String> extractLinks(String doc) {

        ArrayList<String> links = new ArrayList<>();

        // eliminate everything up until the first paragraph tag
        String tempDoc = doc.toLowerCase();
        int firstPIndex = tempDoc.indexOf("<p>");
        doc = doc.substring(firstPIndex, doc.length());

        // find all of the href tags
        Pattern pattern = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc);
        while (matcher.find()) {

            // add strings that start with /wiki/, and doesn't contain ':' or '#' to the links list.
            String tempString = matcher.group(1);
            if((tempString.startsWith("/wiki/")) && (!tempString.contains(":")) && (!tempString.contains("#"))) {

                if(!links.contains(matcher.group(1))) {
                    links.add(matcher.group(1));
                }
            }
        }

        return links;
    }


    /**
     * Contructs the web graph over following pages.
     *
     * Consider the first max many pages that are visited when you do a BFS with seedUrl. The program should construct
     * the web graph only over those pages, and writes the graph to the file fileName.
     */
    public void crawl() throws IOException, InterruptedException {


        // construct graph
        Graph graph = new Graph();

        String currentUrl = this.seedUrl;
        toProcessQueue.add(currentUrl);

        // while there are items in the process queue, process them
        while(!toProcessQueue.isEmpty()) {
            boolean full = false;
            visitedSet.add(currentUrl);


            // add the vertex to the graph
            graph.addVertex(currentUrl);

            // gets the links from the current document
            String doc = getHTMLStringFromLink(currentUrl);
            List<String> links = extractLinks(doc);

            // so long as the graph is not full, we want to add stuff to the process queue
            int i = 0;
            boolean justDoneLoading = false;
            while((graph.getVertices().size() < max) && i < links.size()) {

                String currentLink = links.get(i);

                // add it to the graph
                graph.addVertex(currentLink);

                // add the links to the queue if it is not in there and has yet to be visited
                if(!visitedSet.contains(currentLink) && (!toProcessQueue.contains(currentLink))) {
                    toProcessQueue.add(currentLink);
                }

                graph.addEdge(currentUrl, currentLink);

                i++;

                // if full, set flag for just done
                if(graph.getVertices().size() == max){
                    justDoneLoading = true;
                }
            }

            // check for if graph is full, if so set flag
            if(graph.getVertices().size() == max){
                full = true;
            }

            // if just done loading (we have reached the max), we want to continue to add edges between current url
            // and the links that are going to be processed. We do not want to keep adding new verticies. Therefore, we
            // check if the link is in the to process queue and then if it is, add an edge between the current url and
            // the link
            if(justDoneLoading == true) {
                for(i = i; i < links.size(); i++) {
                    if(toProcessQueue.contains(links.get(i))) {
                        graph.addEdge(currentUrl, links.get(i));
                    }
                }
                full = false;
            }

            // if already we have reached the max number of verticies, filter the links to only include the ones
            // that are verticies, then add edges between the current url and the filtered links
            if(full == true) {
                links = filterLinks(graph, links, currentUrl);
                graph.addAllEdges(currentUrl, links);
            }

            // remove the element from the queue and set the current element to the next one
            toProcessQueue.remove(currentUrl);
            currentUrl = toProcessQueue.peek();
        }


        // finally, write the graph to a file
        writeGraphToFile(graph);

    }

    /**
     * Writes a graph to the file specified in the constructor
     *
     * @param graph the graph to write to the file
     * @throws IOException
     */
    private void writeGraphToFile(Graph graph) throws IOException {
        File file = new File(this.fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.append(this.max + "\n");
        fileWriter.write(graph.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Filters links based on if they are already in the graph or not. When filtering, we only want links that are
     * apart of the graph. We also don't want duplicates, so we will filter out if they are eqaul to the current url.
     *
     * @param graph      the graph to get verticies from
     * @param links      the links to filter
     * @param currentUrl the current url
     * @return the filtered links
     */
    private List<String> filterLinks(Graph graph, List<String> links, String currentUrl) {

        Set vertexSet = new HashSet<>(graph.getVertices());
        List<String> filteredLinks = new ArrayList<>();

        for (int i = 0; i < links.size(); i++) {
            if(vertexSet.contains(links.get(i))) {
                if(!filteredLinks.contains(links.get(i)) && (!currentUrl.equals(links.get(i)))) {
                    filteredLinks.add(links.get(i));
                }
            }
        }

        return filteredLinks;
    }

    /**
     * Gets the html from a link
     *
     * @param url the url to get html from
     * @return the html in the form of a string
     * @throws IOException
     * @throws InterruptedException
     */
    private String getHTMLStringFromLink(String url) throws IOException, InterruptedException {

        // every 100 connection, we will sleep for three seconds
        if(connectionCount >= 100) {
            Thread.sleep(3000);
            connectionCount = 0;
        }

        // make the connection
        URL fullUrl = new URL(BASE_URL + url);
        InputStream inputStream = fullUrl.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // get the html
        String line;
        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }

        connectionCount++;
        return builder.toString();
    }

    /**
     * Gets the seed url
     *
     * @return the seed url
     */
    public String getSeedUrl() {
        return seedUrl;
    }

    /**
     * Sets the seed url
     *
     * @param seedUrl the seed url
     */
    public void setSeedUrl(String seedUrl) {
        this.seedUrl = seedUrl;
    }

    /**
     * Gets the max number of pages to be crawled
     *
     * @return the max number of pages to be crawled
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the max number of pages to be crawled
     * @param max the max number of pages to be crawled
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets the name of the file the graph will be written to
     *
     * @return the file the graph will be written to
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the name of the file the graph will be written to
     *
     * @param fileName the name of the file the graph will be written to
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
