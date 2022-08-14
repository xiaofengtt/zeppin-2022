package cn.zeppin.product.itic.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KH_XTKHJG database table.
 * 
 */
@Entity
@Table(name="T_KH_XTKHJG")
public class TKhXtkhjg extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String cz;

	private String dh;

	private String gllx;

	private String jrxkzh;

	private String khbh;

	private String khlx;

	private String khqc;

	private String sfjtkh;

	private String ssdq;

	private String ssgb;

	private String ssjtkhbh;

	private String tsdz;

	private String tyshxydm;

	private String xtjgdm;

	private String xtjgmc;

	private String yzbm;

	private String zjhm;

	private String zjlx;

	public TKhXtkhjg() {
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

	public String getCz() {
		return this.cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getDh() {
		return this.dh;
	}

	public void setDh(String dh) {
		this.dh = dh;
	}

	public String getGllx() {
		return this.gllx;
	}

	public void setGllx(String gllx) {
		this.gllx = gllx;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getKhbh() {
		return this.khbh;
	}

	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}

	public String getKhlx() {
		return this.khlx;
	}

	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}

	public String getKhqc() {
		return this.khqc;
	}

	public void setKhqc(String khqc) {
		this.khqc = khqc;
	}

	public String getSfjtkh() {
		return this.sfjtkh;
	}

	public void setSfjtkh(String sfjtkh) {
		this.sfjtkh = sfjtkh;
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

	public String getSsjtkhbh() {
		return this.ssjtkhbh;
	}

	public void setSsjtkhbh(String ssjtkhbh) {
		this.ssjtkhbh = ssjtkhbh;
	}

	public String getTsdz() {
		return this.tsdz;
	}

	public void setTsdz(String tsdz) {
		this.tsdz = tsdz;
	}

	public String getTyshxydm() {
		return this.tyshxydm;
	}

	public void setTyshxydm(String tyshxydm) {
		this.tyshxydm = tyshxydm;
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

}