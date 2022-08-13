package cn.zeppin.product.ntb.backadmin.vo;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.BkMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class BkMenuVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String title;
	private Integer level;
	private String pid;
	private String icon;
	private String pname;
	private List<BkMenuVO> children = new ArrayList<>();
	
	public BkMenuVO(){
		
	}
	
	public BkMenuVO(BkMenu menu){
		this.uuid = menu.getUuid();
		this.title = menu.getTitle();
		this.level = menu.getLevel();
		this.pid = menu.getPid();
		this.icon = menu.getIcon();
		this.pname = "";
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

	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public List<BkMenuVO> getChildren() {
		return children;
	}
	
	public void setChildren(List<BkMenuVO> children) {
		this.children = children;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
}
