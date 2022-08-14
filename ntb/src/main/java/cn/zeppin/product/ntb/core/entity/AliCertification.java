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
 * @author hehe 2016年2月8日
 * @description 【数据对象】支行信息
 */

@Entity
@Table(name = "ali_certification")
public class AliCertification extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private String productCode;
	private String transcationid;
	private String inscription;
	private String code;
	private String msg;
	private String subCode;
	private String subMsg;
	private String creator;
	private Timestamp createtime;
	
	private String binNo;
	private String verifyCode;
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	@Column(name = "product_code", nullable = false, length = 32)
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Column(name = "transcationid", nullable = false, length = 64)
	public String getTranscationid() {
		return transcationid;
	}

	public void setTranscationid(String transcationid) {
		this.transcationid = transcationid;
	}

	@Column(name = "inscription", length = 50)
	public String getInscription() {
		return inscription;
	}

	public void setInscription(String inscription) {
		this.inscription = inscription;
	}

	@Column(name = "code", nullable = false, length = 10)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "msg", nullable = false, length = 100)
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Column(name = "sub_code", nullable = false, length = 10)
	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	@Column(name = "sub_msg", nullable = false, length = 100)
	public String getSubMsg() {
		return subMsg;
	}

	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}

	@Column(name = "biz_no", nullable = false, length = 32)
	public String getBinNo() {
		return binNo;
	}

	public void setBinNo(String binNo) {
		this.binNo = binNo;
	}

	@Column(name = "verify_code", nullable = false, length = 512)
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
}
