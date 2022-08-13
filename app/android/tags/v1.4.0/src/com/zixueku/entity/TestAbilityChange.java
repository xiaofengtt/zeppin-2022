package com.zixueku.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 类说明 用户答完题,提交后,服务器端返回的数据 用户能力变化情况
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-4-23 上午9:55:28
 */
public class TestAbilityChange implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -908759302732690231L;

	private double rankingRate;
	private int nextTestdayCount;
	private LastRightItemCount[] lastRightItemCount;
	private int testUserCount;
	private double progress;// 进度
	private int brushItemCount;// 答题量
	private int ranking;
	private double correctRate;// 正确率
	private int brushRightItemCount; // 正确量
	private int totalItemCount;// 题目总量
	private List<Knowledge> changeKnowledges;
	public double getRankingRate() {
		return rankingRate;
	}
	public void setRankingRate(double rankingRate) {
		this.rankingRate = rankingRate;
	}
	public int getNextTestdayCount() {
		return nextTestdayCount;
	}
	public void setNextTestdayCount(int nextTestdayCount) {
		this.nextTestdayCount = nextTestdayCount;
	}
	
	public LastRightItemCount[] getLastRightItemCount() {
		return lastRightItemCount;
	}
	public void setLastRightItemCount(LastRightItemCount[] lastRightItemCount) {
		this.lastRightItemCount = lastRightItemCount;
	}
	public int getTestUserCount() {
		return testUserCount;
	}
	public void setTestUserCount(int testUserCount) {
		this.testUserCount = testUserCount;
	}
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	public int getBrushItemCount() {
		return brushItemCount;
	}
	public void setBrushItemCount(int brushItemCount) {
		this.brushItemCount = brushItemCount;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public double getCorrectRate() {
		return correctRate;
	}
	public void setCorrectRate(double correctRate) {
		this.correctRate = correctRate;
	}
	public int getBrushRightItemCount() {
		return brushRightItemCount;
	}
	public void setBrushRightItemCount(int brushRightItemCount) {
		this.brushRightItemCount = brushRightItemCount;
	}
	public int getTotalItemCount() {
		return totalItemCount;
	}
	public void setTotalItemCount(int totalItemCount) {
		this.totalItemCount = totalItemCount;
	}
	public List<Knowledge> getChangeKnowledges() {
		return changeKnowledges;
	}
	public void setChangeKnowledges(List<Knowledge> changeKnowledges) {
		this.changeKnowledges = changeKnowledges;
	}
	@Override
	public String toString() {
		return "TestAbilityChange [rankingRate=" + rankingRate + ", nextTestdayCount=" + nextTestdayCount + ", lastRightItemCount=" + lastRightItemCount
				+ ", testUserCount=" + testUserCount + ", progress=" + progress + ", brushItemCount=" + brushItemCount + ", ranking=" + ranking
				+ ", correctRate=" + correctRate + ", brushRightItemCount=" + brushRightItemCount + ", totalItemCount=" + totalItemCount
				+ ", changeKnowledges=" + changeKnowledges + "]";
	}
	
}
