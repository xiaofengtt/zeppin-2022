package cn.product.score.vo.front;

import java.io.Serializable;

import cn.product.score.entity.Team;

public class TeamVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5906634499291681973L;
	
	private String uuid;
	private String category;
	private String name;
	private String shortname;
	private String icon;
	private String iconUrl;
	private String status;
	
	public TeamVO(Team team){
		this.uuid = team.getUuid();
		this.category = team.getCategory();
		this.name = team.getName();
		this.shortname = team.getShortname();
		this.icon = team.getIcon();
		this.status = team.getStatus();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}