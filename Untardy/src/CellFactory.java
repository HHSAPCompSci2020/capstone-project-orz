public class CellFactory {
    public CellFactory() {

    }

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
