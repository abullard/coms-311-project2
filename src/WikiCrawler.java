import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jack on 3/10/17.
 */
public class WikiCrawler {

    private static final String BASE_URL = "https://en.wikipedia.org";

    private String seedUrl;

    private int max;

    private String fileName;

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
        doc = doc.substring(doc.indexOf("<p>"), doc.length());

        // find all of the href tags
        Pattern pattern = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc);
        while (matcher.find()) {

            // add strings that start with /wiki/, and doesn't contain ':' or '#' to the links list.
            String tempString = matcher.group(1);
            if((tempString.startsWith("/wiki/")) && (!tempString.contains(":")) && (!tempString.contains("#"))) {
                links.add(matcher.group(1));
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
    public void crawl() throws IOException {

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
