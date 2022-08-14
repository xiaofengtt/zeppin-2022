package cn.zeppin.product.ntb.qcb.vo;

import java.util.HashMap;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class QcbMenuLessVO implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1751937972481883042L;
	private String uuid;
	private String name;
	private String title;
	private Integer level;
	private String pid;
	private Boolean flagOwn;
	
	private Map<String, QcbMenuLessVO> childMenu;
	
	
	public QcbMenuLessVO(){
		
	}
	
	public QcbMenuLessVO(QcbMenu menu){
		this.uuid = menu.getUuid();
		this.name = menu.getName();
		this.title = menu.getTitle();
		this.level = menu.getLevel();
		this.pid = menu.getPid();
		this.flagOwn = false;
		this.childMenu = new HashMap<String, QcbMenuLessVO>();
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

	public Boolean getFlagOwn() {
		return flagOwn;
	}

	public void setFlagOwn(Boolean flagOwn) {
		this.flagOwn = flagOwn;
	}

	
	public Map<String, QcbMenuLessVO> getChildMenu() {
		return childMenu;
	}
	

	public void setChildMenu(Map<String, QcbMenuLessVO> childMenu) {
		this.childMenu = childMenu;
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


	public String getTitle() {
		return title;
	}
	

	public void setTitle(String title) {
		this.title = title;
	}
}
