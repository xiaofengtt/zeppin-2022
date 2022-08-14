package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_XTXMXX database table.
 * 
 */
@Entity
@Table(name="T_XM_XTXMXX")
public class TXmXtxmxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bsjtfs;

	private String cjrq;

	private String cpzxbz;

	private BigDecimal csrgjg;

	private String dyjhbz;

	private String fgldbh;

	private String glfs;

	private String glmxm;

	private String glyyhcffs;

	private String hzjgbh;

	private String hzjgmc;

	private String hzms;

	private String jfjzx;

	private String jgsx;

	private String jrxkzh;

	private String jsbz;

	private String jtnbjgmc;

	private String jzpgpd;

	private String jzplpd;

	private String kfpd;

	private String kshbz;

	private String ktqzz;

	private String qxsm;

	private String sfjttj;

	private String sfsx;

	private String sfxjl;

	private String slfs;

	private String ssbm;

	private String strzz;

	private String syfs;

	private String tzfw;

	private String xmclr;

	private String xmhzly;

	private String xmjlbh;

	private String xmtotbs;

	private String xmzgbh;

	private String xmzzr;

	private BigDecimal xtbcl;

	private BigDecimal xtcsclfe;

	private String xtgn;

	private String xtjgdm;

	private String xtjgmc;

	private BigDecimal xtxcgm;

	private String xtxmbm;

	private String xtxmqc;

	private String xtywfl;

	private String xtzxmbh;

	private String yjdqr;

	private String ywtz;

	private String yxfs;

	private BigDecimal yxlhsyqbl;

	private String zcglbgpd;

	private BigDecimal zdyqsyl;

	private BigDecimal zgyqsyl;

	private String ztjd;

	private String zxjglx;

	private String zxxs;

	public TXmXtxmxx() {
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

	public String getBsjtfs() {
		return this.bsjtfs;
	}

	public void setBsjtfs(String bsjtfs) {
		this.bsjtfs = bsjtfs;
	}

	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public String getCpzxbz() {
		return this.cpzxbz;
	}

	public void setCpzxbz(String cpzxbz) {
		this.cpzxbz = cpzxbz;
	}

	public BigDecimal getCsrgjg() {
		return this.csrgjg;
	}

	public void setCsrgjg(BigDecimal csrgjg) {
		this.csrgjg = csrgjg;
	}

	public String getDyjhbz() {
		return this.dyjhbz;
	}

	public void setDyjhbz(String dyjhbz) {
		this.dyjhbz = dyjhbz;
	}

	public String getFgldbh() {
		return this.fgldbh;
	}

	public void setFgldbh(String fgldbh) {
		this.fgldbh = fgldbh;
	}

	public String getGlfs() {
		return this.glfs;
	}

	public void setGlfs(String glfs) {
		this.glfs = glfs;
	}

	public String getGlmxm() {
		return this.glmxm;
	}

	public void setGlmxm(String glmxm) {
		this.glmxm = glmxm;
	}

	public String getGlyyhcffs() {
		return this.glyyhcffs;
	}

	public void setGlyyhcffs(String glyyhcffs) {
		this.glyyhcffs = glyyhcffs;
	}

	public String getHzjgbh() {
		return this.hzjgbh;
	}

	public void setHzjgbh(String hzjgbh) {
		this.hzjgbh = hzjgbh;
	}

	public String getHzjgmc() {
		return this.hzjgmc;
	}

	public void setHzjgmc(String hzjgmc) {
		this.hzjgmc = hzjgmc;
	}

	public String getHzms() {
		return this.hzms;
	}

	public void setHzms(String hzms) {
		this.hzms = hzms;
	}

	public String getJfjzx() {
		return this.jfjzx;
	}

	public void setJfjzx(String jfjzx) {
		this.jfjzx = jfjzx;
	}

	public String getJgsx() {
		return this.jgsx;
	}

	public void setJgsx(String jgsx) {
		this.jgsx = jgsx;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getJsbz() {
		return this.jsbz;
	}

	public void setJsbz(String jsbz) {
		this.jsbz = jsbz;
	}

	public String getJtnbjgmc() {
		return this.jtnbjgmc;
	}

	public void setJtnbjgmc(String jtnbjgmc) {
		this.jtnbjgmc = jtnbjgmc;
	}

	public String getJzpgpd() {
		return this.jzpgpd;
	}

	public void setJzpgpd(String jzpgpd) {
		this.jzpgpd = jzpgpd;
	}

	public String getJzplpd() {
		return this.jzplpd;
	}

	public void setJzplpd(String jzplpd) {
		this.jzplpd = jzplpd;
	}

	public String getKfpd() {
		return this.kfpd;
	}

	public void setKfpd(String kfpd) {
		this.kfpd = kfpd;
	}

	public String getKshbz() {
		return this.kshbz;
	}

	public void setKshbz(String kshbz) {
		this.kshbz = kshbz;
	}

	public String getKtqzz() {
		return this.ktqzz;
	}

	public void setKtqzz(String ktqzz) {
		this.ktqzz = ktqzz;
	}

	public String getQxsm() {
		return this.qxsm;
	}

	public void setQxsm(String qxsm) {
		this.qxsm = qxsm;
	}

	public String getSfjttj() {
		return this.sfjttj;
	}

	public void setSfjttj(String sfjttj) {
		this.sfjttj = sfjttj;
	}

	public String getSfsx() {
		return this.sfsx;
	}

	public void setSfsx(String sfsx) {
		this.sfsx = sfsx;
	}

	public String getSfxjl() {
		return this.sfxjl;
	}

	public void setSfxjl(String sfxjl) {
		this.sfxjl = sfxjl;
	}

	public String getSlfs() {
		return this.slfs;
	}

	public void setSlfs(String slfs) {
		this.slfs = slfs;
	}

	public String getSsbm() {
		return this.ssbm;
	}

	public void setSsbm(String ssbm) {
		this.ssbm = ssbm;
	}

	public String getStrzz() {
		return this.strzz;
	}

	public void setStrzz(String strzz) {
		this.strzz = strzz;
	}

	public String getSyfs() {
		return this.syfs;
	}

	public void setSyfs(String syfs) {
		this.syfs = syfs;
	}

	public String getTzfw() {
		return this.tzfw;
	}

	public void setTzfw(String tzfw) {
		this.tzfw = tzfw;
	}

	public String getXmclr() {
		return this.xmclr;
	}

	public void setXmclr(String xmclr) {
		this.xmclr = xmclr;
	}

	public String getXmhzly() {
		return this.xmhzly;
	}

	public void setXmhzly(String xmhzly) {
		this.xmhzly = xmhzly;
	}

	public String getXmjlbh() {
		return this.xmjlbh;
	}

	public void setXmjlbh(String xmjlbh) {
		this.xmjlbh = xmjlbh;
	}

	public String getXmtotbs() {
		return this.xmtotbs;
	}

	public void setXmtotbs(String xmtotbs) {
		this.xmtotbs = xmtotbs;
	}

	public String getXmzgbh() {
		return this.xmzgbh;
	}

	public void setXmzgbh(String xmzgbh) {
		this.xmzgbh = xmzgbh;
	}

	public String getXmzzr() {
		return this.xmzzr;
	}

	public void setXmzzr(String xmzzr) {
		this.xmzzr = xmzzr;
	}

	public BigDecimal getXtbcl() {
		return this.xtbcl;
	}

	public void setXtbcl(BigDecimal xtbcl) {
		this.xtbcl = xtbcl;
	}

	public BigDecimal getXtcsclfe() {
		return this.xtcsclfe;
	}

	public void setXtcsclfe(BigDecimal xtcsclfe) {
		this.xtcsclfe = xtcsclfe;
	}

	public String getXtgn() {
		return this.xtgn;
	}

	public void setXtgn(String xtgn) {
		this.xtgn = xtgn;
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

	public BigDecimal getXtxcgm() {
		return this.xtxcgm;
	}

	public void setXtxcgm(BigDecimal xtxcgm) {
		this.xtxcgm = xtxcgm;
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

	public String getXtywfl() {
		return this.xtywfl;
	}

	public void setXtywfl(String xtywfl) {
		this.xtywfl = xtywfl;
	}

	public String getXtzxmbh() {
		return this.xtzxmbh;
	}

	public void setXtzxmbh(String xtzxmbh) {
		this.xtzxmbh = xtzxmbh;
	}

	public String getYjdqr() {
		return this.yjdqr;
	}

	public void setYjdqr(String yjdqr) {
		this.yjdqr = yjdqr;
	}

	public String getYwtz() {
		return this.ywtz;
	}

	public void setYwtz(String ywtz) {
		this.ywtz = ywtz;
	}

	public String getYxfs() {
		return this.yxfs;
	}

	public void setYxfs(String yxfs) {
		this.yxfs = yxfs;
	}

	public BigDecimal getYxlhsyqbl() {
		return this.yxlhsyqbl;
	}

	public void setYxlhsyqbl(BigDecimal yxlhsyqbl) {
		this.yxlhsyqbl = yxlhsyqbl;
	}

	public String getZcglbgpd() {
		return this.zcglbgpd;
	}

	public void setZcglbgpd(String zcglbgpd) {
		this.zcglbgpd = zcglbgpd;
	}

	public BigDecimal getZdyqsyl() {
		return this.zdyqsyl;
	}

	public void setZdyqsyl(BigDecimal zdyqsyl) {
		this.zdyqsyl = zdyqsyl;
	}

	public BigDecimal getZgyqsyl() {
		return this.zgyqsyl;
	}

	public void setZgyqsyl(BigDecimal zgyqsyl) {
		this.zgyqsyl = zgyqsyl;
	}

	public String getZtjd() {
		return this.ztjd;
	}

	public void setZtjd(String ztjd) {
		this.ztjd = ztjd;
	}

	public String getZxjglx() {
		return this.zxjglx;
	}

	public void setZxjglx(String zxjglx) {
		this.zxjglx = zxjglx;
	}

	public String getZxxs() {
		return this.zxxs;
	}

	public void setZxxs(String zxxs) {
		this.zxxs = zxxs;
	}

}