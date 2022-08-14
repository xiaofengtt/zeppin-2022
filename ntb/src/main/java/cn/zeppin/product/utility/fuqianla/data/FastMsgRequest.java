package cn.zeppin.product.utility.fuqianla.data;

import java.io.Serializable;

import cn.zeppin.product.utility.fuqianla.data.base.RequestData;

public class FastMsgRequest implements RequestData,Serializable{
    private final static long serialVersionUID = -1;

    private String versionNo;
    private String acqId;
    private String channelId;
    private String merchId;
    private String userID;
    private String txnTp;
    private String customerName;
    private String identType;
    private String identNo;
    private String accountNo;
    private String reqId;
    private String cvvNum;
    private String valDate;
    private String mobileNum;
    private String paymentTp;
    private double amount;
    private String cardTp;
    private String authId;
    private String sendTime;
    private String ccy;
    private String bankId;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTxnTp() {
        return txnTp;
    }

    public void setTxnTp(String txnTp) {
        this.txnTp = txnTp;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
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

    public String getPaymentTp() {
        return paymentTp;
    }

    public void setPaymentTp(String paymentTp) {
        this.paymentTp = paymentTp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCardTp() {
        return cardTp;
    }

    public void setCardTp(String cardTp) {
        this.cardTp = cardTp;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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
        return "FastMsgRequest{" +
                "versionNo='" + versionNo + '\'' +
                ", acqId='" + acqId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", userID='" + userID + '\'' +
                ", txnTp='" + txnTp + '\'' +
                ", customerName='" + customerName + '\'' +
                ", identType='" + identType + '\'' +
                ", identNo='" + identNo + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", reqId='" + reqId + '\'' +
                ", cvvNum='" + cvvNum + '\'' +
                ", valDate='" + valDate + '\'' +
                ", mobileNum='" + mobileNum + '\'' +
                ", paymentTp='" + paymentTp + '\'' +
                ", amount=" + amount +
                ", cardTp='" + cardTp + '\'' +
                ", authId='" + authId + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", ccy='" + ccy + '\'' +
                ", bankId='" + bankId + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                '}';
    }
}
