package cn.zeppin.product.itic.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_GG_YGXXB database table.
 * 
 */
@Entity
@Table(name="T_GG_YGXXB")
public class TGgYgxxb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String jrxkzh;

	private String lxdh;

	private String sfgg;

	private String ssbm;

	private String xtjgdm;

	private String xtjgmc;

	private String yggh;

	private String ygxm;

	private String ygzt;

	private String zjhm;

	private String zjlx;

	private String zw;

	public TGgYgxxb() {
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

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getSfgg() {
		return this.sfgg;
	}

	public void setSfgg(String sfgg) {
		this.sfgg = sfgg;
	}

	public String getSsbm() {
		return this.ssbm;
	}

	public void setSsbm(String ssbm) {
		this.ssbm = ssbm;
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

	public String getYggh() {
		return this.yggh;
	}

	public void setYggh(String yggh) {
		this.yggh = yggh;
	}

	public String getYgxm() {
		return this.ygxm;
	}

	public void setYgxm(String ygxm) {
		this.ygxm = ygxm;
	}

	public String getYgzt() {
		return this.ygzt;
	}

	public void setYgzt(String ygzt) {
		this.ygzt = ygzt;
	}

	public String getZjhm() {
		return this.zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getZjlx() {
		return this.zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}

	public String getZw() {
		return this.zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

}