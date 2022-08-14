package cn.zeppin.product.itic.backadmin.vo;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.itic.core.entity.TSsMenu;
import cn.zeppin.product.itic.core.entity.TSsOperatorMenu;
import cn.zeppin.product.itic.core.entity.base.Entity;

public class TSsOperatorMenuVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String id;
	private String operator;
	private String menu;
	private Integer sort;
	private String title;
	private Integer level;
	private String pid;
	private List<TSsOperatorMenuVO> children = new ArrayList<>();
	
	public TSsOperatorMenuVO(TSsOperatorMenu rm, TSsMenu m){
		this.id = rm.getId();
		this.operator = rm.getOperator();
		this.menu = rm.getMenu();
		this.sort = m.getSort();
		this.title = m.getTitle();
		this.level = m.getMenulevel();
		this.pid = m.getPid();
		this.children = new ArrayList<>();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getMenu() {
		return menu;
	}
	
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
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
	
	public List<TSsOperatorMenuVO> getChildren() {
		return children;
	}
	
	public void setChildren(List<TSsOperatorMenuVO> children) {
		this.children = children;
	}
}
