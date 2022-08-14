package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_GG_JGXXB database table.
 * 
 */
@Entity
@Table(name="T_GG_JGXXB")
public class TGgJgxxb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String clsj;

	private String frxm;

	private String gsgs;

	private String jgdz;

	private String jrxkzh;

	private String tyshxydm;

	private String xtjgdm;

	private String xtjgmc;

	private String yyzt;

	private String yzbm;

	private String zcdq;

	private BigDecimal zczb;

	public TGgJgxxb() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getClsj() {
		return this.clsj;
	}

	public void setClsj(String clsj) {
		this.clsj = clsj;
	}

	public String getFrxm() {
		return this.frxm;
	}

	public void setFrxm(String frxm) {
		this.frxm = frxm;
	}

	public String getGsgs() {
		return this.gsgs;
	}

	public void setGsgs(String gsgs) {
		this.gsgs = gsgs;
	}

	public String getJgdz() {
		return this.jgdz;
	}

	public void setJgdz(String jgdz) {
		this.jgdz = jgdz;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getTyshxydm() {
		return this.tyshxydm;
	}

	public void setTyshxydm(String tyshxydm) {
		this.tyshxydm = tyshxydm;
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

	public String getYyzt() {
		return this.yyzt;
	}

	public void setYyzt(String yyzt) {
		this.yyzt = yyzt;
	}

	public String getYzbm() {
		return this.yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	public String getZcdq() {
		return this.zcdq;
	}

	public void setZcdq(String zcdq) {
		this.zcdq = zcdq;
	}

	public BigDecimal getZczb() {
		return this.zczb;
	}

	public void setZczb(BigDecimal zczb) {
		this.zczb = zczb;
	}

}