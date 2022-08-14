package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KH_TZGWHTB database table.
 * 
 */
@Entity
@Table(name="T_KH_TZGWHTB")
public class TKhTzgwhtb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String dsfjglx;

	private String frdbmc;

	private BigDecimal gtje;

	private BigDecimal gwfl;

	private String hzhtmc;

	private String jrxkzh;

	private String nbpj;

	private String qdrq;

	private String qzlsqk;

	private String sfgt;

	private String sjhtbh;

	private String tzgwjgbh;

	private String tzgwmc;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbh;

	private String xtzxmbm;

	private BigDecimal ydbcx;

	private BigDecimal ydcstzjm;

	private BigDecimal ydpcx;

	private String ydtzblxz;

	private BigDecimal ydyjx;

	private BigDecimal zczb;

	private String zjhm;

	private String zjlx;

	public TKhTzgwhtb() {
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

	public String getDsfjglx() {
		return this.dsfjglx;
	}

	public void setDsfjglx(String dsfjglx) {
		this.dsfjglx = dsfjglx;
	}

	public String getFrdbmc() {
		return this.frdbmc;
	}

	public void setFrdbmc(String frdbmc) {
		this.frdbmc = frdbmc;
	}

	public BigDecimal getGtje() {
		return this.gtje;
	}

	public void setGtje(BigDecimal gtje) {
		this.gtje = gtje;
	}

	public BigDecimal getGwfl() {
		return this.gwfl;
	}

	public void setGwfl(BigDecimal gwfl) {
		this.gwfl = gwfl;
	}

	public String getHzhtmc() {
		return this.hzhtmc;
	}

	public void setHzhtmc(String hzhtmc) {
		this.hzhtmc = hzhtmc;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getNbpj() {
		return this.nbpj;
	}

	public void setNbpj(String nbpj) {
		this.nbpj = nbpj;
	}

	public String getQdrq() {
		return this.qdrq;
	}

	public void setQdrq(String qdrq) {
		this.qdrq = qdrq;
	}

	public String getQzlsqk() {
		return this.qzlsqk;
	}

	public void setQzlsqk(String qzlsqk) {
		this.qzlsqk = qzlsqk;
	}

	public String getSfgt() {
		return this.sfgt;
	}

	public void setSfgt(String sfgt) {
		this.sfgt = sfgt;
	}

	public String getSjhtbh() {
		return this.sjhtbh;
	}

	public void setSjhtbh(String sjhtbh) {
		this.sjhtbh = sjhtbh;
	}

	public String getTzgwjgbh() {
		return this.tzgwjgbh;
	}

	public void setTzgwjgbh(String tzgwjgbh) {
		this.tzgwjgbh = tzgwjgbh;
	}

	public String getTzgwmc() {
		return this.tzgwmc;
	}

	public void setTzgwmc(String tzgwmc) {
		this.tzgwmc = tzgwmc;
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

	public String getXtzxmbm() {
		return this.xtzxmbm;
	}

	public void setXtzxmbm(String xtzxmbm) {
		this.xtzxmbm = xtzxmbm;
	}

	public BigDecimal getYdbcx() {
		return this.ydbcx;
	}

	public void setYdbcx(BigDecimal ydbcx) {
		this.ydbcx = ydbcx;
	}

	public BigDecimal getYdcstzjm() {
		return this.ydcstzjm;
	}

	public void setYdcstzjm(BigDecimal ydcstzjm) {
		this.ydcstzjm = ydcstzjm;
	}

	public BigDecimal getYdpcx() {
		return this.ydpcx;
	}

	public void setYdpcx(BigDecimal ydpcx) {
		this.ydpcx = ydpcx;
	}

	public String getYdtzblxz() {
		return this.ydtzblxz;
	}

	public void setYdtzblxz(String ydtzblxz) {
		this.ydtzblxz = ydtzblxz;
	}

	public BigDecimal getYdyjx() {
		return this.ydyjx;
	}

	public void setYdyjx(BigDecimal ydyjx) {
		this.ydyjx = ydyjx;
	}

	public BigDecimal getZczb() {
		return this.zczb;
	}

	public void setZczb(BigDecimal zczb) {
		this.zczb = zczb;
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

}