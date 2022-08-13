package cn.zeppin.product.ntb.backadmin.vo;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.BkMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class MenuVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String title;
	private Integer level;
	private String pid;
	private List<MenuVO> children = new ArrayList<>();
	
	public MenuVO(){
		
	}
	
	public MenuVO(BkMenu menu){
		this.uuid = menu.getUuid();
		this.title = menu.getTitle();
		this.level = menu.getLevel();
		this.pid = menu.getPid();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<MenuVO> getChildren() {
		return children;
	}
	
	public void setChildren(List<MenuVO> children) {
		this.children = children;
	}
}
