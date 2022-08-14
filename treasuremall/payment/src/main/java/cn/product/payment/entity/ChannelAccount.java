package cn.product.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class ChannelAccount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5756411069757313927L;
	
	@Id
    private String uuid;
	private String channel;
	private String name;
	private String accountNum;
    private String data;
    private BigDecimal balance;
    private BigDecimal poundage;
    private BigDecimal poundageRate;
    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal dailyMax;
    private BigDecimal totalMax;
    private String type;
    private String status;
    private Timestamp createtime;
    
    public class ChannelAccountStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
    }
    
    public class ChannelAccountType{
    	public final static String WITHDRAW = "withdraw";
    	public final static String RECHARGE = "recharge";
    }
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public BigDecimal getPoundageRate() {
		return poundageRate;
	}

	public void setPoundageRate(BigDecimal poundageRate) {
		this.poundageRate = poundageRate;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getDailyMax() {
		return dailyMax;
	}

	public void setDailyMax(BigDecimal dailyMax) {
		this.dailyMax = dailyMax;
	}

	public BigDecimal getTotalMax() {
		return totalMax;
	}

	public void setTotalMax(BigDecimal totalMax) {
		this.totalMax = totalMax;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}