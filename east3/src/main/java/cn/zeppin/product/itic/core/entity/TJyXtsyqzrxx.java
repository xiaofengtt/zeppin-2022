package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_JY_XTSYQZRXX database table.
 * 
 */
@Entity
@Table(name="T_JY_XTSYQZRXX")
public class TJyXtsyqzrxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String crrbh;

	private String crrlx;

	private String jrxkzh;

	private String srrbh;

	private String srrlx;

	private String sypjbh;

	private String xthtbh;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbh;

	private String xtzxmbh;

	private String zrbh;

	private BigDecimal zrfe;

	private String zrrq;

	public TJyXtsyqzrxx() {
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

	public String getCrrbh() {
		return this.crrbh;
	}

	public void setCrrbh(String crrbh) {
		this.crrbh = crrbh;
	}

	public String getCrrlx() {
		return this.crrlx;
	}

	public void setCrrlx(String crrlx) {
		this.crrlx = crrlx;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getSrrbh() {
		return this.srrbh;
	}

	public void setSrrbh(String srrbh) {
		this.srrbh = srrbh;
	}

	public String getSrrlx() {
		return this.srrlx;
	}

	public void setSrrlx(String srrlx) {
		this.srrlx = srrlx;
	}

	public String getSypjbh() {
		return this.sypjbh;
	}

	public void setSypjbh(String sypjbh) {
		this.sypjbh = sypjbh;
	}

	public String getXthtbh() {
		return this.xthtbh;
	}

	public void setXthtbh(String xthtbh) {
		this.xthtbh = xthtbh;
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

	public String getZrbh() {
		return this.zrbh;
	}

	public void setZrbh(String zrbh) {
		this.zrbh = zrbh;
	}

	public BigDecimal getZrfe() {
		return this.zrfe;
	}

	public void setZrfe(BigDecimal zrfe) {
		this.zrfe = zrfe;
	}

	public String getZrrq() {
		return this.zrrq;
	}

	public void setZrrq(String zrrq) {
		this.zrrq = zrrq;
	}

}