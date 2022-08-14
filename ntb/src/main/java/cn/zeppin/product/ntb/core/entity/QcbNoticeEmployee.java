/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】企财宝通知用户关系表
 */

@Entity
@Table(name = "qcb_notice_employee")
public class QcbNoticeEmployee extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2117947936719199283L;
	private String uuid;
	private String qcbNotice;
	private String qcbEmployee;
	private Boolean isShow;
	private Boolean isRead;
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "qcb_notice", nullable = false, length = 36)
	public String getQcbNotice() {
		return qcbNotice;
	}
	
	public void setQcbNotice(String qcbNotice) {
		this.qcbNotice = qcbNotice;
	}
	
	@Column(name = "qcb_employee", nullable = false, length = 36)
	public String getQcbEmployee() {
		return qcbEmployee;
	}
	
	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}

	@Column(name = "is_show", nullable = false)
	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	@Column(name = "is_read", nullable = false)
	public Boolean getIsRead() {
		return isRead;
	}
	
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
}
