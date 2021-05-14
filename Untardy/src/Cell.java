/**
 * 
 * @author jerry
 *
 */
public class Cell {
	
    protected boolean isTraversable;
	
    /**
     * creates new Cell
     */
	public Cell() {
        this.isTraversable = true;
	}

	/**
	 * returns if Cell is traversable
	 * @return returns if Cell is traversable 
	 */
    public boolean isTraversable() {
        return this.isTraversable;
    }
	
    /**
     * @return array of length 3 which corresponds with RGB value of Cell color
     */
	public int[] getColor() {
    	return new int[] {255, 255, 255};
    }
}
