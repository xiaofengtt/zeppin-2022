/**
 * 
 */
package com.whaty.platform.campus.base;

/**
 * @author chenjian
 *
 */
public class CampusNewsStatus {

	/**
	 * 用户是否能够看到
	 */
	private boolean isActive=false;
	
	/**
	 * 是否置顶
	 */
	private boolean isTop=false;
	
	/**
	 * 置顶的顺序
	 */
	private int topSequence=0;
	
	/**
	 * 是否弹出
	 */
	private boolean isPop=false;

	public boolean getIsActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean getIsTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public int getTopSequence() {
		return topSequence;
	}

	public void setTopSequence(int topSequence) {
		this.topSequence = topSequence;
	}

	public boolean getIsPop() {
		return isPop;
	}

	public void setPop(boolean isPop) {
		this.isPop = isPop;
	}
	
	public int getIsActiveNum()
	{
		if(this.getIsActive())
			return 1;
		else
			return 0;
	}
	
	public int getIsTopNum()
	{
		if(this.getIsTop())
			return 1;
		else
			return 0;
	}
	public int getIsPopNum()
	{
		if(this.getIsPop())
			return 1;
		else
			return 0;
	}
}
