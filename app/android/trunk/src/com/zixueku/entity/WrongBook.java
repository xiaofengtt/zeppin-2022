package com.zixueku.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月24日 下午2:33:46
 */
public class WrongBook implements Serializable {
	private static final long serialVersionUID = 2157234918979830763L;
	private List<Item> wrongItemList;
	private int totalNum;
	private int rightNum;
	private int wrongNum;

	public List<Item> getWrongItemList() {
		return wrongItemList;
	}

	public void setWrongItemList(List<Item> wrongItemList) {
		this.wrongItemList = wrongItemList;
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

	

	public int getWrongNum() {
		return wrongNum;
	}

	public void setWrongNum(int wrongNum) {
		this.wrongNum = wrongNum;
	}

	@Override
	public String toString() {
		return "WrongBook [wrongItemList=" + wrongItemList + ", totalNum=" + totalNum + ", rightNum=" + rightNum + ", wrongNum=" + wrongNum + "]";
	}

}
