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
        this.isTraversable = true;
    }
    
    /**
     * @return array of length 3 which corresponds with RGB value of Cell color
     */
    public int[] getColor() {
    	return new int[] {255, 0, 0};
    }
}
