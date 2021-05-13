
public class EntranceCell extends Cell {
	private char buildingChar;
	
	/**
	 * creates new EntranceCell
	 * @param the
	 */
	public EntranceCell(char buildingChar) {
		this.isTraversable = true;
		this.buildingChar = buildingChar;
	}
	
	public int[] fillColor() {
		return new int[] {102, 51, 0};
	}
	
	public char getBuildingChar() {
		return buildingChar;
	}
}
