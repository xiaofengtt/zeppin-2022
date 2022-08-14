package cn.zeppin.product.score.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.InfoPlayers;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;

public class TeamVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2691007427946786584L;
	
	private String uuid;
	private String category;
	private List<String> categoryList;
	private String name;
	private String shortname;
	private String icon;
	private String iconUrl;
	private String status;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private List<Map<String, Object>> coachesList;
	private List<InfoPlayers> playersList;
	
	@SuppressWarnings("unchecked")
	public TeamVO(Team team){
		this.uuid = team.getUuid();
		this.name = team.getName();
		this.shortname = team.getShortname();
		
		this.category = team.getCategory();
		this.categoryList = new ArrayList<String>();
		if(!Utlity.checkStringNull(team.getCategory())){
			String[] categorys = team.getCategory().split(",");
			for(String cate : categorys){
				categoryList.add(cate);
			}
		}
		if(!Utlity.checkStringNull(team.getCoaches())){
			this.coachesList = (List<Map<String, Object>>)JSONUtils.json2obj(team.getCoaches(), List.class);
		}else{
			this.coachesList = new ArrayList<>();
		}
		this.icon = team.getIcon();
		this.status = team.getStatus();
		this.creator = team.getCreator();
		this.createtime = team.getCreatetime();
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

	public List<String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public List<InfoPlayers> getPlayersList() {
		return playersList;
	}

	public void setPlayersList(List<InfoPlayers> playersList) {
		this.playersList = playersList;
	}

	public List<Map<String, Object>> getCoachesList() {
		return coachesList;
	}

	public void setCoachesList(List<Map<String, Object>> coachesList) {
		this.coachesList = coachesList;
	}
}