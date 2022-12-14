package cn.product.worldmall.service.front.impl;

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

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.ActivityInfoBuyfreeDao;
import cn.product.worldmall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.worldmall.dao.ActivityInfoBuyfreeSharesnumDao;
import cn.product.worldmall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.worldmall.dao.ActivityInfoDao;
import cn.product.worldmall.dao.ActivityInfoFirstchargePrizeDao;
import cn.product.worldmall.dao.ActivityInfoScorelotteryPrizeDao;
import cn.product.worldmall.dao.AreaDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserAddressDao;
import cn.product.worldmall.dao.FrontUserBuyfreeOrderDao;
import cn.product.worldmall.dao.FrontUserCheckinHistoryDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.FrontUserScorelotteryHistoryDao;
import cn.product.worldmall.dao.FrontUserVoucherDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.dao.VoucherDao;
import cn.product.worldmall.entity.ActivityInfo;
import cn.product.worldmall.entity.ActivityInfoBuyfree;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods;
import cn.product.worldmall.entity.ActivityInfoBuyfreeSharesnum;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize;
import cn.product.worldmall.entity.ActivityInfoFirstchargePrize;
import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize;
import cn.product.worldmall.entity.AdminOffsetOrder;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserAddress;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder;
import cn.product.worldmall.entity.FrontUserCheckinHistory;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.FrontUserScoreHistory;
import cn.product.worldmall.entity.FrontUserScorelotteryHistory;
import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.Voucher;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoName;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.worldmall.entity.ActivityInfoBuyfree.ActivityInfoBuyfreeStatus;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods.ActivityInfoBuyfreeGoodsStatus;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeStatus;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeType;
import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize.ActivityInfoScorelotteryPrizeStatus;
import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize.ActivityInfoScorelotteryPrizeType;
import cn.product.worldmall.entity.AdminOffsetOrder.AdminOffsetOrderStatus;
import cn.product.worldmall.entity.AdminOffsetOrder.AdminOffsetOrderType;
import cn.product.worldmall.entity.FrontUser.FrontUserStatus;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder.FrontUserBuyfreeOrderReceiveType;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder.FrontUserBuyfreeOrderStatus;
import cn.product.worldmall.entity.FrontUserCheckinHistory.FrontUserCheckinHistoryReceiveType;
import cn.product.worldmall.entity.FrontUserCheckinHistory.FrontUserCheckinHistoryStatus;
import cn.product.worldmall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.worldmall.entity.FrontUserScorelotteryHistory.FrontUserScorelotteryHistoryReceiveType;
import cn.product.worldmall.entity.FrontUserScorelotteryHistory.FrontUserScorelotteryHistoryStatus;
import cn.product.worldmall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.Voucher.VoucherStatus;
import cn.product.worldmall.entity.activity.ConfigBuyfree;
import cn.product.worldmall.entity.activity.ConfigScorelottery;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.service.front.FrontActivityInfoService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.ProvideInfoVO;
import cn.product.worldmall.vo.back.SharenumsVO;
import cn.product.worldmall.vo.front.ActivityInfoBuyfreeVO;
import cn.product.worldmall.vo.front.ActivityInfoCheckinPrizeVO;
import cn.product.worldmall.vo.front.ActivityInfoScorelotteryPrizeVO;
import cn.product.worldmall.vo.front.ActivityInfoVO;
import cn.product.worldmall.vo.front.FrontUserBuyfreeOrderVO;
import cn.product.worldmall.vo.front.FrontUserCheckinHistoryVO;
import cn.product.worldmall.vo.front.FrontUserScorelotteryHistoryVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

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
	
	@Autowired
	private InternationalInfoDao internationalInfoDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		//??????????????????
		ActivityInfo ai = activityInfoDao.get(name);
		if (ai == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The data does not exist!");
			return;
		}
		
		try {
			ActivityInfoVO vo = new ActivityInfoVO(ai);
			if(ActivityInfoName.BUYFREE.equals(ai.getName())) {
				ConfigBuyfree cb = JSONUtils.json2obj(ai.getConfiguration(), ConfigBuyfree.class);
				if(cb != null) {
					vo.setCurrentTimes(cb.getTimesLine());
				}
				
				//?????????????????????
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
				//?????????????????????
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
			} else if(ActivityInfoName.SCORELOTTERY.equals(ai.getName())) {//????????????
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
			} else if(ActivityInfoName.FIRSTCHARGE.equals(ai.getName())) {//??????
				//20200610???????????????????????????
				//??????????????????????????????????????????????????????????????????????????????????????????
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
			result.setMessage("Network anomalies, please try again later!");	
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
		
		//????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		try {
			//??????????????????????????????????????????
			Integer totalResultCount = activityInfoDao.getCountByParams(searchMap);
			//???????????????????????????????????????
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
						
						//?????????????????????
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
						//?????????????????????
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
					} else if(ActivityInfoName.SCORELOTTERY.equals(ai.getName())) {//????????????
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
			result.setMessage("Network anomalies, please try again later!");	
		}
	}

	@Override
	public void buyfreeGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	
    	ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(uuid);
    	if(aib == null) {
    		result.setMessage("Product information error, please try again later!");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	ActivityInfoBuyfreeVO aibvo = new ActivityInfoBuyfreeVO(aib);
    	ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(aib.getActivityInfoBuyfreeGoods());
    	if(aibg != null) {//????????????
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
    	if(ActivityInfoBuyfreeStatus.LOTTERY.equals(aib.getStatus())) {//?????????
			aibvo.setTimeLine(aib.getLotterytime().getTime()+Utlity.TIMELINE-System.currentTimeMillis());
		}
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
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
		
		//???????????????????????????
		if(!Utlity.checkStringNull(frontUser)) {//?????????frontUser
			Map<String, Object> searchMap = new HashMap<String, Object>();
//			searchMap.put("activityInfoBuyfree", aib.getUuid());
			searchMap.put("goodsId", aib.getGoodsId());//goodsId
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
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(ActivityInfoBuyfree aib : list) {
				ActivityInfoBuyfreeVO aibvo = new ActivityInfoBuyfreeVO(aib);
				ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(aib.getActivityInfoBuyfreeGoods());
		    	if(aibg != null) {//????????????
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
				if(ActivityInfoBuyfreeStatus.LOTTERY.equals(aib.getStatus())) {//?????????
					aibvo.setTimeLine(aib.getLotterytime().getTime()+Utlity.TIMELINE-System.currentTimeMillis());
				}
		    	
				aibvo.setGoodsCoverUrl("");
				Resource re = this.resourceDao.get(aib.getGoodsCover());
				if(re != null) {
					aibvo.setGoodsCoverUrl(pathUrl + re.getUrl());
				}
				
				//???????????????????????????
				if(!Utlity.checkStringNull(frontUser)) {//?????????frontUser
					searchMap.clear();
//					searchMap.put("activityInfoBuyfree", aib.getUuid());
					searchMap.put("goodsId", aib.getGoodsId());//goodsId
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
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "1" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "10" : paramsMap.get("pageSize").toString());
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
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(FrontUserBuyfreeOrder fubo : list) {
				FrontUserBuyfreeOrderVO fubovo = new FrontUserBuyfreeOrderVO(fubo);
				//??????????????????
				FrontUser fu = this.frontUserDao.get(fubo.getFrontUser());
				if(fu != null) {
					fubovo.setNickname(fu.getNickname());
					//??????????????????
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fubovo.setImageUrl(pathUrl + res.getUrl());
						}
					}
				}
				
				//??????????????????
				ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(fubo.getActivityInfoBuyfree());
				if(aib != null) {
					//??????????????????
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
				
				//?????????-?????????????????????????????????ID--frontUserHistory
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
		result.setMessage("Successful!");
		return;	
		
		
	}

	@Override
	public void buyfreeHistoryGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();	
    	
    	FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(uuid);
    	
    	if(fubo == null) {
    		result.setMessage("Active order information error, please try again later!");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
    	if(!Utlity.checkStringNull(frontUser)) {
    		if(!fubo.getFrontUser().equals(frontUser)) {
    			result.setMessage("Active order information error, please try again later!");
        		result.setStatus(ResultStatusType.FAILED);
        		return;
    		}
    	}
    	FrontUserBuyfreeOrderVO fubovo = new FrontUserBuyfreeOrderVO(fubo);
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		
		//??????????????????
		FrontUser fu = this.frontUserDao.get(fubo.getFrontUser());
		if(fu != null) {
			fubovo.setNickname(fu.getNickname());
			//??????????????????
			if(!Utlity.checkStringNull(fu.getImage())) {
				Resource res = resourceDao.get(fu.getImage());
				if(res != null) {
					fubovo.setImageUrl(pathUrl + res.getUrl());
				}
			}
		}
		
		//??????????????????
		ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(fubo.getActivityInfoBuyfree());
		if(aib != null) {
			//??????????????????
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
		
		//?????????-?????????????????????????????????ID--frontUserHistory
		if(FrontUserBuyfreeOrderReceiveType.GOLD.equals(fubo.getReceiveType())) {
			FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fubo.getUuid());
			if(fuh != null) {
				fubovo.setFrontUserHistory(fuh.getUuid());
				fubovo.setDealDAmount(fuh.getdAmount());
			}
		}
		
		result.setData(fubovo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
		return;	
	}

	@Override
	public void buyfreePartake(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	
    	/*
		 * ?????????-???????????????
		 */
		InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+"buyfree-"+uuid+"-lock");
        log.info("??????zookeeper???");
    	try {
    		List<FrontUserBuyfreeOrder> listOrder = new ArrayList<FrontUserBuyfreeOrder>();
        	List<ActivityInfoBuyfreeSharesnum> listNums = new ArrayList<ActivityInfoBuyfreeSharesnum>();
        	List<ActivityInfoBuyfree> listBuyfree = new ArrayList<ActivityInfoBuyfree>();
        	Map<String, Object> doMap = new HashMap<String, Object>();
    		
    		if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
    			log.info("??????zookeeper???-----------------??????");
    			FrontUser fu = this.frontUserDao.get(frontUser);
    			if(fu == null) {
    				result.setMessage("User status error, please contact customer service!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    				
    			if(!FrontUserStatus.NORMAL.equals(fu.getStatus()) && !FrontUserStatus.HIGHRISK.equals(fu.getStatus())) {
    				result.setMessage("User status error, please contact customer service!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(uuid);
    	    	if(aib == null) {
    	    		result.setMessage("Product information error, please try again later!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	//??????????????????????????????????????????

    	    	if(!ActivityInfoName.BUYFREE.equals(aib.getActivityInfo())) {
    	    		result.setMessage("Active information is wrong!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	
    	    	ActivityInfo ai = activityInfoDao.get(aib.getActivityInfo());
    	    	if(ai == null || !ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
    	    		result.setMessage("Active has ended!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	
    	    	ConfigBuyfree cb = JSONUtils.json2obj(ai.getConfiguration(), ConfigBuyfree.class);
    	    	if(cb == null) {
    	    		result.setMessage("Active information is wrong!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	//????????????????????????????????????????????????????????????
    	    	Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("frontUser", frontUser);
				searchMap.put("timeline", "0");//goodsId
				searchMap.put("goodsId", aib.getGoodsId());//goodsId
				
				Integer count = this.frontUserBuyfreeOrderDao.getCountByParams(searchMap);
				if(count != null && count > 0) {
					if(count.intValue() - cb.getTimesLine().intValue() >= 0) {
//						result.setMessage("Not enough draws today!");
						result.setMessage("Free draw times is used up today!");
	    	    		result.setStatus(ResultStatusType.FAILED);
	    	    		return;
					}
				}
				
				if(!ActivityInfoBuyfreeStatus.BETTING.equals(aib.getStatus())) {
    	    		result.setMessage("Product information error, please try again later!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
				
    	    	if(aib.getRemainShares().intValue() <= 0) {
    	    		result.setMessage("The current draw is insufficient,  please re-order!");
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
				 * ??????????????????
				 */
				ActivityInfoBuyfreeSharesnum aibs = this.activityInfoBuyfreeSharesnumDao.get(aib.getUuid());
				SharenumsVO svo = JSONUtils.json2obj(aibs.getSharenums(), SharenumsVO.class);
				List<Integer> currentNums = svo.getCurrentNums();
				List<Integer> usedNums = svo.getUsedNums();
				Integer userNums = 0;
				//20200512??????????????????
				int ramNum = Utlity.getRandomNum(0, currentNums.size());
				userNums = currentNums.get(ramNum);
				usedNums.add(currentNums.get(ramNum));
				currentNums.remove(ramNum);
				
				svo.setCurrentNums(currentNums);
				svo.setUsedNums(usedNums);
				aibs.setSharenums(JSONUtils.obj2json(svo));
				listNums.add(aibs);//??????????????????
				
				fubo.setSharenum(userNums);
				listOrder.add(fubo);//????????????
				
				
				
				//??????????????????
				aib.setBetShares(aib.getBetShares().intValue() + 1);
				aib.setRemainShares(aib.getRemainShares().intValue() - 1);
				boolean isLottery = false;
				boolean flag = false;
            	String buyfree = "";
				//??????????????????????????????????????????????????????
				if(aib.getRemainShares() == 0) {
					isLottery = true;
					buyfree = aib.getUuid();
					aib.setStatus(ActivityInfoBuyfreeStatus.LOTTERY);
					aib.setLotterytime(new Timestamp(System.currentTimeMillis()));
					
					//??????????????????????????????
					//??????????????????-???????????????????????????
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
        		
//        		//??????????????????--????????????
//				FrontUserMessage fum = new FrontUserMessage();
//				fum.setUuid(UUID.randomUUID().toString());
//				fum.setFrontUser(fu.getUuid());
//				fum.setFrontUserShowId(fu.getShowId());
//				fum.setTitle("???????????????????????????");
//				fum.setContent("??????"+Utlity.timeSpanToChinaDateString(fubo.getCreatetime())+"?????????"+aib.getGoodsShortTitle()+"????????????Successful!");
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
        		result.setMessage("Successful!");
        		result.setStatus(ResultStatusType.SUCCESS);
        		return;
    		}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Network busy, please try again later!");
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
	 * ?????????????????????????????????????????????
	 * ??????????????????????????????????????????????????????isCheckin??????
	 * ?????????????????????????????????
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
		

    	//???????????????????????????????????????????????????????????????
    	int dayNum = 0;
    	Integer totalDayNum = 0;
    	//????????????????????????????????????????????????
		
//		List<ActivityInfoCheckinPrizeVO> volist = new ArrayList<ActivityInfoCheckinPrizeVO>();
		Map<Integer, ActivityInfoCheckinPrizeVO> listMap = Maps.newLinkedHashMap();
		if(list != null && list.size() > 0) {
			if(!Utlity.checkStringNull(frontUser)) {
				searchMap.clear();
		    	searchMap.put("frontUser", frontUser);
				searchMap.put("timeline", "1");
				List<FrontUserCheckinHistory> listCheckin = this.frontUserCheckinHistoryDao.getListByParams(searchMap);
				if(listCheckin != null && listCheckin.size() > 0) {//???????????????=?????????
					FrontUserCheckinHistory fuch = listCheckin.get(0);
					totalDayNum = fuch.getDayNum();//???????????????
				}
				
				//???????????????????????????
				searchMap.put("timeline", "0");
				Integer count = this.frontUserCheckinHistoryDao.getCountByParams(searchMap);
				if(count != null && count > 0) {//??????
					totalDayNum += 1;
				}
				dayNum = Integer.valueOf(totalDayNum%totalCount);
			}
			
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
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
					//????????????
					Goods goods = this.goodsDao.get(aicp.getPrize());
					if(goods != null) {
						vo.setPrizeDetail(goods.getName());
					}
				} else if(ActivityInfoCheckinPrizeType.VOUCHER.equals(aicp.getPrizeType())) {
					//????????????
					Voucher v = this.voucherDao.get(aicp.getPrize());
					if(v != null) {
						vo.setPrizeDetail(v.getTitle());
					}
				} else if(ActivityInfoCheckinPrizeType.ENTITY.equals(aicp.getPrizeType())) {
					//????????????
					vo.setPrizeDetail(aicp.getPrize());
				}
				listMap.put(vo.getDayNum(), vo);
//				volist.add(vo);
			}
			
			//????????????
			if(dayNum > 0) {
				for(int i = 1; i <= dayNum; i++) {
					ActivityInfoCheckinPrizeVO vo = listMap.get(i);
					vo.setIsCheckin(true);
					listMap.put(i, vo);
				}
			}
		}
		returnMap.put("dayNums", dayNum);
		returnMap.put("dataMap", listMap);//N??????map??????????????????????????????1?????????N???
//		returnMap.put("dayNums", dayNum);//?????????????????????????????????????????????0
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
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "1" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "10" : paramsMap.get("pageSize").toString());
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
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(FrontUserCheckinHistory fuch : list) {
				FrontUserCheckinHistoryVO fuchvo = new FrontUserCheckinHistoryVO(fuch);
				//??????????????????
				FrontUser fu = this.frontUserDao.get(fuch.getFrontUser());
				if(fu != null) {
					fuchvo.setNickname(fu.getNickname());
					//??????????????????
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
				
				//?????????-?????????????????????????????????ID--frontUserHistory
				if(FrontUserCheckinHistoryReceiveType.GOLD.equals(fuch.getReceiveType())) {
					FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fuch.getUuid());
					if(fuh != null) {
						fuchvo.setFrontUserHistory(fuh.getUuid());
						fuchvo.setDealDAmount(fuh.getdAmount());
					}
				}
				if(ActivityInfoCheckinPrizeType.ENTITY.equals(fuch.getPrizeType())) {
					//??????????????????
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
		result.setMessage("Successful!");
		return;
	}

	@Override
	public void checkinHistoryGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();	
    	
    	FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(uuid);
    	
    	if(fuch == null) {
    		result.setMessage("Active order information error, please try again later!");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
    	if(!Utlity.checkStringNull(frontUser)) {
    		if(!fuch.getFrontUser().equals(frontUser)) {
    			result.setMessage("Active order information error, please try again later!");
        		result.setStatus(ResultStatusType.FAILED);
        		return;
    		}
    	}
    	FrontUserCheckinHistoryVO fuchvo = new FrontUserCheckinHistoryVO(fuch);
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		//??????????????????
		FrontUser fu = this.frontUserDao.get(fuch.getFrontUser());
		if(fu != null) {
			fuchvo.setNickname(fu.getNickname());
			//??????????????????
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
		
		//?????????-?????????????????????????????????ID--frontUserHistory
		if(FrontUserCheckinHistoryReceiveType.GOLD.equals(fuch.getReceiveType())) {
			FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fuch.getUuid());
			if(fuh != null) {
				fuchvo.setFrontUserHistory(fuh.getUuid());
				fuchvo.setDealDAmount(fuh.getdAmount());
			}
		}
		if(ActivityInfoCheckinPrizeType.ENTITY.equals(fuch.getPrizeType())) {
			//??????????????????
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
		result.setMessage("Successful!");
		return;	
	}

	@Override
	public void checkinPartake(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
//    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	Map<String, Object> resultMap = Maps.newHashMap();
    	//????????????????????????
    	//?????????????????????
    	
    	/*
		 * ?????????-???????????????
		 */
		InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+"checkin-"+frontUser+"-lock");
        log.info("??????zookeeper???");
    	try {
    		Map<String, Object> doMap = new HashMap<String, Object>();
    		if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
    			log.info("??????zookeeper???-----------------??????:"+Constants.ZK_LOCK_PATHPREFIX+"checkin-"+frontUser+"-lock");
    			/*
    			 * ???????????????????????????
    			 */
    			ActivityInfo ai = this.activityInfoDao.get(ActivityInfoName.CHECKIN);
    			if(ai == null || ActivityInfoStatus.DELETE.equals(ai.getStatus())) {
    				result.setMessage("Active information is wrong!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			if(ActivityInfoStatus.DISABLE.equals(ai.getStatus())) {
    				result.setMessage("Active has ended!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			FrontUser fu = this.frontUserDao.get(frontUser);
    			if(fu == null) {
    				result.setMessage("User status error, please contact customer service!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    				
    			if(!FrontUserStatus.NORMAL.equals(fu.getStatus()) && !FrontUserStatus.HIGHRISK.equals(fu.getStatus())) {
    				result.setMessage("User status error, please contact customer service!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
    			if(fua == null){
    				result.setStatus(ResultStatusType.FAILED);
    				result.setMessage("User status error, please contact customer service!");
    				return;
    			}
    			
    			if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
    				result.setStatus(ResultStatusType.FAILED);
    				result.setMessage("User status error, please contact customer service!");
    				return;
    			}
    			
//    			ActivityInfoCheckinPrize aicp = this.activityInfoCheckinPrizeDao.get(uuid);
//        		if(aicp == null || !ActivityInfoCheckinPrizeStatus.NORMAL.equals(aicp.getStatus())) {
//        			result.setMessage("Check-in information is wrong!");
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
            	//???????????????????????????????????????????????????????????????
            	int dayNum = 0;
            	int totalDayNum = 0;
            	
            	searchMap.clear();
            	searchMap.put("frontUser", frontUser);
            	//???????????????????????????
    			searchMap.put("timeline", "0");
    			Integer count = this.frontUserCheckinHistoryDao.getCountByParams(searchMap);
    			if(count != null && count > 0) {//??????
    				result.setMessage("Do not check-in repeatedly!");
            		result.setStatus(ResultStatusType.FAILED);
            		return;
    			}
        		
    			//???????????????=?????????
    			searchMap.put("timeline", "1");
        		List<FrontUserCheckinHistory> listCheckin = this.frontUserCheckinHistoryDao.getListByParams(searchMap);
        		if(listCheckin != null && listCheckin.size() > 0) {
        			FrontUserCheckinHistory fuch = listCheckin.get(0);
        			totalDayNum = fuch.getDayNum();//???????????????
        		}
    			dayNum = Integer.valueOf(totalDayNum%totalCount);
        		
//    			if(dayNum != (aicp.getDayNum() - 1)) {//???????????????
//    				result.setMessage("??????????????????!");
//            		result.setStatus(ResultStatusType.FAILED);
//            		return;
//    			}
        		ActivityInfoCheckinPrize aicp = listMap.get(dayNum + 1);
        		if(aicp == null || !ActivityInfoCheckinPrizeStatus.NORMAL.equals(aicp.getStatus())) {
	    			result.setMessage("Check-in information is wrong!");
	        		result.setStatus(ResultStatusType.FAILED);
	        		return;
	    		}
        		
    			//??????????????????
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
    			fuch.setRemark("User check-in activity, current day "+ aicp.getDayNum());
    			fuch.setStatus(FrontUserCheckinHistoryStatus.NORMAL);
    	    	fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.NORMAL);
    			//??????????????????????????????
    			if(ActivityInfoCheckinPrizeType.GOLD.equals(aicp.getPrizeType())) {//????????????

        			fuch.setStatus(FrontUserCheckinHistoryStatus.FINISHED);
        			fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.GOLD);
        			
    				BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(aicp.getPrize()));
    				String content = "Check-in activity gift" + aicp.getPrize() + "coin";
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
					
    			} else if(ActivityInfoCheckinPrizeType.VOUCHER.equals(aicp.getPrizeType())) {//????????????
    				
    				fuch.setStatus(FrontUserCheckinHistoryStatus.FINISHED);
    				fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.VOUCHER);
    				
    				Voucher v = this.voucherDao.get(aicp.getPrize());
    				if(v == null || VoucherStatus.DELETE.equals(v.getStatus())) {
    					result.setMessage("Prizes information error, please try again later!");
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
					
					//??????voucher?????????????????????????????????????????????????????????
					fuv.setStarttime(Utlity.getTime(v.getStarttime()) == null ? new Timestamp(System.currentTimeMillis()) : Utlity.getTime(v.getStarttime()));//?????????????????????????????????
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
    			result.setMessage("Successful!");
        		result.setStatus(ResultStatusType.SUCCESS);
        		return;
    		}
    		
    	} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Network busy, please try again later!");
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
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
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
					//????????????
					Goods goods = this.goodsDao.get(aisp.getPrize());
					if(goods != null) {
						vo.setPrizeDetail(goods.getName());
					}
				} else if(ActivityInfoCheckinPrizeType.VOUCHER.equals(aisp.getPrizeType())) {
					//????????????
					Voucher v = this.voucherDao.get(aisp.getPrize());
					if(v != null) {
						vo.setPrizeDetail(v.getTitle());
					}
				} else if(ActivityInfoCheckinPrizeType.ENTITY.equals(aisp.getPrizeType())) {
					//????????????
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
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "1" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "10" : paramsMap.get("pageSize").toString());
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
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(FrontUserScorelotteryHistory fush : list) {
				FrontUserScorelotteryHistoryVO fushvo = new FrontUserScorelotteryHistoryVO(fush);
				//??????????????????
				FrontUser fu = this.frontUserDao.get(fush.getFrontUser());
				if(fu != null) {
					fushvo.setNickname(fu.getNickname());
					//??????????????????
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
				
				//?????????-?????????????????????????????????ID--frontUserHistory
				if(FrontUserScorelotteryHistoryReceiveType.GOLD.equals(fush.getReceiveType())) {
					FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fush.getUuid());
					if(fuh != null) {
						fushvo.setFrontUserHistory(fuh.getUuid());
						fushvo.setDealDAmount(fuh.getdAmount());
					}
				}
				
				if(ActivityInfoCheckinPrizeType.ENTITY.equals(fush.getPrizeType())) {
					//??????????????????
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
		result.setMessage("Successful!");
		return;
	}

	@Override
	public void scorelotteryHistoryGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();	
    	
    	FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(uuid);
    	
    	if(fush == null) {
    		result.setMessage("Active order information error, please try again later!");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
    	if(!Utlity.checkStringNull(frontUser)) {
    		if(!fush.getFrontUser().equals(frontUser)) {
    			result.setMessage("Active order information error, please try again later!");
        		result.setStatus(ResultStatusType.FAILED);
        		return;
    		}
    	}
    	FrontUserScorelotteryHistoryVO fushvo = new FrontUserScorelotteryHistoryVO(fush);
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		//??????????????????
		FrontUser fu = this.frontUserDao.get(fush.getFrontUser());
		if(fu != null) {
			fushvo.setNickname(fu.getNickname());
			//??????????????????
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
		
		//?????????-?????????????????????????????????ID--frontUserHistory
		if(FrontUserScorelotteryHistoryReceiveType.GOLD.equals(fush.getReceiveType())) {
			FrontUserHistory fuh = this.frontUserHistoryDao.getByOrderId(fush.getUuid());
			if(fuh != null) {
				fushvo.setFrontUserHistory(fuh.getUuid());
				fushvo.setDealDAmount(fuh.getdAmount());
			}
		}
		
		if(ActivityInfoCheckinPrizeType.ENTITY.equals(fush.getPrizeType())) {
			//??????????????????
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
		result.setMessage("Successful!");
		return;	
	}

	/**
	 * ???????????????
	 * ??????1~100w???int??????
	 * ??????1~100w??????????????????M
	 * ???????????????????????????1~100w??????????????????????????????I
	 * ??????M?????????I?????????I??????????????????
	 */
	@Override
	public void scorelotteryPartake(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	Map<String, Object> resultMap = Maps.newHashMap();
    	
    	/*
		 * ?????????-???????????????
		 */
		InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+"scorelottery-"+frontUser+"-lock");
        log.info("??????zookeeper???");
    	try {
    		Map<String, Object> doMap = new HashMap<String, Object>();
    		if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
    			log.info("??????zookeeper???-----------------??????:"+Constants.ZK_LOCK_PATHPREFIX+"scorelottery-"+frontUser+"-lock");
    			/*
    			 * ???????????????????????????
    			 */
    			ActivityInfo ai = this.activityInfoDao.get(ActivityInfoName.SCORELOTTERY);
    			if(ai == null || ActivityInfoStatus.DELETE.equals(ai.getStatus())) {
    				result.setMessage("Active information is wrong!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			if(ActivityInfoStatus.DISABLE.equals(ai.getStatus())) {
    				result.setMessage("Active has ended!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			ConfigScorelottery cs = JSONUtils.json2obj(ai.getConfiguration(), ConfigScorelottery.class);
    			if(cs == null) {
    				result.setMessage("Active information is wrong,please try again later!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			FrontUser fu = this.frontUserDao.get(frontUser);
    			if(fu == null) {
    				result.setMessage("User status error, please contact customer service!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    				
    			if(!FrontUserStatus.NORMAL.equals(fu.getStatus()) && !FrontUserStatus.HIGHRISK.equals(fu.getStatus())) {
    				result.setMessage("User status error, please contact customer service!");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			
    			FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
    			if(fua == null){
    				result.setStatus(ResultStatusType.FAILED);
    				result.setMessage("User status error, please contact customer service!");
    				return;
    			}
    			
    			if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
    				result.setStatus(ResultStatusType.FAILED);
    				result.setMessage("User status error, please contact customer service!");
    				return;
    			}
    			//??????????????????????????????
    			if(fua.getScoreBalance().compareTo(BigDecimal.valueOf(Double.valueOf(cs.getScoreLine()))) <= 0) {
    				result.setStatus(ResultStatusType.FAILED);
    				result.setMessage("User points are insufficient to participate in this event!");
    				return;
    			}
    			
    			//????????????
    			/*
    			 * ????????????????????????
    			 * ????????????????????????100w????????????????????????N??????????????????
    			 * ??????????????????0~100w??????????????????
    			 * ????????????????????????????????????????????????????????????
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
        					if(lotteryNum == totalNum) {//??????
        						lotteryPrize = aisp;
        					}
        				}
        			}
        			if(totalNum != 1000000) {
        				result.setMessage("Integral lottery information error, please try again later!");
    	        		result.setStatus(ResultStatusType.FAILED);
    	        		return;
        			}
        		}
        		
        		
        		if(lotteryPrize == null || !ActivityInfoScorelotteryPrizeStatus.NORMAL.equals(lotteryPrize.getStatus())) {
	    			result.setMessage("Integral lottery information error, please try again later!");
	        		result.setStatus(ResultStatusType.FAILED);
	        		return;
	    		}
        		
    			//??????????????????
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
    			fush.setRemark("Points sweepstakes award,"+lotteryPrize.getPrizeTitle());
    			fush.setStatus(FrontUserScorelotteryHistoryStatus.NORMAL);
    			fush.setScoreAmount(BigDecimal.valueOf(Double.valueOf(cs.getScoreLine())));
    	    	fush.setReceiveType(FrontUserCheckinHistoryReceiveType.NORMAL);
    			
    			
    			if(BigDecimal.valueOf(Double.valueOf(cs.getScoreLine())).compareTo(BigDecimal.ZERO) > 0) {
    				//??????????????????
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
    				fushistory.setRemark("Participate in sweepstakes points!");
    				
    				fua.setScoreBalance(fua.getScoreBalance().subtract(BigDecimal.valueOf(Double.valueOf(cs.getScoreLine()))));
        			doMap.put("scoreHistory", fushistory);
    			}
    			//??????????????????????????????
    			if(ActivityInfoScorelotteryPrizeType.GOLD.equals(lotteryPrize.getPrizeType())) {//????????????

        			fush.setStatus(FrontUserScorelotteryHistoryStatus.FINISHED);
        			fush.setReceiveType(FrontUserScorelotteryHistoryReceiveType.GOLD);
        			
    				BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(lotteryPrize.getPrize()));
    				String content = "Points sweepstakes award " + lotteryPrize.getPrize() + "coin!";
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
    			} else if(ActivityInfoScorelotteryPrizeType.VOUCHER.equals(lotteryPrize.getPrizeType())) {//????????????
    				
    				fush.setStatus(FrontUserScorelotteryHistoryStatus.FINISHED);
        			fush.setReceiveType(FrontUserScorelotteryHistoryReceiveType.VOUCHER);
    				
    				Voucher v = this.voucherDao.get(lotteryPrize.getPrize());
    				if(v == null || VoucherStatus.DELETE.equals(v.getStatus())) {
    					result.setMessage("Prizes information error, please try again later!");
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
					
					//??????voucher?????????????????????????????????????????????????????????
					fuv.setStarttime(Utlity.getTime(v.getStarttime()) == null ? new Timestamp(System.currentTimeMillis()) : Utlity.getTime(v.getStarttime()));//?????????????????????????????????
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
    			result.setMessage("Successful!");
        		result.setStatus(ResultStatusType.SUCCESS);
        		return;
    			
    		}
    		
    	} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Network busy, please try again later!");
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
	 * ??????????????????
	 * ???????????????????????????
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
			result.setMessage("User exception, operation failed!");
			return;
		}
		
		//?????????????????????????????????
		if(Utlity.checkStringNull(activityInfo)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Abnormal activity data, please try again later!");
			return;
		}
		
		try {
			//??????????????????
			SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sp != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
			}
			
			//??????????????????
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fu.getUuid());
			fum.setFrontUserShowId(fu.getShowId());
			fum.setTitle("Redemption Successful");
//			StringBuilder content = new StringBuilder("??????"+Utlity.timeSpanToChinaDateString(new Timestamp(System.currentTimeMillis()))+"?????????");
			StringBuilder content = new StringBuilder("You initiated "
//					+ "on " + Utlity.timeSpanToUsString(new Timestamp(System.currentTimeMillis())) 
					+ " ");
			
			if(ActivityInfoName.BUYFREE.equals(activityInfo)) {//0??????
				Map<String, Object> updateMap = new HashMap<String, Object>();
				FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(uuid);
				if(fubo == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Participation record does not exist, if in doubt, please contact customer service!");
					return;
				}
				if(!fubo.getFrontUser().equals(frontUser)) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Participation record does not exist, if in doubt, please contact customer service!");
					return;
				}
				if(FrontUserBuyfreeOrderStatus.FINISHED.equals(fubo.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("The prizes are redeemed, if in doubt, please contact customer service!");
					return;
				}
				if(!FrontUserBuyfreeOrderStatus.WIN.equals(fubo.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("The status of the participation record is incorrect and the prize cannot be claimed!");
					return;
				}
				
				//+lgi.getShortTitle()+" Commodity redemption application is successful,"
				ActivityInfoBuyfree aibf = this.activityInfoBuyfreeDao.get(fubo.getActivityInfoBuyfree());
				if(aibf == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Prizes information is wrong!");
					return;
				}
				fum.setSourceId(fubo.getUuid());
				fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_BUYFREE);
//				content.append(aibf.getGoodsShortTitle()+"Commodity redemption application is successful,");
				content.append(aibf.getGoodsShortTitle()+" Commodity redemption application is successful, ");
				if(FrontUserBuyfreeOrderReceiveType.GOLD.equals(type)) {
					content.append("the coins have arrived!");
					fubo.setStatus(FrontUserBuyfreeOrderStatus.FINISHED);
					fubo.setReceiveType(FrontUserBuyfreeOrderReceiveType.GOLD);
					//????????????????????????
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
			    	}
					//??????????????????
//					BigDecimal dAmount = aibf.getGoodsPrice().multiply(rate);
					//??????????????????
					Goods goods = this.goodsDao.get(aibf.getGoodsId());
					BigDecimal dAmount = BigDecimal.ZERO;
					if(goods != null) {
						dAmount = goods.getPrice().multiply(rate);
					}
					
					//??????????????????
					FrontUserHistory fuh = new FrontUserHistory();
					fuh.setUuid(UUID.randomUUID().toString());
					fuh.setFrontUser(frontUser);
					fuh.setOrderNum(Utlity.getOrderNum());//
					fuh.setType(FrontUserHistoryType.USER_ADD);
					fuh.setOrderType(Constants.ORDER_TYPE_USER_BUYFREE);
					fuh.setOrderNum(Utlity.getOrderNum());//????????????????????????
					fuh.setOrderId(fubo.getUuid());
					fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_BUYFREE));
					fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuh.setdAmount(dAmount);
					fuh.setBalanceBefore(fuacc.getBalance().add(fuacc.getBalanceLock()));
					fuh.setBalanceAfter(fuacc.getBalance().add(fuacc.getBalanceLock()).add(dAmount));
					
					fuh.setRemark("buyfree activity"+aibf.getGoodsShortTitle()+"period"+aibf.getIssueNum());
					//??????????????????
					fuacc.setBalance(fuacc.getBalance().add(dAmount));//??????
					
//					//????????????????????????
//					fuacc.setTotalExchange(fuacc.getTotalRecharge().add(dAmount));//???????????????
//					fuacc.setExchangeTimes(fuacc.getExchangeTimes() + 1);
					
					updateMap.put("frontUserAccount", fuacc);
					updateMap.put("frontUserHistory", fuh);
				} else if(FrontUserBuyfreeOrderReceiveType.ENTITY.equals(type)) {
					fubo.setStatus(FrontUserBuyfreeOrderStatus.RECEIVE);
					fubo.setReceiveType(FrontUserBuyfreeOrderReceiveType.ENTITY);
					content.append("the product is waiting to be shipped!");
					if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())){//???????????????
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Invalid operation, please contact customer service!");
						return;
					}
					if(!FrontUserType.NORMAL.equals(fu.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Invalid operation, please contact customer service!");
						return;
					}
					//????????????????????????
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
			    	}
					
					if(Utlity.checkStringNull(frontUserAddress)) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Please fill in the correct address information!");
						return;
					}
					FrontUserAddress fua = this.frontUserAddressDao.get(frontUserAddress);
					if(fua == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Please fill in the correct address information!");
						return;
					}
					
					//?????????????????????
					List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
					StringBuilder sb = new StringBuilder();
					if(areaNameList != null && areaNameList.size() > 0) {
						for(String address : areaNameList) {
							sb.append(address);
						}
					}
//					sb.append(fua.getAddress());

					sb.append(fua.getAddress());//3
					
					if(!Utlity.checkStringNull(fua.getAsub())) {
						sb.append(",");
						sb.append(fua.getAsub());//4
					}
					
					sb.append(",");
					sb.append(fua.getCity());//5
					
					sb.append(",");
					sb.append(fua.getState());//6
					if(!Utlity.checkStringNull(fua.getInternationalInfo())) {//1
						InternationalInfo ii = this.internationalInfoDao.get(fua.getInternationalInfo());
						if(ii != null) {
							sb.append(",");
							sb.append(ii.getNameEn());
						}
					}
					sb.append(fua.getZipcode());//7
					
					ProvideInfoVO pivo = new ProvideInfoVO();
					pivo.setAddress(sb.toString());
					pivo.setMobile(fua.getPhone());
					pivo.setName(fua.getReceiver());
					fubo.setProvideInfo(JSONUtils.obj2json(pivo));
					
//					//????????????????????????
//					Goods goods = this.goodsDao.get(fubo.getGoodsId());
//					if(goods != null) {
//						fuacc.setTotalDelivery(fuacc.getTotalDelivery().add(goods.getPrice()));//?????????????????????????????????
//						fuacc.setDeliveryTimes(fuacc.getDeliveryTimes() + 1);//??????
//					}	
//					updateMap.put("frontUserAccount", fuacc);
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("The redemption information is wrong, please try again later!");
					return;
				}
				this.frontUserBuyfreeOrderDao.userReceive(fubo, updateMap);
			} else if (ActivityInfoName.CHECKIN.equals(activityInfo)) {
				Map<String, Object> updateMap = new HashMap<String, Object>();
				FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(uuid);
				if(fuch == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Participation record does not exist, if in doubt, please contact customer service!");
					return;
				}
				if(!fuch.getFrontUser().equals(frontUser)) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Participation record does not exist, if in doubt, please contact customer service!");
					return;
				}
				if(!ActivityInfoCheckinPrizeType.ENTITY.equals(fuch.getPrizeType())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Non-kind prizes, not exchange, if in doubt, please contact customer service!");
					return;
				}
				if(FrontUserCheckinHistoryStatus.FINISHED.equals(fuch.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("The prizes are redeemed, if in doubt, please contact customer service!");
					return;
				}
				
				ActivityInfoCheckinPrize aicp = this.activityInfoCheckinPrizeDao.get(fuch.getActivityInfoCheckinPrize());
				if(aicp == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Prizes information is wrong!");
					return;
				}
				content.append(aicp.getPrizeTitle()+" Commodity redemption application is successful,");
				fum.setSourceId(fuch.getUuid());
				fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_CHECKIN);
				if(FrontUserCheckinHistoryReceiveType.GOLD.equals(type)) {

					content.append("the coins have arrived!");
					fuch.setStatus(FrontUserCheckinHistoryStatus.FINISHED);
					fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.GOLD);
					
					//????????????????????????
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
			    	}
					//??????????????????
					Goods goods = this.goodsDao.get(aicp.getPrize());
					BigDecimal dAmount = BigDecimal.ZERO;
					if(goods != null) {
						dAmount = goods.getPrice().multiply(rate);
					}
						
					
					//??????????????????
					FrontUserHistory fuh = new FrontUserHistory();
					fuh.setUuid(UUID.randomUUID().toString());
					fuh.setFrontUser(frontUser);
					fuh.setOrderNum(Utlity.getOrderNum());//
					fuh.setType(FrontUserHistoryType.USER_ADD);
					fuh.setOrderType(Constants.ORDER_TYPE_USER_CHECKIN);
					fuh.setOrderNum(Utlity.getOrderNum());//????????????????????????
					fuh.setOrderId(fuch.getUuid());
					fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_CHECKIN));
					fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuh.setdAmount(dAmount);
					fuh.setBalanceBefore(fuacc.getBalance().add(fuacc.getBalanceLock()));
					fuh.setBalanceAfter(fuacc.getBalance().add(fuacc.getBalanceLock()).add(dAmount));
					
					fuh.setRemark("User check-in activity, "+aicp.getPrizeTitle()+", current day "+aicp.getDayNum());
					//??????????????????
					fuacc.setBalance(fuacc.getBalance().add(dAmount));//??????
					
//					//????????????????????????
//					fuacc.setTotalExchange(fuacc.getTotalRecharge().add(dAmount));//???????????????
//					fuacc.setExchangeTimes(fuacc.getExchangeTimes() + 1);
					
					updateMap.put("frontUserAccount", fuacc);
					updateMap.put("frontUserHistory", fuh);
					
				} else if(FrontUserCheckinHistoryReceiveType.ENTITY.equals(type)) {
					fuch.setStatus(FrontUserCheckinHistoryStatus.RECEIVE);
					fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.ENTITY);
					content.append("the product is waiting to be shipped!");
					
					if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())){//???????????????
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Invalid operation, please contact customer service!");
						return;
					}
					if(!FrontUserType.NORMAL.equals(fu.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Invalid operation, please contact customer service!");
						return;
					}
					//????????????????????????
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
			    	}
					
					if(Utlity.checkStringNull(frontUserAddress)) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Please fill in the correct address information!");
						return;
					}
					FrontUserAddress fua = this.frontUserAddressDao.get(frontUserAddress);
					if(fua == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Please fill in the correct address information!");
						return;
					}
					
					//?????????????????????
					List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
					StringBuilder sb = new StringBuilder();
					if(areaNameList != null && areaNameList.size() > 0) {
						for(String address : areaNameList) {
							sb.append(address);
						}
					}
//					sb.append(fua.getAddress());

					sb.append(fua.getAddress());//3
					
					if(!Utlity.checkStringNull(fua.getAsub())) {
						sb.append(",");
						sb.append(fua.getAsub());//4
					}
					
					sb.append(",");
					sb.append(fua.getCity());//5
					
					sb.append(",");
					sb.append(fua.getState());//6
					if(!Utlity.checkStringNull(fua.getInternationalInfo())) {//1
						InternationalInfo ii = this.internationalInfoDao.get(fua.getInternationalInfo());
						if(ii != null) {
							sb.append(",");
							sb.append(ii.getNameEn());
						}
					}
					sb.append(fua.getZipcode());//7
					
					ProvideInfoVO pivo = new ProvideInfoVO();
					pivo.setAddress(sb.toString());
					pivo.setMobile(fua.getPhone());
					pivo.setName(fua.getReceiver());
					fuch.setProvideInfo(JSONUtils.obj2json(pivo));
					
//					//????????????????????????
//					Goods goods = this.goodsDao.get(fubo.getGoodsId());
//					if(goods != null) {
//						fuacc.setTotalDelivery(fuacc.getTotalDelivery().add(goods.getPrice()));//?????????????????????????????????
//						fuacc.setDeliveryTimes(fuacc.getDeliveryTimes() + 1);//??????
//					}	
//					updateMap.put("frontUserAccount", fuacc);
					
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("The redemption information is wrong, please try again later!");
					return;
				}
				this.frontUserCheckinHistoryDao.userReceive(fuch, updateMap);
			} else if (ActivityInfoName.SCORELOTTERY.equals(activityInfo)) {
				Map<String, Object> updateMap = new HashMap<String, Object>();
				FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(uuid);
				if(fush == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Participation record does not exist, if in doubt, please contact customer service!");
					return;
				}
				if(!fush.getFrontUser().equals(frontUser)) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Participation record does not exist, if in doubt, please contact customer service!");
					return;
				}
				if(!ActivityInfoScorelotteryPrizeType.ENTITY.equals(fush.getPrizeType())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Non-kind prizes, not exchange, if in doubt, please contact customer service!");
					return;
				}
				if(FrontUserScorelotteryHistoryStatus.FINISHED.equals(fush.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("The prizes are redeemed, if in doubt, please contact customer service!");
					return;
				}
				
				ActivityInfoScorelotteryPrize aisp = this.activityInfoScorelotteryPrizeDao.get(fush.getActivityInfoScorelotteryPrize());
				if(aisp == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Prizes information is wrong!");
					return;
				}
				content.append(aisp.getPrizeTitle()+" Commodity redemption application is successful,");
				
				fum.setSourceId(fush.getUuid());
				fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_SCORELOTTERY);
				
				if(FrontUserScorelotteryHistoryReceiveType.GOLD.equals(type)) {
					content.append("the coins have arrived!");
					fush.setStatus(FrontUserScorelotteryHistoryStatus.FINISHED);
					fush.setReceiveType(FrontUserScorelotteryHistoryReceiveType.GOLD);
					
					//????????????????????????
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
			    	}
					//??????????????????
					Goods goods = this.goodsDao.get(aisp.getPrize());
					BigDecimal dAmount = BigDecimal.ZERO;
					if(goods != null) {
						dAmount = goods.getPrice().multiply(rate);
					}
						
					
					//??????????????????
					FrontUserHistory fuh = new FrontUserHistory();
					fuh.setUuid(UUID.randomUUID().toString());
					fuh.setFrontUser(frontUser);
					fuh.setOrderNum(Utlity.getOrderNum());//
					fuh.setType(FrontUserHistoryType.USER_ADD);
					fuh.setOrderType(Constants.ORDER_TYPE_USER_SCORELOTTERY);
					fuh.setOrderNum(Utlity.getOrderNum());//????????????????????????
					fuh.setOrderId(fush.getUuid());
					fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_SCORELOTTERY));
					fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuh.setdAmount(dAmount);
					fuh.setBalanceBefore(fuacc.getBalance().add(fuacc.getBalanceLock()));
					fuh.setBalanceAfter(fuacc.getBalance().add(fuacc.getBalanceLock()).add(dAmount));
					
					fuh.setRemark("Points sweepstakes award,"+aisp.getPrizeTitle());//Points sweepstakes award,
					//??????????????????
					fuacc.setBalance(fuacc.getBalance().add(dAmount));//??????
					
//					//????????????????????????
//					fuacc.setTotalExchange(fuacc.getTotalRecharge().add(dAmount));//???????????????
//					fuacc.setExchangeTimes(fuacc.getExchangeTimes() + 1);
					
					updateMap.put("frontUserAccount", fuacc);
					updateMap.put("frontUserHistory", fuh);
					
				} else if(FrontUserScorelotteryHistoryReceiveType.ENTITY.equals(type)) {
					fush.setStatus(FrontUserScorelotteryHistoryStatus.RECEIVE);
					fush.setReceiveType(FrontUserScorelotteryHistoryReceiveType.ENTITY);
					content.append("the product is waiting to be shipped!");
					
					if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())){//???????????????
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Invalid operation, please contact customer service!");
						return;
					}
					if(!FrontUserType.NORMAL.equals(fu.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Invalid operation, please contact customer service!");
						return;
					}
					//????????????????????????
					FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
					if(fuacc == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
					}
					if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
			    		result.setStatus(ResultStatusType.FAILED);
						result.setMessage("User status error, please contact customer service!");
						return;
			    	}
					
					if(Utlity.checkStringNull(frontUserAddress)) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Please fill in the correct address information!");
						return;
					}
					FrontUserAddress fua = this.frontUserAddressDao.get(frontUserAddress);
					if(fua == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("Please fill in the correct address information!");
						return;
					}
					
					//?????????????????????
					List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
					StringBuilder sb = new StringBuilder();
					if(areaNameList != null && areaNameList.size() > 0) {
						for(String address : areaNameList) {
							sb.append(address);
						}
					}
//					sb.append(fua.getAddress());

					sb.append(fua.getAddress());//3
					
					if(!Utlity.checkStringNull(fua.getAsub())) {
						sb.append(",");
						sb.append(fua.getAsub());//4
					}
					
					sb.append(",");
					sb.append(fua.getCity());//5
					
					sb.append(",");
					sb.append(fua.getState());//6
					if(!Utlity.checkStringNull(fua.getInternationalInfo())) {//1
						InternationalInfo ii = this.internationalInfoDao.get(fua.getInternationalInfo());
						if(ii != null) {
							sb.append(",");
							sb.append(ii.getNameEn());
						}
					}
					sb.append(fua.getZipcode());//7
					
					ProvideInfoVO pivo = new ProvideInfoVO();
					pivo.setAddress(sb.toString());
					pivo.setMobile(fua.getPhone());
					pivo.setName(fua.getReceiver());
					fush.setProvideInfo(JSONUtils.obj2json(pivo));
					
//					//????????????????????????
//					Goods goods = this.goodsDao.get(fubo.getGoodsId());
//					if(goods != null) {
//						fuacc.setTotalDelivery(fuacc.getTotalDelivery().add(goods.getPrice()));//?????????????????????????????????
//						fuacc.setDeliveryTimes(fuacc.getDeliveryTimes() + 1);//??????
//					}	
//					updateMap.put("frontUserAccount", fuacc);
					
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("The redemption information is wrong, please try again later!");
					return;
				}
				this.frontUserScorelotteryHistoryDao.userReceive(fush, updateMap);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Abnormal activity data parameters, please try again later!");
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
			result.setMessage("Network anomalies, please try again later!");
			return;
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
		return;
	}
}
