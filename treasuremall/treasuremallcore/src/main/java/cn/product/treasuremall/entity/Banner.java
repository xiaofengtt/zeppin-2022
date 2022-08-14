package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class Banner implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8739544390841628103L;
	@Id
    private String uuid;
	private String type;
	private String title;
	private String code;
	private String image;
	private String url;
	private String status;
	
	private Timestamp starttime;
	private Timestamp endtime;
	private Integer sort;
	private Timestamp createtime;
	private String frontUserLevel;
    
	public class BannerType {
		public final static String TYPE_START = "type_start";
		public final static String TYPE_TOP = "type_top";
		public final static String TYPE_EJECT = "type_eject";
		public final static String TYPE_LOGIN = "type_login";
		public final static String TYPE_PAYMENT = "type_payment";
	}
	
	public class BannerStatus {
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getFrontUserLevel() {
		return frontUserLevel;
	}

	public void setFrontUserLevel(String frontUserLevel) {
		this.frontUserLevel = frontUserLevel;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

}