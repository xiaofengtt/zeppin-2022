package cn.product.worldmall.service.back.impl;

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
import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.ActivityInfoBuyfreeDao;
import cn.product.worldmall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.worldmall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.worldmall.dao.ActivityInfoDao;
import cn.product.worldmall.dao.ActivityInfoFirstchargePrizeDao;
import cn.product.worldmall.dao.ActivityInfoRechargePrizeDao;
import cn.product.worldmall.dao.ActivityInfoScorelotteryPrizeDao;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.VoucherDao;
import cn.product.worldmall.entity.ActivityInfo;
import cn.product.worldmall.entity.ActivityInfoBuyfree;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize;
import cn.product.worldmall.entity.ActivityInfoFirstchargePrize;
import cn.product.worldmall.entity.ActivityInfoRechargePrize;
import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.Voucher;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoName;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.worldmall.entity.ActivityInfoBuyfree.ActivityInfoBuyfreeStatus;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods.ActivityInfoBuyfreeGoodsStatus;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeStatus;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeType;
import cn.product.worldmall.entity.ActivityInfoFirstchargePrize.ActivityInfoFirstchargePrizeStatus;
import cn.product.worldmall.entity.ActivityInfoFirstchargePrize.ActivityInfoFirstchargePrizeType;
import cn.product.worldmall.entity.ActivityInfoRechargePrize.ActivityInfoRechargePrizeStatus;
import cn.product.worldmall.entity.ActivityInfoRechargePrize.ActivityInfoRechargePrizeType;
import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize.ActivityInfoScorelotteryPrizeStatus;
import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize.ActivityInfoScorelotteryPrizeType;
import cn.product.worldmall.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.worldmall.entity.Goods.GoodsStatus;
import cn.product.worldmall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.worldmall.entity.Voucher.VoucherStatus;
import cn.product.worldmall.entity.activity.ConfigBuyfree;
import cn.product.worldmall.entity.activity.ConfigBuyfreeDetail;
import cn.product.worldmall.entity.activity.ConfigCheckinDetail;
import cn.product.worldmall.entity.activity.ConfigFirstchargeDetail;
import cn.product.worldmall.entity.activity.ConfigRechargeDetail;
import cn.product.worldmall.entity.activity.ConfigScorelottery;
import cn.product.worldmall.entity.activity.ConfigScorelotteryDetail;
import cn.product.worldmall.entity.activity.ConfigBuyfreeDetail.ConfigBuyFreeDetailType;
import cn.product.worldmall.entity.activity.ConfigCheckinDetail.ConfigCheckinDetailType;
import cn.product.worldmall.entity.activity.ConfigFirstchargeDetail.ConfigFirstchargeDetailType;
import cn.product.worldmall.entity.activity.ConfigRechargeDetail.ConfigRechargeDetailType;
import cn.product.worldmall.entity.activity.ConfigScorelotteryDetail.ConfigScorelotteryDetailType;
import cn.product.worldmall.service.back.ActivityInfoService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.ActivityInfoVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

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
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的活动信息的总数
		Integer totalResultCount = activityInfoDao.getCountByParams(searchMap);
		//查询符合条件的活动信息列表
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
		//获取活动信息
		ActivityInfo ai = activityInfoDao.get(name);
		if (ai == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
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
		
		//config信息
		String configuration = paramsMap.get("configuration") == null ? "" : paramsMap.get("configuration").toString();//JSON字符串
		String details =  paramsMap.get("details") == null ? "" : paramsMap.get("details").toString();
		
		if(Utlity.checkStringNull(starttime) || Utlity.checkStringNull(endtime)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("清填写正确的活动有效期！");
			return;
		}
		
		if(details == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请确认详细设置信息！");
			return;
		}
		
		try {
			//获取活动信息
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
				
				//根据活动类型校验格式
				if(ActivityInfoName.BUYFREE.equals(name)) {//0元购
					if(Utlity.checkStringNull(configuration)) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("请完整填写活动基础设置信息！");
						return;
					}
					try {
						ConfigBuyfree cbf = JSONUtils.json2obj(configuration, ConfigBuyfree.class);
						if(cbf != null && cbf.getTimesLine() != null && cbf.getTimesLine() > 0) {
							ai.setConfiguration(configuration);
						} else {
							throw new TransactionException("参与次数配置信息错误！");
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("参与次数配置信息错误！");
					}
					
					try {
						List<ActivityInfoBuyfreeGoods> listAdd = new ArrayList<ActivityInfoBuyfreeGoods>();
						List<ActivityInfoBuyfreeGoods> listEdit = new ArrayList<ActivityInfoBuyfreeGoods>();
						List<ActivityInfoBuyfreeGoods> listDelete = new ArrayList<ActivityInfoBuyfreeGoods>();
						List<ConfigBuyfreeDetail> listDetail = JSONUtils.json2list(details, ConfigBuyfreeDetail.class);
						for(ConfigBuyfreeDetail cbd : listDetail) {
							//detail 格式参数uuid,activityInfo,status,goodsId,goodsCover,shares,sort
//								ConfigBuyfreeDetail cbd = JSONUtils.json2obj(detail, ConfigBuyfreeDetail.class);
							//判断type
							if(cbd != null && !Utlity.checkStringNull(cbd.getType())) {
								
								if(Utlity.checkStringNull(cbd.getGoodsId())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("未选择奖品！");
									return;
								}
								if(Utlity.checkStringNull(cbd.getGoodsCover())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("未上传奖品封面！");
									return;
								}
								if(cbd.getShares() == null || cbd.getShares() == 0) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("未设置总需人次！");
									return;
								}
								
								Goods g = this.goodsDao.get(cbd.getGoodsId());
								if(g == null || GoodsStatus.DELETE.equals(g.getStatus())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("奖品不存在！");
									return;
								}
								if(ConfigBuyFreeDetailType.ADD.equals(cbd.getType())) {//添加
									ActivityInfoBuyfreeGoods aibg = new ActivityInfoBuyfreeGoods();
									aibg.setUuid(UUID.randomUUID().toString());
									aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.DISABLE);//默认不上线
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
									
									//活动开启状态，直接上架
									if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {//将当前已添加的商品做上架操作
										List<ActivityInfoBuyfree> listIssueAdd = new ArrayList<ActivityInfoBuyfree>();
										if(ActivityInfoBuyfreeGoodsStatus.NORMAL.equals(aibg.getStatus())) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("设置已生效，不要重复操作！");
											return;
										}
										aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.NORMAL);
										//判断是否有期数存在
										Integer issueNum = 0;//初始化当前期数
										boolean isNext = true;//是否开展下一期
										//上架操作
										
										if(isNext) {
											//设置当前期数
											aibg.setCurrentIssueNum(issueNum + 1);
											//封装期数信息
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
								} else if(ConfigBuyFreeDetailType.EDIT.equals(cbd.getType())) {//编辑
									ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(cbd.getUuid());
									if(aibg == null || ActivityInfoBuyfreeGoodsStatus.DELETE.equals(aibg.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖品设置不存在！");
										return;
									}
									if(!aibg.getGoodsId().equals(cbd.getGoodsId())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖品信息错误！");
										return;
									}
									aibg.setGoodsCover(cbd.getGoodsCover());
									aibg.setGoodsPrice(g.getPrice());
									aibg.setGoodsTitle(g.getName());
									aibg.setGoodsShortTitle(g.getShortname());
									aibg.setShares(cbd.getShares());
									aibg.setSort(cbd.getSort());
									listEdit.add(aibg);
								} else if(ConfigBuyFreeDetailType.DELETE.equals(cbd.getType())) {//编辑
									ActivityInfoBuyfreeGoods aibg = this.activityInfoBuyfreeGoodsDao.get(cbd.getUuid());
									if(aibg == null || ActivityInfoBuyfreeGoodsStatus.DELETE.equals(aibg.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖品设置不存在！");
										return;
									}
									aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.DELETE);
									listDelete.add(aibg);
								}
							} else {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("详细设置信息有误！");
								return;
							}
						}
						
						editMap.put("add", listAdd);
						editMap.put("edit", listEdit);
						editMap.put("delete", listDelete);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("详细设置信息错误！");
					}
					this.activityInfoDao.editBuyfree(ai, editMap);
				} else if (ActivityInfoName.CHECKIN.equals(name)) {//签到活动
					
					try {
						List<ActivityInfoCheckinPrize> listAdd = new ArrayList<ActivityInfoCheckinPrize>();
						List<ActivityInfoCheckinPrize> listEdit = new ArrayList<ActivityInfoCheckinPrize>();
						List<ActivityInfoCheckinPrize> listDelete = new ArrayList<ActivityInfoCheckinPrize>();
						List<ConfigCheckinDetail> listDetail = JSONUtils.json2list(details, ConfigCheckinDetail.class);
						for(ConfigCheckinDetail ccd : listDetail) {
							if(ccd != null && !Utlity.checkStringNull(ccd.getType())) {
								if(Utlity.checkStringNull(ccd.getPrizeTitle())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("第"+ccd.getDayNum()+"天未填写奖品名称！");
									return;
								}
								if(Utlity.checkStringNull(ccd.getPrizeType()) || Utlity.checkStringNull(ccd.getPrize())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("第"+ccd.getDayNum()+"天未选择奖品！");
									return;
								}
								if(ActivityInfoCheckinPrizeType.ENTITY.equals(ccd.getPrizeType()) || ActivityInfoCheckinPrizeType.VOUCHER.equals(ccd.getPrizeType())) {
									if(ccd.getPrize().length() != 36) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("第"+ccd.getDayNum()+"天奖品信息错误！");
										return;
									}
									if(ActivityInfoCheckinPrizeType.ENTITY.equals(ccd.getPrizeType())) {
										Goods goods = this.goodsDao.get(ccd.getPrize());
										if(goods == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("第"+ccd.getDayNum()+"天奖品信息不存在！");
											return;
										}
									} else {
										Voucher v = this.voucherDao.get(ccd.getPrize());
										if(v == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("第"+ccd.getDayNum()+"天金券信息不存在！");
											return;
										}
									}
								} else if(ActivityInfoCheckinPrizeType.GOLD.equals(ccd.getPrizeType())) {
									if(!Utlity.isNumeric(ccd.getPrize())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("第"+ccd.getDayNum()+"天奖品信息错误,请填写正确的金币数目！");
										return;
									}
								}
								if(ConfigCheckinDetailType.ADD.equals(ccd.getType())) {//添加
									ActivityInfoCheckinPrize aicp = new ActivityInfoCheckinPrize();
									aicp.setUuid(UUID.randomUUID().toString());
									aicp.setStatus(ActivityInfoCheckinPrizeStatus.DISABLE);//默认不上线
									if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
										aicp.setStatus(ActivityInfoCheckinPrizeStatus.NORMAL);//默认不上线
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
								} else if(ConfigCheckinDetailType.EDIT.equals(ccd.getType())) {//添加
									ActivityInfoCheckinPrize aicp = this.activityInfoCheckinPrizeDao.get(ccd.getUuid());
									if(aicp == null || ActivityInfoCheckinPrizeStatus.DELETE.equals(aicp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("第"+ccd.getDayNum()+"天奖品设置不存在！");
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
								} else if(ConfigCheckinDetailType.DELETE.equals(ccd.getType())) {//添加
									ActivityInfoCheckinPrize aicp = this.activityInfoCheckinPrizeDao.get(ccd.getUuid());
									if(aicp == null || ActivityInfoCheckinPrizeStatus.DELETE.equals(aicp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("第"+ccd.getDayNum()+"天奖品设置不存在！");
										return;
									}
									aicp.setStatus(ActivityInfoCheckinPrizeStatus.DELETE);
									listDelete.add(aicp);
								} 
								
							} else {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("第"+ccd.getDayNum()+"天详细设置信息有误！");
								return;
							}
						}
						editMap.put("add", listAdd);
						editMap.put("edit", listEdit);
						editMap.put("delete", listDelete);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("详细设置信息错误！");
					}
					this.activityInfoDao.editCheckin(ai, editMap);
				} else if (ActivityInfoName.SCORELOTTERY.equals(name)) {//积分抽奖
					if(Utlity.checkStringNull(configuration)) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("请完整填写活动基础设置信息！");
						return;
					}
					try {
						ConfigScorelottery cs = JSONUtils.json2obj(configuration, ConfigScorelottery.class);
						if(cs != null) {
							if(cs.getTimesLine() == null || cs.getTimesLine() <= 0) {
								throw new TransactionException("参与次数配置信息错误！");
							}
							if(cs.getScoreLine() == null || !Utlity.isNumeric(cs.getScoreLine())) {
								throw new TransactionException("积分消耗配置信息错误！");
							}
							BigDecimal de = BigDecimal.valueOf(Double.valueOf(cs.getScoreLine()));
							if(de.compareTo(BigDecimal.ZERO) <0) {//支持0积分 纯免费
								throw new TransactionException("请正确填写积分消耗配置信息！");
							}
							ai.setConfiguration(configuration);
						} else {
							throw new TransactionException("基础配置信息错误！");
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("参与次数配置信息错误！");
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
									result.setMessage("奖项"+csd.getSort()+"未填写奖品名称！");
									return;
								}
								if(Utlity.checkStringNull(csd.getPrizeType()) || Utlity.checkStringNull(csd.getPrize())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("奖项"+csd.getSort()+"未选择奖品！");
									return;
								}
								if(csd.getWinningRate() == null || !Utlity.isNumeric(csd.getWinningRate())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("奖项"+csd.getSort()+"未填写中奖率！");
									return;
								}
								BigDecimal rate = BigDecimal.valueOf(Double.valueOf(csd.getWinningRate()));
								if(rate.compareTo(BigDecimal.ZERO) <= 0) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("奖项"+csd.getSort()+"中奖率填入错误！");
									return;
								}
								if(ActivityInfoScorelotteryPrizeType.ENTITY.equals(csd.getPrizeType()) || ActivityInfoScorelotteryPrizeType.VOUCHER.equals(csd.getPrizeType())) {
									if(csd.getPrize().length() != 36) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖项"+csd.getSort()+"奖品信息错误！");
										return;
									}
									if(ActivityInfoScorelotteryPrizeType.ENTITY.equals(csd.getPrizeType())) {
										Goods goods = this.goodsDao.get(csd.getPrize());
										if(goods == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("奖项"+csd.getSort()+"奖品信息不存在！");
											return;
										}
									} else {
										Voucher v = this.voucherDao.get(csd.getPrize());
										if(v == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("奖项"+csd.getSort()+"金券信息不存在！");
											return;
										}
									}
								} else if(ActivityInfoScorelotteryPrizeType.GOLD.equals(csd.getPrizeType())) {
									if(!Utlity.isNumeric(csd.getPrize())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖项"+csd.getSort()+"奖品信息错误,请填写正确的金币数目！");
										return;
									}
								}
								if(ConfigScorelotteryDetailType.ADD.equals(csd.getType())) {//添加
									totalRate = totalRate.add(rate);
									flag = true;
									ActivityInfoScorelotteryPrize aisp = new ActivityInfoScorelotteryPrize();
									aisp.setUuid(UUID.randomUUID().toString());
									aisp.setStatus(ActivityInfoScorelotteryPrizeStatus.DISABLE);//默认不上线
									if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
										aisp.setStatus(ActivityInfoScorelotteryPrizeStatus.NORMAL);//默认不上线
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
								} else if(ConfigScorelotteryDetailType.EDIT.equals(csd.getType())) {//添加
									flag = true;
									totalRate = totalRate.add(rate);
									ActivityInfoScorelotteryPrize aisp = this.activityInfoScorelotteryPrizeDao.get(csd.getUuid());
									if(aisp == null || ActivityInfoScorelotteryPrizeStatus.DELETE.equals(aisp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖项"+csd.getSort()+"奖品设置不存在！");
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
								} else if(ConfigScorelotteryDetailType.DELETE.equals(csd.getType())) {//添加
									ActivityInfoScorelotteryPrize aisp = this.activityInfoScorelotteryPrizeDao.get(csd.getUuid());
									if(aisp == null || ActivityInfoScorelotteryPrizeStatus.DELETE.equals(aisp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖项"+csd.getSort()+"奖品设置不存在！");
										return;
									}
									aisp.setStatus(ActivityInfoScorelotteryPrizeStatus.DELETE);
									listDelete.add(aisp);
								} 
								
							} else {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("奖项"+csd.getSort()+"详细设置信息有误！");
								return;
							}
						}
						if(flag && totalRate.compareTo(BigDecimal.valueOf(100)) != 0) {
							result.setStatus(ResultStatusType.FAILED);
							result.setMessage("中奖率设置有误，合计不为100");
							return;
						}
						editMap.put("add", listAdd);
						editMap.put("edit", listEdit);
						editMap.put("delete", listDelete);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("详细设置信息错误！");
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
									result.setMessage("请填写固定额度！");
									return;
								}
								BigDecimal amount = BigDecimal.valueOf(Double.valueOf(cfd.getAmount()));
								if(amount.compareTo(BigDecimal.ZERO) <= 0) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("请正确填写固定额度！");
									return;
								}
								if(!Utlity.checkStringNull(cfd.getCapitalAccount())) {
									CapitalAccount ca = this.capitalAccountDao.get(cfd.getCapitalAccount());
									if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("所选充值渠道不存在，请重新选择！");
										return;
									}
								}
								
								if(Utlity.checkStringNull(cfd.getPrizeType()) || Utlity.checkStringNull(cfd.getPrize())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("未选择奖品！");
									return;
								}
								
								if(ActivityInfoFirstchargePrizeType.ENTITY.equals(cfd.getPrizeType()) || ActivityInfoFirstchargePrizeType.VOUCHER.equals(cfd.getPrizeType())) {
									if(ActivityInfoFirstchargePrizeType.ENTITY.equals(cfd.getPrizeType())) {
										Goods goods = this.goodsDao.get(cfd.getPrize());
										if(goods == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("奖品信息不存在！");
											return;
										}
									} else {
										String[] vs = cfd.getPrize().split(",");
										for(String voucher : vs) {
											Voucher v = this.voucherDao.get(voucher);
											if(v == null) {
												result.setStatus(ResultStatusType.FAILED);
												result.setMessage("有金券信息不存在，请重新选择！");
												return;
											}
										}
									
									}
								} else if(ActivityInfoFirstchargePrizeType.GOLD.equals(cfd.getPrizeType())) {
									if(!Utlity.isNumeric(cfd.getPrize())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖品信息错误,请填写正确的金币数目！");
										return;
									}
								}
								if(ConfigFirstchargeDetailType.ADD.equals(cfd.getType())) {//添加
									ActivityInfoFirstchargePrize aifp = new ActivityInfoFirstchargePrize();
									aifp.setUuid(UUID.randomUUID().toString());
									aifp.setStatus(ActivityInfoFirstchargePrizeStatus.DISABLE);//默认不上线
									if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
										aifp.setStatus(ActivityInfoFirstchargePrizeStatus.NORMAL);//默认不上线
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
								} else if(ConfigFirstchargeDetailType.EDIT.equals(cfd.getType())) {//添加
									ActivityInfoFirstchargePrize aifp = this.activityInfoFirstchargePrizeDao.get(cfd.getUuid());
									if(aifp == null || ActivityInfoFirstchargePrizeStatus.DELETE.equals(aifp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖品设置不存在！");
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
								} else if(ConfigFirstchargeDetailType.DELETE.equals(cfd.getType())) {//添加
									ActivityInfoFirstchargePrize aifp = this.activityInfoFirstchargePrizeDao.get(cfd.getUuid());
									if(aifp == null || ActivityInfoFirstchargePrizeStatus.DELETE.equals(aifp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖品设置不存在！");
										return;
									}
									aifp.setStatus(ActivityInfoFirstchargePrizeStatus.DELETE);
									listDelete.add(aifp);
								} 
								
							} else {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("详细设置信息有误！");
								return;
							}
						}
						editMap.put("add", listAdd);
						editMap.put("edit", listEdit);
						editMap.put("delete", listDelete);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("详细设置信息错误！");
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
									result.setMessage("请选择充值渠道！");
									return;
								}
								CapitalAccount ca = this.capitalAccountDao.get(crd.getCapitalAccount());
								if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("所选充值渠道不存在，请重新选择！");
									return;
								}
								
								if(Utlity.checkStringNull(crd.getPrizeType()) || Utlity.checkStringNull(crd.getPrize())) {
									result.setStatus(ResultStatusType.FAILED);
									result.setMessage("未选择奖品！");
									return;
								}
								
								if(ActivityInfoRechargePrizeType.VOUCHER.equals(crd.getPrizeType())) {
									
									String[] vs = crd.getPrize().split(",");
									for(String voucher : vs) {
										Voucher v = this.voucherDao.get(voucher);
										if(v == null) {
											result.setStatus(ResultStatusType.FAILED);
											result.setMessage("有金券信息不存在，请重新选择！");
											return;
										}
									}
									
								} else if(ActivityInfoRechargePrizeType.GOLD.equals(crd.getPrizeType())) {
									if(!Utlity.isNumeric(crd.getPrize())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("奖品信息错误,请填写正确的金币数目！");
										return;
									}
								}
								if(ConfigRechargeDetailType.ADD.equals(crd.getType())) {//添加
									ActivityInfoRechargePrize airp = new ActivityInfoRechargePrize();
									airp.setUuid(UUID.randomUUID().toString());
									airp.setStatus(ActivityInfoRechargePrizeStatus.DISABLE);//默认不上线
									if(ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {
										airp.setStatus(ActivityInfoRechargePrizeStatus.NORMAL);//默认不上线
									}
									airp.setCreatetime(new Timestamp(System.currentTimeMillis()));
									airp.setActivityInfo(crd.getActivityInfo());
									airp.setCreator(admin);
									airp.setCapitalAccount(crd.getCapitalAccount());
									
									airp.setPrize(crd.getPrize());
									
									airp.setPrizeType(crd.getPrizeType());
									airp.setSort(crd.getSort());
									listAdd.add(airp);
								} else if(ConfigRechargeDetailType.EDIT.equals(crd.getType())) {//添加
									ActivityInfoRechargePrize airp = this.activityInfoRechargePrizeDao.get(crd.getUuid());
									if(airp == null || ActivityInfoRechargePrizeStatus.DELETE.equals(airp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("详细设置不存在！");
										return;
									}
									
									airp.setCapitalAccount(crd.getCapitalAccount());
									
									airp.setPrize(crd.getPrize());
									
									airp.setPrizeType(crd.getPrizeType());
									airp.setSort(crd.getSort());
									listEdit.add(airp);
								} else if(ConfigRechargeDetailType.DELETE.equals(crd.getType())) {//添加
									ActivityInfoRechargePrize airp = this.activityInfoRechargePrizeDao.get(crd.getUuid());
									if(airp == null || ActivityInfoRechargePrizeStatus.DELETE.equals(airp.getStatus())) {
										result.setStatus(ResultStatusType.FAILED);
										result.setMessage("详细设置不存在！");
										return;
									}
									airp.setStatus(ActivityInfoRechargePrizeStatus.DELETE);
									listDelete.add(airp);
								} 
								
							} else {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("详细设置信息有误！");
								return;
							}
						}
						editMap.put("add", listAdd);
						editMap.put("edit", listEdit);
						editMap.put("delete", listDelete);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("详细设置信息错误！");
					}
					this.activityInfoDao.editRecharge(ai, editMap);
				} else if (ActivityInfoName.RECOMMEND.equals(name)) {
					try {
						Map<String, Object> detailMap = JSONUtils.json2map(details);
						if(detailMap.get("awardRate") == null || detailMap.get("registUrl") == null || detailMap.get("voucher") == null){
							result.setStatus(ResultStatusType.FAILED);
							result.setMessage("缺少必填数据！");
							return;
						}
						String awardRate = detailMap.get("awardRate").toString();
						if(!Utlity.isNumeric(awardRate)){
							result.setStatus(ResultStatusType.FAILED);
							result.setMessage("奖励比率填写有误！");
							return;
						}
						String voucher = detailMap.get("voucher").toString();
						String[] vouchers = voucher.split(",");
						for(String vou : vouchers){
							Voucher v = this.voucherDao.get(vou);
							if(v == null || !VoucherStatus.NORMAL.equals(v.getStatus())){
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("金券设置有误！");
								return;
							}
						}
						
						ai.setConfiguration(details);
						
						this.activityInfoDao.update(ai);
					} catch (Exception e) {
						e.printStackTrace();
						throw new TransactionException("详细设置信息错误！");
					}
				}
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("保存成功");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("该条数据不存在！");
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(e.getMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
			return;
		}
	}

	/**
	 * 开启/关闭
	 */
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		try {
			if(!ActivityInfoStatus.NORMAL.equals(status) && !ActivityInfoStatus.DISABLE.equals(status)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("状态参数有误！");
				return;
			}
			
			//获取活动信息
			ActivityInfo ai = activityInfoDao.get(name);
			if(ai != null && name.equals(ai.getName()) && !ActivityInfoStatus.DELETE.equals(ai.getStatus())){
				if(ActivityInfoName.BUYFREE.equals(name)) {//0元购
					ai.setStatus(status);
					//获取当前活动设置的所有商品
					List<ActivityInfoBuyfreeGoods> list = this.activityInfoBuyfreeGoodsDao.getListByActivityInfo(ai.getName());
					if(list == null || list.size() <= 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("请先设置奖品！");
						return;
					}
					Map<String, Object> editMap = new HashMap<String, Object>();
					//根据不同活动类型更新对应活动状态，及相关内容配置
					if(ActivityInfoStatus.NORMAL.equals(status)) {//将当前已添加的商品做上架操作
						List<ActivityInfoBuyfreeGoods> listEdit = new ArrayList<ActivityInfoBuyfreeGoods>();
						List<ActivityInfoBuyfree> listIssueAdd = new ArrayList<ActivityInfoBuyfree>();
						for(ActivityInfoBuyfreeGoods aibg : list) {
							if(ActivityInfoBuyfreeGoodsStatus.NORMAL.equals(aibg.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("设置已生效，不要重复操作！");
								return;
							}
							aibg.setStatus(ActivityInfoBuyfreeGoodsStatus.NORMAL);
							//判断是否有期数存在
							Integer issueNum = 0;//初始化当前期数
							boolean isNext = true;//是否开展下一期
							//上架操作
							//1.查询是否有相关期数
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
								//设置当前期数
								aibg.setCurrentIssueNum(issueNum + 1);
								//封装期数信息
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
						
					} else if(ActivityInfoStatus.DISABLE.equals(status)) {//关闭活动，不影响当前进行中的商品
						List<ActivityInfoBuyfreeGoods> listEdit = new ArrayList<ActivityInfoBuyfreeGoods>();
						for(ActivityInfoBuyfreeGoods aibg : list) {
							if(ActivityInfoBuyfreeGoodsStatus.DISABLE.equals(aibg.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("奖品已下架！");
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
					//获取当前活动设置的所有商品
					List<ActivityInfoCheckinPrize> list = this.activityInfoCheckinPrizeDao.getListByActivityInfo(ai.getName());
					if(list == null || list.size() <= 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("请先设置奖品！");
						return;
					}
					Map<String, Object> editMap = new HashMap<String, Object>();
					//根据不同活动类型更新对应活动状态，及相关内容配置
					if(ActivityInfoStatus.NORMAL.equals(status) || ActivityInfoStatus.DISABLE.equals(status)) {
						List<ActivityInfoCheckinPrize> listEdit = new ArrayList<ActivityInfoCheckinPrize>();
						for(ActivityInfoCheckinPrize aicp : list) {
							if(ActivityInfoStatus.NORMAL.equals(status) && ActivityInfoCheckinPrizeStatus.NORMAL.equals(aicp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("设置已生效，不要重复操作！");
								return;
							}
							if(ActivityInfoStatus.DISABLE.equals(status) && ActivityInfoCheckinPrizeStatus.DISABLE.equals(aicp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("设置已生效，不要重复操作！");
								return;
							}
							aicp.setStatus(status);
							listEdit.add(aicp);
						}
						editMap.put("edit", listEdit);
					} else {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("状态设置错误！");
						return;
					}
					this.activityInfoDao.editCheckin(ai, editMap);
				} else if (ActivityInfoName.SCORELOTTERY.equals(name)) {

					ai.setStatus(status);
					//获取当前活动设置的所有商品
					List<ActivityInfoScorelotteryPrize> list = this.activityInfoScorelotteryPrizeDao.getListByActivityInfo(ai.getName());
					if(list == null || list.size() <= 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("请先设置奖品！");
						return;
					}
					Map<String, Object> editMap = new HashMap<String, Object>();
					//根据不同活动类型更新对应活动状态，及相关内容配置
					if(ActivityInfoStatus.NORMAL.equals(status) || ActivityInfoStatus.DISABLE.equals(status)) {
						List<ActivityInfoScorelotteryPrize> listEdit = new ArrayList<ActivityInfoScorelotteryPrize>();
						for(ActivityInfoScorelotteryPrize aisp : list) {
							if(ActivityInfoStatus.NORMAL.equals(status) && ActivityInfoScorelotteryPrizeStatus.NORMAL.equals(aisp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("设置已生效，不要重复操作！");
								return;
							}
							if(ActivityInfoStatus.DISABLE.equals(status) && ActivityInfoScorelotteryPrizeStatus.DISABLE.equals(aisp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("设置已生效，不要重复操作！");
								return;
							}
							aisp.setStatus(status);
							listEdit.add(aisp);
						}
						editMap.put("edit", listEdit);
					} else {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("状态设置错误！");
						return;
					}
					this.activityInfoDao.editScorelottery(ai, editMap);
				} else if (ActivityInfoName.FIRSTCHARGE.equals(name)) {

					ai.setStatus(status);
					//获取当前活动设置的所有商品
					List<ActivityInfoFirstchargePrize> list = this.activityInfoFirstchargePrizeDao.getListByActivityInfo(ai.getName());
					if(list == null || list.size() <= 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("请先设置奖品！");
						return;
					}
					Map<String, Object> editMap = new HashMap<String, Object>();
					//根据不同活动类型更新对应活动状态，及相关内容配置
					if(ActivityInfoStatus.NORMAL.equals(status) || ActivityInfoStatus.DISABLE.equals(status)) {
						List<ActivityInfoFirstchargePrize> listEdit = new ArrayList<ActivityInfoFirstchargePrize>();
						for(ActivityInfoFirstchargePrize aifp : list) {
							if(ActivityInfoStatus.NORMAL.equals(status) && ActivityInfoFirstchargePrizeStatus.NORMAL.equals(aifp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("设置已生效，不要重复操作！");
								return;
							}
							if(ActivityInfoStatus.DISABLE.equals(status) && ActivityInfoFirstchargePrizeStatus.DISABLE.equals(aifp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("设置已生效，不要重复操作！");
								return;
							}
							aifp.setStatus(status);
							listEdit.add(aifp);
						}
						editMap.put("edit", listEdit);
					} else {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("状态设置错误！");
						return;
					}
					this.activityInfoDao.editFirstcharge(ai, editMap);
				} else if (ActivityInfoName.RECHARGE.equals(name)) {

					ai.setStatus(status);
					//获取当前活动设置的所有商品
					List<ActivityInfoRechargePrize> list = this.activityInfoRechargePrizeDao.getListByActivityInfo(ai.getName());
					if(list == null || list.size() <= 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("请先设置详细配置！");
						return;
					}
					Map<String, Object> editMap = new HashMap<String, Object>();
					//根据不同活动类型更新对应活动状态，及相关内容配置
					if(ActivityInfoStatus.NORMAL.equals(status) || ActivityInfoStatus.DISABLE.equals(status)) {
						List<ActivityInfoRechargePrize> listEdit = new ArrayList<ActivityInfoRechargePrize>();
						for(ActivityInfoRechargePrize airp : list) {
							if(ActivityInfoStatus.NORMAL.equals(status) && ActivityInfoRechargePrizeStatus.NORMAL.equals(airp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("设置已生效，不要重复操作！");
								return;
							}
							if(ActivityInfoStatus.DISABLE.equals(status) && ActivityInfoRechargePrizeStatus.DISABLE.equals(airp.getStatus())) {
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage("设置已生效，不要重复操作！");
								return;
							}
							airp.setStatus(status);
							listEdit.add(airp);
						}
						editMap.put("edit", listEdit);
					} else {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("状态设置错误！");
						return;
					}
					this.activityInfoDao.editRecharge(ai, editMap);
				} else if (ActivityInfoName.RECOMMEND.equals(name)) {//邀新活动
					ai.setStatus(status);
					this.activityInfoDao.update(ai);
				}
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("操作成功");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("该条数据不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
			return;
		}
	}
	
	
}
