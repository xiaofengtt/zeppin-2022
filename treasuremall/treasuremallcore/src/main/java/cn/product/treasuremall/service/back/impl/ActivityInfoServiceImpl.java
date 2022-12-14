package cn.product.treasuremall.service.back.impl;

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
import cn.product.treasuremall.controller.base.TransactionException;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeDao;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.treasuremall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.treasuremall.dao.ActivityInfoDao;
import cn.product.treasuremall.dao.ActivityInfoFirstchargePrizeDao;
import cn.product.treasuremall.dao.ActivityInfoRechargePrizeDao;
import cn.product.treasuremall.dao.ActivityInfoScorelotteryPrizeDao;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.VoucherDao;
import cn.product.treasuremall.entity.ActivityInfo;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoName;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.treasuremall.entity.ActivityInfoBuyfree;
import cn.product.treasuremall.entity.ActivityInfoBuyfree.ActivityInfoBuyfreeStatus;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeGoods;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeGoods.ActivityInfoBuyfreeGoodsStatus;
import cn.product.treasuremall.entity.ActivityInfoCheckinPrize;
import cn.product.treasuremall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeStatus;
import cn.product.treasuremall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeType;
import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize;
import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize.ActivityInfoFirstchargePrizeStatus;
import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize.ActivityInfoFirstchargePrizeType;
import cn.product.treasuremall.entity.ActivityInfoRechargePrize;
import cn.product.treasuremall.entity.ActivityInfoRechargePrize.ActivityInfoRechargePrizeStatus;
import cn.product.treasuremall.entity.ActivityInfoRechargePrize.ActivityInfoRechargePrizeType;
import cn.product.treasuremall.entity.ActivityInfoScorelotteryPrize;
import cn.product.treasuremall.entity.ActivityInfoScorelotteryPrize.ActivityInfoScorelotteryPrizeStatus;
import cn.product.treasuremall.entity.ActivityInfoScorelotteryPrize.ActivityInfoScorelotteryPrizeType;
import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.Goods.GoodsStatus;
import cn.product.treasuremall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.treasuremall.entity.Voucher;
import cn.product.treasuremall.entity.Voucher.VoucherStatus;
import cn.product.treasuremall.entity.activity.ConfigBuyfree;
import cn.product.treasuremall.entity.activity.ConfigBuyfreeDetail;
import cn.product.treasuremall.entity.activity.ConfigBuyfreeDetail.ConfigBuyFreeDetailType;
import cn.product.treasuremall.entity.activity.ConfigCheckinDetail;
import cn.product.treasuremall.entity.activity.ConfigCheckinDetail.ConfigCheckinDetailType;
import cn.product.treasuremall.entity.activity.ConfigFirstchargeDetail;
import cn.product.treasuremall.entity.activity.ConfigFirstchargeDetail.ConfigFirstchargeDetailType;
import cn.product.treasuremall.entity.activity.ConfigRechargeDetail;
import cn.product.treasuremall.entity.activity.ConfigRechargeDetail.ConfigRechargeDetailType;
import cn.product.treasuremall.entity.activity.ConfigScorelottery;
import cn.product.treasuremall.entity.activity.ConfigScorelotteryDetail;
import cn.product.treasuremall.entity.activity.ConfigScorelotteryDetail.ConfigScorelotteryDetailType;
import cn.product.treasuremall.service.back.ActivityInfoService;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.ActivityInfoVO;

@Service("activityInfoService")
public class ActivityInfoServiceImpl implements ActivityInfoService{
	
	@Autowired
	private ActivityInfoDao activityInfoDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private ActivityInfoBuyfreeGoodsDao activityInfoBuyfreeGoodsDao;
	
	@Autowired
	private ActivityInfoBuyfreeDao activityInfoBuyfreeDao;
	
	@Autowired
	private VoucherDao voucherDao;

	@Autowired
	private ActivityInfoCheckinPrizeDao activityInfoCheckinPrizeDao;

	@Autowired
	private ActivityInfoScorelotteryPrizeDao activityInfoScorelotteryPrizeDao;

	@Autowired
	private ActivityInfoFirstchargePrizeDao activityInfoFirstchargePrizeDao;

	@Autowired
	private CapitalAccountDao capitalAccountDao;

