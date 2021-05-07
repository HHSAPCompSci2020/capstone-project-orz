
import processing.core.PApplet;

public class DrawingSurface extends PApplet {

    private Grid grid;

    public DrawingSurface() {
        grid = new Grid(0, 0, "testfiles/digital.txt");
    }

    public void draw() {
        background(255);
        fill(0);
        text("test, alpha build", 50, 50);
    }
}
