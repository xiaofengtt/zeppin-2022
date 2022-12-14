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
	 * ??????????????????
	 */
	@Scheduled(cron="0 0/2 *  * * * ")
	public void activityInfoDisableTask() {
		try {
			//????????????
			Map<String, Object> searchMap = new HashMap<String, Object>();
			
			//???????????????????????????????????????
			List<ActivityInfo> list = activityInfoDao.getListByParams(searchMap);
			if(list != null && list.size() > 0) {
				for(ActivityInfo ai : list) {
					if(ActivityInfoName.BUYFREE.equals(ai.getName())) {//0??????
						//???????????????????????????????????????
						List<ActivityInfoBuyfreeGoods> listGoods = this.activityInfoBuyfreeGoodsDao.getListByActivityInfo(ai.getName());
						if(listGoods == null || listGoods.size() <= 0) {
							continue;
						}
						//????????????
						long nowTime = System.currentTimeMillis();
//						long starttime = ai.getStarttime() == null ? 0L : ai.getStarttime().getTime();
						long endtime = ai.getEndtime() == null ? 0L : ai.getEndtime().getTime();
						
						Map<String, Object> editMap = new HashMap<String, Object>();
						//????????????????????????????????????????????????????????????????????????
						if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
							if(endtime < nowTime) {//??????????????????-????????????
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
//							if(nowTime >= starttime && endtime >= nowTime ) {//??????????????????-????????????
//								List<ActivityInfoBuyfreeGoods> listEdit = new ArrayList<ActivityInfoBuyfreeGoods>();
//								List<ActivityInfoBuyfree> listIssueAdd = new ArrayList<ActivityInfoBuyfree>();
//								for(ActivityInfoBuyfreeGoods aibg : listGoods) {
//									if(ActivityInfoBuyfreeGoodsStatus.NORMAL.equals(aibg.getStatus())) {
//										continue;
//									}
//									aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.NORMAL);
//									//???????????????????????????
//									Integer issueNum = 0;//?????????????????????
//									boolean isNext = true;//?????????????????????
//									//????????????
//									//1.???????????????????????????
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
//										//??????????????????
//										aibg.setCurrentIssueNum(issueNum + 1);
//										//??????????????????
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
						//???????????????????????????????????????
						List<ActivityInfoCheckinPrize> listCheckin = this.activityInfoCheckinPrizeDao.getListByActivityInfo(ai.getName());
						if(listCheckin == null || listCheckin.size() <= 0) {
							continue;
						}
						//????????????
						long nowTime = System.currentTimeMillis();
//						long starttime = ai.getStarttime() == null ? 0L : ai.getStarttime().getTime();
						long endtime = ai.getEndtime() == null ? 0L : ai.getEndtime().getTime();
						
						Map<String, Object> editMap = new HashMap<String, Object>();
						//????????????????????????????????????????????????????????????????????????
						if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
							if(endtime < nowTime) {//??????????????????-????????????
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
						} else if(ActivityInfoStatus.DISABLE.equals(ai.getStatus())) {//?????????????????????
//							if(nowTime >= starttime && endtime >= nowTime ) {//??????????????????-????????????
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
						//???????????????????????????????????????
						List<ActivityInfoScorelotteryPrize> listScorelottery = this.activityInfoScorelotteryPrizeDao.getListByActivityInfo(ai.getName());
						if(listScorelottery == null || listScorelottery.size() <= 0) {
							continue;
						}
						//????????????
						long nowTime = System.currentTimeMillis();
//						long starttime = ai.getStarttime() == null ? 0L : ai.getStarttime().getTime();
						long endtime = ai.getEndtime() == null ? 0L : ai.getEndtime().getTime();
						
						Map<String, Object> editMap = new HashMap<String, Object>();
						//????????????????????????????????????????????????????????????????????????
						if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
							if(endtime < nowTime) {//??????????????????-????????????
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
//							if(nowTime >= starttime && endtime >= nowTime ) {//??????????????????-????????????
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
						//???????????????????????????????????????
						List<ActivityInfoFirstchargePrize> listFirstcharge = this.activityInfoFirstchargePrizeDao.getListByActivityInfo(ai.getName());
						if(listFirstcharge == null || listFirstcharge.size() <= 0) {
							continue;
						}
						//????????????
						long nowTime = System.currentTimeMillis();
//						long starttime = ai.getStarttime() == null ? 0L : ai.getStarttime().getTime();
						long endtime = ai.getEndtime() == null ? 0L : ai.getEndtime().getTime();
						
						Map<String, Object> editMap = new HashMap<String, Object>();
						//????????????????????????????????????????????????????????????????????????
						if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
							if(endtime < nowTime) {//??????????????????-????????????
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
//							if(nowTime >= starttime && endtime >= nowTime ) {//??????????????????-????????????
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
						//???????????????????????????????????????
						List<ActivityInfoRechargePrize> listRecharge = this.activityInfoRechargePrizeDao.getListByActivityInfo(ai.getName());
						if(listRecharge == null || listRecharge.size() <= 0) {
							continue;
						}
						//????????????
						long nowTime = System.currentTimeMillis();
//						long starttime = ai.getStarttime() == null ? 0L : ai.getStarttime().getTime();
						long endtime = ai.getEndtime() == null ? 0L : ai.getEndtime().getTime();
						
						Map<String, Object> editMap = new HashMap<String, Object>();
						//????????????????????????????????????????????????????????????????????????
						if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
							if(endtime < nowTime) {//??????????????????-????????????
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
//							if(nowTime >= starttime && endtime >= nowTime ) {//??????????????????-????????????
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
	 * 0????????????
	 * ????????????
	 * ???????????????????????????lottery?????????
	 * ??????????????? ??????????????? ???????????????finished
	 * ????????????????????????????????????????????????????????????????????????????????????--????????????????????? ?????????????????????
	 * 
	 * ???????????????
	 * ????????????????????????????????????????????????????????????100??????????????????????????????100??? ??????????????????
	 * ??????100????????????????????????A ???????????????????????????????????????12:30:21 230 ???123021230 ??? 12:30:21 002 12302102???
	 * A???????????????????????????????????????????????????????????? ( luckynum = A % shares + start)
	 */
	@Scheduled(cron="*/10 * *  * * * ")
	public void buyfreelottery() {
		
		try {
			List<FrontUserBuyfreeOrder> listLuck = new ArrayList<FrontUserBuyfreeOrder>();//????????????
			List<ActivityInfoBuyfree> listaib = new ArrayList<ActivityInfoBuyfree>();//???????????????
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("status", ActivityInfoBuyfreeStatus.LOTTERY);
			List<ActivityInfoBuyfree> list = activityInfoBuyfreeDao.getListByParams(searchMap);
			if(list != null && list.size() > 0) {
				for(ActivityInfoBuyfree aib : list) {
	    			
	    			Timestamp lotterytime = aib.getLotterytime();
					//?????????????????????100???
	    			searchMap.clear();
					searchMap.put("timeend", Utlity.timeSpanToString(lotterytime));
					searchMap.put("offSet", 0);
					searchMap.put("pageSize", 100);
					List<FrontUserBuyfreeOrder> listorder = this.frontUserBuyfreeOrderDao.getListByParams(searchMap);//??????????????????
					if(listorder != null && listorder.size() > 0) {
						Map<Integer, FrontUserBuyfreeOrder> orderMap = new HashMap<Integer, FrontUserBuyfreeOrder>();
						Map<Integer, FrontUserBuyfreeOrder> realOrderMap = new HashMap<Integer, FrontUserBuyfreeOrder>();
						//???????????????????????? ??????map??????
						searchMap.clear();
						searchMap.put("activityInfoBuyfree", aib.getUuid());
						searchMap.put("status", FrontUserBuyfreeOrderStatus.NORMAL);
						List<FrontUserBuyfreeOrder> listrealOrder = this.frontUserBuyfreeOrderDao.getListByParams(searchMap);
						if(listrealOrder != null && listrealOrder.size() > 0) {
							for(FrontUserBuyfreeOrder fubo : listrealOrder) {
								addOrderMap(fubo, realOrderMap);
							}
						}
						//????????????A
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
						
						//??????????????????
						long anum = calculateNum%aib.getShares().intValue();
						anum = Math.abs(anum);
						int luckyNum = BigDecimal.valueOf(anum).add(BigDecimal.valueOf(Double.valueOf(Utlity.LUCKY_NUM_START))).intValue();
						//??????????????????????????????????????????
						FrontUserBuyfreeOrder fubo = realOrderMap.get(luckyNum);
						if(fubo == null) {
							throw new TransactionException("???????????????");
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
						
						//??????????????????
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
			 * ??????????????????
			 */
			for(FrontUserBuyfreeOrder fubo : listLuck) {
				if(fubo.getIsLucky()) {
					ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(fubo.getActivityInfoBuyfree());
					if(aib != null) {
						/*
						 * ????????????-????????????
						 */
						//??????????????????
						FrontUserMessage fum = new FrontUserMessage();
						fum.setUuid(UUID.randomUUID().toString());
						fum.setFrontUser(fubo.getFrontUser());
						fum.setFrontUserShowId(fubo.getFrontUserShowId());
						fum.setTitle("You Win");
//						fum.setContent("??????"+Utlity.timeSpanToChinaDateString(fubo.getCreatetime())+"?????????0???????????????"+aib.getGoodsShortTitle()+"?????????????????????????????????????????????");
						fum.setContent("You participated in the free buy activity "
//								+ "on " + Utlity.timeSpanToUsString(fubo.getCreatetime()) 
						+ " , the "+aib.getGoodsShortTitle()+", you won the prize, Go see it!");
						fum.setSourceId(fubo.getUuid());
						fum.setStatus(FrontUserMessageStatus.NORMAL);
						fum.setType(FrontUserMessageType.USER_WIN);
						fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_BUYFREE);
						fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
						this.frontUserMessageDao.sendMessage(fum);
						
						//??????????????????????????????????????????????????????????????????
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
	 * ??????????????????
	 * ??????luckyNum???order???????????????
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