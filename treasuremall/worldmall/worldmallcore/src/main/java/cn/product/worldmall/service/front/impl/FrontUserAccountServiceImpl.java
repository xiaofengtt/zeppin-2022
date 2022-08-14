package cn.product.worldmall.service.front.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.ActivityInfoRecommendRankingDao;
import cn.product.worldmall.dao.AreaDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserAddressDao;
import cn.product.worldmall.dao.FrontUserBankcardDao;
import cn.product.worldmall.dao.FrontUserCommentDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.FrontUserPaidNumberDao;
import cn.product.worldmall.dao.FrontUserPaymentOrderDao;
import cn.product.worldmall.dao.FrontUserRanklistDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.FrontUserScoreHistoryDao;
import cn.product.worldmall.dao.FrontUserVoucherDao;
import cn.product.worldmall.dao.FrontUserWithdrawOrderDao;
import cn.product.worldmall.dao.GoodsCoverImageDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.GoodsIssueSharesnumDao;
import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.dao.VersionDao;
import cn.product.worldmall.dao.WinningInfoDao;
import cn.product.worldmall.dao.WinningInfoReceiveDao;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUser.FrontUserStatus;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.worldmall.entity.FrontUserAddress;
import cn.product.worldmall.entity.FrontUserBankcard;
import cn.product.worldmall.entity.FrontUserComment;
import cn.product.worldmall.entity.FrontUserComment.FrontUserCommentStatus;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.worldmall.entity.FrontUserPaidNumber;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderGroup;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderStatus;
import cn.product.worldmall.entity.FrontUserRanklist;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.FrontUserScoreHistory;
import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.worldmall.entity.FrontUserWithdrawOrder;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.GoodsCoverImage;
import cn.product.worldmall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.worldmall.entity.GoodsIssueSharesnum;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.Version;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfo.WinningInfoType;
import cn.product.worldmall.entity.WinningInfoReceive;
import cn.product.worldmall.entity.WinningInfoReceive.WinningInfoReceiveStatus;
import cn.product.worldmall.entity.WinningInfoReceive.WinningInfoReceiveType;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.service.front.FrontUserAccountService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.ProvideInfoVO;
import cn.product.worldmall.vo.back.SharenumsPKVO;
import cn.product.worldmall.vo.back.SharenumsVO;
import cn.product.worldmall.vo.front.FrontUserAccountVO;
import cn.product.worldmall.vo.front.FrontUserCommentVO;
import cn.product.worldmall.vo.front.FrontUserHistoryVO;
import cn.product.worldmall.vo.front.FrontUserLuckyNumVO;
import cn.product.worldmall.vo.front.FrontUserPaymentOrderVO;
import cn.product.worldmall.vo.front.FrontUserRanklistVO;
import cn.product.worldmall.vo.front.FrontUserScoreHistoryVO;
import cn.product.worldmall.vo.front.LuckyNumVO;
import cn.product.worldmall.vo.front.StatusCountVO;
import cn.product.worldmall.vo.front.WinningInfoVO;

