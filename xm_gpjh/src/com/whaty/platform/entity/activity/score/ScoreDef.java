/**
 * 
 */
package com.whaty.platform.entity.activity.score;


/**
 * ���������˱�ƽ̨�ķ�������
 * @author Administrator
 *
 */
public class ScoreDef {
	
	/**
	 * ��������
	 */
	private String title;

	//��������
	private String type;
	
	//����
	private float score;
	
	//�����ȼ� �������㣬���õ�
	private String scoreLevel;
	
	//������ʾ�������ֻ��ǵȼ�����������֣�isLevelScore=false������ǵȼ���isLevelScore=true;
	private boolean isLevelScore;
	
	//��������
	private String scoreDes;
	
	//���˵��
	private String zeroDes;
	
	//���ԭ��
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
