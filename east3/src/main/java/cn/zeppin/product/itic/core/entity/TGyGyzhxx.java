package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_GY_GYZHXX database table.
 * 
 */
@Entity
@Table(name="T_GY_GYZHXX")
public class TGyGyzhxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bz;

	private String cjrq;

	private String gyzhlc;

	private String jrxkzh;

	private String khjgmc;

	private String khjgzjhm;

	private String khqy;

	private String sftg;

	private String xtjgdm;

	private String xtjgmc;

	private BigDecimal ye;

	private String zhbh;

	private String zhdzpd;

	private String zhswglfs;

	private String zhyjbgfs;

	private String zhzt;

	public TGyGyzhxx() {
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

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public String getGyzhlc() {
		return this.gyzhlc;
	}

	public void setGyzhlc(String gyzhlc) {
		this.gyzhlc = gyzhlc;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getKhjgmc() {
		return this.khjgmc;
	}

	public void setKhjgmc(String khjgmc) {
		this.khjgmc = khjgmc;
	}

	public String getKhjgzjhm() {
		return this.khjgzjhm;
	}

	public void setKhjgzjhm(String khjgzjhm) {
		this.khjgzjhm = khjgzjhm;
	}

	public String getKhqy() {
		return this.khqy;
	}

	public void setKhqy(String khqy) {
		this.khqy = khqy;
	}

	public String getSftg() {
		return this.sftg;
	}

	public void setSftg(String sftg) {
		this.sftg = sftg;
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

	public BigDecimal getYe() {
		return this.ye;
	}

	public void setYe(BigDecimal ye) {
		this.ye = ye;
	}

	public String getZhbh() {
		return this.zhbh;
	}

	public void setZhbh(String zhbh) {
		this.zhbh = zhbh;
	}

	public String getZhdzpd() {
		return this.zhdzpd;
	}

	public void setZhdzpd(String zhdzpd) {
		this.zhdzpd = zhdzpd;
	}

	public String getZhswglfs() {
		return this.zhswglfs;
	}

	public void setZhswglfs(String zhswglfs) {
		this.zhswglfs = zhswglfs;
	}

	public String getZhyjbgfs() {
		return this.zhyjbgfs;
	}

	public void setZhyjbgfs(String zhyjbgfs) {
		this.zhyjbgfs = zhyjbgfs;
	}

	public String getZhzt() {
		return this.zhzt;
	}

	public void setZhzt(String zhzt) {
		this.zhzt = zhzt;
	}

}