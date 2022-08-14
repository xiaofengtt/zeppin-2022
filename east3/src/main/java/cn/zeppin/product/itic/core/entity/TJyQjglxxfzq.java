package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_JY_QJGLXXFZQ database table.
 * 
 */
@Entity
@Table(name="T_JY_QJGLXXFZQ")
public class TJyQjglxxfzq extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bzjdwlyqk;

	private String cjrq;

	private BigDecimal dzyl;

	private String jrxkzh;

	private String jydsbh;

	private BigDecimal ljyqts;

	private String sfczyq;

	private String sffsjxhj;

	private String sffszq;

	private String sfwaqhx;

	private String sfybzzj;

	private String sjdfzjly;

	private BigDecimal tqbjje;

	private BigDecimal tqlxje;

	private BigDecimal tqlxts;

	private BigDecimal tzbddzxpgjz;

	private String tzbddzxpgrq;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbh;

	private String xtzxmbh;

	private String zcglbgpd;

	private String zjqxtzcwjfl;

	private BigDecimal zjsdrzye;

	private BigDecimal zjsdtzye;

	private String zjyczqdrq;

	private BigDecimal zqcs;

	private String zxzcglbgrq;

	public TJyQjglxxfzq() {
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

	public String getBzjdwlyqk() {
		return this.bzjdwlyqk;
	}

	public void setBzjdwlyqk(String bzjdwlyqk) {
		this.bzjdwlyqk = bzjdwlyqk;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public BigDecimal getDzyl() {
		return this.dzyl;
	}

	public void setDzyl(BigDecimal dzyl) {
		this.dzyl = dzyl;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getJydsbh() {
		return this.jydsbh;
	}

	public void setJydsbh(String jydsbh) {
		this.jydsbh = jydsbh;
	}

	public BigDecimal getLjyqts() {
		return this.ljyqts;
	}

	public void setLjyqts(BigDecimal ljyqts) {
		this.ljyqts = ljyqts;
	}

	public String getSfczyq() {
		return this.sfczyq;
	}

	public void setSfczyq(String sfczyq) {
		this.sfczyq = sfczyq;
	}

	public String getSffsjxhj() {
		return this.sffsjxhj;
	}

	public void setSffsjxhj(String sffsjxhj) {
		this.sffsjxhj = sffsjxhj;
	}

	public String getSffszq() {
		return this.sffszq;
	}

	public void setSffszq(String sffszq) {
		this.sffszq = sffszq;
	}

	public String getSfwaqhx() {
		return this.sfwaqhx;
	}

	public void setSfwaqhx(String sfwaqhx) {
		this.sfwaqhx = sfwaqhx;
	}

	public String getSfybzzj() {
		return this.sfybzzj;
	}

	public void setSfybzzj(String sfybzzj) {
		this.sfybzzj = sfybzzj;
	}

	public String getSjdfzjly() {
		return this.sjdfzjly;
	}

	public void setSjdfzjly(String sjdfzjly) {
		this.sjdfzjly = sjdfzjly;
	}

	public BigDecimal getTqbjje() {
		return this.tqbjje;
	}

	public void setTqbjje(BigDecimal tqbjje) {
		this.tqbjje = tqbjje;
	}

	public BigDecimal getTqlxje() {
		return this.tqlxje;
	}

	public void setTqlxje(BigDecimal tqlxje) {
		this.tqlxje = tqlxje;
	}

	public BigDecimal getTqlxts() {
		return this.tqlxts;
	}

	public void setTqlxts(BigDecimal tqlxts) {
		this.tqlxts = tqlxts;
	}

	public BigDecimal getTzbddzxpgjz() {
		return this.tzbddzxpgjz;
	}

	public void setTzbddzxpgjz(BigDecimal tzbddzxpgjz) {
		this.tzbddzxpgjz = tzbddzxpgjz;
	}

	public String getTzbddzxpgrq() {
		return this.tzbddzxpgrq;
	}

	public void setTzbddzxpgrq(String tzbddzxpgrq) {
		this.tzbddzxpgrq = tzbddzxpgrq;
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

	public String getZcglbgpd() {
		return this.zcglbgpd;
	}

	public void setZcglbgpd(String zcglbgpd) {
		this.zcglbgpd = zcglbgpd;
	}

	public String getZjqxtzcwjfl() {
		return this.zjqxtzcwjfl;
	}

	public void setZjqxtzcwjfl(String zjqxtzcwjfl) {
		this.zjqxtzcwjfl = zjqxtzcwjfl;
	}

	public BigDecimal getZjsdrzye() {
		return this.zjsdrzye;
	}

	public void setZjsdrzye(BigDecimal zjsdrzye) {
		this.zjsdrzye = zjsdrzye;
	}

	public BigDecimal getZjsdtzye() {
		return this.zjsdtzye;
	}

	public void setZjsdtzye(BigDecimal zjsdtzye) {
		this.zjsdtzye = zjsdtzye;
	}

	public String getZjyczqdrq() {
		return this.zjyczqdrq;
	}

	public void setZjyczqdrq(String zjyczqdrq) {
		this.zjyczqdrq = zjyczqdrq;
	}

	public BigDecimal getZqcs() {
		return this.zqcs;
	}

	public void setZqcs(BigDecimal zqcs) {
		this.zqcs = zqcs;
	}

	public String getZxzcglbgrq() {
		return this.zxzcglbgrq;
	}

	public void setZxzcglbgrq(String zxzcglbgrq) {
		this.zxzcglbgrq = zxzcglbgrq;
	}

}