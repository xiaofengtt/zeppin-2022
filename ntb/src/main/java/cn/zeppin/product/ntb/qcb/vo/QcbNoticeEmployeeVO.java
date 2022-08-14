/**
 * 
 */
package cn.zeppin.product.ntb.qcb.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbNoticeEmployee;
import cn.zeppin.product.ntb.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe 2016年2月8日
 */

public class QcbNoticeEmployeeVO extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8025156049869106400L;
	
	private String uuid;
	private String title;
	private String content;
	private Timestamp starttime;
	private String starttimeCN;
	private Timestamp endtime;
	private String endtimeCN;
	
	private Timestamp createtime;
	private String createtimeCN;
	
	private Boolean isShow;
	private Boolean isRead;
	
	public QcbNoticeEmployeeVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QcbNoticeEmployeeVO(QcbNoticeEmployee qne) {
		this.uuid = qne.getUuid();
		this.isRead = qne.getIsRead();
		this.isShow = qne.getIsShow();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
		this.createtimeCN = Utlity.timeSpanToString(createtime);
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
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
	

	public Timestamp getStarttime() {
		return starttime;
	}
	

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
		this.starttimeCN = Utlity.timeSpanToString(starttime);
	}
	

	public String getStarttimeCN() {
		return starttimeCN;
	}
	

	public void setStarttimeCN(String starttimeCN) {
		this.starttimeCN = starttimeCN;
	}
	

	public Timestamp getEndtime() {
		return endtime;
	}
	

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
		this.endtimeCN = Utlity.timeSpanToString(endtime);
	}
	

	public String getEndtimeCN() {
		return endtimeCN;
	}
	

	public void setEndtimeCN(String endtimeCN) {
		this.endtimeCN = endtimeCN;
	}

	
	public Boolean getIsShow() {
		return isShow;
	}
	

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	

	public Boolean getIsRead() {
		return isRead;
	}
	

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
}
