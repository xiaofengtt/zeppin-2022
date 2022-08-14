package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_JY_XTSYPZ database table.
 * 
 */
@Entity
@Table(name="T_JY_XTSYPZ")
public class TJyXtsypz extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String jrxkzh;

	private BigDecimal sjcyxtfe;

	private String sypjbh;

	private String syqlbbm;

	private String syrbh;

	private String syrlx;

	private String syrztxz;

	private String xthtbh;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbh;

	private String xtzxmbh;

	public TJyXtsypz() {
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

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public BigDecimal getSjcyxtfe() {
		return this.sjcyxtfe;
	}

	public void setSjcyxtfe(BigDecimal sjcyxtfe) {
		this.sjcyxtfe = sjcyxtfe;
	}

	public String getSypjbh() {
		return this.sypjbh;
	}

	public void setSypjbh(String sypjbh) {
		this.sypjbh = sypjbh;
	}

	public String getSyqlbbm() {
		return this.syqlbbm;
	}

	public void setSyqlbbm(String syqlbbm) {
		this.syqlbbm = syqlbbm;
	}

	public String getSyrbh() {
		return this.syrbh;
	}

	public void setSyrbh(String syrbh) {
		this.syrbh = syrbh;
	}

	public String getSyrlx() {
		return this.syrlx;
	}

	public void setSyrlx(String syrlx) {
		this.syrlx = syrlx;
	}

	public String getSyrztxz() {
		return this.syrztxz;
	}

	public void setSyrztxz(String syrztxz) {
		this.syrztxz = syrztxz;
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

}