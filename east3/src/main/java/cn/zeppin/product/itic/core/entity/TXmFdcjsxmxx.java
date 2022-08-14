package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_FDCJSXMXX database table.
 * 
 */
@Entity
@Table(name="T_XM_FDCJSXMXX")
public class TXmFdcjsxmxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String fdcxmlx;

	private String gytdsyz;

	private String gytdsyzh;

	private String jrxkzh;

	private String jsydghxkz;

	private String jsydghxkzh;

	private String jzgcghxkz;

	private String jzgcghxkzh;

	private String jzgcsgxkz;

	private String jzgcsgxkzh;

	private String kfszz;

	private String kfszzjgdm;

	private BigDecimal rjl;

	private String sfqdysxkz;

	private String trzhtbh;

	private String xmdlwz;

	private String xmjtmc;

	private String xmsfyjrsgjd;

	private String xmsfyksxs;

	private BigDecimal xmwgjd;

	private BigDecimal xmxshkze;

	private BigDecimal xmxsjd;

	private BigDecimal xmyjddkze;

	private String xshkzjjkap;

	private String xtgsdxmdpj;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbh;

	private String xtzxmbm;

	private String xxszdyb;

	private String ysxkzhm;

	private BigDecimal zbjbl;

	private BigDecimal zjzmj;

	private BigDecimal ztzje;

	private BigDecimal zzdmj;

	public TXmFdcjsxmxx() {
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

	public String getFdcxmlx() {
		return this.fdcxmlx;
	}

	public void setFdcxmlx(String fdcxmlx) {
		this.fdcxmlx = fdcxmlx;
	}

	public String getGytdsyz() {
		return this.gytdsyz;
	}

	public void setGytdsyz(String gytdsyz) {
		this.gytdsyz = gytdsyz;
	}

	public String getGytdsyzh() {
		return this.gytdsyzh;
	}

	public void setGytdsyzh(String gytdsyzh) {
		this.gytdsyzh = gytdsyzh;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getJsydghxkz() {
		return this.jsydghxkz;
	}

	public void setJsydghxkz(String jsydghxkz) {
		this.jsydghxkz = jsydghxkz;
	}

	public String getJsydghxkzh() {
		return this.jsydghxkzh;
	}

	public void setJsydghxkzh(String jsydghxkzh) {
		this.jsydghxkzh = jsydghxkzh;
	}

	public String getJzgcghxkz() {
		return this.jzgcghxkz;
	}

	public void setJzgcghxkz(String jzgcghxkz) {
		this.jzgcghxkz = jzgcghxkz;
	}

	public String getJzgcghxkzh() {
		return this.jzgcghxkzh;
	}

	public void setJzgcghxkzh(String jzgcghxkzh) {
		this.jzgcghxkzh = jzgcghxkzh;
	}

	public String getJzgcsgxkz() {
		return this.jzgcsgxkz;
	}

	public void setJzgcsgxkz(String jzgcsgxkz) {
		this.jzgcsgxkz = jzgcsgxkz;
	}

	public String getJzgcsgxkzh() {
		return this.jzgcsgxkzh;
	}

	public void setJzgcsgxkzh(String jzgcsgxkzh) {
		this.jzgcsgxkzh = jzgcsgxkzh;
	}

	public String getKfszz() {
		return this.kfszz;
	}

	public void setKfszz(String kfszz) {
		this.kfszz = kfszz;
	}

	public String getKfszzjgdm() {
		return this.kfszzjgdm;
	}

	public void setKfszzjgdm(String kfszzjgdm) {
		this.kfszzjgdm = kfszzjgdm;
	}

	public BigDecimal getRjl() {
		return this.rjl;
	}

	public void setRjl(BigDecimal rjl) {
		this.rjl = rjl;
	}

	public String getSfqdysxkz() {
		return this.sfqdysxkz;
	}

	public void setSfqdysxkz(String sfqdysxkz) {
		this.sfqdysxkz = sfqdysxkz;
	}

	public String getTrzhtbh() {
		return this.trzhtbh;
	}

	public void setTrzhtbh(String trzhtbh) {
		this.trzhtbh = trzhtbh;
	}

	public String getXmdlwz() {
		return this.xmdlwz;
	}

	public void setXmdlwz(String xmdlwz) {
		this.xmdlwz = xmdlwz;
	}

	public String getXmjtmc() {
		return this.xmjtmc;
	}

	public void setXmjtmc(String xmjtmc) {
		this.xmjtmc = xmjtmc;
	}

	public String getXmsfyjrsgjd() {
		return this.xmsfyjrsgjd;
	}

	public void setXmsfyjrsgjd(String xmsfyjrsgjd) {
		this.xmsfyjrsgjd = xmsfyjrsgjd;
	}

	public String getXmsfyksxs() {
		return this.xmsfyksxs;
	}

	public void setXmsfyksxs(String xmsfyksxs) {
		this.xmsfyksxs = xmsfyksxs;
	}

	public BigDecimal getXmwgjd() {
		return this.xmwgjd;
	}

	public void setXmwgjd(BigDecimal xmwgjd) {
		this.xmwgjd = xmwgjd;
	}

	public BigDecimal getXmxshkze() {
		return this.xmxshkze;
	}

	public void setXmxshkze(BigDecimal xmxshkze) {
		this.xmxshkze = xmxshkze;
	}

	public BigDecimal getXmxsjd() {
		return this.xmxsjd;
	}

	public void setXmxsjd(BigDecimal xmxsjd) {
		this.xmxsjd = xmxsjd;
	}

	public BigDecimal getXmyjddkze() {
		return this.xmyjddkze;
	}

	public void setXmyjddkze(BigDecimal xmyjddkze) {
		this.xmyjddkze = xmyjddkze;
	}

	public String getXshkzjjkap() {
		return this.xshkzjjkap;
	}

	public void setXshkzjjkap(String xshkzjjkap) {
		this.xshkzjjkap = xshkzjjkap;
	}

	public String getXtgsdxmdpj() {
		return this.xtgsdxmdpj;
	}

	public void setXtgsdxmdpj(String xtgsdxmdpj) {
		this.xtgsdxmdpj = xtgsdxmdpj;
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

	public String getXxszdyb() {
		return this.xxszdyb;
	}

	public void setXxszdyb(String xxszdyb) {
		this.xxszdyb = xxszdyb;
	}

	public String getYsxkzhm() {
		return this.ysxkzhm;
	}

	public void setYsxkzhm(String ysxkzhm) {
		this.ysxkzhm = ysxkzhm;
	}

	public BigDecimal getZbjbl() {
		return this.zbjbl;
	}

	public void setZbjbl(BigDecimal zbjbl) {
		this.zbjbl = zbjbl;
	}

	public BigDecimal getZjzmj() {
		return this.zjzmj;
	}

	public void setZjzmj(BigDecimal zjzmj) {
		this.zjzmj = zjzmj;
	}

	public BigDecimal getZtzje() {
		return this.ztzje;
	}

	public void setZtzje(BigDecimal ztzje) {
		this.ztzje = ztzje;
	}

	public BigDecimal getZzdmj() {
		return this.zzdmj;
	}

	public void setZzdmj(BigDecimal zzdmj) {
		this.zzdmj = zzdmj;
	}

}