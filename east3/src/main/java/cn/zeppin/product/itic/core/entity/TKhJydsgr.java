package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KH_JYDSGR database table.
 * 
 */
@Entity
@Table(name="T_KH_JYDSGR")
public class TKhJydsgr extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String czdh;

	private String dsbh;

	private BigDecimal grnsr;

	private String gxlx;

	private String gzdwdz;

	private String jrxkzh;

	private BigDecimal jtnsr;

	private String jzdz;

	private String lxdh;

	private String ssdq;

	private String ssgb;

	private String syrqc;

	private String xtjgdm;

	private String xtjgmc;

	private String yzbm;

	private String zjhm;

	private String zjlx;

	private String zy;

	public TKhJydsgr() {
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

	public String getCzdh() {
		return this.czdh;
	}

	public void setCzdh(String czdh) {
		this.czdh = czdh;
	}

	public String getDsbh() {
		return this.dsbh;
	}

	public void setDsbh(String dsbh) {
		this.dsbh = dsbh;
	}

	public BigDecimal getGrnsr() {
		return this.grnsr;
	}

	public void setGrnsr(BigDecimal grnsr) {
		this.grnsr = grnsr;
	}

	public String getGxlx() {
		return this.gxlx;
	}

	public void setGxlx(String gxlx) {
		this.gxlx = gxlx;
	}

	public String getGzdwdz() {
		return this.gzdwdz;
	}

	public void setGzdwdz(String gzdwdz) {
		this.gzdwdz = gzdwdz;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public BigDecimal getJtnsr() {
		return this.jtnsr;
	}

	public void setJtnsr(BigDecimal jtnsr) {
		this.jtnsr = jtnsr;
	}

	public String getJzdz() {
		return this.jzdz;
	}

	public void setJzdz(String jzdz) {
		this.jzdz = jzdz;
	}

	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getSsdq() {
		return this.ssdq;
	}

	public void setSsdq(String ssdq) {
		this.ssdq = ssdq;
	}

	public String getSsgb() {
		return this.ssgb;
	}

	public void setSsgb(String ssgb) {
		this.ssgb = ssgb;
	}

	public String getSyrqc() {
		return this.syrqc;
	}

	public void setSyrqc(String syrqc) {
		this.syrqc = syrqc;
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

	public String getYzbm() {
		return this.yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	public String getZjhm() {
		return this.zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getZjlx() {
		return this.zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}

	public String getZy() {
		return this.zy;
	}

	public void setZy(String zy) {
		this.zy = zy;
	}

}