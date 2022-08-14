package cn.zeppin.product.utility.fuqianla.data;

import java.io.Serializable;

import cn.zeppin.product.utility.fuqianla.data.base.RequestData;

/**
 * 付款请求Bean
 * @author biaoli4
 */
public class SinglePaymentRequest implements RequestData, Serializable  {
	private static final long serialVersionUID = 7447122398545900003L;

	private String versionNo;//
	private String acqId;// 0 Web，1 POS，2 APP
	private String channelId;// 业务渠道ID:结算平台分配给渠道的ID
	private String merchId;// 商户ID(新结算平台分配)
	private String bizId;// 商户订单号，保证商户端唯一，长度不大于32个字符，由数字0-9，字母大小写a-z组成，不支持特殊字符和汉字。
	private String paymentTp;// 支付类型：参见枚举类型:支付类型
	private String loanProtocol;//	借款协议号
	private String bankLineNo;//	联行号
	private String bizTp;// 业务类型
	private String accountFlag;// 0卡，1存折， 不填写值默认是0
	private String accountType;// 账户类型0个人1公司（默认对私）
	private String sendTime;// 格式：YYYYMMDDHHMMSS,不足14位时，添加0补足14位
	private String ccy;// 币种：参见枚举类型5.2。默认是人民币
	private String bankId;// 银行编码参见枚举类型5.3，
	private Double amount;// 支付金额，单位：分
	private String identType;
	private String identNo;
	private String accountName;// 银行账户开户名称
	private String accountNo;// 转入卡或存折号
	private String province;// 详情参考枚举类型:地区编码，
	private String city;// 详情参考枚举类型:地区编码。
	private String pmtChnTp;// 支付渠道类型，用于处理唐宁，及补单交易：填BANK（指的唐宁卡或别的银行卡付款），THIRD（指的第三方接口代付）或MANUAL（线下付款再倒入）
	private String assgCdtMop;// 指定的路由，当为PmtChnTp为MANUAL时必填
	private String exMerchId;//	通道商户号
	private String mobileNum;// 用户的手机号码
	private String openBankName;// 开户行名称，PmtChnTp为bank时必填，其余时候非必填
	private String account;//	提现虚账户
	private String notifyUrl;// 通知地址
	private String productInfo;// 商品名称，
	private Integer productNum;// 商品数量，必须为整数
	private String description;// 对于商品的具体描述
	private String remark;// 商户的备注信息,原样返回
	private String extend1;//扩展字段1
	private String extend2;//扩展字段2
	/*private String signInfo;// 所有提交信息组织的MD5签名结果
*/
	public String getLoanProtocol() {
		return loanProtocol;
	}

	public void setLoanProtocol(String loanProtocol) {
		this.loanProtocol = loanProtocol;
	}

	public String getExMerchId() {
		return exMerchId;
	}

	public void setExMerchId(String exMerchId) {
		this.exMerchId = exMerchId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getAcqId() {
		return acqId;
	}

	public void setAcqId(String acqId) {
		this.acqId = acqId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
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

	public String getPaymentTp() {
		return paymentTp;
	}

	public void setPaymentTp(String paymentTp) {
		this.paymentTp = paymentTp;
	}

	public String getBizTp() {
		return bizTp;
	}

	public void setBizTp(String bizTp) {
		this.bizTp = bizTp;
	}

	public String getAccountFlag() {
		return accountFlag;
	}

	public void setAccountFlag(String accountFlag) {
		this.accountFlag = accountFlag;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getIdentType() {
		return identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIdentNo() {
		return identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getPmtChnTp() {
		return pmtChnTp;
	}

	public void setPmtChnTp(String pmtChnTp) {
		this.pmtChnTp = pmtChnTp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAssgCdtMop() {
		return assgCdtMop;
	}

	public void setAssgCdtMop(String assgCdtMop) {
		this.assgCdtMop = assgCdtMop;
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

	public String getBankLineNo() {
		return bankLineNo;
	}

	public void setBankLineNo(String bankLineNo) {
		this.bankLineNo = bankLineNo;
	}

	@Override
	public String toString() {
		return "SinglePaymentRequest [versionNo=" + versionNo + ", acqId=" + acqId + ", channelId=" + channelId
				+ ", merchId=" + merchId + ", bizId=" + bizId + ", paymentTp=" + paymentTp + ", loanProtocol="
				+ loanProtocol + ", bankLineNo=" + bankLineNo + ", bizTp=" + bizTp + ", accountFlag=" + accountFlag
				+ ", accountType=" + accountType + ", sendTime=" + sendTime + ", ccy=" + ccy + ", bankId=" + bankId
				+ ", amount=" + amount + ", identType=" + identType + ", identNo=" + identNo + ", accountName="
				+ accountName + ", accountNo=" + accountNo + ", province=" + province + ", city=" + city + ", pmtChnTp="
				+ pmtChnTp + ", assgCdtMop=" + assgCdtMop + ", exMerchId=" + exMerchId + ", mobileNum=" + mobileNum
				+ ", openBankName=" + openBankName + ", account=" + account + ", notifyUrl=" + notifyUrl
				+ ", productInfo=" + productInfo + ", productNum=" + productNum + ", description=" + description
				+ ", remark=" + remark + ", extend1=" + extend1 + ", extend2=" + extend2 + "]";
	}
	
}
