package cn.zeppin.project.chinamobile.media.web.vo.iface;

import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;

public class WebCategoryVO implements Entity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String id;
	private String name;
	private List<WebCategoryVO> child;
	
	
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
	public List<WebCategoryVO> getChild() {
		return child;
	}
	public void setChild(List<WebCategoryVO> child) {
		this.child = child;
	}

	
}
