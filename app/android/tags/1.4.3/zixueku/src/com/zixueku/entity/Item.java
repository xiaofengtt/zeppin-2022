package com.zixueku.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.annotations.SerializedName;

/**
 * 类说明 试题
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-16 下午2:48:07
 */
public class Item implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4811163900358953679L;
	private int id; // 试题id
	private int index;// 试题的题号

	/*
	 * "item.id":402, "item.level":1,
	 * 
	 * "itemType.id":25, "itemType.name":"材料分析题",
	 */
	@SerializedName("itemType.id")
	private short itemTypeId; // 类型id

	@SerializedName("itemType.name")
	private String itemTypeName; // 试题类型

	@SerializedName("item.id")
	private int itemId;

	@SerializedName("item.level")
	private int itemLevel;
	/*
	 * 1单选择 2填空 3判断 4组合 5多选 6问答
	 */
	private short modelType; // 试题类型模板id
	private String source; // 试题出处
	private String sourceTypeCN;
	private Set<CustomerAnswer> customerAnswer = new TreeSet<CustomerAnswer>(); // 用户答案
																				// 存储option的id
	private String analysis; // 试题解析
	private boolean isRight = true; // 用户该题是否答对
	private Data data; // 单选题和多选题的题干信息和选项信息
	private List<Item> children;
	private int testItemCorrectCount; // 答对的次数
	private int testItemCount; // 答题总次数
	private int testItemWrongCount;// 答错的次数
	private boolean isgroup;
	private short defaultScore;
	private int blankInx;
	private boolean analysisIsRead; // 在错题本和大题中标志答案解析是否被查看
	private int completeType = -1;// 错题本中 -1未答, 0答错,1答对 主观题中 -1没看过,0未掌握,1掌握
	private int lastTestCompleteType;
	private int isWrongbookItemTested;  //1已经答过，0未答过

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public short getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(short itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public String getSourceTypeCN() {
		return sourceTypeCN;
	}

	public void setSourceTypeCN(String sourceTypeCN) {
		this.sourceTypeCN = sourceTypeCN;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Set<CustomerAnswer> getCustomerAnswer() {
		return customerAnswer;
	}

	public void setCustomerAnswer(Set<CustomerAnswer> customerAnswer) {
		this.customerAnswer = customerAnswer;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public short getModelType() {
		return modelType;
	}

	public void setModelType(short modelType) {
		this.modelType = modelType;
	}

	public List<Item> getChildren() {
		return children;
	}

	public void setChildren(List<Item> children) {
		this.children = children;
	}

	public int getTestItemCorrectCount() {
		return testItemCorrectCount;
	}

	public void setTestItemCorrectCount(int testItemCorrectCount) {
		this.testItemCorrectCount = testItemCorrectCount;
	}

	public int getTestItemCount() {
		return testItemCount;
	}

	public void setTestItemCount(int testItemCount) {
		this.testItemCount = testItemCount;
	}

	public int getTestItemWrongCount() {
		return testItemWrongCount;
	}

	public void setTestItemWrongCount(int testItemWrongCount) {
		this.testItemWrongCount = testItemWrongCount;
	}

	public boolean isIsgroup() {
		return isgroup;
	}

	public void setIsgroup(boolean isgroup) {
		this.isgroup = isgroup;
	}

	public short getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(short defaultScore) {
		this.defaultScore = defaultScore;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

	public int getBlankInx() {
		return blankInx;
	}

	public void setBlankInx(int blankInx) {
		this.blankInx = blankInx;
	}

	public boolean isAnalysisIsRead() {
		return analysisIsRead;
	}

	public void setAnalysisIsRead(boolean analysisIsRead) {
		this.analysisIsRead = analysisIsRead;
	}

	public int getCompleteType() {
		return completeType;
	}

	public void setCompleteType(int completeType) {
		this.completeType = completeType;
	}
	

	public int getLastTestCompleteType() {
		return lastTestCompleteType;
	}

	public void setLastTestCompleteType(int lastTestCompleteType) {
		this.lastTestCompleteType = lastTestCompleteType;
	}
	

	public int getIsWrongbookItemTested() {
		return isWrongbookItemTested;
	}

	public void setIsWrongbookItemTested(int isWrongbookItemTested) {
		this.isWrongbookItemTested = isWrongbookItemTested;
	}


	@Override
	public String toString() {
		return "Item [id=" + id + ", index=" + index + ", itemTypeId=" + itemTypeId + ", itemTypeName=" + itemTypeName + ", itemId=" + itemId + ", itemLevel=" + itemLevel + ", modelType=" + modelType
				+ ", source=" + source + ", sourceTypeCN=" + sourceTypeCN + ", customerAnswer=" + customerAnswer + ", analysis=" + analysis + ", isRight=" + isRight + ", data=" + data + ", children="
				+ children + ", testItemCorrectCount=" + testItemCorrectCount + ", testItemCount=" + testItemCount + ", testItemWrongCount=" + testItemWrongCount + ", isgroup=" + isgroup
				+ ", defaultScore=" + defaultScore + ", blankInx=" + blankInx + ", analysisIsRead=" + analysisIsRead + ", completeType=" + completeType + ", lastTestCompleteType="
				+ lastTestCompleteType + ", isWrongbookItemTested=" + isWrongbookItemTested + "]";
	}

	/**
	 * 实现深复制的方法
	 */
	public Object deepCopy() {
		ObjectInputStream ois;
		// 字节数组输出流，暂存到内存中
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			// 序列化
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			// 反序列化
			return ois.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
