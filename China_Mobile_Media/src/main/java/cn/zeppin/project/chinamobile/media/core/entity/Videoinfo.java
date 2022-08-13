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
 * Videoinfo entity. 
 * 
 * @author Clark.R 2016.04.29
 */
@Entity
@Table(name = "videoinfo")
public class Videoinfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2690805028405263533L;
	

	private String id;
	private String title;
	private String context;
	private String tag;
	private String status;
	private String thumbnail;
	private String video;
	private String timeLength;
	private Boolean transcodingFlag;
	private String originalVideo;
	private String source;
	private String copyright;
	private String author;
	private String creator;
	private Timestamp createtime;
	
	public class VideoStatusType implements StatusType {
		public final static String DELETED = "deleted";
		public final static String UPLOADED = "uploaded";
		public final static String TRANSCODING = "transcoding";
		public final static String UNCHECKED = "unchecked";
		public final static String FAILED = "failed";
		public final static String CHECKED = "checked";
		public final static String DESTROY = "destroy";
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
	
	@Column(name = "title", nullable = false, length = 200)
	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}


	@Column(name = "context")
	public String getContext() {
		return context;
	}



	public void setContext(String context) {
		this.context = context;
	}


	@Column(name = "tag", length = 200)
	public String getTag() {
		return tag;
	}



	public void setTag(String tag) {
		this.tag = tag;
	}


	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}


	@Column(name = "thumbnail", length = 36)
	public String getThumbnail() {
		return thumbnail;
	}



	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}


//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "video")
	@Column(name = "video", length = 36)
	public String getVideo() {
		return video;
	}



	public void setVideo(String video) {
		this.video = video;
	}


	@Column(name = "time_length", length = 30)
	public String getTimeLength() {
		return timeLength;
	}



	public void setTimeLength(String timeLength) {
		this.timeLength = timeLength;
	}


	@Column(name = "transcoding_flag")
	public Boolean getTranscodingFlag() {
		return transcodingFlag;
	}



	public void setTranscodingFlag(Boolean transcodingFlag) {
		this.transcodingFlag = transcodingFlag;
	}


	@Column(name = "original_video", length = 36)
	public String getOriginalVideo() {
		return originalVideo;
	}


	public void setOriginalVideo(String originalVideo) {
		this.originalVideo = originalVideo;
	}


	@Column(name = "source", length = 36)
	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	@Column(name = "copyright", length = 200)
	public String getCopyright() {
		return copyright;
	}



	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}


	@Column(name = "author", length = 200)
	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
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

