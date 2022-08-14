package cn.product.score.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.product.score.entity.NewsPublish;
import cn.product.score.util.Utlity;

public class NewsPublishVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -592676887207062196L;
	
	private String uuid;
	private String news;
	private String category;
	private List<String> categoryList;
	private String team;
	private List<String> teamList;
	private String title;
	private String content;
	private String cover;
	private String coverUrl;
	private String author;
	private String source;
	private String sourceurl;
	private String imageurl;
	private List<String> imageurlList;
	private String newstime;
	private String status;
	private String checker;
	private String checkerName;
	private Timestamp checktime;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	
	public NewsPublishVO(NewsPublish newsPublish){
		this.uuid = newsPublish.getUuid();
		this.news = newsPublish.getNews();
		this.title = newsPublish.getTitle();
		this.content = newsPublish.getContent();
		this.author = newsPublish.getAuthor();
		this.cover = newsPublish.getCover();
		this.source = newsPublish.getSource();
		this.sourceurl = newsPublish.getSourceurl();
		this.newstime = newsPublish.getNewstime();
		this.status = newsPublish.getStatus();
		this.checker = newsPublish.getChecker();
		this.checktime = newsPublish.getChecktime();
		this.creator = newsPublish.getCreator();
		this.createtime = newsPublish.getCreatetime();
		
		this.category = newsPublish.getCategory();
		this.categoryList = new ArrayList<String>();
		if(!Utlity.checkStringNull(newsPublish.getCategory())){
			String[] categorys = newsPublish.getCategory().split(",");
			for(String cate : categorys){
				categoryList.add(cate);
			}
		}
		
		this.team = newsPublish.getTeam();
		this.teamList = new ArrayList<String>();
		if(!Utlity.checkStringNull(newsPublish.getTeam())){
			String[] teams = newsPublish.getTeam().split(",");
			for(String te : teams){
				teamList.add(te);
			}
		}
		
		this.imageurl = newsPublish.getImageurl();
		this.imageurlList = new ArrayList<String>();
		if(!Utlity.checkStringNull(newsPublish.getImageurl())){
			String[] imageurls = newsPublish.getImageurl().split(",");
			for(String image : imageurls){
				imageurlList.add(image);
			}
		}
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

	public List<String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}

	public List<String> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<String> teamList) {
		this.teamList = teamList;
	}

	public List<String> getImageurlList() {
		return imageurlList;
	}

	public void setImageurlList(List<String> imageurlList) {
		this.imageurlList = imageurlList;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
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

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getSourceurl() {
		return sourceurl;
	}

	public void setSourceurl(String sourceurl) {
		this.sourceurl = sourceurl;
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

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public Timestamp getChecktime() {
		return checktime;
	}

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
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
}