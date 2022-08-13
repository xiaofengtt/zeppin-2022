package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 监考责任书 模板
 * 
 * @author zeppin
 *
 */
@Entity
@Table(name = "invigilation_template")
public class InvigilationTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	private String content;
	private Timestamp createtime;
	private Integer creator;

	public InvigilationTemplate() {
		super();
	}

	public InvigilationTemplate(Integer id, String title, Timestamp createtime, Integer creator) {
		super();
		this.id = id;
		this.title = title;
		this.createtime = createtime;
		this.creator = creator;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TITLE", length = 20)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT", length = 20)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "CREATOR", nullable = false, length = 11)
	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}
}
