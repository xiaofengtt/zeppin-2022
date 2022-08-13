package cn.zeppin.product.ntb.backadmin.vo;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.BkController;
import cn.zeppin.product.ntb.core.entity.BkControllerMethod;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class ControllerVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1307642330847921969L;
	
	private String uuid;
	private String name;
	private String description;
	private List<BkControllerMethod> children = new ArrayList<>();
	
	public ControllerVO(){
		
	}
	
	public ControllerVO(BkController controller){
		this.uuid = controller.getUuid();
		this.name = controller.getName();
		this.description = controller.getDescription();
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

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public List<BkControllerMethod> getChildren() {
		return children;
	}
	
	public void setChildren(List<BkControllerMethod> children) {
		this.children = children;
	}
}
