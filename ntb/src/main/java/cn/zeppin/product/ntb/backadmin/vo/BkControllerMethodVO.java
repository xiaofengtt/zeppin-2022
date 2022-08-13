package cn.zeppin.product.ntb.backadmin.vo;

import cn.zeppin.product.ntb.core.entity.BkControllerMethod;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class BkControllerMethodVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String controller;
	private String controllerName;
	private String name;
	private String description;
	
	public BkControllerMethodVO(){
		
	}
	
	public BkControllerMethodVO(BkControllerMethod controllerMethod){
		this.uuid = controllerMethod.getUuid();
		this.name = controllerMethod.getName();
		this.controller = controllerMethod.getController();
		this.description = controllerMethod.getDescription();
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
