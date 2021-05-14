package cells;

/**
 * 
 * @author Jerry Wu
 *
 */
public class FriendCell extends Cell {

	/**
	 * creates new FriendCell
	 */
	public FriendCell() {
		this.isTraversable = true;
		this.color = new int[] { 255, 192, 203 };
	}

	/**
	 * @return array of length 3 which corresponds with RGB value of Cell color
	 */
	public int[] getColor() {
		return this.color;
	}
}
