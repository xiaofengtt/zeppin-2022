package cn.product.worldmall.service.back.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.AdminDao;
import cn.product.worldmall.dao.AdminOffsetOrderDao;
import cn.product.worldmall.dao.BankDao;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.CapitalPlatformDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserBankcardDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserPaymentOrderDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.FrontUserScoreHistoryDao;
import cn.product.worldmall.dao.FrontUserVoucherDao;
import cn.product.worldmall.dao.FrontUserWithdrawOrderDao;
import cn.product.worldmall.dao.GoodsCoverImageDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.WinningInfoDao;
import cn.product.worldmall.dao.WinningInfoReceiveDao;
import cn.product.worldmall.entity.Admin;
import cn.product.worldmall.entity.Bank;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.entity.CapitalPlatform;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserBankcard;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.FrontUserScoreHistory;
import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.entity.FrontUserWithdrawOrder;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.GoodsCoverImage;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfoReceive;
import cn.product.worldmall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.worldmall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.service.back.UserHistoryService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserHistoryVO;
import cn.product.worldmall.vo.back.FrontUserPaymentOrderVO;
import cn.product.worldmall.vo.back.FrontUserRechargeOrderVO;
import cn.product.worldmall.vo.back.FrontUserScoreHistoryVO;
import cn.product.worldmall.vo.back.FrontUserVoucherVO;
import cn.product.worldmall.vo.back.FrontUserWithdrawOrderVO;
import cn.product.worldmall.vo.back.WinningInfoVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

/**
 */
@Service("userHistoryService")
public class UserHistoryServiceImpl implements UserHistoryService{
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private FrontUserDao frontUserDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private FrontUserBankcardDao frontUserBankcardDao;
	
	@Autowired
    private CapitalAccountDao capitalAccountDao;
	
	@Autowired
    private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
    private BankDao bankDao;
	
	@Autowired
    private AdminDao adminDao;
	
	@Autowired
    private ResourceDao resourceDao;
	
	@Autowired
    private FrontUserVoucherDao frontUserVoucherDao;
	
	@Autowired
    private FrontUserWithdrawOrderDao frontUserWithdrawOrderDao;
	
	@Autowired
    private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
    private WinningInfoDao winningInfoDao;
	
	@Autowired
    private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
    private AdminOffsetOrderDao adminOffsetOrderDao;
	
	@Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
    private GoodsDao goodsDao;
	
	@Autowired
    private GoodsCoverImageDao goodsCoverImageDao;
	
	@Autowired
    private WinningInfoReceiveDao winningInfoReceiveDao;
	
	@Autowired
	private FrontUserScoreHistoryDao frontUserScoreHistoryDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUserHistory fuh = frontUserHistoryDao.get(uuid);
		FrontUserHistoryVO fuhvo = new FrontUserHistoryVO(fuh);
		
//		FrontUser fu = this.frontUserDao.get(fuh.getFrontUser());
//		if(fu != null){
//			fuhvo.setFrontUserName(fu.getRealname());
//			fuhvo.setFrontUserMobile(fu.getMobile());
//		}
		
