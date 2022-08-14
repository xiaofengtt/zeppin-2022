package cn.zeppin.product.utility.fuqianla.data;

import java.io.Serializable;

import cn.zeppin.product.utility.fuqianla.data.base.RequestData;

public class FastTradeCommitRequest implements RequestData,Serializable{
    private final static long serialVersionUID = -3;

    private String versionNo;
    private String acqId;
    private String channelId;
    private String merchId;
    private String userID;
    private String customerName;
    private String identType;
    private String identNo;
    private String bizId;
    private String paymentTp;
    private String tokenCd;
    private String smsFlag;
    private String loanProtocol;
    private String bizTp;
    private String cardTp;
    private String sendTime;
    private String accountNo;
    private String cvvNum;
    private String valDate;
    private String mobileNum;
    private String ccy;
    private String bankId;
    private String authId;
    private String smsCode;
    private String account;
    private double amount;
    private String notifyUrl;
    private String userIP;
    private String productInfo;
    private int productNum;
    private String description;
    private String extend1;
    private String extent2;

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getTokenCd() {
        return tokenCd;
    }

    public void setTokenCd(String tokenCd) {
        this.tokenCd = tokenCd;
    }

    public String getSmsFlag() {
        return smsFlag;
    }

    public void setSmsFlag(String smsFlag) {
        this.smsFlag = smsFlag;
    }

    public String getLoanProtocol() {
        return loanProtocol;
    }

    public void setLoanProtocol(String loanProtocol) {
        this.loanProtocol = loanProtocol;
    }

    public String getBizTp() {
        return bizTp;
    }

    public void setBizTp(String bizTp) {
        this.bizTp = bizTp;
    }

    public String getCardTp() {
        return cardTp;
    }

    public void setCardTp(String cardTp) {
        this.cardTp = cardTp;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCvvNum() {
        return cvvNum;
    }

    public void setCvvNum(String cvvNum) {
        this.cvvNum = cvvNum;
    }

    public String getValDate() {
        return valDate;
    }

    public void setValDate(String valDate) {
        this.valDate = valDate;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
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

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtent2() {
        return extent2;
    }

    public void setExtent2(String extent2) {
        this.extent2 = extent2;
    }

    @Override
    public String toString() {
        return "FastTradeCommitRequest{" +
                "versionNo='" + versionNo + '\'' +
                ", acqId='" + acqId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", userID='" + userID + '\'' +
                ", customerName='" + customerName + '\'' +
                ", identType='" + identType + '\'' +
                ", identNo='" + identNo + '\'' +
                ", bizId='" + bizId + '\'' +
                ", paymentTp='" + paymentTp + '\'' +
                ", tokenCd='" + tokenCd + '\'' +
                ", smsFlag='" + smsFlag + '\'' +
                ", loanProtocol='" + loanProtocol + '\'' +
                ", bizTp='" + bizTp + '\'' +
                ", cardTp='" + cardTp + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", cvvNum='" + cvvNum + '\'' +
                ", valDate='" + valDate + '\'' +
                ", mobileNum='" + mobileNum + '\'' +
                ", ccy='" + ccy + '\'' +
                ", bankId='" + bankId + '\'' +
                ", authId='" + authId + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", account='" + account + '\'' +
                ", amount=" + amount +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", userIP='" + userIP + '\'' +
                ", productInfo='" + productInfo + '\'' +
                ", productNum=" + productNum +
                ", description='" + description + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extent2='" + extent2 + '\'' +
                '}';
    }
}
