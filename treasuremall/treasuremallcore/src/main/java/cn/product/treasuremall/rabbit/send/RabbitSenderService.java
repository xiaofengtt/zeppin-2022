package cn.product.treasuremall.rabbit.send;/**
 * Created by Administrator on 2019/6/21.
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeDao;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.treasuremall.dao.ActivityInfoDao;
import cn.product.treasuremall.dao.FrontUserBuyfreeOrderDao;
import cn.product.treasuremall.dao.FrontUserCheckinHistoryDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.FrontUserScorelotteryHistoryDao;
import cn.product.treasuremall.dao.FrontUserWithdrawOrderDao;
import cn.product.treasuremall.dao.GoodsCoverImageDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.dao.WinningInfoDao;
import cn.product.treasuremall.dao.WinningInfoReceiveDao;
import cn.product.treasuremall.entity.ActivityInfo;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoName;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.treasuremall.entity.ActivityInfoBuyfree;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeGoods;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeGoods.ActivityInfoBuyfreeGoodsStatus;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserBuyfreeOrder;
import cn.product.treasuremall.entity.FrontUserCheckinHistory;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderGroup;
import cn.product.treasuremall.entity.FrontUserRechargeOrder;
import cn.product.treasuremall.entity.FrontUserScorelotteryHistory;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.GoodsCoverImage;
import cn.product.treasuremall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.entity.LuckygameGoods.LuckygameGoodsStatus;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.RobotSetting;
import cn.product.treasuremall.entity.RobotSetting.RobotSettingStatus;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.entity.WinningInfoReceive;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.mapper.RobotSettingMapper;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.SendSmsNew;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.ProvideInfoVO;
import cn.product.treasuremall.vo.front.LuckygameGoodsIssueVO;

/**
 * RabbitMQ发送服务
 * @author Administrator
 *
 */
@Service
public class RabbitSenderService {

