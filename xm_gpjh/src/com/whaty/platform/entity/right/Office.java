package com.whaty.platform.entity.right;

import java.util.Hashtable;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class Office  implements com.whaty.platform.Items{

	public String id;
	
	public String name;
	
	public String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public abstract List getOfficeRightModel() throws PlatformException;
	
	public abstract List getSiteOffice() throws PlatformException;
	
	public abstract Hashtable getModelGroupHash();
}
