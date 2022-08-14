package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_XTDZYWB database table.
 * 
 */
@Entity
@Table(name="T_XM_XTDZYWB")
public class TXmXtdzywb extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String bwhsksrq;

	private String bxdh;

	private String bz;

	private String cjrq;

	private String czjg;

	private String dbdqrq;

	private String dbhth;

	private String dbqsrq;

	private String djjg;

	private String djrq;

	private String djyxzzrq;

	private String dywsyqrmc;

	private String hbrq;

	private String jrxkzh;

	private String pgjgmc;

	private BigDecimal pgjz;

	private String pgrq;

	private String qzdjhm;

	private String qzmc;

	private String qzyxdqrq;

	private String sfnrbwhs;

	private String swsqrq;

	private String xtjgdm;

	private String xtjgmc;

	private String xtxmbm;

	private String xtzxmbm;

	private BigDecimal ydyjz;

	private BigDecimal yxrdjz;

	private BigDecimal zhdyl;

	private String zhdywbh;

	private String zhdywlx;

	private String zhdywmc;

	private BigDecimal zhdywzmjz;

	private String zypzhm;

	private BigDecimal zypzje;

	private String zypzklrq;

	private String zypzlx;

	private String zypzqfjg;

	private String zypzzh;

	private String zywbgfs;

	private String zywpgfs;

	public TXmXtdzywb() {
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

	public String getBwhsksrq() {
		return this.bwhsksrq;
	}

	public void setBwhsksrq(String bwhsksrq) {
		this.bwhsksrq = bwhsksrq;
	}

	public String getBxdh() {
		return this.bxdh;
	}

	public void setBxdh(String bxdh) {
		this.bxdh = bxdh;
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

	public String getCzjg() {
		return this.czjg;
	}

	public void setCzjg(String czjg) {
		this.czjg = czjg;
	}

	public String getDbdqrq() {
		return this.dbdqrq;
	}

	public void setDbdqrq(String dbdqrq) {
		this.dbdqrq = dbdqrq;
	}

	public String getDbhth() {
		return this.dbhth;
	}

	public void setDbhth(String dbhth) {
		this.dbhth = dbhth;
	}

	public String getDbqsrq() {
		return this.dbqsrq;
	}

	public void setDbqsrq(String dbqsrq) {
		this.dbqsrq = dbqsrq;
	}

	public String getDjjg() {
		return this.djjg;
	}

	public void setDjjg(String djjg) {
		this.djjg = djjg;
	}

	public String getDjrq() {
		return this.djrq;
	}

	public void setDjrq(String djrq) {
		this.djrq = djrq;
	}

	public String getDjyxzzrq() {
		return this.djyxzzrq;
	}

	public void setDjyxzzrq(String djyxzzrq) {
		this.djyxzzrq = djyxzzrq;
	}

	public String getDywsyqrmc() {
		return this.dywsyqrmc;
	}

	public void setDywsyqrmc(String dywsyqrmc) {
		this.dywsyqrmc = dywsyqrmc;
	}

	public String getHbrq() {
		return this.hbrq;
	}

	public void setHbrq(String hbrq) {
		this.hbrq = hbrq;
	}

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getPgjgmc() {
		return this.pgjgmc;
	}

	public void setPgjgmc(String pgjgmc) {
		this.pgjgmc = pgjgmc;
	}

	public BigDecimal getPgjz() {
		return this.pgjz;
	}

	public void setPgjz(BigDecimal pgjz) {
		this.pgjz = pgjz;
	}

	public String getPgrq() {
		return this.pgrq;
	}

	public void setPgrq(String pgrq) {
		this.pgrq = pgrq;
	}

	public String getQzdjhm() {
		return this.qzdjhm;
	}

	public void setQzdjhm(String qzdjhm) {
		this.qzdjhm = qzdjhm;
	}

	public String getQzmc() {
		return this.qzmc;
	}

	public void setQzmc(String qzmc) {
		this.qzmc = qzmc;
	}

	public String getQzyxdqrq() {
		return this.qzyxdqrq;
	}

	public void setQzyxdqrq(String qzyxdqrq) {
		this.qzyxdqrq = qzyxdqrq;
	}

	public String getSfnrbwhs() {
		return this.sfnrbwhs;
	}

	public void setSfnrbwhs(String sfnrbwhs) {
		this.sfnrbwhs = sfnrbwhs;
	}

	public String getSwsqrq() {
		return this.swsqrq;
	}

	public void setSwsqrq(String swsqrq) {
		this.swsqrq = swsqrq;
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

	public String getXtzxmbm() {
		return this.xtzxmbm;
	}

	public void setXtzxmbm(String xtzxmbm) {
		this.xtzxmbm = xtzxmbm;
	}

	public BigDecimal getYdyjz() {
		return this.ydyjz;
	}

	public void setYdyjz(BigDecimal ydyjz) {
		this.ydyjz = ydyjz;
	}

	public BigDecimal getYxrdjz() {
		return this.yxrdjz;
	}

	public void setYxrdjz(BigDecimal yxrdjz) {
		this.yxrdjz = yxrdjz;
	}

	public BigDecimal getZhdyl() {
		return this.zhdyl;
	}

	public void setZhdyl(BigDecimal zhdyl) {
		this.zhdyl = zhdyl;
	}

	public String getZhdywbh() {
		return this.zhdywbh;
	}

	public void setZhdywbh(String zhdywbh) {
		this.zhdywbh = zhdywbh;
	}

	public String getZhdywlx() {
		return this.zhdywlx;
	}

	public void setZhdywlx(String zhdywlx) {
		this.zhdywlx = zhdywlx;
	}

	public String getZhdywmc() {
		return this.zhdywmc;
	}

	public void setZhdywmc(String zhdywmc) {
		this.zhdywmc = zhdywmc;
	}

	public BigDecimal getZhdywzmjz() {
		return this.zhdywzmjz;
	}

	public void setZhdywzmjz(BigDecimal zhdywzmjz) {
		this.zhdywzmjz = zhdywzmjz;
	}

	public String getZypzhm() {
		return this.zypzhm;
	}

	public void setZypzhm(String zypzhm) {
		this.zypzhm = zypzhm;
	}

	public BigDecimal getZypzje() {
		return this.zypzje;
	}

	public void setZypzje(BigDecimal zypzje) {
		this.zypzje = zypzje;
	}

	public String getZypzklrq() {
		return this.zypzklrq;
	}

	public void setZypzklrq(String zypzklrq) {
		this.zypzklrq = zypzklrq;
	}

	public String getZypzlx() {
		return this.zypzlx;
	}

	public void setZypzlx(String zypzlx) {
		this.zypzlx = zypzlx;
	}

	public String getZypzqfjg() {
		return this.zypzqfjg;
	}

	public void setZypzqfjg(String zypzqfjg) {
		this.zypzqfjg = zypzqfjg;
	}

	public String getZypzzh() {
		return this.zypzzh;
	}

	public void setZypzzh(String zypzzh) {
		this.zypzzh = zypzzh;
	}

	public String getZywbgfs() {
		return this.zywbgfs;
	}

	public void setZywbgfs(String zywbgfs) {
		this.zywbgfs = zywbgfs;
	}

	public String getZywpgfs() {
		return this.zywpgfs;
	}

	public void setZywpgfs(String zywpgfs) {
		this.zywpgfs = zywpgfs;
	}

}