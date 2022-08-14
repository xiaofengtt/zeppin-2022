package cn.product.worldmall.aws.sns.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.product.worldmall.aws.sns.AwsSnsClient;
import cn.product.worldmall.aws.sns.dao.AwsSnsClientDao;
import cn.product.worldmall.aws.sns.model.SnsDataMessage;
import cn.product.worldmall.aws.sns.util.AwsSnsUtil;
import cn.product.worldmall.aws.sns.util.SampleMessageGenerator;
import cn.product.worldmall.aws.sns.util.SampleMessageGenerator.Platform;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.Topic;

@Component
public class AwsSnsClientDaoImpl implements AwsSnsClientDao {
	
	private final static Logger log = LoggerFactory.getLogger(AwsSnsClientDaoImpl.class);
	
	@Autowired
	private AwsSnsClient awsSnsClient;

	@Override
	public String getEndpointArn(String deviceToken) {
		return awsSnsClient.createPlatformEndpoint(null, deviceToken);
	}

	@Override
	public String getEndpointArn4Android(String deviceToken) {
		return awsSnsClient.createPlatformEndpoint(AwsSnsClient.APP_FCM_XShopping, deviceToken);
	}

	@Override
	public String puhlishEndpoint4GCM(Map<String, Object> data) {
		//message-android google gcm
		return this.publishEndpoint(Platform.GCM, data);
	}

	@Override
	public String puhlishEndpoint4APNS(Map<String, Object> data) {
		//message-ios appstore apns
		return this.publishEndpoint(Platform.APNS, data);
	}

	@Override
	public String puhlishEndpoint4APNS_SANDBOX(Map<String, Object> data) {
		return this.publishEndpoint(Platform.APNS_SANDBOX, data);
	}
	
	private String publishEndpoint(Platform platform, Map<String, Object> data) {
		String message = data.get("message") == null ? "" : data.get("message").toString();
		String title = data.get("title") == null ? "" : data.get("title").toString();
		String endpointArn = data.get("endpointArn") == null ? "" : data.get("endpointArn").toString();
//		String dataStr = data.get("data");
		SnsDataMessage sdm = data.get("snsDataMessage") == null ? null : (SnsDataMessage)data.get("snsDataMessage");
		
		String messageObj = AwsSnsUtil.getPlatformSampleMessage(platform, sdm, title, message);
		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put(platform.name(), messageObj);
		String content = SampleMessageGenerator.jsonify(messageMap);
		log.info("content:"+content);
		PublishResponse result = this.awsSnsClient.puhlishEndpoint(content, endpointArn, platform);
		log.info("result:"+result.toString());
		log.info("messageId:"+result.messageId());
		log.info("responseStatus:"+result.sdkHttpResponse().statusCode());
		return result.messageId()+"_"+result.sdkHttpResponse().statusCode();
	}

	@Override
	public String createSNSTopic(String topicName) {
		return awsSnsClient.createSNSTopic(topicName);
	}

	@Override
	public List<Topic> listSNSTopics() {
		return awsSnsClient.listSNSTopics();
	}

	@Override
	public Map<String, String> getSNSTopicsAttributes(String topicArn) {
		return awsSnsClient.getSNSTopicsAttributes(topicArn).attributes();
	}

	@Override
	public String subscribeApp(String topicArn, String endpointArn) {
		return awsSnsClient.subscribeApp(topicArn, endpointArn).subscriptionArn();
	}

	@Override
	public String unsubscribe(String subscriptionArn) {
		return "_"+awsSnsClient.unsubscribe(subscriptionArn).sdkHttpResponse().statusCode();
	}

	@Override
	public String publishTopic(Map<String, Object> data) {
//		String message = data.get("message");
//		String title = data.get("title");
//		String dataStr = data.get("data");
//		String topicArn = data.get("topicArn");

		String message = data.get("message") == null ? "" : data.get("message").toString();
		String title = data.get("title") == null ? "" : data.get("title").toString();
		String topicArn = data.get("topicArn") == null ? "" : data.get("topicArn").toString();
//		String dataStr = data.get("data");
		SnsDataMessage sdm = data.get("snsDataMessage") == null ? null : (SnsDataMessage)data.get("snsDataMessage");
		
		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put(Platform.GCM.name(), AwsSnsUtil.getPlatformSampleMessage(Platform.GCM, sdm, title, message));
		messageMap.put(Platform.APNS.name(), AwsSnsUtil.getPlatformSampleMessage(Platform.APNS, sdm, title, message));
		messageMap.put(Platform.APNS_SANDBOX.name(), AwsSnsUtil.getPlatformSampleMessage(Platform.APNS_SANDBOX, sdm, title, message));
		messageMap.put("default", message);
		String content = SampleMessageGenerator.jsonify(messageMap);
//		System.out.println(content);
		PublishResponse result = this.awsSnsClient.publishTopic(content, topicArn);
		log.info("result:"+result.toString());
		log.info("messageId:"+result.messageId());
		log.info("responseStatus:"+result.sdkHttpResponse().statusCode());
		return result.messageId() + "_" + result.sdkHttpResponse().statusCode();
	}
}
