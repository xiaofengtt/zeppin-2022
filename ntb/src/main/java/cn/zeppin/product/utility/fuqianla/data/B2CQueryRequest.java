package cn.zeppin.product.utility.fuqianla.data;

import java.io.Serializable;

import cn.zeppin.product.utility.fuqianla.data.base.RequestData;

/**
 * @Author: Sherlock Blaze
 * @Description:
 * @Date: Create in 17:49 26/09/2017
 * @Modified By:
 */
public class B2CQueryRequest implements RequestData,Serializable{
    private static final long serialVersionUID = -5466768507716933847L;

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
        return "B2CQueryRequest{" +
                "versionNo='" + versionNo + '\'' +
                ", channelId='" + channelId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", bizId='" + bizId + '\'' +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }
}
