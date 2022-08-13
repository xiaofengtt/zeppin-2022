package cn.zeppin.access;

import java.util.LinkedList;
import java.util.List;

import cn.zeppin.access.ItemEx;

public class PaperSectionEx {

	private String numString;
	private Integer id;
	private int itemTypeId;
	private String itemTypeName;
	private int modeType;
	private Short inx;
	private String name;
	
	private List<ItemEx> lstItem = new LinkedList<ItemEx>();

	public PaperSectionEx() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Short getInx() {
		return inx;
	}

	public void setInx(Short inx) {
		this.inx = inx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ItemEx> getLstItem() {
		return lstItem;
	}

	public void setLstItem(List<ItemEx> lstItem) {
		this.lstItem = lstItem;
	}

	public String getNumString() {
		return numString;
	}

	public void setNumString(String numString) {
		this.numString = numString;
	}

	public int getModeType() {
		return modeType;
	}

	public void setModeType(int modeType) {
		this.modeType = modeType;
	}
	
	

}
