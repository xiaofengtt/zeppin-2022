package cn.product.worldmall.aws.sns.util;

import java.util.HashMap;
import java.util.Map;

import cn.product.worldmall.aws.sns.model.SnsDataMessage;
import cn.product.worldmall.aws.sns.util.SampleMessageGenerator.Platform;
import cn.product.worldmall.util.Utlity;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;

public class AwsSnsUtil {
	
	public static final String SNS_TITLE = "XShopping";
	
	public static final String RESPONSE_STATU_SUCCESS = "200";
	public static final String RESPONSE_STATU_AUTHORIZATIONERROR = "403";
	public static final String RESPONSE_STATU_ENDPOINTDISABLED = "400";
	public static final String RESPONSE_STATU_INTERNALERROR = "500";
	public static final String RESPONSE_STATU_INVALIDPARAMETER = "400";
	public static final String RESPONSE_STATU_INVALIDSECURITY = "403";
	public static final String RESPONSE_STATU_KMSACCESSDENIED = "200";
	public static final String RESPONSE_STATU_NOTFOUND = "404";
	
	public static enum Protocol {
		http,
		https,
		email,
		sms,
		sqs,
		application,
		lambda,
		firehose;
	}
	
	public static final Map<Platform, Map<String, MessageAttributeValue>> attributesMap = new HashMap<Platform, Map<String, MessageAttributeValue>>();
	static {
		attributesMap.put(Platform.ADM, null);
		attributesMap.put(Platform.GCM, null);
		attributesMap.put(Platform.APNS, null);
		attributesMap.put(Platform.APNS_SANDBOX, null);
		attributesMap.put(Platform.BAIDU, addBaiduNotificationAttributes());
		attributesMap.put(Platform.WNS, addWNSNotificationAttributes());
		attributesMap.put(Platform.MPNS, addMPNSNotificationAttributes());
	}

	public static String getPlatformSampleMessage(Platform platform, SnsDataMessage data, String title, String message) {
		if(Utlity.checkStringNull(title)) {
			title = SNS_TITLE;
		}
		switch (platform) {
		case APNS:
			return SampleMessageGenerator.getSampleAppleMessage(data, title, message);
		case APNS_SANDBOX:
			return SampleMessageGenerator.getSampleAppleMessage(data, title, message);
		case GCM:
			return SampleMessageGenerator.getSampleAndroidMessage(data, title, message);
		case ADM:
			return SampleMessageGenerator.getSampleKindleMessage();
		case BAIDU:
			return SampleMessageGenerator.getSampleBaiduMessage();
		case WNS:
			return SampleMessageGenerator.getSampleWNSMessage();
		case MPNS:
			return SampleMessageGenerator.getSampleMPNSMessage();
		default:
			throw new IllegalArgumentException("Platform not supported : "
					+ platform.name());
		}
	}
	
	private static Map<String, MessageAttributeValue> addBaiduNotificationAttributes() {
		Map<String, MessageAttributeValue> notificationAttributes = new HashMap<String, MessageAttributeValue>();
		notificationAttributes.put("AWS.SNS.MOBILE.BAIDU.DeployStatus",
				MessageAttributeValue.builder().dataType("String").stringValue("1").build());
		notificationAttributes.put("AWS.SNS.MOBILE.BAIDU.MessageKey",
				MessageAttributeValue.builder().dataType("String").stringValue("default-channel-msg-key").build());
		notificationAttributes.put("AWS.SNS.MOBILE.BAIDU.MessageType",
				MessageAttributeValue.builder().dataType("String").stringValue("0").build());
		return notificationAttributes;
	}

	private static Map<String, MessageAttributeValue> addWNSNotificationAttributes() {
		Map<String, MessageAttributeValue> notificationAttributes = new HashMap<String, MessageAttributeValue>();
		notificationAttributes.put("AWS.SNS.MOBILE.WNS.CachePolicy",
				MessageAttributeValue.builder().dataType("String").stringValue("cache").build());
		notificationAttributes.put("AWS.SNS.MOBILE.WNS.Type",
				MessageAttributeValue.builder().dataType("String").stringValue("wns/badge").build());
		return notificationAttributes;
	}

	private static Map<String, MessageAttributeValue> addMPNSNotificationAttributes() {
		Map<String, MessageAttributeValue> notificationAttributes = new HashMap<String, MessageAttributeValue>();
		notificationAttributes.put("AWS.SNS.MOBILE.MPNS.Type",
				MessageAttributeValue.builder().dataType("String").stringValue("token").build()); // This attribute is required.
		notificationAttributes.put("AWS.SNS.MOBILE.MPNS.NotificationClass",
				MessageAttributeValue.builder().dataType("String").stringValue("realtime").build()); // This attribute is required.
														
		return notificationAttributes;
	}
}
