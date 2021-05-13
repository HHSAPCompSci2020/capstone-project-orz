/**
 * 
 * @author jerry
 *
 */
public class CellFactory {
	
	/**
	 * creates new CellFactory
	 */
    public CellFactory() {

    }

    /**
     * generates new Cell based on character type
     * @param c character type
     * @return returns new Cell based on character type
     */
    public Cell generateCell(char c) {
    	if (Character.isLetter(c)) {
    		return new EntranceCell(c);
    	}
        switch (c) {
            case '@':
                return new PlayerCell();
            case '*':
                return new BuildingCell();
            case ' ':
                return new PathCell();
            case '.':
                return new VegetationCell();
            default:
                return new Cell();
        }
    }
}
