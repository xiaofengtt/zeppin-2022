package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KJ_GYNBKMDZB database table.
 * 
 */
@Entity
@Table(name="T_KJ_GYNBKMDZB")
public class TKjGynbkmdzb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private BigDecimal gsywdl;

	private String gsywzl;

	private String gykjkmbh;

	private BigDecimal gykjkmjc;

	private String gykjkmmc;

	private String jrxkzh;

	private String sjkmbh;

	private String sjkmmc;

	public TKjGynbkmdzb() {
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

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public BigDecimal getGsywdl() {
		return this.gsywdl;
	}

	public void setGsywdl(BigDecimal gsywdl) {
		this.gsywdl = gsywdl;
	}

	public String getGsywzl() {
		return this.gsywzl;
	}

	public void setGsywzl(String gsywzl) {
		this.gsywzl = gsywzl;
	}

	public String getGykjkmbh() {
		return this.gykjkmbh;
	}

	public void setGykjkmbh(String gykjkmbh) {
		this.gykjkmbh = gykjkmbh;
	}

	public BigDecimal getGykjkmjc() {
		return this.gykjkmjc;
	}

	public void setGykjkmjc(BigDecimal gykjkmjc) {
		this.gykjkmjc = gykjkmjc;
	}

	public String getGykjkmmc() {
		return this.gykjkmmc;
	}

	public void setGykjkmmc(String gykjkmmc) {
		this.gykjkmmc = gykjkmmc;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getSjkmbh() {
		return this.sjkmbh;
	}

	public void setSjkmbh(String sjkmbh) {
		this.sjkmbh = sjkmbh;
	}

	public String getSjkmmc() {
		return this.sjkmmc;
	}

	public void setSjkmmc(String sjkmmc) {
		this.sjkmmc = sjkmmc;
	}

}