package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KJ_GYZCFZKMTJB database table.
 * 
 */
@Entity
@Table(name="T_KJ_GYZCFZKMTJB")
public class TKjGyzcfzkmtjb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bz;

	private String cjrq;

	private String gytjkmbh;

	private BigDecimal gytjkmje;

	private String gytjkmmc;

	private String jrxkzh;

	private BigDecimal qxlx;

	private String tjrq;

	public TKjGyzcfzkmtjb() {
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

	public String getGytjkmbh() {
		return this.gytjkmbh;
	}

	public void setGytjkmbh(String gytjkmbh) {
		this.gytjkmbh = gytjkmbh;
	}

	public BigDecimal getGytjkmje() {
		return this.gytjkmje;
	}

	public void setGytjkmje(BigDecimal gytjkmje) {
		this.gytjkmje = gytjkmje;
	}

	public String getGytjkmmc() {
		return this.gytjkmmc;
	}

	public void setGytjkmmc(String gytjkmmc) {
		this.gytjkmmc = gytjkmmc;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public BigDecimal getQxlx() {
		return this.qxlx;
	}

	public void setQxlx(BigDecimal qxlx) {
		this.qxlx = qxlx;
	}

	public String getTjrq() {
		return this.tjrq;
	}

	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
	}

}