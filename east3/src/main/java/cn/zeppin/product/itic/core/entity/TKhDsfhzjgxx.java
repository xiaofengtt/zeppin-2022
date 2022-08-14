package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_KH_DSFHZJGXX database table.
 * 
 */
@Entity
@Table(name="T_KH_DSFHZJGXX")
public class TKhDsfhzjgxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String clrq;

	private String djbh;

	private String djlx;

	private String djrq;

	private String dsfjgbh;

	private String dsflx;

	private String dsfmc;

	private String fddbr;

	private String fddbrzjhm;

	private String fddbrzjlx;

	private String jrxkzh;

	private String nbpj;

	private String sfdj;

	private String shxydm;

	private String xtjgdm;

	private String xtjgmc;

	private BigDecimal zczb;

	private String zjdm;

	private String zjlx;

	public TKhDsfhzjgxx() {
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

	public String getClrq() {
		return this.clrq;
	}

	public void setClrq(String clrq) {
		this.clrq = clrq;
	}

	public String getDjbh() {
		return this.djbh;
	}

	public void setDjbh(String djbh) {
		this.djbh = djbh;
	}

	public String getDjlx() {
		return this.djlx;
	}

	public void setDjlx(String djlx) {
		this.djlx = djlx;
	}

	public String getDjrq() {
		return this.djrq;
	}

	public void setDjrq(String djrq) {
		this.djrq = djrq;
	}

	public String getDsfjgbh() {
		return this.dsfjgbh;
	}

	public void setDsfjgbh(String dsfjgbh) {
		this.dsfjgbh = dsfjgbh;
	}

	public String getDsflx() {
		return this.dsflx;
	}

	public void setDsflx(String dsflx) {
		this.dsflx = dsflx;
	}

	public String getDsfmc() {
		return this.dsfmc;
	}

	public void setDsfmc(String dsfmc) {
		this.dsfmc = dsfmc;
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

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getNbpj() {
		return this.nbpj;
	}

	public void setNbpj(String nbpj) {
		this.nbpj = nbpj;
	}

	public String getSfdj() {
		return this.sfdj;
	}

	public void setSfdj(String sfdj) {
		this.sfdj = sfdj;
	}

	public String getShxydm() {
		return this.shxydm;
	}

	public void setShxydm(String shxydm) {
		this.shxydm = shxydm;
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

	public BigDecimal getZczb() {
		return this.zczb;
	}

	public void setZczb(BigDecimal zczb) {
		this.zczb = zczb;
	}

	public String getZjdm() {
		return this.zjdm;
	}

	public void setZjdm(String zjdm) {
		this.zjdm = zjdm;
	}

	public String getZjlx() {
		return this.zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}

}