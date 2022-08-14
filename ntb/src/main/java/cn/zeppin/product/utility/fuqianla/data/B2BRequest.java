package cn.zeppin.product.utility.fuqianla.data;


import java.io.Serializable;

import cn.zeppin.product.utility.fuqianla.data.base.RequestData;

/**
 * @author Sherlock Blaze
 * @date 05/11/2017-12:24 AM
 * @description
 */
public class B2BRequest implements RequestData,Serializable{

    private static final long serialVersionUID = -3592847370940347527L;

    private String versionNo;
    private String acqId;
    private String channelId;
    private String merchId;
    private String account;
    private String bizId;
    private String paymentTp;
    private String bizTp;
    private String cardTp;
    private String sendTime;
    private String ccy;
    private String loanProtocol;
    private String investProtocol;
    private String bankId;
    private double amount;
    private String customerName;
    private String identType;
    private String identNo;
    private String returnUrl;
    private String notifyUrl;
    private String userIp;
    private String productInfo;
    private int productNum;
    private String description;
    private String remark;
    private String extend1;
    private String extend2;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getLoanProtocol() {
        return loanProtocol;
    }

    public void setLoanProtocol(String loanProtocol) {
        this.loanProtocol = loanProtocol;
    }

    public String getInvestProtocol() {
        return investProtocol;
    }

    public void setInvestProtocol(String investProtocol) {
        this.investProtocol = investProtocol;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "B2BRequest{" +
                "versionNo='" + versionNo + '\'' +
                ", acqId='" + acqId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", account='" + account + '\'' +
                ", bizId='" + bizId + '\'' +
                ", paymentTp='" + paymentTp + '\'' +
                ", bizTp='" + bizTp + '\'' +
                ", cardTp='" + cardTp + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", ccy='" + ccy + '\'' +
                ", loanProtocol='" + loanProtocol + '\'' +
                ", investProtocol='" + investProtocol + '\'' +
                ", bankId='" + bankId + '\'' +
                ", amount=" + amount +
                ", customerName='" + customerName + '\'' +
                ", identType='" + identType + '\'' +
                ", identNo='" + identNo + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", userIp='" + userIp + '\'' +
                ", productInfo='" + productInfo + '\'' +
                ", productNum=" + productNum +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                '}';
    }
}
