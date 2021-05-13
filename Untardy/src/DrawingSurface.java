
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import processing.core.PApplet;
import processing.core.PFont;

/**
 * 
 * @author Justin Chiang
 *
 */
public class DrawingSurface extends PApplet {

    private Grid grid;
    
    private PFont clockFont;
    private PFont regFont;

    public int time;
    public static final int wait = 100;

    private boolean intro;
    private boolean tardy;
    private boolean finish;
    private boolean cover;

    private PlayerData playerData;
    private Clock clock;
    
    private int entranceRow;
    private int entranceCol;

    public DrawingSurface() {
        grid = new Grid(20, 20, "testfiles/digital.txt");
        intro = true;
        tardy = false;
        finish = false;
        cover = true;
        playerData = new PlayerData();
        clock = new Clock(8, 45, "AM");
        
        entranceRow = 17;
        entranceCol = 3;
    }

    public void setup() {
    	clockFont = createFont("DS-DIGI.TTF", 20);
    	regFont = createFont("Oswald-VariableFont_wght.ttf", 20);
        time = millis();
    }

    public void draw() {

        background(211, 211, 211);
        fill(0);
        
        

        if (intro) {
        }
        
        
        //CASES
        
        //Tardy case
        if (tardy) {
        	//Prompts user to restart or quit game if the player reaches 3 tardies
        	if(playerData.getTardies() == 2) {
        		String[] options = new String[] {"Play Again", "Quit"};
        		int choice = JOptionPane.showOptionDialog(frame, "You've received three tardies! Do you want to restart or quit the game?", 
        				"Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        		
        		if(choice == 0) {
        			playerData.restartGame();
        		}
        		else {
        			exit();
        		}
        		
        	}
        	//Restarts the day if the player has less than 3 tardies
        	else {
        		JOptionPane.showMessageDialog(frame, "You were tardy to class! You've received a tardy, and will restart the day.");
    			playerData.restartDay();
        	}
        	
        	if (playerData.getCurrentPeriod() == 1) {
        		//Put player at entrance of school
        		 grid.setPlayerLoc(entranceRow, entranceCol);
        	}
        	
        	clock.setTime(8, 45, 0, "AM");
        	clock.setElapsedSec(0);
        	cover = true;
        	tardy = false;
        }

        //Win condition
        if (finish) {
        	
        	//Completed day
        	if(playerData.getCurrentPeriod() == 5) {
        		
        		//Congratulates the player for beating the game if completed Friday schedule
        		if(playerData.getCurrentDayName() == "Friday") {
            		String[] options = new String[] {"Play Again", "Quit"};
            		int choice = JOptionPane.showOptionDialog(frame, "Congratulations! You beat the game with a score of " + playerData.getScore() + " points. "
            				+ "Do you want to restart or quit the game?", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            		
            		if(choice == 0) {
            			playerData.restartGame();
            			tardy = false;
            		}
            		else {
            			exit();
            		}
            	}
        		
        		//Congratulates the player for beating the current day's schedule 
            	else {
            		JOptionPane.showMessageDialog(frame, "You completed the day! You earned " + playerData.getDayScore() + " points, and will now progress to the next day.");
        			playerData.nextDay();
        			
            	}
        		
        		grid.setPlayerLoc(entranceRow, entranceCol);
        		clock.setTime(8, 45, 0, "AM");
        	}
        	
        	//Completed period
        	else {
        		
        		//Congratulates the player for beating the current period, configures settings for next period
        		String className = playerData.getSchedule(playerData.getCurrentDayNum())[playerData.getCurrentPeriod()-1].substring(
        				playerData.getSchedule(playerData.getCurrentDayNum())[playerData.getCurrentPeriod()-1].indexOf(':') + 2);
        		if(playerData.getCurrentPeriod() == 1) {
        			JOptionPane.showMessageDialog(frame, "You made it on time! " + className + " and tutorial were over in an instant, don't be late to your next class!");
        			clock.setTime(10, 20, 0, "AM");
        		}
        		else if(playerData.getCurrentPeriod() == 2) {
        			JOptionPane.showMessageDialog(frame, "You made it on time! " + className + " and brunch were over in an instant, don't be late to your next class!");
        			clock.setTime(11, 25, 0, "AM");
        		}
        		else if(playerData.getCurrentPeriod() == 3) {
        			JOptionPane.showMessageDialog(frame, "You made it on time! " + className + " was over in an instant, don't be late to your next class!");
        			clock.setTime(12, 15, 0, "PM");
        		}
        		else if(playerData.getCurrentPeriod() == 4) {
        			JOptionPane.showMessageDialog(frame, "You made it on time! " + className + " and lunch were over in an instant, don't be late to your next class!");
        			clock.setTime(1, 45, 0, "PM");
        		}
        		playerData.nextPeriod();
        	}
        	clock.setElapsedSec(0);
        	cover = true;
        	finish = false;
        }

        
        
        //GAME DISPLAY
        
        // Display the game grid on the left side of the screen
        if (grid != null) {
            grid.draw(this, 0, 0, height, height);
        }
        
        //Cover the grid if the player is not yet ready to play the game
        if(cover) {
        	pushStyle();
        	
        	fill(255);
        	rect(0, 0, height, height);
        	
        	fill(0);
        	textFont(regFont);
            textSize(20);
            textAlign(CENTER);
            text("This cover will hide the map until you're ready to play.", height * 1 / 2, height * 1 / 2);
            text("Press the space bar when you're ready to play!", height * 1 / 2, (height * 1 / 2) + 24);
            
            popStyle();
        }
        else {
        	//Time mechanic
        	 if (millis() - time >= wait) {
                 clock.updateTime();
                 time = millis();
             }
        }
        
        
        //Text display on the right side of the screen
        
        
        //Displays clock
        pushStyle();
        textFont(clockFont);
        fill(255, 0, 0);
        textSize(34);
        text("Current Time", height + 20, height * 1 / 16);
        textSize(40);
        text(clock.displayTime(), height + 20, (height * 1 / 16) + 38);
        popStyle();
        
        
        //Displays current objective of which period to get to
        pushStyle();
        textFont(regFont);
        fill(0, 0, 0);
        textSize(22);
        
        String classTime = "";
        if(playerData.getCurrentPeriod() == 1) {
        	classTime = "8:50 AM";
        	text("Objective: You're almost late! Get to period " + playerData.getCurrentPeriod(), height + 20, height * 1 / 4);
        	text("by " + classTime + "!", height + 20, (height * 1 / 4) + 26);
        }
        else {
        	if(playerData.getCurrentPeriod() == 2) {
            	classTime = "10:25 AM";
            }
            else if(playerData.getCurrentPeriod() == 3) {
            	classTime = "11:30 AM";
            }
            else if(playerData.getCurrentPeriod() == 4) {
            	classTime = "12:20 PM";
            }
            else if(playerData.getCurrentPeriod() == 5) {
            	classTime = "1:50 PM";
            }
        	text("Objective: Get from period " + (playerData.getCurrentPeriod()-1) + " to period " + (playerData.getCurrentPeriod()), height + 20, height * 1 / 4); 
        	text("by " + classTime + "!", height + 20, (height * 1 / 4) + 26);
        }
        
        //Displays current day's schedule
        text("Class Schedule", height + 20, height * 4 / 10);
        line(height + 20, height * 4 / 10, (height + 20) + (textWidth("Class Schedule")), height * 4 / 10);
		for(int i=0; i<playerData.getSchedule(playerData.getCurrentDayNum()).length; i++) {
			if(i < playerData.getCurrentPeriod()-1) {
				fill(0, 153, 51);
			}
			else if(i == playerData.getCurrentPeriod()-1) {
				fill(255, 128, 0);
			}
			text("Period " + (i+1) + ": " + playerData.getSchedule(playerData.getCurrentDayNum())[i], height + 20, (height * 4 / 10) + ((i+1) * 26));
			fill(0);
		}
        
		//Displays player's current stats
		text("Current Day: " + playerData.getCurrentDayName(), height + 20, height * 3 / 4);
        text("Total Tardies: " + playerData.getTardies(), height + 20, (height * 3 / 4) + 26);
        text("Score: " + playerData.getScore(), height + 20, (height * 3 / 4) + (26 * 2));
        
        popStyle();

        //CHECKING CASES
        
        //Check if player is tardy
        if(clock.getElapsedSec() == 60) {
        	clock.setElapsedSec(0);
        	tardy = true;
        }
   
        //Check if player reached building
        String targetClass = playerData.getSchedule(playerData.getCurrentDayNum())[playerData.getCurrentPeriod()-1];
        char targetClassBuilding = targetClass.charAt(0);
        if (grid.hasReachedBuilding(targetClassBuilding)) {
        	this.finish = true;
        }
    }

    public void keyPressed() {
        //Player movement mechanic
        switch (keyCode) {
            case KeyEvent.VK_UP:
                grid.movePlayerUp();
                break;
            case KeyEvent.VK_DOWN:
                grid.movePlayerDown();
                break;
            case KeyEvent.VK_LEFT:
                grid.movePlayerLeft();
                break;
            case KeyEvent.VK_RIGHT:
                grid.movePlayerRight();
                break;
        }
        //Uncovers the grid
        if(cover && keyCode == ' ') {
        	cover = false;
        }
    }
}
