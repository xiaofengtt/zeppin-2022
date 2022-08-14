package cn.product.worldmall.service.front.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserPaymentOrderDao;
import cn.product.worldmall.dao.GoodsCoverImageDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.GoodsIssueSharesnumDao;
import cn.product.worldmall.dao.GoodsTypeDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.dao.MobileCodeDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.dao.VersionDao;
import cn.product.worldmall.dao.WinningInfoDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderGroup;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderStatus;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.GoodsCoverImage;
import cn.product.worldmall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.worldmall.entity.GoodsIssueSharesnum;
import cn.product.worldmall.entity.GoodsType;
import cn.product.worldmall.entity.LuckygameGoods;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.Version;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.service.front.FrontGoodsIssueService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.SharenumsPKVO;
import cn.product.worldmall.vo.front.FrontUserPaymentOrderTopVO;
import cn.product.worldmall.vo.front.FrontUserPaymentOrderVO;
import cn.product.worldmall.vo.front.GroupWinningInfoVO;
import cn.product.worldmall.vo.front.LuckygameGoodsIssueVO;
import cn.product.worldmall.vo.front.WinningInfoVO;

@Service("frontGoodsIssueService")
public class FrontGoodsIssueServiceImpl implements FrontGoodsIssueService{
	
	@Autowired
	private FrontUserDao frontUserDao;

	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
	private LuckygameGoodsDao luckygameGoodsDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private GoodsCoverImageDao goodsCoverImageDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
	private WinningInfoDao winningInfoDao;
	
	@Autowired
	private GoodsTypeDao goodsTypeDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private GoodsIssueSharesnumDao goodsIssueSharesnumDao;
	
	@Autowired
	private VersionDao versionDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	
    	LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(uuid);
    	if(lgi == null) {
    		result.setMessage("Product information error, please try again later!");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	//获取金币汇率
		SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sprate != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
		}
    	LuckygameGoodsIssueVO lgivo = new LuckygameGoodsIssueVO(lgi);
    	lgivo.setPrice(lgivo.getdPrice().divide(rate));//奖品价值（元）
    	LuckygameGoods lg = this.luckygameGoodsDao.get(lgi.getLuckygameGoods());
    	if(lg != null) {//当前期数
    		lgivo.setLuckygameGoodsStatus(lg.getStatus());
    		lgivo.setCurrentIssueNum(lg.getCurrentIssueNum());
    		lgivo.setCurrentIssueStatus(lg.getStatus());
    		lgivo.setTabs(lg.getTabs());
//    		Map<String, Object> searchMap = new HashMap<String, Object>();
//			searchMap.put("luckygameGoods", lg.getUuid());
//			searchMap.put("sort", "issue_num desc");
//			List<LuckygameGoodsIssue> list = this.luckygameGoodsIssueDao.getListByParams(searchMap);
//			if(list != null && list.size() > 0) {
//				lgivo.setCurrentIssueUuid(list.get(0).getUuid());
//			}
    		LuckygameGoodsIssue lgiCurrent = this.luckygameGoodsIssueDao.getCurrentIssue(lgi.getLuckygameGoods());
			if(lgi != null) {
				lgivo.setCurrentIssueUuid(lgiCurrent.getUuid());
				lgivo.setCurrentIssueNum(lgiCurrent.getIssueNum());
				lgivo.setCurrentIssueStatus(lgiCurrent.getStatus());
			}
    	}
    	if(LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {//倒计时
			lgivo.setTimeLine(lgi.getLotterytime().getTime()+Utlity.TIMELINE-System.currentTimeMillis());
		}
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
    	
    	Goods good = this.goodsDao.get(lgi.getGoodsId());
    	lgivo.setCode(good.getCode());
    	lgivo.setDescription(good.getDescription());
    	lgivo.setCoverImg("");
    	lgivo.setPrice(good.getPrice());
		//奖品图片
//		Map<String, Object> searchMap = new HashMap<String, Object>();
//		searchMap.put("belongs", lgi.getGoodsId());
////		searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
		List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId());
		if(gcis != null && gcis.size() > 0) {
			List<String> imgList = new ArrayList<String>();
			List<String> imgDetail = new ArrayList<String>();
			List<String> imgShow = new ArrayList<String>();
			for(GoodsCoverImage gci : gcis) {
				Resource re = this.resourceDao.get(gci.getImage());
				if(re != null) {
					if(GoodsCoverImageType.TYPE_LIST.equals(gci.getType())) {
						imgList.add(pathUrl + re.getUrl());
					} else if(GoodsCoverImageType.TYPE_DETAIL.equals(gci.getType())) {
						imgDetail.add(pathUrl + re.getUrl());
					} else if(GoodsCoverImageType.TYPE_SHOW.equals(gci.getType())) {
						imgShow.add(pathUrl + re.getUrl());
					}
				}
			}
			lgivo.setImgDetail(imgDetail);
			lgivo.setImgList(imgList);
			lgivo.setImgShow(imgShow);
			lgivo.setCoverImg(imgList.get(0) == null ? "" : imgList.get(0));
		}
		
