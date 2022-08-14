package cn.zeppin.product.utility.fuqianla.json;

import java.io.Serializable;

import cn.zeppin.product.utility.fuqianla.data.base.RequestData;

public class BatchPaymentQueryReqData implements Serializable, RequestData{
    private final static long serialVersionUID = 439194299134L;

    private String versionNo;
    private String channelId;
    private String merchId;
    private String bizId;
    private String sendTime;

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
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

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "BatchPaymentQueryReqData{" +
                "versionNo='" + versionNo + '\'' +
                ", channelId='" + channelId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", bizId='" + bizId + '\'' +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }
}
