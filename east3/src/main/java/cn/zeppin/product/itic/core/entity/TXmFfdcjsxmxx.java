package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_FFDCJSXMXX database table.
 * 
 */
@Entity
@Table(name="T_XM_FFDCJSXMXX")
public class TXmFfdcjsxmxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String jrxkzh;

	private String pzxmwjbh;

	private String pzxmwjmc;

	private String trzhtbh;

	private String xmjtmc;

	private String xmlx;

	private BigDecimal xmzbjbl;

	private BigDecimal xmztzje;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbm;

	private String xtzxmbm;

	public TXmFfdcjsxmxx() {
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

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getPzxmwjbh() {
		return this.pzxmwjbh;
	}

	public void setPzxmwjbh(String pzxmwjbh) {
		this.pzxmwjbh = pzxmwjbh;
	}

	public String getPzxmwjmc() {
		return this.pzxmwjmc;
	}

	public void setPzxmwjmc(String pzxmwjmc) {
		this.pzxmwjmc = pzxmwjmc;
	}

	public String getTrzhtbh() {
		return this.trzhtbh;
	}

	public void setTrzhtbh(String trzhtbh) {
		this.trzhtbh = trzhtbh;
	}

	public String getXmjtmc() {
		return this.xmjtmc;
	}

	public void setXmjtmc(String xmjtmc) {
		this.xmjtmc = xmjtmc;
	}

	public String getXmlx() {
		return this.xmlx;
	}

	public void setXmlx(String xmlx) {
		this.xmlx = xmlx;
	}

	public BigDecimal getXmzbjbl() {
		return this.xmzbjbl;
	}

	public void setXmzbjbl(BigDecimal xmzbjbl) {
		this.xmzbjbl = xmzbjbl;
	}

	public BigDecimal getXmztzje() {
		return this.xmztzje;
	}

	public void setXmztzje(BigDecimal xmztzje) {
		this.xmztzje = xmztzje;
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