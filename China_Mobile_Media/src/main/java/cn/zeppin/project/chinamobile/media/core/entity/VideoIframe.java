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
 * VideoIframe entity. 
 */
@Entity
@Table(name = "video_iframe")
public class VideoIframe extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String video;
	private String timepoint;
	private String path;
	private Timestamp createtime;
	
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

	@Column(name = "timepoint", nullable = false, length = 20)
	public String getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}
	
	@Column(name = "video", nullable = false, length = 36)
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Column(name = "path", length = 200)
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}

