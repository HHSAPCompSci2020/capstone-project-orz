
import processing.core.PApplet;

/**
 * 
 * @author Justin Chiang
 *
 */
public class DrawingSurface extends PApplet {

    private Grid grid;
    
  	public int time;
  	public static final int wait = 100;
  	
    private boolean intro;
    private boolean tardy;
    private boolean finish;
    
    private PlayerData playerData;
    private Clock clock;

    public DrawingSurface() {
        grid = new Grid(20, 20, "testfiles/digital.txt");
        intro = true;
        tardy = false;
        finish = false;
        playerData = new PlayerData();
        clock = new Clock(12, 58, "AM");
    }
    
    public void setup() {
		time = millis();
	}

    public void draw() {
    	
    	background(255);
    	fill(0);
    	
    	if(intro) {
    		//Display introduction screen until clicked off of
    	}
    	
    	if(tardy) {
    		//Pop up telling player they got a tardy, then restarts the day
    	}
    	
    	if(finish) {
    		//Display victory screen with player score
    	}
    	
    	//Display regular game
    	if(grid != null) {
    		grid.draw(this, 0, 0, height, height);
    	}
        text(clock.displayTime(), width*3/4, height*1/8);
        text(playerData.printSchedule(), width*3/4, height*3/8);
        
        //time updates with every second, use with clock methods to update clock
  		if(millis() - time >= wait) {
  			clock.updateTime();
  			time = millis();
  		}
    }
    
    public void mousePressed() {
    	if(mouseButton == LEFT) {
    		if(intro) { //Click anywhere to exit introduction screen
    			intro = false;
        	}
    	}
    }
}