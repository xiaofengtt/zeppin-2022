package cn.zeppin.project.chinamobile.media.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity;

/**
 * VideoCommodityPoint entity. 
 */

@Entity
@Table(name = "video_commodity_point")
public class VideoCommodityPoint extends BaseEntity {
	

	private static final long serialVersionUID = 1L;
	private String id;
	private String video;
	private String timepoint;
	private String iframe;
	private String commodity;
	private String showType;
	private String showMessage;
	private String showPosition;
	private String showGif;
	private String showBanner;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	// Constructors

	/** default constructor */
	public VideoCommodityPoint() {
		
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator") 
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "video", nullable = false, length = 36)
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
	@Column(name = "timepoint", nullable = false, length = 20)
	public String getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}
	
	@Column(name = "iframe", nullable = false, length = 36)
	public String getIframe() {
		return iframe;
	}

	public void setIframe(String iframe) {
		this.iframe = iframe;
	}
	
	@Column(name = "commodity", nullable = false, length = 36)
	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	
	@Column(name = "show_type", nullable = false, length = 20)
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
	
	@Column(name = "show_message", length = 200)
	public String getShowMessage() {
		return showMessage;
	}

	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}
	
	@Column(name = "show_position", length = 20)
	public String getShowPosition() {
		return showPosition;
	}

	public void setShowPosition(String showPosition) {
		this.showPosition = showPosition;
	}
	
	@Column(name = "show_gif", length = 36)
	public String getShowGif() {
		return showGif;
	}

	public void setShowGif(String showGif) {
		this.showGif = showGif;
	}
	
	@Column(name = "show_banner", length = 36)
	public String getShowBanner() {
		return showBanner;
	}

	public void setShowBanner(String showBanner) {
		this.showBanner = showBanner;
	}
	
	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
}
