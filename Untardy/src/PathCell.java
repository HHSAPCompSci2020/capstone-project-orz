/**
 * 
 * @author Jerry Wu
 *
 */
public class PathCell extends Cell {
	
	/**
	 * creates new PathCell
	 */
    public PathCell() {
        this.isTraversable = true;
    }
    
    /**
     * @return array of length 3 which corresponds with RGB value of Cell color
     */
    public int[] getColor() {
    	return new int[] {242, 242, 242};
    }
}
