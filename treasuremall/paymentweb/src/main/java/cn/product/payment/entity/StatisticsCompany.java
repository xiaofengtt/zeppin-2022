package cn.product.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class StatisticsCompany implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7081999072494722201L;
	
    private String uuid;
	private String dailyDate;
	private String company;
	private String companyChannel;
	private String channel;
	private String type;
    private BigDecimal totalAmount;
    private BigDecimal poundage;
    private BigDecimal amount;
    private Timestamp createtime;
    
    public class StatisticsCompanyType{
    	public final static String USER_WITHDRAW = "user_withdraw";
    	public final static String USER_RECHARGE = "user_recharge";
    	public final static String COMPANY_WITHDRAW = "company_withdraw";
    	public final static String COMPANY_RECHARGE = "company_recharge";
    }
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyChannel() {
		return companyChannel;
	}

	public void setCompanyChannel(String companyChannel) {
		this.companyChannel = companyChannel;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}