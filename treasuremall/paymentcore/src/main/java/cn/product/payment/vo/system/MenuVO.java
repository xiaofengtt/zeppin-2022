package cn.product.payment.vo.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.product.payment.entity.Menu;

public class MenuVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String type;
	private String name;
	private Integer level;
	private String parent;
	private String pname;
	private String url;
	private String icon;
	private List<MenuVO> children = new ArrayList<>();
	
	public MenuVO(){
		
	}
	
	public MenuVO(Menu menu){
		this.uuid = menu.getUuid();
		this.type = menu.getType();
		this.name = menu.getName();
		this.level = menu.getLevel();
		this.parent = menu.getParent();
		this.url = menu.getUrl();
		this.icon = menu.getIcon();
		this.pname = "";
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<MenuVO> getChildren() {
		return children;
	}
	
	public void setChildren(List<MenuVO> children) {
		this.children = children;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
