package cn.product.score.util.reapal.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4358310568897110866L;

	private Integer index;//
	private String bankcard;
	private String realname;
	private String bankname;
	private String branchbank;
	private String branchbanks;
	private String properties;
	private BigDecimal price;
	private String currency;
	private String province;
	private String city;
	private String mobile;
	private String idtype;
	private String idcard;
	private String protocol;
	private String ordernum;
	private String remark;
	
	public class WithdrawDateProperties {
		public static final String PUBLIC = "公";
		public static final String PRIVATE = "私";
	}
	public class WithdrawDateCurrency {
		public static final String CNY = "CNY";
	}
	
	public WithdrawData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WithdrawData(Integer index, String bankcard, String realname,
			String bankname, String branchbank, String branchbanks,
			String properties, BigDecimal price, String currency,
			String province, String city, String mobile, String idtype,
			String idcard, String protocol, String ordernum, String remark) {
		super();
		this.index = index;
		this.bankcard = bankcard;
		this.realname = realname;
		this.bankname = bankname;
		this.branchbank = branchbank;
		this.branchbanks = branchbanks;
		this.properties = properties;
		this.price = price;
		this.currency = currency;
		this.province = province;
		this.city = city;
		this.mobile = mobile;
		this.idtype = idtype;
		this.idcard = idcard;
		this.protocol = protocol;
		this.ordernum = ordernum;
		this.remark = remark;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getBankcard() {
		return bankcard;
	}
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBranchbank() {
		return branchbank;
	}
	public void setBranchbank(String branchbank) {
		this.branchbank = branchbank;
	}
	public String getBranchbanks() {
		return branchbanks;
	}
	public void setBranchbanks(String branchbanks) {
		this.branchbanks = branchbanks;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdtype() {
		return idtype;
	}
	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return index + "," + bankcard
				+ "," + realname + "," + bankname
				+ "," + branchbank + "," + branchbanks
				+ "," + properties + "," + price
				+ "," + currency + "," + province
				+ "," + city + "," + mobile + ","
				+ idtype + "," + idcard + "," + protocol
				+ "," + ordernum + "," + remark;
	}
	
}
