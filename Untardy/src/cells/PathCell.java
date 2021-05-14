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
		this.color = new int[] { 242, 242, 242 };
	}

	/**
	 * @return array of length 3 which corresponds with RGB value of Cell color
	 */
	public int[] getColor() {
		return this.color;
	}
}
