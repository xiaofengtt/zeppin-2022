/**
 * 
 */
package cn.zeppin.product.itic.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;

/**
 * @author L
 *
 * 
 * @description 【数据对象】TNUMBER_RELATION入库
 */
@Entity
//@Table(name = "TNUMBER_RELATION", catalog = "EFCRM", schema="dbo")
@Table(name = "TNUMBER_RELATION")
public class TnumberRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141633696155630930L;

	private Integer id;
	private String status;
	private Timestamp createtime;
	private Integer fkTcustomers;
	private Integer fkToperator;
	private String toMobile;
	private String toTel;
	private String tcPhone;
	private String tcTel;
	private Integer processStatus;
	
	private Integer fkOpMobile;
	
	private Timestamp expiryDate;//失效时间
	
	private Integer maxduration;
	private Integer isrecord;
	private String waybillnum;
	
	public static class TnumberRelationStatus {
		public final static String NORMAL = "normal";
		public final static String UNABLE = "unable";
		public final static String DELETED = "deleted";
		public final static String ERROR = "error";
		public final static String EMPTY = "empty";
	}
	
	public static class TnumberRelationProcessStatus {
		public final static Integer NORMAL = -1;
		public final static Integer ADD = 0;
		public final static Integer EDIT = 1;
		public final static Integer DELETED = 2;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "STATUS", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATETIME", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "FK_TCUSTOMERS")
	public Integer getFkTcustomers() {
		return fkTcustomers;
	}

	public void setFkTcustomers(Integer fkTcustomers) {
		this.fkTcustomers = fkTcustomers;
	}

	@Column(name = "FK_TOPERATOR", nullable = false)
	public Integer getFkToperator() {
		return fkToperator;
	}

	public void setFkToperator(Integer fkToperator) {
		this.fkToperator = fkToperator;
	}

	@Column(name = "TO_MOBILE", length = 20)
	public String getToMobile() {
		return toMobile;
	}

	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}

	@Column(name = "TO_TEL", length = 20)
	public String getToTel() {
		return toTel;
	}

	public void setToTel(String toTel) {
		this.toTel = toTel;
	}

	@Column(name = "TC_PHONE", length = 20)
	public String getTcPhone() {
		return tcPhone;
	}

	public void setTcPhone(String tcPhone) {
		this.tcPhone = tcPhone;
	}
	
	@Column(name = "TC_TEL", length = 20)
	public String getTcTel() {
		return tcTel;
	}

	public void setTcTel(String tcTel) {
		this.tcTel = tcTel;
	}

	@Column(name = "PROCESS_STATUS", nullable = false)
	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	@Column(name = "FK_OP_MOBILE")
	public Integer getFkOpMobile() {
		return fkOpMobile;
	}

	public void setFkOpMobile(Integer fkOpMobile) {
		this.fkOpMobile = fkOpMobile;
	}

	@Column(name = "EXPIRY_DATE")
	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name = "MAXDURATION")
	public Integer getMaxduration() {
		return maxduration;
	}

	public void setMaxduration(Integer maxduration) {
		this.maxduration = maxduration;
	}

	@Column(name = "ISRECORD")
	public Integer getIsrecord() {
		return isrecord;
	}

	public void setIsrecord(Integer isrecord) {
		this.isrecord = isrecord;
	}

	@Column(name = "WAYBILLNUM")
	public String getWaybillnum() {
		return waybillnum;
	}

	public void setWaybillnum(String waybillnum) {
		this.waybillnum = waybillnum;
	}
	
}
