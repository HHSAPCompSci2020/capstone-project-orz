import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * 
 * @author Justin Chiang
 *
 */

public class Player {
	
	private String[][] schedules;
	private int score;
	private int tardies;
	private int currentPeriod;
	private int currentDay;
	private int dayScore;
	
	private final String[] englishClasses = new String[] {"C BUILDING: Lit & Writing", 
			"C BUILDING: World Lit & Writing", "C BUILDING: American Lit", 
			"C BUILDING: British Lit", "C BUILDING: Contemporary Lit", "C BUILDING: Myth/Folk & Writ", 
			"C BUILDING: AP English"
	};
	private final String[] mathClasses = new String[] {"B BUILDING: Algebra 1", "B BUILDING: Geometry", 
			"B BUILDING: Algebra2/Trig", "B BUILDING: Pre-Calculus", "B BUILDING: AP Calculus AB", 
			"B BUILDING: AP Calculus BC", "B BUILDING: AP Statistics"};
	private final String[] scienceClasses = new String[] {"I BUILDING: Biology", "I BUILDING: Chemistry",
			"I BUILDING: AP Physics 1", "I BUILDING: AP Biology", "I BUILDING: AP Chemistry", "I BUILDING: AP Physics C", 
			"I BUILDING: AP Env. Science"};
	private final String[] socialscienceClasses = new String[] {"A BUILDING: World History", "A BUILDING: US History", 
			"A BUILDING: AP US History", "A BUILDING: US Gov't", "A BUILDING: AP US Gov't", "A BUILDING: Economics"};
	private final String[] peClasses = new String[] {"G GYM: PE 9", "G GYM: PE 10", "G GYM: PE 11", "G GYM: PE 12",
			"G GYM: PE Weights"};
	private final String[] languageClasses = new String[] {"L BUILDING: Chinese", "L BUILDING: Spanish", "L BUILDING: French", "L BUILDING: Japanese"};
	
	/**
	 * Constructs a new Player object with a week of randomized schedules, and the score, number of tardies, current day, and score for the day set to 0, and the current period set to 1 
	 */
	public Player() {
		this.schedules = new String[][] {assignSchedule(), assignSchedule(), assignSchedule(), assignSchedule(), assignSchedule()};
		this.score = 0;
		this.tardies = 0;
		this.currentPeriod = 1;
		this.currentDay = 0;
		this.dayScore = 0;
	}
	
	/**
	 * Randomizes a Player object's schedules throughout the week by randomly selecting classes from the fields of String arrays of different types of classes
	 * @return a randomized schedule for one day of the player's weekly schedule
	 */
	public String[] assignSchedule() {
		ArrayList<String> shuffleClasses = new ArrayList<String>();
		String[] classSchedule = new String[6];
		
		shuffleClasses.add(englishClasses[(int)(Math.random()*englishClasses.length)]);
		shuffleClasses.add(mathClasses[(int)(Math.random()*mathClasses.length)]);
		shuffleClasses.add(scienceClasses[(int)(Math.random()*scienceClasses.length)]);
		shuffleClasses.add(socialscienceClasses[(int)(Math.random()*socialscienceClasses.length)]);
		shuffleClasses.add(peClasses[(int)(Math.random()*peClasses.length)]);
		shuffleClasses.add(languageClasses[(int)(Math.random()*languageClasses.length)]);
		
		Collections.shuffle(shuffleClasses);
		
		for(int i=0; i<shuffleClasses.size(); i++) {
			classSchedule[i] = shuffleClasses.get(i);
		}
		
		return classSchedule;
	}
	
	/**
	 * Returns a Player object's schedule corresponding to the specified day in the week in integer form
	 * @param day the day of the week in integer form to return the schedule of
	 * @return the player's schedule for the specified day in the week
	 */
	public String[] getSchedule(int day) {
		return schedules[day];
	}
	
	/**
	 * Set a Player object's schedule on the specified day in the week in integer form to the specified schedule
	 * @param day the day of the week in integer form to set the schedule of
	 * @param schedule the schedule in a String array format to set the specified schedule to
	 */
	public void setSchedule(int day, String[] schedule) {
		this.schedules[day] = schedule;
	}
	
	/**
	 * Returns a Player object's total score
	 * @return the player's total score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Sets a Player object's total score to the specified value
	 * @param score the score to set the player's total score to
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Returns a Player object's number of tardies
	 * @return the player's number of tardies 
	 */
	public int getTardies() {
		return tardies;
	}

	/**
	 * Sets a Player object's number of tardies to the specified number of tardies
	 * @param tardies the number of tardies to set the player's number of tardies to
	 */
	public void setTardies(int tardies) {
		this.tardies = tardies;
	}
	
	/**
	 * Returns a Player object's current period in their day's schedule
	 * @return the player's current period
	 */
	public int getCurrentPeriod() {
		return currentPeriod;
	}
	
	/**
	 * Sets a Player object's current period in their day's schedule to the specified period number
	 * @param currentPeriod the period number to set the player's current period to
	 */
	public void setCurrentPeriod(int currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	
	/**
	 * Returns a Player object's current day in the week they are currently on in String form
	 * @return the player's current day in String form
	 */
	public String getCurrentDayName() {
		if(currentDay == 0) {
			return "Monday";
		}
		if(currentDay == 1) {
			return "Tuesday";
		}
		if(currentDay == 2) {
			return "Wednesday";
		}
		if(currentDay == 3) {
			return "Thursday";
		}
		if(currentDay == 4) {
			return "Friday";
		}
		return "";
	}
	
	/**
	 * Returns a Player object's current day in the week they are currently on in integer form
	 * @return the player's current day in integer form
	 */
	public int getCurrentDayNum() {
		return this.currentDay;
	}
	
	/**
	 * Sets a Player object's current day in the week in integer form to the specified day in the week
	 * @param currentDay the day in the week in integer form to set the player's current day to
	 */
	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}
	
	/**
	 * Returns a Player object's score for the current day they are on
	 * @return the player's score for the current day
	 */
	public int getDayScore() {
		return this.dayScore;
	}
	
	/**
	 * Sets a Player object's score for the current day to the specified value
	 * @param dayScore the score to set the player's day score to
	 */
	public void setDayScore(int dayScore) {
		this.dayScore = dayScore;
	}
	
	/**
	 * Updates a Player object's stats to reflect a restart on the current day when the player receives a tardy
	 */
	public void restartDay() {
		tardies++;
		currentPeriod = 1;
		dayScore = 0;
	}
	
	/**
	 * Updates a Player object's stats to reflect a restart on the entire week when the player receives 3 total tardies
	 * @param option the number that indicates whether or not to restart the game from a game over or new play through
	 */
	public void restartGame(int option) {
		if(option == 1) {
			schedules = new String[][] {assignSchedule(), assignSchedule(), assignSchedule(), assignSchedule(), assignSchedule()};
		}
		score = 0;
		tardies = 0;
		currentPeriod = 1;
		currentDay = 0;
		dayScore = 0;
	}
	
	/**
	 * Updates a Player object's stats to reflect a progression to the next day in the week when the player passes their current day
	 */
	public void nextDay() {
		setCurrentPeriod(1);
		currentDay++;
		dayScore = 0;
	}
	
	/**
	 * Updates a Player object's stats to reflect a progression in their current day when the player passes their current period
	 */
	public void nextPeriod() {
		currentPeriod++;
		score += dayScore;
	}
	
}
