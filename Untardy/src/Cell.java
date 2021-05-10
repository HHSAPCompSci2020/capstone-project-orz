
public class Cell {
	
	private char cellType;
    protected boolean isTraversable;
	
	public Cell() {
		this.cellType = 0;
        this.isTraversable = true;
	}
	
	public Cell(char cellType) {
		this.cellType = cellType;
	}

    boolean getTraversable() {
        return this.isTraversable;
    }
	
	public char displayTest() {
//		if(cellType == 0) {
//			return ' ';
//		}
		return cellType;
	}
}
