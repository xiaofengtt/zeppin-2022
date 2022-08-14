package cn.product.worldmall.aotutask;

import java.math.BigDecimal;
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

import cn.product.worldmall.api.base.TransactionException;
import cn.product.worldmall.dao.ActivityInfoBuyfreeDao;
import cn.product.worldmall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.worldmall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.worldmall.dao.ActivityInfoDao;
import cn.product.worldmall.dao.ActivityInfoFirstchargePrizeDao;
import cn.product.worldmall.dao.ActivityInfoRechargePrizeDao;
import cn.product.worldmall.dao.ActivityInfoScorelotteryPrizeDao;
import cn.product.worldmall.dao.FrontUserBuyfreeOrderDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.entity.ActivityInfo;
import cn.product.worldmall.entity.ActivityInfoBuyfree;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize;
import cn.product.worldmall.entity.ActivityInfoFirstchargePrize;
import cn.product.worldmall.entity.ActivityInfoRechargePrize;
import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoName;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.worldmall.entity.ActivityInfoBuyfree.ActivityInfoBuyfreeStatus;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods.ActivityInfoBuyfreeGoodsStatus;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeStatus;
import cn.product.worldmall.entity.ActivityInfoFirstchargePrize.ActivityInfoFirstchargePrizeStatus;
import cn.product.worldmall.entity.ActivityInfoRechargePrize.ActivityInfoRechargePrizeStatus;
import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize.ActivityInfoScorelotteryPrizeStatus;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder.FrontUserBuyfreeOrderStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;

@Component
public class ActivityInfoTask {
	
	private final static Logger log = LoggerFactory.getLogger(ActivityInfoTask.class);
	
	
	@Autowired
	private ActivityInfoDao activityInfoDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private ActivityInfoBuyfreeGoodsDao activityInfoBuyfreeGoodsDao;
	
	@Autowired
	private ActivityInfoBuyfreeDao activityInfoBuyfreeDao;

    @Autowired
    private FrontUserMessageDao frontUserMessageDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserBuyfreeOrderDao frontUserBuyfreeOrderDao;

	@Autowired
	private ActivityInfoCheckinPrizeDao activityInfoCheckinPrizeDao;

	@Autowired
	private ActivityInfoScorelotteryPrizeDao activityInfoScorelotteryPrizeDao;

	@Autowired
	private ActivityInfoFirstchargePrizeDao activityInfoFirstchargePrizeDao;

