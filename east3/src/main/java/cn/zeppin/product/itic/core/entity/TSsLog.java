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

@Entity
@Table(name = "T_SS_LOG")
public class TSsLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String type;
	private String datatype;
	private String dataproduct;
	private String dataid;
	private Clob olddata;
	private Clob newdata;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class TSsLogStatus{
		public final static String NORMAL = "normal";
		public final static String ROLLBACK = "rollback";
	}
	
	public class TSsLogType{
		public final static String ADD = "add";
		public final static String EDIT = "edit";
		public final static String DELETE = "delete";
		public final static String CHECK = "check";
		public final static String NOPASS = "nopass";
		public final static String ROLLBACK = "rollback";
		public final static String SYNC = "sync";
		public final static String MIDSYNC = "midsync";
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getDataproduct() {
		return dataproduct;
	}

	public void setDataproduct(String dataproduct) {
		this.dataproduct = dataproduct;
	}

	public Clob getOlddata() {
		return olddata;
	}

	public void setOlddata(Clob olddata) {
		this.olddata = olddata;
	}

	public Clob getNewdata() {
		return newdata;
	}

	public void setNewdata(Clob newdata) {
		this.newdata = newdata;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
