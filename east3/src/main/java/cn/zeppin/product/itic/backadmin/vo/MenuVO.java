package cn.zeppin.product.itic.backadmin.vo;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.itic.core.entity.TSsMenu;
import cn.zeppin.product.itic.core.entity.base.Entity;

public class MenuVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String id;
	private String title;
	private Integer menulevel;
	private String pid;
	private List<MenuVO> children = new ArrayList<>();
	
	public MenuVO(){
		
	}
	
	public MenuVO(TSsMenu menu){
		this.id = menu.getId();
		this.title = menu.getTitle();
		this.menulevel = menu.getMenulevel();
		this.pid = menu.getPid();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMenulevel() {
		return menulevel;
	}
	
	public void setMenulevel(Integer menulevel) {
		this.menulevel = menulevel;
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
