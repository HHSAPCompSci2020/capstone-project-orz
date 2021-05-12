public class CellFactory {
    public CellFactory() {

    }

    public Cell generateCell(char c) {
        switch (c) {
            case 'P':
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
