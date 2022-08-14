package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KJ_GYZZKJQKMB database table.
 * 
 */
@Entity
@Table(name="T_KJ_GYZZKJQKMB")
public class TKjGyzzkjqkmb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private BigDecimal bqdffse;

	private BigDecimal bqjffse;

	private BigDecimal bszq;

	private String cjrq;

	private String gyzzkjkmbh;

	private BigDecimal gyzzkjkmjc;

	private BigDecimal gyzzkjkmlx;

	private String gyzzkjkmmc;

	private String hsbz;

	private String jrxkzh;

	private String kjrq;

	private BigDecimal qcdfye;

	private BigDecimal qcjfye;

	private BigDecimal qmdfye;

	private BigDecimal qmjfye;

	private String xtjgdm;

	private String xtjgmc;

	public TKjGyzzkjqkmb() {
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

	public BigDecimal getBqdffse() {
		return this.bqdffse;
	}

	public void setBqdffse(BigDecimal bqdffse) {
		this.bqdffse = bqdffse;
	}

	public BigDecimal getBqjffse() {
		return this.bqjffse;
	}

	public void setBqjffse(BigDecimal bqjffse) {
		this.bqjffse = bqjffse;
	}

	public BigDecimal getBszq() {
		return this.bszq;
	}

	public void setBszq(BigDecimal bszq) {
		this.bszq = bszq;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public String getGyzzkjkmbh() {
		return this.gyzzkjkmbh;
	}

	public void setGyzzkjkmbh(String gyzzkjkmbh) {
		this.gyzzkjkmbh = gyzzkjkmbh;
	}

	public BigDecimal getGyzzkjkmjc() {
		return this.gyzzkjkmjc;
	}

	public void setGyzzkjkmjc(BigDecimal gyzzkjkmjc) {
		this.gyzzkjkmjc = gyzzkjkmjc;
	}

	public BigDecimal getGyzzkjkmlx() {
		return this.gyzzkjkmlx;
	}

	public void setGyzzkjkmlx(BigDecimal gyzzkjkmlx) {
		this.gyzzkjkmlx = gyzzkjkmlx;
	}

	public String getGyzzkjkmmc() {
		return this.gyzzkjkmmc;
	}

	public void setGyzzkjkmmc(String gyzzkjkmmc) {
		this.gyzzkjkmmc = gyzzkjkmmc;
	}

	public String getHsbz() {
		return this.hsbz;
	}

	public void setHsbz(String hsbz) {
		this.hsbz = hsbz;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getKjrq() {
		return this.kjrq;
	}

	public void setKjrq(String kjrq) {
		this.kjrq = kjrq;
	}

	public BigDecimal getQcdfye() {
		return this.qcdfye;
	}

	public void setQcdfye(BigDecimal qcdfye) {
		this.qcdfye = qcdfye;
	}

	public BigDecimal getQcjfye() {
		return this.qcjfye;
	}

	public void setQcjfye(BigDecimal qcjfye) {
		this.qcjfye = qcjfye;
	}

	public BigDecimal getQmdfye() {
		return this.qmdfye;
	}

	public void setQmdfye(BigDecimal qmdfye) {
		this.qmdfye = qmdfye;
	}

	public BigDecimal getQmjfye() {
		return this.qmjfye;
	}

	public void setQmjfye(BigDecimal qmjfye) {
		this.qmjfye = qmjfye;
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

}