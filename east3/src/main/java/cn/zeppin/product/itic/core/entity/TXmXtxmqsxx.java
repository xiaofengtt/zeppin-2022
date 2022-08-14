package cn.zeppin.product.itic.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;


/**
 * The persistent class for the T_XM_XTXMQSXX database table.
 * 
 */
@Entity
@Table(name="T_XM_XTXMQSXX")
public class TXmXtxmqsxx extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Timestamp ctltime;
	private String cjrq;

	private String jrxkzh;

	private BigDecimal pfje;

	private String qsrq;

	private String sfaydrqqs;

	private BigDecimal shesuje;

	private BigDecimal sjsy;

	private BigDecimal sjsyl;

	private BigDecimal sjxtbcl;

	private BigDecimal ssxtye;

	private BigDecimal strljjbbc;

	private BigDecimal strljyjbc;

	private BigDecimal sunshie;

	private BigDecimal tzgwljjbbc;

	private BigDecimal tzgwljyjbc;

	private BigDecimal xtbjljjfe;

	private BigDecimal xtfy;

	private BigDecimal xtfyl;

	private String xtjgdm;

	private String xtjgmc;

	private BigDecimal xtsyljfpe;

	private String xtxmbm;

	private String xtzxmbmh;

	public TXmXtxmqsxx() {
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

	public String getJrxkzh() {
		return this.jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public BigDecimal getPfje() {
		return this.pfje;
	}

	public void setPfje(BigDecimal pfje) {
		this.pfje = pfje;
	}

	public String getQsrq() {
		return this.qsrq;
	}

	public void setQsrq(String qsrq) {
		this.qsrq = qsrq;
	}

	public String getSfaydrqqs() {
		return this.sfaydrqqs;
	}

	public void setSfaydrqqs(String sfaydrqqs) {
		this.sfaydrqqs = sfaydrqqs;
	}

	public BigDecimal getShesuje() {
		return this.shesuje;
	}

	public void setShesuje(BigDecimal shesuje) {
		this.shesuje = shesuje;
	}

	public BigDecimal getSjsy() {
		return this.sjsy;
	}

	public void setSjsy(BigDecimal sjsy) {
		this.sjsy = sjsy;
	}

	public BigDecimal getSjsyl() {
		return this.sjsyl;
	}

	public void setSjsyl(BigDecimal sjsyl) {
		this.sjsyl = sjsyl;
	}

	public BigDecimal getSjxtbcl() {
		return this.sjxtbcl;
	}

	public void setSjxtbcl(BigDecimal sjxtbcl) {
		this.sjxtbcl = sjxtbcl;
	}

	public BigDecimal getSsxtye() {
		return this.ssxtye;
	}

	public void setSsxtye(BigDecimal ssxtye) {
		this.ssxtye = ssxtye;
	}

	public BigDecimal getStrljjbbc() {
		return this.strljjbbc;
	}

	public void setStrljjbbc(BigDecimal strljjbbc) {
		this.strljjbbc = strljjbbc;
	}

	public BigDecimal getStrljyjbc() {
		return this.strljyjbc;
	}

	public void setStrljyjbc(BigDecimal strljyjbc) {
		this.strljyjbc = strljyjbc;
	}

	public BigDecimal getSunshie() {
		return this.sunshie;
	}

	public void setSunshie(BigDecimal sunshie) {
		this.sunshie = sunshie;
	}

	public BigDecimal getTzgwljjbbc() {
		return this.tzgwljjbbc;
	}

	public void setTzgwljjbbc(BigDecimal tzgwljjbbc) {
		this.tzgwljjbbc = tzgwljjbbc;
	}

	public BigDecimal getTzgwljyjbc() {
		return this.tzgwljyjbc;
	}

	public void setTzgwljyjbc(BigDecimal tzgwljyjbc) {
		this.tzgwljyjbc = tzgwljyjbc;
	}

	public BigDecimal getXtbjljjfe() {
		return this.xtbjljjfe;
	}

	public void setXtbjljjfe(BigDecimal xtbjljjfe) {
		this.xtbjljjfe = xtbjljjfe;
	}

	public BigDecimal getXtfy() {
		return this.xtfy;
	}

	public void setXtfy(BigDecimal xtfy) {
		this.xtfy = xtfy;
	}

	public BigDecimal getXtfyl() {
		return this.xtfyl;
	}

	public void setXtfyl(BigDecimal xtfyl) {
		this.xtfyl = xtfyl;
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

	public BigDecimal getXtsyljfpe() {
		return this.xtsyljfpe;
	}

	public void setXtsyljfpe(BigDecimal xtsyljfpe) {
		this.xtsyljfpe = xtsyljfpe;
	}

	public String getXtxmbm() {
		return this.xtxmbm;
	}

	public void setXtxmbm(String xtxmbm) {
		this.xtxmbm = xtxmbm;
	}

	public String getXtzxmbmh() {
		return this.xtzxmbmh;
	}

	public void setXtzxmbmh(String xtzxmbmh) {
		this.xtzxmbmh = xtzxmbmh;
	}

}