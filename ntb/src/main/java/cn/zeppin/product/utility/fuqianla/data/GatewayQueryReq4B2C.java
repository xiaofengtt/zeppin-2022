package cn.zeppin.product.utility.fuqianla.data;

import java.io.Serializable;

import cn.zeppin.product.utility.fuqianla.data.base.RequestData;

/**
 * b2c 订单查询请求bean
 * @author biaoli4
 *
 */
public class GatewayQueryReq4B2C implements Serializable,RequestData{
	private static final long serialVersionUID = 6881869319471532728L;
	
	// 接口版本号
	private String versionNo;
	// 业务渠道id
	private String channelId;
	// 商户id
	private String merchId;
	// 商户订单号
	private String bizId;
	// 提交时间
	private String sendTime;
	// 签名信息
	private String signInfo;
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
	public String getSignInfo() {
		return signInfo;
	}
	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}

	@Override
	public String toString() {
		return "GatewayQueryReq4B2C{" +
				"versionNo='" + versionNo + '\'' +
				", channelId='" + channelId + '\'' +
				", merchId='" + merchId + '\'' +
				", bizId='" + bizId + '\'' +
				", sendTime='" + sendTime + '\'' +
				", signInfo='" + signInfo + '\'' +
				'}';
	}
}
