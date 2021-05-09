
public class Cell {
	
	private char cellType;
    protected boolean isTraversable;
	
	public Cell() {
		this.cellType = 0;
        isTraversable = true;
	}

    boolean getTraversable() {
        return this.isTraversable;
    }
	
	public Cell(char cellType) {
		this.cellType = cellType;
	}
	
	public char displayTest() {
		return cellType;
	}
}
