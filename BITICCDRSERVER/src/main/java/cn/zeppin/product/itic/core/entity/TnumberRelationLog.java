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
 * @description 【数据对象】TNUMBER_RELATION_LOG入库
 */
@Entity
@Table(name = "TNUMBER_RELATION_LOG")
public class TnumberRelationLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141633696155630930L;

	private Integer id;
	private String status;
	private Timestamp createtime;
	private Integer fkTcustomers;
	private Integer fkToperator;
	private String tcPhone;
	private String tcTel;
	private String type;
	private Integer fkTnumberRelation;
	private String toMobile;
	private String toTel;
	
	
	public static class TnumberRelationLogStatus {
		public final static String NORMAL = "normal";
		public final static String DISABLED = "disabled";
		public final static String DELETED = "deleted";
		public final static String ERROR = "error";
	}
	
	public static class TnumberRelationLogType {
		public final static String INSERT = "insert";
		public final static String DELETE = "delete";
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

	@Column(name = "TYPE", length = 20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "FK_TNUMBER_RELATION", length = 20)
	public Integer getFkTnumberRelation() {
		return fkTnumberRelation;
	}

	public void setFkTnumberRelation(Integer fkTnumberRelation) {
		this.fkTnumberRelation = fkTnumberRelation;
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
}
