package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_XTDBHTB database table.
 * 
 */
@Entity
@Table(name="T_XM_XTDBHTB")
public class TXmXtdbhtb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bzlx;

	private String bzrlx;

	private String cjrq;

	private String dbbz;

	private String dbdqrq;

	private String dbhtbh;

	private String dbhtdqrq;

	private String dbhtlx;

	private String dbhtqdrq;

	private String dbhtsxrq;

	private String dbhtzt;

	private String dblx;

	private String dbqsrq;

	private String dbrbh;

	private String dbrlx;

	private String dbwpxh;

	private BigDecimal dbzje;

	private String jrxkzh;

	private String sfjkrglf;

	private String wbhtbh;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbm;

	private String xtzxmbm;

	public TXmXtdbhtb() {
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

	public String getBzlx() {
		return this.bzlx;
	}

	public void setBzlx(String bzlx) {
		this.bzlx = bzlx;
	}

	public String getBzrlx() {
		return this.bzrlx;
	}

	public void setBzrlx(String bzrlx) {
		this.bzrlx = bzrlx;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public String getDbbz() {
		return this.dbbz;
	}

	public void setDbbz(String dbbz) {
		this.dbbz = dbbz;
	}

	public String getDbdqrq() {
		return this.dbdqrq;
	}

	public void setDbdqrq(String dbdqrq) {
		this.dbdqrq = dbdqrq;
	}

	public String getDbhtbh() {
		return this.dbhtbh;
	}

	public void setDbhtbh(String dbhtbh) {
		this.dbhtbh = dbhtbh;
	}

	public String getDbhtdqrq() {
		return this.dbhtdqrq;
	}

	public void setDbhtdqrq(String dbhtdqrq) {
		this.dbhtdqrq = dbhtdqrq;
	}

	public String getDbhtlx() {
		return this.dbhtlx;
	}

	public void setDbhtlx(String dbhtlx) {
		this.dbhtlx = dbhtlx;
	}

	public String getDbhtqdrq() {
		return this.dbhtqdrq;
	}

	public void setDbhtqdrq(String dbhtqdrq) {
		this.dbhtqdrq = dbhtqdrq;
	}

	public String getDbhtsxrq() {
		return this.dbhtsxrq;
	}

	public void setDbhtsxrq(String dbhtsxrq) {
		this.dbhtsxrq = dbhtsxrq;
	}

	public String getDbhtzt() {
		return this.dbhtzt;
	}

	public void setDbhtzt(String dbhtzt) {
		this.dbhtzt = dbhtzt;
	}

	public String getDblx() {
		return this.dblx;
	}

	public void setDblx(String dblx) {
		this.dblx = dblx;
	}

	public String getDbqsrq() {
		return this.dbqsrq;
	}

	public void setDbqsrq(String dbqsrq) {
		this.dbqsrq = dbqsrq;
	}

	public String getDbrbh() {
		return this.dbrbh;
	}

	public void setDbrbh(String dbrbh) {
		this.dbrbh = dbrbh;
	}

	public String getDbrlx() {
		return this.dbrlx;
	}

	public void setDbrlx(String dbrlx) {
		this.dbrlx = dbrlx;
	}

	public String getDbwpxh() {
		return this.dbwpxh;
	}

	public void setDbwpxh(String dbwpxh) {
		this.dbwpxh = dbwpxh;
	}

	public BigDecimal getDbzje() {
		return this.dbzje;
	}

	public void setDbzje(BigDecimal dbzje) {
		this.dbzje = dbzje;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getSfjkrglf() {
		return this.sfjkrglf;
	}

	public void setSfjkrglf(String sfjkrglf) {
		this.sfjkrglf = sfjkrglf;
	}

	public String getWbhtbh() {
		return this.wbhtbh;
	}

	public void setWbhtbh(String wbhtbh) {
		this.wbhtbh = wbhtbh;
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

}