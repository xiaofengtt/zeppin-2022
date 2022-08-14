package cn.product.treasuremall.service.front.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeDao;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeSharesnumDao;
import cn.product.treasuremall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.treasuremall.dao.ActivityInfoDao;
import cn.product.treasuremall.dao.ActivityInfoFirstchargePrizeDao;
import cn.product.treasuremall.dao.ActivityInfoScorelotteryPrizeDao;
import cn.product.treasuremall.dao.AreaDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserAddressDao;
import cn.product.treasuremall.dao.FrontUserBuyfreeOrderDao;
import cn.product.treasuremall.dao.FrontUserCheckinHistoryDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserMessageDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.FrontUserScorelotteryHistoryDao;
import cn.product.treasuremall.dao.FrontUserVoucherDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.dao.VoucherDao;
import cn.product.treasuremall.entity.ActivityInfo;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoName;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.treasuremall.entity.ActivityInfoBuyfree;
import cn.product.treasuremall.entity.ActivityInfoBuyfree.ActivityInfoBuyfreeStatus;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeGoods;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeGoods.ActivityInfoBuyfreeGoodsStatus;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeSharesnum;
import cn.product.treasuremall.entity.ActivityInfoCheckinPrize;
import cn.product.treasuremall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeStatus;
import cn.product.treasuremall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeType;
import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize;
import cn.product.treasuremall.entity.ActivityInfoScorelotteryPrize;
import cn.product.treasuremall.entity.ActivityInfoScorelotteryPrize.ActivityInfoScorelotteryPrizeStatus;
import cn.product.treasuremall.entity.ActivityInfoScorelotteryPrize.ActivityInfoScorelotteryPrizeType;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderStatus;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderType;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserStatus;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.treasuremall.entity.FrontUserAddress;
import cn.product.treasuremall.entity.FrontUserBuyfreeOrder;
import cn.product.treasuremall.entity.FrontUserBuyfreeOrder.FrontUserBuyfreeOrderReceiveType;
import cn.product.treasuremall.entity.FrontUserBuyfreeOrder.FrontUserBuyfreeOrderStatus;
import cn.product.treasuremall.entity.FrontUserCheckinHistory;
import cn.product.treasuremall.entity.FrontUserCheckinHistory.FrontUserCheckinHistoryReceiveType;
import cn.product.treasuremall.entity.FrontUserCheckinHistory.FrontUserCheckinHistoryStatus;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.treasuremall.entity.FrontUserScoreHistory;
import cn.product.treasuremall.entity.FrontUserScorelotteryHistory;
import cn.product.treasuremall.entity.FrontUserScorelotteryHistory.FrontUserScorelotteryHistoryReceiveType;
import cn.product.treasuremall.entity.FrontUserScorelotteryHistory.FrontUserScorelotteryHistoryStatus;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.Voucher;
import cn.product.treasuremall.entity.Voucher.VoucherStatus;
import cn.product.treasuremall.entity.activity.ConfigBuyfree;
import cn.product.treasuremall.entity.activity.ConfigScorelottery;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.rabbit.send.RabbitSenderService;
import cn.product.treasuremall.service.front.FrontActivityInfoService;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.SendSmsNew;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.ProvideInfoVO;
import cn.product.treasuremall.vo.back.SharenumsVO;
import cn.product.treasuremall.vo.front.ActivityInfoBuyfreeVO;
import cn.product.treasuremall.vo.front.ActivityInfoCheckinPrizeVO;
import cn.product.treasuremall.vo.front.ActivityInfoScorelotteryPrizeVO;
import cn.product.treasuremall.vo.front.ActivityInfoVO;
import cn.product.treasuremall.vo.front.FrontUserBuyfreeOrderVO;
import cn.product.treasuremall.vo.front.FrontUserCheckinHistoryVO;
import cn.product.treasuremall.vo.front.FrontUserScorelotteryHistoryVO;

@Service("frontActivityInfoService")
public class FrontActivityInfoServiceImpl implements FrontActivityInfoService{
	
	public static final Logger log= LoggerFactory.getLogger(FrontActivityInfoServiceImpl.class);

	@Autowired
	private ActivityInfoBuyfreeDao activityInfoBuyfreeDao;
	
	@Autowired
	private ActivityInfoBuyfreeGoodsDao activityInfoBuyfreeGoodsDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserBuyfreeOrderDao frontUserBuyfreeOrderDao;
	
	@Autowired
	private SystemParamDao systemParamDao;

	@Autowired
	private ActivityInfoDao activityInfoDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
    private GoodsDao goodsDao;
	
    @Autowired
    private CuratorFramework curatorFramework;
	
    @Autowired
    private FrontUserVoucherDao frontUserVoucherDao;

    @Autowired
    private ActivityInfoBuyfreeSharesnumDao activityInfoBuyfreeSharesnumDao;
 
    @Autowired
    private RabbitSenderService rabbitSenderService;

    @Autowired
    private FrontUserMessageDao frontUserMessageDao;

    @Autowired
    private FrontUserCheckinHistoryDao frontUserCheckinHistoryDao;

    @Autowired
    private ActivityInfoCheckinPrizeDao activityInfoCheckinPrizeDao;
	
	@Autowired
	private VoucherDao voucherDao;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;

    @Autowired
    private FrontUserScorelotteryHistoryDao frontUserScorelotteryHistoryDao;

    @Autowired
    private ActivityInfoScorelotteryPrizeDao activityInfoScorelotteryPrizeDao;

    @Autowired
    private ActivityInfoFirstchargePrizeDao activityInfoFirstchargePrizeDao;

	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
	private AreaDao areaDao;
	
	@Autowired
    private FrontUserAddressDao frontUserAddressDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		//获取活动信息
		ActivityInfo ai = activityInfoDao.get(name);
		if (ai == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		
		try {
			ActivityInfoVO vo = new ActivityInfoVO(ai);
			if(ActivityInfoName.BUYFREE.equals(ai.getName())) {
				ConfigBuyfree cb = JSONUtils.json2obj(ai.getConfiguration(), ConfigBuyfree.class);
				if(cb != null) {
					vo.setCurrentTimes(cb.getTimesLine());
				}
				
				//计算可参与次数
				if(!Utlity.checkStringNull(frontUser)) {
					Map<String, Object> searchMap = new HashMap<String, Object>();
					searchMap.put("frontUser", frontUser);
					searchMap.put("timeline", "0");
					
					Integer count = this.frontUserBuyfreeOrderDao.getCountByParams(searchMap);
					if(count != null && count > 0) {
						vo.setCurrentTimes(vo.getCurrentTimes() - count);
						if(vo.getCurrentTimes() <= 0) {
							vo.setIsPartake(false);
						}
					}
				}
			} else if(ActivityInfoName.CHECKIN.equals(ai.getName())) {
				//计算是否已签到
				if(!Utlity.checkStringNull(frontUser)) {
					Map<String, Object> searchMap = new HashMap<String, Object>();
					searchMap.put("frontUser", frontUser);
					searchMap.put("timeline", "0");
					
					Integer count = this.frontUserCheckinHistoryDao.getCountByParams(searchMap);
					if(count != null && count > 0) {
						vo.setCurrentTimes(0);
						vo.setIsPartake(false);
					}
				}
			} else if(ActivityInfoName.SCORELOTTERY.equals(ai.getName())) {//积分抽奖
				ConfigScorelottery cs = JSONUtils.json2obj(ai.getConfiguration(), ConfigScorelottery.class);
				if(cs != null) {
					vo.setCurrentTimes(cs.getTimesLine());
				}
				if(!Utlity.checkStringNull(frontUser)) {
					Map<String, Object> searchMap = new HashMap<String, Object>();
					searchMap.put("frontUser", frontUser);
					searchMap.put("timeline", "0");
					
					Integer count = this.frontUserScorelotteryHistoryDao.getCountByParams(searchMap);
					if(count != null && count > 0) {
						vo.setCurrentTimes(vo.getCurrentTimes() - count);
						if(vo.getCurrentTimes() <= 0) {
							vo.setIsPartake(false);
						}
					}
				}
			} else if(ActivityInfoName.FIRSTCHARGE.equals(ai.getName())) {//首充
				//20200610判断用户是否已参与
				//查询充值记录中该用户参与活动的记录是否存在，若存在，则已参与
				List<ActivityInfoFirstchargePrize> listFirstcharge = this.activityInfoFirstchargePrizeDao.getListByActivityInfo(ai.getName());
				Map<String, Object> prize = new HashMap<String, Object>();
				if(listFirstcharge != null && listFirstcharge.size() > 0) {
					ActivityInfoFirstchargePrize aifp = listFirstcharge.get(0);
					prize.put("amount", aifp.getAmount().toString());
				} else {
					prize.put("amount", "66");
				}
				vo.setConfig(prize);
				if(!Utlity.checkStringNull(frontUser)) {
					Boolean isPartakeFirstcharge = this.frontUserRechargeOrderDao.isPartakeFirstcharge(frontUser);
					if(isPartakeFirstcharge) {
						vo.setCurrentTimes(0);
						vo.setIsPartake(false);
					}
				}
			}
			
			result.setData(vo);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("success");	
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("网络异常！");	
		}
			
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		try {
			//查询符合条件的活动信息的总数
			Integer totalResultCount = activityInfoDao.getCountByParams(searchMap);
			//查询符合条件的活动信息列表
			List<ActivityInfo> list = activityInfoDao.getListByParams(searchMap);
			List<ActivityInfoVO> listvo = new ArrayList<ActivityInfoVO>();
			if(list != null && list.size() > 0) {
				for(ActivityInfo ai : list) {
					ActivityInfoVO vo = new ActivityInfoVO(ai);
					if(ActivityInfoName.BUYFREE.equals(ai.getName())) {
						ConfigBuyfree cb = JSONUtils.json2obj(ai.getConfiguration(), ConfigBuyfree.class);
						if(cb != null) {
							vo.setCurrentTimes(cb.getTimesLine());
						}
						
						//计算可参与次数
						if(!Utlity.checkStringNull(frontUser)) {
							searchMap.clear();
							searchMap.put("frontUser", frontUser);
							searchMap.put("timeline", "0");
							
							Integer count = this.frontUserBuyfreeOrderDao.getCountByParams(searchMap);
							if(count != null && count > 0) {
								vo.setCurrentTimes(vo.getCurrentTimes() - count);
							}
						}
					} else if(ActivityInfoName.CHECKIN.equals(ai.getName())) {
						//计算是否已签到
						if(!Utlity.checkStringNull(frontUser)) {
							searchMap.clear();
							searchMap.put("frontUser", frontUser);
							searchMap.put("timeline", "0");
							
							Integer count = this.frontUserCheckinHistoryDao.getCountByParams(searchMap);
							if(count != null && count > 0) {
								vo.setCurrentTimes(0);
								vo.setIsPartake(false);
							}
						}
					} else if(ActivityInfoName.SCORELOTTERY.equals(ai.getName())) {//积分抽奖
						ConfigScorelottery cs = JSONUtils.json2obj(ai.getConfiguration(), ConfigScorelottery.class);
						if(cs != null) {
							vo.setCurrentTimes(cs.getTimesLine());
						}
						if(!Utlity.checkStringNull(frontUser)) {
							searchMap.clear();
							searchMap.put("frontUser", frontUser);
							searchMap.put("timeline", "0");
							
							Integer count = this.frontUserScorelotteryHistoryDao.getCountByParams(searchMap);
							if(count != null && count > 0) {
								vo.setCurrentTimes(vo.getCurrentTimes() - count);
								if(vo.getCurrentTimes() <= 0) {
									vo.setIsPartake(false);
								}
							}
						}
					}
					
					listvo.add(vo);
				}
			}
			result.setData(listvo);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("success");
			result.setPageNum(pageNum);
			result.setPageSize(pageSize);
			result.setTotalResultCount(totalResultCount);	
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("网络异常！");	
		}
	}

