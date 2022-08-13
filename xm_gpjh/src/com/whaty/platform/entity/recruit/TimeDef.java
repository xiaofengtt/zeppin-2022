package com.whaty.platform.entity.recruit;

public class TimeDef {
	private String startTime;

	private String endTime;
	
	public TimeDef()
	{}
	public TimeDef(String start,String end)
	{
		this.startTime = start;
		this.endTime = end;
	}
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
