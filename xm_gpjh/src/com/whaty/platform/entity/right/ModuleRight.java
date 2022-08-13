package com.whaty.platform.entity.right;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class ModuleRight implements com.whaty.platform.Items {
	
	public String id ;
	
	public String name ;
	
	public String status ;

	public String modelgroup_id;
	
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
	
	public abstract int setActive();

	public abstract int cancelActive();
	

	public abstract List getModelGroups(String manager_id)
			throws PlatformException;

	public abstract List getRights(String manager_id, String modelgroup)
			throws PlatformException;
	
	public abstract List getSiteRights(String submanager_id, String modelgroup)
			throws PlatformException;

	public String getModelgroup_id() {
		return modelgroup_id;
	}

	public void setModelgroup_id(String modelgroup_id) {
		this.modelgroup_id = modelgroup_id;
	}
	
}
