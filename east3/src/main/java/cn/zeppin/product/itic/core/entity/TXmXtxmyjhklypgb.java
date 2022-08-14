package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_XTXMYJHKLYPGB database table.
 * 
 */
@Entity
@Table(name="T_XM_XTXMYJHKLYPGB")
public class TXmXtxmyjhklypgb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private BigDecimal bot;

	private String cjrq;

	private BigDecimal czdd;

	private BigDecimal fdcsxsr;

	private String htbh;

	private String jrxkzh;

	private BigDecimal jyxxjl;

	private String pgrq;

	private BigDecimal pgxh;

	private String pgzq;

	private BigDecimal qtje;

	private BigDecimal tdcrsr;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbm;

	private String xtxmqc;

	private BigDecimal xtzczrhcsbx;

	private String xtzxmbh;

	private BigDecimal zxfyfh;

	private BigDecimal zxsfh;

	public TXmXtxmyjhklypgb() {
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

	public BigDecimal getBot() {
		return this.bot;
	}

	public void setBot(BigDecimal bot) {
		this.bot = bot;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public BigDecimal getCzdd() {
		return this.czdd;
	}

	public void setCzdd(BigDecimal czdd) {
		this.czdd = czdd;
	}

	public BigDecimal getFdcsxsr() {
		return this.fdcsxsr;
	}

	public void setFdcsxsr(BigDecimal fdcsxsr) {
		this.fdcsxsr = fdcsxsr;
	}

	public String getHtbh() {
		return this.htbh;
	}

	public void setHtbh(String htbh) {
		this.htbh = htbh;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public BigDecimal getJyxxjl() {
		return this.jyxxjl;
	}

	public void setJyxxjl(BigDecimal jyxxjl) {
		this.jyxxjl = jyxxjl;
	}

	public String getPgrq() {
		return this.pgrq;
	}

	public void setPgrq(String pgrq) {
		this.pgrq = pgrq;
	}

	public BigDecimal getPgxh() {
		return this.pgxh;
	}

	public void setPgxh(BigDecimal pgxh) {
		this.pgxh = pgxh;
	}

	public String getPgzq() {
		return this.pgzq;
	}

	public void setPgzq(String pgzq) {
		this.pgzq = pgzq;
	}

	public BigDecimal getQtje() {
		return this.qtje;
	}

	public void setQtje(BigDecimal qtje) {
		this.qtje = qtje;
	}

	public BigDecimal getTdcrsr() {
		return this.tdcrsr;
	}

	public void setTdcrsr(BigDecimal tdcrsr) {
		this.tdcrsr = tdcrsr;
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

	public String getXtxmqc() {
		return this.xtxmqc;
	}

	public void setXtxmqc(String xtxmqc) {
		this.xtxmqc = xtxmqc;
	}

	public BigDecimal getXtzczrhcsbx() {
		return this.xtzczrhcsbx;
	}

	public void setXtzczrhcsbx(BigDecimal xtzczrhcsbx) {
		this.xtzczrhcsbx = xtzczrhcsbx;
	}

	public String getXtzxmbh() {
		return this.xtzxmbh;
	}

	public void setXtzxmbh(String xtzxmbh) {
		this.xtzxmbh = xtzxmbh;
	}

	public BigDecimal getZxfyfh() {
		return this.zxfyfh;
	}

	public void setZxfyfh(BigDecimal zxfyfh) {
		this.zxfyfh = zxfyfh;
	}

	public BigDecimal getZxsfh() {
		return this.zxsfh;
	}

	public void setZxsfh(BigDecimal zxsfh) {
		this.zxsfh = zxsfh;
	}

}