package cn.product.worldmall.rabbit.receive;/**
 * Created by Administrator on 2019/6/21.
 */

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.product.worldmall.aws.sns.dao.AwsSnsClientDao;
import cn.product.worldmall.aws.sns.model.SnsDataMessage;
import cn.product.worldmall.aws.sns.util.AwsSnsUtil;
import cn.product.worldmall.dao.FrontDeviceTokenDao;
import cn.product.worldmall.dao.FrontDeviceTokenMessageDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.MobileCodeDao;
import cn.product.worldmall.dao.NoticeTopicDao;
import cn.product.worldmall.dao.NoticeTopicMessageDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.FrontDeviceToken.FrontDeviceTokenDeviceType;
import cn.product.worldmall.entity.FrontDeviceTokenMessage;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.MobileCode;
import cn.product.worldmall.entity.MobileCode.MobileCodeCreatorType;
import cn.product.worldmall.entity.MobileCode.MobileCodeStatus;
import cn.product.worldmall.entity.MobileCode.MobileCodeTypes;
import cn.product.worldmall.entity.NoticeTopic;
import cn.product.worldmall.entity.NoticeTopicMessage;
import cn.product.worldmall.entity.NoticeTopicMessage.NoticeTopicMessageStatus;
import cn.product.worldmall.entity.NoticeTopicMessage.NoticeTopicMessageType;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;

/**
 * RabbitMQ接收消息服务
 **/
@Service
public class RabbitReceiver4SampleNoticeService {

    public static final Logger log= LoggerFactory.getLogger(RabbitReceiver4SampleNoticeService.class);


    @Autowired
    private Environment environment;
    
    @Autowired
    private RabbitSenderService rabbitSenderService;

    @Autowired
    private LuckygameGoodsDao luckygameGoodsDao;
    
    @Autowired
    private FrontUserMessageDao frontUserMessageDao;
    
    @Autowired
    private GoodsDao goodsDao;
    
    @Autowired
    private MobileCodeDao mobileCodeDao;
    
    @Autowired
    private SystemParamDao systemParamDao;
    
    //使用基于zookeeper 有序集群同步锁
    /*
     * 注册curator
     */
    @Autowired
    private CuratorFramework curatorFramework;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontDeviceTokenDao frontDeviceTokenDao;

	@Autowired
	private AwsSnsClientDao awsSnsClientDao;

	@Autowired
	private NoticeTopicDao noticeTopicDao;

	@Autowired
	private NoticeTopicMessageDao noticeTopicMessageDao;

