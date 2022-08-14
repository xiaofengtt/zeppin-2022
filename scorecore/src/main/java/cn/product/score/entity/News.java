package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class News implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -592676887207062196L;
	
	@Id
	private String uuid;
	private String category;
	private String team;
	private String title;
	private String content;
	private String author;
	private String source;
	private String sourceurl;
	private String imageurl;
	private String newstime;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class NewsStatus{
		public final static String NORMAL = "normal";
		public final static String PUBLISH = "publish";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
	}
	
	public class NewsSource{
		public final static String TENCENT = "tencent";
		public final static String SINA = "sina";
		public final static String NETEASY = "neteasy";
		public final static String SELF = "self";
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}