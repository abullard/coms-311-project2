import java.io.IOException;

public class Project2 {
    public static void main(String[] args) throws IOException, InterruptedException {

        WikiCrawler wikiCrawler = new WikiCrawler("/wiki/Complexity_theory", 500, "WikiCT.txt");
        wikiCrawler.crawl();
    }

}