	@Override
	public void buyfreeGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	
    	ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(uuid);
    	if(aib == null) {
    		result.setMessage("商品信息错误，请稍后重试！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	ActivityInfoBuyfreeVO aibvo = new ActivityInfoBuyfreeVO(aib);
    	ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(aib.getActivityInfoBuyfreeGoods());
    	if(aibg != null) {//当前期数
    		aibvo.setActivityInfoBuyfreeGoodsStatus(aibg.getStatus());
    		aibvo.setCurrentIssueNum(aibg.getCurrentIssueNum());
    		Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("activityInfoBuyfreeGoods", aibg.getUuid());
			searchMap.put("sort", "issue_num desc");
			List<ActivityInfoBuyfree> list = this.activityInfoBuyfreeDao.getListByParams(searchMap);
			if(list != null && list.size() > 0) {
				aibvo.setCurrentIssueUuid(list.get(0).getUuid());
			}
    	}
    	if(ActivityInfoBuyfreeStatus.LOTTERY.equals(aib.getStatus())) {//倒计时
			aibvo.setTimeLine(aib.getLotterytime().getTime()+Utlity.TIMELINE-System.currentTimeMillis());
		}
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
    	
		aibvo.setGoodsCoverUrl("");
		Resource re = this.resourceDao.get(aib.getGoodsCover());
		if(re != null) {
			aibvo.setGoodsCoverUrl(pathUrl + re.getUrl());
		}
		
		//是否当天第一次购买
		if(!Utlity.checkStringNull(frontUser)) {//需要传frontUser
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("activityInfoBuyfree", aib.getUuid());
			searchMap.put("frontUser", frontUser);
			searchMap.put("timeline", "0");
			
			Integer count = this.frontUserBuyfreeOrderDao.getCountByParams(searchMap);
			if(count != null && count > 0) {
				aibvo.setIsFirstBuy(false);
			}
		}
		result.setData(aibvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void buyfreeList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String names = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		if(Utlity.checkStringNull(status)) {
			status = ActivityInfoBuyfreeStatus.BETTING;
		}
		searchMap.put("status", status);
		searchMap.put("names", names);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		Integer totalCount = activityInfoBuyfreeDao.getCountByParams(searchMap);
		List<ActivityInfoBuyfree> list = activityInfoBuyfreeDao.getListByParams(searchMap);
		
		List<ActivityInfoBuyfreeVO> listvo = new ArrayList<ActivityInfoBuyfreeVO>();
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(ActivityInfoBuyfree aib : list) {
				ActivityInfoBuyfreeVO aibvo = new ActivityInfoBuyfreeVO(aib);
				ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(aib.getActivityInfoBuyfreeGoods());
		    	if(aibg != null) {//当前期数
		    		aibvo.setActivityInfoBuyfreeGoodsStatus(aibg.getStatus());
		    		aibvo.setCurrentIssueNum(aibg.getCurrentIssueNum());
		    		searchMap.clear();
					searchMap.put("activityInfoBuyfreeGoods", aibg.getUuid());
					searchMap.put("sort", "issue_num desc");
					List<ActivityInfoBuyfree> lista = this.activityInfoBuyfreeDao.getListByParams(searchMap);
					if(lista != null && lista.size() > 0) {
						aibvo.setCurrentIssueUuid(lista.get(0).getUuid());
					}
		    	}
				if(ActivityInfoBuyfreeStatus.LOTTERY.equals(aib.getStatus())) {//倒计时
					aibvo.setTimeLine(aib.getLotterytime().getTime()+Utlity.TIMELINE-System.currentTimeMillis());
				}
		    	
				aibvo.setGoodsCoverUrl("");
				Resource re = this.resourceDao.get(aib.getGoodsCover());
				if(re != null) {
					aibvo.setGoodsCoverUrl(pathUrl + re.getUrl());
				}
				
				//是否当天第一次购买
				if(!Utlity.checkStringNull(frontUser)) {//需要传frontUser
					searchMap.clear();
					searchMap.put("activityInfoBuyfree", aib.getUuid());
					searchMap.put("frontUser", frontUser);
					searchMap.put("timeline", "0");
					
					Integer count = this.frontUserBuyfreeOrderDao.getCountByParams(searchMap);
					if(count != null && count > 0) {
						aibvo.setIsFirstBuy(false);
					}
				}
				listvo.add(aibvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void buyfreeHistoryList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();	
    	
    	Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", status);
		searchMap.put("frontUser", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		List<FrontUserBuyfreeOrder> list = this.frontUserBuyfreeOrderDao.getLeftListByParams(searchMap);
		Integer totalCount = this.frontUserBuyfreeOrderDao.getLeftCountByParams(searchMap);
		List<FrontUserBuyfreeOrderVO> listvo = new ArrayList<FrontUserBuyfreeOrderVO>();
		
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(FrontUserBuyfreeOrder fubo : list) {
				FrontUserBuyfreeOrderVO fubovo = new FrontUserBuyfreeOrderVO(fubo);
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(fubo.getFrontUser());
				if(fu != null) {
					fubovo.setNickname(fu.getNickname());
					//获取头像信息
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fubovo.setImageUrl(pathUrl + res.getUrl());
						}
					}
				}
				
				//封装商品信息
				ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(fubo.getActivityInfoBuyfree());
				if(aib != null) {
					//获取金币汇率
					SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
					BigDecimal rate = BigDecimal.ONE;
					if(sprate != null) {
						rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
					}
					
					fubovo.setIssueNum(aib.getIssueNum());
					fubovo.setTitle(aib.getGoodsTitle());
					fubovo.setShortTitle(aib.getGoodsShortTitle());
					fubovo.setShares(aib.getShares());
					fubovo.setPrice(aib.getGoodsPrice());
					fubovo.setDealPrice(aib.getGoodsPrice().multiply(rate));
					
					fubovo.setCover("");
					Resource re = this.resourceDao.get(aib.getGoodsCover());
					if(re != null) {
						fubovo.setCover(pathUrl + re.getUrl());
					}
					
					Goods good = this.goodsDao.get(aib.getGoodsId());
					fubovo.setCode(good.getCode());
				}
				
				//已兑奖-兑换金币，封装金币明细ID--frontUserHistory
				if(FrontUserBuyfreeOrderReceiveType.GOLD.equals(fubo.getReceiveType())) {
					FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fubo.getUuid());
					if(fuh != null) {
						fubovo.setFrontUserHistory(fuh.getUuid());
						fubovo.setDealDAmount(fuh.getdAmount());
					}
				}
				listvo.add(fubovo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;	
		
		
	}

	@Override
	public void buyfreeHistoryGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();	
    	
    	FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(uuid);
    	
    	if(fubo == null) {
    		result.setMessage("活动订单信息错误，请稍后重试！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
    	if(!Utlity.checkStringNull(frontUser)) {
    		if(!fubo.getFrontUser().equals(frontUser)) {
    			result.setMessage("活动订单查询有误，请稍后重试！");
        		result.setStatus(ResultStatusType.FAILED);
        		return;
    		}
    	}
    	FrontUserBuyfreeOrderVO fubovo = new FrontUserBuyfreeOrderVO(fubo);
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		
		//封装用户信息
		FrontUser fu = this.frontUserDao.get(fubo.getFrontUser());
		if(fu != null) {
			fubovo.setNickname(fu.getNickname());
			//获取头像信息
			if(!Utlity.checkStringNull(fu.getImage())) {
				Resource res = resourceDao.get(fu.getImage());
				if(res != null) {
					fubovo.setImageUrl(pathUrl + res.getUrl());
				}
			}
		}
		
		//封装商品信息
		ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(fubo.getActivityInfoBuyfree());
		if(aib != null) {
			//获取金币汇率
			SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sprate != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
			}
			
			fubovo.setIssueNum(aib.getIssueNum());
			fubovo.setTitle(aib.getGoodsTitle());
			fubovo.setShortTitle(aib.getGoodsShortTitle());
			fubovo.setShares(aib.getShares());
			fubovo.setPrice(aib.getGoodsPrice());
			fubovo.setDealPrice(aib.getGoodsPrice().multiply(rate));
			
			fubovo.setCover("");
			Resource re = this.resourceDao.get(aib.getGoodsCover());
			if(re != null) {
				fubovo.setCover(pathUrl + re.getUrl());
			}
			
			Goods good = this.goodsDao.get(aib.getGoodsId());
			fubovo.setCode(good.getCode());
		}
		
		//已兑奖-兑换金币，封装金币明细ID--frontUserHistory
		if(FrontUserBuyfreeOrderReceiveType.GOLD.equals(fubo.getReceiveType())) {
			FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fubo.getUuid());
			if(fuh != null) {
				fubovo.setFrontUserHistory(fuh.getUuid());
				fubovo.setDealDAmount(fuh.getdAmount());
			}
		}
		
		result.setData(fubovo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("查询成功！");
		return;	
	}

	@Override
	public void buyfreePartake(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	
    	/*
		 * 获取锁-声明子节点
		 */
		InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+"buyfree-"+uuid+"-lock");
        log.info("获取zookeeper锁");
    	try {
    		List<FrontUserBuyfreeOrder> listOrder = new ArrayList<FrontUserBuyfreeOrder>();
        	List<ActivityInfoBuyfreeSharesnum> listNums = new ArrayList<ActivityInfoBuyfreeSharesnum>();
        	List<ActivityInfoBuyfree> listBuyfree = new ArrayList<ActivityInfoBuyfree>();
        	Map<String, Object> doMap = new HashMap<String, Object>();
    		
    		if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
    			log.info("获取zookeeper锁-----------------生效");
    			FrontUser fu = this.frontUserDao.get(frontUser);
    			if(fu == null) {
    				result.setMessage("用户信息错误，请稍后重试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    				
    			if(!FrontUserStatus.NORMAL.equals(fu.getStatus()) && !FrontUserStatus.HIGHRISK.equals(fu.getStatus())) {
    				result.setMessage("用户状态错误，请联系客服！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(uuid);
    	    	if(aib == null) {
    	    		result.setMessage("抽奖商品信息错误，请稍后重试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	//判断活动状态，活动可参与次数

    	    	if(!ActivityInfoName.BUYFREE.equals(aib.getActivityInfo())) {
    	    		result.setMessage("活动信息错误！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	
    	    	ActivityInfo ai = activityInfoDao.get(aib.getActivityInfo());
    	    	if(ai == null || !ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
    	    		result.setMessage("活动已结束！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	
    	    	ConfigBuyfree cb = JSONUtils.json2obj(ai.getConfiguration(), ConfigBuyfree.class);
    	    	if(cb == null) {
    	    		result.setMessage("活动信息有误！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	//获取用户当天参与次数，判断今日是否可参与
    	    	Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("frontUser", frontUser);
				searchMap.put("timeline", "0");
				
				Integer count = this.frontUserBuyfreeOrderDao.getCountByParams(searchMap);
				if(count != null && count > 0) {
					if(count.intValue() - cb.getTimesLine().intValue() >= 0) {
						result.setMessage("今日抽奖次数不足！");
	    	    		result.setStatus(ResultStatusType.FAILED);
	    	    		return;
					}
				}
				
				if(!ActivityInfoBuyfreeStatus.BETTING.equals(aib.getStatus())) {
    	    		result.setMessage("抽奖商品信息错误，请稍后重试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
				
    	    	if(aib.getRemainShares().intValue() <= 0) {
    	    		result.setMessage("当前抽奖商品可参与次数不足，请重新下单！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	
    	    	
    	    	FrontUserBuyfreeOrder fubo = new FrontUserBuyfreeOrder();
    	    	fubo.setUuid(UUID.randomUUID().toString());
    	    	fubo.setActivityInfoBuyfree(aib.getUuid());
    	    	fubo.setCreatetime(new Timestamp(System.currentTimeMillis()));
				fubo.setFrontUser(fu.getUuid());
				fubo.setFrontUserShowId(fu.getShowId());
				fubo.setGoodsId(aib.getGoodsId());
				
				if(Utlity.checkStringNull(ip)) {
					ip = fu.getIp();
				}
				fubo.setIp(ip);
				fubo.setStatus(FrontUserBuyfreeOrderStatus.NORMAL);
    	    	fubo.setReceiveType(FrontUserBuyfreeOrderReceiveType.NORMAL);
				
				/*
				 * 分发抽奖号码
				 */
				ActivityInfoBuyfreeSharesnum aibs = this.activityInfoBuyfreeSharesnumDao.get(aib.getUuid());
				SharenumsVO svo = JSONUtils.json2obj(aibs.getSharenums(), SharenumsVO.class);
				List<Integer> currentNums = svo.getCurrentNums();
				List<Integer> usedNums = svo.getUsedNums();
				Integer userNums = 0;
				//20200512改为随机分号
				int ramNum = Utlity.getRandomNum(0, currentNums.size());
				userNums = currentNums.get(ramNum);
				usedNums.add(currentNums.get(ramNum));
				currentNums.remove(ramNum);
				
				svo.setCurrentNums(currentNums);
				svo.setUsedNums(usedNums);
				aibs.setSharenums(JSONUtils.obj2json(svo));
				listNums.add(aibs);//保存抽奖号码
				
				fubo.setSharenum(userNums);
				listOrder.add(fubo);//保存订单
				
				
				
				//扣减投注份额
				aib.setBetShares(aib.getBetShares().intValue() + 1);
				aib.setRemainShares(aib.getRemainShares().intValue() - 1);
				boolean isLottery = false;
				boolean flag = false;
            	String buyfree = "";
				//如果可投注份额清零，则进入待开奖状态
				if(aib.getRemainShares() == 0) {
					isLottery = true;
					buyfree = aib.getUuid();
					aib.setStatus(ActivityInfoBuyfreeStatus.LOTTERY);
					aib.setLotterytime(new Timestamp(System.currentTimeMillis()));
					
					//投注清零，开展下一期
					//查询奖品状态-看是否要开展下一期
					ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(aib.getActivityInfoBuyfreeGoods());
					if(aibg != null && ActivityInfoBuyfreeGoodsStatus.NORMAL.equals(aibg.getStatus())) {
						flag = true;
					}
				}
				listBuyfree.add(aib);
				
				doMap.put("activityInfoBuyfree", listBuyfree);
				doMap.put("issueNums", listNums);
				doMap.put("paymentOrder", listOrder);
        		
        		this.frontUserBuyfreeOrderDao.userBet(doMap);
        		
//        		//下单通知消息--暂不开放
//				FrontUserMessage fum = new FrontUserMessage();
//				fum.setUuid(UUID.randomUUID().toString());
//				fum.setFrontUser(fu.getUuid());
//				fum.setFrontUserShowId(fu.getShowId());
//				fum.setTitle("提示，参与抽奖成功");
//				fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fubo.getCreatetime())+"参与的"+aib.getGoodsShortTitle()+"商品抽奖下单成功！");
//				fum.setSourceId(fubo.getUuid());
//				fum.setStatus(FrontUserMessageStatus.NORMAL);
//				fum.setType(FrontUserMessageType.USER_ORDER);
//				fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_PAYMENT);
//				fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				this.frontUserMessageDao.sendMessage(fum);
				
        		if(flag) {
					this.rabbitSenderService.buyfreeStartMessageSend(aib.getActivityInfoBuyfreeGoods());
				}
				if(isLottery && !Utlity.checkStringNull(buyfree)) {
					this.rabbitSenderService.buyfreelotterySend(buyfree);
            	}
        		result.setMessage("下单成功！");
        		result.setStatus(ResultStatusType.SUCCESS);
        		return;
    		}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("操作异常，请稍后重试！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
		} finally {
			if (mutex!=null){
                try {
					mutex.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
	}

	/**
	 * 要判断当前用户前一天是否已签到
	 * 如果是，那么根据昨天签到的天数，设置isCheckin的值
	 * 否则，重新开始计算连续
	 */
	@Override
	public void checkinList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String names = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	Map<String, Object> searchMap = new HashMap<String, Object>();
    	
    	searchMap.clear();
    	if(Utlity.checkStringNull(status)) {
			status = ActivityInfoCheckinPrizeStatus.NORMAL;
		}
		searchMap.put("names", names);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		searchMap.put("status", status);
		Integer totalCount = activityInfoCheckinPrizeDao.getCountByParams(searchMap);
		List<ActivityInfoCheckinPrize> list = activityInfoCheckinPrizeDao.getListByParams(searchMap);
		

    	//先看一下是否是连续签到，确定已连续签到几天
    	int dayNum = 0;
    	Integer totalDayNum = 0;
    	//然后根据连续天数，确定哪天已签到
		
//		List<ActivityInfoCheckinPrizeVO> volist = new ArrayList<ActivityInfoCheckinPrizeVO>();
		Map<Integer, ActivityInfoCheckinPrizeVO> listMap = Maps.newLinkedHashMap();
		if(list != null && list.size() > 0) {
			if(!Utlity.checkStringNull(frontUser)) {
				searchMap.clear();
		    	searchMap.put("frontUser", frontUser);
				searchMap.put("timeline", "1");
				List<FrontUserCheckinHistory> listCheckin = this.frontUserCheckinHistoryDao.getListByParams(searchMap);
				if(listCheckin != null && listCheckin.size() > 0) {//昨天有签到=是连续
					FrontUserCheckinHistory fuch = listCheckin.get(0);
					totalDayNum = fuch.getDayNum();//已连续天数
				}
				
				//判断今日是否已签到
				searchMap.put("timeline", "0");
				Integer count = this.frontUserCheckinHistoryDao.getCountByParams(searchMap);
				if(count != null && count > 0) {//已签
					totalDayNum += 1;
				}
				dayNum = Integer.valueOf(totalDayNum%totalCount);
			}
			
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			
			for(ActivityInfoCheckinPrize aicp : list) {
				ActivityInfoCheckinPrizeVO vo = new ActivityInfoCheckinPrizeVO(aicp);
				vo.setPrizeCoverUrl("");
				if(!Utlity.checkStringNull(aicp.getPrizeCover())) {
					Resource re = this.resourceDao.get(aicp.getPrizeCover());
					if(re != null) {
						vo.setPrizeCoverUrl(pathUrl + re.getUrl());
					}
				}
				
				if(ActivityInfoCheckinPrizeType.ENTITY.equals(aicp.getPrizeType())) {
					//实物奖品
					Goods goods = this.goodsDao.get(aicp.getPrize());
					if(goods != null) {
						vo.setPrizeDetail(goods.getName());
					}
				} else if(ActivityInfoCheckinPrizeType.VOUCHER.equals(aicp.getPrizeType())) {
					//金券奖品
					Voucher v = this.voucherDao.get(aicp.getPrize());
					if(v != null) {
						vo.setPrizeDetail(v.getTitle());
					}
				} else if(ActivityInfoCheckinPrizeType.ENTITY.equals(aicp.getPrizeType())) {
					//金币奖品
					vo.setPrizeDetail(aicp.getPrize());
				}
				listMap.put(vo.getDayNum(), vo);
//				volist.add(vo);
			}
			
			//处理已签
			if(dayNum > 0) {
				for(int i = 1; i <= dayNum; i++) {
					ActivityInfoCheckinPrizeVO vo = listMap.get(i);
					vo.setIsCheckin(true);
					listMap.put(i, vo);
				}
			}
		}
		returnMap.put("dayNums", dayNum);
		returnMap.put("dataMap", listMap);//N天的map集合数据，按顺序从第1天到第N天
//		returnMap.put("dayNums", dayNum);//用户当前连续签到多少天，默认是0
		result.setData(returnMap);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void checkinHistoryList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();	
    	
    	Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", status);
		searchMap.put("frontUser", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		List<FrontUserCheckinHistory> list = this.frontUserCheckinHistoryDao.getLeftListByParams(searchMap);
		Integer totalCount = this.frontUserCheckinHistoryDao.getLeftCountByParams(searchMap);
		List<FrontUserCheckinHistoryVO> listvo = new ArrayList<FrontUserCheckinHistoryVO>();
		
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(FrontUserCheckinHistory fuch : list) {
				FrontUserCheckinHistoryVO fuchvo = new FrontUserCheckinHistoryVO(fuch);
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(fuch.getFrontUser());
				if(fu != null) {
					fuchvo.setNickname(fu.getNickname());
					//获取头像信息
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fuchvo.setImageUrl(pathUrl + res.getUrl());
						}
					}
				}
				
				if(!Utlity.checkStringNull(fuch.getPrizeCover())) {
					Resource re = this.resourceDao.get(fuch.getPrizeCover());
					if(re != null) {
						fuchvo.setPrizeCoverUrl(pathUrl + re.getUrl());
					}
				}
				
				//已兑奖-兑换金币，封装金币明细ID--frontUserHistory
				if(FrontUserCheckinHistoryReceiveType.GOLD.equals(fuch.getReceiveType())) {
					FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fuch.getUuid());
					if(fuh != null) {
						fuchvo.setFrontUserHistory(fuh.getUuid());
						fuchvo.setDealDAmount(fuh.getdAmount());
					}
				}
				if(ActivityInfoCheckinPrizeType.ENTITY.equals(fuch.getPrizeType())) {
					//获取金币汇率
					SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
					BigDecimal rate = BigDecimal.ONE;
					if(sprate != null) {
						rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
					}
					
					Goods goods = this.goodsDao.get(fuch.getPrize());
					if(goods != null) {
						fuchvo.setPrice(goods.getPrice());
						fuchvo.setDealPrice(goods.getPrice().multiply(rate));
					}
				}
				listvo.add(fuchvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;
	}

	@Override
	public void checkinHistoryGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();	
    	
    	FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(uuid);
    	
    	if(fuch == null) {
    		result.setMessage("活动订单信息错误，请稍后重试！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
    	if(!Utlity.checkStringNull(frontUser)) {
    		if(!fuch.getFrontUser().equals(frontUser)) {
    			result.setMessage("活动订单查询有误，请稍后重试！");
        		result.setStatus(ResultStatusType.FAILED);
        		return;
    		}
    	}
    	FrontUserCheckinHistoryVO fuchvo = new FrontUserCheckinHistoryVO(fuch);
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		//封装用户信息
		FrontUser fu = this.frontUserDao.get(fuch.getFrontUser());
		if(fu != null) {
			fuchvo.setNickname(fu.getNickname());
			//获取头像信息
			if(!Utlity.checkStringNull(fu.getImage())) {
				Resource res = resourceDao.get(fu.getImage());
				if(res != null) {
					fuchvo.setImageUrl(pathUrl + res.getUrl());
				}
			}
		}
		
		if(!Utlity.checkStringNull(fuch.getPrizeCover())) {
			Resource re = this.resourceDao.get(fuch.getPrizeCover());
			if(re != null) {
				fuchvo.setPrizeCoverUrl(pathUrl + re.getUrl());
			}
		}
		
		//已兑奖-兑换金币，封装金币明细ID--frontUserHistory
		if(FrontUserCheckinHistoryReceiveType.GOLD.equals(fuch.getReceiveType())) {
			FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fuch.getUuid());
			if(fuh != null) {
				fuchvo.setFrontUserHistory(fuh.getUuid());
				fuchvo.setDealDAmount(fuh.getdAmount());
			}
		}
		if(ActivityInfoCheckinPrizeType.ENTITY.equals(fuch.getPrizeType())) {
			//获取金币汇率
			SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sprate != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
			}
			
			Goods goods = this.goodsDao.get(fuch.getPrize());
			if(goods != null) {
				fuchvo.setPrice(goods.getPrice());
				fuchvo.setDealPrice(goods.getPrice().multiply(rate));
			}
		}
		
		result.setData(fuchvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("查询成功！");
		return;	
	}

	@Override
	public void checkinPartake(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
//    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	Map<String, Object> resultMap = Maps.newHashMap();
    	//判断今日是否已签
    	//判断是否是今日
    	
    	/*
		 * 获取锁-声明子节点
		 */
		InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+"checkin-"+frontUser+"-lock");
        log.info("获取zookeeper锁");
    	try {
    		Map<String, Object> doMap = new HashMap<String, Object>();
    		if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
    			log.info("获取zookeeper锁-----------------生效:"+Constants.ZK_LOCK_PATHPREFIX+"checkin-"+frontUser+"-lock");
    			/*
    			 * 判断活动是否已过期
    			 */
    			ActivityInfo ai = this.activityInfoDao.get(ActivityInfoName.CHECKIN);
    			if(ai == null || ActivityInfoStatus.DELETE.equals(ai.getStatus())) {
    				result.setMessage("活动不存在！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			if(ActivityInfoStatus.DISABLE.equals(ai.getStatus())) {
    				result.setMessage("活动当前已结束！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			FrontUser fu = this.frontUserDao.get(frontUser);
    			if(fu == null) {
    				result.setMessage("用户信息错误，请稍后重试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    				
    			if(!FrontUserStatus.NORMAL.equals(fu.getStatus()) && !FrontUserStatus.HIGHRISK.equals(fu.getStatus())) {
    				result.setMessage("用户状态错误，请联系客服！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
    			if(fua == null){
    				result.setStatus(ResultStatusType.FAILED);
    				result.setMessage("用户账户状态异常， 请联系管理员！");
    				return;
    			}
    			
    			if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
    				result.setStatus(ResultStatusType.FAILED);
    				result.setMessage("用户账户状态异常， 请联系管理员！");
    				return;
    			}
    			
//    			ActivityInfoCheckinPrize aicp = this.activityInfoCheckinPrizeDao.get(uuid);
//        		if(aicp == null || !ActivityInfoCheckinPrizeStatus.NORMAL.equals(aicp.getStatus())) {
//        			result.setMessage("签到信息错误！");
//            		result.setStatus(ResultStatusType.FAILED);
//            		return;
//        		}
        		
        		Map<String, Object> searchMap = new HashMap<String, Object>();
        		searchMap.put("status", ActivityInfoCheckinPrizeStatus.NORMAL);
        		Integer totalCount = activityInfoCheckinPrizeDao.getCountByParams(searchMap);
        		List<ActivityInfoCheckinPrize> list = activityInfoCheckinPrizeDao.getListByParams(searchMap);
        		Map<Integer, ActivityInfoCheckinPrize> listMap = Maps.newLinkedHashMap();
        		if(list != null && list.size() > 0) {
        			for(ActivityInfoCheckinPrize aicp : list) {
        				listMap.put(aicp.getDayNum(), aicp);
//        				volist.add(vo);
        			}
        		}
            	//先看一下是否是连续签到，确定已连续签到几天
            	int dayNum = 0;
            	int totalDayNum = 0;
            	
            	searchMap.clear();
            	searchMap.put("frontUser", frontUser);
            	//判断今日是否已签到
    			searchMap.put("timeline", "0");
    			Integer count = this.frontUserCheckinHistoryDao.getCountByParams(searchMap);
    			if(count != null && count > 0) {//已签
    				result.setMessage("今日已完成签到！");
            		result.setStatus(ResultStatusType.FAILED);
            		return;
    			}
        		
    			//昨天有签到=是连续
    			searchMap.put("timeline", "1");
        		List<FrontUserCheckinHistory> listCheckin = this.frontUserCheckinHistoryDao.getListByParams(searchMap);
        		if(listCheckin != null && listCheckin.size() > 0) {
        			FrontUserCheckinHistory fuch = listCheckin.get(0);
        			totalDayNum = fuch.getDayNum();//已连续天数
        		}
    			dayNum = Integer.valueOf(totalDayNum%totalCount);
        		
//    			if(dayNum != (aicp.getDayNum() - 1)) {//不是当前天
//    				result.setMessage("签到日期错误！");
//            		result.setStatus(ResultStatusType.FAILED);
//            		return;
//    			}
        		ActivityInfoCheckinPrize aicp = listMap.get(dayNum + 1);
        		if(aicp == null || !ActivityInfoCheckinPrizeStatus.NORMAL.equals(aicp.getStatus())) {
	    			result.setMessage("签到信息错误！");
	        		result.setStatus(ResultStatusType.FAILED);
	        		return;
	    		}
        		
    			//添加签到记录
    			FrontUserCheckinHistory fuch = new FrontUserCheckinHistory();
    			fuch.setUuid(UUID.randomUUID().toString());
    			fuch.setActivityInfoCheckinPrize(aicp.getUuid());
    			fuch.setCreatetime(new Timestamp(System.currentTimeMillis()));
    			fuch.setDayNum(totalDayNum+1);
    			fuch.setFrontUser(frontUser);
    			fuch.setFrontUserShowId(fu.getShowId());
    			fuch.setIp(ip);
    			fuch.setPrize(aicp.getPrize());
    			fuch.setPrizeCover(aicp.getPrizeCover());
    			fuch.setPrizeTitle(aicp.getPrizeTitle());
    			fuch.setPrizeType(aicp.getPrizeType());
    			fuch.setRemark("用户签到活动，当前第"+ aicp.getDayNum()+"天");
    			fuch.setStatus(FrontUserCheckinHistoryStatus.NORMAL);
    	    	fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.NORMAL);
    			//根据奖品类型分发奖品
    			if(ActivityInfoCheckinPrizeType.GOLD.equals(aicp.getPrizeType())) {//发放金币

        			fuch.setStatus(FrontUserCheckinHistoryStatus.FINISHED);
        			fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.GOLD);
        			
    				BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(aicp.getPrize()));
    				String content = "签到活动赠送" + aicp.getPrize() + "金币";
    				AdminOffsetOrder aoo = new AdminOffsetOrder();
    				aoo.setUuid(UUID.randomUUID().toString());
    				aoo.setdAmount(dAmount);
    				aoo.setFrontUser(frontUser);
					aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
    				aoo.setOrderNum(Utlity.getOrderNum());
					aoo.setReason(content);
					aoo.setRemark(content);
					aoo.setStatus(AdminOffsetOrderStatus.CHECKED);
					aoo.setType(AdminOffsetOrderType.ADMIN_ADD);
    				aoo.setCreatetime(new Timestamp(System.currentTimeMillis()));
    				
    				FrontUserHistory fuh = new FrontUserHistory();
    				fuh.setUuid(UUID.randomUUID().toString());
    				fuh.setFrontUser(aoo.getFrontUser());
    				fuh.setOrderNum(aoo.getOrderNum());
    				fuh.setOrderId(aoo.getUuid());
    				fuh.setdAmount(aoo.getdAmount());
    				fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
    				fuh.setCreatetime(aoo.getCreatetime());
    				fuh.setRemark(content);
    				
    				fuh.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
					fuh.setType(FrontUserHistoryType.USER_ADD);
					fuh.setReason(content);
					
					fua.setBalance(fua.getBalance().add(aoo.getdAmount()));
					fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
					fuch.setFrontUserHistory(fuh.getUuid());
					
					doMap.put("offsetOrder", aoo);
					doMap.put("userHistory", fuh);
					doMap.put("userAccount", fua);
					
    			} else if(ActivityInfoCheckinPrizeType.VOUCHER.equals(aicp.getPrizeType())) {//发放金券
    				
    				fuch.setStatus(FrontUserCheckinHistoryStatus.FINISHED);
    				fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.VOUCHER);
    				
    				Voucher v = this.voucherDao.get(aicp.getPrize());
    				if(v == null || VoucherStatus.DELETE.equals(v.getStatus())) {
    					result.setMessage("奖品信息错误，请稍后重试！");
    		    		result.setStatus(ResultStatusType.FAILED);
    		    		return;
					}
					FrontUserVoucher fuv = new FrontUserVoucher();
					fuv.setUuid(UUID.randomUUID().toString());
					fuv.setFrontUser(frontUser);
					fuv.setVoucher(v.getUuid());
					fuv.setTitle(v.getTitle());
					fuv.setDiscription(v.getDiscription());
					fuv.setdAmount(v.getdAmount());
					fuv.setPayMin(v.getPayMin());
					fuv.setGoodsType(v.getGoodsType());
					fuv.setGoods(v.getGoods());
					
					//按照voucher设定的起止时间，计算初始时间和结束时间
					fuv.setStarttime(Utlity.getTime(v.getStarttime()) == null ? new Timestamp(System.currentTimeMillis()) : Utlity.getTime(v.getStarttime()));//未设置代表当前时间生效
					fuv.setEndtime(Utlity.getTime(v.getEndtime()));
					fuv.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuv.setStatus(FrontUserVoucherStatus.UNSTART);
					fuv.setOperattime(fuv.getCreatetime());
					doMap.put("userVoucher", fuv);
    			}

    			doMap.put("checkinHistory", fuch);
    			this.frontUserCheckinHistoryDao.checkin(doMap);
    			
    			resultMap.put("dayNum", dayNum + 1);
    			resultMap.put("prize", aicp);
    			result.setData(resultMap);
    			result.setMessage("签到成功！");
        		result.setStatus(ResultStatusType.SUCCESS);
        		return;
    		}
    		
    	} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("操作异常，请稍后重试！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
		} finally {
			if (mutex!=null){
                try {
					mutex.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
	}

	@Override
	public void scorelotteryList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String names = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		if(Utlity.checkStringNull(status)) {
			status = ActivityInfoScorelotteryPrizeStatus.NORMAL;
		}
		searchMap.put("status", status);
		searchMap.put("names", names);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		Integer totalCount = activityInfoScorelotteryPrizeDao.getCountByParams(searchMap);
		List<ActivityInfoScorelotteryPrize> list = activityInfoScorelotteryPrizeDao.getListByParams(searchMap);
		
		List<ActivityInfoScorelotteryPrizeVO> volist = new ArrayList<ActivityInfoScorelotteryPrizeVO>();
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(ActivityInfoScorelotteryPrize aisp : list) {
				ActivityInfoScorelotteryPrizeVO vo = new ActivityInfoScorelotteryPrizeVO(aisp);
				vo.setPrizeCoverUrl("");
				if(!Utlity.checkStringNull(aisp.getPrizeCover())) {
					Resource re = this.resourceDao.get(aisp.getPrizeCover());
					if(re != null) {
						vo.setPrizeCoverUrl(pathUrl + re.getUrl());
					}
				}
				
				if(ActivityInfoCheckinPrizeType.ENTITY.equals(aisp.getPrizeType())) {
					//实物奖品
					Goods goods = this.goodsDao.get(aisp.getPrize());
					if(goods != null) {
						vo.setPrizeDetail(goods.getName());
					}
				} else if(ActivityInfoCheckinPrizeType.VOUCHER.equals(aisp.getPrizeType())) {
					//金券奖品
					Voucher v = this.voucherDao.get(aisp.getPrize());
					if(v != null) {
						vo.setPrizeDetail(v.getTitle());
					}
				} else if(ActivityInfoCheckinPrizeType.ENTITY.equals(aisp.getPrizeType())) {
					//金币奖品
					vo.setPrizeDetail(aisp.getPrize());
				}
				volist.add(vo);
			}
		}
		
		result.setData(volist);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void scorelotteryHistoryList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();	
    	
    	Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", status);
		searchMap.put("frontUser", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		List<FrontUserScorelotteryHistory> list = this.frontUserScorelotteryHistoryDao.getLeftListByParams(searchMap);
		Integer totalCount = this.frontUserScorelotteryHistoryDao.getLeftCountByParams(searchMap);
		List<FrontUserScorelotteryHistoryVO> listvo = new ArrayList<FrontUserScorelotteryHistoryVO>();
		
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(FrontUserScorelotteryHistory fush : list) {
				FrontUserScorelotteryHistoryVO fushvo = new FrontUserScorelotteryHistoryVO(fush);
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(fush.getFrontUser());
				if(fu != null) {
					fushvo.setNickname(fu.getNickname());
					//获取头像信息
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fushvo.setImageUrl(pathUrl + res.getUrl());
						}
					}
				}
				
				if(!Utlity.checkStringNull(fush.getPrizeCover())) {
					Resource re = this.resourceDao.get(fush.getPrizeCover());
					if(re != null) {
						fushvo.setPrizeCoverUrl(pathUrl + re.getUrl());
					}
				}
				
				//已兑奖-兑换金币，封装金币明细ID--frontUserHistory
				if(FrontUserScorelotteryHistoryReceiveType.GOLD.equals(fush.getReceiveType())) {
					FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fush.getUuid());
					if(fuh != null) {
						fushvo.setFrontUserHistory(fuh.getUuid());
						fushvo.setDealDAmount(fuh.getdAmount());
					}
				}
				
				if(ActivityInfoCheckinPrizeType.ENTITY.equals(fush.getPrizeType())) {
					//获取金币汇率
					SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
					BigDecimal rate = BigDecimal.ONE;
					if(sprate != null) {
						rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
					}
					
					Goods goods = this.goodsDao.get(fush.getPrize());
					if(goods != null) {
						fushvo.setPrice(goods.getPrice());
						fushvo.setDealPrice(goods.getPrice().multiply(rate));
					}
				}
				
				listvo.add(fushvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;
	}

	@Override
	public void scorelotteryHistoryGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();	
    	
    	FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(uuid);
    	
    	if(fush == null) {
    		result.setMessage("活动订单信息错误，请稍后重试！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
    	if(!Utlity.checkStringNull(frontUser)) {
    		if(!fush.getFrontUser().equals(frontUser)) {
    			result.setMessage("活动订单查询有误，请稍后重试！");
        		result.setStatus(ResultStatusType.FAILED);
        		return;
    		}
    	}
    	FrontUserScorelotteryHistoryVO fushvo = new FrontUserScorelotteryHistoryVO(fush);
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		//封装用户信息
		FrontUser fu = this.frontUserDao.get(fush.getFrontUser());
		if(fu != null) {
			fushvo.setNickname(fu.getNickname());
			//获取头像信息
			if(!Utlity.checkStringNull(fu.getImage())) {
				Resource res = resourceDao.get(fu.getImage());
				if(res != null) {
					fushvo.setImageUrl(pathUrl + res.getUrl());
				}
			}
		}
		
		if(!Utlity.checkStringNull(fush.getPrizeCover())) {
			Resource re = this.resourceDao.get(fush.getPrizeCover());
			if(re != null) {
				fushvo.setPrizeCoverUrl(pathUrl + re.getUrl());
			}
		}
		
		//已兑奖-兑换金币，封装金币明细ID--frontUserHistory
		if(FrontUserScorelotteryHistoryReceiveType.GOLD.equals(fush.getReceiveType())) {
			FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fush.getUuid());
			if(fuh != null) {
				fushvo.setFrontUserHistory(fuh.getUuid());
				fushvo.setDealDAmount(fuh.getdAmount());
			}
		}
		
		if(ActivityInfoCheckinPrizeType.ENTITY.equals(fush.getPrizeType())) {
			//获取金币汇率
			SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sprate != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
			}
			
			Goods goods = this.goodsDao.get(fush.getPrize());
			if(goods != null) {
				fushvo.setPrice(goods.getPrice());
				fushvo.setDealPrice(goods.getPrice().multiply(rate));
			}
		}
		
		result.setData(fushvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("查询成功！");
		return;	
	}

	/**
	 * 抽奖算法：
	 * 生成1~100w个int数值
	 * 获取1~100w之间的随机数M
	 * 根据奖品中奖率，在1~100w之间生成对应抽奖区间I
	 * 判断M所在的I，获取I所代表的奖品
	 */
	@Override
	public void scorelotteryPartake(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	Map<String, Object> resultMap = Maps.newHashMap();
    	
    	/*
		 * 获取锁-声明子节点
		 */
		InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+"scorelottery-"+frontUser+"-lock");
        log.info("获取zookeeper锁");
    	try {
    		Map<String, Object> doMap = new HashMap<String, Object>();
    		if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
    			log.info("获取zookeeper锁-----------------生效:"+Constants.ZK_LOCK_PATHPREFIX+"scorelottery-"+frontUser+"-lock");
    			/*
    			 * 判断活动是否已过期
    			 */
    			ActivityInfo ai = this.activityInfoDao.get(ActivityInfoName.SCORELOTTERY);
    			if(ai == null || ActivityInfoStatus.DELETE.equals(ai.getStatus())) {
    				result.setMessage("活动不存在！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			if(ActivityInfoStatus.DISABLE.equals(ai.getStatus())) {
    				result.setMessage("活动当前已结束！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			ConfigScorelottery cs = JSONUtils.json2obj(ai.getConfiguration(), ConfigScorelottery.class);
    			if(cs == null) {
    				result.setMessage("活动信息错误，请稍候再试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			FrontUser fu = this.frontUserDao.get(frontUser);
    			if(fu == null) {
    				result.setMessage("用户信息错误，请稍后重试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    				
    			if(!FrontUserStatus.NORMAL.equals(fu.getStatus()) && !FrontUserStatus.HIGHRISK.equals(fu.getStatus())) {
    				result.setMessage("用户状态错误，请联系客服！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
    			if(fua == null){
    				result.setStatus(ResultStatusType.FAILED);
    				result.setMessage("用户账户状态异常， 请联系管理员！");
    				return;
    			}
    			
    			if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
    				result.setStatus(ResultStatusType.FAILED);
    				result.setMessage("用户账户状态异常， 请联系管理员！");
    				return;
    			}
    			//判断用户积分是否足够
    			if(fua.getScoreBalance().compareTo(BigDecimal.valueOf(Double.valueOf(cs.getScoreLine()))) <= 0) {
    				result.setStatus(ResultStatusType.FAILED);
    				result.setMessage("当前积分不足，无法参与本次活动！");
    				return;
    			}
    			
    			//抽奖算法
    			/*
    			 * 获取所有奖品列表
    			 * 按照不同中奖率，100w份抽奖号，拆分成N个抽奖号区间
    			 * 然后获取一个0~100w之间的随机数
    			 * 判断是在哪个抽奖号区间，即抽中了哪个奖品
    			 */
    			Map<String, Object> searchMap = new HashMap<String, Object>();
        		searchMap.put("status", ActivityInfoScorelotteryPrizeStatus.NORMAL);
        		List<ActivityInfoScorelotteryPrize> list = activityInfoScorelotteryPrizeDao.getListByParams(searchMap);
        		int lotteryNum = Utlity.getRandomNum(1, 1000001);
        		int totalNum = 0;
        		ActivityInfoScorelotteryPrize lotteryPrize = null;
        		if(list != null && list.size() > 0) {
        			for(ActivityInfoScorelotteryPrize aisp : list) {
        				int interval = aisp.getWinningRate().multiply(BigDecimal.valueOf(10000)).intValue();
        				for(int i = 1; i <= interval; i++) {
        					totalNum++;
        					if(lotteryNum == totalNum) {//中奖
        						lotteryPrize = aisp;
        					}
        				}
        			}
        			if(totalNum != 1000000) {
        				result.setMessage("积分抽奖信息错误，请稍候再试！");
    	        		result.setStatus(ResultStatusType.FAILED);
    	        		return;
        			}
        		}
        		
        		
        		if(lotteryPrize == null || !ActivityInfoScorelotteryPrizeStatus.NORMAL.equals(lotteryPrize.getStatus())) {
	    			result.setMessage("积分抽奖信息错误！");
	        		result.setStatus(ResultStatusType.FAILED);
	        		return;
	    		}
        		
    			//添加抽奖记录
				FrontUserScorelotteryHistory fush = new FrontUserScorelotteryHistory();
    			fush.setUuid(UUID.randomUUID().toString());
    			fush.setActivityInfoScorelotteryPrize(lotteryPrize.getUuid());
    			fush.setCreatetime(new Timestamp(System.currentTimeMillis()));
    			fush.setFrontUser(frontUser);
    			fush.setFrontUserShowId(fu.getShowId());
    			fush.setIp(ip);
    			fush.setPrize(lotteryPrize.getPrize());
    			fush.setPrizeCover(lotteryPrize.getPrizeCover());
    			fush.setPrizeTitle(lotteryPrize.getPrizeTitle());
    			fush.setPrizeType(lotteryPrize.getPrizeType());
    			fush.setRemark("积分抽奖活动奖励，"+lotteryPrize.getPrizeTitle());
    			fush.setStatus(FrontUserScorelotteryHistoryStatus.NORMAL);
    			fush.setScoreAmount(BigDecimal.valueOf(Double.valueOf(cs.getScoreLine())));
    	    	fush.setReceiveType(FrontUserCheckinHistoryReceiveType.NORMAL);
    			
    			
    			if(BigDecimal.valueOf(Double.valueOf(cs.getScoreLine())).compareTo(BigDecimal.ZERO) > 0) {
    				//积分消耗记录
        			FrontUserScoreHistory fushistory = new FrontUserScoreHistory();
    				fushistory.setUuid(UUID.randomUUID().toString());
    				fushistory.setFrontUser(fush.getFrontUser());
    				fushistory.setFrontUserShowId(fush.getFrontUserShowId());
    				fushistory.setOrderNum(Utlity.getOrderNum());
    				fushistory.setOrderId(fush.getUuid());
    				fushistory.setType(FrontUserHistoryType.USER_SUB);
    				fushistory.setOrderType(Constants.ORDER_TYPE_USER_SCORELOTTERY);
    				fushistory.setsAmount(BigDecimal.valueOf(Double.valueOf(cs.getScoreLine())));
    				fushistory.setScoreBalanceBefore(fua.getScoreBalance());
    				fushistory.setScoreBalanceAfter(fua.getScoreBalance().subtract(BigDecimal.valueOf(Double.valueOf(cs.getScoreLine()))));
    				fushistory.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_SCORELOTTERY));
    				fushistory.setCreatetime(fush.getCreatetime());
    				fushistory.setRemark("参与积分抽奖！");
    				
    				fua.setScoreBalance(fua.getScoreBalance().subtract(BigDecimal.valueOf(Double.valueOf(cs.getScoreLine()))));
        			doMap.put("scoreHistory", fushistory);
    			}
    			//根据奖品类型分发奖品
    			if(ActivityInfoScorelotteryPrizeType.GOLD.equals(lotteryPrize.getPrizeType())) {//发放金币

        			fush.setStatus(FrontUserScorelotteryHistoryStatus.FINISHED);
        			fush.setReceiveType(FrontUserScorelotteryHistoryReceiveType.GOLD);
        			
    				BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(lotteryPrize.getPrize()));
    				String content = "积分抽奖活动抽中" + lotteryPrize.getPrize() + "金币";
    				AdminOffsetOrder aoo = new AdminOffsetOrder();
    				aoo.setUuid(UUID.randomUUID().toString());
    				aoo.setdAmount(dAmount);
    				aoo.setFrontUser(frontUser);
					aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
    				aoo.setOrderNum(Utlity.getOrderNum());
					aoo.setReason(content);
					aoo.setRemark(content);
					aoo.setStatus(AdminOffsetOrderStatus.CHECKED);
					aoo.setType(AdminOffsetOrderType.ADMIN_ADD);
    				aoo.setCreatetime(new Timestamp(System.currentTimeMillis()));
    				
    				FrontUserHistory fuh = new FrontUserHistory();
    				fuh.setUuid(UUID.randomUUID().toString());
    				fuh.setFrontUser(aoo.getFrontUser());
    				fuh.setOrderNum(aoo.getOrderNum());
    				fuh.setOrderId(aoo.getUuid());
    				fuh.setdAmount(aoo.getdAmount());
    				fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
    				fuh.setCreatetime(aoo.getCreatetime());
    				fuh.setRemark(content);
    				
    				fuh.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
					fuh.setType(FrontUserHistoryType.USER_ADD);
					fuh.setReason(content);
					
					fua.setBalance(fua.getBalance().add(aoo.getdAmount()));
					fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
					fush.setFrontUserHistory(fuh.getUuid());
					
					doMap.put("offsetOrder", aoo);
					doMap.put("userHistory", fuh);
    			} else if(ActivityInfoScorelotteryPrizeType.VOUCHER.equals(lotteryPrize.getPrizeType())) {//发放金券
    				
    				fush.setStatus(FrontUserScorelotteryHistoryStatus.FINISHED);
        			fush.setReceiveType(FrontUserScorelotteryHistoryReceiveType.VOUCHER);
    				
    				Voucher v = this.voucherDao.get(lotteryPrize.getPrize());
    				if(v == null || VoucherStatus.DELETE.equals(v.getStatus())) {
    					result.setMessage("奖品信息错误，请稍后重试！");
    		    		result.setStatus(ResultStatusType.FAILED);
    		    		return;
					}
					FrontUserVoucher fuv = new FrontUserVoucher();
					fuv.setUuid(UUID.randomUUID().toString());
					fuv.setFrontUser(frontUser);
					fuv.setVoucher(v.getUuid());
					fuv.setTitle(v.getTitle());
					fuv.setDiscription(v.getDiscription());
					fuv.setdAmount(v.getdAmount());
					fuv.setPayMin(v.getPayMin());
					fuv.setGoodsType(v.getGoodsType());
					fuv.setGoods(v.getGoods());
					
					//按照voucher设定的起止时间，计算初始时间和结束时间
					fuv.setStarttime(Utlity.getTime(v.getStarttime()) == null ? new Timestamp(System.currentTimeMillis()) : Utlity.getTime(v.getStarttime()));//未设置代表当前时间生效
					fuv.setEndtime(Utlity.getTime(v.getEndtime()));
					fuv.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuv.setStatus(FrontUserVoucherStatus.UNSTART);
					fuv.setOperattime(fuv.getCreatetime());
					doMap.put("userVoucher", fuv);
    			}
    			
    			doMap.put("scorelotteryHistory", fush);
				doMap.put("userAccount", fua);
    			this.frontUserScorelotteryHistoryDao.scorelottery(doMap);
    			
    			resultMap.put("prize", lotteryPrize);
    			result.setData(resultMap);
    			result.setMessage("操作成功！");
        		result.setStatus(ResultStatusType.SUCCESS);
        		return;
    			
    		}
    		
    	} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("操作异常，请稍后重试！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
		} finally {
			if (mutex!=null){
                try {
					mutex.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
	}

	/**
	 * 用户兑奖流程
	 * 可以兑换金币和实物
	 */
	@Override
	public void receive(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		String activityInfo = paramsMap.get("activityInfo") == null ? "" : paramsMap.get("activityInfo").toString();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
//		String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
		String frontUserAddress = paramsMap.get("frontUserAddress") == null ? "" : paramsMap.get("frontUserAddress").toString();
		
		FrontUser fu = this.frontUserDao.get(frontUser);	
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户异常，操作失败！");
			return;
		}
		
		//判断不同活动，进行兑换
		if(Utlity.checkStringNull(activityInfo)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("活动数据异常，请稍候再试！");
			return;
		}
		
		try {
			//获取金币汇率
			SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sp != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
			}
			
			//兑奖通知消息
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fu.getUuid());
			fum.setFrontUserShowId(fu.getShowId());
			fum.setTitle("提示，兑奖成功");
			StringBuilder content = new StringBuilder("您在"+Utlity.timeSpanToChinaDateString(new Timestamp(System.currentTimeMillis()))+"发起的");
			
			if(ActivityInfoName.BUYFREE.equals(activityInfo)) {//0元购
				Map<String, Object> updateMap = new HashMap<String, Object>();
				FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(uuid);
				if(fubo == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("参与记录不存在，如有疑问，请联系客服！");
					return;
				}
				if(!fubo.getFrontUser().equals(frontUser)) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("参与记录不存在，如有疑问，请联系客服！");
					return;
				}
				if(FrontUserBuyfreeOrderStatus.FINISHED.equals(fubo.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("已完成兑奖，如有疑问，请联系客服！");
					return;
				}
				if(!FrontUserBuyfreeOrderStatus.WIN.equals(fubo.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("参与记录状态有误，无法兑奖！");
					return;
				}
				
				//+lgi.getShortTitle()+"商品兑奖申请成功，"
				ActivityInfoBuyfree aibf = this.activityInfoBuyfreeDao.get(fubo.getActivityInfoBuyfree());
				if(aibf == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("奖品信息有误！");
					return;
				}
				fum.setSourceId(fubo.getUuid());
				fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_BUYFREE);
				content.append(aibf.getGoodsShortTitle()+"商品兑奖申请成功，");
				if(FrontUserBuyfreeOrderReceiveType.GOLD.equals(type)) {
					content.append("金币已到账！");
					fubo.setStatus(FrontUserBuyfreeOrderStatus.FINISHED);
					fubo.setReceiveType(FrontUserBuyfreeOrderReceiveType.GOLD);
					//查询当前账户对象
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户不存在！");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户异常，操作失败");
						return;
			    	}
					//计算到帐金币
//					BigDecimal dAmount = aibf.getGoodsPrice().multiply(rate);
					//计算到帐金币
					Goods goods = this.goodsDao.get(aibf.getGoodsId());
					BigDecimal dAmount = BigDecimal.ZERO;
					if(goods != null) {
						dAmount = goods.getPrice().multiply(rate);
					}
					
					//封装交易流水
					FrontUserHistory fuh = new FrontUserHistory();
					fuh.setUuid(UUID.randomUUID().toString());
					fuh.setFrontUser(frontUser);
					fuh.setOrderNum(Utlity.getOrderNum());//
					fuh.setType(FrontUserHistoryType.USER_ADD);
					fuh.setOrderType(Constants.ORDER_TYPE_USER_BUYFREE);
					fuh.setOrderNum(Utlity.getOrderNum());//生成字符串订单号
					fuh.setOrderId(fubo.getUuid());
					fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_BUYFREE));
					fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuh.setdAmount(dAmount);
					fuh.setBalanceBefore(fuacc.getBalance().add(fuacc.getBalanceLock()));
					fuh.setBalanceAfter(fuacc.getBalance().add(fuacc.getBalanceLock()).add(dAmount));
					
					fuh.setRemark("0元购活动"+aibf.getGoodsShortTitle()+"第"+aibf.getIssueNum()+"期");
					//更新账户余额
					fuacc.setBalance(fuacc.getBalance().add(dAmount));//加币
					
//					//暂不算入用户统计
//					fuacc.setTotalExchange(fuacc.getTotalRecharge().add(dAmount));//总兑奖金额
//					fuacc.setExchangeTimes(fuacc.getExchangeTimes() + 1);
					
					updateMap.put("frontUserAccount", fuacc);
					updateMap.put("frontUserHistory", fuh);
				} else if(FrontUserBuyfreeOrderReceiveType.ENTITY.equals(type)) {
					fubo.setStatus(FrontUserBuyfreeOrderStatus.RECEIVE);
					fubo.setReceiveType(FrontUserBuyfreeOrderReceiveType.ENTITY);
					content.append("商品待发货！");
					if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())){//黑名单校验
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("操作无效， 请联系管理员！");
						return;
					}
					if(!FrontUserType.NORMAL.equals(fu.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("操作无效， 请联系管理员！");
						return;
					}
					//查询当前账户对象
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户不存在！");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户异常，操作失败");
						return;
			    	}
					
					if(Utlity.checkStringNull(frontUserAddress)) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("请填写地址信息！");
						return;
					}
					FrontUserAddress fua = this.frontUserAddressDao.get(frontUserAddress);
					if(fua == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("地址信息有误！");
						return;
					}
					
					//封装地址全信息
					List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
					StringBuilder sb = new StringBuilder();
					if(areaNameList != null && areaNameList.size() > 0) {
						for(String address : areaNameList) {
							sb.append(address);
						}
					}
					sb.append(fua.getAddress());
					
					ProvideInfoVO pivo = new ProvideInfoVO();
					pivo.setAddress(sb.toString());
					pivo.setMobile(fua.getPhone());
					pivo.setName(fua.getReceiver());
					fubo.setProvideInfo(JSONUtils.obj2json(pivo));
					
//					//暂不算入用户统计
//					Goods goods = this.goodsDao.get(fubo.getGoodsId());
//					if(goods != null) {
//						fuacc.setTotalDelivery(fuacc.getTotalDelivery().add(goods.getPrice()));//总兑奖奖品价值（法币）
//						fuacc.setDeliveryTimes(fuacc.getDeliveryTimes() + 1);//次数
//					}	
//					updateMap.put("frontUserAccount", fuacc);
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("兑换信息错误，请稍候再试！");
					return;
				}
				this.frontUserBuyfreeOrderDao.userReceive(fubo, updateMap);
			} else if (ActivityInfoName.CHECKIN.equals(activityInfo)) {
				Map<String, Object> updateMap = new HashMap<String, Object>();
				FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(uuid);
				if(fuch == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("参与记录不存在，如有疑问，请联系客服！");
					return;
				}
				if(!fuch.getFrontUser().equals(frontUser)) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("参与记录不存在，如有疑问，请联系客服！");
					return;
				}
				if(!ActivityInfoCheckinPrizeType.ENTITY.equals(fuch.getPrizeType())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("非实物奖品，不予兑换，如有疑问，请联系客服！");
					return;
				}
				if(FrontUserCheckinHistoryStatus.FINISHED.equals(fuch.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("已完成兑奖，如有疑问，请联系客服！");
					return;
				}
				
				ActivityInfoCheckinPrize aicp = this.activityInfoCheckinPrizeDao.get(fuch.getActivityInfoCheckinPrize());
				if(aicp == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("奖品信息有误！");
					return;
				}
				content.append(aicp.getPrizeTitle()+"商品兑奖申请成功，");
				fum.setSourceId(fuch.getUuid());
				fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_CHECKIN);
				if(FrontUserCheckinHistoryReceiveType.GOLD.equals(type)) {

					content.append("金币已到账！");
					fuch.setStatus(FrontUserCheckinHistoryStatus.FINISHED);
					fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.GOLD);
					
					//查询当前账户对象
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户不存在！");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户异常，操作失败");
						return;
			    	}
					//计算到帐金币
					Goods goods = this.goodsDao.get(aicp.getPrize());
					BigDecimal dAmount = BigDecimal.ZERO;
					if(goods != null) {
						dAmount = goods.getPrice().multiply(rate);
					}
						
					
					//封装交易流水
					FrontUserHistory fuh = new FrontUserHistory();
					fuh.setUuid(UUID.randomUUID().toString());
					fuh.setFrontUser(frontUser);
					fuh.setOrderNum(Utlity.getOrderNum());//
					fuh.setType(FrontUserHistoryType.USER_ADD);
					fuh.setOrderType(Constants.ORDER_TYPE_USER_CHECKIN);
					fuh.setOrderNum(Utlity.getOrderNum());//生成字符串订单号
					fuh.setOrderId(fuch.getUuid());
					fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_CHECKIN));
					fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuh.setdAmount(dAmount);
					fuh.setBalanceBefore(fuacc.getBalance().add(fuacc.getBalanceLock()));
					fuh.setBalanceAfter(fuacc.getBalance().add(fuacc.getBalanceLock()).add(dAmount));
					
					fuh.setRemark("签到活动"+aicp.getPrizeTitle()+"，第"+aicp.getDayNum()+"天");
					//更新账户余额
					fuacc.setBalance(fuacc.getBalance().add(dAmount));//加币
					
//					//暂不算入用户统计
//					fuacc.setTotalExchange(fuacc.getTotalRecharge().add(dAmount));//总兑奖金额
//					fuacc.setExchangeTimes(fuacc.getExchangeTimes() + 1);
					
					updateMap.put("frontUserAccount", fuacc);
					updateMap.put("frontUserHistory", fuh);
					
				} else if(FrontUserCheckinHistoryReceiveType.ENTITY.equals(type)) {
					fuch.setStatus(FrontUserCheckinHistoryStatus.RECEIVE);
					fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.ENTITY);
					content.append("商品待发货！");
					
					if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())){//黑名单校验
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("操作无效， 请联系管理员！");
						return;
					}
					if(!FrontUserType.NORMAL.equals(fu.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("操作无效， 请联系管理员！");
						return;
					}
					//查询当前账户对象
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户不存在！");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户异常，操作失败");
						return;
			    	}
					
					if(Utlity.checkStringNull(frontUserAddress)) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("请填写地址信息！");
						return;
					}
					FrontUserAddress fua = this.frontUserAddressDao.get(frontUserAddress);
					if(fua == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("地址信息有误！");
						return;
					}
					
					//封装地址全信息
					List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
					StringBuilder sb = new StringBuilder();
					if(areaNameList != null && areaNameList.size() > 0) {
						for(String address : areaNameList) {
							sb.append(address);
						}
					}
					sb.append(fua.getAddress());
					
					ProvideInfoVO pivo = new ProvideInfoVO();
					pivo.setAddress(sb.toString());
					pivo.setMobile(fua.getPhone());
					pivo.setName(fua.getReceiver());
					fuch.setProvideInfo(JSONUtils.obj2json(pivo));
					
//					//暂不算入用户统计
//					Goods goods = this.goodsDao.get(fubo.getGoodsId());
//					if(goods != null) {
//						fuacc.setTotalDelivery(fuacc.getTotalDelivery().add(goods.getPrice()));//总兑奖奖品价值（法币）
//						fuacc.setDeliveryTimes(fuacc.getDeliveryTimes() + 1);//次数
//					}	
//					updateMap.put("frontUserAccount", fuacc);
					
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("兑换信息错误，请稍候再试！");
					return;
				}
				this.frontUserCheckinHistoryDao.userReceive(fuch, updateMap);
			} else if (ActivityInfoName.SCORELOTTERY.equals(activityInfo)) {
				Map<String, Object> updateMap = new HashMap<String, Object>();
				FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(uuid);
				if(fush == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("参与记录不存在，如有疑问，请联系客服！");
					return;
				}
				if(!fush.getFrontUser().equals(frontUser)) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("参与记录不存在，如有疑问，请联系客服！");
					return;
				}
				if(!ActivityInfoScorelotteryPrizeType.ENTITY.equals(fush.getPrizeType())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("非实物奖品，不予兑换，如有疑问，请联系客服！");
					return;
				}
				if(FrontUserScorelotteryHistoryStatus.FINISHED.equals(fush.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("已完成兑奖，如有疑问，请联系客服！");
					return;
				}
				
				ActivityInfoScorelotteryPrize aisp = this.activityInfoScorelotteryPrizeDao.get(fush.getActivityInfoScorelotteryPrize());
				if(aisp == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("奖品信息有误！");
					return;
				}
				content.append(aisp.getPrizeTitle()+"商品兑奖申请成功，");
				
				fum.setSourceId(fush.getUuid());
				fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_SCORELOTTERY);
				
				if(FrontUserScorelotteryHistoryReceiveType.GOLD.equals(type)) {
					content.append("金币已到账！");
					fush.setStatus(FrontUserScorelotteryHistoryStatus.FINISHED);
					fush.setReceiveType(FrontUserScorelotteryHistoryReceiveType.GOLD);
					
					//查询当前账户对象
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户不存在！");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户异常，操作失败");
						return;
			    	}
					//计算到帐金币
					Goods goods = this.goodsDao.get(aisp.getPrize());
					BigDecimal dAmount = BigDecimal.ZERO;
					if(goods != null) {
						dAmount = goods.getPrice().multiply(rate);
					}
						
					
					//封装交易流水
					FrontUserHistory fuh = new FrontUserHistory();
					fuh.setUuid(UUID.randomUUID().toString());
					fuh.setFrontUser(frontUser);
					fuh.setOrderNum(Utlity.getOrderNum());//
					fuh.setType(FrontUserHistoryType.USER_ADD);
					fuh.setOrderType(Constants.ORDER_TYPE_USER_SCORELOTTERY);
					fuh.setOrderNum(Utlity.getOrderNum());//生成字符串订单号
					fuh.setOrderId(fush.getUuid());
					fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_SCORELOTTERY));
					fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuh.setdAmount(dAmount);
					fuh.setBalanceBefore(fuacc.getBalance().add(fuacc.getBalanceLock()));
					fuh.setBalanceAfter(fuacc.getBalance().add(fuacc.getBalanceLock()).add(dAmount));
					
					fuh.setRemark("积分转盘摇奖活动"+aisp.getPrizeTitle());
					//更新账户余额
					fuacc.setBalance(fuacc.getBalance().add(dAmount));//加币
					
//					//暂不算入用户统计
//					fuacc.setTotalExchange(fuacc.getTotalRecharge().add(dAmount));//总兑奖金额
//					fuacc.setExchangeTimes(fuacc.getExchangeTimes() + 1);
					
					updateMap.put("frontUserAccount", fuacc);
					updateMap.put("frontUserHistory", fuh);
					
				} else if(FrontUserScorelotteryHistoryReceiveType.ENTITY.equals(type)) {
					fush.setStatus(FrontUserScorelotteryHistoryStatus.RECEIVE);
					fush.setReceiveType(FrontUserScorelotteryHistoryReceiveType.ENTITY);
					content.append("商品待发货！");
					
					if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())){//黑名单校验
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("操作无效， 请联系管理员！");
						return;
					}
					if(!FrontUserType.NORMAL.equals(fu.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("操作无效， 请联系管理员！");
						return;
					}
					//查询当前账户对象
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户不存在！");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户异常，操作失败");
						return;
			    	}
					
					if(Utlity.checkStringNull(frontUserAddress)) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("请填写地址信息！");
						return;
					}
					FrontUserAddress fua = this.frontUserAddressDao.get(frontUserAddress);
					if(fua == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("地址信息有误！");
						return;
					}
					
					//封装地址全信息
					List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
					StringBuilder sb = new StringBuilder();
					if(areaNameList != null && areaNameList.size() > 0) {
						for(String address : areaNameList) {
							sb.append(address);
						}
					}
					sb.append(fua.getAddress());
					
					ProvideInfoVO pivo = new ProvideInfoVO();
					pivo.setAddress(sb.toString());
					pivo.setMobile(fua.getPhone());
					pivo.setName(fua.getReceiver());
					fush.setProvideInfo(JSONUtils.obj2json(pivo));
					
//					//暂不算入用户统计
//					Goods goods = this.goodsDao.get(fubo.getGoodsId());
//					if(goods != null) {
//						fuacc.setTotalDelivery(fuacc.getTotalDelivery().add(goods.getPrice()));//总兑奖奖品价值（法币）
//						fuacc.setDeliveryTimes(fuacc.getDeliveryTimes() + 1);//次数
//					}	
//					updateMap.put("frontUserAccount", fuacc);
					
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("兑换信息错误，请稍候再试！");
					return;
				}
				this.frontUserScorelotteryHistoryDao.userReceive(fush, updateMap);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("活动数据参数异常，请稍候再试！");
				return;
			}
			
			fum.setContent(content.toString());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_WIN);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			this.frontUserMessageDao.sendMessage(fum);
			if("gold".equals(type)) {
				this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECEIVE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("网络异常，请稍后重试！");
			return;
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("兑换成功！");
		return;
	}
}
