
public class Cell {
	
    protected boolean isTraversable;
	
	public Cell() {
        this.isTraversable = true;
	}

    public boolean isTraversable() {
        return this.isTraversable;
    }
	
	public int[] fillColor() {
    	return new int[] {255, 255, 255};
    }
}
