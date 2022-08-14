package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_JY_XTZJMJJFPLS database table.
 * 
 */
@Entity
@Table(name="T_JY_XTZJMJJFPLS")
public class TJyXtzjmjjfpl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bfkhhbh;

	private String bfkhhmc;

	private String bfzh;

	private String cbmbz;

	private String cjrq;

	private String dfkhhbh;

	private String dfkhhmc;

	private String dfzh;

	private BigDecimal dwjz;

	private String fhfs;

	private String hsbz;

	private String jrxkzh;

	private BigDecimal jyfe;

	private BigDecimal jyfy;

	private BigDecimal jyje;

	private String jylsbh;

	private String jysj;

	private String srrbh;

	private String srrlx;

	private BigDecimal syje;

	private String sypjbh;

	private String xthtbh;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbh;

	private String xtzxmbh;

	private String ywbz;

	private String zjfx;

	private String zrdjbh;

	public TJyXtzjmjjfpl() {
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

	public String getBfkhhbh() {
		return this.bfkhhbh;
	}

	public void setBfkhhbh(String bfkhhbh) {
		this.bfkhhbh = bfkhhbh;
	}

	public String getBfkhhmc() {
		return this.bfkhhmc;
	}

	public void setBfkhhmc(String bfkhhmc) {
		this.bfkhhmc = bfkhhmc;
	}

	public String getBfzh() {
		return this.bfzh;
	}

	public void setBfzh(String bfzh) {
		this.bfzh = bfzh;
	}

	public String getCbmbz() {
		return this.cbmbz;
	}

	public void setCbmbz(String cbmbz) {
		this.cbmbz = cbmbz;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public String getDfkhhbh() {
		return this.dfkhhbh;
	}

	public void setDfkhhbh(String dfkhhbh) {
		this.dfkhhbh = dfkhhbh;
	}

	public String getDfkhhmc() {
		return this.dfkhhmc;
	}

	public void setDfkhhmc(String dfkhhmc) {
		this.dfkhhmc = dfkhhmc;
	}

	public String getDfzh() {
		return this.dfzh;
	}

	public void setDfzh(String dfzh) {
		this.dfzh = dfzh;
	}

	public BigDecimal getDwjz() {
		return this.dwjz;
	}

	public void setDwjz(BigDecimal dwjz) {
		this.dwjz = dwjz;
	}

	public String getFhfs() {
		return this.fhfs;
	}

	public void setFhfs(String fhfs) {
		this.fhfs = fhfs;
	}

	public String getHsbz() {
		return this.hsbz;
	}

	public void setHsbz(String hsbz) {
		this.hsbz = hsbz;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public BigDecimal getJyfe() {
		return this.jyfe;
	}

	public void setJyfe(BigDecimal jyfe) {
		this.jyfe = jyfe;
	}

	public BigDecimal getJyfy() {
		return this.jyfy;
	}

	public void setJyfy(BigDecimal jyfy) {
		this.jyfy = jyfy;
	}

	public BigDecimal getJyje() {
		return this.jyje;
	}

	public void setJyje(BigDecimal jyje) {
		this.jyje = jyje;
	}

	public String getJylsbh() {
		return this.jylsbh;
	}

	public void setJylsbh(String jylsbh) {
		this.jylsbh = jylsbh;
	}

	public String getJysj() {
		return this.jysj;
	}

	public void setJysj(String jysj) {
		this.jysj = jysj;
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

	public BigDecimal getSyje() {
		return this.syje;
	}

	public void setSyje(BigDecimal syje) {
		this.syje = syje;
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

	public String getYwbz() {
		return this.ywbz;
	}

	public void setYwbz(String ywbz) {
		this.ywbz = ywbz;
	}

	public String getZjfx() {
		return this.zjfx;
	}

	public void setZjfx(String zjfx) {
		this.zjfx = zjfx;
	}

	public String getZrdjbh() {
		return this.zrdjbh;
	}

	public void setZrdjbh(String zrdjbh) {
		this.zrdjbh = zrdjbh;
	}

}