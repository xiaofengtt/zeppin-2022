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
 * @description 【数据对象】TIN_NUMBER入库
 */
@Entity
@Table(name = "TIN_NUMBER")
public class TinNumber extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141633696155630930L;

	private Integer id;
	private String tcTel;
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

	@Column(name = "TC_TEL", length = 20)
	public String getTcTel() {
		return tcTel;
	}

	public void setTcTel(String tcTel) {
		this.tcTel = tcTel;
	}

	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
