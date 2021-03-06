
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import cells.Cell;
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
    private int playerMoves;
    private Clock clock;
    
    private int entranceRow;
    private int entranceCol;
    
    private int moveFriendCellPositionsTime;
    private final int updateFriendCellPositionsWait;
    
    private int movePlayerPositionTime;
    private final int updatePlayerCellPositionWait;
    
    private double friendCellFreq;
    private final double increaseFriendCellFreqFactor;
    
    private int startingPlayerRowBeforePeriod;
    private int startingPlayerColBeforePeriod;

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
        playerMoves = 0;
        clock = new Clock(7, 55, "AM");
        
        entranceRow = 19;
        entranceCol = 47;
        
        this.moveFriendCellPositionsTime = 0;
        this.updateFriendCellPositionsWait = 120;
        
        this.movePlayerPositionTime = 0;
        this.updatePlayerCellPositionWait = 120;
        
        this.friendCellFreq = 0.01;
        this.increaseFriendCellFreqFactor = 0.005;
        
        int[] playerLoc = grid.getPlayerLocation();
        this.startingPlayerRowBeforePeriod = playerLoc[0];
        this.startingPlayerColBeforePeriod = playerLoc[1];
        
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
        		friendCellFreq -= (increaseFriendCellFreqFactor * (player.getCurrentPeriod()-1));
        		player.restartDay();
        	}
        	
        	if (player.getCurrentPeriod() == 1) {
        		//Put player at entrance of school
        		 grid.setPlayerLoc(entranceRow, entranceCol);
        	}
        	playerMoves = 0;
        	clock.setTime(7, 55, 0, "AM");
        	clock.setElapsedSec(0);
        	cover = true;
        	tardy = false;
        }

        //Win condition
        if (finish) {
        	//Update friend cell frequencies and spawn more
        	this.friendCellFreq += this.increaseFriendCellFreqFactor;
        	grid.generateFriendCells(this.friendCellFreq);
        	
        	//Draw shortest path and calculate bonus points
        	String targetClass = player.getSchedule(player.getCurrentDayNum())[player.getCurrentPeriod()-1];
            char targetClassBuilding = targetClass.charAt(0);
            
        	int shortestPath = grid.displayShortestPath(this.startingPlayerRowBeforePeriod, 
        							this.startingPlayerColBeforePeriod, targetClassBuilding).size();
        	
        	int shortestPathPoints = 0;
        	if(Math.abs(shortestPath - playerMoves) <= 5) {
        		shortestPathPoints = 150;
        	}
        	else if(Math.abs(shortestPath - playerMoves) <= 10) {
        		shortestPathPoints = 100;
        	}
        	else if(Math.abs(shortestPath - playerMoves) <= 15) {
        		shortestPathPoints = 50;
        	}
        	
        	//Update player score
        	int timeLeftOver = 300 - clock.getElapsedSec();
    		player.setDayScore(player.getDayScore() + timeLeftOver + shortestPathPoints);
    		
        	//Completed day
        	if(player.getCurrentPeriod() == 6) {
        		this.friendCellFreq -= this.increaseFriendCellFreqFactor * 3;
        		//Congratulates the player for beating the game if completed Friday schedule
        		if(player.getCurrentDayName().equals("Friday")) {
            		String[] options = new String[] {"Play Again", "Quit"};
            		int choice = JOptionPane.showOptionDialog(frame, "Congratulations! You beat the game with a score of " + (player.getScore() + player.getDayScore()) + " points. "
            				+ "Do you want to restart or quit the game?", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            		
            		if(choice == 0) {
            			player.restartGame(1);
            			friendCellFreq = 0.01;
            			tardy = false;
            		}
            		else {
            			exit();
            		}
            	}
        		
        		//Congratulates the player for beating the current day's schedule 
            	else {
            		JOptionPane.showMessageDialog(frame, "You completed the day! You now have a total of " + (player.getDayScore() + player.getScore()) + " points, and will now progress to the next day.");
        			player.nextDay();
            	}
        		
        		grid.setPlayerLoc(entranceRow, entranceCol);
        		clock.setTime(7, 55, 0, "AM");
        	}
        	
        	//Completed period
        	else {
        		//Congratulates the player for beating the current period, configures settings for next period
        		
        		String className = player.getSchedule(player.getCurrentDayNum())[player.getCurrentPeriod()-1].substring(
        				player.getSchedule(player.getCurrentDayNum())[player.getCurrentPeriod()-1].indexOf(':') + 2);
        		if(player.getCurrentPeriod() == 1) {
        			JOptionPane.showMessageDialog(frame, "Points for Time Left Over: " + timeLeftOver + "\n" + "Points for Shortest Path: " + shortestPathPoints + "\n" + "Total Points Earned: " + (timeLeftOver + shortestPathPoints) + "\n\n" + "You made it on time! " + className + " was over in an instant, don't be late to your next class!");
        			clock.setTime(8, 45, 0, "AM");
        		}
        		else if(player.getCurrentPeriod() == 2) {
        			JOptionPane.showMessageDialog(frame, "Points for Time Left Over: " + timeLeftOver + "\n" + "Points for Shortest Path: " + shortestPathPoints + "\n" + "Total Points Earned: " + (timeLeftOver + shortestPathPoints) + "\n\n" + "You made it on time! " + className + " and tutorial were over in an instant, don't be late to your next class!");
        			clock.setTime(10, 20, 0, "AM");
        		}
        		else if(player.getCurrentPeriod() == 3) {
        			JOptionPane.showMessageDialog(frame, "Points for Time Left Over: " + timeLeftOver + "\n" + "Points for Shortest Path: " + shortestPathPoints + "\n" + "Total Points Earned: " + (timeLeftOver + shortestPathPoints) + "\n\n" + "You made it on time! " + className + " and brunch were over in an instant, don't be late to your next class!");
        			clock.setTime(11, 25, 0, "PM");
        		}
        		else if(player.getCurrentPeriod() == 4) {
        			JOptionPane.showMessageDialog(frame, "Points for Time Left Over: " + timeLeftOver + "\n" + "Points for Shortest Path: " + shortestPathPoints + "\n" + "Total Points Earned: " + (timeLeftOver + shortestPathPoints) + "\n\n" + "You made it on time! " + className + " was over in an instant, don't be late to your next class!");
        			clock.setTime(12, 15, 0, "PM");
        		}
        		else if(player.getCurrentPeriod() == 5) {
        			JOptionPane.showMessageDialog(frame, "Points for Time Left Over: " + timeLeftOver + "\n" + "Points for Shortest Path: " + shortestPathPoints + "\n" + "Total Points Earned: " + (timeLeftOver + shortestPathPoints) + "\n\n" + "You made it on time! " + className + " and lunch were over in an instant, don't be late to your next class!");
        			clock.setTime(1, 45, 0, "PM");
        		}
        		player.nextPeriod();
        	}
        	
        	playerMoves = 0;
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
        text("Score: " + (player.getDayScore()+player.getScore()), height + 20, (height * 3 / 4) + (26 * 2));
        
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
                    if(grid.playerHasMoved()) {
                    	playerMoves++;
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
            	if (millis() - this.movePlayerPositionTime >= this.updatePlayerCellPositionWait) {
                    this.movePlayerPositionTime = millis();
                    grid.movePlayerDown();
                    if(grid.playerHasMoved()) {
                    	playerMoves++;
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
            	if (millis() - this.movePlayerPositionTime >= this.updatePlayerCellPositionWait) {
                    this.movePlayerPositionTime = millis();
                    grid.movePlayerLeft();
                    if(grid.playerHasMoved()) {
                    	playerMoves++;
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
            	if (millis() - this.movePlayerPositionTime >= this.updatePlayerCellPositionWait) {
                    this.movePlayerPositionTime = millis();
                    grid.movePlayerRight();
                    if(grid.playerHasMoved()) {
                    	playerMoves++;
                    }
                }
                break;
        }
        
        //Uncovers the grid
        if(cover && keyCode == ' ') {
        	cover = false;
        	int[] playerLoc = grid.getPlayerLocation();
        	this.startingPlayerRowBeforePeriod = playerLoc[0];
        	this.startingPlayerColBeforePeriod = playerLoc[1];
        }
        
    }
    
}
