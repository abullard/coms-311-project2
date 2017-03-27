/**
 * Created by Austin Bullard on 3/27/2017.
 */
public class Timer {

    private String vertex = "";
    private int time = 0;

    public Timer(int counter, String v) {
        vertex = v;
        time = counter;
    }

    public int getTime() {
        return time;
    }

    public String getVertex() {
        return vertex;
    }

}
