package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_JY_QJGLXXZQ database table.
 * 
 */
@Entity
@Table(name="T_JY_QJGLXXZQ")
public class TJyQjglxxzq extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private BigDecimal bndxtljjzzzl;

	private String cjrq;

	private String jrxkzh;

	private BigDecimal ljqhzjje;

	private BigDecimal ljzjzjje;

	private BigDecimal xmclyljzzzl;

	private String xtccdwjzjspd;

	private String xtccdwjzplpd;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbh;

	private String xtzqzhzh;

	private String xtzxmbh;

	private String zcglbgpd;

	private BigDecimal zjqhzjje;

	private String zjqhzjrq;

	private BigDecimal zjsdxtccdwjz;

	private BigDecimal zjsdxtxmccbl;

	private BigDecimal zjsdxtxmcydddccye;

	private BigDecimal zjsdxtxmcydtbzjzy;

	private BigDecimal zjsdxtxmcygpje;

	private BigDecimal zjsdxtxmcyjjje;

	private BigDecimal zjsdxtxmcyjkddccye;

	private BigDecimal zjsdxtxmcykddccye;

	private BigDecimal zjsdxtxmcyktbzjzy;

	private BigDecimal zjsdxtxmcyqtyjzqje;

	private BigDecimal zjsdxtxmcyzqje;

	private BigDecimal zjsdxtxmqhzjzhjszbjye;

	private String zjzcglbgrq;

	private BigDecimal zjzjzjje;

	private String zjzjzjrq;

	public TJyQjglxxzq() {
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

	public BigDecimal getBndxtljjzzzl() {
		return this.bndxtljjzzzl;
	}

	public void setBndxtljjzzzl(BigDecimal bndxtljjzzzl) {
		this.bndxtljjzzzl = bndxtljjzzzl;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public BigDecimal getLjqhzjje() {
		return this.ljqhzjje;
	}

	public void setLjqhzjje(BigDecimal ljqhzjje) {
		this.ljqhzjje = ljqhzjje;
	}

	public BigDecimal getLjzjzjje() {
		return this.ljzjzjje;
	}

	public void setLjzjzjje(BigDecimal ljzjzjje) {
		this.ljzjzjje = ljzjzjje;
	}

	public BigDecimal getXmclyljzzzl() {
		return this.xmclyljzzzl;
	}

	public void setXmclyljzzzl(BigDecimal xmclyljzzzl) {
		this.xmclyljzzzl = xmclyljzzzl;
	}

	public String getXtccdwjzjspd() {
		return this.xtccdwjzjspd;
	}

	public void setXtccdwjzjspd(String xtccdwjzjspd) {
		this.xtccdwjzjspd = xtccdwjzjspd;
	}

	public String getXtccdwjzplpd() {
		return this.xtccdwjzplpd;
	}

	public void setXtccdwjzplpd(String xtccdwjzplpd) {
		this.xtccdwjzplpd = xtccdwjzplpd;
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

	public String getXtzqzhzh() {
		return this.xtzqzhzh;
	}

	public void setXtzqzhzh(String xtzqzhzh) {
		this.xtzqzhzh = xtzqzhzh;
	}

	public String getXtzxmbh() {
		return this.xtzxmbh;
	}

	public void setXtzxmbh(String xtzxmbh) {
		this.xtzxmbh = xtzxmbh;
	}

	public String getZcglbgpd() {
		return this.zcglbgpd;
	}

	public void setZcglbgpd(String zcglbgpd) {
		this.zcglbgpd = zcglbgpd;
	}

	public BigDecimal getZjqhzjje() {
		return this.zjqhzjje;
	}

	public void setZjqhzjje(BigDecimal zjqhzjje) {
		this.zjqhzjje = zjqhzjje;
	}

	public String getZjqhzjrq() {
		return this.zjqhzjrq;
	}

	public void setZjqhzjrq(String zjqhzjrq) {
		this.zjqhzjrq = zjqhzjrq;
	}

	public BigDecimal getZjsdxtccdwjz() {
		return this.zjsdxtccdwjz;
	}

	public void setZjsdxtccdwjz(BigDecimal zjsdxtccdwjz) {
		this.zjsdxtccdwjz = zjsdxtccdwjz;
	}

	public BigDecimal getZjsdxtxmccbl() {
		return this.zjsdxtxmccbl;
	}

	public void setZjsdxtxmccbl(BigDecimal zjsdxtxmccbl) {
		this.zjsdxtxmccbl = zjsdxtxmccbl;
	}

	public BigDecimal getZjsdxtxmcydddccye() {
		return this.zjsdxtxmcydddccye;
	}

	public void setZjsdxtxmcydddccye(BigDecimal zjsdxtxmcydddccye) {
		this.zjsdxtxmcydddccye = zjsdxtxmcydddccye;
	}

	public BigDecimal getZjsdxtxmcydtbzjzy() {
		return this.zjsdxtxmcydtbzjzy;
	}

	public void setZjsdxtxmcydtbzjzy(BigDecimal zjsdxtxmcydtbzjzy) {
		this.zjsdxtxmcydtbzjzy = zjsdxtxmcydtbzjzy;
	}

	public BigDecimal getZjsdxtxmcygpje() {
		return this.zjsdxtxmcygpje;
	}

	public void setZjsdxtxmcygpje(BigDecimal zjsdxtxmcygpje) {
		this.zjsdxtxmcygpje = zjsdxtxmcygpje;
	}

	public BigDecimal getZjsdxtxmcyjjje() {
		return this.zjsdxtxmcyjjje;
	}

	public void setZjsdxtxmcyjjje(BigDecimal zjsdxtxmcyjjje) {
		this.zjsdxtxmcyjjje = zjsdxtxmcyjjje;
	}

	public BigDecimal getZjsdxtxmcyjkddccye() {
		return this.zjsdxtxmcyjkddccye;
	}

	public void setZjsdxtxmcyjkddccye(BigDecimal zjsdxtxmcyjkddccye) {
		this.zjsdxtxmcyjkddccye = zjsdxtxmcyjkddccye;
	}

	public BigDecimal getZjsdxtxmcykddccye() {
		return this.zjsdxtxmcykddccye;
	}

	public void setZjsdxtxmcykddccye(BigDecimal zjsdxtxmcykddccye) {
		this.zjsdxtxmcykddccye = zjsdxtxmcykddccye;
	}

	public BigDecimal getZjsdxtxmcyktbzjzy() {
		return this.zjsdxtxmcyktbzjzy;
	}

	public void setZjsdxtxmcyktbzjzy(BigDecimal zjsdxtxmcyktbzjzy) {
		this.zjsdxtxmcyktbzjzy = zjsdxtxmcyktbzjzy;
	}

	public BigDecimal getZjsdxtxmcyqtyjzqje() {
		return this.zjsdxtxmcyqtyjzqje;
	}

	public void setZjsdxtxmcyqtyjzqje(BigDecimal zjsdxtxmcyqtyjzqje) {
		this.zjsdxtxmcyqtyjzqje = zjsdxtxmcyqtyjzqje;
	}

	public BigDecimal getZjsdxtxmcyzqje() {
		return this.zjsdxtxmcyzqje;
	}

	public void setZjsdxtxmcyzqje(BigDecimal zjsdxtxmcyzqje) {
		this.zjsdxtxmcyzqje = zjsdxtxmcyzqje;
	}

	public BigDecimal getZjsdxtxmqhzjzhjszbjye() {
		return this.zjsdxtxmqhzjzhjszbjye;
	}

	public void setZjsdxtxmqhzjzhjszbjye(BigDecimal zjsdxtxmqhzjzhjszbjye) {
		this.zjsdxtxmqhzjzhjszbjye = zjsdxtxmqhzjzhjszbjye;
	}

	public String getZjzcglbgrq() {
		return this.zjzcglbgrq;
	}

	public void setZjzcglbgrq(String zjzcglbgrq) {
		this.zjzcglbgrq = zjzcglbgrq;
	}

	public BigDecimal getZjzjzjje() {
		return this.zjzjzjje;
	}

	public void setZjzjzjje(BigDecimal zjzjzjje) {
		this.zjzjzjje = zjzjzjje;
	}

	public String getZjzjzjrq() {
		return this.zjzjzjrq;
	}

	public void setZjzjzjrq(String zjzjzjrq) {
		this.zjzjzjrq = zjzjzjrq;
	}

}