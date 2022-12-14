package cn.product.worldmall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserPaymentOrderDao;
import cn.product.worldmall.dao.GoodsCoverImageDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.GoodsCoverImage;
import cn.product.worldmall.entity.LuckygameGoods;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.worldmall.entity.LuckygameGoods.LuckygameGoodsStatus;
import cn.product.worldmall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.worldmall.service.back.LuckygameGoodsService;
import cn.product.worldmall.vo.back.LuckygameGoodsIssueVO;
import cn.product.worldmall.vo.back.LuckygameGoodsVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("luckygameGoodsService")
public class LuckygameGoodsServiceImpl implements LuckygameGoodsService{
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private GoodsCoverImageDao goodsCoverImageDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private LuckygameGoodsDao luckygameGoodsDao;
	
	@Autowired
	private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
	private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String gameType = paramsMap.get("gameType") == null ? "" : paramsMap.get("gameType").toString();
		String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("names", name);
		searchMap.put("gameType", gameType);
		searchMap.put("demoFlag", demoFlag);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = luckygameGoodsDao.getCountByParams(searchMap);
		List<LuckygameGoods> list = luckygameGoodsDao.getListByParams(searchMap);
		List<LuckygameGoodsVO> listvo = new ArrayList<LuckygameGoodsVO>();
		if(list != null && list.size() > 0) {
			for(LuckygameGoods lg : list) {
				LuckygameGoodsVO lgvo = new LuckygameGoodsVO(lg);
				Goods good = this.goodsDao.get(lg.getGoodsId());
				lgvo.setCode(good.getCode());
				lgvo.setCoverImg("");
				//???????????????
//				searchMap.clear();
//				searchMap.put("belongs", lg.getGoodsId());
//				searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
//				List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
				List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lg.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
				if(gcis != null && gcis.size() > 0) {
					Resource re = this.resourceDao.get(gcis.get(0).getImage());
					if(re != null) {
						lgvo.setCoverImg(re.getUrl());
					}
				}
				listvo.add(lgvo);
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
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	LuckygameGoods lg = luckygameGoodsDao.get(uuid);
		if(lg == null || LuckygameGoodsStatus.DELETE.equals(lg.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
		
		LuckygameGoodsVO lgvo = new LuckygameGoodsVO(lg);
		Goods good = this.goodsDao.get(lg.getGoodsId());
		lgvo.setCode(good.getCode());
		lgvo.setCoverImg("");
		//???????????????
//		Map<String, Object> searchMap = new HashMap<String, Object>();
//		searchMap.put("belongs", lg.getGoodsId());
//		searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
//		List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
		List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(lg.getGoodsId(), GoodsCoverImageType.TYPE_LIST);
		if(gcis != null && gcis.size() > 0) {
			Resource re = this.resourceDao.get(gcis.get(0).getImage());
			if(re != null) {
				lgvo.setCoverImg(re.getUrl());
			}
		}
		result.setData(lgvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void issuelist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String luckygameGoods = paramsMap.get("luckygameGoods") == null ? "" : paramsMap.get("luckygameGoods").toString();
		String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("luckygameGoods", luckygameGoods);
		searchMap.put("demoFlag", demoFlag);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = luckygameGoodsIssueDao.getCountByParams(searchMap);
		List<LuckygameGoodsIssue> list = luckygameGoodsIssueDao.getListByParams(searchMap);
		
		List<LuckygameGoodsIssueVO> listvo = new ArrayList<LuckygameGoodsIssueVO>();
		if(list != null && list.size() > 0) {
			for(LuckygameGoodsIssue lgi : list) {
				LuckygameGoodsIssueVO lgivo = new LuckygameGoodsIssueVO(lgi);
				
				//??????????????????
				LuckygameGoods lg = this.luckygameGoodsDao.get(lgi.getLuckygameGoods());
				if(lg != null) {
					lgivo.setTabs(lg.getTabs() == null ? "" : lg.getTabs());
				}
				
				//?????????????????????
				searchMap.clear();
				searchMap.put("goodsIssue", lgi.getUuid());
				searchMap.put("isLuck", 1);
				
				List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
				if(listorder != null && listorder.size() > 0) {
					FrontUserPaymentOrder fupo = listorder.get(0);
					lgivo.setShowIdStr(fupo.getFrontUserShowId()+"");
					FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
					lgivo.setNickname(fu.getNickname());
					lgivo.setdAmount(fupo.getdAmount());
					lgivo.setActualDAmount(fupo.getActualDAmount());
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

	@Override
	public void issueget(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
    	LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(uuid);
    	
    	LuckygameGoodsIssueVO lgivo = new LuckygameGoodsIssueVO(lgi);
		
    	Goods good = this.goodsDao.get(lgi.getGoodsId());
    	lgivo.setCode(good.getCode());
    	lgivo.setCoverImg("");
		//???????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("belongs", lgi.getGoodsId());
		searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
		List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
		if(gcis != null && gcis.size() > 0) {
			Resource re = this.resourceDao.get(gcis.get(0).getImage());
			if(re != null) {
				lgivo.setCoverImg(re.getUrl());
			}
		}
		
		//??????????????????
		LuckygameGoods lg = this.luckygameGoodsDao.get(lgi.getLuckygameGoods());
		if(lg != null) {
			lgivo.setTabs(lg.getTabs() == null ? "" : lg.getTabs());
		}
		
		//?????????????????????
		searchMap.clear();
		searchMap.put("goodsIssue", lgi.getUuid());
		searchMap.put("isLuck", 1);
		
		List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
		if(listorder != null && listorder.size() > 0) {
			FrontUserPaymentOrder fupo = listorder.get(0);
			lgivo.setShowIdStr(fupo.getFrontUserShowId()+"");
			FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
			lgivo.setNickname(fu.getNickname());
			lgivo.setdAmount(fupo.getdAmount());
			lgivo.setActualDAmount(fupo.getActualDAmount());
			lgivo.setPaymenttime(fupo.getCreatetime());
		}
		result.setData(lgivo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		LuckygameGoods luckygameGoods = (LuckygameGoods) paramsMap.get("luckygameGoods");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		try {
			luckygameGoods.setUuid(UUID.randomUUID().toString());
			
			Goods goods = this.goodsDao.get(luckygameGoods.getGoodsId());
			if(goods == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????");
				return;
			}
			//?????????????????????????????????/???????????????
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("goodsId", goods.getUuid());
			searchMap.put("gameType", luckygameGoods.getGameType());
			Integer count = this.luckygameGoodsDao.getCountByParams(searchMap);
			if(count != null && count > 0) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
			searchMap.clear();
			Integer sort = this.luckygameGoodsDao.getCountByParams(searchMap);
			
			luckygameGoods.setGoodsType(goods.getType());
//			luckygameGoods.setGameType(Constants.GAME_TYPE_TRADITION);//20200709????????????????????????????????????????????????????????????????????????
			luckygameGoods.setTitle(goods.getName());
			luckygameGoods.setShortTitle(goods.getShortname());
			
			luckygameGoods.setSort(sort.intValue()+1);
			luckygameGoods.setStatus(LuckygameGoodsStatus.DISABLE);//???????????????
			luckygameGoods.setCreatetime(new Timestamp(System.currentTimeMillis()));
			luckygameGoods.setCreator(admin);
			
			luckygameGoods.setCurrentIssueNum(0);
			luckygameGoods.setDemoFlag(goods.getDemoFlag());
			luckygameGoods.setInternationalInfo(goods.getInternationalInfo());
			
			this.luckygameGoodsDao.insert(luckygameGoods);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("???????????????");
		} catch (Exception e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????");
			return;
		}
		
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		LuckygameGoods luckygameGoods = (LuckygameGoods) paramsMap.get("luckygameGoods");
		
		try {
			LuckygameGoods gameGoods = this.luckygameGoodsDao.get(luckygameGoods.getUuid());
			
			if(gameGoods == null || LuckygameGoodsStatus.DELETE.equals(gameGoods.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????");
				return;
			}
			
			if(LuckygameGoodsStatus.NORMAL.equals(gameGoods.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
			
			if(!gameGoods.getGoodsId().equals(luckygameGoods.getGoodsId())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????,???????????????!");
				return;
			}
			
			Goods goods = this.goodsDao.get(luckygameGoods.getGoodsId());
			if(goods == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????");
				return;
			}
			
			gameGoods.setTitle(goods.getName());
			gameGoods.setShortTitle(goods.getShortname());
			
			gameGoods.setdPrice(luckygameGoods.getdPrice());
			gameGoods.setBetPerShare(luckygameGoods.getBetPerShare());
			gameGoods.setProfitRate(luckygameGoods.getProfitRate());
			gameGoods.setShares(luckygameGoods.getShares());
			gameGoods.setTotalIssueNum(luckygameGoods.getTotalIssueNum());
			
			gameGoods.setDemoFlag(goods.getDemoFlag());
			gameGoods.setInternationalInfo(goods.getInternationalInfo());
			gameGoods.setTabs(luckygameGoods.getTabs());
			
			
			this.luckygameGoodsDao.update(gameGoods);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("???????????????");
		} catch (Exception e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????");
			return;
		}
	}

	/**
	 * ????????????
	 * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	 */
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		if(LuckygameGoodsStatus.DELETE.equals(status)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
		try {
			LuckygameGoods lg = luckygameGoodsDao.get(uuid);
			if(lg == null || LuckygameGoodsStatus.DELETE.equals(lg.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("??????????????????");
				return;
			}
			
			
			if(LuckygameGoodsStatus.NORMAL.equals(status)) {//??????
				if(LuckygameGoodsStatus.NORMAL.equals(lg.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("??????????????????");
					return;
				}
				lg.setStatus(status);
				Integer issueNum = 0;//?????????????????????
				boolean isNext = true;//?????????????????????
				//????????????
				//1.???????????????????????????
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("luckygameGoods", lg.getUuid());
				searchMap.put("sort", "issue_num desc");
				List<LuckygameGoodsIssue> list = this.luckygameGoodsIssueDao.getListByParams(searchMap);
				if(list != null && list.size() > 0) {
					for(LuckygameGoodsIssue lgi : list) {
						if(LuckygameGoodsIssueStatus.BETTING.equals(lgi.getStatus())) {
//							result.setStatus(ResultStatusType.FAILED);
//							result.setMessage("????????????????????????????????????????????????"+lgi.getIssueNum()+"??????");
//							return;
							isNext = false;
						}
					}
					issueNum = list.get(0).getIssueNum();
				} 
				
				if(isNext) {
					//??????????????????
					lg.setCurrentIssueNum(issueNum + 1);
					//????????????????????????
					LuckygameGoodsIssue lgi = new LuckygameGoodsIssue();
					lgi.setUuid(UUID.randomUUID().toString());
					lgi.setLuckygameGoods(lg.getUuid());
					lgi.setGoodsId(lg.getGoodsId());
					lgi.setGoodsType(lg.getGoodsType());
					lgi.setGameType(lg.getGameType());
					lgi.setTitle(lg.getTitle());
					lgi.setShortTitle(lg.getShortTitle());
					lgi.setdPrice(lg.getdPrice());
					lgi.setProfitRate(lg.getProfitRate());
					lgi.setSort(lg.getSort());
					lgi.setPromotionFlag(lg.getPromotionFlag());
					
					lgi.setCreatetime(new Timestamp(System.currentTimeMillis()));
					lgi.setStatus(LuckygameGoodsIssueStatus.BETTING);
					lgi.setShares(lg.getShares());
					lgi.setBetPerShare(lg.getBetPerShare());
					lgi.setBetShares(0);
					lgi.setRemainShares(lg.getShares());
					
					lgi.setIssueNum(lg.getCurrentIssueNum());//????????????

					lgi.setDemoFlag(lg.getDemoFlag());
					lgi.setInternationalInfo(lg.getInternationalInfo());
					
					/*
					 * ???????????????????????????????????????????????????
					 */
//					lgi.setLotterytime(lgi.getCreatetime());
//					lgi.setFinishedtime(lgi.getCreatetime());
					this.luckygameGoodsDao.updateStatus(lg, lgi);
				} else {
					this.luckygameGoodsDao.update(lg);
				}
				
			} else if (LuckygameGoodsStatus.DISABLE.equals(status)) {//?????? - ???????????????????????????????????????
				if(LuckygameGoodsStatus.DISABLE.equals(lg.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("??????????????????");
					return;
				}
				lg.setStatus(status);
				this.luckygameGoodsDao.update(lg);
			}
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????");
			return;
		}
		
	}

	/**
	 * ????????????
	 * ??????????????????-?????????????????????
	 */
	@Override
	public void sorts(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		
		try {
			LuckygameGoods lg = luckygameGoodsDao.get(uuid);
			if(lg == null || LuckygameGoodsStatus.DELETE.equals(lg.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
				return;
			}
			
			//??????????????????
			//up ?????? down ?????? top ??????
			if(!"top".equals(type) && !"up".equals(type) && !"down".equals(type)) {//??????
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????");
				return;
			} 
			
			//????????????????????????????????????????????????????????? ????????????????????????
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("sorttype", type);
			searchMap.put("luckygameGoods", lg.getUuid());
			String[] statusArr = {LuckygameGoodsIssueStatus.BETTING,LuckygameGoodsIssueStatus.LOTTERY};
			searchMap.put("status", statusArr);
			searchMap.put("sort", lg.getSort());
			this.luckygameGoodsDao.updateSort(lg, searchMap);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????");
			return;
		}
		
	}

	/**
	 * ??????
	 * ??????????????????????????????????????????
	 * ???????????????????????????
	 */
	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();		
		try {
			LuckygameGoods lg = luckygameGoodsDao.get(uuid);
			if(lg == null || LuckygameGoodsStatus.DELETE.equals(lg.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
				return;
			}
			
			lg.setStatus(LuckygameGoodsStatus.DELETE);
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("luckygameGoods", lg.getUuid());
//			List<LuckygameGoodsIssue> list = this.luckygameGoodsIssueDao.getListByParams(searchMap);
			List<LuckygameGoodsIssue> list = null;
			
			this.luckygameGoodsDao.updateStatus(lg,list);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("???????????????");
		} catch (Exception e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????");
			return;
		}
	}
}
