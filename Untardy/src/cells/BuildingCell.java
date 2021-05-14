package cells;

/**
 * 
 * @author Jerry Wu
 *
 */
public class BuildingCell extends Cell {

	/**
	 * creates new BuildingCell
	 */
	public BuildingCell() {
		this.isTraversable = false;
		this.color = new int[] { 179, 179, 179 };
	}

	/**
	 * @return array of length 3 which corresponds with RGB value of Cell color
	 */
	public int[] getColor() {
		return this.color;
	}
}
