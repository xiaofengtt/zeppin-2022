package com.zixueku.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 类说明
 * 大题
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月17日 下午5:26:49
 */
public class Material implements Serializable {

	private static final long serialVersionUID = -3930419641270885452L;
	private List<Item> materialItems;
	private int totalNum;

	public List<Item> getMaterialItems() {
		return materialItems;
	}

	public void setMaterialItems(List<Item> materialItems) {
		this.materialItems = materialItems;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	@Override
	public String toString() {
		return "Material [materialItems=" + materialItems + ", totalNum=" + totalNum + "]";
	}

}
