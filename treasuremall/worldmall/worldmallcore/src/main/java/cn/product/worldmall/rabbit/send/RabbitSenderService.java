package cn.product.worldmall.rabbit.send;/**
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

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.dao.ActivityInfoBuyfreeDao;
import cn.product.worldmall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.worldmall.dao.ActivityInfoDao;
import cn.product.worldmall.dao.FrontUserBuyfreeOrderDao;
import cn.product.worldmall.dao.FrontUserCheckinHistoryDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserPaymentOrderDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.FrontUserScorelotteryHistoryDao;
import cn.product.worldmall.dao.FrontUserWithdrawOrderDao;
import cn.product.worldmall.dao.GoodsCoverImageDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.dao.WinningInfoDao;
import cn.product.worldmall.dao.WinningInfoReceiveDao;
import cn.product.worldmall.entity.ActivityInfo;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoName;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.worldmall.entity.ActivityInfoBuyfree;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods.ActivityInfoBuyfreeGoodsStatus;
import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.FrontDeviceTokenMessage;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder;
import cn.product.worldmall.entity.FrontUserCheckinHistory;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderGroup;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.FrontUserScorelotteryHistory;
import cn.product.worldmall.entity.FrontUserWithdrawOrder;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.GoodsCoverImage;
import cn.product.worldmall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.worldmall.entity.LuckygameGoods;
import cn.product.worldmall.entity.LuckygameGoods.LuckygameGoodsStatus;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.worldmall.entity.NoticeTopicMessage;
import cn.product.worldmall.entity.NoticeTopicMessage.NoticeTopicMessageStatus;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.RobotSetting;
import cn.product.worldmall.entity.RobotSetting.RobotSettingStatus;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfoReceive;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.mapper.RobotSettingMapper;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.ProvideInfoVO;
import cn.product.worldmall.vo.front.LuckygameGoodsIssueVO;

/**
 * RabbitMQ????????????
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
	 * ??????????????????????????????ready??????
	 * @param frontUser
	 */
	public void robotWorkMessageSend(String frontUser) {
		try {
			RobotSetting rs = this.robotSettingMapper.selectByPrimaryKey(frontUser);
			if(rs != null && RobotSettingStatus.START.equals(rs.getStatus())) {//????????????
				//??????expire rs.getPeriodMin()??????s
				int expire = Utlity.getRandomNum(rs.getPeriodMin(), rs.getPeriodRandom())*1000;//??????ms
				log.info("------------??????????????????--------------------???"+expire);
				String qchange = rs.getRobotId();
				//????????????
				Queue qu = createQueue("worldmall.robot.ready.queue."+qchange, "worldmall.robot.ready.exchange."+qchange);
				rabbitAdmin.declareQueue(qu);
				//???????????????
				TopicExchange ex = createExchange("worldmall.robot.ready.exchange."+qchange);
				rabbitAdmin.declareExchange(ex);
				//????????????
				rabbitAdmin.declareBinding(BindingBuilder.bind(qu).to(robotWorkReadyStartExchange)
						.with("worldmall.robot.ready.routing.key."+qchange));
				
				rabbitAdmin.declareBinding(BindingBuilder.bind(robotWorkReadyStartQueue).to(ex).with(environment.getProperty("mq.robot.start.routing.key")));
				
				rabbitAdmin.purgeQueue("worldmall.robot.ready.queue."+rs.getRobotId());//????????????
				//???????????????ready????????????????????????????????????????????????start??????
				rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.robot.start.exchange"));
                rabbitTemplate.setRoutingKey("worldmall.robot.ready.routing.key."+qchange);
                rabbitTemplate.convertAndSend(rs, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties mp=message.getMessageProperties();
                        mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        mp.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,RobotSetting.class);

                        //TODO???????????????TTL(?????????????????????????????????10s)
                        log.info("------------??????--------------------???"+rabbitTemplate.getRoutingKey());
                        mp.setExpiration(String.valueOf(expire));
                        return message;
                    }
                });
			} else {
				if(rs != null) {
					rabbitAdmin.purgeQueue("worldmall.robot.ready.queue."+rs.getRobotId());//????????????
				}
			}
		} catch (Exception e) {
			log.error("??????????????????????????????ready?????????????????????{}",frontUser,e.fillInStackTrace());
		}
	}
	
	/**
	 * ???????????????????????????
	 * @param frontUser
	 */
	public void robotWorkMessageRemove(String frontUser) {
		try {
			 log.info("??????????????????????????????{}",frontUser);
			String qName = "worldmall.robot.ready.queue."+frontUser;
			String eName = "worldmall.robot.ready.exchange."+frontUser;
			deleteQueue(qName);
			deleteExchange(eName);
		} catch (Exception e) {
			log.error("???????????????????????????????????????{}",frontUser,e.fillInStackTrace());
		}
	}
	
    /**
     * ?????????????????????????????????-?????????????????????????????????????????????????????????????????????????????????
     * @param orderCode
     */
    public void sendKillSuccessOrderExpireMsg(final String orderCode){
        try {
            if (StringUtils.isNotBlank(orderCode)){
            	StringBuilder demoStr = new StringBuilder("????????????demo");
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

                            //TODO???????????????TTL(?????????????????????????????????10s)
                            mp.setExpiration(environment.getProperty("mq.kill.item.success.kill.expire"));
                            return message;
                        }
                    });
                }
            }
        }catch (Exception e){
            log.error("?????????????????????????????????-?????????????????????????????????????????????????????????????????????????????????-???????????????????????????{}",orderCode,e.fillInStackTrace());
        }
    }
    
    /**
     * ????????????????????????
     * @param goodsIssue
     */
    public void lotterySend(String goodsIssue){
        try {
        	 log.info("????????????????????????-?????????????????????{}",goodsIssue);
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

                        //TODO???????????????TTL(?????????????????????????????????10s)
                        mp.setExpiration(environment.getProperty("mq.lottery.start.expire"));
                        return message;
                    }
                });
            }
        }catch (Exception e){
            log.error("????????????????????????-???????????????????????????????????????--{}",goodsIssue,e.fillInStackTrace());
        }
    }

    /**
     * ?????????????????????????????????????????????????????????
     * @param lgUuid
     */
    public void goodsIssueStartMessageSend(String lgUuid) {
        log.info("???????????????????????????????????????-?????????????????????{}",lgUuid);
        try {
    		LuckygameGoods lg = this.luckygameGoodsDao.get(lgUuid);
    		if(lg != null && LuckygameGoodsStatus.NORMAL.equals(lg.getStatus())) {//???????????????????????????
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
			log.error("???????????????????????????????????????-???????????????????????????{}",lgUuid,e.fillInStackTrace());
		}
    }
    
    /**
     * ??????????????????
     */
    public void pushInfoStartMessageSend() {
        log.info("??????????????????-?????????????????????{}");
        DataResult<Object> data = new DataResult<Object>();
        try {
        	//????????????????????????--50?????????
    		Integer pageNum = 1;
    		Integer pageSize = 20;
    		
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
    		
    		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
    		String pathUrl = "";
    		if(sp != null) {
    			pathUrl = sp.getParamValue();
    		} else {
    			pathUrl = Utlity.IMAGE_PATH_URL;
    		}
    		//??????????????????
			SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sprate != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
			}
			
    		List<LuckygameGoodsIssueVO> listvo = new ArrayList<LuckygameGoodsIssueVO>();
    		if(list != null && list.size() > 0) {
    			for(LuckygameGoodsIssue lgi : list) {
    				LuckygameGoodsIssueVO lgivo = new LuckygameGoodsIssueVO(lgi);
    				lgivo.setPrice(lgivo.getdPrice().divide(rate));//?????????????????????
    				Goods good = this.goodsDao.get(lgi.getGoodsId());
    				lgivo.setCode(good.getCode());
    				lgivo.setDescription("");
    				lgivo.setCoverImg("");
    				lgivo.setPrice(good.getPrice());
    				//???????????????
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
    				
    				if(LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {//?????????
    					lgivo.setTimeLine(lgi.getLotterytime().getTime()+Utlity.TIMELINE-System.currentTimeMillis());
    				}
    				
    				//??????????????????
    				LuckygameGoods lg = this.luckygameGoodsDao.get(lgi.getLuckygameGoods());
    				if(lg != null) {
    					lgivo.setTabs(lg.getTabs() == null ? "" : lg.getTabs());
    				}
    				
    				//?????????????????????
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
    		//????????????????????????????????????
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
			log.error("??????????????????-???????????????????????????{}",data.getData(),e.fillInStackTrace());
		}
    }
    
    /**
     * ??????????????????
     * @param wilist
     */
    public void pushWinInfoStartMessageSend(List<WinningInfo> wilist) {
        log.info("??????????????????-?????????????????????{}");
        try {
        	List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
        	//??????????????????
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
            				nickname = "?????????";
            			} else if(FrontUserPaymentOrderGroup.RAIDER.equals(fupo.getPaymentGroup())){
            				nickname = "?????????";
            			}
                	}
                	LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
            		goodsname = lgi.getShortTitle();
            		price = lgi.getdPrice().divide(rate);
            		Goods good = this.goodsDao.get(lgi.getGoodsId());
            		if(good != null) {
            			price = good.getPrice();
            		}
            	}
            	
            	Map<String, Object> data = new HashMap<String, Object>();
            	data.put("nickname", nickname);
            	data.put("goodsname", goodsname);
            	data.put("price", price);//????????????
            	dataList.add(data);
        	}
        	Map<String, String> params = new HashMap<String, String>();
    		params.put("status", ResultStatusType.SUCCESS.name());
    		params.put("message", "success");
    		params.put("data", JSONUtils.obj2json(dataList));
    		params.put("dataType", Constants.SOCKET_DATA_TYPE_WININFO);
        	
    		//????????????????????????????????????
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
			log.error("??????????????????-???????????????????????????{}",wilist.isEmpty(),e.fillInStackTrace());
		}
    }
    
    /**
     * ????????????????????????
     * @param fum
     */
    public void messageStartMessageSend(FrontUserMessage fum) {
        log.info("????????????????????????????????????-?????????????????????{}",fum.getTitle());
        try {
    		if(fum != null && FrontUserMessageStatus.NORMAL.equals(fum.getStatus())) {//????????????
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
			log.error("????????????????????????????????????-???????????????????????????{}",fum.getTitle(),e.fillInStackTrace());
		}
    }
    
    /**
     * ???????????????????????????
     * @param lgUuid
     */
    public void smsStartMessageSend(FrontUserMessage fum, String temp_id) {
        log.info("???????????????????????????????????????-?????????????????????{}",temp_id);
        try {
        	Map<String, Object> params = new HashMap<String, Object>();
        	String codestr = Utlity.getCaptcha(4);
        	String content = "";
        	Map<String, String> vars = new HashMap<String, String>();
        	vars.put("temp_id", temp_id);
        	//????????????????????????????????????
        	switch (temp_id) {
				case SendSmsNew.TREASUREMALL_SH_TEMP:
					vars.put("code", codestr);
					content = "?????????????????????????????????"+codestr+"??????????????????3??????????????????";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_DELIVERY:
					if(FrontUserMessageSourceType.ORDER_TYPE_USER_RECEIVE.equals(fum.getSourceType())) {
						WinningInfoReceive wir = this.winningInfoReceiveDao.get(fum.getSourceId());
						if(wir != null) {
							Goods goods = this.goodsDao.get(wir.getGoodsId());
							String goodsName = "??????";
							if(goods != null) {
								goodsName = goods.getShortname();
							}
							ProvideInfoVO pivo = JSONUtils.json2obj(wir.getProvideInfo(), ProvideInfoVO.class);
							String company = "??????";
							String num = "?????????APP??????";
							if(pivo != null) {
								company = Constants.companyInfoMap.get(pivo.getCompany()) == null ? "??????" : Constants.companyInfoMap.get(pivo.getCompany());
								num = pivo.getExpressNumber();
							}
							vars.put("code1", goodsName);
							vars.put("code2", company);
							vars.put("code3", num);
							content = "????????????????????????"+goodsName+"????????????"+company+"???????????????????????????"+num+"????????????????????????????????????????????????????????????????????????";
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_BUYFREE.equals(fum.getSourceType())) {
						FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(fum.getSourceId());
						if(fubo != null) {
							Goods goods = this.goodsDao.get(fubo.getGoodsId());
							String goodsName = "??????";
							if(goods != null) {
								goodsName = goods.getShortname();
							}
							ProvideInfoVO pivo = JSONUtils.json2obj(fubo.getProvideInfo(), ProvideInfoVO.class);
							String company = "??????";
							String num = "?????????APP??????";
							if(pivo != null) {
								company = Constants.companyInfoMap.get(pivo.getCompany()) == null ? "??????" : Constants.companyInfoMap.get(pivo.getCompany());
								num = pivo.getExpressNumber();
							}
							vars.put("code1", goodsName);
							vars.put("code2", company);
							vars.put("code3", num);
							content = "????????????????????????"+goodsName+"????????????"+company+"???????????????????????????"+num+"????????????????????????????????????????????????????????????????????????";
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_CHECKIN.equals(fum.getSourceType())) {
						FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(fum.getSourceId());
						if(fuch != null) {
//							goodsName = fuch.getPrizeTitle();
							Goods goods = this.goodsDao.get(fuch.getPrize());
							String goodsName = "??????";
							if(goods != null) {
								goodsName = goods.getShortname();
							}
							ProvideInfoVO pivo = JSONUtils.json2obj(fuch.getProvideInfo(), ProvideInfoVO.class);
							String company = "??????";
							String num = "?????????APP??????";
							if(pivo != null) {
								company = Constants.companyInfoMap.get(pivo.getCompany()) == null ? "??????" : Constants.companyInfoMap.get(pivo.getCompany());
								num = pivo.getExpressNumber();
							}
							vars.put("code1", goodsName);
							vars.put("code2", company);
							vars.put("code3", num);
							content = "????????????????????????"+goodsName+"????????????"+company+"???????????????????????????"+num+"????????????????????????????????????????????????????????????????????????";
						}
					} else if (FrontUserMessageSourceType.ORDER_TYPE_USER_SCORELOTTERY.equals(fum.getSourceType())) {
						FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(fum.getSourceId());
						if(fush != null) {
//							goodsName = fuch.getPrizeTitle();
							Goods goods = this.goodsDao.get(fush.getPrize());
							String goodsName = "??????";
							if(goods != null) {
								goodsName = goods.getShortname();
							}
							ProvideInfoVO pivo = JSONUtils.json2obj(fush.getProvideInfo(), ProvideInfoVO.class);
							String company = "??????";
							String num = "?????????APP??????";
							if(pivo != null) {
								company = Constants.companyInfoMap.get(pivo.getCompany()) == null ? "??????" : Constants.companyInfoMap.get(pivo.getCompany());
								num = pivo.getExpressNumber();
							}
							vars.put("code1", goodsName);
							vars.put("code2", company);
							vars.put("code3", num);
							content = "????????????????????????"+goodsName+"????????????"+company+"???????????????????????????"+num+"????????????????????????????????????????????????????????????????????????";
						}
					}
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW_FAIL:
					content = "???????????????????????????,?????????????????????????????????????????????????????????";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE:
					String recharge = "";
					FrontUserHistory fuhRecharge = this.frontUserHistoryDao.get(fum.getSourceId());
					if(fuhRecharge != null) {
						FrontUserRechargeOrder furo = this.frontUserRechargeOrderDao.get(fuhRecharge.getOrderId());
						recharge = furo.getAmount().toString(); 
					}
					vars.put("code", recharge);
					content = "???????????????"+recharge+"??????????????????????????????????????????????????????";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW:
					String withdraw = "";
					FrontUserHistory fuhWithdraw = this.frontUserHistoryDao.get(fum.getSourceId());
					if(fuhWithdraw != null) {
						FrontUserWithdrawOrder fuwo = this.frontUserWithdrawOrderDao.get(fuhWithdraw.getOrderId());
						withdraw = fuwo.getActualAmount().toString(); 
					}
					vars.put("code", withdraw);
					content = "???????????????"+withdraw+"??????????????????????????????????????????????????????";
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
					content = "????????????????????????????????????"+goodsName+"???????????????APP??????????????????";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_GROUPWIN:
					String groupName = "";
					if(FrontUserMessageSourceType.ORDER_TYPE_USER_WIN.equals(fum.getSourceType())) {
						FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(fum.getSourceId());
						if(fupo != null) {
							if(FrontUserPaymentOrderGroup.LUCKY.equals(fupo.getPaymentGroup())) {
								groupName = "?????????";
							} else if(FrontUserPaymentOrderGroup.RAIDER.equals(fupo.getPaymentGroup())) {
								groupName = "?????????";
							}
						}
					}
					vars.put("name", groupName);
					//??????????????????????????????PK??????????????????%name%???????????????????????????????????????????????????????????????????????????APP??????????????????
					content = "??????????????????????????????PK??????????????????"+groupName+"???????????????????????????????????????????????????????????????????????????APP??????????????????";
					break;
				case SendSmsNew.TREASUREMALL_SH_TEMP_RECEIVE:
					//??????????????????
					SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
					BigDecimal rate = BigDecimal.ONE;
					if(sp != null) {
						rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
					}
					String name = "";
					String money = "";
					if(FrontUserMessageSourceType.ORDER_TYPE_USER_RECEIVE.equals(fum.getSourceType())) {
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
					content = "??????????????????"+name+"??????????????????"+money+"??????????????????????????????";
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
			log.error("????????????????????????????????????-???????????????????????????{}",temp_id,e.fillInStackTrace());
		}
    }
    

    
    /**
     * ????????????????????????
     * @param fum
     */
    public void messageStartSnsMessageSend(FrontDeviceTokenMessage fdtm) {
        log.info("????????????????????????????????????-?????????????????????{}",fdtm.getTitle());
        try {
    		if(fdtm != null && FrontUserMessageStatus.NORMAL.equals(fdtm.getStatus())) {//????????????
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.noticemessage.start.exchange"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.noticemessage.start.routing.key"));

                rabbitTemplate.convertAndSend(fdtm, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties=message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,FrontDeviceTokenMessage.class);
                        return message;
                    }
                });
    		}
		} catch (Exception e) {
			log.error("????????????????????????????????????-???????????????????????????{}",fdtm.getTitle(),e.fillInStackTrace());
		}
    }

    
    /**
     * ???????????????SNS??????
     * @param fum
     */
    public void messageStartSnsMessageSend(NoticeTopicMessage ntm) {
        log.info("???????????????SNS??????-?????????????????????{}",ntm.getTitle());
        try {
    		if(ntm != null && NoticeTopicMessageStatus.NORMAL.equals(ntm.getStatus())) {//????????????
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.topicnoticemessage.start.exchange"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.topicnoticemessage.start.routing.key"));

                rabbitTemplate.convertAndSend(ntm, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties=message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,NoticeTopicMessage.class);
                        return message;
                    }
                });
    		}
		} catch (Exception e) {
			log.error("???????????????SNS??????-???????????????????????????{}",ntm.getTitle(),e.fillInStackTrace());
		}
    }
    
    /**
     * ???????????????????????????????????????????????????0??????
     * @param lgUuid
     */
    public void buyfreeStartMessageSend(String bfUuid) {
        log.info("?????????????????????0??????????????????-?????????????????????{}",bfUuid);
        try {
    		ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(bfUuid);
    		if(aibg != null && ActivityInfoBuyfreeGoodsStatus.NORMAL.equals(aibg.getStatus())) {//???????????????????????????
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
			log.error("?????????????????????0??????????????????-???????????????????????????{}",bfUuid,e.fillInStackTrace());
		}
    }
    
    /**
     * 0??????????????????????????????
     * @param goodsIssue
     */
    public void buyfreelotterySend(String bfl){
        try {
        	 log.info("0??????????????????????????????-?????????????????????{}",bfl);
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

                        //TODO???????????????TTL(?????????????????????????????????10s)
                        mp.setExpiration(environment.getProperty("mq.buyfreelottery.start.expire"));
                        return message;
                    }
                });
            }
        }catch (Exception e){
            log.error("0??????????????????????????????-???????????????????????????????????????--{}",bfl,e.fillInStackTrace());
        }
    }
    
    /**
     * ??????????????????????????????????????????
     * @param fu
     */
    public void recommendStartMessageSend(FrontUser fu) {
        log.info("??????????????????????????????????????????-?????????????????????{}",fu.getUuid());
        try {
    		ActivityInfo ai = this.activityInfoDao.get(ActivityInfoName.RECOMMEND);
    		if(ai != null && ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {//???????????????????????????
    			//???????????????????????????????????????
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
			log.error("??????????????????????????????????????????????????????-???????????????????????????{}",fu.getUuid(),e.fillInStackTrace());
		}
    }
    
    /**
     * ??????????????????????????????AWS-SNS-endpointArn??????
     * @param fdt
     */
    public void endpointarnStartMessageSend(FrontDeviceToken fdt) {
        log.info("??????????????????????????????AWS-SNS-endpointArn??????-?????????????????????{}",fdt.getUuid());
        try {
    		if(fdt != null && !Utlity.checkStringNull(fdt.getDeviceToken())) {//????????????SNS-APP??????
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.endpointarn.start.exchange"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.endpointarn.start.routing.key"));

                rabbitTemplate.convertAndSend(fdt, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties=message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,FrontDeviceToken.class);
                        return message;
                    }
                });
    		}
		} catch (Exception e) {
			log.error("??????????????????????????????AWS-SNS-endpointArn??????????????????-???????????????????????????{}",fdt.getUuid(),e.fillInStackTrace());
		}
    }
    
    
    /**
     * ??????????????????AWS??????
     * @param fdt
     */
    public void bindingTopicSend(FrontDeviceToken fdt) {
        log.info("??????????????????AWS??????-?????????????????????{}",fdt.getUuid());
        try {
    		if(fdt != null && !Utlity.checkStringNull(fdt.getTopic())) {//????????????SNS-????????????
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.bindingtopic.start.exchange"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.bindingtopic.start.routing.key"));

                rabbitTemplate.convertAndSend(fdt, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties=message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,FrontDeviceToken.class);
                        return message;
                    }
                });
    		}
		} catch (Exception e) {
			log.error("??????????????????AWS??????-???????????????????????????{}",fdt.getUuid(),e.fillInStackTrace());
		}
    }
    
    /**
     * ????????????
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
     * ???????????????
     * @param exchangeName
     * @return
     */
    private TopicExchange createExchange(String exchangeName) {
		return new TopicExchange(exchangeName, true, false);
	}
    
    /**
     * ????????????
     * @param name
     */
    private Boolean deleteQueue(String name) {
    	return rabbitAdmin.deleteQueue(name);
    }
    
    /**
     * ???????????????
     * @param name
     */
    private Boolean deleteExchange(String name) {
    	return rabbitAdmin.deleteExchange(name);
    }
}




