@Service("frontUserAccountService")
public class FrontUserAccountServiceImpl implements FrontUserAccountService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserBankcardDao frontUserBankcardDao;
	
	@Autowired
	private FrontUserVoucherDao frontUserVoucherDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
    private GoodsDao goodsDao;
	
	@Autowired
    private GoodsCoverImageDao goodsCoverImageDao;
	
	@Autowired
    private WinningInfoReceiveDao winningInfoReceiveDao;
	
	@Autowired
    private ResourceDao resourceDao;
	
	@Autowired
    private WinningInfoDao winningInfoDao;
	
	@Autowired
    private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
    private FrontUserCommentDao frontUserCommentDao;
	
	@Autowired
    private FrontUserAddressDao frontUserAddressDao;
	
	@Autowired
    private FrontUserPaidNumberDao frontUserPaidNumberDao;
	
	@Autowired
	private FrontUserRanklistDao frontUserRanklistDao;
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
	private FrontUserWithdrawOrderDao frontUserWithdrawOrderDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private LuckygameGoodsDao luckygameGoodsDao;

	@Autowired
	private FrontUserMessageDao frontUserMessageDao;
	
	@Autowired
	private AreaDao areaDao;
	
	@Autowired
	private FrontUserScoreHistoryDao frontUserScoreHistoryDao;
	
	@Autowired
	private GoodsIssueSharesnumDao goodsIssueSharesnumDao;
	
	@Autowired
	private ActivityInfoRecommendRankingDao activityInfoRecommendRankingDao;
	
	@Autowired
	private InternationalInfoDao internationalInfoDao;
	
	@Autowired
	private VersionDao versionDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
    	SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sprate != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
		}
		
    	FrontUser fu = this.frontUserDao.get(uuid);
		FrontUserAccount fua = this.frontUserAccountDao.get(uuid);
		FrontUserRanklist fur = this.frontUserRanklistDao.get(uuid);
		ActivityInfoRecommendRanking airr = this.activityInfoRecommendRankingDao.getByFrontUser(uuid);
		
		FrontUserAccountVO vo = new FrontUserAccountVO(fu,fua);
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", uuid);
		searchMap.put("status", FrontUserVoucherStatus.NORMAL);
		Integer voucherCount = frontUserVoucherDao.getCountByParams(searchMap);
		vo.setVoucherCount(voucherCount);
		
		//paypal账号信息
		FrontUserBankcard fub = this.frontUserBankcardDao.getPaypal(uuid);
		if(fub != null) {
			vo.setPaypalAccount(fub.getAccountNumber());
		}
		
		vo.setRankNum(0);
		if(fur != null) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			
			vo.setRankNum(fur.getRankNum());
			FrontUserRanklistVO furvo = new FrontUserRanklistVO(fur);
			furvo.setTotalWinning(fur.getTotalWinning().multiply(rate));
			if(fu != null) {
				furvo.setShowId(fu.getShowId());
				furvo.setNickname(fu.getNickname());
				if(!Utlity.checkStringNull(fu.getImage())) {
					Resource re = this.resourceDao.get(fu.getImage());
					if(re != null) {
						furvo.setImageUrl(pathUrl + re.getUrl());
					}
				}
			}
			vo.setRankInfo(furvo);
		}
		if(airr != null) {
			Map<String, Object> recommendRankInfo = new HashMap<String, Object>();
			recommendRankInfo.put("award", airr.getAward());
			recommendRankInfo.put("recommend", airr.getRecommend());
			vo.setRecommendRankInfo(recommendRankInfo);
		}
			
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void month(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		
		List<Map<String, Object>> list = this.frontUserHistoryDao.getMonthListByParams(searchMap);
		List<String> monthList = new ArrayList<String>();
		if(list != null && list.size() > 0) {
			for(Map<String, Object> month : list) {
				String monthStr = String.valueOf(month.get("months"));
				monthList.add(monthStr);
			}
		}
		result.setData(monthList);
		result.setStatus(ResultStatusType.SUCCESS);
		return;
	}
	
	@Override
	public void historyList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String starttime = paramsMap.get("starttime") == null ? "" : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "1" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "10" : paramsMap.get("pageSize").toString());
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("type", type);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", "createtime desc");
		
		List<FrontUserHistory> list = this.frontUserHistoryDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserHistoryDao.getCountByParams(paramsMap);
		
		List<FrontUserHistoryVO> listvo = new ArrayList<FrontUserHistoryVO>();
		if(list != null && list.size() > 0) {
			for(FrontUserHistory fuh : list) {
				FrontUserHistoryVO fuhvo = new FrontUserHistoryVO(fuh);
				listvo.add(fuhvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void historyGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		FrontUserHistory fuh = this.frontUserHistoryDao.get(uuid);
		if(fuh == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Transaction record does not exist!");
			return;
		}
		if(!frontUser.equals(fuh.getFrontUser())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Illegal record, can not query!");
			return;
		}
		FrontUserHistoryVO fuhvo = new FrontUserHistoryVO(fuh);
		
		//获取货币和符号
		SystemParam spcurrency = this.systemParamDao.get(SystemParamKey.INTERNATIONAL_CURRENCIES_SYMBOL);
		String symbol = "";
		
		//参与购买和兑换商品
		//需要回传goodsIssue
		if(Constants.ORDER_TYPE_USER_PAYMENT.equals(fuh.getOrderType()) || Constants.ORDER_TYPE_USER_EXCHANGE.equals(fuh.getOrderType())) {
			FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(fuh.getOrderId());
			if(fupo != null) {
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(fupo.getGoodsIssue());
				if(lgi != null) {
					fuhvo.setGoodsIssue(lgi.getUuid());
					fuhvo.setGoodsTitle(lgi.getTitle());
					fuhvo.setGameType(lgi.getGameType());
				}
				
				//封装红包信息
				if(fupo.getIsVoucherUsed()) {
					fuhvo.setVoucher(fupo.getVoucher());
					if(!Utlity.checkStringNull(fupo.getVoucher())) {
						FrontUserVoucher fuv = this.frontUserVoucherDao.get(fupo.getVoucher());
						if(fuv != null) {
							fuhvo.setVoucherDAmount(fuv.getdAmount());
							fuhvo.setVoucherTitle(fuv.getTitle());
						}
					} else {
						fuhvo.setVoucherDAmount(fupo.getdAmount().subtract(fupo.getActualDAmount()));
					}
				} else {
					fuhvo.setVoucherDAmount(BigDecimal.ZERO);
				}
			}
		} else if (Constants.ORDER_TYPE_USER_RECHARGE.equals(fuh.getOrderType())) {
			FrontUserRechargeOrder furo = this.frontUserRechargeOrderDao.get(fuh.getOrderId());
			if(furo != null) {
				if(spcurrency != null) {
					Map<String, Object> currencies = JSONUtils.json2map(spcurrency.getParamValue());
					if(currencies != null && !currencies.isEmpty()) {
						symbol = currencies.get(furo.getCurrency()) == null ? "" : currencies.get(furo.getCurrency()).toString();
					}
				}
				if(Utlity.checkStringNull(symbol)) {
					symbol = furo.getCurrency();
				}
				
				fuhvo.setAmount(furo.getAmount());
				fuhvo.setCurrency(furo.getCurrency());
				fuhvo.setCurrencyRate(furo.getCurrencyRate());
				fuhvo.setCurrencyAmount(symbol+furo.getCurrencyAmount());
			}
		} else if (Constants.ORDER_TYPE_USER_WITHDRAW.equals(fuh.getOrderType())) {
			FrontUserWithdrawOrder fuwo = this.frontUserWithdrawOrderDao.get(fuh.getOrderId());
			if(fuwo != null) {
				if(spcurrency != null) {
					Map<String, Object> currencies = JSONUtils.json2map(spcurrency.getParamValue());
					if(currencies != null && !currencies.isEmpty()) {
						symbol = currencies.get(fuwo.getCurrency()) == null ? "" : currencies.get(fuwo.getCurrency()).toString();
					}
				}
				if(Utlity.checkStringNull(symbol)) {
					symbol = fuwo.getCurrency();
				}
				
				fuhvo.setAmount(fuwo.getActualAmount());
				fuhvo.setPoundage(fuwo.getPoundage());
				
				fuhvo.setCurrency(fuwo.getCurrency());
				fuhvo.setCurrencyRate(fuwo.getCurrencyRate());
				fuhvo.setCurrencyAmount(symbol+fuwo.getCurrencyAmount());
				BigDecimal cp = fuwo.getPoundage().multiply(BigDecimal.valueOf(Double.valueOf(fuwo.getCurrencyRate()))).setScale(2, BigDecimal.ROUND_HALF_UP);
				fuhvo.setCurrencyAmountPoundage(symbol+cp.toString());
			}
			
			//查询消耗积分记录
			FrontUserScoreHistory fush = this.frontUserScoreHistoryDao.getByOrderId(fuwo.getUuid());
			if(fush != null) {
				fuhvo.setPointHistory(fush.getUuid());
				fuhvo.setPoints(fush.getsAmount().intValue());
			}
		}
		
		result.setData(fuhvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void voucherStatusList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		List<StatusCountVO> list = frontUserVoucherDao.getStatusList(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		return;
	}

	@Override
	public void voucherList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String goods = paramsMap.get("goods") == null ? "" : paramsMap.get("goods").toString();
		String goodsType = paramsMap.get("goodsType") == null ? "" : paramsMap.get("goodsType").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String payMin = paramsMap.get("payMin") == null ? "" : paramsMap.get("payMin").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		if(!"".equals(status)){
			if(FrontUserVoucherStatus.NORMAL.equals(status) || FrontUserVoucherStatus.UNSTART.equals(status)){
				searchMap.put("status", status);
			}else{
				searchMap.put("statusArr", "('"+FrontUserVoucherStatus.OVERTIME+"','"+FrontUserVoucherStatus.USED+"')");
			}
		}
		searchMap.put("goods", goods);
		searchMap.put("goodsType", goodsType);
		if(!Utlity.checkStringNull(payMin)) {
			searchMap.put("payMin", payMin);
		}
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", "createtime desc");
		
		List<FrontUserVoucher> list = frontUserVoucherDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserVoucherDao.getCountByParams(searchMap);
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void paymentList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String goodsIssue = paramsMap.get("goodsIssue") == null ? "" : paramsMap.get("goodsIssue").toString();
		String gameType = paramsMap.get("gameType") == null ? "" : paramsMap.get("gameType").toString();
		
		//20201106增加马甲商品查询区分
    	String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	
    	if(Utlity.checkStringNull(demoFlag)) {
    		if(!Utlity.checkStringNull(version) && !Utlity.checkStringNull(channel)) {
        		Boolean isDemo = isDemo(version, channel);
        		if(isDemo) {
        			demoFlag = "1";
        		} else {
        			demoFlag = "0";
        		}
    		} else {
    			demoFlag = "1";
    		}
    	}
    	
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", uuid);
		searchMap.put("goodsIssue", goodsIssue);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("gameType", gameType);
		searchMap.put("demoFlag", demoFlag);
		
		searchMap.put("sort", "createtime desc,is_lucky desc");
		List<FrontUserPaymentOrder> list = this.frontUserPaymentOrderDao.getGroupListByParams(searchMap);
		Integer totalCount = this.frontUserPaymentOrderDao.getGroupCountByParams(searchMap);
		
		List<FrontUserPaymentOrderVO> listvo = new ArrayList<FrontUserPaymentOrderVO>();
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			
			for(FrontUserPaymentOrder fupo : list) {
				fupo.setUuid(fupo.getUuid().split(",")[0]);
				FrontUserPaymentOrderVO fupovo = new FrontUserPaymentOrderVO(fupo);
				//封装商品信息
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(fupo.getGoodsIssue());
				if(lgi != null) {
					fupovo.setdPrice(lgi.getdPrice());
					fupovo.setTitle(lgi.getTitle());
					fupovo.setShortTitle(lgi.getShortTitle());
					fupovo.setShares(lgi.getShares());
					fupovo.setIssueNum(lgi.getIssueNum());
					fupovo.setGameStatus(lgi.getStatus());
					fupovo.setBetShares(lgi.getBetShares());
					fupovo.setFinishedtime(lgi.getFinishedtime());
					fupovo.setLotterytime(lgi.getLotterytime());
					if(LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {
						fupovo.setTimeLine(lgi.getLotterytime().getTime()+Utlity.TIMELINE-System.currentTimeMillis());
					}

					if(Constants.GAME_TYPE_GROUP2.equals(lgi.getGameType())) {//战队玩法
						//封装战队玩法参与次数
						Map<String, Integer> groupShares = fupovo.getGroupShares();
						GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
						if(gis != null) {
							SharenumsPKVO spkvo = JSONUtils.json2obj(gis.getSharenums(), SharenumsPKVO.class);
							groupShares.put(FrontUserPaymentOrderGroup.LUCKY, spkvo.getLucky().getCurrentNums().size());
							groupShares.put(FrontUserPaymentOrderGroup.RAIDER, spkvo.getRaider().getCurrentNums().size());
							fupovo.setGroupShares(groupShares);
						}
					}
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					fupovo.setPrice(good.getPrice());
					fupovo.setCode(good.getCode());
					fupovo.setCover("");
					//商品封面图
//					searchMap.clear();
//					searchMap.put("belongs", lgi.getGoodsId());
//					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
					if(gcis != null && gcis.size() > 0) {
						Resource re = this.resourceDao.get(gcis.get(0).getImage());
						if(re != null) {
							fupovo.setCover(pathUrl + re.getUrl());
						}
					}
					
					//封装当前进行中的期数
					LuckygameGoodsIssue lgiCurrent = this.luckygameGoodsIssueDao.getCurrentIssue(lgi.getLuckygameGoods());
					if(lgi != null) {
						fupovo.setCurrentIssueUuid(lgiCurrent.getUuid());
						fupovo.setCurrentIssueNum(lgiCurrent.getIssueNum());
					}
				}
				if(Constants.GAME_TYPE_TRADITION.equals(lgi.getGameType())) {
					//判断是否兑奖和是否晒单
//					searchMap.clear();
//					searchMap.put("goodsIssue", fupo.getGoodsIssue());
//					List<WinningInfo> listwin = this.winningInfoDao.getListByParams(searchMap);
//					if(listwin != null && listwin.size() > 0) {
//						WinningInfo wi = listwin.get(0);
//						if(wi.getOrderId().equals(fupo.getUuid())) {//是本人中奖
//							fupovo.setReceviceType(wi.getType());
//							fupovo.setWinningInfo(wi.getUuid());
//							fupovo.setDealPrice(wi.getDealPrice());
//							if(!WinningInfoType.NORMAL.equals(wi.getType())) {
//								fupovo.setIsRecevice(true);
//							}
//						} 
////						else {//非本人中奖-封装用户昵称和中奖号
////							fupovo.setLuckyNumber(wi.getLuckynum());
////							FrontUser fu = this.frontUserDao.get(wi.getFrontUser());
////							if(fu != null) {
////								fupovo.setNickname(fu.getNickname());
////							}
////							fupovo.setBuyCount(wi.getBuyCount());
////						}
//					}
					WinningInfo wi = this.winningInfoDao.getByGoodsIssue(fupo.getGoodsIssue());
					if(wi != null) {
						if(wi.getOrderId().equals(fupo.getUuid())) {//是本人中奖
							fupovo.setWinningInfo(wi.getUuid());
							fupovo.setReceviceType(wi.getType());
							if(!WinningInfoType.NORMAL.equals(wi.getType())) {
								fupovo.setIsRecevice(true);
							}
						} 
					}
//					searchMap.put("orderId", fupo.getUuid());
//					Integer countcom = this.frontUserCommentDao.getCountByParams(searchMap);
//					if(countcom != null && countcom > 0) {
//						fupovo.setIsComment(true);
//					}
					fupovo.setIsComment(this.frontUserCommentDao.isComment(fupo.getUuid()));
				} else if(Constants.GAME_TYPE_GROUP2.equals(lgi.getGameType())) {
					if(fupo.getIsLucky() != null && fupo.getIsLucky()) {//如果中奖
						fupovo.setReceviceType(WinningInfoType.GOLD);
						fupovo.setIsRecevice(true);
					}
				}
				listvo.add(fupovo);
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
	public void paymentGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(uuid);
		if(fupo == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The query information does not exist!");
			return;	
		}
		
		if(!fupo.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The query information does not exist!");
			return;	
		}
		
		FrontUserPaymentOrderVO fupovo = new FrontUserPaymentOrderVO(fupo);
		//封装商品信息
		LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(fupo.getGoodsIssue());
		if(lgi != null) {
			fupovo.setdPrice(lgi.getdPrice());
			fupovo.setTitle(lgi.getTitle());
			fupovo.setShortTitle(lgi.getShortTitle());
			fupovo.setShares(lgi.getShares());
			fupovo.setIssueNum(lgi.getIssueNum());
			fupovo.setGameStatus(lgi.getStatus());
			fupovo.setBetShares(lgi.getBetShares());
			fupovo.setFinishedtime(lgi.getFinishedtime());
			fupovo.setLotterytime(lgi.getLotterytime());
			if(LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {
				fupovo.setTimeLine(lgi.getLotterytime().getTime()+Utlity.TIMELINE-System.currentTimeMillis());
			}

			
			if(Constants.GAME_TYPE_GROUP2.equals(lgi.getGameType())) {//战队玩法
				//封装战队玩法参与次数
				Map<String, Integer> groupShares = fupovo.getGroupShares();
				GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
				if(gis != null) {
					SharenumsPKVO spkvo = JSONUtils.json2obj(gis.getSharenums(), SharenumsPKVO.class);
					groupShares.put(FrontUserPaymentOrderGroup.LUCKY, spkvo.getLucky().getCurrentNums().size());
					groupShares.put(FrontUserPaymentOrderGroup.RAIDER, spkvo.getRaider().getCurrentNums().size());
					fupovo.setGroupShares(groupShares);
				}
			}
			
			Goods good = this.goodsDao.get(lgi.getGoodsId());
			fupovo.setPrice(good.getPrice());
			fupovo.setCode(good.getCode());
			fupovo.setCover("");
			//商品封面图
//			Map<String, Object> searchMap = new HashMap<String, Object>();
//			searchMap.put("belongs", lgi.getGoodsId());
//			searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
//			List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
			List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
			if(gcis != null && gcis.size() > 0) {
				SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
				String pathUrl = "";
				if(sp != null) {
					pathUrl = sp.getParamValue();
				} else {
					pathUrl = Utlity.IMAGE_PATH_URL;
				}
				Resource re = this.resourceDao.get(gcis.get(0).getImage());
				if(re != null) {
					fupovo.setCover(pathUrl + re.getUrl());
				}
			}
			
			//封装当前进行中的期数
			LuckygameGoodsIssue lgiCurrent = this.luckygameGoodsIssueDao.getCurrentIssue(lgi.getLuckygameGoods());
			if(lgi != null) {
				fupovo.setCurrentIssueUuid(lgiCurrent.getUuid());
				fupovo.setCurrentIssueNum(lgiCurrent.getIssueNum());
			}
		}
		
		//判断是否兑奖和是否晒单
//		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		if(Constants.GAME_TYPE_TRADITION.equals(lgi.getGameType())) {
			//判断是否兑奖和是否晒单
//			searchMap.put("goodsIssue", fupo.getGoodsIssue());
//			List<WinningInfo> listwin = this.winningInfoDao.getListByParams(searchMap);
//			if(listwin != null && listwin.size() > 0) {
//				WinningInfo wi = listwin.get(0);
//				if(wi.getOrderId().equals(fupo.getUuid())) {//是本人中奖
//					fupovo.setWinningInfo(wi.getUuid());
//					fupovo.setReceviceType(wi.getType());
//					if(!WinningInfoType.NORMAL.equals(wi.getType())) {
//						fupovo.setIsRecevice(true);
//					}
//				} 
//			}
			WinningInfo wi = this.winningInfoDao.getByGoodsIssue(fupo.getGoodsIssue());
			if(wi != null) {
				if(wi.getOrderId().equals(fupo.getUuid())) {//是本人中奖
					fupovo.setWinningInfo(wi.getUuid());
					fupovo.setReceviceType(wi.getType());
					if(!WinningInfoType.NORMAL.equals(wi.getType())) {
						fupovo.setIsRecevice(true);
					}
				} 
			}
//			searchMap.clear();
//			searchMap.put("orderId", fupo.getUuid());
//			Integer countcom = this.frontUserCommentDao.getCountByParams(searchMap);
//			if(countcom != null && countcom > 0) {
//				fupovo.setIsComment(true);
//			}
			fupovo.setIsComment(this.frontUserCommentDao.isComment(fupo.getUuid()));
		} else if(Constants.GAME_TYPE_GROUP2.equals(lgi.getGameType())) {
			if(fupo.getIsLucky() != null && fupo.getIsLucky()) {//如果中奖
				fupovo.setReceviceType(WinningInfoType.GOLD);
				fupovo.setIsRecevice(true);
			}
		}
		
		result.setData(fupovo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
		return;	
	}

	@Override
	public void winningInfoList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String gameType = paramsMap.get("gameType") == null ? "" : paramsMap.get("gameType").toString();

		//20201106增加马甲商品查询区分
    	String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	
    	if(Utlity.checkStringNull(demoFlag)) {
    		if(!Utlity.checkStringNull(version) && !Utlity.checkStringNull(channel)) {
        		Boolean isDemo = isDemo(version, channel);
        		if(isDemo) {
        			demoFlag = "1";
        		} else {
        			demoFlag = "0";
        		}
    		} else {
    			demoFlag = "1";
    		}
    	}
    	
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", uuid);
		searchMap.put("gameType", gameType);
		searchMap.put("demoFlag", demoFlag);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		List<WinningInfo> list = this.winningInfoDao.getListByParams(searchMap);
		Integer totalCount = this.winningInfoDao.getCountByParams(searchMap);
		
		List<WinningInfoVO> listvo = new ArrayList<WinningInfoVO>();
		if(list != null && list.size() > 0) {
			for(WinningInfo wi : list) {
				WinningInfoReceive wir = this.winningInfoReceiveDao.get(wi.getUuid());
				WinningInfoVO wivo = null;
				if(wir != null) {
					wivo = new WinningInfoVO(wi, wir);
				} else {
					wivo = new WinningInfoVO(wi);
				}
				
				//封装商品信息
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
				if(lgi != null) {
					wivo.setTitle(lgi.getTitle());
					wivo.setShortTitle(lgi.getShortTitle());
					wivo.setShares(lgi.getShares());
					wivo.setIssueNum(lgi.getIssueNum());
					wivo.setLuckyGroup(lgi.getLuckyGroup());//获奖战队
					wivo.setFinishedtime(lgi.getFinishedtime());
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					wivo.setCode(good.getCode());
					wivo.setPrice(good.getPrice());
					wivo.setCover("");
					//商品封面图
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
					if(gcis != null && gcis.size() > 0) {
						SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
						String pathUrl = "";
						if(sp != null) {
							pathUrl = sp.getParamValue();
						} else {
							pathUrl = Utlity.IMAGE_PATH_URL;
						}
						Resource re = this.resourceDao.get(gcis.get(0).getImage());
						if(re != null) {
							wivo.setCover(pathUrl + re.getUrl());
						}
					}
					//封装领奖信息
					//封装当前进行中的期数
					LuckygameGoodsIssue lgiCurrent = this.luckygameGoodsIssueDao.getCurrentIssue(lgi.getLuckygameGoods());
					if(lgi != null) {
						wivo.setCurrentIssueUuid(lgiCurrent.getUuid());
						wivo.setCurrentIssueNum(lgiCurrent.getIssueNum());
					}
				}
				if(Constants.GAME_TYPE_TRADITION.equals(wivo.getGameType())) {
					//判断是否兑奖和是否晒单
//					searchMap.clear();
//					searchMap.put("orderId", wi.getOrderId());
//					Integer countcom = this.frontUserCommentDao.getCountByParams(searchMap);
//					if(countcom != null && countcom > 0) {
//						wivo.setIsComment(true);
//					}
					wivo.setIsComment(this.frontUserCommentDao.isComment(wi.getOrderId()));
				}
				listvo.add(wivo);
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
	public void winningInfoGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		WinningInfo wi = this.winningInfoDao.get(uuid);
		if(wi == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The query information does not exist!");
			return;	
		}
		
		if(!wi.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The query information does not exist!");
			return;	
		}
		
		WinningInfoReceive wir = this.winningInfoReceiveDao.get(wi.getUuid());
		WinningInfoVO wivo = null;
		if(wir != null) {
			wivo = new WinningInfoVO(wi, wir);
		} else {
			wivo = new WinningInfoVO(wi);
		}
		
		//封装商品信息
		LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
		if(lgi != null) {
			wivo.setTitle(lgi.getTitle());
			wivo.setShortTitle(lgi.getShortTitle());
			wivo.setShares(lgi.getShares());
			wivo.setIssueNum(lgi.getIssueNum());
			wivo.setLuckyGroup(lgi.getLuckyGroup());//获奖战队
			wivo.setFinishedtime(lgi.getFinishedtime());
			
			Goods good = this.goodsDao.get(lgi.getGoodsId());
			wivo.setCode(good.getCode());
			wivo.setPrice(good.getPrice());
			wivo.setCover("");
			//商品封面图
			List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
			if(gcis != null && gcis.size() > 0) {
				SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
				String pathUrl = "";
				if(sp != null) {
					pathUrl = sp.getParamValue();
				} else {
					pathUrl = Utlity.IMAGE_PATH_URL;
				}
				Resource re = this.resourceDao.get(gcis.get(0).getImage());
				if(re != null) {
					wivo.setCover(pathUrl + re.getUrl());
				}
			}
			//封装领奖信息
			//封装当前进行中的期数
			LuckygameGoodsIssue lgiCurrent = this.luckygameGoodsIssueDao.getCurrentIssue(lgi.getLuckygameGoods());
			if(lgi != null) {
				wivo.setCurrentIssueUuid(lgiCurrent.getUuid());
				wivo.setCurrentIssueNum(lgiCurrent.getIssueNum());
			}
		}
		if(Constants.GAME_TYPE_TRADITION.equals(wivo.getGameType())) {
			//判断是否兑奖和是否晒单
//			Map<String, Object> searchMap = new HashMap<String, Object>();
//			searchMap.put("orderId", wi.getOrderId());
//			Integer countcom = this.frontUserCommentDao.getCountByParams(searchMap);
//			if(countcom != null && countcom > 0) {
//				wivo.setIsComment(true);
//			}
			wivo.setIsComment(this.frontUserCommentDao.isComment(wi.getOrderId()));
		}
		result.setData(wivo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
		return;	
	}
	
	@Override
	public void receive(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
//		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		String winningInfo = paramsMap.get("winningInfo") == null ? "" : paramsMap.get("winningInfo").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
		String frontUserAddress = paramsMap.get("frontUserAddress") == null ? "" : paramsMap.get("frontUserAddress").toString();
		
		FrontUser fu = this.frontUserDao.get(frontUser);
		
		WinningInfo wi = this.winningInfoDao.get(winningInfo);
		if(wi == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The winning order does not exist!");
			return;
		}
		if(!wi.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The winning order does not exist!");
			return;
		}
		//查询当前账户对象
		FrontUserAccount fuacc = this.frontUserAccountDao.get(frontUser);
		if(fuacc == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account does not exist!");
			return;
		}
		if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User Account abnormal, the operation fails!");
			return;
    	}
		
		LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
		if(lgi == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Prizes information is wrong!");
			return;
		}
		if(this.winningInfoReceiveDao.get(wi.getUuid()) != null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The prizes are redeemed!");
			return;
		}
		try {

			WinningInfoReceive wir = new WinningInfoReceive();
			wir.setWinningInfo(wi.getUuid());
			wir.setCreatetime(new Timestamp(System.currentTimeMillis()));
			wir.setFrontUser(wi.getFrontUser());
			wir.setGoodsId(lgi.getGoodsId());
			wir.setIp(ip);
			wir.setOrderId(wi.getOrderId());
			wir.setShowId(fu.getShowId());
			wir.setStatus(WinningInfoReceiveStatus.RECEIVE);
			wir.setType(type);
			
			wi.setType(type);//同步更新
			

			//兑奖通知消息
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fu.getUuid());
			fum.setFrontUserShowId(fu.getShowId());
			fum.setTitle("Redemption Successful");
//			StringBuilder content = new StringBuilder("您在"+Utlity.timeSpanToChinaDateString(wir.getCreatetime())+"发起的"+lgi.getShortTitle()+"商品兑奖申请成功，");
			StringBuilder content = new StringBuilder("You initiated "
//					+ "on " + Utlity.timeSpanToUsString(wir.getCreatetime()) 
			+ " " + lgi.getShortTitle() + " Commodity redemption application is successful,");
			//判断领奖类型
			//金币--直接入账？
			if(WinningInfoReceiveType.GOLD.equals(type)) {
				content.append("the coins have arrived!");
				wir.setStatus(WinningInfoReceiveStatus.FINISHED);
				
				if(!FrontUserAccountStatus.NORMAL.equals(fuacc.getAccountStatus())) {
		    		result.setStatus(ResultStatusType.FAILED);
					result.setMessage("User Account abnormal, the operation fails");
					return;
		    	}
				//封装交易流水
				FrontUserHistory fuh = new FrontUserHistory();
				fuh.setUuid(UUID.randomUUID().toString());
				fuh.setFrontUser(frontUser);
				fuh.setOrderNum(Utlity.getOrderNum());//
				fuh.setType(FrontUserHistoryType.USER_ADD);
				fuh.setOrderType(Constants.ORDER_TYPE_USER_EXCHANGE);
				fuh.setOrderNum(Utlity.getOrderNum());//生成字符串订单号
				fuh.setOrderId(wi.getOrderId());
				fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_EXCHANGE));
				fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
				fuh.setdAmount(wi.getDealPrice());
				fuh.setBalanceBefore(fuacc.getBalance().add(fuacc.getBalanceLock()));
				fuh.setBalanceAfter(fuacc.getBalance().add(fuacc.getBalanceLock()).add(wi.getDealPrice()));
				
				fuh.setRemark(lgi.getShortTitle()+" period"+lgi.getIssueNum());
				//更新账户余额
				fuacc.setBalance(fuacc.getBalance().add(wi.getDealPrice()));//加币
				fuacc.setTotalExchange(fuacc.getTotalRecharge().add(wi.getDealPrice()));//总兑奖金额
				fuacc.setExchangeTimes(fuacc.getExchangeTimes() + 1);
				
				this.winningInfoReceiveDao.insertReceive(fuacc, fuh, wir, wi);
			} else if(WinningInfoReceiveType.ENTITY.equals(type)) {//实物--提交申请，等待派奖
				content.append("the product is waiting to be shipped!");
				if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())){//黑名单校验
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Invalid operation, please contact the administrator!");
					return;
				}
				if(!FrontUserType.NORMAL.equals(fu.getType())){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("Invalid operation, please contact the administrator!");
					return;
				}
				
//				//20200624增加积分消耗按系统设置参数生效
//				/*
//				 * 1，增加一个实物兑换积分扣除比率的系统参数 M
//					2，积分充足时，积分扣除额 J = 商品法币价格 G*M
//					3，积分不足时，积分扣除额 J = 用户积分余额，补充扣除金币数=G-J/M
//					4，积分为0时，补充扣除金币数 = G
//					5，金币余额不足时，提示不满足兑换实物条件，终止本次兑换
//				 */
//				SystemParam scorerate = this.systemParamDao.get(SystemParamKey.DELIVERY_SCOREAMOUNT);//提现积分消耗
//				BigDecimal scoreRate = BigDecimal.valueOf(2);
//				if(scorerate != null) {
//					scoreRate = BigDecimal.valueOf(Double.valueOf(scorerate.getParamValue()));
//				}
//				
//				//计算所需积分
//				BigDecimal scoreAmount = BigDecimal.ZERO;
//				BigDecimal scoreBalance = fuacc.getScoreBalance();
//				BigDecimal dAmount = wi.getDealPrice();//奖品价值金币数
//				Boolean flag = false;
//				scoreAmount = dAmount.multiply(scoreRate).setScale(0, BigDecimal.ROUND_UP);//计算提现额度1元1积分 保留整数位 小数向上取整;
//				if(scoreBalance.compareTo(BigDecimal.ZERO) > 0) {
//					if(scoreBalance.compareTo(scoreAmount) >= 0) {//积分充足扣除积分
//						flag = true;
//					}
//				}
//				
//				if(!flag) {
//					result.setStatus(ResultStatusType.FAILED);
//					result.setMessage("用户积分不足，不满足兑换实物条件，详情请联系客服!");
//					return;
//				}
				
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
				
				StringBuilder sb = new StringBuilder();
				if(!Utlity.checkStringNull(fua.getArea())) {
					//封装地址全信息
					List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
					if(areaNameList != null && areaNameList.size() > 0) {
						for(String address : areaNameList) {
							sb.append(address);
						}
					}
	    		}
				
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
				wir.setProvideInfo(JSONUtils.obj2json(pivo));
				
				Goods goods = this.goodsDao.get(wi.getGoodsId());
				if(goods != null) {
					fuacc.setTotalDelivery(fuacc.getTotalDelivery().add(goods.getPrice()));//总兑奖奖品价值（法币）
					fuacc.setDeliveryTimes(fuacc.getDeliveryTimes() + 1);//次数
				}
				this.winningInfoReceiveDao.insertReceive(fuacc, null ,wir, wi);
			}
			

			fum.setContent(content.toString());
			fum.setSourceId(wi.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_WIN);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECEIVE);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			this.frontUserMessageDao.sendMessage(fum);
			if(WinningInfoReceiveType.GOLD.equals(type)) {
				this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECEIVE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network anomalies, please try again later!");
			return;
		}
		
		//机器人默认自动领取金币--未实现
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
	}

	@Override
	public void receiveGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		WinningInfoReceive wir = this.winningInfoReceiveDao.get(uuid);
		if(wir == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The complete order does not exist!");
			return;
		}
		if(!wir.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The complete order does not exist!");
			return;
		}
		
		WinningInfo wi = this.winningInfoDao.get(wir.getWinningInfo());
		WinningInfoVO wivo = new WinningInfoVO(wi, wir);
		
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		//封装用户信息
		FrontUser fu = this.frontUserDao.get(wi.getFrontUser());
		if(fu != null) {
			wivo.setNickname(fu.getNickname());
			wivo.setShowId(fu.getShowId());
//			wivo.setIsRobot(fu.getType().equals(FrontUserType.ROBOT));
			
			Resource re = this.resourceDao.get(fu.getImage());
			if(re != null) {
				wivo.setImageUrl(pathUrl + re.getUrl());
			}
		}
		
		//封装订单号
		
		
		//封装商品信息
		LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
		if(lgi != null) {
			wivo.setTitle(lgi.getTitle());
			wivo.setShortTitle(lgi.getShortTitle());
			wivo.setShares(lgi.getShares());
			wivo.setIssueNum(lgi.getIssueNum());
			wivo.setStarttime(lgi.getCreatetime());
			wivo.setFinishedtime(lgi.getFinishedtime());
			wivo.setLuckyGroup(lgi.getLuckyGroup());
			
			Goods good = this.goodsDao.get(lgi.getGoodsId());
			wivo.setCode(good.getCode());
			wivo.setPrice(good.getPrice());
			wivo.setSource(good.getSource());
			wivo.setSourceUrl(good.getSourceUrl());
			wivo.setCover("");
			//商品封面图
//			Map<String, Object> searchMap = new HashMap<String, Object>();
//			searchMap.put("belongs", lgi.getGoodsId());
//			searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
//			List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
			List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
			if(gcis != null && gcis.size() > 0) {
				Resource re = this.resourceDao.get(gcis.get(0).getImage());
				if(re != null) {
					wivo.setCover(pathUrl + re.getUrl());
				}
			}
		}
		
		result.setData(wivo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
	}

	@Override
	public void receiveList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String gameType = paramsMap.get("gameType") == null ? "" : paramsMap.get("gameType").toString();

		//20201106增加马甲商品查询区分
    	String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	
    	if(Utlity.checkStringNull(demoFlag)) {
    		if(!Utlity.checkStringNull(version) && !Utlity.checkStringNull(channel)) {
        		Boolean isDemo = isDemo(version, channel);
        		if(isDemo) {
        			demoFlag = "1";
        		} else {
        			demoFlag = "0";
        		}
    		} else {
    			demoFlag = "1";
    		}
    	}
    	
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", uuid);
		searchMap.put("gameType", gameType);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<WinningInfoReceive> list = this.winningInfoReceiveDao.getListByParams(searchMap);
		Integer totalCount = this.winningInfoReceiveDao.getCountByParams(searchMap);
		
		List<WinningInfoVO> listvo = new ArrayList<WinningInfoVO>();
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(WinningInfoReceive wir : list) {
				WinningInfo wi = this.winningInfoDao.get(wir.getWinningInfo());
				WinningInfoVO wivo = new WinningInfoVO(wi, wir);
				
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(wi.getFrontUser());
				if(fu != null) {
					wivo.setNickname(fu.getNickname());
					wivo.setShowId(fu.getShowId());
//					wivo.setIsRobot(fu.getType().equals(FrontUserType.ROBOT));
					
					Resource re = this.resourceDao.get(fu.getImage());
					if(re != null) {
						wivo.setImageUrl(pathUrl + re.getUrl());
					}
				}
				
				if(WinningInfoReceiveType.GOLD.equals(wir.getType())) {//金币兑奖 给订单号
					//封装订单号
					searchMap.clear();
					searchMap.put("frontUser", wi.getFrontUser());
					searchMap.put("orderId", wi.getOrderId());
					List<FrontUserHistory> listhistory = this.frontUserHistoryDao.getListByParams(searchMap);
					if(listhistory != null && listhistory.size() > 0) {
						wivo.setFrontUserHistory(listhistory.get(0).getUuid());
					}
				}
				
				//封装商品信息
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
				if(lgi != null) {
					wivo.setTitle(lgi.getTitle());
					wivo.setShortTitle(lgi.getShortTitle());
					wivo.setShares(lgi.getShares());
					wivo.setIssueNum(lgi.getIssueNum());
					wivo.setStarttime(lgi.getCreatetime());
					wivo.setFinishedtime(lgi.getFinishedtime());
					wivo.setLuckyGroup(lgi.getLuckyGroup());
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					wivo.setCode(good.getCode());
					wivo.setPrice(good.getPrice());
					wivo.setSource(good.getSource());
					wivo.setSourceUrl(good.getSourceUrl());
					wivo.setCover("");
					//商品封面图
//					searchMap.clear();
//					searchMap.put("belongs", lgi.getGoodsId());
//					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
//					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
					if(gcis != null && gcis.size() > 0) {
						Resource re = this.resourceDao.get(gcis.get(0).getImage());
						if(re != null) {
							wivo.setCover(pathUrl + re.getUrl());
						}
					}
				}
				listvo.add(wivo);
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
	public void commentList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
//		searchMap.put("status", FrontUserCommentStatus.CHECKED);
		searchMap.put("sort", sorts);
		searchMap.put("frontUser", uuid);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的用户晒单信息的总数
		Integer totalResultCount = frontUserCommentDao.getCountByParams(searchMap);
		//查询符合条件的用户晒单信息列表
		List<FrontUserComment> list = frontUserCommentDao.getListByParams(searchMap);
		List<FrontUserCommentVO> listvo = new ArrayList<FrontUserCommentVO>();
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		for(FrontUserComment frontUserComment : list) {
			//界面返回封装对象
			FrontUserCommentVO frontUserCommentVO = new FrontUserCommentVO(frontUserComment);
			
			//资源信息
			//图片
			if(!Utlity.checkStringNull(frontUserComment.getImage())) {
				String[] images = frontUserComment.getImage().split(",");
				List<Map<String, Object>> listImage = new ArrayList<Map<String,Object>>();
				for(String image : images) {
					if(!Utlity.checkStringNull(image)) {
						Resource re = this.resourceDao.get(image);
						if(re != null) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("image", re.getUuid());
							map.put("url", pathUrl + re.getUrl());
							listImage.add(map);
						}
					}
				}
				frontUserCommentVO.setImageList(listImage);
			}
			//视频
			if(!Utlity.checkStringNull(frontUserComment.getVideo())) {
				String[] videos = frontUserComment.getVideo().split(",");
				List<Map<String, Object>> listVideo = new ArrayList<Map<String,Object>>();
				for(String video : videos) {
					if(!Utlity.checkStringNull(video)) {
						Resource re = this.resourceDao.get(video);
						if(re != null) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("video", re.getUuid());
							map.put("url", pathUrl + re.getUrl());
							listVideo.add(map);
						}
					}
				}
				frontUserCommentVO.setVideoList(listVideo);
			}
			//商品信息
			LuckygameGoodsIssue lgi = luckygameGoodsIssueDao.get(frontUserComment.getGoodsIssue());
			if(lgi != null) {
				frontUserCommentVO.setTitle(lgi.getTitle());
				frontUserCommentVO.setIssueNum(lgi.getIssueNum());
				frontUserCommentVO.setCoverImg("");
				//奖品封面图
//				searchMap.clear();
//				searchMap.put("belongs", lgi.getGoodsId());
//				searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
//				List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
				List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
				if(gcis != null && gcis.size() > 0) {
					Resource re = this.resourceDao.get(gcis.get(0).getImage());
					if(re != null) {
						frontUserCommentVO.setCoverImg(pathUrl + re.getUrl());
					}
				}
			}
			//用户信息
			FrontUser fu = this.frontUserDao.get(frontUserComment.getFrontUser());
			if(fu != null) {
				frontUserCommentVO.setNickName(fu.getNickname());
				if(!Utlity.checkStringNull(fu.getImage())) {
					Resource re = this.resourceDao.get(fu.getImage());
					if(re != null) {
						frontUserCommentVO.setImageUrl(pathUrl + re.getUrl());
					}
				}
			}
			//中奖信息
			WinningInfo wi = this.winningInfoDao.get(frontUserComment.getWinningInfo());
			if(wi != null) {
				frontUserCommentVO.setBuyCount(wi.getBuyCount());
				frontUserCommentVO.setLuckynum(wi.getLuckynum());
				frontUserCommentVO.setWinningTime(wi.getWinningTime());
			}
			
			listvo.add(frontUserCommentVO);
		}
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
		
	}

	@Override
	public void commentGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		//获取用户晒单信息
		FrontUserComment frontUserComment = frontUserCommentDao.get(uuid);
		if (frontUserComment == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The data does not exist!");
			return;
		}
		
		if(!frontUserComment.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The data does not exist!");
			return;
		}
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		//界面返回封装对象
		FrontUserCommentVO frontUserCommentVO = new FrontUserCommentVO(frontUserComment);
		
		//资源信息
		//图片
		if(!Utlity.checkStringNull(frontUserComment.getImage())) {
			String[] images = frontUserComment.getImage().split(",");
			List<Map<String, Object>> listImage = new ArrayList<Map<String,Object>>();
			for(String image : images) {
				if(!Utlity.checkStringNull(image)) {
					Resource re = this.resourceDao.get(image);
					if(re != null) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("image", re.getUuid());
						map.put("url", pathUrl + re.getUrl());
						listImage.add(map);
					}
				}
			}
			frontUserCommentVO.setImageList(listImage);
		}
		//视频
		if(!Utlity.checkStringNull(frontUserComment.getVideo())) {
			String[] videos = frontUserComment.getVideo().split(",");
			List<Map<String, Object>> listVideo = new ArrayList<Map<String,Object>>();
			for(String video : videos) {
				if(!Utlity.checkStringNull(video)) {
					Resource re = this.resourceDao.get(video);
					if(re != null) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("video", re.getUuid());
						map.put("url", pathUrl + re.getUrl());
						listVideo.add(map);
					}
				}
			}
			frontUserCommentVO.setVideoList(listVideo);
		}
		//商品信息
		LuckygameGoodsIssue lgi = luckygameGoodsIssueDao.get(frontUserComment.getGoodsIssue());
		if(lgi != null) {
			frontUserCommentVO.setTitle(lgi.getTitle());
			frontUserCommentVO.setIssueNum(lgi.getIssueNum());
		}
		//用户信息
		FrontUser fu = this.frontUserDao.get(frontUserComment.getFrontUser());
		if(fu != null) {
			frontUserCommentVO.setNickName(fu.getNickname());
			if(!Utlity.checkStringNull(fu.getImage())) {
				Resource re = this.resourceDao.get(fu.getImage());
				if(re != null) {
					frontUserCommentVO.setImageUrl(pathUrl + re.getUrl());
				}
			}
		}
		//中奖信息
		WinningInfo wi = this.winningInfoDao.get(frontUserComment.getWinningInfo());
		if(wi != null) {
			frontUserCommentVO.setBuyCount(wi.getBuyCount());
			frontUserCommentVO.setLuckynum(wi.getLuckynum());
			frontUserCommentVO.setWinningTime(wi.getWinningTime());
		}
		result.setData(frontUserCommentVO);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void commentAdd(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		String imageList = paramsMap.get("imageList") == null ? "" : paramsMap.get("imageList").toString(); 
		String videoList = paramsMap.get("videoList") == null ? "" : paramsMap.get("videoList").toString();
		
		String winningInfo = paramsMap.get("winningInfo") == null ? "" : paramsMap.get("winningInfo").toString();
		String detail = paramsMap.get("detail") == null ? "" : paramsMap.get("detail").toString();
		
		//获取相关信息
		//用户信息
//		FrontUser fu = this.frontUserDao.get(frontUser);
		//中奖信息
		WinningInfo wi = this.winningInfoDao.get(winningInfo);
		if(wi == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The winning order does not exist!");
			return;
		}
		if(!wi.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The winning order does not exist!");
			return;
		}
		//订单信息
		FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(wi.getOrderId());
		if(fupo == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Order does not exist!");
			return;
		}
		
		//判断是否兑奖和是否晒单
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("orderId", fupo.getUuid());
		Integer countcom = this.frontUserCommentDao.getCountByParams(searchMap);
		if(countcom != null && countcom > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Cannot repeat share!");
			return;
		}
		
		//封装晒单信息
		FrontUserComment fuc = new FrontUserComment();
		fuc.setUuid(UUID.randomUUID().toString());
		fuc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fuc.setDetail(detail);
		fuc.setFrontUser(frontUser);
		fuc.setFrontUserShowId(wi.getShowId());
		fuc.setGoodsIssue(wi.getGoodsIssue());
		if(!Utlity.checkStringNull(imageList)) {
			fuc.setImage(imageList);
		}
		if(!Utlity.checkStringNull(videoList)) {
			fuc.setVideo(videoList);
		}
		fuc.setOrderId(wi.getOrderId());
		fuc.setOrderNum(fupo.getOrderNum());
		fuc.setStatus(FrontUserCommentStatus.NORMAL);//待审核
		fuc.setWinningInfo(winningInfo);
		fuc.setDemoFlag(wi.getDemoFlag());
		
		this.frontUserCommentDao.insert(fuc);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
		
	}

	@Override
	public void luckyNumList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String uuid = paramsMap.get("luckygameGoodsIssue") == null ? "" : paramsMap.get("luckygameGoodsIssue").toString();
    	
