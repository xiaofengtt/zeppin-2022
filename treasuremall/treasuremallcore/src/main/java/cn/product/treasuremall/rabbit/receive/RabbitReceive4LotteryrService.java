package cn.product.treasuremall.rabbit.receive;/**
 * Created by Administrator on 2019/6/21.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.controller.base.TransactionException;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserMessageDao;
import cn.product.treasuremall.dao.FrontUserPaidNumberDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.LuckygameGoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.treasuremall.entity.FrontUserPaidNumber;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderGroup;
import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderStatus;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.entity.WinningInfo.WinningInfoType;
import cn.product.treasuremall.entity.WinningInfoReceive;
import cn.product.treasuremall.entity.WinningInfoReceive.WinningInfoReceiveStatus;
import cn.product.treasuremall.entity.WinningInfoReceive.WinningInfoReceiveType;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.rabbit.send.RabbitSenderService;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.SendSmsNew;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.SharenumsVO;

/**
 * RabbitMQ接收消息服务
 **/
@Service
public class RabbitReceive4LotteryrService {

    public static final Logger log= LoggerFactory.getLogger(RabbitReceive4LotteryrService.class);


    @Autowired
    private Environment environment;
    
    @Autowired
    private RabbitSenderService rabbitSenderService;

    @Autowired
    private LuckygameGoodsDao luckygameGoodsDao;
    
    @Autowired
    private FrontUserMessageDao frontUserMessageDao;
    
    @Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
    
    //使用基于zookeeper 有序集群同步锁
    /*
     * 注册curator
     */
    @Autowired
    private CuratorFramework curatorFramework;
	