	@Autowired
	private FrontDeviceTokenMessageDao frontDeviceTokenMessageDao;
    /**
     * 绑定设备获取AWS-SNS endpointArn
     * @param result
     */
    @RabbitListener(queues = {"${mq.endpointarn.start.queue}"},containerFactory = "singleListenerContainer")
    public void getEndpointarnStart(FrontDeviceToken fdt) {
    	try {
    		String endpoint = "";
    		if(FrontDeviceTokenDeviceType.ANDROID.equals(fdt.getDeviceType())) {
    			endpoint = this.awsSnsClientDao.getEndpointArn4Android(fdt.getDeviceToken());
    		} else if (FrontDeviceTokenDeviceType.IOS.equals(fdt.getDeviceType())) {
    			endpoint = this.awsSnsClientDao.getEndpointArn(fdt.getDeviceToken());
    		}
    		
			if(!Utlity.checkStringNull(endpoint)) {
				fdt.setEndpointArn(endpoint);
			}
			
			if(!Utlity.checkStringNull(fdt.getTopic())) {
				//如果之前绑定过主题
				//那么需要先解绑，再重新绑定
				Map<String, Object> mapTopic = JSONUtils.json2map(fdt.getTopic());
				if(mapTopic != null && mapTopic.size() > 0) {
					for(String key : mapTopic.keySet()) {
						if(Utlity.checkStringNull(mapTopic.get(key) == null ? "" : mapTopic.get(key).toString())) {//未绑定，需要绑定
							NoticeTopic nt = this.noticeTopicDao.get(key);
							if(nt != null) {
								String  subscribeArn = this.awsSnsClientDao.subscribeApp(nt.getTopicArn(), endpoint);
								mapTopic.put(key, subscribeArn);
							}
	    				} else {//已绑定-先解绑
	    					//解绑Topic
	    					String subscriptionArn = mapTopic.get(key).toString();
							this.awsSnsClientDao.unsubscribe(subscriptionArn);
							//解绑endpoint--未实现
							NoticeTopic nt = this.noticeTopicDao.get(key);
							if(nt != null) {
								String  subscribeArn = this.awsSnsClientDao.subscribeApp(nt.getTopicArn(), endpoint);
								mapTopic.put(key, subscribeArn);
							}
	    				}
					}
					fdt.setTopic(JSONUtils.obj2json(mapTopic));
				}
			}
    		if(this.frontDeviceTokenDao.get(fdt.getUuid()) != null) {
    			fdt.setUpdatetime(new Timestamp(System.currentTimeMillis()));
    			this.frontDeviceTokenDao.update(fdt);
    		} else {
    			this.frontDeviceTokenDao.insert(fdt);
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送消息至用户端监听者-发生异常：",e.fillInStackTrace());
			return;
		}
    }
    
    /**
     * 绑定设备 绑定Topic
     * @param result
     */
    @RabbitListener(queues = {"${mq.bindingtopic.start.queue}"},containerFactory = "singleListenerContainer")
    public void bindingTopicStart(FrontDeviceToken fdt) {
    	try {
    		String endpoint = fdt.getEndpointArn();
    		Map<String, Object> map = JSONUtils.json2map(fdt.getTopic());
    		if(map != null && map.size() > 0) {
    			for(String key : map.keySet()) {
    				if(Utlity.checkStringNull(map.get(key) == null ? "" : map.get(key).toString())) {//未绑定，需要绑定
						NoticeTopic nt = this.noticeTopicDao.get(key);
						if(nt != null) {
							String  subscribeArn = this.awsSnsClientDao.subscribeApp(nt.getTopicArn(), endpoint);
							map.put(key, subscribeArn);
						}
    				}
    			}
    			fdt.setTopic(JSONUtils.obj2json(map));
    			this.frontDeviceTokenDao.update(fdt);
    		} else {
    			log.error("绑定设备 绑定Topic-发生错误：Topic数据错误");
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			log.error("绑定设备 绑定Topic-发生异常：",e.fillInStackTrace());
			return;
		}
    }
    
    
    
    /**
     * 发送消息至用户端
     * @param result
     */
    @RabbitListener(queues = {"${mq.message.start.queue}"},containerFactory = "multiListenerContainer")
    public void messageStart(FrontUserMessage fum) {
    	try {
    		this.frontUserMessageDao.insert(fum);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送消息至用户端监听者-发生异常：",e.fillInStackTrace());
			return;
		}
    }
    
    /**
     * 发送短信通知消息至用户
     */
    @SuppressWarnings("unchecked")
	@RabbitListener(queues = {"${mq.sms.start.queue}"},containerFactory = "multiListenerContainer")
    public void smsStart(Map<String, Object> params) {
    	try {
    		Thread.sleep(10L);//间隔10ms
    		log.info("----------------发送短信通知消息至用户，开始发送---------------："+params);
    		String codestr = params.get("codestr").toString();
    		String content = params.get("content").toString();
    		String mobile = params.get("mobile").toString();
    		
    		Map<String, String> vars = (Map<String, String>) params.get("vars");
    		
    		String deviceType = SendSmsNew.DEVICE_TYPE_TREASUREMALL_SH;
		
			SystemParam sp = this.systemParamDao.get(SystemParamKey.SMS_SEND_DEVICE_TYPE);
			if(sp != null) {
				Map<String, Object> deviceMap = JSONUtils.json2map(sp.getParamValue());
				for(String device : deviceMap.keySet()){
					Boolean flag = Boolean.valueOf(deviceMap.get(device).toString());
					if(flag) {
						deviceType = device;
						break;
					}
				}
			} else {
				deviceType = SendSmsNew.DEVICE_TYPE_TREASUREMALL_SH;
			}
    		String resultStr = SendSmsNew.sendSms4Other(codestr, content, mobile, deviceType, vars);
    		if ("0".equals(resultStr.split("_")[0])) {
    			MobileCode mc = new MobileCode();
        		mc.setUuid(UUID.randomUUID().toString());
        		mc.setCode(codestr);
        		mc.setContent(content);
        		mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
        		mc.setCreatortype(MobileCodeCreatorType.FRONT);
        		mc.setMobile(mobile);
        		mc.setStatus(MobileCodeStatus.NORMAL);
        		mc.setType(MobileCodeTypes.NOTICE);
        		this.mobileCodeDao.insert(mc);
    		} else {
    			log.error("发送短信通知消息至用户监听者-发送失败：{}",resultStr);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送消息至用户端监听者-发生异常：",e.fillInStackTrace());
			return;
		}
    }
    
    /**
     * 发送SNS消息至客户
     * @param result
     */
    @RabbitListener(queues = {"${mq.noticemessage.start.queue}"},containerFactory = "multiListenerContainer")
    public void messageSnsStart(FrontDeviceTokenMessage fdtm) {
    	try {
//    		this.frontUserMessageDao.insert(fum);
    		String message = fdtm.getContent();
//    		String title = fdtm.getTitle();
    		FrontDeviceToken fdt = this.frontDeviceTokenDao.get(fdtm.getFrontDeviceToken());
    		if(fdt != null && !Utlity.checkStringNull(fdt.getEndpointArn())) {
    			Map<String, Object> data = new HashMap<String, Object>();
    			data.put("message", message);
    			data.put("title", null);
    			data.put("endpointArn", fdt.getEndpointArn());
    			SnsDataMessage sdm = new SnsDataMessage();
    			sdm.setNoticeId(fdtm.getUuid());
    			sdm.setNoticeType(fdtm.getType());
    			data.put("snsDataMessage", sdm);
    			String result = "";
    			if(FrontDeviceTokenDeviceType.ANDROID.equals(fdt.getDeviceType())) {
    				result = this.awsSnsClientDao.puhlishEndpoint4GCM(data);
    			} else {
    				result = this.awsSnsClientDao.puhlishEndpoint4APNS(data);
    			}
    			fdtm.setResult(result);
    			log.info("SNSMessage Result is :"+result);
    			this.frontDeviceTokenMessageDao.insertMessage(fdt, fdtm);
    			
    		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" 发送SNS消息监听者-发生异常：",e.fillInStackTrace());
			return;
		}
    }
    
    /**
     * 发送SNS消息至主题
     * @param result
     */
    @RabbitListener(queues = {"${mq.topicnoticemessage.start.queue}"},containerFactory = "multiListenerContainer")
    public void messageSnsTopicStart(NoticeTopicMessage ntm) {
    	try {
    		ntm = this.noticeTopicMessageDao.get(ntm.getUuid());
    		
    		if(ntm != null && NoticeTopicMessageStatus.NORMAL.equals(ntm.getStatus())) {
    			//判断发送时间
    			if(System.currentTimeMillis() - ntm.getSendtime().getTime() >= 0) {//发送
    				NoticeTopic nt = this.noticeTopicDao.get(ntm.getNoticeTopic());
    				if(nt != null) {
    					String topicArn = nt.getTopicArn();
    					Map<String, Object> data = new HashMap<String, Object>();
    	    			data.put("message", ntm.getContent());
    	    			data.put("title", ntm.getTitle());
    	    			data.put("topicArn", topicArn);
    	    			SnsDataMessage sdm = new SnsDataMessage();
    	    			sdm.setNoticeId(ntm.getUuid());
    	    			sdm.setNoticeType(NoticeTopicMessageType.SYSTEM_PUBLISH);
    	    			data.put("snsDataMessage", sdm);
    	    			String result = this.awsSnsClientDao.publishTopic(data);
    	    			String responseStatus = result.split("_")[1];
    	    			if(AwsSnsUtil.RESPONSE_STATU_SUCCESS.equals(responseStatus)) {
    	    				ntm.setStatus(NoticeTopicMessageStatus.FINISHED);
    	    			} else {
    	    				ntm.setStatus(NoticeTopicMessageStatus.FAIL);
    	    			}
    	    			this.noticeTopicMessageDao.update(ntm);
    	    			log.info("SNSTopicMessage Result is :"+result);
    				}
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" 发送SNS消息监听者-发生异常：",e.fillInStackTrace());
			return;
		}
    }
}












