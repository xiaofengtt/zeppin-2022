package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_XTZJMJHTXX database table.
 * 
 */
@Entity
@Table(name="T_XM_XTZJMJHTXX")
public class TXmXtzjmjhtxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String clcpbh;

	private String htqdd;

	private String jfzclx;

	private String jrxkzh;

	private String nbhtbh;

	private String qdrq;

	private String sczjdzr;

	private String sfjrjglccp;

	private String syqbh;

	private String wbhtbh;

	private String wthtmc;

	private BigDecimal wtje;

	private String wtrbh;

	private String wtrlx;

	private String wtrqc;

	private String wtzjxz;

	private String xsfs;

	private String xssmc;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbh;

	private String xtxmqc;

	private String xtzxmbm;

	public TXmXtzjmjhtxx() {
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

	public String getClcpbh() {
		return this.clcpbh;
	}

	public void setClcpbh(String clcpbh) {
		this.clcpbh = clcpbh;
	}

	public String getHtqdd() {
		return this.htqdd;
	}

	public void setHtqdd(String htqdd) {
		this.htqdd = htqdd;
	}

	public String getJfzclx() {
		return this.jfzclx;
	}

	public void setJfzclx(String jfzclx) {
		this.jfzclx = jfzclx;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getNbhtbh() {
		return this.nbhtbh;
	}

	public void setNbhtbh(String nbhtbh) {
		this.nbhtbh = nbhtbh;
	}

	public String getQdrq() {
		return this.qdrq;
	}

	public void setQdrq(String qdrq) {
		this.qdrq = qdrq;
	}

	public String getSczjdzr() {
		return this.sczjdzr;
	}

	public void setSczjdzr(String sczjdzr) {
		this.sczjdzr = sczjdzr;
	}

	public String getSfjrjglccp() {
		return this.sfjrjglccp;
	}

	public void setSfjrjglccp(String sfjrjglccp) {
		this.sfjrjglccp = sfjrjglccp;
	}

	public String getSyqbh() {
		return this.syqbh;
	}

	public void setSyqbh(String syqbh) {
		this.syqbh = syqbh;
	}

	public String getWbhtbh() {
		return this.wbhtbh;
	}

	public void setWbhtbh(String wbhtbh) {
		this.wbhtbh = wbhtbh;
	}

	public String getWthtmc() {
		return this.wthtmc;
	}

	public void setWthtmc(String wthtmc) {
		this.wthtmc = wthtmc;
	}

	public BigDecimal getWtje() {
		return this.wtje;
	}

	public void setWtje(BigDecimal wtje) {
		this.wtje = wtje;
	}

	public String getWtrbh() {
		return this.wtrbh;
	}

	public void setWtrbh(String wtrbh) {
		this.wtrbh = wtrbh;
	}

	public String getWtrlx() {
		return this.wtrlx;
	}

	public void setWtrlx(String wtrlx) {
		this.wtrlx = wtrlx;
	}

	public String getWtrqc() {
		return this.wtrqc;
	}

	public void setWtrqc(String wtrqc) {
		this.wtrqc = wtrqc;
	}

	public String getWtzjxz() {
		return this.wtzjxz;
	}

	public void setWtzjxz(String wtzjxz) {
		this.wtzjxz = wtzjxz;
	}

	public String getXsfs() {
		return this.xsfs;
	}

	public void setXsfs(String xsfs) {
		this.xsfs = xsfs;
	}

	public String getXssmc() {
		return this.xssmc;
	}

	public void setXssmc(String xssmc) {
		this.xssmc = xssmc;
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

	public String getXtxmqc() {
		return this.xtxmqc;
	}

	public void setXtxmqc(String xtxmqc) {
		this.xtxmqc = xtxmqc;
	}

	public String getXtzxmbm() {
		return this.xtzxmbm;
	}

	public void setXtzxmbm(String xtzxmbm) {
		this.xtzxmbm = xtzxmbm;
	}

}