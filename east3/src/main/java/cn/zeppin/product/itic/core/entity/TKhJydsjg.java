package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KH_JYDSJG database table.
 * 
 */
@Entity
@Table(name="T_KH_JYDSJG")
public class TKhJydsjg extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String czdh;

	private String djdqrq;

	private String dsbh;

	private String fddbr;

	private String fddbrzjhm;

	private String fddbrzjlx;

	private String gllx;

	private String hyfl;

	private String hymx;

	private String jjcf;

	private String jrxkzh;

	private String jtmc;

	private String jydsqc;

	private String ptbz;

	private String ptzfjb;

	private String ptzfmc;

	private String qyclrq;

	private String qygm;

	private String qyxz;

	private BigDecimal qyzfz;

	private BigDecimal qyzzc;

	private String sfjt;

	private String shxydm;

	private String snbz;

	private String ssbz;

	private String ssd;

	private String ssdq;

	private String ssgb;

	private String txdz;

	private String xhlb;

	private String xtjgdm;

	private String xtjgmc;

	private String yzbm;

	private String zcdz;

	private String zclb;

	private BigDecimal zczb;

	private String zjhm;

	private String zjlx;

	public TKhJydsjg() {
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

	public String getCzdh() {
		return this.czdh;
	}

	public void setCzdh(String czdh) {
		this.czdh = czdh;
	}

	public String getDjdqrq() {
		return this.djdqrq;
	}

	public void setDjdqrq(String djdqrq) {
		this.djdqrq = djdqrq;
	}

	public String getDsbh() {
		return this.dsbh;
	}

	public void setDsbh(String dsbh) {
		this.dsbh = dsbh;
	}

	public String getFddbr() {
		return this.fddbr;
	}

	public void setFddbr(String fddbr) {
		this.fddbr = fddbr;
	}

	public String getFddbrzjhm() {
		return this.fddbrzjhm;
	}

	public void setFddbrzjhm(String fddbrzjhm) {
		this.fddbrzjhm = fddbrzjhm;
	}

	public String getFddbrzjlx() {
		return this.fddbrzjlx;
	}

	public void setFddbrzjlx(String fddbrzjlx) {
		this.fddbrzjlx = fddbrzjlx;
	}

	public String getGllx() {
		return this.gllx;
	}

	public void setGllx(String gllx) {
		this.gllx = gllx;
	}

	public String getHyfl() {
		return this.hyfl;
	}

	public void setHyfl(String hyfl) {
		this.hyfl = hyfl;
	}

	public String getHymx() {
		return this.hymx;
	}

	public void setHymx(String hymx) {
		this.hymx = hymx;
	}

	public String getJjcf() {
		return this.jjcf;
	}

	public void setJjcf(String jjcf) {
		this.jjcf = jjcf;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getJtmc() {
		return this.jtmc;
	}

	public void setJtmc(String jtmc) {
		this.jtmc = jtmc;
	}

	public String getJydsqc() {
		return this.jydsqc;
	}

	public void setJydsqc(String jydsqc) {
		this.jydsqc = jydsqc;
	}

	public String getPtbz() {
		return this.ptbz;
	}

	public void setPtbz(String ptbz) {
		this.ptbz = ptbz;
	}

	public String getPtzfjb() {
		return this.ptzfjb;
	}

	public void setPtzfjb(String ptzfjb) {
		this.ptzfjb = ptzfjb;
	}

	public String getPtzfmc() {
		return this.ptzfmc;
	}

	public void setPtzfmc(String ptzfmc) {
		this.ptzfmc = ptzfmc;
	}

	public String getQyclrq() {
		return this.qyclrq;
	}

	public void setQyclrq(String qyclrq) {
		this.qyclrq = qyclrq;
	}

	public String getQygm() {
		return this.qygm;
	}

	public void setQygm(String qygm) {
		this.qygm = qygm;
	}

	public String getQyxz() {
		return this.qyxz;
	}

	public void setQyxz(String qyxz) {
		this.qyxz = qyxz;
	}

	public BigDecimal getQyzfz() {
		return this.qyzfz;
	}

	public void setQyzfz(BigDecimal qyzfz) {
		this.qyzfz = qyzfz;
	}

	public BigDecimal getQyzzc() {
		return this.qyzzc;
	}

	public void setQyzzc(BigDecimal qyzzc) {
		this.qyzzc = qyzzc;
	}

	public String getSfjt() {
		return this.sfjt;
	}

	public void setSfjt(String sfjt) {
		this.sfjt = sfjt;
	}

	public String getShxydm() {
		return this.shxydm;
	}

	public void setShxydm(String shxydm) {
		this.shxydm = shxydm;
	}

	public String getSnbz() {
		return this.snbz;
	}

	public void setSnbz(String snbz) {
		this.snbz = snbz;
	}

	public String getSsbz() {
		return this.ssbz;
	}

	public void setSsbz(String ssbz) {
		this.ssbz = ssbz;
	}

	public String getSsd() {
		return this.ssd;
	}

	public void setSsd(String ssd) {
		this.ssd = ssd;
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

	public String getTxdz() {
		return this.txdz;
	}

	public void setTxdz(String txdz) {
		this.txdz = txdz;
	}

	public String getXhlb() {
		return this.xhlb;
	}

	public void setXhlb(String xhlb) {
		this.xhlb = xhlb;
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

	public String getZcdz() {
		return this.zcdz;
	}

	public void setZcdz(String zcdz) {
		this.zcdz = zcdz;
	}

	public String getZclb() {
		return this.zclb;
	}

	public void setZclb(String zclb) {
		this.zclb = zclb;
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