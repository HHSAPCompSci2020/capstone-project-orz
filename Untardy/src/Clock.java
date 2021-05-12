
/**
 * 
 * @author Justin Chiang
 *
 */
public class Clock {
	
	private int hour, min, sec;
	private String timeOfDay;
	private int elapsedSec;
	
	public Clock() {
		this.hour = 0;
		this.min = 0;
		this.sec = 0;
		this.timeOfDay = "AM";
	}
	
	public Clock(int hour, int min, String timeOfDay) {
		this.hour = hour;
		this.min = min;
		this.sec = 0;
		this.timeOfDay = timeOfDay;
	}
	
	public Clock(int hour, int min, int sec, String timeOfDay) {
		this.hour = hour;
		this.min = min;
		this.sec = sec;
		this.timeOfDay = timeOfDay;
	}
	
	public String displayTime() {
		if(sec == 60) {
			sec = 0;
			min += 1;
		}
		if(min == 60) {
			min = 0;
			hour += 1;
		}
		if(hour > 12) { //Not perfect implementation but functional for school day (only AM to PM)
			hour -= 12;
			timeOfDay = "PM";
		}
		
		return(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec) + " " + timeOfDay);
	}
	
	public int[] getTime() {
		return new int[] {hour, min, sec};
	}
	
	public void setTime(int hour, int min, int sec, String timeOfDay) {
		this.hour = hour;
		this.min = min;
		this.sec = sec;
		this.timeOfDay = timeOfDay;
	}
	
	public int getElapsedSec() {
		return elapsedSec;
	}
	
	public void setElapsedSec(int elapsedSec) {
		this.elapsedSec = elapsedSec;
	}
	
	public void updateTime() {
		sec += 1;
		elapsedSec += 1;
	}

}
