package cn.zeppin.product.utility.fuqianla.data;

import java.io.Serializable;

import cn.zeppin.product.utility.fuqianla.data.base.RequestData;

/**
 * 收款请求Bean
 *
 * @author biaoli4
 */
public class BatchReceiptRequest implements Serializable, RequestData {
    private static final long serialVersionUID = -4953568444214539019L;

    private String versionNo;//
    private String acqId;// 0 Web，1 POS，2 APP
    private String channelId;// 业务渠道ID:结算平台分配给渠道的ID
    private String merchId;// 商户ID(新结算平台分配)
    private String bizId;// 商户订单号，保证商户端唯一，长度不大于32个字符，由数字0-9，字母大小写a-z组成，不支持特殊字符和汉字。
    private String paymentTp;// 支付类型：参见枚举类型:支付类型
    private String pmtChnTp;// 支付渠道类型
    private String assgCdtMop;// 指定路由
    private String exMerchId;
    private String loanProtocol;
    private String investProtocol;
    private String splitFlg;
    private String bizTp;
    private String reserveFlag;
    private String sendTime;
    private String reserveTime;
    private String accountFlag;
    private String accountType;
    private String ccy;
    private String bankId;
    private double amount;
    private String accountName;
    private String identType;
    private String identNo;
    private String mobileNum;
    private String accountNo;
    private String account;
    private String notifyUrl;
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

    public String getPmtChnTp() {
        return pmtChnTp;
    }

    public void setPmtChnTp(String pmtChnTp) {
        this.pmtChnTp = pmtChnTp;
    }

    public String getAssgCdtMop() {
        return assgCdtMop;
    }

    public void setAssgCdtMop(String assgCdtMop) {
        this.assgCdtMop = assgCdtMop;
    }

    public String getExMerchId() {
        return exMerchId;
    }

    public void setExMerchId(String exMerchId) {
        this.exMerchId = exMerchId;
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

    public String getSplitFlg() {
        return splitFlg;
    }

    public void setSplitFlg(String splitFlg) {
        this.splitFlg = splitFlg;
    }

    public String getBizTp() {
        return bizTp;
    }

    public void setBizTp(String bizTp) {
        this.bizTp = bizTp;
    }

    public String getReserveFlag() {
        return reserveFlag;
    }

    public void setReserveFlag(String reserveFlag) {
        this.reserveFlag = reserveFlag;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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
        return "BatchReceiptRequest{" +
                "versionNo='" + versionNo + '\'' +
                ", acqId='" + acqId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", bizId='" + bizId + '\'' +
                ", paymentTp='" + paymentTp + '\'' +
                ", pmtChnTp='" + pmtChnTp + '\'' +
                ", assgCdtMop='" + assgCdtMop + '\'' +
                ", exMerchId='" + exMerchId + '\'' +
                ", loanProtocol='" + loanProtocol + '\'' +
                ", investProtocol='" + investProtocol + '\'' +
                ", splitFlg='" + splitFlg + '\'' +
                ", bizTp='" + bizTp + '\'' +
                ", reserveFlag='" + reserveFlag + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", reserveTime='" + reserveTime + '\'' +
                ", accountFlag='" + accountFlag + '\'' +
                ", accountType='" + accountType + '\'' +
                ", ccy='" + ccy + '\'' +
                ", bankId='" + bankId + '\'' +
                ", amount=" + amount +
                ", accountName='" + accountName + '\'' +
                ", identType='" + identType + '\'' +
                ", identNo='" + identNo + '\'' +
                ", mobileNum='" + mobileNum + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", account='" + account + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", productInfo='" + productInfo + '\'' +
                ", productNum=" + productNum +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                '}';
    }
}
