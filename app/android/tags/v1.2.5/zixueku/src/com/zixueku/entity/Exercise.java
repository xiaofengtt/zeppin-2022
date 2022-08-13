package com.zixueku.entity;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 类说明 练习
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-16 下午3:51:49
 */
public class Exercise implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8476051497546268409L;
	@SerializedName("ssoUserTest.id")
	private int ssoUserTestId; // 本次测试id
	@SerializedName("paper.id")
	private int paperId;
	private String title; // 本次测试名称
	private int knowledgeId; // 知识点id（可以不要）
	private int subjectId; // 学科id（可以不要）
	private List<Item> items; // 试题信息
	private int totalNum; // 本次测试的总题数
	private int rightNum; // 本次测试中答对题目的数量


	public int getSsoUserTestId() {
		return ssoUserTestId;
	}

	public void setSsoUserTestId(int ssoUserTestId) {
		this.ssoUserTestId = ssoUserTestId;
	}

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(int knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getRightNum() {
		return rightNum;
	}

	public void setRightNum(int rightNum) {
		this.rightNum = rightNum;
	}

	@Override
	public String toString() {
		return "Exercise [ssoUserTestId=" + ssoUserTestId + ", paperId=" + paperId + ", title=" + title + ", knowledgeId=" + knowledgeId + ", subjectId="
				+ subjectId + ", items=" + items + ", totalNum=" + totalNum + ", rightNum=" + rightNum + "]";
	}

}
