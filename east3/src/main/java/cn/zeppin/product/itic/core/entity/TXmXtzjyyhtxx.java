package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_XTZJYYHTXX database table.
 * 
 */
@Entity
@Table(name="T_XM_XTZJYYHTXX")
public class TXmXtzjyyhtxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bdzclx;

	private String bdzcmc;

	private BigDecimal bdzcrzjz;

	private String cjrq;

	private String dsfbh;

	private String dsflx;

	private String hkfs;

	private String htdqrq;

	private BigDecimal htqdje;

	private String htqdrq;

	private BigDecimal htqyll;

	private String jrxkzh;

	private BigDecimal jydsnhzjcb;

	private String scfkrq;

	private String tcfs;

	private String tqzz;

	private BigDecimal trzye;

	private String txhy;

	private String txhymx;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbh;

	private String xtzxmbm;

	private String ysdsfbh;

	private String ysdsflx;

	private String yshth;

	private String ywzl;

	private String yyhtbh;

	private String yyhtmc;

	private String zcwjfl;

	private String zjtxdq;

	private String zjyyfs;

	public TXmXtzjyyhtxx() {
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

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public BigDecimal getJydsnhzjcb() {
		return this.jydsnhzjcb;
	}

	public void setJydsnhzjcb(BigDecimal jydsnhzjcb) {
		this.jydsnhzjcb = jydsnhzjcb;
	}

	public String getScfkrq() {
		return this.scfkrq;
	}

	public void setScfkrq(String scfkrq) {
		this.scfkrq = scfkrq;
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

	public BigDecimal getTrzye() {
		return this.trzye;
	}

	public void setTrzye(BigDecimal trzye) {
		this.trzye = trzye;
	}

	public String getTxhy() {
		return this.txhy;
	}

	public void setTxhy(String txhy) {
		this.txhy = txhy;
	}

	public String getTxhymx() {
		return this.txhymx;
	}

	public void setTxhymx(String txhymx) {
		this.txhymx = txhymx;
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

	public String getYsdsfbh() {
		return this.ysdsfbh;
	}

	public void setYsdsfbh(String ysdsfbh) {
		this.ysdsfbh = ysdsfbh;
	}

	public String getYsdsflx() {
		return this.ysdsflx;
	}

	public void setYsdsflx(String ysdsflx) {
		this.ysdsflx = ysdsflx;
	}

	public String getYshth() {
		return this.yshth;
	}

	public void setYshth(String yshth) {
		this.yshth = yshth;
	}

	public String getYwzl() {
		return this.ywzl;
	}

	public void setYwzl(String ywzl) {
		this.ywzl = ywzl;
	}

	public String getYyhtbh() {
		return this.yyhtbh;
	}

	public void setYyhtbh(String yyhtbh) {
		this.yyhtbh = yyhtbh;
	}

	public String getYyhtmc() {
		return this.yyhtmc;
	}

	public void setYyhtmc(String yyhtmc) {
		this.yyhtmc = yyhtmc;
	}

	public String getZcwjfl() {
		return this.zcwjfl;
	}

	public void setZcwjfl(String zcwjfl) {
		this.zcwjfl = zcwjfl;
	}

	public String getZjtxdq() {
		return this.zjtxdq;
	}

	public void setZjtxdq(String zjtxdq) {
		this.zjtxdq = zjtxdq;
	}

	public String getZjyyfs() {
		return this.zjyyfs;
	}

	public void setZjyyfs(String zjyyfs) {
		this.zjyyfs = zjyyfs;
	}

}