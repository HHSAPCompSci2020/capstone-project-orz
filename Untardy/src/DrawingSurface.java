
import processing.core.PApplet;

public class DrawingSurface extends PApplet {
	
	private Grid grid;
	
	public DrawingSurface() {
		grid = new Grid(0, 0, "testfiles/digital.txt");
	}
	
	public void draw() {
		background(255);
		fill(0);
		if(grid != null) {
			grid.draw(this, 0, 0, height, height);
		}
	}

}
