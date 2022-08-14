package cn.product.treasuremall.service.front.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.AreaDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserAddressDao;
import cn.product.treasuremall.dao.FrontUserBankcardDao;
import cn.product.treasuremall.dao.FrontUserCommentDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserPaidNumberDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.FrontUserRanklistDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.FrontUserVoucherDao;
import cn.product.treasuremall.dao.FrontUserWithdrawOrderDao;
import cn.product.treasuremall.dao.GoodsCoverImageDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.dao.WinningInfoDao;
import cn.product.treasuremall.dao.WinningInfoReceiveDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.treasuremall.entity.FrontUserAddress;
import cn.product.treasuremall.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.product.treasuremall.entity.FrontUserComment;
import cn.product.treasuremall.entity.FrontUserComment.FrontUserCommentStatus;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserPaidNumber;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderStatus;
import cn.product.treasuremall.entity.FrontUserRanklist;
import cn.product.treasuremall.entity.FrontUserRechargeOrder;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.GoodsCoverImage;
import cn.product.treasuremall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.entity.WinningInfo.WinningInfoType;
import cn.product.treasuremall.entity.WinningInfoReceive;
import cn.product.treasuremall.entity.WinningInfoReceive.WinningInfoReceiveStatus;
import cn.product.treasuremall.entity.WinningInfoReceive.WinningInfoReceiveType;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.service.front.FrontUserAccountService;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.ProvideInfoVO;
import cn.product.treasuremall.vo.back.SharenumsVO;
import cn.product.treasuremall.vo.front.FrontUserAccountVO;
import cn.product.treasuremall.vo.front.FrontUserCommentVO;
import cn.product.treasuremall.vo.front.FrontUserHistoryVO;
import cn.product.treasuremall.vo.front.FrontUserLuckyNumVO;
import cn.product.treasuremall.vo.front.FrontUserPaymentOrderVO;
import cn.product.treasuremall.vo.front.FrontUserRanklistVO;
import cn.product.treasuremall.vo.front.LuckyNumVO;
import cn.product.treasuremall.vo.front.StatusCountVO;
import cn.product.treasuremall.vo.front.WinningInfoVO;

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
	private AreaDao areaDao;
	
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
		
		FrontUserAccountVO vo = new FrontUserAccountVO(fu,fua);
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", uuid);
		searchMap.put("status", FrontUserBankcardStatus.NORMAL);
		Integer bankcardCount = frontUserBankcardDao.getCountByParams(searchMap);
		searchMap.remove("status");
		Integer voucherCount = frontUserVoucherDao.getCountByParams(searchMap);
		
		vo.setBankcardCount(bankcardCount);
		vo.setVoucherCount(voucherCount);
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
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void historyList(InputParams params, DataResult<Object> result) {
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
		
		List<FrontUserHistory> list = this.frontUserHistoryDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserHistoryDao.getCountByParams(paramsMap);
		
		result.setData(list);
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
			result.setMessage("交易记录不存在！");
			return;
		}
		if(!frontUser.equals(fuh.getFrontUser())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("非本用户记录,无法查询！");
			return;
		}
		FrontUserHistoryVO fuhvo = new FrontUserHistoryVO(fuh);
		//参与购买和兑换商品
		//需要回传goodsIssue
		if(Constants.ORDER_TYPE_USER_PAYMENT.equals(fuh.getOrderType()) || Constants.ORDER_TYPE_USER_EXCHANGE.equals(fuh.getOrderType())) {
			FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(fuh.getOrderId());
			if(fupo != null) {
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(fupo.getGoodsIssue());
				if(lgi != null) {
					fuhvo.setGoodsIssue(lgi.getUuid());
					fuhvo.setGoodsTitle(lgi.getTitle());
				}
			}
		} else if (Constants.ORDER_TYPE_USER_RECHARGE.equals(fuh.getOrderType())) {
			FrontUserRechargeOrder furo = this.frontUserRechargeOrderDao.get(fuh.getOrderId());
			if(furo != null) {
				fuhvo.setAmount(furo.getAmount());
			}
		} else if (Constants.ORDER_TYPE_USER_WITHDRAW.equals(fuh.getOrderType())) {
			FrontUserWithdrawOrder fuwo = this.frontUserWithdrawOrderDao.get(fuh.getOrderId());
			if(fuwo != null) {
				fuhvo.setAmount(fuwo.getActualAmount());
				fuhvo.setPoundage(fuwo.getPoundage());
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
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", "createtime desc");
		
		List<FrontUserVoucher> list = frontUserVoucherDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserVoucherDao.getCountByParams(paramsMap);
		
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
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", uuid);
		searchMap.put("goodsIssue", goodsIssue);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		List<FrontUserPaymentOrder> list = this.frontUserPaymentOrderDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserPaymentOrderDao.getCountByParams(searchMap);
		
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
					if(LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {
						fupovo.setTimeLine(lgi.getLotterytime().getTime()+30000L-System.currentTimeMillis());
					}
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					fupovo.setPrice(good.getPrice());
					fupovo.setCode(good.getCode());
					fupovo.setCover("");
					//商品封面图
					searchMap.clear();
					searchMap.put("belongs", lgi.getGoodsId());
					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
					if(gcis != null && gcis.size() > 0) {
						Resource re = this.resourceDao.get(gcis.get(0).getImage());
						if(re != null) {
							fupovo.setCover(pathUrl + re.getUrl());
						}
					}
				}
				//判断是否兑奖和是否晒单
				searchMap.clear();
				searchMap.put("orderId", fupo.getUuid());
				List<WinningInfo> listwin = this.winningInfoDao.getListByParams(searchMap);
				if(listwin != null && listwin.size() > 0) {
					WinningInfo wi = listwin.get(0);
					fupovo.setWinningInfo(wi.getUuid());
					if(!WinningInfoType.NORMAL.equals(wi.getType())) {
						fupovo.setIsRecevice(true);
					}
				}
				Integer countcom = this.frontUserCommentDao.getCountByParams(searchMap);
				if(countcom != null && countcom > 0) {
					fupovo.setIsComment(true);
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
		result.setMessage("查询成功！");
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
			result.setMessage("查询信息不存在！");
			return;	
		}
		
		if(!fupo.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("查询信息不存在！");
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
			if(LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {
				fupovo.setTimeLine(lgi.getLotterytime().getTime()+30000L-System.currentTimeMillis());
			}
			
			Goods good = this.goodsDao.get(lgi.getGoodsId());
			fupovo.setPrice(good.getPrice());
			fupovo.setCode(good.getCode());
			fupovo.setCover("");
			//商品封面图
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("belongs", lgi.getGoodsId());
			searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
			List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
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
		}
		//判断是否兑奖和是否晒单
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("orderId", fupo.getUuid());
		List<WinningInfo> listwin = this.winningInfoDao.getListByParams(searchMap);
		if(listwin != null && listwin.size() > 0) {
			WinningInfo wi = listwin.get(0);
			fupovo.setWinningInfo(wi.getUuid());
			if(!WinningInfoType.NORMAL.equals(wi.getType())) {
				fupovo.setIsRecevice(true);
			}
		}
		Integer countcom = this.frontUserCommentDao.getCountByParams(searchMap);
		if(countcom != null && countcom > 0) {
			fupovo.setIsComment(true);
		}
		result.setData(fupovo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("查询成功！");
		return;	
	}

	@Override
	public void winningInfoList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", uuid);
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
					wivo.setIsRecevice(true);
				} else {
					wivo = new WinningInfoVO(wi);
				}
				
				//封装商品信息
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
				if(lgi != null) {
					wivo.setTitle(lgi.getTitle());
					wivo.setShortTitle(lgi.getShortTitle());
					wivo.setShares(lgi.getShares());
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					wivo.setCode(good.getCode());
					wivo.setPrice(good.getPrice());
					wivo.setCover("");
					//商品封面图
					searchMap.clear();
					searchMap.put("belongs", lgi.getGoodsId());
					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
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
					LuckygameGoods lg = this.luckygameGoodsDao.get(lgi.getLuckygameGoods());
			    	if(lg != null) {//当前期数
			    		wivo.setCurrentIssueNum(lg.getCurrentIssueNum());
			    		searchMap.clear();
						searchMap.put("luckygameGoods", lg.getUuid());
						searchMap.put("sort", "issue_num desc");
						List<LuckygameGoodsIssue> listissue = this.luckygameGoodsIssueDao.getListByParams(searchMap);
						if(listissue != null && listissue.size() > 0) {
							wivo.setCurrentIssueUuid(listissue.get(0).getUuid());
						}
			    	}
				}
				
				//判断是否兑奖和是否晒单
				searchMap.clear();
				searchMap.put("orderId", wi.getOrderId());
				Integer countcom = this.frontUserCommentDao.getCountByParams(searchMap);
				if(countcom != null && countcom > 0) {
					wivo.setIsComment(true);
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
		result.setMessage("查询成功！");
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
			result.setMessage("中奖信息不存在！");
			return;
		}
		if(!wi.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("中奖信息不存在！");
			return;
		}
		LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
		if(lgi == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("奖品信息有误！");
			return;
		}
		if(this.winningInfoReceiveDao.get(wi.getUuid()) != null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("奖品兑奖完毕！");
			return;
		}
		
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
		
		wi.setType(type);
		//判断领奖类型
		//金币--直接入账？
		if(WinningInfoReceiveType.GOLD.equals(type)) {
			wir.setStatus(WinningInfoReceiveStatus.FINISHED);
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
			
			fuh.setRemark(Constants.ORDER_REASON_SYSTEM_ADD);
			//更新账户余额
			fuacc.setBalance(fuacc.getBalance().add(wi.getDealPrice()));//加币
			fuacc.setTotalExchange(fuacc.getTotalRecharge().add(wi.getDealPrice()));//总兑奖金额
			fuacc.setExchangeTimes(fuacc.getExchangeTimes() + 1);
			
			this.winningInfoReceiveDao.insertReceive(fuacc, fuh, wir, wi);
		} else if(WinningInfoReceiveType.ENTITY.equals(type)) {//实物--提交申请，等待派奖
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
			wir.setProvideInfo(JSONUtils.obj2json(pivo));
			
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
			Goods goods = this.goodsDao.get(wi.getGoodsId());
			if(goods != null) {
				fuacc.setTotalDelivery(fuacc.getTotalDelivery().add(goods.getPrice()));//总兑奖奖品价值（法币）
				fuacc.setDeliveryTimes(fuacc.getDeliveryTimes() + 1);//次数
			}
			this.winningInfoReceiveDao.insertReceive(fuacc, null ,wir, wi);
		}
		
		
		//机器人默认自动领取金币--未实现
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("处理成功！");
	}

	@Override
	public void receiveGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		WinningInfoReceive wir = this.winningInfoReceiveDao.get(uuid);
		if(wir == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("领奖信息不存在！");
			return;
		}
		if(!wir.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("领奖信息不存在！");
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
			wivo.setIsRobot(fu.getType().equals(FrontUserType.ROBOT));
			
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
			
			Goods good = this.goodsDao.get(lgi.getGoodsId());
			wivo.setCode(good.getCode());
			wivo.setPrice(good.getPrice());
			wivo.setSource(good.getSource());
			wivo.setSourceUrl(good.getSourceUrl());
			wivo.setCover("");
			//商品封面图
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("belongs", lgi.getGoodsId());
			searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
			List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
			if(gcis != null && gcis.size() > 0) {
				Resource re = this.resourceDao.get(gcis.get(0).getImage());
				if(re != null) {
					wivo.setCover(pathUrl + re.getUrl());
				}
			}
		}
		
		result.setData(wivo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("查询成功！");
	}

	@Override
	public void receiveList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", uuid);
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
					wivo.setIsRobot(fu.getType().equals(FrontUserType.ROBOT));
					
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
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					wivo.setCode(good.getCode());
					wivo.setPrice(good.getPrice());
					wivo.setSource(good.getSource());
					wivo.setSourceUrl(good.getSourceUrl());
					wivo.setCover("");
					//商品封面图
					searchMap.clear();
					searchMap.put("belongs", lgi.getGoodsId());
					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
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
		result.setMessage("查询成功！");
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
		searchMap.put("status", FrontUserCommentStatus.CHECKED);
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
			LuckygameGoodsIssue lgi = luckygameGoodsIssueDao.get(frontUserComment.getUuid());
			if(lgi != null) {
				frontUserCommentVO.setTitle(lgi.getTitle());
				frontUserCommentVO.setIssueNum(lgi.getIssueNum());
				frontUserCommentVO.setCoverImg("");
				//奖品封面图
				searchMap.clear();
				searchMap.put("belongs", lgi.getGoodsId());
				searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
				List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
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
			result.setMessage("该条数据不存在！");
			return;
		}
		
		if(!frontUserComment.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
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
		LuckygameGoodsIssue lgi = luckygameGoodsIssueDao.get(frontUserComment.getUuid());
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
			result.setMessage("中奖信息不存在！");
			return;
		}
		if(!wi.getFrontUser().equals(frontUser)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("中奖信息不存在！");
			return;
		}
		//订单信息
		FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(wi.getOrderId());
		if(fupo == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("订单信息不存在！");
			return;
		}
		
		//判断是否兑奖和是否晒单
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("orderId", fupo.getUuid());
		Integer countcom = this.frontUserCommentDao.getCountByParams(searchMap);
		if(countcom != null && countcom > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("不能重复晒单！");
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
		
		this.frontUserCommentDao.insert(fuc);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功");
		
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
		result.setMessage("查询成功！");
	}
}
