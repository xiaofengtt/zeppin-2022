package cn.zeppin.product.itic.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_GY_GYDBGXB database table.
 * 
 */
@Entity
@Table(name="T_GY_GYDBGXB")
public class TGyGydbgxb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bdbhtbh;

	private String cjrq;

	private String dbhtbh;

	private String jrxkzh;

	private String xtjgdm;

	private String xtjgmc;

	public TGyGydbgxb() {
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
	
	public Timestamp getUpdatetime() {
		return updatetime;
	}


	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}


	public Timestamp getCtltime() {
		return ctltime;
	}


	public void setCtltime(Timestamp ctltime) {
		this.ctltime = ctltime;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getBdbhtbh() {
		return this.bdbhtbh;
	}

	public void setBdbhtbh(String bdbhtbh) {
		this.bdbhtbh = bdbhtbh;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public String getDbhtbh() {
		return this.dbhtbh;
	}

	public void setDbhtbh(String dbhtbh) {
		this.dbhtbh = dbhtbh;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getXtjgdm() {
		return this.xtjgdm;
	}

	public void setXtjgdm(String xtjgdm) {
		this.xtjgdm = xtjgdm;
	}

	public String getXtjgmc() {
		return this.xtjgmc;
	}

	public void setXtjgmc(String xtjgmc) {
		this.xtjgmc = xtjgmc;
	}

}