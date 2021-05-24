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
		this.originalColor = new int[] { 51, 153, 51 };
		this.color = this.originalColor.clone();
	}

	/**
	 * @return array of length 3 which corresponds with RGB value of Cell color
	 */
	public int[] getColor() {
		return this.color;
	}
}
