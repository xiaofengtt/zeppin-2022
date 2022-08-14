package cn.product.worldmall.aws.sns.dao;

import java.util.List;
import java.util.Map;

import software.amazon.awssdk.services.sns.model.Topic;

public interface AwsSnsClientDao {
	
	public String getEndpointArn(String deviceToken);
	
	public String getEndpointArn4Android(String deviceToken);
	
	public String puhlishEndpoint4GCM(Map<String, Object> data);
	
	public String puhlishEndpoint4APNS(Map<String, Object> data);
	
	public String puhlishEndpoint4APNS_SANDBOX(Map<String, Object> data);
	
	public String createSNSTopic(String topicName);
	
	public List<Topic> listSNSTopics();
	
	public Map<String, String> getSNSTopicsAttributes(String topicArn);
	
	public String subscribeApp(String topicArn, String endpointArn);
	
	public String unsubscribe(String subscriptionArn);
	
	public String publishTopic(Map<String, Object> data);
}