	@Autowired
	private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
	private FrontUserPaidNumberDao frontUserPaidNumberDao;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
    /**
     * 自动开奖
     * @param lgi
     */
    @RabbitListener(queues = {"${mq.lottery.start.queue}"},containerFactory = "singleListenerContainer")
    public void lotteryStart(LuckygameGoodsIssue lgiOld) {
    	List<InterProcessSemaphoreMutex> listMutex = new ArrayList<InterProcessSemaphoreMutex>();//记录每个抽奖拿到的锁
    	try {
    		/*
			 * 获取锁-声明子节点
			 */
			InterProcessSemaphoreMutex mutex=new InterProcessSemaphoreMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+"lottery-"+lgiOld.getUuid()+"-lock");
			listMutex.add(mutex);
			log.info("获取zookeeper锁，开奖同步锁"+lgiOld.getUuid());
			if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
	    		LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.getById(lgiOld.getUuid());
	    		if(lgi != null && LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {
    				if(Constants.GAME_TYPE_TRADITION.equals(lgi.getGameType())) {
        				traditionLottery(lgi);
        			} else if (Constants.GAME_TYPE_GROUP2.equals(lgi.getGameType())) {
        				group2Lottery(lgi);
        			}
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("自动开奖-监听者-发生异常：",e.fillInStackTrace());
			return;
		} finally {
        	if(listMutex.size() > 0) {//依次解锁
        		log.info("自动开奖-监听者-本次开奖结束！释放锁");
        		for(InterProcessSemaphoreMutex mutex : listMutex) {
        			if (mutex!=null){
                        try {
							mutex.release();
						} catch (Exception e) {
							e.printStackTrace();
						}
                    }
        		}
        	}
        }
    }
    
    /**
     * 传统玩法开奖
     * @param lgi
     * @throws TransactionException
     */
    public void traditionLottery(LuckygameGoodsIssue lgi) throws TransactionException {
    	//从数据库或缓存更新最新的商品数据，避免出现缓存冗余问题，导致无法开奖
    	lgi = this.luckygameGoodsIssueDao.getById(lgi.getUuid());
    	if (lgi != null && LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())){//开奖
    		//20201116增加前置开奖判断
    		boolean isLottery = false;
    		if(lgi.getLuckyNumber() != null && lgi.getLuckyNumber().intValue() > 0) {
    			isLottery = true;
    		}
    		
			List<FrontUserPaymentOrder> listLuck = new ArrayList<FrontUserPaymentOrder>();//中奖订单
			List<WinningInfo> listwin = new ArrayList<WinningInfo>();//中奖信息
			List<LuckygameGoodsIssue> listlgi = new ArrayList<LuckygameGoodsIssue>();//已完成期数
			Map<String, FrontUserAccount> mapfua = new HashMap<String, FrontUserAccount>();
			
			Timestamp lotterytime = lgi.getLotterytime();
			//由此时间向前取100条
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("timeend", Utlity.timeSpanToString(lotterytime));
			searchMap.put("offSet", 0);
			searchMap.put("pageSize", 100);
			List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);//计算用的记录
			if(listorder != null && listorder.size() > 0) {
				Map<Integer, FrontUserPaymentOrder> orderMap = new HashMap<Integer, FrontUserPaymentOrder>();
				Map<Integer, FrontUserPaymentOrder> realOrderMap = new HashMap<Integer, FrontUserPaymentOrder>();
				Map<String, Integer> realBuyCountMap = new HashMap<String, Integer>();
				//查询实际订单列表 封装map备用
				searchMap.clear();
				searchMap.put("goodsIssue", lgi.getUuid());
				searchMap.put("status", FrontUserPaymentOrderStatus.SUCCESS);
				List<FrontUserPaymentOrder> listrealOrder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
				if(listrealOrder != null && listrealOrder.size() > 0) {
					for(FrontUserPaymentOrder fupo : listrealOrder) {
						addOrderMap(fupo, realOrderMap, realBuyCountMap);
					}
				}
				int luckyNum = 0;
				if(!isLottery) {
					//计算数值A
					long calculateNum = 0;
					if(listorder.size() == 100) {
						for(FrontUserPaymentOrder fupo : listorder) {
							calculateNum += addOrderMap(fupo, orderMap);
						}
					} else {
						for(FrontUserPaymentOrder fupo : listorder) {
							calculateNum += addOrderMap(fupo, orderMap);
						}
						int remain = 100 - listorder.size();
						int k = 0;
						for(int i = 0; i < remain; i++) {
							if(k < listorder.size()) {
								FrontUserPaymentOrder fupo = listorder.get(k);
								calculateNum += addOrderMap(fupo, orderMap);
								k++;
							} else {
								k = 0;
								FrontUserPaymentOrder fupo = listorder.get(k);
								calculateNum += addOrderMap(fupo, orderMap);
								k++;
							}
						}
					}
					
					//计算幸运号码
					long anum = calculateNum%lgi.getShares().intValue();
					anum = Math.abs(anum);
					luckyNum = BigDecimal.valueOf(anum).add(BigDecimal.valueOf(Double.valueOf(Utlity.LUCKY_NUM_START))).intValue();
				} else {
					luckyNum = lgi.getLuckyNumber();
				}
				
				//从真实订单列表中获取订单信息
				FrontUserPaymentOrder fupo = realOrderMap.get(luckyNum);
				if(fupo == null) {
					throw new TransactionException("开奖异常！");
				}
				fupo.setIsLucky(true);
				fupo.setLuckyNumber(Integer.valueOf(luckyNum));
				listLuck.add(fupo);
				for(FrontUserPaymentOrder order : listrealOrder) {
					if(!order.getUuid().equals(fupo.getUuid())) {
						order.setIsLucky(false);
						listLuck.add(order);
					}
				}
				
				//记录开奖时间
				lgi.setStatus(LuckygameGoodsIssueStatus.FINISHED);
				lgi.setFinishedtime(new Timestamp(System.currentTimeMillis()));
				lgi.setLuckyNumber(Integer.valueOf(luckyNum));
				listlgi.add(lgi);
				
				//封装中奖纪录
				//获取中奖用户参与记录汇总--20200420修改增加
				//20200714改为由前边已查询出的订单，处理的map，进行buyCount的获取
//				searchMap.clear();
//				searchMap.put("frontUser", fupo.getFrontUser());
//				searchMap.put("goodsIssue", fupo.getGoodsIssue());
//				searchMap.put("status", FrontUserPaymentOrderStatus.SUCCESS);
//				List<FrontUserPaymentOrder> listOrder = this.frontUserPaymentOrderDao.getGroupListByParams(searchMap);
				WinningInfo wi = new WinningInfo();
				wi.setUuid(UUID.randomUUID().toString());
				wi.setFrontUser(fupo.getFrontUser());
				wi.setWinningTime(lgi.getFinishedtime());
				wi.setGoodsIssue(fupo.getGoodsIssue());
				wi.setGoodsId(lgi.getGoodsId());
				wi.setShowId(fupo.getFrontUserShowId());
				wi.setGameType(fupo.getGameType());
				wi.setLuckynum(Integer.valueOf(luckyNum));
				wi.setPaymentDAmount(fupo.getdAmount());
				wi.setBuyCount(realBuyCountMap.get(fupo.getFrontUser()));
				wi.setDealPrice(lgi.getdPrice());
				wi.setType(WinningInfoType.NORMAL);//未领奖
				wi.setOrderId(fupo.getUuid());
				listwin.add(wi);
				
				//更新用户账户信息（异步？）
				FrontUserAccount fua = mapfua.get(fupo.getFrontUser());
				if(fua == null) {
					fua = this.frontUserAccountDao.get(fupo.getFrontUser());
				}
				fua.setTotalWinning(fua.getTotalWinning().add(wi.getDealPrice()));
				fua.setWinningTimes(fua.getWinningTimes() + 1);
				mapfua.put(fua.getFrontUser(), fua);
				
				this.luckygameGoodsIssueDao.taskLottery(mapfua, listLuck, listwin, listlgi, null, null, null);
				
				/*
				 * 异步处理
				 */
				//中奖通知消息
				FrontUserMessage fum = new FrontUserMessage();
				fum.setUuid(UUID.randomUUID().toString());
				fum.setFrontUser(fupo.getFrontUser());
				fum.setFrontUserShowId(fupo.getFrontUserShowId());
				fum.setTitle("提示，中奖通知");
				fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fupo.getCreatetime())+"参与的"+lgi.getShortTitle()+"商品抽奖，中奖啦，快去看看吧！");
				fum.setSourceId(fupo.getUuid());
				fum.setStatus(FrontUserMessageStatus.NORMAL);
				fum.setType(FrontUserMessageType.USER_WIN);
				fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WIN);
				fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
				this.frontUserMessageDao.sendMessage(fum);
				
				//判断是否是机器人，机器人不予发送中将短信通知
				FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
				if(fu != null && FrontUserType.NORMAL.equals(fu.getType())) {
					this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WINNING);
				}
				
				if(listwin.size() > 0) {
					rabbitSenderService.pushWinInfoStartMessageSend(listwin);//异步发送开奖信息至前端
				}
			}
		}
    }
    /**
     * 战队玩法开奖算法
     * @param lgi
     * @throws TransactionException
     */
    public void group2Lottery(LuckygameGoodsIssue lgi) throws TransactionException {
    	//从数据库或缓存更新最新的商品数据，避免出现缓存冗余问题，导致无法开奖
    	lgi = this.luckygameGoodsIssueDao.getById(lgi.getUuid());
    	if (lgi != null && LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())){//开奖
    		
    		String groupStr = "";
			List<FrontUserPaymentOrder> listLuck = new ArrayList<FrontUserPaymentOrder>();//中奖订单
			List<WinningInfo> listwin = new ArrayList<WinningInfo>();//中奖信息
			List<LuckygameGoodsIssue> listlgi = new ArrayList<LuckygameGoodsIssue>();//已完成期数
			Map<String, FrontUserAccount> mapfua = new HashMap<String, FrontUserAccount>();
			List<WinningInfoReceive> insertReceive = new ArrayList<WinningInfoReceive>();//中奖信息
			List<FrontUserHistory> insertHistory = new ArrayList<FrontUserHistory>();//中奖信息
			List<FrontUserMessage> fumList = new ArrayList<FrontUserMessage>();//站内信通知
			Timestamp lotterytime = lgi.getLotterytime();
			//由此时间向前取100条
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("timeend", Utlity.timeSpanToString(lotterytime));
			searchMap.put("offSet", 0);
			searchMap.put("pageSize", 100);
			List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);//计算用的记录
			if(listorder != null && listorder.size() > 0) {
				Map<Integer, FrontUserPaymentOrder> orderMap = new HashMap<Integer, FrontUserPaymentOrder>();
				Map<Integer, FrontUserPaymentOrder> realOrderMap = new HashMap<Integer, FrontUserPaymentOrder>();
				Map<String, List<FrontUserPaymentOrder>> realGroupMap = new HashMap<String, List<FrontUserPaymentOrder>>();
				Map<String, List<FrontUserPaymentOrder>> realWinOrderMap = new HashMap<String, List<FrontUserPaymentOrder>>();
				//查询实际订单列表 封装map备用
				searchMap.clear();
				searchMap.put("goodsIssue", lgi.getUuid());
				searchMap.put("status", FrontUserPaymentOrderStatus.SUCCESS);
				List<FrontUserPaymentOrder> listrealOrder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
				if(listrealOrder != null && listrealOrder.size() > 0) {
					for(FrontUserPaymentOrder fupo : listrealOrder) {
						//realGroupMap包括所属战队的相关订单
						addOrderMap(fupo, realOrderMap, null, realGroupMap);
					}
				}
				//计算数值A
				long calculateNum = 0;
				if(listorder.size() == 100) {
					for(FrontUserPaymentOrder fupo : listorder) {
						calculateNum += addOrderMap(fupo, orderMap);
					}
				} else {
					for(FrontUserPaymentOrder fupo : listorder) {
						calculateNum += addOrderMap(fupo, orderMap);
					}
					int remain = 100 - listorder.size();
					int k = 0;
					for(int i = 0; i < remain; i++) {
						if(k < listorder.size()) {
							FrontUserPaymentOrder fupo = listorder.get(k);
							calculateNum += addOrderMap(fupo, orderMap);
							k++;
						} else {
							k = 0;
							FrontUserPaymentOrder fupo = listorder.get(k);
							calculateNum += addOrderMap(fupo, orderMap);
							k++;
						}
					}
				}
				
				//计算幸运号码
				long anum = calculateNum%lgi.getShares().intValue();
				anum = Math.abs(anum);
				int luckyNum = BigDecimal.valueOf(anum).add(BigDecimal.valueOf(Double.valueOf(Utlity.LUCKY_NUM_START))).intValue();
				//从真实订单列表中获取订单信息
				FrontUserPaymentOrder fupo = realOrderMap.get(luckyNum);
				if(fupo == null) {
					throw new TransactionException("开奖异常！");
				}
				
				/*
				 * 战队玩法中奖订单处理逻辑
				 * 获取中奖号码对应订单由此确认中奖号码所属战队
				 * 更新抽奖期数信息lgi的luckyGroup信息
				 * 更新对应战队所含订单的中奖信息
				 * 异步自动兑奖？需要确认
				 */
				List<FrontUserPaymentOrder> isLucky = realGroupMap.get(fupo.getPaymentGroup());
				if(isLucky == null || isLucky.isEmpty()) {
					throw new TransactionException("抽奖订单为空，开奖异常！m");
				}
				
				//处理中奖订单
				for(FrontUserPaymentOrder order : isLucky) {
					order.setIsLucky(true);
					if(fupo.getUuid().equals(order.getUuid())) {
						order.setLuckyNumber(Integer.valueOf(luckyNum));//mvp
					}
					listLuck.add(order);
					
					//按用户封装获奖订单
					if(realWinOrderMap.containsKey(order.getFrontUser())) {
						List<FrontUserPaymentOrder> list = realWinOrderMap.get(order.getFrontUser());
						list.add(order);
						realWinOrderMap.put(order.getFrontUser(), list);
					} else {
						List<FrontUserPaymentOrder> list = new ArrayList<FrontUserPaymentOrder>();
						list.add(order);
						realWinOrderMap.put(order.getFrontUser(), list);
					}
				}
				//处理未中订单
				String notLuckyKey = FrontUserPaymentOrderGroup.RAIDER.equals(fupo.getPaymentGroup()) ? FrontUserPaymentOrderGroup.LUCKY : FrontUserPaymentOrderGroup.RAIDER;
				List<FrontUserPaymentOrder> isNotLucky = realGroupMap.get(notLuckyKey);
				if(isNotLucky == null || isNotLucky.isEmpty()) {
					throw new TransactionException("抽奖订单为空，开奖异常！n");
				}
				for(FrontUserPaymentOrder order : isNotLucky) {
					if(!order.getUuid().equals(fupo.getUuid())) {
						order.setIsLucky(false);
						listLuck.add(order);
					}
				}
				
				//记录开奖时间
				lgi.setStatus(LuckygameGoodsIssueStatus.FINISHED);
				lgi.setFinishedtime(new Timestamp(System.currentTimeMillis()));
				lgi.setLuckyNumber(Integer.valueOf(luckyNum));
				lgi.setLuckyGroup(fupo.getPaymentGroup());//中奖组
				listlgi.add(lgi);
				
				//中奖记录处理
				for(Map.Entry<String,List<FrontUserPaymentOrder>> entry : realWinOrderMap.entrySet()){
				    String frontUser = entry.getKey();
				    List<FrontUserPaymentOrder> list = entry.getValue();
				    BigDecimal totalDAmount = BigDecimal.ZERO;
				    Integer totalBuyCount = 0;
				    for(FrontUserPaymentOrder fupovo : list) {
				    	totalDAmount = totalDAmount.add(fupovo.getdAmount());
				    	totalBuyCount += fupovo.getBuyCount();
				    }
				    
				  //中奖订单
					WinningInfo wi = new WinningInfo();
					wi.setUuid(UUID.randomUUID().toString());
					wi.setFrontUser(list.get(0).getFrontUser());
					wi.setWinningTime(lgi.getFinishedtime());
					wi.setGoodsIssue(list.get(0).getGoodsIssue());
					wi.setGoodsId(lgi.getGoodsId());
					wi.setShowId(list.get(0).getFrontUserShowId());
					wi.setGameType(list.get(0).getGameType());
					wi.setLuckynum(Integer.valueOf(luckyNum));
					wi.setPaymentDAmount(totalDAmount);
					wi.setBuyCount(totalBuyCount);//总计参与次数
					
					//计算可获得金币数
					//根据用户在中奖战队方所投注的金币数，按比例计算出用户中奖金币 P
					//公式：中奖战队总需投注金币数M 、用户投注金币数N 奖品总价值金币数T
					// P = N/M*T
//					BigDecimal dealPrice = getDealPrice(lgi.getdPrice(), lgi.getBetPerShare(), lgi.getShares(), realPaymetMap.get(order.getFrontUser()), 2);
					BigDecimal dealPrice = getDealPrice(lgi.getdPrice(), lgi.getShares(), wi.getBuyCount(), 2);
					wi.setDealPrice(dealPrice);
					wi.setType(WinningInfoType.GOLD);//未领奖
					wi.setOrderId(list.get(0).getUuid());
					listwin.add(wi);
					
					//更新用户账户信息（异步？）
					FrontUserAccount fua = mapfua.get(frontUser);
					if(fua == null) {
						fua = this.frontUserAccountDao.get(frontUser);
					}
					fua.setTotalWinning(fua.getTotalWinning().add(wi.getDealPrice()));
					fua.setWinningTimes(fua.getWinningTimes() + 1);
					
					//自动同步兑奖
					FrontUser fu = this.frontUserDao.get(frontUser);
					receive(wi, lgi, fu, fua, insertReceive, insertHistory);
					mapfua.put(fua.getFrontUser(), fua);
					
					//通知消息处理
					if(FrontUserPaymentOrderGroup.LUCKY.equals(fupo.getPaymentGroup())) {//幸运对
						groupStr = "幸运队";
					} else if (FrontUserPaymentOrderGroup.RAIDER.equals(fupo.getPaymentGroup())) {//夺宝队
						groupStr = "夺宝队";
					}
					//中奖通知消息
					FrontUserMessage fum = new FrontUserMessage();
					fum.setUuid(UUID.randomUUID().toString());
					fum.setFrontUser(frontUser);
					fum.setFrontUserShowId(fu.getShowId());
					fum.setTitle("提示，中奖通知");
					fum.setContent("您在"+Utlity.timeSpanToChinaDateString(list.get(0).getCreatetime())+"参与的战队PK"+lgi.getShortTitle()+"商品抽奖，您加入的"+groupStr+"获得胜利，快去看看吧！");
					fum.setSourceId(wi.getOrderId());
					fum.setStatus(FrontUserMessageStatus.NORMAL);
					fum.setType(FrontUserMessageType.USER_WIN);
					fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WIN);
					fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fumList.add(fum);
				}
				
				this.luckygameGoodsIssueDao.taskLottery(mapfua, listLuck, listwin, listlgi, insertReceive, insertHistory);
				
				/*
				 * 异步处理
				 */
				this.frontUserMessageDao.sendMessage(fumList);
				this.frontUserMessageDao.sendMessage(fumList, SendSmsNew.TREASUREMALL_SH_TEMP_GROUPWIN);
				List<WinningInfo> listwinMS = new ArrayList<WinningInfo>();//中奖信息
				listwinMS.add(listwin.get(0));
				if(listwin.size() > 0) {
					rabbitSenderService.pushWinInfoStartMessageSend(listwin);//异步发送开奖信息至前端
				}
			}
		}
    }
    
	/**
	 * 重复代码封装
	 * 处理luckyNum和order的对应关系
	 * @param fupo
	 * @param calculateNum
	 * @param orderMap
	 */
	public long addOrderMap(FrontUserPaymentOrder fupo, Map<Integer, FrontUserPaymentOrder> orderMap) {
		FrontUserPaidNumber fupn = this.frontUserPaidNumberDao.get(fupo.getUuid());
		String paidSharenums = fupn.getPaidSharenums();
		if(!Utlity.checkStringNull(paidSharenums)) {//封装中奖号码和对应购买记录
			SharenumsVO svo = JSONUtils.json2obj(paidSharenums, SharenumsVO.class);
			List<Integer> current = svo.getCurrentNums();
			for(Integer num : current) {
				orderMap.put(num, fupo);
			}
		}
		long timeNum = Utlity.getTimeNum(fupo.getCreatetime());
		return timeNum;
	}
	
    
	/**
	 * 传统玩法
	 * 重复代码封装
	 * 处理luckyNum和order的对应关系
	 * @param fupo
	 * @param calculateNum
	 * @param orderMap
	 */
	public long addOrderMap(FrontUserPaymentOrder fupo, Map<Integer, FrontUserPaymentOrder> orderMap, Map<String, Integer> realBuyCountMap) {
		FrontUserPaidNumber fupn = this.frontUserPaidNumberDao.get(fupo.getUuid());
		String paidSharenums = fupn.getPaidSharenums();
		if(!Utlity.checkStringNull(paidSharenums)) {//封装中奖号码和对应购买记录
			SharenumsVO svo = JSONUtils.json2obj(paidSharenums, SharenumsVO.class);
			List<Integer> current = svo.getCurrentNums();
			for(Integer num : current) {
				orderMap.put(num, fupo);
			}
		}
		
		if(realBuyCountMap != null) {
			//计算总投注数
			Integer totalBuyCount = realBuyCountMap.get(fupo.getFrontUser()) == null ? 0 : realBuyCountMap.get(fupo.getFrontUser());
			totalBuyCount+=fupo.getBuyCount();
			realBuyCountMap.put(fupo.getFrontUser(), totalBuyCount);
		}
		
		long timeNum = Utlity.getTimeNum(fupo.getCreatetime());
		return timeNum;
	}
	
    
	/**
	 * 战队玩法
	 * 重复代码封装
	 * 处理luckyNum和战队以及orderList的对应关系
	 * @param fupo
	 * @param orderMap
	 * @param groupMap
	 * @return
	 */
	public long addOrderMap(FrontUserPaymentOrder fupo, Map<Integer, FrontUserPaymentOrder> orderMap, Map<String, Integer> realBuyCountMap, Map<String, List<FrontUserPaymentOrder>> groupMap) {
		
		FrontUserPaidNumber fupn = this.frontUserPaidNumberDao.get(fupo.getUuid());
		String paidSharenums = fupn.getPaidSharenums();
		if(!Utlity.checkStringNull(paidSharenums)) {//封装中奖号码和对应购买记录
			SharenumsVO svo = JSONUtils.json2obj(paidSharenums, SharenumsVO.class);
			List<Integer> current = svo.getCurrentNums();
			for(Integer num : current) {
				orderMap.put(num, fupo);
			}
		}
		if(realBuyCountMap != null) {
			//计算总投注数
			Integer totalBuyCount = realBuyCountMap.get(fupo.getFrontUser()) == null ? 0 : realBuyCountMap.get(fupo.getFrontUser());
			totalBuyCount+=fupo.getBuyCount();
			realBuyCountMap.put(fupo.getFrontUser(), totalBuyCount);
		}
		
		if(Constants.GAME_TYPE_GROUP2.equals(fupo.getGameType())) {//战队玩法
			if(!Utlity.checkStringNull(fupo.getPaymentGroup())) {
				List<FrontUserPaymentOrder> groupOrder = groupMap.get(fupo.getPaymentGroup()) == null ? new ArrayList<FrontUserPaymentOrder>() : groupMap.get(fupo.getPaymentGroup());
				groupOrder.add(fupo);
				groupMap.put(fupo.getPaymentGroup(), groupOrder);
			}
		}
		
		long timeNum = Utlity.getTimeNum(fupo.getCreatetime());
		return timeNum;
	}
	
	/**
	 * 获取中奖金币数
	 * 计算可获得金币数
	 * 根据用户在中奖战队方所投注的金币数，按比例计算出用户中奖金币 P
	 * 公式：中奖战队总需投注金币数M 、用户投注金币数N 奖品总价值金币数T
	 * P = N/M*T
	 * @param dPrice
	 * @param betPerShare
	 * @param shares 必须为coefficient的倍数
	 * @param totalPayment
	 * @param coefficient
	 * @return
	 */
	public static BigDecimal getDealPrice(BigDecimal dPrice, Integer shares, Integer totalBuyCount, Integer coefficient) {
		BigDecimal dealPrice = BigDecimal.ONE;
		//计算中奖战队总需投注金币数M
		Double totalLuckyShares = Double.valueOf(shares.intValue()/coefficient.intValue());
		dealPrice = dPrice.divide(BigDecimal.valueOf(totalLuckyShares),8,BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(totalBuyCount)).setScale(2, BigDecimal.ROUND_DOWN);
		if(dealPrice.compareTo(BigDecimal.ZERO) <= 0) {
			dealPrice = BigDecimal.ONE;
		}
		return dealPrice;
	}
	
	public void receive(WinningInfo wi, LuckygameGoodsIssue lgi, FrontUser fu, FrontUserAccount fua, List<WinningInfoReceive> insertReceive, List<FrontUserHistory> insertHistory) {
		WinningInfoReceive wir = new WinningInfoReceive();
		wir.setWinningInfo(wi.getUuid());
		wir.setCreatetime(new Timestamp(System.currentTimeMillis()));
		wir.setFrontUser(wi.getFrontUser());
		wir.setGoodsId(lgi.getGoodsId());
		wir.setIp(fu.getIp());
		wir.setOrderId(wi.getOrderId());
		wir.setShowId(fu.getShowId());
		wir.setStatus(WinningInfoReceiveStatus.FINISHED);
		wir.setType(WinningInfoReceiveType.GOLD);
		
		wi.setType(WinningInfoType.GOLD);
		
		//封装交易流水
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setUuid(UUID.randomUUID().toString());
		fuh.setFrontUser(wi.getFrontUser());
		fuh.setOrderNum(Utlity.getOrderNum());//
		fuh.setType(FrontUserHistoryType.USER_ADD);
		fuh.setOrderType(Constants.ORDER_TYPE_USER_EXCHANGE);
		fuh.setOrderNum(Utlity.getOrderNum());//生成字符串订单号
		fuh.setOrderId(wi.getOrderId());
		fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_EXCHANGE));
		fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fuh.setdAmount(wi.getDealPrice());
		fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
		fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()).add(wi.getDealPrice()));
		fuh.setRemark(Constants.ORDER_REASON_SYSTEM_ADD);
		//更新账户余额
		fua.setBalance(fua.getBalance().add(wi.getDealPrice()));//加币
		fua.setTotalExchange(fua.getTotalRecharge().add(wi.getDealPrice()));//总兑奖金额
		fua.setExchangeTimes(fua.getExchangeTimes() + 1);
		
		insertReceive.add(wir);
		insertHistory.add(fuh);
	}
}












