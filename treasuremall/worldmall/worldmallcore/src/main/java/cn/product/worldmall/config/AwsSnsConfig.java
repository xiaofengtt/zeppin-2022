package cn.product.worldmall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

/**
 */
@Configuration
public class AwsSnsConfig {
	
    @Value("${aws.credentials.accesskey}")
    private String accesskey;
    
    @Value("${aws.credentials.secretkey}")
    private String secretkey;
    
//    @Value("${aws.credentials.region}")
//    private String region;


    @Bean
    public SnsClient snsClient() {
    	AwsBasicCredentials credentials = AwsBasicCredentials.create(accesskey, secretkey);
    	StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);
    	
    	return SnsClient.builder().region(Region.US_WEST_1).credentialsProvider(credentialsProvider).build();
    }

}
