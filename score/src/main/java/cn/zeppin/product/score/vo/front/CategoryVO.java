package cn.zeppin.product.score.vo.front;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import cn.zeppin.product.score.entity.Category;

public class CategoryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2691007427946786584L;
	
	private String uuid;
	private String name;
	private String shortname;
	private Integer level;
	private String parent;
	private String scode;
	private String icon;
	private String iconUrl;
	private Boolean istag;
	private String status;
	private List<CategoryVO> children;
	
	public CategoryVO(Category category){
		this.uuid = category.getUuid();
		this.name = category.getName();
		this.shortname = category.getShortname();
		this.level = category.getLevel();
		this.parent = category.getParent();
		this.scode = category.getScode();
		this.icon = category.getIcon();
		this.istag = category.getIstag();
		this.status = category.getStatus();
		this.children = new LinkedList<CategoryVO>();
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

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Boolean getIstag() {
		return istag;
	}

	public void setIstag(Boolean istag) {
		this.istag = istag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CategoryVO> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryVO> children) {
		this.children = children;
	}
}