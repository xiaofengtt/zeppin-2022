package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_XTZHXX database table.
 * 
 */
@Entity
@Table(name="T_XM_XTZHXX")
public class TXmXtzhxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bz;

	private String cjrq;

	private String jrxkzh;

	private String khjgmc;

	private String sftg;

	private String tgrjrxkz;

	private String tgrmc;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbm;

	private String xtzhlx;

	private String xtzhzh;

	private String xtzxmbh;

	private BigDecimal ye;

	private String zhbh;

	private String zhdzpd;

	private String zhswglfs;

	private String zhyjbgfs;

	private String zhzt;

	private String zjhm;

	public TXmXtzhxx() {
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

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getKhjgmc() {
		return this.khjgmc;
	}

	public void setKhjgmc(String khjgmc) {
		this.khjgmc = khjgmc;
	}

	public String getSftg() {
		return this.sftg;
	}

	public void setSftg(String sftg) {
		this.sftg = sftg;
	}

	public String getTgrjrxkz() {
		return this.tgrjrxkz;
	}

	public void setTgrjrxkz(String tgrjrxkz) {
		this.tgrjrxkz = tgrjrxkz;
	}

	public String getTgrmc() {
		return this.tgrmc;
	}

	public void setTgrmc(String tgrmc) {
		this.tgrmc = tgrmc;
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

	public String getXtzhlx() {
		return this.xtzhlx;
	}

	public void setXtzhlx(String xtzhlx) {
		this.xtzhlx = xtzhlx;
	}

	public String getXtzhzh() {
		return this.xtzhzh;
	}

	public void setXtzhzh(String xtzhzh) {
		this.xtzhzh = xtzhzh;
	}

	public String getXtzxmbh() {
		return this.xtzxmbh;
	}

	public void setXtzxmbh(String xtzxmbh) {
		this.xtzxmbh = xtzxmbh;
	}

	public BigDecimal getYe() {
		return this.ye;
	}

	public void setYe(BigDecimal ye) {
		this.ye = ye;
	}

	public String getZhbh() {
		return this.zhbh;
	}

	public void setZhbh(String zhbh) {
		this.zhbh = zhbh;
	}

	public String getZhdzpd() {
		return this.zhdzpd;
	}

	public void setZhdzpd(String zhdzpd) {
		this.zhdzpd = zhdzpd;
	}

	public String getZhswglfs() {
		return this.zhswglfs;
	}

	public void setZhswglfs(String zhswglfs) {
		this.zhswglfs = zhswglfs;
	}

	public String getZhyjbgfs() {
		return this.zhyjbgfs;
	}

	public void setZhyjbgfs(String zhyjbgfs) {
		this.zhyjbgfs = zhyjbgfs;
	}

	public String getZhzt() {
		return this.zhzt;
	}

	public void setZhzt(String zhzt) {
		this.zhzt = zhzt;
	}

	public String getZjhm() {
		return this.zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

}