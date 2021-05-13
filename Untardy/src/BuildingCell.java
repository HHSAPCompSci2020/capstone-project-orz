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
    }
    
    /**
     * @return array of length 3 which corresponds with RGB value of Cell color
     */
    public int[] fillColor() {
    	return new int[] {204, 153, 255};
    }
}
