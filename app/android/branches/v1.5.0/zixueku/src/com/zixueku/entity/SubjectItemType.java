package com.zixueku.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 类说明
 * http://localhost:8080/SelfCool/userSubjectItemTypeList?user.id=1&subject.id
 * =28&isStandard=0
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月16日 下午1:43:01
 */
public class SubjectItemType implements java.io.Serializable {
	private static final long serialVersionUID = 5243229981997262090L;

	private int id;
	private int inx;
	@SerializedName("itemType.id")
	private int itemTypeId;
	@SerializedName("itemType.name")
	private String itemTypeName;
	private int lastRightItemCount;
	private int releasedItemCount;
	@SerializedName("subject.id")
	private int subjectId;

	public SubjectItemType() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInx() {
		return inx;
	}

	public void setInx(int inx) {
		this.inx = inx;
	}

	public int getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(int itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public int getLastRightItemCount() {
		return lastRightItemCount;
	}

	public void setLastRightItemCount(int lastRightItemCount) {
		this.lastRightItemCount = lastRightItemCount;
	}

	public int getReleasedItemCount() {
		return releasedItemCount;
	}

	public void setReleasedItemCount(int releasedItemCount) {
		this.releasedItemCount = releasedItemCount;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public String toString() {
		return "SubjectItemType [id=" + id + ", inx=" + inx + ", itemTypeId=" + itemTypeId + ", itemTypeName=" + itemTypeName + ", lastRightItemCount="
				+ lastRightItemCount + ", releasedItemCount=" + releasedItemCount + ", subjectId=" + subjectId + "]";
	}

}
