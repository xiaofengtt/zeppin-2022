package com.whaty.platform.entity.recruit;

public class RecruitLimit {

	private int recruitNum;

	private boolean recruitNumValid;

	private int signNum;

	private boolean signNumValid;

	private TimeDef signTime;

	private boolean signTimeValid;

	public int getRecruitNum() {
		return recruitNum;
	}

	public void setRecruitNum(int recruitNum) {
		this.recruitNum = recruitNum;
	}

	public boolean isRecruitNumValid() {
		return recruitNumValid;
	}

	public void setRecruitNumValid(boolean recruitNumValid) {
		this.recruitNumValid = recruitNumValid;
	}

	public int getSignNum() {
		return signNum;
	}

	public void setSignNum(int signNum) {
		this.signNum = signNum;
	}

	public boolean isSignNumValid() {
		return signNumValid;
	}

	public void setSignNumValid(boolean signNumValid) {
		this.signNumValid = signNumValid;
	}

	public TimeDef getSignTime() {
		return signTime;
	}

	public void setSignTime(TimeDef signTime) {
		this.signTime = signTime;
	}

	public boolean isSignTimeValid() {
		return signTimeValid;
	}

	public void setSignTimeValid(boolean signTimeValid) {
		this.signTimeValid = signTimeValid;
	}
}
