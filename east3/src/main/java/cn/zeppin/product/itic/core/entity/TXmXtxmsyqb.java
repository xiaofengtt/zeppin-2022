package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_XTXMSYQB database table.
 * 
 */
@Entity
@Table(name="T_XM_XTXMSYQB")
public class TXmXtxmsyqb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String fhfs;

	private String jrxkzh;

	private String syfpfs;

	private String syfppd;

	private String syljtfs;

	private String syqbh;

	private String syqkfpd;

	private String syqlbdqr;

	private String syqlbqsr;

	private String syqlx;

	private BigDecimal syqyqsyl;

	private String syqyxfs;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbm;

	private String xtzxmbm;

	private String yqsylsm;

	public TXmXtxmsyqb() {
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

	public String getFhfs() {
		return this.fhfs;
	}

	public void setFhfs(String fhfs) {
		this.fhfs = fhfs;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getSyfpfs() {
		return this.syfpfs;
	}

	public void setSyfpfs(String syfpfs) {
		this.syfpfs = syfpfs;
	}

	public String getSyfppd() {
		return this.syfppd;
	}

	public void setSyfppd(String syfppd) {
		this.syfppd = syfppd;
	}

	public String getSyljtfs() {
		return this.syljtfs;
	}

	public void setSyljtfs(String syljtfs) {
		this.syljtfs = syljtfs;
	}

	public String getSyqbh() {
		return this.syqbh;
	}

	public void setSyqbh(String syqbh) {
		this.syqbh = syqbh;
	}

	public String getSyqkfpd() {
		return this.syqkfpd;
	}

	public void setSyqkfpd(String syqkfpd) {
		this.syqkfpd = syqkfpd;
	}

	public String getSyqlbdqr() {
		return this.syqlbdqr;
	}

	public void setSyqlbdqr(String syqlbdqr) {
		this.syqlbdqr = syqlbdqr;
	}

	public String getSyqlbqsr() {
		return this.syqlbqsr;
	}

	public void setSyqlbqsr(String syqlbqsr) {
		this.syqlbqsr = syqlbqsr;
	}

	public String getSyqlx() {
		return this.syqlx;
	}

	public void setSyqlx(String syqlx) {
		this.syqlx = syqlx;
	}

	public BigDecimal getSyqyqsyl() {
		return this.syqyqsyl;
	}

	public void setSyqyqsyl(BigDecimal syqyqsyl) {
		this.syqyqsyl = syqyqsyl;
	}

	public String getSyqyxfs() {
		return this.syqyxfs;
	}

	public void setSyqyxfs(String syqyxfs) {
		this.syqyxfs = syqyxfs;
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

	public String getXtxmbm() {
		return this.xtxmbm;
	}

	public void setXtxmbm(String xtxmbm) {
		this.xtxmbm = xtxmbm;
	}

	public String getXtzxmbm() {
		return this.xtzxmbm;
	}

	public void setXtzxmbm(String xtzxmbm) {
		this.xtzxmbm = xtzxmbm;
	}

	public String getYqsylsm() {
		return this.yqsylsm;
	}

	public void setYqsylsm(String yqsylsm) {
		this.yqsylsm = yqsylsm;
	}

}