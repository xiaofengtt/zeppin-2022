package cn.zeppin.product.utility.fuqianla.data;

import java.io.Serializable;

import cn.zeppin.product.utility.fuqianla.data.base.RequestData;

/**
 * 付款请求Bean
 * @author biaoli4
 */
public class SingleReceiptQueryRequest implements RequestData, Serializable  {
	private static final long serialVersionUID = 8994918602525129240L;

	private String versionNo;// 
	private String channelId;// 业务渠道ID:结算平台分配给渠道的ID
	private String merchId;// 商户ID(新结算平台分配)
	private String bizId;// 商户订单号，保证商户端唯一，长度不大于128个字符，由数字0-9，字母大小写a-z组成，不支持特殊字符和汉字
	private String sendTime;// 格式：YYYYMMDDHHMMSS,不足14位时，添加0补足14位
	/*private String signInfo;// 所有提交信息组织的MD5签名结果
*/
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
		return "SingleReceiptQueryRequest [versionNo=" + versionNo + ", channelId=" + channelId + ", merchId=" + merchId
				+ ", bizId=" + bizId + ", sendTime=" + sendTime + "]";
	}

/*	public String getSignInfo() {
		return signInfo;
	}

	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}*/

}