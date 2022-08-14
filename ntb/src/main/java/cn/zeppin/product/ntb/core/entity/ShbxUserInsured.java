/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】社保熊用户参保人关系
 */

@Entity
@Table(name = "shbx_user_insured", uniqueConstraints = {@UniqueConstraint(columnNames = {"shbx_user","shbx_insured"})})
public class ShbxUserInsured extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4588413064040739072L;
	
	private String uuid;
	private String shbxUser;
	private String shbxInsured;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class ShbxUserInsuredStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETED = "deleted";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "shbx_user", nullable = false, length = 36)
	public String getShbxUser() {
		return shbxUser;
	}
	
	public void setShbxUser(String shbxUser) {
		this.shbxUser = shbxUser;
	}
	
	@Column(name = "shbx_insured", nullable = false, length = 36)
	public String getShbxInsured() {
		return shbxInsured;
	}
	
	public void setShbxInsured(String shbxInsured) {
		this.shbxInsured = shbxInsured;
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