		if(Constants.GAME_TYPE_GROUP2.equals(lgi.getGameType())) {//战队玩法
			//封装战队玩法参与次数
			Map<String, Integer> groupShares = lgivo.getGroupShares();
			GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
			if(gis != null) {
				SharenumsPKVO spkvo = JSONUtils.json2obj(gis.getSharenums(), SharenumsPKVO.class);
				groupShares.put(FrontUserPaymentOrderGroup.LUCKY, spkvo.getLucky().getCurrentNums().size());
				groupShares.put(FrontUserPaymentOrderGroup.RAIDER, spkvo.getRaider().getCurrentNums().size());
				lgivo.setGroupShares(groupShares);
			}
		}
		
		
		//封装中奖人信息
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.clear();
		searchMap.put("goodsIssue", lgi.getUuid());
		searchMap.put("isLuck", 1);
		
		List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
		if(listorder != null && listorder.size() > 0) {
			FrontUserPaymentOrder fupo = listorder.get(0);
			lgivo.setShowIdStr(fupo.getFrontUserShowId()+"");
			lgivo.setFrontUser(fupo.getFrontUser());
			searchMap.clear();
			searchMap.put("goodsIssue", lgi.getUuid());
			searchMap.put("frontUser", fupo.getFrontUser());
			List<FrontUserPaymentOrder> listordersum = this.frontUserPaymentOrderDao.getGroupListByParams(searchMap);
			if(listordersum != null && listordersum.size() > 0) {
				lgivo.setBuyCount(listordersum.get(0).getBuyCount());
			}
			FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
			lgivo.setNickname(fu.getNickname());
			lgivo.setdAmount(fupo.getdAmount());
			lgivo.setActualDAmount(fupo.getActualDAmount());
			if(!Utlity.checkStringNull(fu.getImage())) {
				Resource re = this.resourceDao.get(fu.getImage());
				if(re != null) {
					lgivo.setImageUrl(pathUrl + re.getUrl());
				}
			}
		}
		
