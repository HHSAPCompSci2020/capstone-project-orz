
import java.awt.event.KeyEvent;
import java.io.File;

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

    private Player player;
    private Clock clock;
    
    private int entranceRow;
    private int entranceCol;
    
    private int moveFriendCellPositionsTime;
    private final int updateFriendCellPositionsWait;
    
    private int movePlayerPositionTime;
    private final int updatePlayerCellPositionWait;
    
    private double friendCellFreq;
    private final double increaseFriendCellFreqFactor;

    /**
     * Constructs a DrawingSurface object and initializes relevant fields
     */
    public DrawingSurface() {
        grid = new Grid(50, 50, "data" + File.separator + "homestead.txt");
        intro = true;
        tardy = false;
        finish = false;
        cover = true;
        player = new Player();
        clock = new Clock(8, 45, "AM");
        
        entranceRow = 19;
        entranceCol = 47;
        
        this.moveFriendCellPositionsTime = 0;
        this.updateFriendCellPositionsWait = 120;
        
        this.movePlayerPositionTime = 0;
        this.updatePlayerCellPositionWait = 120;
        
        this.friendCellFreq = 0.01;
        this.increaseFriendCellFreqFactor = 0.01;
        
    }

    /**
     * Setup for a DrawingSurface object before it is drawn, run once to configure certain fields and values
     */
    public void setup() {
    	clockFont = createFont("DS-DIGI.TTF", 20);
    	regFont = createFont("Oswald-VariableFont_wght.ttf", 20);
        time = millis();
        
        grid.generateFriendCells(friendCellFreq);
    }

    /**
     * Draws a DrawingSurface object onto this PApplet, includes all graphics of the game including menus, text, and game grid
     */
    public void draw() {

        background(211, 211, 211);
        fill(0);
        
        //CASES
        
        //Introduction case
        if (intro) {
        	JOptionPane.showMessageDialog(frame, 
        			"Welcome to Untardy! This game simulates a student at Homestead walking from class to class with the\n"
        			+ "objective of not being tardy. You will play through a full week of a student's class schedule. If\n"
        			+ "you reach 3 tardies throughout the week, you will have to restart from the beginning of the week.\n\n"
        			+ "There will be a clock at the top right of the game, as well as your current objective and your current\n"
        			+ "day's class schedule on the right side of the game. At the end of the day, you will earn points\n"
        			+ "depending on how fast you complete your objectives and how optimal the paths you walked to class were. Good luck!");
        	intro = false;
        }
        
        //Tardy case
        if (tardy) {
        	//Prompts user to restart or quit game if the player reaches 3 tardies
        	if(player.getTardies() == 2) {
        		String[] options = new String[] {"Play Again", "Quit"};
        		int choice = JOptionPane.showOptionDialog(frame, "You've received three tardies! Do you want to restart or quit the game?", 
        				"Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        		
        		if(choice == 0) {
        			player.restartGame(0);
        			friendCellFreq = 0.01; 
        		}
        		else {
        			exit();
        		}
        		
        	}
        	//Restarts the day if the player has less than 3 tardies
        	else {
        		JOptionPane.showMessageDialog(frame, "You were tardy to class! You've received a tardy, and will restart the day.");
    			player.restartDay();
    			friendCellFreq -= (0.0000005 * (player.getCurrentPeriod()-1));
        	}
        	
        	if (player.getCurrentPeriod() == 1) {
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
        	this.friendCellFreq += this.increaseFriendCellFreqFactor;
        	grid.generateFriendCells(this.friendCellFreq);
        	
        	// add timeLeftOver to dayScore
        	int timeLeftOver = 60 - clock.getElapsedSec();
    		player.setDayScore(player.getDayScore() + timeLeftOver);
        	
        	//Completed day
        	if(player.getCurrentPeriod() == 6) {
        		this.friendCellFreq -= this.increaseFriendCellFreqFactor * 6;
        		grid.generateFriendCells(this.friendCellFreq);
        		//Congratulates the player for beating the game if completed Friday schedule
        		if(player.getCurrentDayName().equals("Friday")) {
            		String[] options = new String[] {"Play Again", "Quit"};
            		int choice = JOptionPane.showOptionDialog(frame, "Congratulations! You beat the game with a score of " + (player.getScore() + player.getDayScore()) + " points. "
            				+ "Do you want to restart or quit the game?", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            		
            		if(choice == 0) {
            			player.restartGame(1);
            			tardy = false;
            		}
            		else {
            			exit();
            		}
            	}
        		
        		//Congratulates the player for beating the current day's schedule 
            	else {
            		JOptionPane.showMessageDialog(frame, "You completed the day! You earned " + player.getDayScore() + " points, and will now progress to the next day.");
        			player.nextDay();
        			
            		// add dayScore to score and reset dayScore
            		player.setScore(player.getScore() + player.getDayScore());
            		player.setDayScore(0);
            	}
        		
        		grid.setPlayerLoc(entranceRow, entranceCol);
        		clock.setTime(8, 45, 0, "AM");
        	}
        	
        	//Completed period
        	else {
        		//Congratulates the player for beating the current period, configures settings for next period
        		String className = player.getSchedule(player.getCurrentDayNum())[player.getCurrentPeriod()-1].substring(
        				player.getSchedule(player.getCurrentDayNum())[player.getCurrentPeriod()-1].indexOf(':') + 2);
        		if(player.getCurrentPeriod() == 1) {
        			JOptionPane.showMessageDialog(frame, "You made it on time! " + className + " was over in an instant, don't be late to your next class!");
        			clock.setTime(7, 55, 0, "AM");
        		}
        		else if(player.getCurrentPeriod() == 2) {
        			JOptionPane.showMessageDialog(frame, "You made it on time! " + className + " and tutorial were over in an instant, don't be late to your next class!");
        			clock.setTime(8, 45, 0, "AM");
        		}
        		else if(player.getCurrentPeriod() == 3) {
        			JOptionPane.showMessageDialog(frame, "You made it on time! " + className + " and brunch were over in an instant, don't be late to your next class!");
        			clock.setTime(11, 25, 0, "PM");
        		}
        		else if(player.getCurrentPeriod() == 4) {
        			JOptionPane.showMessageDialog(frame, "You made it on time! " + className + " was over in an instant, don't be late to your next class!");
        			clock.setTime(12, 15, 0, "PM");
        		}
        		else if(player.getCurrentPeriod() == 5) {
        			JOptionPane.showMessageDialog(frame, "You made it on time! " + className + " and lunch were over in an instant, don't be late to your next class!");
        			clock.setTime(1, 45, 0, "PM");
        		}
        		player.nextPeriod();
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
        if(player.getCurrentPeriod() == 1) {
        	classTime = "8:00 AM";
        	text("Objective: You're almost late! Get to period " + player.getCurrentPeriod(), height + 20, height * 1 / 4);
        	text("by " + classTime + "!", height + 20, (height * 1 / 4) + 26);
        }
        else {
        	if(player.getCurrentPeriod() == 2) {
            	classTime = "8:50 AM";
            }
            else if(player.getCurrentPeriod() == 3) {
            	classTime = "10:25 AM";
            }
            else if(player.getCurrentPeriod() == 4) {
            	classTime = "11:30 AM";
            }
            else if(player.getCurrentPeriod() == 5) {
            	classTime = "12:20 PM";
            }
            else if(player.getCurrentPeriod() == 6) {
            	classTime = "1:50 PM";
            }
        	text("Objective: Get from period " + (player.getCurrentPeriod()-1) + " to period " + (player.getCurrentPeriod()), height + 20, height * 1 / 4); 
        	text("by " + classTime + "!", height + 20, (height * 1 / 4) + 26);
        }
        
        //Displays current day's schedule
        text("Class Schedule", height + 20, height * 4 / 10);
        line(height + 20, height * 4 / 10, (height + 20) + (textWidth("Class Schedule")), height * 4 / 10);
		for(int i=0; i<player.getSchedule(player.getCurrentDayNum()).length; i++) {
			if(i < player.getCurrentPeriod()-1) {
				fill(0, 153, 51);
			}
			else if(i == player.getCurrentPeriod()-1) {
				fill(255, 128, 0);
			}
			text("Period " + (i+1) + ": " + player.getSchedule(player.getCurrentDayNum())[i], height + 20, (height * 4 / 10) + ((i+1) * 26));
			fill(0);
		}
        
		//Displays player's current stats
		text("Current Day: " + player.getCurrentDayName(), height + 20, height * 3 / 4);
        text("Total Tardies: " + player.getTardies(), height + 20, (height * 3 / 4) + 26);
        text("Score: " + player.getScore(), height + 20, (height * 3 / 4) + (26 * 2));
        
        popStyle();

        
        
        //CHECK CASES
        
        //Check if player is tardy
        if(clock.getElapsedSec() == 300) {
        	clock.setElapsedSec(0);
        	tardy = true;
        }
   
        //Check if player reached building
        String targetClass = player.getSchedule(player.getCurrentDayNum())[player.getCurrentPeriod()-1];
        char targetClassBuilding = targetClass.charAt(0);
        if (grid.hasReachedBuilding(targetClassBuilding)) {
        	this.finish = true;
        }
        
        if (millis() - this.moveFriendCellPositionsTime >= this.updateFriendCellPositionsWait) {
            this.moveFriendCellPositionsTime = millis();
            grid.moveFriendCellPositions();
        }
        if (grid.playerIsBlocked()) {
        	grid.setRandomAdjFriendToPathCell();
        }
        
        
    }

    /**
     * Represents key interactions with a DrawingSurface object, which causes specific interactions such as player movement or menu interaction
     */
    public void keyPressed() {
        //Player movement mechanic
        switch (keyCode) {
            case KeyEvent.VK_UP:
            	if (millis() - this.movePlayerPositionTime >= this.updatePlayerCellPositionWait) {
                    this.movePlayerPositionTime = millis();
                    grid.movePlayerUp();
                }
                break;
            case KeyEvent.VK_DOWN:
            	if (millis() - this.movePlayerPositionTime >= this.updatePlayerCellPositionWait) {
                    this.movePlayerPositionTime = millis();
                    grid.movePlayerDown();
                }
                break;
            case KeyEvent.VK_LEFT:
            	if (millis() - this.movePlayerPositionTime >= this.updatePlayerCellPositionWait) {
                    this.movePlayerPositionTime = millis();
                    grid.movePlayerLeft();
                }
                break;
            case KeyEvent.VK_RIGHT:
            	if (millis() - this.movePlayerPositionTime >= this.updatePlayerCellPositionWait) {
                    this.movePlayerPositionTime = millis();
                    grid.movePlayerRight();
                }
                break;
        }
        
        //Uncovers the grid
        if(cover && keyCode == ' ') {
        	cover = false;
        }
    }
    
}
