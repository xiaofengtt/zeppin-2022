package com.whaty.platform.entity.right;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class Right implements com.whaty.platform.Items {

	public String id;

	public String name;

	public String status;

	public String right_id;

	public String model_id;

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRight_id() {
		return right_id;
	}

	public void setRight_id(String right_id) {
		this.right_id = right_id;
	}

	public abstract int setActive();

	public abstract int cancelActive();

	public abstract List getModelGroups(String manager_id)
			throws PlatformException;

	public abstract List getRights(String manager_id, String modelgroup)
			throws PlatformException;
	
	public abstract List getSiteRights(String submanager_id, String modelgroup)
			throws PlatformException;

}
