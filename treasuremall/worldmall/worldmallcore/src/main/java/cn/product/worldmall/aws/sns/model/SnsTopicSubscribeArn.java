package cn.product.worldmall.aws.sns.model;

import java.io.Serializable;

public class SnsTopicSubscribeArn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2425463926337151271L;
	
	private String topic;
	private String subscribeArn;
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getSubscribeArn() {
		return subscribeArn;
	}
	public void setSubscribeArn(String subscribeArn) {
		this.subscribeArn = subscribeArn;
	}
	
}
