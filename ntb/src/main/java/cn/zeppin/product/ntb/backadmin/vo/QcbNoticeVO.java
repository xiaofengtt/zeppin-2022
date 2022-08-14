/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbNotice;
import cn.zeppin.product.ntb.core.entity.QcbNotice.QcbNoticeStatus;
import cn.zeppin.product.ntb.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe 2016年2月8日
 */

public class QcbNoticeVO extends BaseEntity {
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
	
	private String creator;
	private String creatorCN;
	private Timestamp createtime;
	private String createtimeCN;
	private String status;
	private String statusCN;
	
	private Integer count = 1;
	
	
	public QcbNoticeVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QcbNoticeVO(QcbNotice qn) {
		this.uuid = qn.getUuid();
		this.title = qn.getTitle();
		this.content = qn.getContent();
		this.starttime = qn.getStarttime();
		this.starttimeCN = Utlity.timeSpanToString(qn.getStarttime());
		this.endtime = qn.getEndtime();
		this.endtimeCN = Utlity.timeSpanToString(qn.getEndtime());
		this.createtime = qn.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qn.getCreatetime());
		this.creator = qn.getCreator();
		this.creatorCN = "--";
		this.status = qn.getStatus();
		if(QcbNoticeStatus.NORMAL.equals(qn.getStatus())){
			this.statusCN = "正常";
		} else if (QcbNoticeStatus.DELETED.equals(qn.getStatus())) {
			this.statusCN = "已删除";
		} else if (QcbNoticeStatus.DISABLED.equals(qn.getStatus())) {
			this.statusCN = "已失效";
		} else {
			this.statusCN = "-";
		}
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getStatusCN() {
		return statusCN;
	}

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public String getCreatorCN() {
		return creatorCN;
	}

	public void setCreatorCN(String creatorCN) {
		this.creatorCN = creatorCN;
	}
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	
}
