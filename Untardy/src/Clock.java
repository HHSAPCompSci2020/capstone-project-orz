
/**
 * 
 * @author Justin Chiang
 *
 */
public class Clock {
	
	private int hour, min, sec;
	private int elapsedSec;
	private String timeOfDay;
	
	/**
	 * Constructs a new instance of a Clock object with the hour, minutes, and seconds set to zero, and the time of day set to AM
	 */
	public Clock() {
		this.hour = 0;
		this.min = 0;
		this.sec = 0;
		this.timeOfDay = "AM";
	}
	
	/**
	 * Constructs a new instance of a Clock object with the hour, minutes, and time of day set to the specified values
	 * @param hour the hour value of the clock
	 * @param min the minute value of the clock
	 * @param timeOfDay the time of the day (AM or PM) of the clock
	 */
	public Clock(int hour, int min, String timeOfDay) {
		this.hour = hour;
		this.min = min;
		this.sec = 0;
		this.elapsedSec = 0;
		this.timeOfDay = timeOfDay;
	}
	
	/**
	 * Constructs a new instance of a Clock object with the hour, minutes, seconds and time of day set to the specified values
	 * @param hour the hour value of the clock
	 * @param min the minute value of the clock
	 * @param sec the second value of the clock
	 * @param timeOfDay the time of the day (AM or PM) of the clock
	 */
	public Clock(int hour, int min, int sec, String timeOfDay) {
		this.hour = hour;
		this.min = min;
		this.sec = sec;
		this.elapsedSec = 0;
		this.timeOfDay = timeOfDay;
	}
	
	/**
	 * Returns a string of a Clock object's current time with the format hour:min:sec timeOfDay
	 * @return the clock's current time
	 */
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
	
	/**
	 * Returns an integer array containing in order the hour, minute, and second values of a Clock object
	 * @return an integer array containing the hour, minute, and second values of the clock
	 */
	public int[] getTime() {
		return new int[] {hour, min, sec};
	}
	
	/**
	 * Sets a Clock object's hour, minute, and second values to the specified values
	 * @param hour the hour value to set the clock to
	 * @param min the minute value to set the clock to
	 * @param sec the second value to set the clock to
	 * @param timeOfDay the time of day (AM or PM) value to set the clock to
	 */
	public void setTime(int hour, int min, int sec, String timeOfDay) {
		this.hour = hour;
		this.min = min;
		this.sec = sec;
		this.timeOfDay = timeOfDay;
	}
	
	/**
	 * Returns the number of elapsed seconds of a Clock object since its start time
	 * @return the number of elapsed seconds of the clock
	 */
	public int getElapsedSec() {
		return elapsedSec;
	}
	
	/**
	 * Sets a Clock object's number of elapsed seconds to the specified value
	 * @param elapsedSec the number of elapsed seconds to set the clock to
	 */
	public void setElapsedSec(int elapsedSec) {
		this.elapsedSec = elapsedSec;
	}
	
	/**
	 * Increments a Clock object's second value and number of elapsed seconds by 1
	 */
	public void updateTime() {
		sec += 1;
		elapsedSec += 1;
	}

}
