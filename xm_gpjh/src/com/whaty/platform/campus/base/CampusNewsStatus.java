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
	 * �û��Ƿ��ܹ�����
	 */
	private boolean isActive=false;
	
	/**
	 * �Ƿ��ö�
	 */
	private boolean isTop=false;
	
	/**
	 * �ö���˳��
	 */
	private int topSequence=0;
	
	/**
	 * �Ƿ񵯳�
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