		result.setData(fuhvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	/**
	 * ??????????????????
	 */
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserHistoryDao.getCountByParams(searchMap);
		List<FrontUserHistory> fuhList = frontUserHistoryDao.getListByParams(searchMap);
		List<FrontUserHistoryVO> listvo = new ArrayList<FrontUserHistoryVO>();
		//??????????????????
		if(fuhList != null && fuhList.size() > 0) {
			for(FrontUserHistory fuh : fuhList) {
				FrontUserHistoryVO fuhvo = new FrontUserHistoryVO(fuh);
				if(!fuh.getOrderType().equals(Constants.ORDER_TYPE_USER_PAYMENT)) {
					fuhvo.setTitle(Constants.orderTypeTemplateInfoMap.get(fuh.getOrderType()));
				} else {
					fuhvo.setTitle("");
					//??????????????????
					FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(fuh.getOrderId());
					if(fupo != null) {
						LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(fupo.getGoodsIssue());
						fuhvo.setTitle(lgi.getShortTitle());
					}
				}
				listvo.add(fuhvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	/**
	 * ????????????????????????--????????????
	 */
	@Override
	public void partakelist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		String goodsIssue = paramsMap.get("goodsIssue") == null ? "" : paramsMap.get("goodsIssue").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("goodsIssue", goodsIssue);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		List<FrontUserPaymentOrder> list = this.frontUserPaymentOrderDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserPaymentOrderDao.getCountByParams(searchMap);
		
		List<FrontUserPaymentOrderVO> listvo = new ArrayList<FrontUserPaymentOrderVO>();
		if(list != null && list.size() > 0) {
			for(FrontUserPaymentOrder fupo : list) {
				FrontUserPaymentOrderVO fupovo = new FrontUserPaymentOrderVO(fupo);
				//??????????????????
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(fupo.getGoodsIssue());
				if(lgi != null) {
					fupovo.setTitle(lgi.getTitle());
					fupovo.setShortTitle(lgi.getShortTitle());
					fupovo.setShares(lgi.getShares());
					
					//??????????????????
					if(fupo.getIsVoucherUsed()) {
						if(!Utlity.checkStringNull(fupo.getVoucher())) {
							FrontUserVoucher fuv = this.frontUserVoucherDao.get(fupo.getVoucher());
							if(fuv != null) {
								fupovo.setVoucherDAmount(fuv.getdAmount());
								fupovo.setVoucherTitle(fuv.getTitle());
							}
							
						} else {
							fupovo.setVoucherDAmount(fupo.getdAmount().subtract(fupo.getActualDAmount()));
						}
					} else {
						fupovo.setVoucherDAmount(BigDecimal.ZERO);
					}
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					fupovo.setPrice(good.getPrice());
					fupovo.setCode(good.getCode());
					fupovo.setCover("");
					//???????????????
//					searchMap.clear();
//					searchMap.put("belongs", lgi.getGoodsId());
//					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
//					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
					if(gcis != null && gcis.size() > 0) {
						Resource re = this.resourceDao.get(gcis.get(0).getImage());
						if(re != null) {
							fupovo.setCover(re.getUrl());
						}
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
		result.setMessage("???????????????");
		return;	
	}
	
	/**
	 * ????????????????????????
	 * ??????goods????????????
	 * ?????????goodsID????????????childList
	 */
	@Override
	public void winlist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
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
				
				//??????????????????
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
				if(lgi != null) {
					wivo.setTitle(lgi.getTitle());
					wivo.setShortTitle(lgi.getShortTitle());
					wivo.setShares(lgi.getShares());
					wivo.setIssueNum(lgi.getIssueNum());
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					wivo.setCode(good.getCode());
					wivo.setCover("");
					wivo.setPrice(good.getPrice());
					//???????????????
//					searchMap.clear();
//					searchMap.put("belongs", lgi.getGoodsId());
//					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
//					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
					if(gcis != null && gcis.size() > 0) {
						Resource re = this.resourceDao.get(gcis.get(0).getImage());
						if(re != null) {
							wivo.setCover(re.getUrl());
						}
					}
					
					//??????????????????
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
		result.setMessage("???????????????");
		return;	
	}
	
	/**
	 * ????????????????????????
	 */
	@Override
	public void rechargelist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		List<FrontUserRechargeOrder> list = this.frontUserRechargeOrderDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserRechargeOrderDao.getCountByParams(searchMap);
		
		List<FrontUserRechargeOrderVO> listvo = new ArrayList<FrontUserRechargeOrderVO>();
		if(list != null) {
			for(FrontUserRechargeOrder furo : list) {
				FrontUserRechargeOrderVO furovo = new FrontUserRechargeOrderVO(furo);
				CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
				CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
				
				furovo.setAccountName(ca.getName());
				furovo.setAccountNum(ca.getAccountNum());
				furovo.setCapitalPlatform(cp.getName());
				
				listvo.add(furovo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("???????????????");
		return;		
	}
	
	/**
	 * ????????????????????????
	 */
	@Override
	public void withdrawlist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		List<FrontUserWithdrawOrder> list = this.frontUserWithdrawOrderDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserWithdrawOrderDao.getCountByParams(searchMap);
		
		List<FrontUserWithdrawOrderVO> voList =  new ArrayList<>();
		for(FrontUserWithdrawOrder fuwo : list){
			FrontUserWithdrawOrderVO vo = new FrontUserWithdrawOrderVO(fuwo);
	    	FrontUser fu = frontUserDao.get(fuwo.getFrontUser());
	    	if(fu != null){
	    		vo.setFrontUserNickname(fu.getNickname());
	    		
	    		FrontUserAccount fua = frontUserAccountDao.get(fu.getUuid());
	    		if(fua != null){
	    			vo.setFrontUserBalance(fua.getBalance());
	    			vo.setFrontUserBalanceLock(fua.getBalanceLock());
	    		}
	    	}
	    	
	    	if(fuwo.getOperator() != null){
	    		Admin operator = adminDao.get(fuwo.getOperator());
	    		if(operator != null){
	    			vo.setOperatorName(operator.getRealname());
	    		}
	    	}
	    	
	    	if(fuwo.getFrontUserBankcard() != null){
	    		FrontUserBankcard fub = frontUserBankcardDao.get(fuwo.getFrontUserBankcard());
	    		if(fub != null){
	    			vo.setFrontUserBranchBank(fub.getBranchBank());
	    			vo.setFrontUserAccountNumber(fub.getAccountNumber());
	    			vo.setFrontUserAccountHolder(fub.getAccountHolder());
	    			Bank bank = bankDao.get(fub.getBank());
	    			if(bank != null){
	    				vo.setFrontUserBankName(bank.getName());
	    			}
	    		}
	    	}
	    	voList.add(vo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("???????????????");
		return;
	}
	
	/**
	 * ??????????????????
	 *
	 */
	@Override
	public void voucherlist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		List<FrontUserVoucher> list = this.frontUserVoucherDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserVoucherDao.getCountByParams(searchMap);
		
		List<FrontUserVoucherVO> listvo = new ArrayList<FrontUserVoucherVO>();
		if(list != null && list.size() > 0) {
			for(FrontUserVoucher fuv : list) {
				FrontUserVoucherVO fuvvo = new FrontUserVoucherVO(fuv);
				
				//???????????????????????????????????????
				if(FrontUserVoucherStatus.USED.equals(fuv.getStatus())) {
					FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(fuv.getOrderId());
					LuckygameGoodsIssue lgi = luckygameGoodsIssueDao.get(fupo.getGoodsIssue());
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					fuvvo.setCode(good.getCode());
					fuvvo.setOrderNum(String.valueOf(fupo.getOrderNum()));
					fuvvo.setIssueNum(lgi.getIssueNum());
				}
				listvo.add(fuvvo);
			}
		}
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("???????????????");
		return;
	}

	/**
	 * ??????????????????
	 */
	@Override
	public void scorelist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserScoreHistoryDao.getCountByParams(searchMap);
		List<FrontUserScoreHistory> fushList = frontUserScoreHistoryDao.getListByParams(searchMap);
		List<FrontUserScoreHistoryVO> listvo = new ArrayList<FrontUserScoreHistoryVO>();
		//??????????????????
		if(fushList != null && fushList.size() > 0) {
			for(FrontUserScoreHistory fush : fushList) {
				FrontUserScoreHistoryVO fuhvo = new FrontUserScoreHistoryVO(fush);
				if(!fush.getOrderType().equals(Constants.ORDER_TYPE_USER_PAYMENT)) {
					fuhvo.setTitle(Constants.orderTypeTemplateInfoMap.get(fush.getOrderType()));
				} else {
					fuhvo.setTitle("");
					//??????????????????
					FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(fush.getOrderId());
					if(fupo != null) {
						LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(fupo.getGoodsIssue());
						fuhvo.setTitle(lgi.getShortTitle());
					}
				}
				listvo.add(fuhvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}
}
