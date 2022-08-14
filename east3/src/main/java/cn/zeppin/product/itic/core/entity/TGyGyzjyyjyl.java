package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_GY_GYZJYYJYLS database table.
 * 
 */
@Entity
@Table(name="T_GY_GYZJYYJYLS")
public class TGyGyzjyyjyl extends BaseEntity {
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

	private BigDecimal fy;

	private String htbh;

	private BigDecimal jhbj;

	private BigDecimal jhsy;

	private String jjbh;

	private String jrxkzh;

	private String jybz;

	private String jydsbh;

	private String jydslx;

	private String jylsbh;

	private String jysj;

	private String xtjgdm;

	private String xtjgmc;

	private String ywbs;

	private String zjfx;

	public TGyGyzjyyjyl() {
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

	public BigDecimal getFy() {
		return this.fy;
	}

	public void setFy(BigDecimal fy) {
		this.fy = fy;
	}

	public String getHtbh() {
		return this.htbh;
	}

	public void setHtbh(String htbh) {
		this.htbh = htbh;
	}

	public BigDecimal getJhbj() {
		return this.jhbj;
	}

	public void setJhbj(BigDecimal jhbj) {
		this.jhbj = jhbj;
	}

	public BigDecimal getJhsy() {
		return this.jhsy;
	}

	public void setJhsy(BigDecimal jhsy) {
		this.jhsy = jhsy;
	}

	public String getJjbh() {
		return this.jjbh;
	}

	public void setJjbh(String jjbh) {
		this.jjbh = jjbh;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getJybz() {
		return this.jybz;
	}

	public void setJybz(String jybz) {
		this.jybz = jybz;
	}

	public String getJydsbh() {
		return this.jydsbh;
	}

	public void setJydsbh(String jydsbh) {
		this.jydsbh = jydsbh;
	}

	public String getJydslx() {
		return this.jydslx;
	}

	public void setJydslx(String jydslx) {
		this.jydslx = jydslx;
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

	public String getYwbs() {
		return this.ywbs;
	}

	public void setYwbs(String ywbs) {
		this.ywbs = ywbs;
	}

	public String getZjfx() {
		return this.zjfx;
	}

	public void setZjfx(String zjfx) {
		this.zjfx = zjfx;
	}

}