package cn.product.score.vo.front;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.product.score.entity.NewsPublish;
import cn.product.score.entity.Team;

public class NewsPublishVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -592676887207062196L;
	
	private String uuid;
	private String news;
	private String category;
	private List<CategoryVO> categoryList;
	private String team;
	private List<Team> teamList;
	private String title;
	private String content;
	private String coverUrl;
	private String author;
	private String newstime;
	private String status;
	
	public NewsPublishVO(NewsPublish newsPublish){
		this.uuid = newsPublish.getUuid();
		this.news = newsPublish.getNews();
		this.title = newsPublish.getTitle();
		this.content = newsPublish.getContent();
		this.author = newsPublish.getAuthor();
		this.newstime = newsPublish.getNewstime();
		this.status = newsPublish.getStatus();
		
		this.category = newsPublish.getCategory();
		this.categoryList = new ArrayList<CategoryVO>();
		
		this.team = newsPublish.getTeam();
		this.teamList = new ArrayList<Team>();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public List<CategoryVO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryVO> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Team> teamList) {
		this.teamList = teamList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getNewstime() {
		return newstime;
	}

	public void setNewstime(String newstime) {
		this.newstime = newstime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}