	@Autowired
	private ActivityInfoRechargePrizeDao activityInfoRechargePrizeDao;
	
	
	/**
	 * 活动自动起止
	 */
	@Scheduled(cron="0 0/2 *  * * * ")
	public void activityInfoDisableTask() {
		try {
			//查询条件
			Map<String, Object> searchMap = new HashMap<String, Object>();
			
			//查询符合条件的活动信息列表
			List<ActivityInfo> list = activityInfoDao.getListByParams(searchMap);
			if(list != null && list.size() > 0) {
				for(ActivityInfo ai : list) {
					if(ActivityInfoName.BUYFREE.equals(ai.getName())) {//0元购
						//获取当前活动设置的所有商品
						List<ActivityInfoBuyfreeGoods> listGoods = this.activityInfoBuyfreeGoodsDao.getListByActivityInfo(ai.getName());
						if(listGoods == null || listGoods.size() <= 0) {
							continue;
						}
						//判断日期
						long nowTime = System.currentTimeMillis();
//						long starttime = ai.getStarttime() == null ? 0L : ai.getStarttime().getTime();
						long endtime = ai.getEndtime() == null ? 0L : ai.getEndtime().getTime();
						
						Map<String, Object> editMap = new HashMap<String, Object>();
						//根据不同活动类型更新对应活动状态，及相关内容配置
						if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
							if(endtime < nowTime) {//过了截至时间-自动截至
								ai.setStatus(ActivityInfoStatus.DISABLE);
								List<ActivityInfoBuyfreeGoods> listEdit = new ArrayList<ActivityInfoBuyfreeGoods>();
								for(ActivityInfoBuyfreeGoods aibg : listGoods) {
									if(ActivityInfoBuyfreeGoodsStatus.DISABLE.equals(aibg.getStatus())) {
										continue;
									}
									aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.DISABLE);
									listEdit.add(aibg);
								}
								editMap.put("edit", listEdit);
								this.activityInfoDao.editBuyfree(ai, editMap);
							}
						} else if(ActivityInfoStatus.DISABLE.equals(ai.getStatus())) {
//							if(nowTime >= starttime && endtime >= nowTime ) {//到了起始时间-自动开启
//								List<ActivityInfoBuyfreeGoods> listEdit = new ArrayList<ActivityInfoBuyfreeGoods>();
//								List<ActivityInfoBuyfree> listIssueAdd = new ArrayList<ActivityInfoBuyfree>();
//								for(ActivityInfoBuyfreeGoods aibg : listGoods) {
//									if(ActivityInfoBuyfreeGoodsStatus.NORMAL.equals(aibg.getStatus())) {
//										continue;
//									}
//									aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.NORMAL);
//									//判断是否有期数存在
//									Integer issueNum = 0;//初始化当前期数
//									boolean isNext = true;//是否开展下一期
//									//上架操作
//									//1.查询是否有相关期数
//									searchMap.clear();
//									searchMap.put("activityInfoBuyfreeGoods", aibg.getUuid());
//									searchMap.put("sort", "issue_num desc");
//									List<ActivityInfoBuyfree> listIssue = this.activityInfoBuyfreeDao.getListByParams(searchMap);
//									if(listIssue != null && listIssue.size() > 0) {
//										for(ActivityInfoBuyfree aib : listIssue) {
//											if(ActivityInfoBuyfreeStatus.BETTING.equals(aib.getStatus())) {
//												isNext = false;
//											}
//										}
//										issueNum = listIssue.get(0).getIssueNum();
//									}
//									
//									if(isNext) {
//										//设置当前期数
//										aibg.setCurrentIssueNum(issueNum + 1);
//										//封装期数信息
//										ActivityInfoBuyfree aib = new ActivityInfoBuyfree();
//										aib.setUuid(UUID.randomUUID().toString());
//										aib.setActivityInfo(ai.getName());
//										aib.setActivityInfoBuyfreeGoods(aibg.getUuid());
//										aib.setBetShares(0);
//										aib.setCreatetime(new Timestamp(System.currentTimeMillis()));
//										aib.setGoodsCover(aibg.getGoodsCover());
//										aib.setGoodsId(aibg.getGoodsId());
//										aib.setGoodsPrice(aibg.getGoodsPrice());
//										aib.setGoodsTitle(aibg.getGoodsTitle());
//										aib.setGoodsShortTitle(aibg.getGoodsShortTitle());
//										aib.setIssueNum(aibg.getCurrentIssueNum());
//										aib.setRemainShares(aibg.getShares());
//										aib.setShares(aibg.getShares());
//										aib.setSort(aibg.getSort());
//										aib.setStatus(ActivityInfoBuyfreeStatus.BETTING);
//										listIssueAdd.add(aib);
//									}
//									
//									listEdit.add(aibg);
//								}
//
//								editMap.put("edit", listEdit);
//								editMap.put("issueAdd", listIssueAdd);
//								this.activityInfoDao.editBuyfree(ai, editMap);
//							}
						}
					} else if (ActivityInfoName.CHECKIN.equals(ai.getName())) {
						//获取当前活动设置的所有商品
						List<ActivityInfoCheckinPrize> listCheckin = this.activityInfoCheckinPrizeDao.getListByActivityInfo(ai.getName());
						if(listCheckin == null || listCheckin.size() <= 0) {
							continue;
						}
						//判断日期
						long nowTime = System.currentTimeMillis();
//						long starttime = ai.getStarttime() == null ? 0L : ai.getStarttime().getTime();
						long endtime = ai.getEndtime() == null ? 0L : ai.getEndtime().getTime();
						
						Map<String, Object> editMap = new HashMap<String, Object>();
						//根据不同活动类型更新对应活动状态，及相关内容配置
						if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
							if(endtime < nowTime) {//过了截至时间-自动截至
								ai.setStatus(ActivityInfoStatus.DISABLE);
								List<ActivityInfoCheckinPrize> listEdit = new ArrayList<ActivityInfoCheckinPrize>();
								for(ActivityInfoCheckinPrize aicp : listCheckin) {
									if(ActivityInfoCheckinPrizeStatus.DISABLE.equals(aicp.getStatus())) {
										continue;
									}
									aicp.setStatus(ActivityInfoCheckinPrizeStatus.DISABLE);
									listEdit.add(aicp);
								}
								editMap.put("edit", listEdit);

								this.activityInfoDao.editCheckin(ai, editMap);
							}
						} else if(ActivityInfoStatus.DISABLE.equals(ai.getStatus())) {//暂时不自动开启
//							if(nowTime >= starttime && endtime >= nowTime ) {//到了起始时间-自动开启
//								ai.setStatus(ActivityInfoStatus.NORMAL);
//								List<ActivityInfoCheckinPrize> listEdit = new ArrayList<ActivityInfoCheckinPrize>();
//								for(ActivityInfoCheckinPrize aicp : listCheckin) {
//									if(ActivityInfoCheckinPrizeStatus.NORMAL.equals(aicp.getStatus())) {
//										continue;
//									}
//									aicp.setStatus(ActivityInfoCheckinPrizeStatus.NORMAL);
//									listEdit.add(aicp);
//								}
//								editMap.put("edit", listEdit);
//								this.activityInfoDao.editCheckin(ai, editMap);
//							}
						} else {
							continue;
						}
					} else if (ActivityInfoName.SCORELOTTERY.equals(ai.getName())) {
						//获取当前活动设置的所有商品
						List<ActivityInfoScorelotteryPrize> listScorelottery = this.activityInfoScorelotteryPrizeDao.getListByActivityInfo(ai.getName());
						if(listScorelottery == null || listScorelottery.size() <= 0) {
							continue;
						}
						//判断日期
						long nowTime = System.currentTimeMillis();
//						long starttime = ai.getStarttime() == null ? 0L : ai.getStarttime().getTime();
						long endtime = ai.getEndtime() == null ? 0L : ai.getEndtime().getTime();
						
						Map<String, Object> editMap = new HashMap<String, Object>();
						//根据不同活动类型更新对应活动状态，及相关内容配置
						if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
							if(endtime < nowTime) {//过了截至时间-自动截至
								ai.setStatus(ActivityInfoStatus.DISABLE);
								List<ActivityInfoScorelotteryPrize> listEdit = new ArrayList<ActivityInfoScorelotteryPrize>();
								for(ActivityInfoScorelotteryPrize aisp : listScorelottery) {
									if(ActivityInfoScorelotteryPrizeStatus.DISABLE.equals(aisp.getStatus())) {
										continue;
									}
									aisp.setStatus(ActivityInfoScorelotteryPrizeStatus.DISABLE);
									listEdit.add(aisp);
								}
								editMap.put("edit", listEdit);
								
								this.activityInfoDao.editScorelottery(ai, editMap);
							}
						} else if(ActivityInfoStatus.DISABLE.equals(ai.getStatus())) {
//							if(nowTime >= starttime && endtime >= nowTime ) {//到了起始时间-自动开启
//								ai.setStatus(ActivityInfoStatus.NORMAL);
//								List<ActivityInfoScorelotteryPrize> listEdit = new ArrayList<ActivityInfoScorelotteryPrize>();
//								for(ActivityInfoScorelotteryPrize aisp : listScorelottery) {
//									if(ActivityInfoScorelotteryPrizeStatus.NORMAL.equals(aisp.getStatus())) {
//										continue;
//									}
//									aisp.setStatus(ActivityInfoScorelotteryPrizeStatus.NORMAL);
//									listEdit.add(aisp);
//								}
//								editMap.put("edit", listEdit);
//								this.activityInfoDao.editScorelottery(ai, editMap);
//							}
						} else {
							continue;
						}
					} else if (ActivityInfoName.FIRSTCHARGE.equals(ai.getName())) {
						//获取当前活动设置的所有商品
						List<ActivityInfoFirstchargePrize> listFirstcharge = this.activityInfoFirstchargePrizeDao.getListByActivityInfo(ai.getName());
						if(listFirstcharge == null || listFirstcharge.size() <= 0) {
							continue;
						}
						//判断日期
						long nowTime = System.currentTimeMillis();
//						long starttime = ai.getStarttime() == null ? 0L : ai.getStarttime().getTime();
						long endtime = ai.getEndtime() == null ? 0L : ai.getEndtime().getTime();
						
						Map<String, Object> editMap = new HashMap<String, Object>();
						//根据不同活动类型更新对应活动状态，及相关内容配置
						if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
							if(endtime < nowTime) {//过了截至时间-自动截至
								ai.setStatus(ActivityInfoStatus.DISABLE);
								List<ActivityInfoFirstchargePrize> listEdit = new ArrayList<ActivityInfoFirstchargePrize>();
								for(ActivityInfoFirstchargePrize aifp : listFirstcharge) {
									if(ActivityInfoFirstchargePrizeStatus.DISABLE.equals(aifp.getStatus())) {
										continue;
									}
									aifp.setStatus(ActivityInfoFirstchargePrizeStatus.DISABLE);
									listEdit.add(aifp);
								}
								editMap.put("edit", listEdit);
								
								this.activityInfoDao.editFirstcharge(ai, editMap);
							}
						} else if(ActivityInfoStatus.DISABLE.equals(ai.getStatus())) {
//							if(nowTime >= starttime && endtime >= nowTime ) {//到了起始时间-自动开启
//								ai.setStatus(ActivityInfoStatus.NORMAL);
//								List<ActivityInfoFirstchargePrize> listEdit = new ArrayList<ActivityInfoFirstchargePrize>();
//								for(ActivityInfoFirstchargePrize aifp : listFirstcharge) {
//									if(ActivityInfoFirstchargePrizeStatus.NORMAL.equals(aifp.getStatus())) {
//										continue;
//									}
//									aifp.setStatus(ActivityInfoFirstchargePrizeStatus.NORMAL);
//									listEdit.add(aifp);
//								}
//								editMap.put("edit", listEdit);
//								this.activityInfoDao.editFirstcharge(ai, editMap);
//							}
						} else {
							continue;
						}
					} else if (ActivityInfoName.RECHARGE.equals(ai.getName())) {
						//获取当前活动设置的所有商品
						List<ActivityInfoRechargePrize> listRecharge = this.activityInfoRechargePrizeDao.getListByActivityInfo(ai.getName());
						if(listRecharge == null || listRecharge.size() <= 0) {
							continue;
						}
						//判断日期
						long nowTime = System.currentTimeMillis();
//						long starttime = ai.getStarttime() == null ? 0L : ai.getStarttime().getTime();
						long endtime = ai.getEndtime() == null ? 0L : ai.getEndtime().getTime();
						
						Map<String, Object> editMap = new HashMap<String, Object>();
						//根据不同活动类型更新对应活动状态，及相关内容配置
						if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
							if(endtime < nowTime) {//过了截至时间-自动截至
								ai.setStatus(ActivityInfoStatus.DISABLE);
								List<ActivityInfoRechargePrize> listEdit = new ArrayList<ActivityInfoRechargePrize>();
								for(ActivityInfoRechargePrize airp : listRecharge) {
									if(ActivityInfoRechargePrizeStatus.DISABLE.equals(airp.getStatus())) {
										continue;
									}
									airp.setStatus(ActivityInfoRechargePrizeStatus.DISABLE);
									listEdit.add(airp);
								}
								editMap.put("edit", listEdit);
								
								this.activityInfoDao.editRecharge(ai, editMap);
							}
						} else if(ActivityInfoStatus.DISABLE.equals(ai.getStatus())) {
//							if(nowTime >= starttime && endtime >= nowTime ) {//到了起始时间-自动开启
//								ai.setStatus(ActivityInfoStatus.NORMAL);
//								List<ActivityInfoRechargePrize> listEdit = new ArrayList<ActivityInfoRechargePrize>();
//								for(ActivityInfoRechargePrize airp : listRecharge) {
//									if(ActivityInfoRechargePrizeStatus.NORMAL.equals(airp.getStatus())) {
//										continue;
//									}
//									airp.setStatus(ActivityInfoRechargePrizeStatus.NORMAL);
//									listEdit.add(airp);
//								}
//								editMap.put("edit", listEdit);
//								this.activityInfoDao.editRecharge(ai, editMap);
//							}
						} else {
							continue;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 0元购开奖
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
	public void buyfreelottery() {
		
		try {
			List<FrontUserBuyfreeOrder> listLuck = new ArrayList<FrontUserBuyfreeOrder>();//中奖订单
			List<ActivityInfoBuyfree> listaib = new ArrayList<ActivityInfoBuyfree>();//已完成期数
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("status", ActivityInfoBuyfreeStatus.LOTTERY);
			List<ActivityInfoBuyfree> list = activityInfoBuyfreeDao.getListByParams(searchMap);
			if(list != null && list.size() > 0) {
				for(ActivityInfoBuyfree aib : list) {
	    			
	    			Timestamp lotterytime = aib.getLotterytime();
					//由此时间向前取100条
	    			searchMap.clear();
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
					}
				}
			}
			this.activityInfoBuyfreeDao.taskLottery(listLuck, listaib, null, null, null);
			
			/*
			 * 异步通知处理
			 */
			for(FrontUserBuyfreeOrder fubo : listLuck) {
				if(fubo.getIsLucky()) {
					ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(fubo.getActivityInfoBuyfree());
					if(aib != null) {
						/*
						 * 异步处理-暂不发送
						 */
						//中奖通知消息
						FrontUserMessage fum = new FrontUserMessage();
						fum.setUuid(UUID.randomUUID().toString());
						fum.setFrontUser(fubo.getFrontUser());
						fum.setFrontUserShowId(fubo.getFrontUserShowId());
						fum.setTitle("You Win");
//						fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fubo.getCreatetime())+"参与的0元购活动，"+aib.getGoodsShortTitle()+"商品抽奖，中奖啦，快去看看吧！");
						fum.setContent("You participated in the free buy activity "
//								+ "on " + Utlity.timeSpanToUsString(fubo.getCreatetime()) 
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
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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