//    	FrontUser fu = this.frontUserDao.get(frontUser);
//    	LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(uuid);
    	
    	Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("goodsIssue", uuid);
		searchMap.put("status", FrontUserPaymentOrderStatus.SUCCESS);
		List<FrontUserPaymentOrder> list = this.frontUserPaymentOrderDao.getListByParams(searchMap);
		FrontUserLuckyNumVO fulnvo = new FrontUserLuckyNumVO();
		if(list != null && list.size() > 0) {
			Integer buyCount = 0;
			Boolean isLuck = false;
			Integer luckyNum = 0;
			List<LuckyNumVO> listlnvo = new ArrayList<LuckyNumVO>();
			for(FrontUserPaymentOrder fupo : list) {
				buyCount += fupo.getBuyCount();
				if(fupo.getIsLucky() != null && fupo.getIsLucky()) {
					isLuck = true;
					luckyNum = fupo.getLuckyNumber();
				}
				//获取幸运号
				FrontUserPaidNumber fupn = this.frontUserPaidNumberDao.get(fupo.getUuid());
				if(fupn != null) {
					SharenumsVO svo = JSONUtils.json2obj(fupn.getPaidSharenums(), SharenumsVO.class);
					List<Integer> nums = svo.getCurrentNums();
					if(nums != null) {
						for(Integer num : nums) {
							LuckyNumVO numvo = new LuckyNumVO();
							numvo.setLuckynum(num);
							if(isLuck && (luckyNum.intValue() == num.intValue())) {
								numvo.setIsLuck(true);
							} else {
								numvo.setIsLuck(false);
							}
							listlnvo.add(numvo);
						}
					}
				}
			}
			
			fulnvo.setBuyCount(buyCount);
			fulnvo.setListNums(listlnvo);
			fulnvo.setLuckynum(luckyNum);
		}
		
		result.setData(fulnvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
	}

	@Override
	public void scoreList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String starttime = paramsMap.get("starttime") == null ? "" : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("type", type);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", "createtime desc");
		
		List<FrontUserScoreHistory> list = this.frontUserScoreHistoryDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserScoreHistoryDao.getCountByParams(paramsMap);
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void scoreGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		FrontUserScoreHistory fush = this.frontUserScoreHistoryDao.get(uuid);
		if(fush == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Points record does not exist!");
			return;
		}
		if(!frontUser.equals(fush.getFrontUser())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Illegal record, can not query!");
			return;
		}
		FrontUserScoreHistoryVO fushvo = new FrontUserScoreHistoryVO(fush);
		//参与购买和兑换商品
		//需要回传goodsIssue
		if(Constants.ORDER_TYPE_USER_PAYMENT.equals(fush.getOrderType()) || Constants.ORDER_TYPE_USER_EXCHANGE.equals(fush.getOrderType())) {
			FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(fush.getOrderId());
			if(fupo != null) {
				fushvo.setTotalDAmount(fupo.getdAmount());
				fushvo.setActualDAmount(fupo.getActualDAmount());
				fushvo.setBuyCount(fupo.getBuyCount());
				
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(fupo.getGoodsIssue());
				if(lgi != null) {
					fushvo.setGoodsIssue(lgi.getUuid());
					fushvo.setGoodsTitle(lgi.getTitle());
				}
			}
		} else if (Constants.ORDER_TYPE_USER_RECHARGE.equals(fush.getOrderType())) {
			FrontUserRechargeOrder furo = this.frontUserRechargeOrderDao.get(fush.getOrderId());
			if(furo != null) {
				fushvo.setAmount(furo.getAmount());
			}
		} else if (Constants.ORDER_TYPE_USER_WITHDRAW.equals(fush.getOrderType())) {
			FrontUserWithdrawOrder fuwo = this.frontUserWithdrawOrderDao.get(fush.getOrderId());
			if(fuwo != null) {
				fushvo.setAmount(fuwo.getActualAmount());
				fushvo.setPoundage(fuwo.getPoundage());
			}
		}
		
		result.setData(fushvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	/**
	 * 判断是否为马甲
	 * @param version
	 * @param channel
	 * @return
	 */
	private Boolean isDemo(String version, String channel) {
		if(Utlity.checkStringNull(version) || Utlity.checkStringNull(channel)) {
			return true;
		}
		Version ver = this.versionDao.getByChannelVersion(channel, version);
		if(ver != null) {
			return !ver.getFlag();
		} else {
			return true;
		}
	}
}
