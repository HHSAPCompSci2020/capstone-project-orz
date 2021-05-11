import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * 
 * @author Justin Chiang
 *
 */

public class PlayerData {
	
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
	private final String[] peClasses = new String[] {"GYM: PE 9", "GYM: PE 10", "GYM: PE 11", "GYM: PE 12",
			"GYM: PE Weights"};
	
	public PlayerData() {
		this.schedules = new String[][] {assignSchedule(), assignSchedule(), assignSchedule(), assignSchedule(), assignSchedule()};
		this.score = 0;
		this.tardies = 0;
		this.currentPeriod = 1;
		this.currentDay = 0;
		this.dayScore = 0;
	}
	
	public String[] assignSchedule() {
		ArrayList<String> shuffleClasses = new ArrayList<String>();
		String[] classSchedule = new String[5];
		
		shuffleClasses.add(englishClasses[(int)(Math.random()*englishClasses.length)]);
		shuffleClasses.add(mathClasses[(int)(Math.random()*mathClasses.length)]);
		shuffleClasses.add(scienceClasses[(int)(Math.random()*scienceClasses.length)]);
		shuffleClasses.add(socialscienceClasses[(int)(Math.random()*socialscienceClasses.length)]);
		shuffleClasses.add(peClasses[(int)(Math.random()*peClasses.length)]);
		
		Collections.shuffle(shuffleClasses);
		
		for(int i=0; i<shuffleClasses.size(); i++) {
			classSchedule[i] = shuffleClasses.get(i);
		}
		
		return classSchedule;
	}
	
	public String[] getSchedule(int x) {
		return schedules[x];
	}
	
	public void setSchedule(int x, String[] schedule) {
		this.schedules[x] = schedule;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getTardies() {
		return tardies;
	}

	public void setTardies(int tardies) {
		this.tardies = tardies;
	}
	
	public int getCurrentPeriod() {
		return currentPeriod;
	}
	
	public void setCurrentPeriod(int currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	
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
	
	public int getCurrentDayNum() {
		return this.currentDay;
	}
	
	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}
	
	public int getDayScore() {
		return this.dayScore;
	}
	
	public void setDayScore(int dayScore) {
		this.dayScore = dayScore;
	}
	
	public void restartDay() {
		tardies++;
		currentPeriod = 1;
		dayScore = 0;
	}
	
	public void restartGame() {
		schedules = new String[][] {assignSchedule(), assignSchedule(), assignSchedule(), assignSchedule(), assignSchedule()};
		score = 0;
		tardies = 0;
		currentPeriod = 1;
		currentDay = 0;
		dayScore = 0;
	}
	
	public void nextDay() {
		setCurrentPeriod(1);
		currentDay++;
		dayScore = 0;
	}
	
	public void nextPeriod() {
		currentPeriod++;
		score += dayScore;
	}
	
	/*
	 * private String[] schedule;
private int score;
private int tardies;
private int currentPeriod;
private int currentDay;
private int dayScore;
	 */
}
