import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Austin Bullard on 3/25/2017.
 * abullard
 */
public class test {
    public static void main(String[] args) throws FileNotFoundException{
        GraphProcessor gp = new GraphProcessor("hello.txt");
        System.out.println("Same Component: " + gp.sameComponent("C", "B"));
        System.out.println("Largest Component: " + gp.largestComponent());
        System.out.println("Component Vertices(A): " + gp.componentVertices("A"));
        System.out.println("Component Vertices(B): " + gp.componentVertices("B"));
        System.out.println("Number Component: " + gp.numComponents());
        System.out.println();
        System.out.println(gp.bfsPath("A", "G"));
    }

}
