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
 * @description 【数据对象】企财宝企业认证审核记录
 */

@Entity
@Table(name = "qcb_company_operate_check")
public class QcbCompanyOperateCheck extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8705570241318843074L;
	private String uuid;
	private String qcbCompanyOperate;
	private String reason;
	private String status;
	private String creator;
	private Timestamp createtime;

	public class QcbCompanyOperateCheckStatus{
		public final static String UNPASSED = "unpassed";
		public final static String CHECKED = "checked";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "qcb_company_operate", nullable = false, length = 36)
	public String getQcbCompanyOperate() {
		return qcbCompanyOperate;
	}
	
	public void setQcbCompanyOperate(String qcbCompanyOperate) {
		this.qcbCompanyOperate = qcbCompanyOperate;
	}

	@Column(name = "reason", nullable = false, length = 200)
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Column(name = "status", nullable = false, length = 20)
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
