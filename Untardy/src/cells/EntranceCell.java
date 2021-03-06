package cells;

/**
 * 
 * @author Jerry Wu
 *
 */
public class EntranceCell extends Cell {
	private char buildingChar;

	/**
	 * creates new EntranceCell
	 * 
	 * @param buildingChar character name of building
	 */
	public EntranceCell(char buildingChar) {
		this.isTraversable = true;
		this.buildingChar = buildingChar;
		this.originalColor = new int[] { 251, 201, 1 };
		this.color = this.originalColor.clone();
	}

	/**
	 * @return array of length 3 which corresponds with RGB value of Cell color
	 */
	public int[] getColor() {
		return this.color;
	}

	/**
	 * @return building's char that this Cell corresponds with
	 */
	public char getBuildingChar() {
		return buildingChar;
	}
}
