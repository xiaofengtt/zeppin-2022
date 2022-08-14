/**
 * 
 */
package cn.zeppin.product.itic.core.entity;

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
 * @description 【数据对象】TOUT_NUMBER入库
 */
@Entity
@Table(name = "TOUT_NUMBER")
public class ToutNumber extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141633696155630930L;

	private Integer id;
	private String toMobile;
	private String toTel;
	private String status;
	
	public class ToutNumberStatus {
		public final static String NORMAL = "normal";
		public final static String USED = "used";
		public final static String DELETED = "deleted";
		public final static String ERROR = "error";
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
