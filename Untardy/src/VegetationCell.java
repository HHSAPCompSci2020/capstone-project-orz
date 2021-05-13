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
    }

    /**
     * @return array of length 3 which corresponds with RGB value of Cell color
     */
    public int[] fillColor() {
    	return new int[] {51, 153, 51};
    }
}
