package cn.product.worldmall.aotutask;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.worldmall.aws.sns.dao.AwsSnsClientDao;
import cn.product.worldmall.dao.FrontDeviceTokenDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.NoticeTopicDao;
import cn.product.worldmall.dao.NoticeTopicMessageDao;
import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.NoticeTopic;
import cn.product.worldmall.entity.NoticeTopic.NoticeTopicStatus;
import cn.product.worldmall.entity.NoticeTopic.NoticeTopicType;
import cn.product.worldmall.entity.NoticeTopicMessage;
import cn.product.worldmall.entity.NoticeTopicMessage.NoticeTopicMessageStatus;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import software.amazon.awssdk.services.sns.model.Topic;

@Component
public class FrontDeviceTokenTask {
	
	private final static Logger log = LoggerFactory.getLogger(FrontDeviceTokenTask.class);
	
	@Autowired
    private FrontUserDao frontUserDao;

	@Autowired
    private FrontDeviceTokenDao frontDeviceTokenDao;
	
	@Autowired
	private AwsSnsClientDao awsSnsClientDao;
	
	@Autowired
	private RabbitSenderService rabbitSenderService;
	
	@Autowired
	private NoticeTopicDao noticeTopicDao;
	
	@Autowired
	private NoticeTopicMessageDao noticeTopicMessageDao;

	/**
	 * 校准用户设备信息中的用户级别
	 * 暂定三十分钟一次
	 */
	@Scheduled(cron="0 0/30 *  * * * ")
	public void calibrateDeviceTokenFrontUserGroup() {
		try {
			List<FrontDeviceToken> updateList = new ArrayList<FrontDeviceToken>();
			List<FrontDeviceToken> list = this.frontDeviceTokenDao.getFrontUserGroupDfList();
			if(list != null && list.size() > 0) {
				for(FrontDeviceToken fdt : list) {
					FrontUser fu = this.frontUserDao.get(fdt.getFrontUser());
					if(fu != null) {
						fdt.setFrontUserGroup(fu.getLevel());
						updateList.add(fdt);
					}
				}
			}
			this.frontUserDao.updateOrInsertDeviceTokenBatch(null, updateList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 每天执行一次，检查未能成功绑定亚马逊的deviceToken，重新绑定
	 */
	@Scheduled(cron="0 5 23  * * ? ")
	public void createEndpointArn4ErrorList() {
		try {

			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("unEndpoint", "unEndpoint");
			List<FrontDeviceToken> list = this.frontDeviceTokenDao.getListByParams(searchMap);
			
			if(list != null && list.size() > 0) {
				for(FrontDeviceToken fdt : list) {//异步处理
					rabbitSenderService.endpointarnStartMessageSend(fdt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 每五分钟一次 获取主题信息并更新
	 */
	@Scheduled(cron="0 0/5 *  * * ? ")
	public void loadTopicAtrributeFromAws() {
		try {
			List<NoticeTopic> listInsert = new ArrayList<NoticeTopic>();
			List<NoticeTopic> listUpdate = new ArrayList<NoticeTopic>();
			List<Topic> list = this.awsSnsClientDao.listSNSTopics();
			if(list != null && list.size() > 0) {
				for(Topic t : list) {
					String topicArn = t.topicArn();
					Map<String, String> info = this.awsSnsClientDao.getSNSTopicsAttributes(topicArn);
					String displayName = info.get("DisplayName");
					if(!Utlity.checkStringNull(info.get("DisplayName"))) {
						NoticeTopic nt = this.noticeTopicDao.getByArn(topicArn);
						if(nt != null) {
							nt.setDisplayName(displayName);
							listUpdate.add(nt);
						} else {
							
							nt = new NoticeTopic();
							nt.setUuid(UUID.randomUUID().toString());
							nt.setCreatetime(new Timestamp(System.currentTimeMillis()));
							nt.setType(NoticeTopicType.TOPIC_DEFAULT);
							nt.setStatus(NoticeTopicStatus.NORMAL);
							nt.setTopicArn(topicArn);
							
							nt.setDisplayName(displayName);
							listInsert.add(nt);
						}
					}
				}
			}
			this.frontDeviceTokenDao.updateTopicTask(listInsert, listUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 每三分钟一次  获取未发送的主题消息，发送至主题
	 */
	@Scheduled(cron="0 0/3 *  * * ? ")
	public void sendNoticeTopicMessage() {
		try {
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("status", NoticeTopicMessageStatus.NORMAL);
			List<NoticeTopicMessage> list = this.noticeTopicMessageDao.getListByParams(searchMap);
			if(list != null && list.size() > 0) {
				for(NoticeTopicMessage ntm : list) {
					if(System.currentTimeMillis() - ntm.getSendtime().getTime() >= 0) {//超过发送时间才能发送
						this.rabbitSenderService.messageStartSnsMessageSend(ntm);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 每三分钟一次 解绑已失效的用户设备，包括EndpointArn 和Topic 主要是Topic绑定
	 */
	@Scheduled(cron="0 0/3 *  * * ? ")
	public void unBinding() {
		try {
			List<FrontDeviceToken> updateList = new ArrayList<FrontDeviceToken>();
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("unBinding", "unBinding");
			List<FrontDeviceToken> list = this.frontDeviceTokenDao.getListByParams(searchMap);
			
			if(list != null && list.size() > 0) {
				for(FrontDeviceToken fdt : list) {//异步处理
//					rabbitSenderService.endpointarnStartMessageSend(fdt);
					if(!Utlity.checkStringNull(fdt.getTopic())) {
						Map<String, Object> mapTopic = JSONUtils.json2map(fdt.getTopic());
						if(mapTopic != null && mapTopic.size() > 0) {
							if(!Utlity.checkStringNull(mapTopic.get("subscribeArn") == null ? "" : mapTopic.get("subscribeArn").toString())) {
								String subscriptionArn = mapTopic.get("subscribeArn").toString();
								this.awsSnsClientDao.unsubscribe(subscriptionArn);//解绑Topic
								//解绑endpoint--未实现
								updateList.add(fdt);
								if(!fdt.getUuid().equals(list.get(list.size() - 1).getUuid())) {
									Thread.sleep(50);//防止IP被封
								}
							}
						}
					}
				}
				this.frontUserDao.updateOrInsertDeviceTokenBatch(null, updateList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}