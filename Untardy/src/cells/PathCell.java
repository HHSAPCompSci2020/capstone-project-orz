package cells;

/**
 * 
 * @author Jerry Wu
 *
 */
public class PathCell extends Cell {

	/**
	 * creates new PathCell
	 */
	public PathCell() {
		this.isTraversable = true;
		this.originalColor = new int[] { 242, 242, 242 };
		this.color = this.originalColor.clone();
	}

	/**
	 * @return array of length 3 which corresponds with RGB value of Cell color
	 */
	public int[] getColor() {
		return this.color;
	}
}
