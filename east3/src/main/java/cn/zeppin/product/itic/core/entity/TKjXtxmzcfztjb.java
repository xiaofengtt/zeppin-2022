package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KJ_XTXMZCFZTJB database table.
 * 
 */
@Entity
@Table(name="T_KJ_XTXMZCFZTJB")
public class TKjXtxmzcfztjb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private BigDecimal bszq;

	private String bz;

	private String cjrq;

	private String jrxkzh;

	private String tjrq;

	private String xtjgdm;

	private String xtjgmc;

	private String xttjkmbh;

	private BigDecimal xttjkmje;

	private String xttjkmmc;

	private String xtxmbh;

	private String xtzxmbh;

	public TKjXtxmzcfztjb() {
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

	public BigDecimal getBszq() {
		return this.bszq;
	}

	public void setBszq(BigDecimal bszq) {
		this.bszq = bszq;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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

	public String getTjrq() {
		return this.tjrq;
	}

	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
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

	public String getXttjkmbh() {
		return this.xttjkmbh;
	}

	public void setXttjkmbh(String xttjkmbh) {
		this.xttjkmbh = xttjkmbh;
	}

	public BigDecimal getXttjkmje() {
		return this.xttjkmje;
	}

	public void setXttjkmje(BigDecimal xttjkmje) {
		this.xttjkmje = xttjkmje;
	}

	public String getXttjkmmc() {
		return this.xttjkmmc;
	}

	public void setXttjkmmc(String xttjkmmc) {
		this.xttjkmmc = xttjkmmc;
	}

	public String getXtxmbh() {
		return this.xtxmbh;
	}

	public void setXtxmbh(String xtxmbh) {
		this.xtxmbh = xtxmbh;
	}

	public String getXtzxmbh() {
		return this.xtzxmbh;
	}

	public void setXtzxmbh(String xtzxmbh) {
		this.xtzxmbh = xtzxmbh;
	}

}