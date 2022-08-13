package com.zixueku.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 类说明 正在备考
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-3 上午10:10:05
 */
public class PrepareExam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6947927546132921018L;

	private int subjectid;
	private String subjectname; // 备考科目名称
	private String resourcePath; // 图标地址
	private double progress; // 复习进度
	private int nextTestdayCount; // 距下次考试剩余天数
	private double correctRate;// 正确率
	private int brushItemCount;// 刷题量
	private double rankingRate; // 排名
	private boolean knowledgeIsLoad; // 该学科下的知识点是否加载过
	private List<Knowledge> knowledgeData;

	// private transient SwipeView swipeView;

	public int getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public int getNextTestdayCount() {
		return nextTestdayCount;
	}

	public void setNextTestdayCount(int nextTestdayCount) {
		this.nextTestdayCount = nextTestdayCount;
	}

	public double getCorrectRate() {
		return correctRate;
	}

	public void setCorrectRate(double correctRate) {
		this.correctRate = correctRate;
	}

	public int getBrushItemCount() {
		return brushItemCount;
	}

	public void setBrushItemCount(int brushItemCount) {
		this.brushItemCount = brushItemCount;
	}

	public double getRankingRate() {
		return rankingRate;
	}

	public void setRankingRate(double rankingRate) {
		this.rankingRate = rankingRate;
	}

	public boolean isKnowledgeIsLoad() {
		return knowledgeIsLoad;
	}

	public void setKnowledgeIsLoad(boolean knowledgeIsLoad) {
		this.knowledgeIsLoad = knowledgeIsLoad;
	}

	public List<Knowledge> getKnowledgeData() {
		return knowledgeData;
	}

	public void setKnowledgeData(List<Knowledge> knowledgeData) {
		this.knowledgeData = knowledgeData;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		return "PrepareExam [subjectid=" + subjectid + ", subjectname=" + subjectname + ", resourcePath=" + resourcePath + ", progress=" + progress
				+ ", nextTestdayCount=" + nextTestdayCount + ", correctRate=" + correctRate + ", brushItemCount=" + brushItemCount + ", rankingRate="
				+ rankingRate + ", knowledgeIsLoad=" + knowledgeIsLoad + ", knowledgeData=" + knowledgeData + "]";
	}
	
}
