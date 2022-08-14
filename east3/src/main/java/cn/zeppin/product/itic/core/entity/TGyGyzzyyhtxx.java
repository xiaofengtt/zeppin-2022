package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_GY_GYZZYYHTXX database table.
 * 
 */
@Entity
@Table(name="T_GY_GYZZYYHTXX")
public class TGyGyzzyyhtxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bdzclx;

	private String bdzcmc;

	private BigDecimal bdzcrzjz;

	private String bmmc;

	private String btzcpdm;

	private String cjrq;

	private String dsfbh;

	private String dsflx;

	private String gyywlb;

	private String hkfs;

	private String htdqrq;

	private BigDecimal htqdje;

	private String htqdrq;

	private BigDecimal htqyll;

	private String hymx;

	private String jbrbh;

	private String jrxkzh;

	private String ptzcpmc;

	private String rzhtdqrq;

	private String scfkrq;

	private String sfbgscp;

	private String tcfs;

	private String tqzz;

	private String trzhtbh;

	private String trzhtmc;

	private String txdq;

	private String txhy;

	private String wbhtbh;

	private String xtjgdm;

	private String xtjgmc;

	private BigDecimal yqsyl;

	private String ysdsbh;

	private String ysdslx;

	private String ysfqjglx;

	private String yshtbh;

	private String zcwjfl;

	private BigDecimal zhrzcb;

	private String zjyyfs;

	public TGyGyzzyyhtxx() {
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

	public String getBdzclx() {
		return this.bdzclx;
	}

	public void setBdzclx(String bdzclx) {
		this.bdzclx = bdzclx;
	}

	public String getBdzcmc() {
		return this.bdzcmc;
	}

	public void setBdzcmc(String bdzcmc) {
		this.bdzcmc = bdzcmc;
	}

	public BigDecimal getBdzcrzjz() {
		return this.bdzcrzjz;
	}

	public void setBdzcrzjz(BigDecimal bdzcrzjz) {
		this.bdzcrzjz = bdzcrzjz;
	}

	public String getBmmc() {
		return this.bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getBtzcpdm() {
		return this.btzcpdm;
	}

	public void setBtzcpdm(String btzcpdm) {
		this.btzcpdm = btzcpdm;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public String getDsfbh() {
		return this.dsfbh;
	}

	public void setDsfbh(String dsfbh) {
		this.dsfbh = dsfbh;
	}

	public String getDsflx() {
		return this.dsflx;
	}

	public void setDsflx(String dsflx) {
		this.dsflx = dsflx;
	}

	public String getGyywlb() {
		return this.gyywlb;
	}

	public void setGyywlb(String gyywlb) {
		this.gyywlb = gyywlb;
	}

	public String getHkfs() {
		return this.hkfs;
	}

	public void setHkfs(String hkfs) {
		this.hkfs = hkfs;
	}

	public String getHtdqrq() {
		return this.htdqrq;
	}

	public void setHtdqrq(String htdqrq) {
		this.htdqrq = htdqrq;
	}

	public BigDecimal getHtqdje() {
		return this.htqdje;
	}

	public void setHtqdje(BigDecimal htqdje) {
		this.htqdje = htqdje;
	}

	public String getHtqdrq() {
		return this.htqdrq;
	}

	public void setHtqdrq(String htqdrq) {
		this.htqdrq = htqdrq;
	}

	public BigDecimal getHtqyll() {
		return this.htqyll;
	}

	public void setHtqyll(BigDecimal htqyll) {
		this.htqyll = htqyll;
	}

	public String getHymx() {
		return this.hymx;
	}

	public void setHymx(String hymx) {
		this.hymx = hymx;
	}

	public String getJbrbh() {
		return this.jbrbh;
	}

	public void setJbrbh(String jbrbh) {
		this.jbrbh = jbrbh;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getPtzcpmc() {
		return this.ptzcpmc;
	}

	public void setPtzcpmc(String ptzcpmc) {
		this.ptzcpmc = ptzcpmc;
	}

	public String getRzhtdqrq() {
		return this.rzhtdqrq;
	}

	public void setRzhtdqrq(String rzhtdqrq) {
		this.rzhtdqrq = rzhtdqrq;
	}

	public String getScfkrq() {
		return this.scfkrq;
	}

	public void setScfkrq(String scfkrq) {
		this.scfkrq = scfkrq;
	}

	public String getSfbgscp() {
		return this.sfbgscp;
	}

	public void setSfbgscp(String sfbgscp) {
		this.sfbgscp = sfbgscp;
	}

	public String getTcfs() {
		return this.tcfs;
	}

	public void setTcfs(String tcfs) {
		this.tcfs = tcfs;
	}

	public String getTqzz() {
		return this.tqzz;
	}

	public void setTqzz(String tqzz) {
		this.tqzz = tqzz;
	}

	public String getTrzhtbh() {
		return this.trzhtbh;
	}

	public void setTrzhtbh(String trzhtbh) {
		this.trzhtbh = trzhtbh;
	}

	public String getTrzhtmc() {
		return this.trzhtmc;
	}

	public void setTrzhtmc(String trzhtmc) {
		this.trzhtmc = trzhtmc;
	}

	public String getTxdq() {
		return this.txdq;
	}

	public void setTxdq(String txdq) {
		this.txdq = txdq;
	}

	public String getTxhy() {
		return this.txhy;
	}

	public void setTxhy(String txhy) {
		this.txhy = txhy;
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

	public BigDecimal getYqsyl() {
		return this.yqsyl;
	}

	public void setYqsyl(BigDecimal yqsyl) {
		this.yqsyl = yqsyl;
	}

	public String getYsdsbh() {
		return this.ysdsbh;
	}

	public void setYsdsbh(String ysdsbh) {
		this.ysdsbh = ysdsbh;
	}

	public String getYsdslx() {
		return this.ysdslx;
	}

	public void setYsdslx(String ysdslx) {
		this.ysdslx = ysdslx;
	}

	public String getYsfqjglx() {
		return this.ysfqjglx;
	}

	public void setYsfqjglx(String ysfqjglx) {
		this.ysfqjglx = ysfqjglx;
	}

	public String getYshtbh() {
		return this.yshtbh;
	}

	public void setYshtbh(String yshtbh) {
		this.yshtbh = yshtbh;
	}

	public String getZcwjfl() {
		return this.zcwjfl;
	}

	public void setZcwjfl(String zcwjfl) {
		this.zcwjfl = zcwjfl;
	}

	public BigDecimal getZhrzcb() {
		return this.zhrzcb;
	}

	public void setZhrzcb(BigDecimal zhrzcb) {
		this.zhrzcb = zhrzcb;
	}

	public String getZjyyfs() {
		return this.zjyyfs;
	}

	public void setZjyyfs(String zjyyfs) {
		this.zjyyfs = zjyyfs;
	}

}