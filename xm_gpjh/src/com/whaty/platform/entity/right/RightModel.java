package com.whaty.platform.entity.right;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class RightModel implements com.whaty.platform.Items{

	public String id;
	
	public String name;
	
	public String office_id;
	
	public String model_id;
	
	public String status;

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

	public String getOffice_id() {
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
	
	public abstract List getModelRights() throws PlatformException;
	
	public abstract List getSiteModelRights() throws PlatformException;
}
