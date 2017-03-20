import java.io.IOException;

public class Project2 {
    public static void main(String[] args) throws IOException {

        WikiCrawler wikiCrawler = new WikiCrawler("/wiki/Complexity_theory", 20, "WikiCT.txt");
        wikiCrawler.crawl();
    }

}