		//是否第一次购买
		if(!Utlity.checkStringNull(frontUser)) {//需要传frontUser
			searchMap.clear();
			searchMap.put("goodsIssue", lgi.getUuid());
			searchMap.put("frontUser", frontUser);
			Integer count = this.frontUserPaymentOrderDao.getCountByParams(searchMap);
			if(count != null && count > 0) {
				lgivo.setIsFirstBuy(false);
			}
		}
		result.setData(lgivo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String luckygameGoods = paramsMap.get("luckygameGoods") == null ? "" : paramsMap.get("luckygameGoods").toString();
		String goodsType = paramsMap.get("goodsType") == null ? "" : paramsMap.get("goodsType").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String names = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String[] statuses = paramsMap.get("statuses") == null ? null : (String[])paramsMap.get("statuses");
		String betLineMax = paramsMap.get("betLineMax") == null ? "" : paramsMap.get("betLineMax").toString();
		String betLineMin = paramsMap.get("betLineMin") == null ? "" : paramsMap.get("betLineMin").toString();
		
		String priceLineMax = paramsMap.get("priceLineMax") == null ? "" : paramsMap.get("priceLineMax").toString();
		String priceLineMin = paramsMap.get("priceLineMin") == null ? "" : paramsMap.get("priceLineMin").toString();
		
		String gameType = paramsMap.get("gameType") == null ? Constants.GAME_TYPE_TRADITION : paramsMap.get("gameType").toString();
    	
    	//20201106增加马甲商品查询区分
    	String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	
    	if(Utlity.checkStringNull(demoFlag)) {
    		Boolean isDemo = isDemo(version, channel);
    		if(isDemo) {
    			demoFlag = "1";
    		} else {
    			demoFlag = "0";
    		}
    	}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("luckygameGoods", luckygameGoods);
		searchMap.put("goodsType", goodsType);
		searchMap.put("gameType", gameType);
		searchMap.put("status", status);
		searchMap.put("names", names);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		if(!Utlity.checkStringNull(betLineMax)) {
			searchMap.put("maxPay", betLineMax);
		}
		if(!Utlity.checkStringNull(betLineMin)) {
			searchMap.put("minPay", betLineMin);
		}
		if(!Utlity.checkStringNull(priceLineMax)) {
			searchMap.put("goodsPriceMax", priceLineMax);
		}
		if(!Utlity.checkStringNull(priceLineMin)) {
			searchMap.put("goodsPriceMin", priceLineMin);
		}
		searchMap.put("sort", sort);
		if(statuses != null) {
			searchMap.put("statuses", statuses);
		}
		searchMap.put("demoFlag", demoFlag);
		
		Integer totalCount = luckygameGoodsIssueDao.getCountByParams(searchMap);
		List<LuckygameGoodsIssue> list = luckygameGoodsIssueDao.getListByParams(searchMap);
		
		List<LuckygameGoodsIssueVO> listvo = new ArrayList<LuckygameGoodsIssueVO>();
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
    		//获取金币汇率
			SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sprate != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
			}
			for(LuckygameGoodsIssue lgi : list) {
				LuckygameGoodsIssueVO lgivo = new LuckygameGoodsIssueVO(lgi);
				lgivo.setPrice(lgivo.getdPrice().divide(rate));//奖品价值（元）
				Goods good = this.goodsDao.get(lgi.getGoodsId());
				lgivo.setCode(good.getCode());
				lgivo.setDescription("");
				lgivo.setCoverImg("");
				lgivo.setPrice(good.getPrice());
				//获取标签信息
				LuckygameGoods lg = this.luckygameGoodsDao.get(lgi.getLuckygameGoods());
				if(lg != null) {
					lgivo.setTabs(lg.getTabs() == null ? "" : lg.getTabs());
				}
				//奖品封面图
//				searchMap.clear();
//				searchMap.put("belongs", lgi.getGoodsId());
//				searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
//				List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
				List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lgi.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
				if(gcis != null && gcis.size() > 0) {
					Resource re = this.resourceDao.get(gcis.get(0).getImage());
					if(re != null) {
						lgivo.setCoverImg(pathUrl + re.getUrl());
					}
				}
				
				if(Constants.GAME_TYPE_GROUP2.equals(lgi.getGameType())) {//战队玩法
					//封装战队玩法参与次数
					Map<String, Integer> groupShares = lgivo.getGroupShares();
					GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
					if(gis != null) {
						SharenumsPKVO spkvo = JSONUtils.json2obj(gis.getSharenums(), SharenumsPKVO.class);
						groupShares.put(FrontUserPaymentOrderGroup.LUCKY, spkvo.getLucky().getCurrentNums().size());
						groupShares.put(FrontUserPaymentOrderGroup.RAIDER, spkvo.getRaider().getCurrentNums().size());
						lgivo.setGroupShares(groupShares);
					}
				}
				
				if(LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {//倒计时
					lgivo.setTimeLine(lgi.getLotterytime().getTime()+Utlity.TIMELINE-System.currentTimeMillis());
				}
				//封装中奖人信息
				searchMap.clear();
				searchMap.put("goodsIssue", lgi.getUuid());
				searchMap.put("isLuck", 1);
				
				List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
				if(listorder != null && listorder.size() > 0) {
					FrontUserPaymentOrder fupo = listorder.get(0);
					lgivo.setShowIdStr(fupo.getFrontUserShowId()+"");
					lgivo.setFrontUser(fupo.getFrontUser());
					searchMap.clear();
					searchMap.put("goodsIssue", lgi.getUuid());
					searchMap.put("frontUser", fupo.getFrontUser());
					List<FrontUserPaymentOrder> listordersum = this.frontUserPaymentOrderDao.getGroupListByParams(searchMap);
					if(listordersum != null && listordersum.size() > 0) {
						lgivo.setBuyCount(listordersum.get(0).getBuyCount());
					}
					FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
					lgivo.setNickname(fu.getNickname());
					lgivo.setdAmount(fupo.getdAmount());
					lgivo.setActualDAmount(fupo.getActualDAmount());
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource re = this.resourceDao.get(fu.getImage());
						if(re != null) {
							lgivo.setImageUrl(pathUrl + re.getUrl());
						}
					}
				}
				listvo.add(lgivo);
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
	 * 本期参与记录
	 */
	@Override
	public void paymentList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String goodsIssue = paramsMap.get("goodsIssue") == null ? "" : paramsMap.get("goodsIssue").toString();
		Boolean isLuck = paramsMap.get("isLuck") == null ? null : Boolean.valueOf(paramsMap.get("isLuck").toString());
		String gameType = paramsMap.get("gameType") == null ? Constants.GAME_TYPE_TRADITION : paramsMap.get("gameType").toString();

    	//20201106增加马甲商品查询区分
    	String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	
    	if(Utlity.checkStringNull(demoFlag)) {
    		Boolean isDemo = isDemo(version, channel);
    		if(isDemo) {
    			demoFlag = "1";
    		} else {
    			demoFlag = "0";
    		}
    	}
    	
		if(Constants.GAME_TYPE_TRADITION.equals(gameType)) {
			tradition2PaymentList(result, pageNum, pageSize, sort, showId, goodsIssue, isLuck, demoFlag);
		} else if (Constants.GAME_TYPE_GROUP2.equals(gameType)) {
			group2PaymentList(result, pageNum, pageSize, sort, goodsIssue);
		} else {
			result.setMessage("Network anomalies, please try again later!");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
		}
	}
	