	@Autowired
	private ActivityInfoRechargePrizeDao activityInfoRechargePrizeDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//??????????????????????????????????????????
		Integer totalResultCount = activityInfoDao.getCountByParams(searchMap);
		//???????????????????????????????????????
		List<ActivityInfo> list = activityInfoDao.getListByParams(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		//??????????????????
		ActivityInfo ai = activityInfoDao.get(name);
		if (ai == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
		}
		
		ActivityInfoVO vo = new ActivityInfoVO(ai);
		if(ActivityInfoName.RECOMMEND.equals(ai.getName())){
			String vouchers = vo.getConfigurationMap().get("voucher").toString();
			String[] voucherUuids = vouchers.split(",");
			List<Map<String, Object>> voucherList = new ArrayList<Map<String, Object>>();
			for(String uuid : voucherUuids){
				Voucher v = this.voucherDao.get(uuid);
				
				if(v != null){
					Map<String, Object> vMap = new HashMap<String, Object>();
					vMap.put("uuid", uuid);
					vMap.put("title", v.getTitle());
					voucherList.add(vMap);
				}
			}
			vo.getConfigurationMap().put("voucher", voucherList);
		}
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String starttime = paramsMap.get("starttime") == null ? "" : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
		String bannerUrl = paramsMap.get("bannerUrl") == null ? "" : paramsMap.get("bannerUrl").toString();
		String linkUrl = paramsMap.get("linkUrl") == null ? "" : paramsMap.get("linkUrl").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		//config??????
		String configuration = paramsMap.get("configuration") == null ? "" : paramsMap.get("configuration").toString();//JSON?????????
		String details =  paramsMap.get("details") == null ? "" : paramsMap.get("details").toString();
		
		if(Utlity.checkStringNull(starttime) || Utlity.checkStringNull(endtime)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????????????????");
			return;
		}
		
		if(details == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????????????????");
			return;
		}
		
		try {
			//??????????????????
			ActivityInfo ai = activityInfoDao.get(name);
			if(ai != null && name.equals(ai.getName()) && !ActivityInfoStatus.DELETE.equals(ai.getStatus())){
				Map<String, Object> editMap = new HashMap<String, Object>();
				
				ai.setStarttime(new Timestamp(Utlity.stringToDatetime(starttime).getTime()));
				ai.setEndtime(new Timestamp(Utlity.stringToDatetime(endtime).getTime()));
				
				if(!Utlity.checkStringNull(bannerUrl)) {
					ai.setBannerUrl(bannerUrl);
				}

				if(!Utlity.checkStringNull(linkUrl)) {
					ai.setLinkUrl(linkUrl);
				}
				
				//??????????????????????????????
				if(ActivityInfoName.BUYFREE.equals(name)) {//0??????
					if(Utlity.checkStringNull(configuration)) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("??????????????????????????????????????????");
						return;
					}
					try {
						ConfigBuyfree cbf = JSONUtils.json2obj(configuration, ConfigBuyfree.class);
						if(cbf != null && cbf.getTimesLine() != null && cbf.getTimesLine() > 0) {
							ai.setConfiguration(configuration);
						} else {
							throw new TransactionException("?????????????????????????????????");
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("?????????????????????????????????");
					}
					
					try {
						List<ActivityInfoBuyfreeGoods> listAdd = new ArrayList<ActivityInfoBuyfreeGoods>();
						List<ActivityInfoBuyfreeGoods> listEdit = new ArrayList<ActivityInfoBuyfreeGoods>();
						List<ActivityInfoBuyfreeGoods> listDelete = new ArrayList<ActivityInfoBuyfreeGoods>();
						List<ConfigBuyfreeDetail> listDetail = JSONUtils.json2list(details, ConfigBuyfreeDetail.class);
						for(ConfigBuyfreeDetail cbd : listDetail) {
							//detail ????????????uuid,activityInfo,status,goodsId,goodsCover,shares,sort
//								ConfigBuyfreeDetail cbd = JSONUtils.json2obj(detail, ConfigBuyfreeDetail.class);
							//??????type
							if(cbd != null && !Utlity.checkStringNull(cbd.getType())) {
								
								if(Utlity.checkStringNull(cbd.getGoodsId())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("??????????????????");
									return;
								}
								if(Utlity.checkStringNull(cbd.getGoodsCover())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("????????????????????????");
									return;
								}
								if(cbd.getShares() == null || cbd.getShares() == 0) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("????????????????????????");
									return;
								}
								
								Goods g = this.goodsDao.get(cbd.getGoodsId());
								if(g == null || GoodsStatus.DELETE.equals(g.getStatus())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("??????????????????");
									return;
								}
								if(ConfigBuyFreeDetailType.ADD.equals(cbd.getType())) {//??????
									ActivityInfoBuyfreeGoods aibg = new ActivityInfoBuyfreeGoods();
									aibg.setUuid(UUID.randomUUID().toString());
									aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.DISABLE);//???????????????
									aibg.setCreatetime(new Timestamp(System.currentTimeMillis()));
									aibg.setActivityInfo(cbd.getActivityInfo());
									aibg.setCreator(admin);
									aibg.setCurrentIssueNum(0);
									aibg.setGoodsCover(cbd.getGoodsCover());
									aibg.setGoodsId(cbd.getGoodsId());
									aibg.setGoodsPrice(g.getPrice());
									aibg.setGoodsTitle(g.getName());
									aibg.setGoodsShortTitle(g.getShortname());
									aibg.setShares(cbd.getShares());
									aibg.setSort(cbd.getSort());
									
									//?????????????????????????????????
									if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {//??????????????????????????????????????????
										List<ActivityInfoBuyfree> listIssueAdd = new ArrayList<ActivityInfoBuyfree>();
										if(ActivityInfoBuyfreeGoodsStatus.NORMAL.equals(aibg.getStatus())) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("???????????????????????????????????????");
											return;
										}
										aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.NORMAL);
										//???????????????????????????
										Integer issueNum = 0;//?????????????????????
										boolean isNext = true;//?????????????????????
										//????????????
										
										if(isNext) {
											//??????????????????
											aibg.setCurrentIssueNum(issueNum + 1);
											//??????????????????
											ActivityInfoBuyfree aib = new ActivityInfoBuyfree();
											aib.setUuid(UUID.randomUUID().toString());
											aib.setActivityInfo(ai.getName());
											aib.setActivityInfoBuyfreeGoods(aibg.getUuid());
											aib.setBetShares(0);
											aib.setCreatetime(new Timestamp(System.currentTimeMillis()));
											aib.setGoodsCover(aibg.getGoodsCover());
											aib.setGoodsId(aibg.getGoodsId());
											aib.setGoodsPrice(aibg.getGoodsPrice());
											aib.setGoodsTitle(aibg.getGoodsTitle());
											aib.setGoodsShortTitle(aibg.getGoodsShortTitle());
											aib.setIssueNum(aibg.getCurrentIssueNum());
											aib.setRemainShares(aibg.getShares());
											aib.setShares(aibg.getShares());
											aib.setSort(aibg.getSort());
											aib.setStatus(ActivityInfoBuyfreeStatus.BETTING);
											listIssueAdd.add(aib);
										}
										
										listEdit.add(aibg);
										editMap.put("issueAdd", listIssueAdd);
									}
									listAdd.add(aibg);
								} else if(ConfigBuyFreeDetailType.EDIT.equals(cbd.getType())) {//??????
									ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(cbd.getUuid());
									if(aibg == null || ActivityInfoBuyfreeGoodsStatus.DELETE.equals(aibg.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("????????????????????????");
										return;
									}
									if(!aibg.getGoodsId().equals(cbd.getGoodsId())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("?????????????????????");
										return;
									}
									aibg.setGoodsCover(cbd.getGoodsCover());
									aibg.setGoodsPrice(g.getPrice());
									aibg.setGoodsTitle(g.getName());
									aibg.setGoodsShortTitle(g.getShortname());
									aibg.setShares(cbd.getShares());
									aibg.setSort(cbd.getSort());
									listEdit.add(aibg);
								} else if(ConfigBuyFreeDetailType.DELETE.equals(cbd.getType())) {//??????
									ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(cbd.getUuid());
									if(aibg == null || ActivityInfoBuyfreeGoodsStatus.DELETE.equals(aibg.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("????????????????????????");
										return;
									}
									aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.DELETE);
									listDelete.add(aibg);
								}
							} else {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????");
								return;
							}
						}
						
						editMap.put("add", listAdd);
						editMap.put("edit", listEdit);
						editMap.put("delete", listDelete);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("???????????????????????????");
					}
					this.activityInfoDao.editBuyfree(ai, editMap);
				} else if (ActivityInfoName.CHECKIN.equals(name)) {//????????????
					
					try {
						List<ActivityInfoCheckinPrize> listAdd = new ArrayList<ActivityInfoCheckinPrize>();
						List<ActivityInfoCheckinPrize> listEdit = new ArrayList<ActivityInfoCheckinPrize>();
						List<ActivityInfoCheckinPrize> listDelete = new ArrayList<ActivityInfoCheckinPrize>();
						List<ConfigCheckinDetail> listDetail = JSONUtils.json2list(details, ConfigCheckinDetail.class);
						for(ConfigCheckinDetail ccd : listDetail) {
							if(ccd != null && !Utlity.checkStringNull(ccd.getType())) {
								if(Utlity.checkStringNull(ccd.getPrizeTitle())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("???"+ccd.getDayNum()+"???????????????????????????");
									return;
								}
								if(Utlity.checkStringNull(ccd.getPrizeType()) || Utlity.checkStringNull(ccd.getPrize())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("???"+ccd.getDayNum()+"?????????????????????");
									return;
								}
								if(ActivityInfoCheckinPrizeType.ENTITY.equals(ccd.getPrizeType()) || ActivityInfoCheckinPrizeType.VOUCHER.equals(ccd.getPrizeType())) {
									if(ccd.getPrize().length() != 36) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("???"+ccd.getDayNum()+"????????????????????????");
										return;
									}
									if(ActivityInfoCheckinPrizeType.ENTITY.equals(ccd.getPrizeType())) {
										Goods goods = this.goodsDao.get(ccd.getPrize());
										if(goods == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("???"+ccd.getDayNum()+"???????????????????????????");
											return;
										}
									} else {
										Voucher v = this.voucherDao.get(ccd.getPrize());
										if(v == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("???"+ccd.getDayNum()+"???????????????????????????");
											return;
										}
									}
								} else if(ActivityInfoCheckinPrizeType.GOLD.equals(ccd.getPrizeType())) {
									if(!Utlity.isNumeric(ccd.getPrize())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("???"+ccd.getDayNum()+"?????????????????????,?????????????????????????????????");
										return;
									}
								}
								if(ConfigCheckinDetailType.ADD.equals(ccd.getType())) {//??????
									ActivityInfoCheckinPrize aicp = new ActivityInfoCheckinPrize();
									aicp.setUuid(UUID.randomUUID().toString());
									aicp.setStatus(ActivityInfoCheckinPrizeStatus.DISABLE);//???????????????
									if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
										aicp.setStatus(ActivityInfoCheckinPrizeStatus.NORMAL);//???????????????
									}
									aicp.setCreatetime(new Timestamp(System.currentTimeMillis()));
									aicp.setActivityInfo(ccd.getActivityInfo());
									aicp.setCreator(admin);
									
									aicp.setDayNum(ccd.getDayNum());
									aicp.setPrize(ccd.getPrize());
									if(!Utlity.checkStringNull(ccd.getPrizeCover())) {
										aicp.setPrizeCover(ccd.getPrizeCover());
									}
									aicp.setPrizeTitle(ccd.getPrizeTitle());
									aicp.setPrizeType(ccd.getPrizeType());
									aicp.setSort(ccd.getSort());
									listAdd.add(aicp);
								} else if(ConfigCheckinDetailType.EDIT.equals(ccd.getType())) {//??????
									ActivityInfoCheckinPrize aicp = this.activityInfoCheckinPrizeDao.get(ccd.getUuid());
									if(aicp == null || ActivityInfoCheckinPrizeStatus.DELETE.equals(aicp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("???"+ccd.getDayNum()+"???????????????????????????");
										return;
									}
									
									aicp.setDayNum(ccd.getDayNum());
									aicp.setPrize(ccd.getPrize());
									if(!Utlity.checkStringNull(ccd.getPrizeCover())) {
										aicp.setPrizeCover(ccd.getPrizeCover());
									}
									aicp.setPrizeTitle(ccd.getPrizeTitle());
									aicp.setPrizeType(ccd.getPrizeType());
									aicp.setSort(ccd.getSort());
									listEdit.add(aicp);
								} else if(ConfigCheckinDetailType.DELETE.equals(ccd.getType())) {//??????
									ActivityInfoCheckinPrize aicp = this.activityInfoCheckinPrizeDao.get(ccd.getUuid());
									if(aicp == null || ActivityInfoCheckinPrizeStatus.DELETE.equals(aicp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("???"+ccd.getDayNum()+"???????????????????????????");
										return;
									}
									aicp.setStatus(ActivityInfoCheckinPrizeStatus.DELETE);
									listDelete.add(aicp);
								} 
								
							} else {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???"+ccd.getDayNum()+"??????????????????????????????");
								return;
							}
						}
						editMap.put("add", listAdd);
						editMap.put("edit", listEdit);
						editMap.put("delete", listDelete);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("???????????????????????????");
					}
					this.activityInfoDao.editCheckin(ai, editMap);
				} else if (ActivityInfoName.SCORELOTTERY.equals(name)) {//????????????
					if(Utlity.checkStringNull(configuration)) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("??????????????????????????????????????????");
						return;
					}
					try {
						ConfigScorelottery cs = JSONUtils.json2obj(configuration, ConfigScorelottery.class);
						if(cs != null) {
							if(cs.getTimesLine() == null || cs.getTimesLine() <= 0) {
								throw new TransactionException("?????????????????????????????????");
							}
							if(cs.getScoreLine() == null || !Utlity.isNumeric(cs.getScoreLine())) {
								throw new TransactionException("?????????????????????????????????");
							}
							BigDecimal de = BigDecimal.valueOf(Double.valueOf(cs.getScoreLine()));
							if(de.compareTo(BigDecimal.ZERO) <0) {//??????0?????? ?????????
								throw new TransactionException("??????????????????????????????????????????");
							}
							ai.setConfiguration(configuration);
						} else {
							throw new TransactionException("???????????????????????????");
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("?????????????????????????????????");
					}
					
					
					try {
						List<ActivityInfoScorelotteryPrize> listAdd = new ArrayList<ActivityInfoScorelotteryPrize>();
						List<ActivityInfoScorelotteryPrize> listEdit = new ArrayList<ActivityInfoScorelotteryPrize>();
						List<ActivityInfoScorelotteryPrize> listDelete = new ArrayList<ActivityInfoScorelotteryPrize>();
						List<ConfigScorelotteryDetail> listDetail = JSONUtils.json2list(details, ConfigScorelotteryDetail.class);
						BigDecimal totalRate = BigDecimal.ZERO;
						boolean flag = false;
						for(ConfigScorelotteryDetail csd : listDetail) {
							if(csd != null && !Utlity.checkStringNull(csd.getType())) {
								if(Utlity.checkStringNull(csd.getPrizeTitle())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("??????"+csd.getSort()+"????????????????????????");
									return;
								}
								if(Utlity.checkStringNull(csd.getPrizeType()) || Utlity.checkStringNull(csd.getPrize())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("??????"+csd.getSort()+"??????????????????");
									return;
								}
								if(csd.getWinningRate() == null || !Utlity.isNumeric(csd.getWinningRate())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("??????"+csd.getSort()+"?????????????????????");
									return;
								}
								BigDecimal rate = BigDecimal.valueOf(Double.valueOf(csd.getWinningRate()));
								if(rate.compareTo(BigDecimal.ZERO) <= 0) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("??????"+csd.getSort()+"????????????????????????");
									return;
								}
								if(ActivityInfoScorelotteryPrizeType.ENTITY.equals(csd.getPrizeType()) || ActivityInfoScorelotteryPrizeType.VOUCHER.equals(csd.getPrizeType())) {
									if(csd.getPrize().length() != 36) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("??????"+csd.getSort()+"?????????????????????");
										return;
									}
									if(ActivityInfoScorelotteryPrizeType.ENTITY.equals(csd.getPrizeType())) {
										Goods goods = this.goodsDao.get(csd.getPrize());
										if(goods == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("??????"+csd.getSort()+"????????????????????????");
											return;
										}
									} else {
										Voucher v = this.voucherDao.get(csd.getPrize());
										if(v == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("??????"+csd.getSort()+"????????????????????????");
											return;
										}
									}
								} else if(ActivityInfoScorelotteryPrizeType.GOLD.equals(csd.getPrizeType())) {
									if(!Utlity.isNumeric(csd.getPrize())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("??????"+csd.getSort()+"??????????????????,?????????????????????????????????");
										return;
									}
								}
								if(ConfigScorelotteryDetailType.ADD.equals(csd.getType())) {//??????
									totalRate = totalRate.add(rate);
									flag = true;
									ActivityInfoScorelotteryPrize aisp = new ActivityInfoScorelotteryPrize();
									aisp.setUuid(UUID.randomUUID().toString());
									aisp.setStatus(ActivityInfoScorelotteryPrizeStatus.DISABLE);//???????????????
									if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
										aisp.setStatus(ActivityInfoScorelotteryPrizeStatus.NORMAL);//???????????????
									}
									aisp.setCreatetime(new Timestamp(System.currentTimeMillis()));
									aisp.setActivityInfo(csd.getActivityInfo());
									aisp.setCreator(admin);
									
									aisp.setWinningRate(rate);
									aisp.setPrize(csd.getPrize());
									if(!Utlity.checkStringNull(csd.getPrizeCover())) {
										aisp.setPrizeCover(csd.getPrizeCover());
									}
									aisp.setPrizeTitle(csd.getPrizeTitle());
									aisp.setPrizeType(csd.getPrizeType());
									aisp.setSort(csd.getSort());
									listAdd.add(aisp);
								} else if(ConfigScorelotteryDetailType.EDIT.equals(csd.getType())) {//??????
									flag = true;
									totalRate = totalRate.add(rate);
									ActivityInfoScorelotteryPrize aisp = this.activityInfoScorelotteryPrizeDao.get(csd.getUuid());
									if(aisp == null || ActivityInfoScorelotteryPrizeStatus.DELETE.equals(aisp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("??????"+csd.getSort()+"????????????????????????");
										return;
									}
									
									aisp.setWinningRate(rate);
									aisp.setPrize(csd.getPrize());
									if(!Utlity.checkStringNull(csd.getPrizeCover())) {
										aisp.setPrizeCover(csd.getPrizeCover());
									}
									aisp.setPrizeTitle(csd.getPrizeTitle());
									aisp.setPrizeType(csd.getPrizeType());
									aisp.setSort(csd.getSort());
									listEdit.add(aisp);
								} else if(ConfigScorelotteryDetailType.DELETE.equals(csd.getType())) {//??????
									ActivityInfoScorelotteryPrize aisp = this.activityInfoScorelotteryPrizeDao.get(csd.getUuid());
									if(aisp == null || ActivityInfoScorelotteryPrizeStatus.DELETE.equals(aisp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("??????"+csd.getSort()+"????????????????????????");
										return;
									}
									aisp.setStatus(ActivityInfoScorelotteryPrizeStatus.DELETE);
									listDelete.add(aisp);
								} 
								
							} else {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("??????"+csd.getSort()+"???????????????????????????");
								return;
							}
						}
						if(flag && totalRate.compareTo(BigDecimal.valueOf(100)) != 0) {
							result.setStatus(ResultStatusType.FAILED);
							result.setMessage("????????????????????????????????????100");
							return;
						}
						editMap.put("add", listAdd);
						editMap.put("edit", listEdit);
						editMap.put("delete", listDelete);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("???????????????????????????");
					}
					this.activityInfoDao.editScorelottery(ai, editMap);
				} else if (ActivityInfoName.FIRSTCHARGE.equals(name)) {
					try {
						List<ActivityInfoFirstchargePrize> listAdd = new ArrayList<ActivityInfoFirstchargePrize>();
						List<ActivityInfoFirstchargePrize> listEdit = new ArrayList<ActivityInfoFirstchargePrize>();
						List<ActivityInfoFirstchargePrize> listDelete = new ArrayList<ActivityInfoFirstchargePrize>();
						List<ConfigFirstchargeDetail> listDetail = JSONUtils.json2list(details, ConfigFirstchargeDetail.class);
						for(ConfigFirstchargeDetail cfd : listDetail) {
							if(cfd != null && !Utlity.checkStringNull(cfd.getType())) {
								
								if(Utlity.checkStringNull(cfd.getAmount())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("????????????????????????");
									return;
								}
								BigDecimal amount = BigDecimal.valueOf(Double.valueOf(cfd.getAmount()));
								if(amount.compareTo(BigDecimal.ZERO) <= 0) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("??????????????????????????????");
									return;
								}
								if(!Utlity.checkStringNull(cfd.getCapitalAccount())) {
									CapitalAccount ca = this.capitalAccountDao.get(cfd.getCapitalAccount());
									if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("????????????????????????????????????????????????");
										return;
									}
								}
								
								if(Utlity.checkStringNull(cfd.getPrizeType()) || Utlity.checkStringNull(cfd.getPrize())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("??????????????????");
									return;
								}
								
								if(ActivityInfoFirstchargePrizeType.ENTITY.equals(cfd.getPrizeType()) || ActivityInfoFirstchargePrizeType.VOUCHER.equals(cfd.getPrizeType())) {
									if(ActivityInfoFirstchargePrizeType.ENTITY.equals(cfd.getPrizeType())) {
										Goods goods = this.goodsDao.get(cfd.getPrize());
										if(goods == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("????????????????????????");
											return;
										}
									} else {
										String[] vs = cfd.getPrize().split(",");
										for(String voucher : vs) {
											Voucher v = this.voucherDao.get(voucher);
											if(v == null) {
												result.setStatus(ResultStatusType.FAILED);
												result.setMessage("?????????????????????????????????????????????");
												return;
											}
										}
									
									}
								} else if(ActivityInfoFirstchargePrizeType.GOLD.equals(cfd.getPrizeType())) {
									if(!Utlity.isNumeric(cfd.getPrize())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("??????????????????,?????????????????????????????????");
										return;
									}
								}
								if(ConfigFirstchargeDetailType.ADD.equals(cfd.getType())) {//??????
									ActivityInfoFirstchargePrize aifp = new ActivityInfoFirstchargePrize();
									aifp.setUuid(UUID.randomUUID().toString());
									aifp.setStatus(ActivityInfoFirstchargePrizeStatus.DISABLE);//???????????????
									if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
										aifp.setStatus(ActivityInfoFirstchargePrizeStatus.NORMAL);//???????????????
									}
									aifp.setCreatetime(new Timestamp(System.currentTimeMillis()));
									aifp.setActivityInfo(cfd.getActivityInfo());
									aifp.setCreator(admin);
									
									aifp.setAmount(amount);
									if(!Utlity.checkStringNull(cfd.getCapitalAccount())) {
										aifp.setCapitalAccount(cfd.getCapitalAccount());
									}
									
									aifp.setPrize(cfd.getPrize());
									
									aifp.setPrizeType(cfd.getPrizeType());
									aifp.setSort(cfd.getSort());
									listAdd.add(aifp);
								} else if(ConfigFirstchargeDetailType.EDIT.equals(cfd.getType())) {//??????
									ActivityInfoFirstchargePrize aifp = this.activityInfoFirstchargePrizeDao.get(cfd.getUuid());
									if(aifp == null || ActivityInfoFirstchargePrizeStatus.DELETE.equals(aifp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("????????????????????????");
										return;
									}
									
									aifp.setAmount(amount);
									if(!Utlity.checkStringNull(cfd.getCapitalAccount())) {
										aifp.setCapitalAccount(cfd.getCapitalAccount());
									} else {
										aifp.setCapitalAccount(null);
									}
									
									aifp.setPrize(cfd.getPrize());
									
									aifp.setPrizeType(cfd.getPrizeType());
									aifp.setSort(cfd.getSort());
									listEdit.add(aifp);
								} else if(ConfigFirstchargeDetailType.DELETE.equals(cfd.getType())) {//??????
									ActivityInfoFirstchargePrize aifp = this.activityInfoFirstchargePrizeDao.get(cfd.getUuid());
									if(aifp == null || ActivityInfoFirstchargePrizeStatus.DELETE.equals(aifp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("????????????????????????");
										return;
									}
									aifp.setStatus(ActivityInfoFirstchargePrizeStatus.DELETE);
									listDelete.add(aifp);
								} 
								
							} else {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????");
								return;
							}
						}
						editMap.put("add", listAdd);
						editMap.put("edit", listEdit);
						editMap.put("delete", listDelete);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("???????????????????????????");
					}
					this.activityInfoDao.editFirstcharge(ai, editMap);
				} else if (ActivityInfoName.RECHARGE.equals(name)) {
					try {
						List<ActivityInfoRechargePrize> listAdd = new ArrayList<ActivityInfoRechargePrize>();
						List<ActivityInfoRechargePrize> listEdit = new ArrayList<ActivityInfoRechargePrize>();
						List<ActivityInfoRechargePrize> listDelete = new ArrayList<ActivityInfoRechargePrize>();
						List<ConfigRechargeDetail> listDetail = JSONUtils.json2list(details, ConfigRechargeDetail.class);
						for(ConfigRechargeDetail crd : listDetail) {
							if(crd != null && !Utlity.checkStringNull(crd.getType())) {
								
								if(Utlity.checkStringNull(crd.getCapitalAccount())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("????????????????????????");
									return;
								}
								CapitalAccount ca = this.capitalAccountDao.get(crd.getCapitalAccount());
								if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("????????????????????????????????????????????????");
									return;
								}
								
								if(Utlity.checkStringNull(crd.getPrizeType()) || Utlity.checkStringNull(crd.getPrize())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("??????????????????");
									return;
								}
								
								if(ActivityInfoRechargePrizeType.VOUCHER.equals(crd.getPrizeType())) {
									
									String[] vs = crd.getPrize().split(",");
									for(String voucher : vs) {
										Voucher v = this.voucherDao.get(voucher);
										if(v == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("?????????????????????????????????????????????");
											return;
										}
									}
									
								} else if(ActivityInfoRechargePrizeType.GOLD.equals(crd.getPrizeType())) {
									if(!Utlity.isNumeric(crd.getPrize())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("??????????????????,?????????????????????????????????");
										return;
									}
								}
								if(ConfigRechargeDetailType.ADD.equals(crd.getType())) {//??????
									ActivityInfoRechargePrize airp = new ActivityInfoRechargePrize();
									airp.setUuid(UUID.randomUUID().toString());
									airp.setStatus(ActivityInfoRechargePrizeStatus.DISABLE);//???????????????
									if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
										airp.setStatus(ActivityInfoRechargePrizeStatus.NORMAL);//???????????????
									}
									airp.setCreatetime(new Timestamp(System.currentTimeMillis()));
									airp.setActivityInfo(crd.getActivityInfo());
									airp.setCreator(admin);
									airp.setCapitalAccount(crd.getCapitalAccount());
									
									airp.setPrize(crd.getPrize());
									
									airp.setPrizeType(crd.getPrizeType());
									airp.setSort(crd.getSort());
									listAdd.add(airp);
								} else if(ConfigRechargeDetailType.EDIT.equals(crd.getType())) {//??????
									ActivityInfoRechargePrize airp = this.activityInfoRechargePrizeDao.get(crd.getUuid());
									if(airp == null || ActivityInfoRechargePrizeStatus.DELETE.equals(airp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("????????????????????????");
										return;
									}
									
									airp.setCapitalAccount(crd.getCapitalAccount());
									
									airp.setPrize(crd.getPrize());
									
									airp.setPrizeType(crd.getPrizeType());
									airp.setSort(crd.getSort());
									listEdit.add(airp);
								} else if(ConfigRechargeDetailType.DELETE.equals(crd.getType())) {//??????
									ActivityInfoRechargePrize airp = this.activityInfoRechargePrizeDao.get(crd.getUuid());
									if(airp == null || ActivityInfoRechargePrizeStatus.DELETE.equals(airp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("????????????????????????");
										return;
									}
									airp.setStatus(ActivityInfoRechargePrizeStatus.DELETE);
									listDelete.add(airp);
								} 
								
							} else {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????");
								return;
							}
						}
						editMap.put("add", listAdd);
						editMap.put("edit", listEdit);
						editMap.put("delete", listDelete);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("???????????????????????????");
					}
					this.activityInfoDao.editRecharge(ai, editMap);
				} else if (ActivityInfoName.RECOMMEND.equals(name)) {
					try {
						Map<String, Object> detailMap = JSONUtils.json2map(details);
						if(detailMap.get("awardRate") == null || detailMap.get("registUrl") == null || detailMap.get("voucher") == null){
							result.setStatus(ResultStatusType.FAILED);
							result.setMessage("?????????????????????");
							return;
						}
						String awardRate = detailMap.get("awardRate").toString();
						if(!Utlity.isNumeric(awardRate)){
							result.setStatus(ResultStatusType.FAILED);
							result.setMessage("???????????????????????????");
							return;
						}
						String voucher = detailMap.get("voucher").toString();
						String[] vouchers = voucher.split(",");
						for(String vou : vouchers){
							Voucher v = this.voucherDao.get(vou);
							if(v == null || !VoucherStatus.NORMAL.equals(v.getStatus())){
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("?????????????????????");
								return;
							}
						}
						
						ai.setConfiguration(details);
						
						this.activityInfoDao.update(ai);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("???????????????????????????");
					}
				}
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("????????????");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(e.getMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????");
			return;
		}
	}

	/**
	 * ??????/??????
	 */
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		try {
			if(!ActivityInfoStatus.NORMAL.equals(status) && !ActivityInfoStatus.DISABLE.equals(status)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????");
				return;
			}
			
			//??????????????????
			ActivityInfo ai = activityInfoDao.get(name);
			if(ai != null && name.equals(ai.getName()) && !ActivityInfoStatus.DELETE.equals(ai.getStatus())){
				if(ActivityInfoName.BUYFREE.equals(name)) {//0??????
					ai.setStatus(status);
					//???????????????????????????????????????
					List<ActivityInfoBuyfreeGoods> list = this.activityInfoBuyfreeGoodsDao.getListByActivityInfo(ai.getName());
					if(list == null || list.size() <= 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("?????????????????????");
						return;
					}
					Map<String, Object> editMap = new HashMap<String, Object>();
					//????????????????????????????????????????????????????????????????????????
					if(ActivityInfoStatus.NORMAL.equals(status)) {//??????????????????????????????????????????
						List<ActivityInfoBuyfreeGoods> listEdit = new ArrayList<ActivityInfoBuyfreeGoods>();
						List<ActivityInfoBuyfree> listIssueAdd = new ArrayList<ActivityInfoBuyfree>();
						for(ActivityInfoBuyfreeGoods aibg : list) {
							if(ActivityInfoBuyfreeGoodsStatus.NORMAL.equals(aibg.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????????????????");
								return;
							}
							aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.NORMAL);
							//???????????????????????????
							Integer issueNum = 0;//?????????????????????
							boolean isNext = true;//?????????????????????
							//????????????
							//1.???????????????????????????
							Map<String, Object> searchMap = new HashMap<String, Object>();
							searchMap.put("activityInfoBuyfreeGoods", aibg.getUuid());
							searchMap.put("sort", "issue_num desc");
							List<ActivityInfoBuyfree> listIssue = this.activityInfoBuyfreeDao.getListByParams(searchMap);
							if(listIssue != null && listIssue.size() > 0) {
								for(ActivityInfoBuyfree aib : listIssue) {
									if(LuckygameGoodsIssueStatus.BETTING.equals(aib.getStatus())) {
										isNext = false;
									}
								}
								issueNum = listIssue.get(0).getIssueNum();
							}
							
							if(isNext) {
								//??????????????????
								aibg.setCurrentIssueNum(issueNum + 1);
								//??????????????????
								ActivityInfoBuyfree aib = new ActivityInfoBuyfree();
								aib.setUuid(UUID.randomUUID().toString());
								aib.setActivityInfo(ai.getName());
								aib.setActivityInfoBuyfreeGoods(aibg.getUuid());
								aib.setBetShares(0);
								aib.setCreatetime(new Timestamp(System.currentTimeMillis()));
								aib.setGoodsCover(aibg.getGoodsCover());
								aib.setGoodsId(aibg.getGoodsId());
								aib.setGoodsPrice(aibg.getGoodsPrice());
								aib.setGoodsTitle(aibg.getGoodsTitle());
								aib.setGoodsShortTitle(aibg.getGoodsShortTitle());
								aib.setIssueNum(aibg.getCurrentIssueNum());
								aib.setRemainShares(aibg.getShares());
								aib.setShares(aibg.getShares());
								aib.setSort(aibg.getSort());
								aib.setStatus(ActivityInfoBuyfreeStatus.BETTING);
								listIssueAdd.add(aib);
							}
							
							listEdit.add(aibg);
						}

						editMap.put("edit", listEdit);
						editMap.put("issueAdd", listIssueAdd);
						
					} else if(ActivityInfoStatus.DISABLE.equals(status)) {//????????????????????????????????????????????????
						List<ActivityInfoBuyfreeGoods> listEdit = new ArrayList<ActivityInfoBuyfreeGoods>();
						for(ActivityInfoBuyfreeGoods aibg : list) {
							if(ActivityInfoBuyfreeGoodsStatus.DISABLE.equals(aibg.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("??????????????????");
								return;
							}
							aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.DISABLE);
							listEdit.add(aibg);
						}
						
						editMap.put("edit", listEdit);
					}
					this.activityInfoDao.editBuyfree(ai, editMap);
				} else if (ActivityInfoName.CHECKIN.equals(name)) {

					ai.setStatus(status);
					//???????????????????????????????????????
					List<ActivityInfoCheckinPrize> list = this.activityInfoCheckinPrizeDao.getListByActivityInfo(ai.getName());
					if(list == null || list.size() <= 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("?????????????????????");
						return;
					}
					Map<String, Object> editMap = new HashMap<String, Object>();
					//????????????????????????????????????????????????????????????????????????
					if(ActivityInfoStatus.NORMAL.equals(status) || ActivityInfoStatus.DISABLE.equals(status)) {
						List<ActivityInfoCheckinPrize> listEdit = new ArrayList<ActivityInfoCheckinPrize>();
						for(ActivityInfoCheckinPrize aicp : list) {
							if(ActivityInfoStatus.NORMAL.equals(status) && ActivityInfoCheckinPrizeStatus.NORMAL.equals(aicp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????????????????");
								return;
							}
							if(ActivityInfoStatus.DISABLE.equals(status) && ActivityInfoCheckinPrizeStatus.DISABLE.equals(aicp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????????????????");
								return;
							}
							aicp.setStatus(status);
							listEdit.add(aicp);
						}
						editMap.put("edit", listEdit);
					} else {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("?????????????????????");
						return;
					}
					this.activityInfoDao.editCheckin(ai, editMap);
				} else if (ActivityInfoName.SCORELOTTERY.equals(name)) {

					ai.setStatus(status);
					//???????????????????????????????????????
					List<ActivityInfoScorelotteryPrize> list = this.activityInfoScorelotteryPrizeDao.getListByActivityInfo(ai.getName());
					if(list == null || list.size() <= 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("?????????????????????");
						return;
					}
					Map<String, Object> editMap = new HashMap<String, Object>();
					//????????????????????????????????????????????????????????????????????????
					if(ActivityInfoStatus.NORMAL.equals(status) || ActivityInfoStatus.DISABLE.equals(status)) {
						List<ActivityInfoScorelotteryPrize> listEdit = new ArrayList<ActivityInfoScorelotteryPrize>();
						for(ActivityInfoScorelotteryPrize aisp : list) {
							if(ActivityInfoStatus.NORMAL.equals(status) && ActivityInfoScorelotteryPrizeStatus.NORMAL.equals(aisp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????????????????");
								return;
							}
							if(ActivityInfoStatus.DISABLE.equals(status) && ActivityInfoScorelotteryPrizeStatus.DISABLE.equals(aisp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????????????????");
								return;
							}
							aisp.setStatus(status);
							listEdit.add(aisp);
						}
						editMap.put("edit", listEdit);
					} else {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("?????????????????????");
						return;
					}
					this.activityInfoDao.editScorelottery(ai, editMap);
				} else if (ActivityInfoName.FIRSTCHARGE.equals(name)) {

					ai.setStatus(status);
					//???????????????????????????????????????
					List<ActivityInfoFirstchargePrize> list = this.activityInfoFirstchargePrizeDao.getListByActivityInfo(ai.getName());
					if(list == null || list.size() <= 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("?????????????????????");
						return;
					}
					Map<String, Object> editMap = new HashMap<String, Object>();
					//????????????????????????????????????????????????????????????????????????
					if(ActivityInfoStatus.NORMAL.equals(status) || ActivityInfoStatus.DISABLE.equals(status)) {
						List<ActivityInfoFirstchargePrize> listEdit = new ArrayList<ActivityInfoFirstchargePrize>();
						for(ActivityInfoFirstchargePrize aifp : list) {
							if(ActivityInfoStatus.NORMAL.equals(status) && ActivityInfoFirstchargePrizeStatus.NORMAL.equals(aifp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????????????????");
								return;
							}
							if(ActivityInfoStatus.DISABLE.equals(status) && ActivityInfoFirstchargePrizeStatus.DISABLE.equals(aifp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????????????????");
								return;
							}
							aifp.setStatus(status);
							listEdit.add(aifp);
						}
						editMap.put("edit", listEdit);
					} else {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("?????????????????????");
						return;
					}
					this.activityInfoDao.editFirstcharge(ai, editMap);
				} else if (ActivityInfoName.RECHARGE.equals(name)) {

					ai.setStatus(status);
					//???????????????????????????????????????
					List<ActivityInfoRechargePrize> list = this.activityInfoRechargePrizeDao.getListByActivityInfo(ai.getName());
					if(list == null || list.size() <= 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("???????????????????????????");
						return;
					}
					Map<String, Object> editMap = new HashMap<String, Object>();
					//????????????????????????????????????????????????????????????????????????
					if(ActivityInfoStatus.NORMAL.equals(status) || ActivityInfoStatus.DISABLE.equals(status)) {
						List<ActivityInfoRechargePrize> listEdit = new ArrayList<ActivityInfoRechargePrize>();
						for(ActivityInfoRechargePrize airp : list) {
							if(ActivityInfoStatus.NORMAL.equals(status) && ActivityInfoRechargePrizeStatus.NORMAL.equals(airp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????????????????");
								return;
							}
							if(ActivityInfoStatus.DISABLE.equals(status) && ActivityInfoRechargePrizeStatus.DISABLE.equals(airp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("???????????????????????????????????????");
								return;
							}
							airp.setStatus(status);
							listEdit.add(airp);
						}
						editMap.put("edit", listEdit);
					} else {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("?????????????????????");
						return;
					}
					this.activityInfoDao.editRecharge(ai, editMap);
				} else if (ActivityInfoName.RECOMMEND.equals(name)) {//????????????
					ai.setStatus(status);
					this.activityInfoDao.update(ai);
				}
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("????????????");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????");
			return;
		}
	}
	
	
}