    public static final Logger log= LoggerFactory.getLogger(RabbitSenderService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private RobotSettingMapper robotSettingMapper;
    
    @Autowired
    private RabbitAdmin rabbitAdmin;
    
    @Autowired
    private TopicExchange robotWorkReadyStartExchange;
    
    @Autowired
    private Queue robotWorkReadyStartQueue;
    
    @Autowired
    private LuckygameGoodsDao luckygameGoodsDao;
    
    @Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
    
    @Autowired
    private GoodsDao goodsDao;
	
	@Autowired
	private GoodsCoverImageDao goodsCoverImageDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
	private WinningInfoDao winningInfoDao;
	
	@Autowired
	private WinningInfoReceiveDao winningInfoReceiveDao;
	
	@Autowired
	private FrontUserWithdrawOrderDao frontUserWithdrawOrderDao;	
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;	
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private ActivityInfoBuyfreeGoodsDao activityInfoBuyfreeGoodsDao;
	
	@Autowired
	private ActivityInfoBuyfreeDao activityInfoBuyfreeDao;
	
	@Autowired
	private FrontUserBuyfreeOrderDao frontUserBuyfreeOrderDao;
	
	@Autowired
	private FrontUserCheckinHistoryDao frontUserCheckinHistoryDao;
	
	@Autowired
	private FrontUserScorelotteryHistoryDao frontUserScorelotteryHistoryDao;
	
	@Autowired
	private ActivityInfoDao activityInfoDao;
	
    /**
	 * 发送工作准备消息进入ready队列
	 * @param frontUser
	 */
	public void robotWorkMessageSend(String frontUser) {
		try {
			RobotSetting rs = this.robotSettingMapper.selectByPrimaryKey(frontUser);
			if(rs != null && RobotSettingStatus.START.equals(rs.getStatus())) {//工作开启
				//计算expire rs.getPeriodMin()单位s
				int expire = Utlity.getRandomNum(rs.getPeriodMin(), rs.getPeriodRandom())*1000;//单位ms
				log.info("------------执行间隔时间--------------------："+expire);
				String qchange = rs.getRobotId();
				//声明队列
				Queue qu = createQueue("treasuremall.robot.ready.queue."+qchange, "treasuremall.robot.ready.exchange."+qchange);
				rabbitAdmin.declareQueue(qu);
				//声明交换机
				TopicExchange ex = createExchange("treasuremall.robot.ready.exchange."+qchange);
				rabbitAdmin.declareExchange(ex);
				//两次绑定
				rabbitAdmin.declareBinding(BindingBuilder.bind(qu).to(robotWorkReadyStartExchange)
						.with("treasuremall.robot.ready.routing.key."+qchange));
				
				rabbitAdmin.declareBinding(BindingBuilder.bind(robotWorkReadyStartQueue).to(ex).with(environment.getProperty("mq.robot.start.routing.key")));
				
				rabbitAdmin.purgeQueue("treasuremall.robot.ready.queue."+rs.getRobotId());//清空队列
				//发送消息到ready（死信）等待超时——————进入start执行
				rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.robot.start.exchange"));
                rabbitTemplate.setRoutingKey("treasuremall.robot.ready.routing.key."+qchange);
                rabbitTemplate.convertAndSend(rs, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties mp=message.getMessageProperties();
                        mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        mp.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,RobotSetting.class);

                        //TODO：动态设置TTL(为了测试方便，暂且设置10s)
                        log.info("------------执行--------------------："+rabbitTemplate.getRoutingKey());
                        mp.setExpiration(String.valueOf(expire));
                        return message;
                    }
                });
			} else {
				if(rs != null) {
					rabbitAdmin.purgeQueue("treasuremall.robot.ready.queue."+rs.getRobotId());//清空队列
				}
			}
		} catch (Exception e) {
			log.error("发送工作准备消息进入ready队列，消息为：{}",frontUser,e.fillInStackTrace());
		}
	}
	
	/**
	 * 删除机器人工作队列
	 * @param frontUser
	 */
	public void robotWorkMessageRemove(String frontUser) {
		try {
			 log.info("删除机器人工作队列：{}",frontUser);
			String qName = "treasuremall.robot.ready.queue."+frontUser;
			String eName = "treasuremall.robot.ready.exchange."+frontUser;
			deleteQueue(qName);
			deleteExchange(eName);
		} catch (Exception e) {
			log.error("删除机器人工作队列，异常：{}",frontUser,e.fillInStackTrace());
		}
	}
	
    /**
     * 秒杀成功后生成抢购订单-发送信息入死信队列，等待着一定时间失效超时未支付的订单
     * @param orderCode
     */
    public void sendKillSuccessOrderExpireMsg(final String orderCode){
        try {
            if (StringUtils.isNotBlank(orderCode)){
            	StringBuilder demoStr = new StringBuilder("测试发送demo");
                if (demoStr!=null){
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(environment.getProperty("mq.kill.item.success.kill.dead.prod.exchange"));
                    rabbitTemplate.setRoutingKey(environment.getProperty("mq.kill.item.success.kill.dead.prod.routing.key"));
                    rabbitTemplate.convertAndSend(demoStr, new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message message) throws AmqpException {
                            MessageProperties mp=message.getMessageProperties();
                            mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                            mp.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,StringBuilder.class);

                            //TODO：动态设置TTL(为了测试方便，暂且设置10s)
                            mp.setExpiration(environment.getProperty("mq.kill.item.success.kill.expire"));
                            return message;
                        }
                    });
                }
            }
        }catch (Exception e){
            log.error("秒杀成功后生成抢购订单-发送信息入死信队列，等待着一定时间失效超时未支付的订单-发生异常，消息为：{}",orderCode,e.fillInStackTrace());
        }
    }
    
    /**
     * 计时开奖消息发送
     * @param goodsIssue
     */
    public void lotterySend(String goodsIssue){
        try {
        	 log.info("计时开奖消息发送-准备发送消息：{}",goodsIssue);
        	LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(goodsIssue);
            if (lgi != null && LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())){
            	rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.lottery.start.exchange"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.lottery.ready.routing.key"));
                rabbitTemplate.convertAndSend(lgi, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties mp=message.getMessageProperties();
                        mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        mp.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,LuckygameGoodsIssue.class);

                        //TODO：动态设置TTL(为了测试方便，暂且设置10s)
                        mp.setExpiration(environment.getProperty("mq.lottery.start.expire"));
                        return message;
                    }
                });
            }
        }catch (Exception e){
            log.error("计时开奖消息发送-发生异常，消息为：开奖奖品--{}",goodsIssue,e.fillInStackTrace());
        }
    }

    /**
     * 当前抽奖已募集完成，异步开启下一期抽奖
     * @param lgUuid
     */
    public void goodsIssueStartMessageSend(String lgUuid) {
        log.info("异步开启下一期抽奖通知消息-准备发送消息：{}",lgUuid);
        try {
    		LuckygameGoods lg = this.luckygameGoodsDao.get(lgUuid);
    		if(lg != null && LuckygameGoodsStatus.NORMAL.equals(lg.getStatus())) {//发送开启下一期消息
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.goodsIssus.start.exchange"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.goodsIssus.start.routing.key"));

                rabbitTemplate.convertAndSend(lg, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties=message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,LuckygameGoods.class);
                        return message;
                    }
                });
    		}
		} catch (Exception e) {
			log.error("异步开启下一期抽奖通知消息-发生异常，消息为：{}",lgUuid,e.fillInStackTrace());
		}
    }
    
    /**
     * 推送中奖消息
     */
    public void pushInfoStartMessageSend() {
        log.info("推送中奖消息-准备发送消息：{}");
        DataResult<Object> data = new DataResult<Object>();
        try {
        	//查询即将开奖消息--50条当前
    		Integer pageNum = 1;
    		Integer pageSize = 50;
    		
    		Map<String, Object> searchMap = new HashMap<String, Object>();
    		String[] statuses = {LuckygameGoodsIssueStatus.LOTTERY, LuckygameGoodsIssueStatus.FINISHED};
    		searchMap.put("statuses", statuses);
    		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
    			searchMap.put("offSet", (pageNum-1)*pageSize);
    			searchMap.put("pageSize", pageSize);
    		}
    		searchMap.put("sort", "status desc,lotterytime desc");
    		searchMap.put("gameType", Constants.GAME_TYPE_TRADITION);
    		Integer totalCount = luckygameGoodsIssueDao.getCountByParams(searchMap);
    		List<LuckygameGoodsIssue> list = luckygameGoodsIssueDao.getListByParams(searchMap);
    		
    		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
    		String pathUrl = "";
    		if(sp != null) {
    			pathUrl = sp.getParamValue();
    		} else {
    			pathUrl = Utlity.IMAGE_PATH_URL;
    		}
    		//获取金币汇率
			SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sprate != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
			}
			
    		List<LuckygameGoodsIssueVO> listvo = new ArrayList<LuckygameGoodsIssueVO>();
    		if(list != null && list.size() > 0) {
    			for(LuckygameGoodsIssue lgi : list) {
    				LuckygameGoodsIssueVO lgivo = new LuckygameGoodsIssueVO(lgi);
    				lgivo.setPrice(lgivo.getdPrice().multiply(rate));//奖品价值（元）
    				Goods good = this.goodsDao.get(lgi.getGoodsId());
    				lgivo.setCode(good.getCode());
    				lgivo.setCoverImg("");
    				//奖品封面图
    				searchMap.clear();
    				searchMap.put("belongs", lgi.getGoodsId());
    				searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
    				List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
    				if(gcis != null && gcis.size() > 0) {
    					Resource re = this.resourceDao.get(gcis.get(0).getImage());
    					if(re != null) {
    						lgivo.setCoverImg(pathUrl + re.getUrl());
    					}
    				}
    				
    				if(LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {//倒计时
    					lgivo.setTimeLine(lgi.getLotterytime().getTime()+Utlity.TIMELINE-System.currentTimeMillis());
    				}
    				
    				//获取标签信息
    				LuckygameGoods lg = this.luckygameGoodsDao.get(lgi.getLuckygameGoods());
    				if(lg != null) {
    					lgivo.setTabs(lg.getTabs());
    				}
    				
    				//封装中奖人信息
    				searchMap.clear();
    				searchMap.put("goodsIssue", lgi.getUuid());
    				searchMap.put("isLuck", 1);
    				
    				List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
    				if(listorder != null && listorder.size() > 0) {
    					FrontUserPaymentOrder fupo = listorder.get(0);
    					lgivo.setShowIdStr(fupo.getFrontUserShowId()+"");
    					lgivo.setFrontUser(fupo.getFrontUser());
    					searchMap.clear();
    					searchMap.put("goodsIssue", lgi.getUuid());
    					searchMap.put("frontUser", fupo.getFrontUser());
    					List<FrontUserPaymentOrder> listordersum = this.frontUserPaymentOrderDao.getGroupListByParams(searchMap);
    					if(listordersum != null && listordersum.size() > 0) {
    						lgivo.setBuyCount(listordersum.get(0).getBuyCount());
    					}
    					FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
    					lgivo.setNickname(fu.getNickname());
    					lgivo.setdAmount(fupo.getdAmount());
    					lgivo.setActualDAmount(fupo.getActualDAmount());
    					if(!Utlity.checkStringNull(fu.getImage())) {
    						Resource re = this.resourceDao.get(fu.getImage());
    						if(re != null) {
    							lgivo.setImageUrl(pathUrl + re.getUrl());
    						}
    					}
    				}
    				listvo.add(lgivo);
    			}
    		}
    		
    		
    		data.setData(listvo);
    		data.setStatus(ResultStatusType.SUCCESS);
    		data.setMessage("success");
    		data.setPageNum(pageNum);
    		data.setPageSize(pageSize);
    		data.setTotalResultCount(totalCount);
    		
    		Map<String, String> params = new HashMap<String, String>();
    		params.put("status", data.getStatus().toString());
    		params.put("message", data.getMessage());
    		params.put("pageNum", data.getPageNum()+"");
    		params.put("pageSize", data.getPageSize()+"");
    		params.put("totalResultCount", data.getTotalResultCount()+"");
    		params.put("data", JSONUtils.obj2json(data.getData()));
    		params.put("dataType", Constants.SOCKET_DATA_TYPE_LOTTERYLIST);
    		//逻辑未写完，暂不开放使用
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(environment.getProperty("mq.pushInfo.start.exchange"));
            rabbitTemplate.setRoutingKey(environment.getProperty("mq.pushInfo.start.routing.key"));

            rabbitTemplate.convertAndSend(params, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties=message.getMessageProperties();
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Map.class);
                    return message;
                }
            });
		} catch (Exception e) {
			e.printStackTrace();
			log.error("推送中奖消息-发生异常，消息为：{}",data.getData(),e.fillInStackTrace());
		}
    }
    
    /**
     * 推送开奖消息
     * @param wilist
     */
    public void pushWinInfoStartMessageSend(List<WinningInfo> wilist) {
        log.info("推送开奖消息-准备发送消息：{}");
        try {
        	List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
        	//获取金币汇率
			SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sp != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
			}
        	for(WinningInfo wi : wilist) {
        		String nickname = "";
            	String goodsname = "";
            	BigDecimal price = BigDecimal.ZERO;
            	if(wi != null) {
            		if(Constants.GAME_TYPE_TRADITION.equals(wi.getGameType())) {
                		
                		FrontUser fu = this.frontUserDao.get(wi.getFrontUser());
                		nickname = fu.getNickname();
                	} else if(Constants.GAME_TYPE_GROUP2.equals(wi.getGameType())) {
                		
            			FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(wi.getOrderId());
            			if(FrontUserPaymentOrderGroup.LUCKY.equals(fupo.getPaymentGroup())){
            				nickname = "幸运队";
            			} else if(FrontUserPaymentOrderGroup.RAIDER.equals(fupo.getPaymentGroup())){
            				nickname = "夺宝队";
            			}
                	}
                	LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
            		goodsname = lgi.getShortTitle();
            		price = lgi.getdPrice().multiply(rate);
            	}
            	
            	Map<String, Object> data = new HashMap<String, Object>();
            	data.put("nickname", nickname);
            	data.put("goodsname", goodsname);
            	data.put("price", price);//单位：元
            	dataList.add(data);
        	}
        	Map<String, String> params = new HashMap<String, String>();
    		params.put("status", ResultStatusType.SUCCESS.name());
    		params.put("message", "success");
    		params.put("data", JSONUtils.obj2json(dataList));
    		params.put("dataType", Constants.SOCKET_DATA_TYPE_WININFO);
        	
    		//逻辑未写完，暂不开放使用
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(environment.getProperty("mq.pushInfo.start.exchange"));
            rabbitTemplate.setRoutingKey(environment.getProperty("mq.pushInfo.start.routing.key"));

            rabbitTemplate.convertAndSend(params, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties=message.getMessageProperties();
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Map.class);
                    return message;
                }
            });
		} catch (Exception e) {
			e.printStackTrace();
			log.error("推送开奖消息-发生异常，消息为：{}",wilist.isEmpty(),e.fillInStackTrace());
		}
    }
    
    /**
     * 发送消息至用户端
     * @param fum
     */
    public void messageStartMessageSend(FrontUserMessage fum) {
        log.info("发送消息至用户端通知消息-准备发送消息：{}",fum.getTitle());
        try {
    		if(fum != null && FrontUserMessageStatus.NORMAL.equals(fum.getStatus())) {//发送消息
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.message.start.exchange"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.message.start.routing.key"));

                rabbitTemplate.convertAndSend(fum, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties=message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,FrontUserMessage.class);
                        return message;
                    }
                });
    		}
		} catch (Exception e) {
			log.error("发送消息至用户端通知消息-发生异常，消息为：{}",fum.getTitle(),e.fillInStackTrace());
		}
    }
    
    /**
     * 发送短信消息至用户
     * @param lgUuid
     */
    public void smsStartMessageSend(FrontUserMessage fum, String temp_id) {
        log.info("发送短信消息至用户通知消息-准备发送消息：{}",temp_id);
        try {
        	Map<String, Object> params = new HashMap<String, Object>();
        	String codestr = Utlity.getCaptcha(4);
        	String content = "";
        	Map<String, String> vars = new HashMap<String, String>();
        	vars.put("temp_id", temp_id);
        	//根据不同模板封装短信参数
        	switch (temp_id) {
				case SendSmsNew.TREASUREMALL_SH_TEMP:
					vars.put("code", codestr);
					content = "您本次操作的验证码是："+codestr+"，验证码将在3分钟后失效。";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_DELIVERY:
					if(FrontUserMessageSourceType.ORDER_TYPE_USER_WIN.equals(fum.getSourceType())) {
						WinningInfoReceive wir = this.winningInfoReceiveDao.get(fum.getSourceId());
						if(wir != null) {
							Goods goods = this.goodsDao.get(wir.getGoodsId());
							String goodsName = "商品";
							if(goods != null) {
								goodsName = goods.getShortname();
							}
							ProvideInfoVO pivo = JSONUtils.json2obj(wir.getProvideInfo(), ProvideInfoVO.class);
							String company = "快递";
							String num = "请登录APP查看";
							if(pivo != null) {
								company = Constants.companyInfoMap.get(pivo.getCompany()) == null ? "快递" : Constants.companyInfoMap.get(pivo.getCompany());
								num = pivo.getExpressNumber();
							}
							vars.put("code1", goodsName);
							vars.put("code2", company);
							vars.put("code3", num);
							content = "尊敬的用户，您的"+goodsName+"已经通过"+company+"物流寄出，物流单号"+num+"。收货后请及时开箱验货，如有问题，请与客服联系。";
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_BUYFREE.equals(fum.getSourceType())) {
						FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(fum.getSourceId());
						if(fubo != null) {
							Goods goods = this.goodsDao.get(fubo.getGoodsId());
							String goodsName = "商品";
							if(goods != null) {
								goodsName = goods.getShortname();
							}
							ProvideInfoVO pivo = JSONUtils.json2obj(fubo.getProvideInfo(), ProvideInfoVO.class);
							String company = "快递";
							String num = "请登录APP查看";
							if(pivo != null) {
								company = Constants.companyInfoMap.get(pivo.getCompany()) == null ? "快递" : Constants.companyInfoMap.get(pivo.getCompany());
								num = pivo.getExpressNumber();
							}
							vars.put("code1", goodsName);
							vars.put("code2", company);
							vars.put("code3", num);
							content = "尊敬的用户，您的"+goodsName+"已经通过"+company+"物流寄出，物流单号"+num+"。收货后请及时开箱验货，如有问题，请与客服联系。";
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_CHECKIN.equals(fum.getSourceType())) {
						FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(fum.getSourceId());
						if(fuch != null) {
//							goodsName = fuch.getPrizeTitle();
							Goods goods = this.goodsDao.get(fuch.getPrize());
							String goodsName = "商品";
							if(goods != null) {
								goodsName = goods.getShortname();
							}
							ProvideInfoVO pivo = JSONUtils.json2obj(fuch.getProvideInfo(), ProvideInfoVO.class);
							String company = "快递";
							String num = "请登录APP查看";
							if(pivo != null) {
								company = Constants.companyInfoMap.get(pivo.getCompany()) == null ? "快递" : Constants.companyInfoMap.get(pivo.getCompany());
								num = pivo.getExpressNumber();
							}
							vars.put("code1", goodsName);
							vars.put("code2", company);
							vars.put("code3", num);
							content = "尊敬的用户，您的"+goodsName+"已经通过"+company+"物流寄出，物流单号"+num+"。收货后请及时开箱验货，如有问题，请与客服联系。";
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_SCORELOTTERY.equals(fum.getSourceType())) {
						FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(fum.getSourceId());
						if(fush != null) {
//							goodsName = fuch.getPrizeTitle();
							Goods goods = this.goodsDao.get(fush.getPrize());
							String goodsName = "商品";
							if(goods != null) {
								goodsName = goods.getShortname();
							}
							ProvideInfoVO pivo = JSONUtils.json2obj(fush.getProvideInfo(), ProvideInfoVO.class);
							String company = "快递";
							String num = "请登录APP查看";
							if(pivo != null) {
								company = Constants.companyInfoMap.get(pivo.getCompany()) == null ? "快递" : Constants.companyInfoMap.get(pivo.getCompany());
								num = pivo.getExpressNumber();
							}
							vars.put("code1", goodsName);
							vars.put("code2", company);
							vars.put("code3", num);
							content = "尊敬的用户，您的"+goodsName+"已经通过"+company+"物流寄出，物流单号"+num+"。收货后请及时开箱验货，如有问题，请与客服联系。";
						}
					}
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW_FAIL:
					content = "您的提现申请未通过,资金已退回到账户中，如有问题请咨询客服";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE:
					String recharge = "";
					FrontUserHistory fuhRecharge = this.frontUserHistoryDao.get(fum.getSourceId());
					if(fuhRecharge != null) {
						FrontUserRechargeOrder furo = this.frontUserRechargeOrderDao.get(fuhRecharge.getOrderId());
						recharge = furo.getAmount().toString(); 
					}
					vars.put("code", recharge);
					content = "您账户充值"+recharge+"元成功，请核对，如有问题请联系客服。";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW:
					String withdraw = "";
					FrontUserHistory fuhWithdraw = this.frontUserHistoryDao.get(fum.getSourceId());
					if(fuhWithdraw != null) {
						FrontUserWithdrawOrder fuwo = this.frontUserWithdrawOrderDao.get(fuhWithdraw.getOrderId());
						withdraw = fuwo.getActualAmount().toString(); 
					}
					vars.put("code", withdraw);
					content = "您账户提现"+withdraw+"元成功，请核对，如有问题请联系客服。";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_WINNING:
					String goodsName = "";
					if(FrontUserMessageSourceType.ORDER_TYPE_USER_WIN.equals(fum.getSourceType())) {
						FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(fum.getSourceId());
						if(fupo != null) {
							Goods goods = this.goodsDao.get(fupo.getGoodsId());
							if(goods != null) {
								goodsName = goods.getShortname();
							}
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_BUYFREE.equals(fum.getSourceType())) {
						FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(fum.getSourceId());
						if(fubo != null) {
							Goods goods = this.goodsDao.get(fubo.getGoodsId());
							if(goods != null) {
								goodsName = goods.getShortname();
							}
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_CHECKIN.equals(fum.getSourceType())) {
						FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(fum.getSourceId());
						if(fuch != null) {
//							goodsName = fuch.getPrizeTitle();
							Goods goods = this.goodsDao.get(fuch.getPrize());
							if(goods != null) {
								goodsName = goods.getShortname();
							}
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_SCORELOTTERY.equals(fum.getSourceType())) {
						FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(fum.getSourceId());
						if(fush != null) {
//							goodsName = fuch.getPrizeTitle();
							Goods goods = this.goodsDao.get(fush.getPrize());
							if(goods != null) {
								goodsName = goods.getShortname();
							}
						}
					}
					vars.put("name", goodsName);
					content = "恭喜您！！！成功拍得商品"+goodsName+"，请您登录APP或网站查看。";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_GROUPWIN:
					String groupName = "";
					if(FrontUserMessageSourceType.ORDER_TYPE_USER_WIN.equals(fum.getSourceType())) {
						FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(fum.getSourceId());
						if(fupo != null) {
							if(FrontUserPaymentOrderGroup.LUCKY.equals(fupo.getPaymentGroup())) {
								groupName = "幸运队";
							} else if(FrontUserPaymentOrderGroup.RAIDER.equals(fupo.getPaymentGroup())) {
								groupName = "夺宝队";
							}
						}
					}
					vars.put("name", groupName);
					//恭喜您！！！本期战队PK赛，您加入的%name%获得胜利，所得金币奖励，已兑换到您的账户，请您登录APP或网站查看。
					content = "恭喜您！！！本期战队PK赛，您加入的"+groupName+"获得胜利，所得金币奖励，已兑换到您的账户，请您登录APP或网站查看。";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_RECEIVE:
					//获取金币汇率
					SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
					BigDecimal rate = BigDecimal.ONE;
					if(sp != null) {
						rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
					}
					String name = "";
					String money = "";
					if(FrontUserMessageSourceType.ORDER_TYPE_USER_WIN.equals(fum.getSourceType())) {
						WinningInfo wi = this.winningInfoDao.get(fum.getSourceId());
						if(wi != null) {
							Goods goods = this.goodsDao.get(wi.getGoodsId());
							if(goods != null) {
								name = goods.getShortname();
								money = goods.getPrice().multiply(rate).toString();
							}
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_BUYFREE.equals(fum.getSourceType())) {
						FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(fum.getSourceId());
						if(fubo != null) {
							Goods goods = this.goodsDao.get(fubo.getGoodsId());
							if(goods != null) {
								name = goods.getShortname();
								money = goods.getPrice().multiply(rate).toString();
							}
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_CHECKIN.equals(fum.getSourceType())) {
						FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(fum.getSourceId());
						if(fuch != null) {
//							goodsName = fuch.getPrizeTitle();
							Goods goods = this.goodsDao.get(fuch.getPrize());
							if(goods != null) {
								name = goods.getShortname();
								money = goods.getPrice().multiply(rate).toString();
							}
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_SCORELOTTERY.equals(fum.getSourceType())) {
						FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(fum.getSourceId());
						if(fush != null) {
//							goodsName = fuch.getPrizeTitle();
							Goods goods = this.goodsDao.get(fush.getPrize());
							if(goods != null) {
								name = goods.getShortname();
								money = goods.getPrice().multiply(rate).toString();
							}
						}
					}
					
					vars.put("name", name);
					vars.put("money", money);
					content = "您拍得的商品"+name+"已经成功兑换"+money+"金币，祝您购物愉快！";
					break;
				default:
					break;
			}
        	
        	FrontUser fu = this.frontUserDao.get(fum.getFrontUser());
        	params.put("codestr", codestr);
        	params.put("content", content);
        	params.put("mobile", fu.getMobile());
        	params.put("vars", vars);
        	
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(environment.getProperty("mq.sms.start.exchange"));
            rabbitTemplate.setRoutingKey(environment.getProperty("mq.sms.start.routing.key"));

            rabbitTemplate.convertAndSend(params, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties=message.getMessageProperties();
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Map.class);
                    return message;
                }
            });
		} catch (Exception e) {
			log.error("发送消息至用户端通知消息-发生异常，消息为：{}",temp_id,e.fillInStackTrace());
		}
    }
    

    /**
     * 当前抽奖已募集完成，异步开启下一期0元购
     * @param lgUuid
     */
    public void buyfreeStartMessageSend(String bfUuid) {
        log.info("异步开启下一期0元购通知消息-准备发送消息：{}",bfUuid);
        try {
    		ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(bfUuid);
    		if(aibg != null && ActivityInfoBuyfreeGoodsStatus.NORMAL.equals(aibg.getStatus())) {//发送开启下一期消息
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.buyfree.start.exchange"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.buyfree.start.routing.key"));

                rabbitTemplate.convertAndSend(aibg, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties=message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,ActivityInfoBuyfreeGoods.class);
                        return message;
                    }
                });
    		}
		} catch (Exception e) {
			log.error("异步开启下一期0元购通知消息-发生异常，消息为：{}",bfUuid,e.fillInStackTrace());
		}
    }
    
    /**
     * 0元购计时开奖消息发送
     * @param goodsIssue
     */
    public void buyfreelotterySend(String bfl){
        try {
        	 log.info("0元购计时开奖消息发送-准备发送消息：{}",bfl);
        	 ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(bfl);
            if (aib != null && LuckygameGoodsIssueStatus.LOTTERY.equals(aib.getStatus())){
            	rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.buyfreelottery.start.exchange"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.buyfreelottery.ready.routing.key"));
                rabbitTemplate.convertAndSend(aib, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties mp=message.getMessageProperties();
                        mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        mp.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,ActivityInfoBuyfree.class);

                        //TODO：动态设置TTL(为了测试方便，暂且设置10s)
                        mp.setExpiration(environment.getProperty("mq.buyfreelottery.start.expire"));
                        return message;
                    }
                });
            }
        }catch (Exception e){
            log.error("0元购计时开奖消息发送-发生异常，消息为：开奖奖品--{}",bfl,e.fillInStackTrace());
        }
    }
    
    /**
     * 用户邀请注册活动异步赠送金券
     * @param fu
     */
    public void recommendStartMessageSend(FrontUser fu) {
        log.info("用户邀请注册活动异步赠送金券-准备发送消息：{}",fu.getUuid());
        try {
    		ActivityInfo ai = this.activityInfoDao.get(ActivityInfoName.RECOMMEND);
    		if(ai != null && ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {//发送开启下一期消息
    			//不需要判断是否需要赠送金币
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.recommend.start.exchange"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.recommend.start.routing.key"));

                rabbitTemplate.convertAndSend(fu, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties=message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,FrontUser.class);
                        return message;
                    }
                });
    		}
		} catch (Exception e) {
			log.error("异步开启下一期0元购通知消息-发生异常，消息为：{}",fu.getUuid(),e.fillInStackTrace());
		}
    }
    
    /**
     * 声明队列
     * @param queueName
     * @param rs
     * @return
     */
    private Queue createQueue(String queueName, String exchangeName) {
    	Map<String, Object> paramsMap = Maps.newHashMap();
    	paramsMap.put("x-dead-letter-exchange", exchangeName);
    	paramsMap.put("x-dead-letter-routing-key", environment.getProperty("mq.robot.start.routing.key"));
		return new Queue(queueName, true, false, false, paramsMap);
    }
    
    /**
     * 创建交换机
     * @param exchangeName
     * @return
     */
    private TopicExchange createExchange(String exchangeName) {
		return new TopicExchange(exchangeName, true, false);
	}
    
    /**
     * 删除队列
     * @param name
     */
    private Boolean deleteQueue(String name) {
    	return rabbitAdmin.deleteQueue(name);
    }
    
    /**
     * 删除交换机
     * @param name
     */
    private Boolean deleteExchange(String name) {
    	return rabbitAdmin.deleteExchange(name);
    }
}




























