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
    }
    
    /**
     * @return array of length 3 which corresponds with RGB value of Cell color
     */
    public int[] getColor() {
    	return new int[] {255, 192, 203};
    }
}
