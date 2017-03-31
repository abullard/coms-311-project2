import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Austin Bullard on 3/25/2017.
 * abullard
 */
public class test {
    public static void main(String[] args) throws IOException, InterruptedException {
//        WikiCrawler wc = new WikiCrawler("/wiki/Computer_Science", 500, "WikiCS.txt");
//        wc.crawl();
        GraphProcessor gp = new GraphProcessor("WikiCS.txt");

        System.out.println("Number of components of the graph: " + gp.numComponents());

        System.out.println("Size of largest component: " + gp.largestComponent());



    }

}
