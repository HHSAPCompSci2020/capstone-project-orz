package cells;

/**
 * 
 * @author Jerry Wu
 *
 */
public class Cell {

	protected boolean isTraversable;
	protected int[] color;
	protected int[] originalColor;
	/**
	 * creates new Cell
	 */
	public Cell() {
		this.isTraversable = true;
		this.originalColor = new int[] { 255, 255, 255 };
		this.color = originalColor.clone();
	}

	/**
	 * returns if Cell is traversable
	 * 
	 * @return returns if Cell is traversable
	 */
	public boolean isTraversable() {
		return this.isTraversable;
	}

	/**
	 * @return array of length 3 which corresponds with RGB value of Cell color
	 */
	public int[] getColor() {
		return this.color;
	}
	
	public void setColor(int r, int g, int b) {
		this.color = new int[] {r, g, b};
	}
	
	public void revertColorToOriginalColor() {
		this.color = originalColor.clone();
	}
}
