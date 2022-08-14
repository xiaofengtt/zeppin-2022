package cn.zeppin.product.score.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.score.entity.News;
import cn.zeppin.product.score.util.Utlity;

public class NewsVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -592676887207062196L;
	
	private String uuid;
	private String category;
	private List<String> categoryList;
	private String team;
	private List<String> teamList;
	private String title;
	private String content;
	private String author;
	private String source;
	private String sourceurl;
	private String imageurl;
	private List<String> imageurlList;
	private String newstime;
	private String status;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	
	public NewsVO(News news){
		this.uuid = news.getUuid();
		this.title = news.getTitle();
		this.content = news.getContent();
		this.author = news.getAuthor();
		this.source = news.getSource();
		this.sourceurl = news.getSourceurl();
		this.newstime = news.getNewstime();
		this.status = news.getStatus();
		this.creator = news.getCreator();
		this.createtime = news.getCreatetime();
		
		this.category = news.getCategory();
		this.categoryList = new ArrayList<String>();
		if(!Utlity.checkStringNull(news.getCategory())){
			String[] categorys = news.getCategory().split(",");
			for(String cate : categorys){
				categoryList.add(cate);
			}
		}
		
		this.team = news.getTeam();
		this.teamList = new ArrayList<String>();
		if(!Utlity.checkStringNull(news.getTeam())){
			String[] teams = news.getTeam().split(",");
			for(String te : teams){
				teamList.add(te);
			}
		}
		
		this.imageurl = news.getImageurl();
		this.imageurlList = new ArrayList<String>();
		if(!Utlity.checkStringNull(news.getImageurl())){
			String[] imageurls = news.getImageurl().split(",");
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