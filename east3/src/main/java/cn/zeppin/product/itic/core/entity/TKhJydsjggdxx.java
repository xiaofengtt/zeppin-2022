package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KH_JYDSJGGDXX database table.
 * 
 */
@Entity
@Table(name="T_KH_JYDSJGGDXX")
public class TKhJydsjggdxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private BigDecimal cgbl;

	private String cjrq;

	private String dsbh;

	private String gdlx;

	private String gdmc;

	private String gdzjhm;

	private String jrxkzh;

	private String kgfs;

	private String shxydm;

	private String xtjgdm;

	private String xtjgmc;

	private String zjlx;

	public TKhJydsjggdxx() {
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

	public BigDecimal getCgbl() {
		return this.cgbl;
	}

	public void setCgbl(BigDecimal cgbl) {
		this.cgbl = cgbl;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public String getDsbh() {
		return this.dsbh;
	}

	public void setDsbh(String dsbh) {
		this.dsbh = dsbh;
	}

	public String getGdlx() {
		return this.gdlx;
	}

	public void setGdlx(String gdlx) {
		this.gdlx = gdlx;
	}

	public String getGdmc() {
		return this.gdmc;
	}

	public void setGdmc(String gdmc) {
		this.gdmc = gdmc;
	}

	public String getGdzjhm() {
		return this.gdzjhm;
	}

	public void setGdzjhm(String gdzjhm) {
		this.gdzjhm = gdzjhm;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getKgfs() {
		return this.kgfs;
	}

	public void setKgfs(String kgfs) {
		this.kgfs = kgfs;
	}

	public String getShxydm() {
		return this.shxydm;
	}

	public void setShxydm(String shxydm) {
		this.shxydm = shxydm;
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

	public String getZjlx() {
		return this.zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}

}