package cn.product.worldmall.rabbit.receive;/**
 * Created by Administrator on 2019/6/21.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.ActivityInfoBuyfreeDao;
import cn.product.worldmall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.worldmall.dao.FrontUserBuyfreeOrderDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.entity.ActivityInfoBuyfree;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.ActivityInfoBuyfree.ActivityInfoBuyfreeStatus;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder.FrontUserBuyfreeOrderStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;

/**
 * RabbitMQ接收消息服务
 **/
@Service
public class RabbitReceive4BuyfreeLotteryrService {

    public static final Logger log= LoggerFactory.getLogger(RabbitReceive4BuyfreeLotteryrService.class);


    @Autowired
    private Environment environment;
    
    @Autowired
    private RabbitSenderService rabbitSenderService;

    @Autowired
    private FrontUserMessageDao frontUserMessageDao;
    
    //使用基于zookeeper 有序集群同步锁
    /*
     * 注册curator
     */
    @Autowired
    private CuratorFramework curatorFramework;
	
	@Autowired
	private FrontUserBuyfreeOrderDao frontUserBuyfreeOrderDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private ActivityInfoBuyfreeGoodsDao activityInfoBuyfreeGoodsDao;
	
	@Autowired
	private ActivityInfoBuyfreeDao activityInfoBuyfreeDao;
    /**
     * 自动开奖
     * @param aib
     */
    @RabbitListener(queues = {"${mq.buyfreelottery.start.queue}"},containerFactory = "singleListenerContainer")
    public void lotteryStart(ActivityInfoBuyfree aibOld) {
    	try {
    		ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(aibOld.getUuid());
    		if (aib != null && ActivityInfoBuyfreeStatus.LOTTERY.equals(aib.getStatus())){//开奖
    			List<FrontUserBuyfreeOrder> listLuck = new ArrayList<FrontUserBuyfreeOrder>();//中奖订单
    			List<ActivityInfoBuyfree> listaib = new ArrayList<ActivityInfoBuyfree>();//已完成期数
    			
    			Timestamp lotterytime = aib.getLotterytime();
				//由此时间向前取100条
    			Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("timeend", Utlity.timeSpanToString(lotterytime));
				searchMap.put("offSet", 0);
				searchMap.put("pageSize", 100);
				List<FrontUserBuyfreeOrder> listorder = this.frontUserBuyfreeOrderDao.getListByParams(searchMap);//计算用的记录
				if(listorder != null && listorder.size() > 0) {
					Map<Integer, FrontUserBuyfreeOrder> orderMap = new HashMap<Integer, FrontUserBuyfreeOrder>();
					Map<Integer, FrontUserBuyfreeOrder> realOrderMap = new HashMap<Integer, FrontUserBuyfreeOrder>();
					//查询实际订单列表 封装map备用
					searchMap.clear();
					searchMap.put("activityInfoBuyfree", aib.getUuid());
					searchMap.put("status", FrontUserBuyfreeOrderStatus.NORMAL);
					List<FrontUserBuyfreeOrder> listrealOrder = this.frontUserBuyfreeOrderDao.getListByParams(searchMap);
					if(listrealOrder != null && listrealOrder.size() > 0) {
						for(FrontUserBuyfreeOrder fubo : listrealOrder) {
							addOrderMap(fubo, realOrderMap);
						}
					}
					//计算数值A
					long calculateNum = 0;
					if(listorder.size() == 100) {
						for(FrontUserBuyfreeOrder fubo : listorder) {
							calculateNum += addOrderMap(fubo, orderMap);
						}
					} else {
						for(FrontUserBuyfreeOrder fubo : listorder) {
							calculateNum += addOrderMap(fubo, orderMap);
						}
						int remain = 100 - listorder.size();
						int k = 0;
						for(int i = 0; i < remain; i++) {
							if(k < listorder.size()) {
								FrontUserBuyfreeOrder fubo = listorder.get(k);
								calculateNum += addOrderMap(fubo, orderMap);
								k++;
							} else {
								k = 0;
								FrontUserBuyfreeOrder fubo = listorder.get(k);
								calculateNum += addOrderMap(fubo, orderMap);
								k++;
							}
						}
					}
					
					//计算幸运号码
					long anum = calculateNum%aib.getShares().intValue();
					anum = Math.abs(anum);
					int luckyNum = BigDecimal.valueOf(anum).add(BigDecimal.valueOf(Double.valueOf(Utlity.LUCKY_NUM_START))).intValue();
					//从真实订单列表中获取订单信息
					FrontUserBuyfreeOrder fubo = realOrderMap.get(luckyNum);
					if(fubo == null) {
						throw new TransactionException("开奖异常！");
					}
					fubo.setIsLucky(true);
					fubo.setStatus(FrontUserBuyfreeOrderStatus.WIN);
					listLuck.add(fubo);
					for(FrontUserBuyfreeOrder order : listrealOrder) {
						if(!order.getUuid().equals(fubo.getUuid())) {
							order.setIsLucky(false);
							order.setStatus(FrontUserBuyfreeOrderStatus.NOWIN);
							listLuck.add(order);
						}
					}
					
					//记录开奖时间
					aib.setStatus(ActivityInfoBuyfreeStatus.FINISHED);
					aib.setFinishedtime(new Timestamp(System.currentTimeMillis()));
					aib.setLuckyNumber(Integer.valueOf(luckyNum));
					listaib.add(aib);
					
					fubo.setWinningTime(aib.getFinishedtime());
					
					this.activityInfoBuyfreeDao.taskLottery(listLuck, listaib, null, null, null);
					
					/*
					 * 异步处理-暂不发送
					 */
					//中奖通知消息
					FrontUserMessage fum = new FrontUserMessage();
					fum.setUuid(UUID.randomUUID().toString());
					fum.setFrontUser(fubo.getFrontUser());
					fum.setFrontUserShowId(fubo.getFrontUserShowId());
					fum.setTitle("You Win");
//					fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fubo.getCreatetime())+"参与的0元购活动，"+aib.getGoodsShortTitle()+"商品抽奖，中奖啦，快去看看吧！");
					fum.setContent("You participated in the free buy activity "
//							+ "on " + Utlity.timeSpanToUsString(fubo.getCreatetime()) 
					+ " , the "+aib.getGoodsShortTitle()+", you won the prize, Go see it!");
					fum.setSourceId(fubo.getUuid());
					fum.setStatus(FrontUserMessageStatus.NORMAL);
					fum.setType(FrontUserMessageType.USER_WIN);
					fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_BUYFREE);
					fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
					this.frontUserMessageDao.sendMessage(fum);
					
					//判断是否是机器人，机器人不予发送中将短信通知
					FrontUser fu = this.frontUserDao.get(fubo.getFrontUser());
					if(fu != null && FrontUserType.NORMAL.equals(fu.getType())) {
						this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WINNING);
					}
				}
    		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("自动开奖-监听者-发生异常：",e.fillInStackTrace());
			return;
		}
    }
    
	/**
	 * 重复代码封装
	 * 处理luckyNum和order的对应关系
	 * @param fubo
	 * @param calculateNum
	 * @param orderMap
	 */
	public long addOrderMap(FrontUserBuyfreeOrder fubo, Map<Integer, FrontUserBuyfreeOrder> orderMap) {
		orderMap.put(fubo.getSharenum(), fubo);
		long timeNum = Utlity.getTimeNum(fubo.getCreatetime());
		return timeNum;
	}
}












