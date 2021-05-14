package cells;

/**
 * 
 * @author Jerry Wu
 *
 */
public class VegetationCell extends Cell {

	/**
	 * creates new VegetationCell
	 */
	public VegetationCell() {
		this.isTraversable = false;
		this.color = new int[] { 51, 153, 51 };
	}

	/**
	 * @return array of length 3 which corresponds with RGB value of Cell color
	 */
	public int[] getColor() {
		return this.color;
	}
}
