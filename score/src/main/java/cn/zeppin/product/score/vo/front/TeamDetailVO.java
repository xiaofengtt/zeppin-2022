package cn.zeppin.product.score.vo.front;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;

public class TeamDetailVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5906634499291681973L;
	
	private String uuid;
	private String category;
	private List<CategoryVO> categoryList;
	private String name;
	private String shortname;
	private String icon;
	private String iconUrl;
	private String status;
	private List<Map<String, Object>> coachesList;
	private List<InfoPlayersVO> playersList;
	
	@SuppressWarnings("unchecked")
	public TeamDetailVO(Team team){
		this.uuid = team.getUuid();
		this.category = team.getCategory();
		this.categoryList = new ArrayList<CategoryVO>();
		this.name = team.getName();
		this.shortname = team.getShortname();
		this.icon = team.getIcon();
		this.status = team.getStatus();
		
		if(!Utlity.checkStringNull(team.getCoaches())){
			this.coachesList = (List<Map<String, Object>>)JSONUtils.json2obj(team.getCoaches(), List.class);
		}else{
			this.coachesList = new ArrayList<>();
		}
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

	public List<CategoryVO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryVO> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Map<String, Object>> getCoachesList() {
		return coachesList;
	}

	public void setCoachesList(List<Map<String, Object>> coachesList) {
		this.coachesList = coachesList;
	}

	public List<InfoPlayersVO> getPlayersList() {
		return playersList;
	}

	public void setPlayersList(List<InfoPlayersVO> playersList) {
		this.playersList = playersList;
	}
}