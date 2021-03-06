package cells;

/**
 * 
 * @author Jerry Wu
 *
 */
public class PlayerCell extends Cell {

	/**
	 * creates new PlayerCell
	 */
	public PlayerCell() {
		this.isTraversable = false;
		this.originalColor = new int[] { 255, 0, 0 };
		this.color = this.originalColor.clone();
	}

	/**
	 * @return array of length 3 which corresponds with RGB value of Cell color
	 */
	public int[] getColor() {
		return this.color;
	}
}
