package cn.zeppin.project.chinamobile.media.web.vo;

import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;

public class CommodityDisplayVO implements Entity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String id;
	private String name;
	private Integer displayIndex;
	
	
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
	public Integer getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(Integer displayIndex) {
		this.displayIndex = displayIndex;
	}
	
	

}
