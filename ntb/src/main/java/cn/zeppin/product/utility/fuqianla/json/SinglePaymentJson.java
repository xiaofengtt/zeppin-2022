package cn.zeppin.product.utility.fuqianla.json;

import java.io.Serializable;

/**
 * 付款请求Bean
 * @author biaoli4
 */
public class SinglePaymentJson implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7829154415386045889L;
	
	private String merchId;
	private String bizId;
	private String account;
	private String respCode;
	private String respDescription;
	private String state;
	private Double amount;
	private Double actSttlmAmt;
	private String txId;
	private String completeTime;
	private String failReason;
	private String cdtMop;
	private String exMerchId;
	private String splitFlag;
	private String settleDate ;
	private String extend1;
	private String extend2;
	private String signInfo;
	
	
	public SinglePaymentJson() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDescription() {
		return respDescription;
	}
	public void setRespDescription(String respDescription) {
		this.respDescription = respDescription;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getActSttlmAmt() {
		return actSttlmAmt;
	}
	public void setActSttlmAmt(Double actSttlmAmt) {
		this.actSttlmAmt = actSttlmAmt;
	}
	public String getTxId() {
		return txId;
	}
	public void setTxId(String txId) {
		this.txId = txId;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getCdtMop() {
		return cdtMop;
	}
	public void setCdtMop(String cdtMop) {
		this.cdtMop = cdtMop;
	}
	public String getExMerchId() {
		return exMerchId;
	}
	public void setExMerchId(String exMerchId) {
		this.exMerchId = exMerchId;
	}
	public String getSplitFlag() {
		return splitFlag;
	}
	public void setSplitFlag(String splitFlag) {
		this.splitFlag = splitFlag;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getExtend1() {
		return extend1;
	}
	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}
	public String getExtend2() {
		return extend2;
	}
	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}
	public String getSignInfo() {
		return signInfo;
	}
	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}
	
}
