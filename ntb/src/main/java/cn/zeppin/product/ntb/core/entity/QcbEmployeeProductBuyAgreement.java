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
 * @author hehe
 * @description 【数据对象】企财宝员工理财产品购买协议
 */

@Entity
@Table(name = "qcb_employee_product_buy_agreement")
public class QcbEmployeeProductBuyAgreement extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6121638623124350691L;
	
	private String uuid;
	private String qcbEmployee;
	
	private String records;
	private String name;
	
	private Timestamp createtime;
	private String type;
	
	private String status;
	
	private String scode;
	
	private String url;
	
	public class QcbEmployeeProductBuyAgreementStatus{
		public final static String FAIL = "fail";
		public final static String CONFIRMING = "confirming";
		public final static String SUCCESS = "success";
		public final static String FINISHED = "finished";
	}
	
	public class QcbEmployeeProductBuyAgreementType{
		public final static String BANKPRODUCT = "bankproduct";//银行理财产品
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "qcb_employee", nullable = false, length = 36)
	public String getQcbEmployee() {
		return qcbEmployee;
	}

	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}

	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Column(name = "records", nullable = false, length = 36)
	public String getRecords() {
		return records;
	}
	

	public void setRecords(String records) {
		this.records = records;
	}
	
	@Column(name = "name", nullable = false, length = 200)
	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}
	

	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "scode", nullable = false, length = 36)
	public String getScode() {
		return scode;
	}
	

	public void setScode(String code) {
		this.scode = code;
	}

	@Column(name = "url", nullable = false, length = 300)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
