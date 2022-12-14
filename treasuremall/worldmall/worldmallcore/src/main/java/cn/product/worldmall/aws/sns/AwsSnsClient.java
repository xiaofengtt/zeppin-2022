package cn.product.worldmall.aws.sns;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.product.worldmall.aws.sns.util.AwsSnsUtil;
import cn.product.worldmall.aws.sns.util.AwsSnsUtil.Protocol;
import cn.product.worldmall.aws.sns.util.SampleMessageGenerator.Platform;
import cn.product.worldmall.util.Utlity;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreatePlatformEndpointRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesResponse;
import software.amazon.awssdk.services.sns.model.ListTopicsRequest;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;
import software.amazon.awssdk.services.sns.model.Topic;
import software.amazon.awssdk.services.sns.model.UnsubscribeRequest;
import software.amazon.awssdk.services.sns.model.UnsubscribeResponse;

@Component
public class AwsSnsClient {

	private final static Logger log = LoggerFactory.getLogger(AwsSnsClient.class);

	@Autowired
	private SnsClient snsClient;

	private static final String TOPIC_ARN_TopicOne = "arn:aws:sns:us-west-1:014219048352:TopicOne";

	public static final String APP_ARN_XShopping = "arn:aws:sns:us-west-1:014219048352:app/APNS/XShopping-iOS";
	public static final String APP_APNS_SANDBOX_XShopping = "arn:aws:sns:us-west-1:014219048352:app/APNS_SANDBOX/XShopping-iOS";

	public static final String APP_FCM_XShopping = "arn:aws:sns:us-west-1:014219048352:app/GCM/XShopping-Android";

	/**
	 * ????????????
	 * 
	 * @param topicName
	 * @return
	 */
	public String createSNSTopic(String topicName) {
		CreateTopicResponse result;
		try {
			CreateTopicRequest request = CreateTopicRequest.builder().name(topicName).build();
			result = snsClient.createTopic(request);
			return result.topicArn();
		} catch (SnsException e) {
			e.printStackTrace();
			log.error("create sns topic error:{}", e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	public List<Topic> listSNSTopics() {
		try {
			ListTopicsRequest request = ListTopicsRequest.builder().build();
			return snsClient.listTopics(request).topics();
		} catch (SnsException e) {
			e.printStackTrace();
			log.error("create sns topic error:{}", e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ????????????????????????
	 * @param topicArn
	 * @return
	 */
	public GetTopicAttributesResponse getSNSTopicsAttributes(String topicArn) {
		try {
			GetTopicAttributesRequest request = GetTopicAttributesRequest.builder().topicArn(topicArn).build();
			GetTopicAttributesResponse result = snsClient.getTopicAttributes(request);
//			Map<String, String> topic = result.attributes();
			log.info(result.toString());
			return result;
		} catch (SnsException e) {
			e.printStackTrace();
			log.error("get sns topic attributes error:{}", e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ??????deviceToken???????????????endpointArn
	 * 
	 * @param appArn      -- ?????????????????????
	 * @param deviceToken
	 * @return
	 */
	public String createPlatformEndpoint(String appArn, String deviceToken) {
		if (Utlity.checkStringNull(appArn)) {
			appArn = APP_ARN_XShopping;
		}
		try {
			CreatePlatformEndpointRequest request = CreatePlatformEndpointRequest.builder()
					.platformApplicationArn(appArn).token(deviceToken).build();
			return snsClient.createPlatformEndpoint(request).endpointArn();
		} catch (SnsException e) {
			e.printStackTrace();
			log.error("create sns PlatformEndpoint error:{}", e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ?????????????????????
	 * 
	 * @param message
	 * @param topicArn
	 * @return
	 */
	public PublishResponse publishTopic(String message, String topicArn) {

		try {

			PublishRequest request = PublishRequest.builder()
					.message(message).topicArn(topicArn).messageStructure("json").build();
			return snsClient.publish(request);

		} catch (SnsException e) {
			e.printStackTrace();
			log.error("publish topic error:{}", e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ?????????????????????
	 * 
	 * @param message     JSON?????????????????????????????????JSON????????????
	 * @param endpointArn
	 * @return
	 */
	public PublishResponse puhlishEndpoint(String message, String endpointArn, Platform platform) {
		try {
			PublishRequest request = PublishRequest.builder().messageAttributes(AwsSnsUtil.attributesMap.get(platform))
					.message(message).targetArn(endpointArn).messageStructure("json").build();
			return snsClient.publish(request);
		} catch (SnsException e) {
			e.printStackTrace();
			log.error("puhlish endpoint error:{}", e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ??????????????????????????????????????????
	 * 
	 * @param topicArn
	 * @param endpointArn
	 * @return
	 */
	public SubscribeResponse subscribeApp(String topicArn, String endpointArn) {
		try {
			if (Utlity.checkStringNull(topicArn)) {
				topicArn = TOPIC_ARN_TopicOne;
			}
			SubscribeRequest request = SubscribeRequest.builder().protocol(Protocol.application.name())
					.endpoint(endpointArn).returnSubscriptionArn(true).topicArn(topicArn).build();
			return snsClient.subscribe(request);
		} catch (SnsException e) {
			e.printStackTrace();
			log.error("subscribe app error:{}", e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ??????/????????????
	 * 
	 * @param subscribeArn ????????????????????????subArn
	 * @return
	 */
	public UnsubscribeResponse unsubscribe(String subscriptionArn) {
		try {
			UnsubscribeRequest request = UnsubscribeRequest.builder().subscriptionArn(subscriptionArn).build();
			return snsClient.unsubscribe(request);
		} catch (SnsException e) {
			e.printStackTrace();
			log.error("unsubscribe error:{}", e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
