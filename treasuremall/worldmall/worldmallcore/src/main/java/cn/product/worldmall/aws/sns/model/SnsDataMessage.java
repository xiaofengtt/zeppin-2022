package cn.product.worldmall.aws.sns.model;

import java.io.Serializable;

public class SnsDataMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3976972239956188245L;
	
	private String noticeType;
	private String noticeId;
	
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	
}
