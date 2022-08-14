/**
 * 
 */
package cn.zeppin.product.itic.core.entity;

import java.sql.Clob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;

/**
 * @author 
 * 
 * @description 【数据对象】后台TCheckInfo入库
 */

@Entity
@Table(name="T_CHECK_INFO")
public class TCheckInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String type;
	private String dataid;
	private String datatype;
	private String dataproduct;
	private Clob newdata;
	private Timestamp updatetime;
	private String creator;
	
	public class TCheckInfoType{
		public final static String ADD = "add";
		public final static String EDIT = "edit";
		public final static String DELETE = "delete";
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDataproduct() {
		return dataproduct;
	}

	public void setDataproduct(String dataproduct) {
		this.dataproduct = dataproduct;
	}

	public Clob getNewdata() {
		return newdata;
	}

	public void setNewdata(Clob newdata) {
		this.newdata = newdata;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}
