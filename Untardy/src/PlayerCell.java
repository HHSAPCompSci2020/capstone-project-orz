
public class PlayerCell extends Cell {
    public PlayerCell() {
        this.isTraversable = true;
    }
    
    public int[] fillColor() {
    	return new int[] {255, 255, 0};
    }
}
