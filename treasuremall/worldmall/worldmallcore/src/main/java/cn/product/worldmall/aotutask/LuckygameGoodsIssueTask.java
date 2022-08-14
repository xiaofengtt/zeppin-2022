package cn.product.worldmall.aotutask;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.worldmall.api.base.TransactionException;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserPaidNumberDao;
import cn.product.worldmall.dao.FrontUserPaymentOrderDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.worldmall.entity.FrontUserPaidNumber;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderGroup;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderStatus;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfo.WinningInfoType;
import cn.product.worldmall.entity.WinningInfoReceive;
import cn.product.worldmall.entity.WinningInfoReceive.WinningInfoReceiveStatus;
import cn.product.worldmall.entity.WinningInfoReceive.WinningInfoReceiveType;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.SharenumsVO;

@Component
public class LuckygameGoodsIssueTask {

    public static final Logger log= LoggerFactory.getLogger(LuckygameGoodsIssueTask.class);
	
	@Autowired
	private LuckygameGoodsDao luckygameGoodsDao;

	@Autowired
	private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
	private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
	private FrontUserPaidNumberDao frontUserPaidNumberDao;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;

	@Autowired
	private FrontUserDao frontUserDao;
	
    @Autowired
    private CuratorFramework curatorFramework;
	
	/**
	 * 开奖算法
	 * 定时查询所有状态在lottery的期数
	 * 计算并开奖 开奖完成后 将状态置为finished
	 * 并查询奖品状态，若上架则自动开启下一期，否则不做任何处理--改为投注完成后 自动开启下一期
	 * 
	 * 中奖规则：
	 * 设定最后一个投注记录时间为基准，获取此前100条全站投注记录（不足100的 循环补全？）
	 * 以此100条投注时间求和得A 时间格式为时分秒毫秒（例：12:30:21 230 即123021230 或 12:30:21 002 12302102）
	 * A除以总需人次取余数，加上初始值得中奖号码 ( luckynum = A % shares + start)
	 */
	@Scheduled(cron="*/10 * *  * * * ")
	public void lottery() {
		List<InterProcessSemaphoreMutex> listMutex = new ArrayList<InterProcessSemaphoreMutex>();//记录每个抽奖拿到的锁
		try {
			List<FrontUserPaymentOrder> listLuck = new ArrayList<FrontUserPaymentOrder>();//中奖订单
			List<WinningInfo> listwin = new ArrayList<WinningInfo>();//中奖信息
			List<LuckygameGoodsIssue> listlgi = new ArrayList<LuckygameGoodsIssue>();//已完成期数
//			List<LuckygameGoodsIssue> listlginew = new ArrayList<LuckygameGoodsIssue>();//新开期数
//			List<LuckygameGoods> listlg = new ArrayList<LuckygameGoods>();//奖品信息
//			List<GoodsIssueSharesnum> listgis = new ArrayList<GoodsIssueSharesnum>();//奖品信息
			Map<String, FrontUserAccount> mapfua = new HashMap<String, FrontUserAccount>();
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("status", LuckygameGoodsIssueStatus.LOTTERY);
			searchMap.put("gameType", Constants.GAME_TYPE_TRADITION);
			List<LuckygameGoodsIssue> list = luckygameGoodsIssueDao.getListByParams(searchMap);
			if(list != null && list.size() > 0) {
				for(LuckygameGoodsIssue lgi : list) {
					Timestamp lotterytime = lgi.getLotterytime();
					//判断开奖时间是否超时，未超时的不处理，
					//超时的，即，超过15s以上未开奖的，转为计时任务开奖；未安全起见，判断时长为17s超时
					boolean flagTimeCheck = Utlity.checkLotteryTime(lotterytime.getTime(), System.currentTimeMillis());
					if(flagTimeCheck) {
						/*
		    			 * 获取锁-声明子节点
		    			 */
						InterProcessSemaphoreMutex mutex=new InterProcessSemaphoreMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+"lottery-"+lgi.getUuid()+"-lock");
		    			listMutex.add(mutex);
		    			log.info("传统玩法计时任务-获取zookeeper锁，开奖同步锁"+lgi.getUuid());
						if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
							//重复校验
							lgi = this.luckygameGoodsIssueDao.getById(lgi.getUuid());
							if(!LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {
								continue;
							}
							//20201116增加前置开奖判断
				    		boolean isLottery = false;
				    		if(lgi.getLuckyNumber() != null && lgi.getLuckyNumber().intValue() > 0) {
				    			isLottery = true;
				    		}
							//由此时间向前取100条
							searchMap.clear();
							searchMap.put("timeend", Utlity.timeSpanToString(lotterytime));
							searchMap.put("offSet", 0);
							searchMap.put("pageSize", 100);
							List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);//计算用的记录
							if(listorder != null && listorder.size() > 0) {
								Map<Integer, FrontUserPaymentOrder> orderMap = new HashMap<Integer, FrontUserPaymentOrder>();
								Map<Integer, FrontUserPaymentOrder> realOrderMap = new HashMap<Integer, FrontUserPaymentOrder>();
								//查询实际订单列表 封装map备用
								searchMap.clear();
								searchMap.put("goodsIssue", lgi.getUuid());
								searchMap.put("status", FrontUserPaymentOrderStatus.SUCCESS);
								List<FrontUserPaymentOrder> listrealOrder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
								if(listrealOrder != null && listrealOrder.size() > 0) {
									for(FrontUserPaymentOrder fupo : listrealOrder) {
										addOrderMap(fupo, realOrderMap);
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
								//获取中奖用户参与记录汇总
								searchMap.clear();
								searchMap.put("frontUser", fupo.getFrontUser());
								searchMap.put("goodsIssue", fupo.getGoodsIssue());
								searchMap.put("status", FrontUserPaymentOrderStatus.SUCCESS);
								List<FrontUserPaymentOrder> listOrder = this.frontUserPaymentOrderDao.getGroupListByParams(searchMap);
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
//								wi.setBuyCount(fupo.getBuyCount());
								wi.setBuyCount(listOrder.get(0).getBuyCount());
								wi.setDealPrice(lgi.getdPrice());
								wi.setType(WinningInfoType.NORMAL);//未领奖
								wi.setOrderId(fupo.getUuid());
								wi.setDemoFlag(lgi.getDemoFlag());
								wi.setInternationalInfo(lgi.getInternationalInfo());
								listwin.add(wi);
								
								//更新用户账户信息（异步？）
								FrontUserAccount fua = mapfua.get(fupo.getFrontUser());
								if(fua == null) {
									fua = this.frontUserAccountDao.get(fupo.getFrontUser());
								}
								fua.setTotalWinning(fua.getTotalWinning().add(wi.getDealPrice()));
								fua.setWinningTimes(fua.getWinningTimes() + 1);
								mapfua.put(fua.getFrontUser(), fua);
								
							}
						}
					}
				}
			}
			this.luckygameGoodsIssueDao.taskLottery(mapfua, listLuck, listwin, listlgi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
        	if(listMutex.size() > 0) {//依次解锁
        		log.info("自动开奖-传统玩法计时任务开奖-本次开奖结束！释放锁");
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
	 * 战队玩法开奖
	 */
	@Scheduled(cron="*/10 * *  * * * ")
	public void groupLottery() {
		List<InterProcessSemaphoreMutex> listMutex = new ArrayList<InterProcessSemaphoreMutex>();//记录每个抽奖拿到的锁
		try {
			List<FrontUserPaymentOrder> listLuck = new ArrayList<FrontUserPaymentOrder>();//中奖订单
			List<WinningInfo> listwin = new ArrayList<WinningInfo>();//中奖信息
			List<LuckygameGoodsIssue> listlgi = new ArrayList<LuckygameGoodsIssue>();//已完成期数
			Map<String, FrontUserAccount> mapfua = new HashMap<String, FrontUserAccount>();
			List<WinningInfoReceive> insertReceive = new ArrayList<WinningInfoReceive>();//中奖信息
			List<FrontUserHistory> insertHistory = new ArrayList<FrontUserHistory>();//中奖信息
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("status", LuckygameGoodsIssueStatus.LOTTERY);
			searchMap.put("gameType", Constants.GAME_TYPE_GROUP2);
			List<LuckygameGoodsIssue> list = luckygameGoodsIssueDao.getListByParams(searchMap);
			if(list != null && list.size() > 0) {
				for(LuckygameGoodsIssue lgi : list) {
					Timestamp lotterytime = lgi.getLotterytime();
					//判断开奖时间是否超时，未超时的不处理，
					//超时的，即，超过15s以上未开奖的，转为计时任务开奖；未安全起见，判断时长为17s超时
					boolean flagTimeCheck = Utlity.checkLotteryTime(lotterytime.getTime(), System.currentTimeMillis());
					if(flagTimeCheck) {
						/*
		    			 * 获取锁-声明子节点
		    			 */
						InterProcessSemaphoreMutex mutex=new InterProcessSemaphoreMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+"lottery-"+lgi.getUuid()+"-lock");
		    			listMutex.add(mutex);
		    			log.info("战队PK计时任务-获取zookeeper锁，开奖同步锁"+lgi.getUuid());
						if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
							//重复校验
							lgi = this.luckygameGoodsIssueDao.get(lgi.getUuid());
							if(!LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {
								continue;
							}
							//由此时间向前取100条
							searchMap.clear();
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
										List<FrontUserPaymentOrder> listor = realWinOrderMap.get(order.getFrontUser());
										listor.add(order);
										realWinOrderMap.put(order.getFrontUser(), listor);
									} else {
										List<FrontUserPaymentOrder> listor = new ArrayList<FrontUserPaymentOrder>();
										listor.add(order);
										realWinOrderMap.put(order.getFrontUser(), listor);
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
								    List<FrontUserPaymentOrder> listor = entry.getValue();
								    BigDecimal totalDAmount = BigDecimal.ZERO;
								    Integer totalBuyCount = 0;
								    for(FrontUserPaymentOrder fupovo : listor) {
								    	totalDAmount = totalDAmount.add(fupovo.getdAmount());
								    	totalBuyCount += fupovo.getBuyCount();
								    }
								    
								  //中奖订单
									WinningInfo wi = new WinningInfo();
									wi.setUuid(UUID.randomUUID().toString());
									wi.setFrontUser(listor.get(0).getFrontUser());
									wi.setWinningTime(lgi.getFinishedtime());
									wi.setGoodsIssue(listor.get(0).getGoodsIssue());
									wi.setGoodsId(lgi.getGoodsId());
									wi.setShowId(listor.get(0).getFrontUserShowId());
									wi.setGameType(listor.get(0).getGameType());
									wi.setLuckynum(Integer.valueOf(luckyNum));
									wi.setPaymentDAmount(totalDAmount);
									wi.setBuyCount(totalBuyCount);//总计参与次数
									
									//计算可获得金币数
									//根据用户在中奖战队方所投注的金币数，按比例计算出用户中奖金币 P
									//公式：中奖战队总需投注金币数M 、用户投注金币数N 奖品总价值金币数T
									// P = N/M*T
//									BigDecimal dealPrice = getDealPrice(lgi.getdPrice(), lgi.getBetPerShare(), lgi.getShares(), realPaymetMap.get(order.getFrontUser()), 2);
									BigDecimal dealPrice = getDealPrice(lgi.getdPrice(), lgi.getShares(), wi.getBuyCount(), 2);
									wi.setDealPrice(dealPrice);
									wi.setType(WinningInfoType.GOLD);//未领奖
									wi.setOrderId(listor.get(0).getUuid());
									wi.setDemoFlag(lgi.getDemoFlag());
									wi.setInternationalInfo(lgi.getInternationalInfo());
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
								}
							}
						}
					}
				}
			}
			this.luckygameGoodsIssueDao.taskLottery(mapfua, listLuck, listwin, listlgi, insertReceive, insertHistory);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
        	if(listMutex.size() > 0) {//依次解锁
            	log.info("自动开奖-战队PK计时任务开奖-本次开奖结束！释放锁");
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
