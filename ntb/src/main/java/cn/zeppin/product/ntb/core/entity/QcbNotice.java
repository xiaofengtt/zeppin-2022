/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】企财宝通知
 */

@Entity
@Table(name = "qcb_notice")
public class QcbNotice extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2117947936719199283L;
	private String uuid;
	private String title;
	private String content;
	private Timestamp starttime;
	private Timestamp endtime;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class QcbNoticeStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLED = "disabled";
		public final static String DELETED = "deleted";
	}

	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Column(name = "title", nullable = false)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "starttime", nullable = false)
	public Timestamp getStarttime() {
		return starttime;
	}
	
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	@Column(name = "endtime", nullable = false)
	public Timestamp getEndtime() {
		return endtime;
	}
	
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	
	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
