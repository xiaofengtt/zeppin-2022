package com.zixueku.entity;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 类说明 知识树数据结构
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-12 下午5:42:16
 */
public class Knowledge implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4088266285155263388L;

	/** 元素的id */
	private int id;

	/** 文字内容 */
	private String name;

	/** 在tree中的层级 */
	private int level;

	/** 是否有子元素 */
	private boolean hasChild;

	private LinkedList<Knowledge> data;

	/** item是否展开 */
	private boolean isExpanded;

	/** 该知识点用户已或得的等级 */
	private int rank;

	/** 该知识点总题目数量 */
	private int itemCount;

	/** 该知识点已做对题目数量 */
	private int rightCount;

	private String scode;

	private boolean changeFlag;

	// 用户已做题目数量
	private int testTotalCount;

	private int rightChangeCount;

	/** 表示该元素位于最顶层的层级 */
	public static final int TOP_LEVEL = 1;

	/** 表示该节点没有父元素，也就是level为1的节点 */
	public static final int NO_PARENT = -1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHaschild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public LinkedList<Knowledge> getData() {
		return data;
	}

	public void setData(LinkedList<Knowledge> data) {
		this.data = data;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getRightCount() {
		return rightCount;
	}

	public void setRightCount(int rightCount) {
		this.rightCount = rightCount;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public boolean isChangeFlag() {
		return changeFlag;
	}

	public void setChangeFlag(boolean changeFlag) {
		this.changeFlag = changeFlag;
	}

	public int getTestTotalCount() {
		return testTotalCount;
	}

	public void setTestTotalCount(int testTotalCount) {
		this.testTotalCount = testTotalCount;
	}

	public int getRightChangeCount() {
		return rightChangeCount;
	}

	public void setRightChangeCount(int rightChangeCount) {
		this.rightChangeCount = rightChangeCount;
	}

	@Override
	public String toString() {
		return "Knowledge [id=" + id + ", name=" + name + ", level=" + level + ", hasChild=" + hasChild + ", data=" + data + ", isExpanded=" + isExpanded
				+ ", rank=" + rank + ", itemCount=" + itemCount + ", rightCount=" + rightCount + ", scode=" + scode + ", changeFlag=" + changeFlag
				+ ", testTotalCount=" + testTotalCount + ", rightChangeCount=" + rightChangeCount + "]";
	}

}
