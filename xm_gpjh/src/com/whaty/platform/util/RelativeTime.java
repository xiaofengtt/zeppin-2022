/**
 * 
 */
package com.whaty.platform.util;

import java.util.Date;

/**
 * @author chenjian
 *
 */
public class RelativeTime {
	
	private int hour;
	
	private int minute;
	
	private float second;

	/**
	 * 
	 */
	public RelativeTime() {
		
	}
	
	public RelativeTime(String hour,String minute,String second) {
		
	}
	
	public RelativeTime(String time){
		
	}
	

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public float getSecond() {
		return second;
	}

	public void setSecond(float second) {
		this.second = second;
	}

	public RelativeTime add(RelativeTime time)
	{
		return time;
	}

	public String toStr()
	{
		return "00:00:00.0";
	}
	
	public float toHour()
	{
		return 0;
	}
	
	public float toMinute()
	{
		return 0;
	}
	
	public float toSecond()
	{
		return 0;
	}
	
	public RelativeTime computeTime(Date dataStart,Date dataEnd)
	{
		return new RelativeTime();
	}
}
