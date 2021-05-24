package cells;

/**
 * 
 * @author Jerry Wu
 *
 */
public class BuildingCell extends Cell {
	private char buildingChar;
	/**
	 * creates new BuildingCell
	 */
	public BuildingCell() {
		this.buildingChar = 0;
		this.isTraversable = false;
		this.originalColor = new int[] { 179, 179, 179 };
		this.color = originalColor.clone();
	}
	
	/**
	 * creates new BuildingCell with buildingChar 
	 * @param buildingChar the char of the building
	 */
	public BuildingCell(char buildingChar) {
		this.isTraversable = false;
		this.color = new int[] { 179, 179, 179 };
		this.buildingChar = buildingChar;
	}
	
	/**
	 * @return returns buildingChar
	 */
	public char getBuildingChar() {
		return  buildingChar;
	}
	
	
	/**
	 * sets buildingChar to newBuildingChar
	 * @param newBuildingChar the char to set the building character field to
	 */
	public void setBuildingChar(char newBuildingChar) {
		this.buildingChar = newBuildingChar;
	}

	/**
	 * @return array of length 3 which corresponds with RGB value of Cell color
	 */
	public int[] getColor() {
		return this.color;
	}
}