	public void tradition2PaymentList(DataResult<Object> result, Integer pageNum, Integer pageSize, String sort, String showId, String goodsIssue, Boolean isLuck, String demoFlag) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("showId", showId);
		searchMap.put("goodsIssue", goodsIssue);
		if(isLuck != null) {
			searchMap.put("isLuck", isLuck);
		}
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		searchMap.put("demoFlag", demoFlag);
		
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
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
				if(fu != null) {
					fupovo.setNickname(fu.getNickname());
					//获取头像信息
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fupovo.setImageURL(pathUrl + res.getUrl());
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
		result.setMessage("Successful!");
	}
	
	public void group2PaymentList(DataResult<Object> result, Integer pageNum, Integer pageSize, String sort, String goodsIssue) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("goodsIssue", goodsIssue);
		
		//幸运队数据
		Map<String, Object> luckyData = new HashMap<String, Object>();
		searchMap.put("paymentGroup", FrontUserPaymentOrderGroup.LUCKY);
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
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
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
				if(fu != null) {
					fupovo.setNickname(fu.getNickname());
					//获取头像信息
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fupovo.setImageURL(pathUrl + res.getUrl());
						}
					}
				}
				listvo.add(fupovo);
			}
		}
		luckyData.put("listData", listvo);
		luckyData.put("pageNum", pageNum);
		luckyData.put("pageSize", pageSize);
		luckyData.put("totalResultCount", totalCount);
		
		//夺宝队数据
		Map<String, Object> raiderData = new HashMap<String, Object>();
		
		searchMap.put("paymentGroup", FrontUserPaymentOrderGroup.RAIDER);
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<FrontUserPaymentOrder> raiderlist = this.frontUserPaymentOrderDao.getListByParams(searchMap);
		Integer raidertotalCount = this.frontUserPaymentOrderDao.getCountByParams(searchMap);
		
		List<FrontUserPaymentOrderVO> raiderlistvo = new ArrayList<FrontUserPaymentOrderVO>();
		if(raiderlist != null && raiderlist.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(FrontUserPaymentOrder fupo : raiderlist) {
				FrontUserPaymentOrderVO fupovo = new FrontUserPaymentOrderVO(fupo);
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
				if(fu != null) {
					fupovo.setNickname(fu.getNickname());
					//获取头像信息
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fupovo.setImageURL(pathUrl + res.getUrl());
						}
					}
				}
				raiderlistvo.add(fupovo);
			}
		}
		raiderData.put("listData", raiderlistvo);
		raiderData.put("pageNum", pageNum);
		raiderData.put("pageSize", pageSize);
		raiderData.put("totalResultCount", raidertotalCount);
		
		Map<String, Map<String, Object>> data = new HashMap<String, Map<String, Object>>();
		data.put(FrontUserPaymentOrderGroup.LUCKY, luckyData);
		data.put(FrontUserPaymentOrderGroup.RAIDER, raiderData);
		result.setData(data);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount >= raidertotalCount?totalCount : raidertotalCount);
		result.setMessage("Successful!");
	}

	/**
	 * 往期揭晓结果
	 */
	@Override
	public void winningInfoList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String goodsId = paramsMap.get("goodsId") == null ? "" : paramsMap.get("goodsId").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		String gameType = paramsMap.get("gameType") == null ? Constants.GAME_TYPE_TRADITION : paramsMap.get("gameType").toString();
    	
    	//20201106增加马甲商品查询区分
    	String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	
    	if(Utlity.checkStringNull(demoFlag)) {
    		Boolean isDemo = isDemo(version, channel);
    		if(isDemo) {
    			demoFlag = "1";
    		} else {
    			demoFlag = "0";
    		}
    	}
    	
		if(Constants.GAME_TYPE_TRADITION.equals(gameType)) {
			tradition2WinningInfo(result, pageNum, pageSize, showId, goodsId, type, sort, demoFlag);
		} else if (Constants.GAME_TYPE_GROUP2.equals(gameType)) {
			group2WinningInfo(result, pageNum, pageSize, goodsId, sort, gameType);
		} else {
			result.setMessage("Network anomalies, please try again later!");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
		}
		
	}
	
	/**
	 * 传统玩法
	 * @param result
	 * @param pageNum
	 * @param pageSize
	 * @param showId
	 * @param goodsId
	 * @param type
	 * @param sort
	 */
	public void tradition2WinningInfo(DataResult<Object> result, Integer pageNum, Integer pageSize, String showId, String goodsId, String type, String sort, String demoFlag) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("showId", showId);
		searchMap.put("goodsId", goodsId);
		searchMap.put("type", type);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		searchMap.put("demoFlag", demoFlag);
		
		List<WinningInfo> list = this.winningInfoDao.getListByParams(searchMap);
		Integer totalCount = this.winningInfoDao.getCountByParams(searchMap);
		
		List<WinningInfoVO> listvo = new ArrayList<WinningInfoVO>();
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(WinningInfo wi : list) {
				WinningInfoVO wivo = null;
				wivo = new WinningInfoVO(wi);
				
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
	/**
	 * 查询战队玩法往期揭晓数据
	 * @param pageNum
	 * @param pageSize
	 * @param showId
	 * @param goodsId
	 * @param type
	 * @param sort
	 * @param gameType
	 */
	public void group2WinningInfo(DataResult<Object> result, Integer pageNum, Integer pageSize, String goodsId, String sort, String gameType) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("goodsId", goodsId);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		searchMap.put("gameType", gameType);
		searchMap.put("status", LuckygameGoodsIssueStatus.FINISHED);
		List<LuckygameGoodsIssue> list = this.luckygameGoodsIssueDao.getListByParams(searchMap);
		Integer totalCount = this.luckygameGoodsIssueDao.getCountByParams(searchMap);
		List<GroupWinningInfoVO> listvo = new ArrayList<GroupWinningInfoVO>();
		if(list != null && list.size() > 0) {
			for(LuckygameGoodsIssue lgi : list) {
				GroupWinningInfoVO gwivo = new GroupWinningInfoVO(lgi);
				listvo.add(gwivo);
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

	/**
	 * 获取沙发、土豪、包尾数据
	 */
	@Override
	public void paymentTop(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(uuid);
		if(lgi == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Product information error!");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("goodsIssue", uuid);
		searchMap.put("status", FrontUserPaymentOrderStatus.SUCCESS);
		//获取数据集
		List<FrontUserPaymentOrder> list = this.frontUserPaymentOrderDao.getListByParams(searchMap);
		
		Map<String, Object> shafa = new HashMap<String, Object>();
		shafa.put("nickname", "");
		shafa.put("imageUrl", "");
		shafa.put("frontUser", "");
		shafa.put("frontUserShowId", "");
		shafa.put("buyCount", "");
		Map<String, Object> tuhao = new HashMap<String, Object>();
		tuhao.put("nickname", "");
		tuhao.put("imageUrl", "");
		tuhao.put("frontUser", "");
		tuhao.put("frontUserShowId", "");
		tuhao.put("buyCount", "");
		Map<String, Object> baowei = new HashMap<String, Object>();
		baowei.put("nickname", "");
		baowei.put("imageUrl", "");
		baowei.put("frontUser", "");
		baowei.put("frontUserShowId", "");
		baowei.put("buyCount", "");

		FrontUserPaymentOrderTopVO topvo = new FrontUserPaymentOrderTopVO();
		
		if(list != null && list.size() > 0) {
			FrontUserPaymentOrder first = list.get(list.size() - 1);//沙发
			FrontUser fu = this.frontUserDao.get(first.getFrontUser());
			if(fu != null) {
				shafa.put("nickname", fu.getNickname());
				shafa.put("frontUser", fu.getUuid());
				shafa.put("frontUserShowId", fu.getShowId());
				if(!Utlity.checkStringNull(fu.getImage())) {
					Resource r = this.resourceDao.get(fu.getImage());
					if(r != null) {
						shafa.put("imageUrl", Utlity.IMAGE_PATH_URL + r.getUrl());
					} else {
						shafa.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
					}
				} else {
					shafa.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
				}
			}
			FrontUserPaymentOrder top1 = null;//土豪
			Map<String, FrontUserPaymentOrder> countMap = new HashMap<String, FrontUserPaymentOrder>();
			for(FrontUserPaymentOrder fupo : list) {
				if(countMap.containsKey(fupo.getFrontUser())) {
					Integer buycount = fupo.getBuyCount()+countMap.get(fupo.getFrontUser()).getBuyCount();
					fupo.setBuyCount(buycount);
				}
				countMap.put(fupo.getFrontUser(), fupo);
			}
			shafa.put("buyCount", countMap.get(first.getFrontUser()).getBuyCount());
			List<Map.Entry<String,FrontUserPaymentOrder>> listsort = new ArrayList<Map.Entry<String,FrontUserPaymentOrder>>(countMap.entrySet());
			Collections.sort(listsort, new Comparator<Map.Entry<String,FrontUserPaymentOrder>>() {
				@Override
				public int compare(Map.Entry<String,FrontUserPaymentOrder> u1, Map.Entry<String,FrontUserPaymentOrder> u2) {//buyCount升序
					return u1.getValue().getBuyCount() - u2.getValue().getBuyCount(); //相等为0
				}
			});
			top1 = listsort.get(listsort.size() - 1).getValue();
			FrontUser futop = this.frontUserDao.get(top1.getFrontUser());
			if(futop != null) {
				tuhao.put("nickname", futop.getNickname());
				tuhao.put("frontUser", futop.getUuid());
				tuhao.put("frontUserShowId", futop.getShowId());
				if(!Utlity.checkStringNull(futop.getImage())) {
					Resource r = this.resourceDao.get(futop.getImage());
					if(r != null) {
						tuhao.put("imageUrl", Utlity.IMAGE_PATH_URL + r.getUrl());
					} else {
						tuhao.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
					}
				} else {
					tuhao.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
				}
			}
			tuhao.put("buyCount", countMap.get(top1.getFrontUser()).getBuyCount());
			if(!LuckygameGoodsIssueStatus.BETTING.equals(lgi.getStatus())) {
				FrontUserPaymentOrder last = list.get(0);//包尾
				FrontUser fue = this.frontUserDao.get(last.getFrontUser());
				if(fue != null) {
					baowei.put("nickname", fue.getNickname());
					baowei.put("frontUser", fue.getUuid());
					baowei.put("frontUserShowId", fue.getShowId());
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource r = this.resourceDao.get(fue.getImage());
						if(r != null) {
							baowei.put("imageUrl", Utlity.IMAGE_PATH_URL + r.getUrl());
						} else {
							baowei.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
						}
					} else {
						baowei.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
					}
				}
				baowei.put("buyCount", countMap.get(last.getFrontUser()).getBuyCount());
			}
		}
		
		topvo.setBaowei(baowei);
		topvo.setShafa(shafa);
		topvo.setTuhao(tuhao);
		
		result.setData(topvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	/**
	 * 商品类型列表
	 */
	@Override
	public void goodsType(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String level = paramsMap.get("level") == null ? "" : paramsMap.get("level").toString();
		String parent = paramsMap.get("parent") == null ? "" : paramsMap.get("parent").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("name", name);
		paramsls.put("level", level);
		paramsls.put("parent", parent);
		paramsls.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			paramsls.put("offSet", (pageNum-1)*pageSize);
			paramsls.put("pageSize", pageSize);
		}
		paramsls.put("sort", sort);
		
		Integer totalCount = goodsTypeDao.getCountByParams(paramsls);
		List<GoodsType> goodsTypeList = goodsTypeDao.getListByParams(paramsls);
		
		result.setData(goodsTypeList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
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
