import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * 
 * @author Justin Chiang
 *
 */

public class PlayerData {
	
	private String[] schedule;
	private int score;
	private int tardies;
	
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
		this.schedule = assignSchedule();
		this.score = 0;
		this.tardies = 3;
	}
	
	public PlayerData(String[] schedule) {
		this.schedule = schedule;
		this.score = 0;
		this.tardies = 3;
	}
	
	public PlayerData(String[] schedule, int score, int tardies) {
		this.schedule = schedule;
		this.score = score;
		this.tardies = tardies;
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
	
	public String printSchedule() {
		String s = "";
		for(int i=0; i<schedule.length; i++) {
			s += "Period " + (i+1) + ": " + schedule[i] + "\n";
		}
		return s;
	}
	
	public String[] getSchedule() {
		return schedule;
	}
	
	public void setSchedule(String[] schedule) {
		this.schedule = schedule;
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
}
