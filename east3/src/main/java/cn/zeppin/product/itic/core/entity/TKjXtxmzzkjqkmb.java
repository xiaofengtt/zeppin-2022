package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KJ_XTXMZZKJQKMB database table.
 * 
 */
@Entity
@Table(name="T_KJ_XTXMZZKJQKMB")
public class TKjXtxmzzkjqkmb extends BaseEntity {
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

	private String hsbz;

	private String jrxkzh;

	private String kjrq;

	private BigDecimal qcdfye;

	private BigDecimal qcjfye;

	private BigDecimal qmdfye;

	private BigDecimal qmjfye;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbh;

	private String xtzxmbh;

	private String xtzzkjkmbh;

	private BigDecimal xtzzkjkmjc;

	private BigDecimal xtzzkjkmlx;

	private String xtzzkjkmmc;

	public TKjXtxmzzkjqkmb() {
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

	public String getXtzzkjkmbh() {
		return this.xtzzkjkmbh;
	}

	public void setXtzzkjkmbh(String xtzzkjkmbh) {
		this.xtzzkjkmbh = xtzzkjkmbh;
	}

	public BigDecimal getXtzzkjkmjc() {
		return this.xtzzkjkmjc;
	}

	public void setXtzzkjkmjc(BigDecimal xtzzkjkmjc) {
		this.xtzzkjkmjc = xtzzkjkmjc;
	}

	public BigDecimal getXtzzkjkmlx() {
		return this.xtzzkjkmlx;
	}

	public void setXtzzkjkmlx(BigDecimal xtzzkjkmlx) {
		this.xtzzkjkmlx = xtzzkjkmlx;
	}

	public String getXtzzkjkmmc() {
		return this.xtzzkjkmmc;
	}

	public void setXtzzkjkmmc(String xtzzkjkmmc) {
		this.xtzzkjkmmc = xtzzkjkmmc;
	}

}