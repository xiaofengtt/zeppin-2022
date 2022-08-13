/**
 * 
 */
package com.whaty.platform.entity.activity.score;


/**
 * 该类描述了本平台的分数定义
 * @author Administrator
 *
 */
public class ScoreDef {
	
	/**
	 * 分数名称
	 */
	private String title;

	//分数类型
	private String type;
	
	//分数
	private float score;
	
	//分数等级 例如优秀，良好等
	private String scoreLevel;
	
	//分数显示采用数字还是等级，如果是数字，isLevelScore=false；如果是等级，isLevelScore=true;
	private boolean isLevelScore;
	
	//分数描述
	private String scoreDes;
	
	//零分说明
	private String zeroDes;
	
	//零分原因
	private String zeroCause;

	public ScoreDef() {
		
	}
	
	public ScoreDef(float score) {
		this.score=score;
		setSimpleLevel();
		this.scoreDes="";
		if(score==0)
			this.zeroDes=ScoreZeroCause.NORMAL;
		this.zeroCause="";
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getScoreDes() {
		return scoreDes;
	}

	public void setScoreDes(String scoreDes) {
		this.scoreDes = scoreDes;
	}

	public String getScoreLevel() {
		return scoreLevel;
	}

	public void setScoreLevel(String scoreLevel) {
		this.scoreLevel = scoreLevel;
	}

	public String getZeroCause() {
		return zeroCause;
	}

	public void setZeroCause(String zeroCause) {
		this.zeroCause = zeroCause;
	}

	public String getZeroDes() {
		return zeroDes;
	}

	public void setZeroDes(String zeroDes) {
		this.zeroDes = zeroDes;
	}
	
	public void setDetailLevel()
	{
		if(score>=90)
		{
			this.scoreLevel=ScoreLevelType.ADVANCE;
		}
		else if(score>=80)
		{
			this.scoreLevel=ScoreLevelType.GOOD;
		}	
		else if(score>=70)
		{
			this.scoreLevel=ScoreLevelType.NORMAL;
		}
		else if(score>=60)
		{
			this.scoreLevel=ScoreLevelType.PASSED;
		}
		else
		{
			this.scoreLevel=ScoreLevelType.NOTPASSED;
		}
	}
	
	public void setSimpleLevel()
	{
		if(score>=60)
		{
			this.scoreLevel=ScoreLevelType.PASSED;
		}
		else
		{
			this.scoreLevel=ScoreLevelType.NOTPASSED;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean getIsLevelScore() {
		return isLevelScore;
	}

	public void setLevelScore(boolean isLevelScore) {
		this.isLevelScore = isLevelScore;
	}

	

	
	
	
}
