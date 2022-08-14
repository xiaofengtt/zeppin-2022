package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_GG_GDXXB database table.
 * 
 */
@Entity
@Table(name="T_GG_GDXXB")
public class TGgGdxxb extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	private String id;
	private BigDecimal cgbl;
	private String cjrq;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String gdlb;
	private String gdlx;
	private String gdmc;
	private String gdzjhm;
	private String gdzjlx;
	private String gdzt;
	private String jrxkzh;
	private String kgfs;
	private String status;
	private String xtjgdm;
	private String xtjgmc;

	public TGgGdxxb() {
	}


	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return this.id;
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


	public BigDecimal getCgbl() {
		return this.cgbl;
	}

	public void setCgbl(BigDecimal cgbl) {
		this.cgbl = cgbl;
	}


	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}


	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}


	public String getGdlb() {
		return this.gdlb;
	}

	public void setGdlb(String gdlb) {
		this.gdlb = gdlb;
	}


	public String getGdlx() {
		return this.gdlx;
	}

	public void setGdlx(String gdlx) {
		this.gdlx = gdlx;
	}


	public String getGdmc() {
		return this.gdmc;
	}

	public void setGdmc(String gdmc) {
		this.gdmc = gdmc;
	}


	public String getGdzjhm() {
		return this.gdzjhm;
	}

	public void setGdzjhm(String gdzjhm) {
		this.gdzjhm = gdzjhm;
	}


	public String getGdzjlx() {
		return this.gdzjlx;
	}

	public void setGdzjlx(String gdzjlx) {
		this.gdzjlx = gdzjlx;
	}


	public String getGdzt() {
		return this.gdzt;
	}

	public void setGdzt(String gdzt) {
		this.gdzt = gdzt;
	}


	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}


	public String getKgfs() {
		return this.kgfs;
	}

	public void setKgfs(String kgfs) {
		this.kgfs = kgfs;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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