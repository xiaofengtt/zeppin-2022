/**
 * 
 */
package com.whaty.platform.entity.test;

import java.util.Date;

/**该类描述了考试时间定义
 * @author chenjian
 *
 */
public class TestTimeDef {
	
	private Date startTime;
	
	private Date endTime;

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
}
