package com.whaty.platform.vote.basic;

import java.util.List;

import com.whaty.platform.vote.util.exception.VoteException;

public abstract class VoteQuestion {
	
	private String id;
	
	private String questionBody;
	
	private String questionType;
	
	private int itemTotalNum;
	
	private List questionItems;
	
	private List questionItemResults;
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	
	public String getQuestionBody() {
		return questionBody;
	}

	public void setQuestionBody(String questionBody) {
		this.questionBody = questionBody;
	}

	public int getItemTotalNum() {
		return itemTotalNum;
	}

	public void setItemTotalNum(int itemTotalNum) {
		this.itemTotalNum = itemTotalNum;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	
	

	/**
	 * @return the questionItemResults
	 */
	public List getQuestionItemResults() {
		return questionItemResults;
	}

	/**
	 * @param questionItemResults the questionItemResults to set
	 */
	public void setQuestionItemResults(List questionItemResults) {
		this.questionItemResults = questionItemResults;
	}

	/**
	 * @return the questionItems
	 */
	public List getQuestionItems() {
		return questionItems;
	}

	/**
	 * @param questionItems the questionItems to set
	 */
	public void setQuestionItems(List questionItems) {
		this.questionItems = questionItems;
	}

	/**����Ŀ���ѡ��
	 * @param items
	 * @throws VoteException
	 */
	public abstract void addQuestionItems(List items) throws VoteException;
	
	/**ɾ��ѡ��
	 * @param num
	 * @throws VoteException
	 */
	public abstract void deleteQuestionItems(int num) throws VoteException;
	
		
	/**��ӵ����
	 * @param num Ҫ��ӵ���
	 * @param itemNum  Ҫ��ڼ���item���
	 * @throws VoteException
	 */
	public abstract void addQuestionResult(int num,int itemNum) throws VoteException;
	
	/**��յ����
	 * @throws VoteException
	 */
	public abstract void clearResult() throws VoteException;
	
	public abstract void update() throws VoteException;

}
