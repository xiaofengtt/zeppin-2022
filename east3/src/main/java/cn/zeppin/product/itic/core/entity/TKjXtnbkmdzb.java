package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KJ_XTNBKMDZB database table.
 * 
 */
@Entity
@Table(name="T_KJ_XTNBKMDZB")
public class TKjXtnbkmdzb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private BigDecimal gsywdl;

	private String gsywzl;

	private String jrxkzh;

	private String sjkmbh;

	private String sjkmmc;

	private String xtjgdm;

	private String xtjgmc;

	private String xtkjkmbh;

	private BigDecimal xtkjkmjc;

	private String xtkjkmmc;

	private String xtxmbh;

	private String xtzxmbh;

	public TKjXtnbkmdzb() {
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

	public String getXtkjkmbh() {
		return this.xtkjkmbh;
	}

	public void setXtkjkmbh(String xtkjkmbh) {
		this.xtkjkmbh = xtkjkmbh;
	}

	public BigDecimal getXtkjkmjc() {
		return this.xtkjkmjc;
	}

	public void setXtkjkmjc(BigDecimal xtkjkmjc) {
		this.xtkjkmjc = xtkjkmjc;
	}

	public String getXtkjkmmc() {
		return this.xtkjkmmc;
	}

	public void setXtkjkmmc(String xtkjkmmc) {
		this.xtkjkmmc = xtkjkmmc;
	}

	public String getXtxmbh() {
		return this.xtxmbh;
	}

	public void setXtxmbh(String xtxmbh) {
		this.xtxmbh = xtxmbh;
	}

	public String getXtzxmbh() {
		return this.xtzxmbh;
	}

	public void setXtzxmbh(String xtzxmbh) {
		this.xtzxmbh = xtzxmbh;
	}

}