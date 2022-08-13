package com.cmos.china.mobile.media.core.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.cmos.china.mobile.media.core.util.Utlity;

/**
 * 留言信息bean
 * @author zeppin
 *
 */
@SuppressWarnings("serial")
public class LeaveMessageVO implements Serializable {

	private String id;
	private String content;
	private String status;
	private String creator;
	private Timestamp createtime;
	private String name;
	private String title;
	private String createtimeCN;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
		this.createtimeCN = Utlity.timeSpanToString(createtime);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreatetimeCN() {
		return createtimeCN;
	}
	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
	
}
