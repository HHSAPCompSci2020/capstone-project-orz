
public class PlayerData {
	
	private String[] schedule;
	private int score;
	private int lives;
	
	public PlayerData() {
		this.schedule = null; //maybe a way to randomize a default class schedule
		this.score = 0;
		this.lives = 3;
	}
	
	public PlayerData(String[] schedule) {
		this.schedule = schedule;
		this.score = 0;
		this.lives = 3;
	}
	
	public PlayerData(String[] schedule, int score, int lives) {
		this.schedule = schedule;
		this.score = score;
		this.lives = lives;
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
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}
