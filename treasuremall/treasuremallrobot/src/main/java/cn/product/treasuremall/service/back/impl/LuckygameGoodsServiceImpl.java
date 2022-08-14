package cn.product.treasuremall.service.back.impl;

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
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.GoodsCoverImageDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.GoodsCoverImage;
import cn.product.treasuremall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.entity.LuckygameGoods.LuckygameGoodsStatus;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.service.back.LuckygameGoodsService;
import cn.product.treasuremall.vo.back.LuckygameGoodsIssueVO;
import cn.product.treasuremall.vo.back.LuckygameGoodsVO;

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
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("names", name);
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
				//奖品封面图
				searchMap.clear();
				searchMap.put("belongs", lg.getGoodsId());
				searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
				List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
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
			result.setMessage("奖品不存在");
			return;
		}
		
		LuckygameGoodsVO lgvo = new LuckygameGoodsVO(lg);
		Goods good = this.goodsDao.get(lg.getGoodsId());
		lgvo.setCode(good.getCode());
		lgvo.setCoverImg("");
		//奖品封面图
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("belongs", lg.getGoodsId());
		searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
		List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
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
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("luckygameGoods", luckygameGoods);
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
				
				//封装中奖人信息
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
		//奖品封面图
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
		
		//封装中奖人信息
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
				result.setMessage("奖品信息有误！");
				return;
			}
			//判断商品是否已存在期数/或已上架过
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("goodsId", goods.getUuid());
			Integer count = this.luckygameGoodsDao.getCountByParams(searchMap);
			if(count != null && count > 0) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("抽奖奖品已存在！");
				return;
			}
			searchMap.clear();
			Integer sort = this.luckygameGoodsDao.getCountByParams(searchMap);
			
			luckygameGoods.setGoodsType(goods.getType());
			luckygameGoods.setGameType(Constants.GAME_TYPE_TRADITION);
			luckygameGoods.setTitle(goods.getName());
			luckygameGoods.setShortTitle(goods.getShortname());
			
			luckygameGoods.setSort(sort.intValue()+1);
			luckygameGoods.setStatus(LuckygameGoodsStatus.DISABLE);//初始未上架
			luckygameGoods.setCreatetime(new Timestamp(System.currentTimeMillis()));
			luckygameGoods.setCreator(admin);
			
			luckygameGoods.setCurrentIssueNum(0);
			
			this.luckygameGoodsDao.insert(luckygameGoods);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("添加成功！");
		} catch (Exception e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常");
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
				result.setMessage("抽奖信息不存在");
				return;
			}
			
			if(LuckygameGoodsStatus.NORMAL.equals(gameGoods.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("抽奖信息状态异常");
				return;
			}
			
			if(!gameGoods.getGoodsId().equals(luckygameGoods.getGoodsId())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("抽奖商品选择错误,请重新选择!");
				return;
			}
			
			Goods goods = this.goodsDao.get(luckygameGoods.getGoodsId());
			if(goods == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("奖品信息有误！");
				return;
			}
			
			gameGoods.setTitle(goods.getName());
			gameGoods.setShortTitle(goods.getShortname());
			
			gameGoods.setdPrice(luckygameGoods.getdPrice());
			gameGoods.setBetPerShare(luckygameGoods.getBetPerShare());
			gameGoods.setProfitRate(luckygameGoods.getProfitRate());
			gameGoods.setShares(luckygameGoods.getShares());
			gameGoods.setTotalIssueNum(luckygameGoods.getTotalIssueNum());
			
			this.luckygameGoodsDao.update(gameGoods);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("修改成功！");
		} catch (Exception e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常");
			return;
		}
	}

	/**
	 * 上架下架
	 * 初步设计，在上架时开始生成前端抽奖商品记录和相关中奖号码，下架时，对应修改相关状态
	 */
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		if(LuckygameGoodsStatus.DELETE.equals(status)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("参数错误！");
			return;
		}
		try {
			LuckygameGoods lg = luckygameGoodsDao.get(uuid);
			if(lg == null || LuckygameGoodsStatus.DELETE.equals(lg.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("奖品不存在！");
				return;
			}
			
			
			if(LuckygameGoodsStatus.NORMAL.equals(status)) {//上架
				if(LuckygameGoodsStatus.NORMAL.equals(lg.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("奖品已上架！");
					return;
				}
				lg.setStatus(status);
				Integer issueNum = 0;//初始化当前期数
				boolean isNext = true;//是否开展下一期
				//上架操作
				//1.查询是否有相关期数
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("luckygameGoods", lg.getUuid());
				searchMap.put("sort", "issue_num desc");
				List<LuckygameGoodsIssue> list = this.luckygameGoodsIssueDao.getListByParams(searchMap);
				if(list != null && list.size() > 0) {
					for(LuckygameGoodsIssue lgi : list) {
						if(LuckygameGoodsIssueStatus.BETTING.equals(lgi.getStatus())) {
//							result.setStatus(ResultStatusType.FAILED);
//							result.setMessage("当前奖品有未结束的抽奖，当前为第"+lgi.getIssueNum()+"期！");
//							return;
							isNext = false;
						}
					}
					issueNum = list.get(0).getIssueNum();
				} 
				
				if(isNext) {
					//设置当前期数
					lg.setCurrentIssueNum(issueNum + 1);
					//封装抽奖期数信息
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
					
					lgi.setIssueNum(lg.getCurrentIssueNum());//当前期数
					
					/*
					 * 开奖中和已结束的两个时间应该是可空
					 */
//					lgi.setLotterytime(lgi.getCreatetime());
//					lgi.setFinishedtime(lgi.getCreatetime());
					this.luckygameGoodsDao.updateStatus(lg, lgi);
				} else {
					this.luckygameGoodsDao.update(lg);
				}
				
			} else if (LuckygameGoodsStatus.DISABLE.equals(status)) {//下架 - 是否结束进行中的抽奖期数？
				if(LuckygameGoodsStatus.DISABLE.equals(lg.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("奖品已下架！");
					return;
				}
				lg.setStatus(status);
				this.luckygameGoodsDao.update(lg);
			}
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常");
			return;
		}
		
	}

	/**
	 * 排序功能
	 * 抽奖奖品排序-应对到每期抽奖
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
				result.setMessage("奖品不存在");
				return;
			}
			
			//处理排序逻辑
			//up 向上 down 向下 top 置顶
			if(!"top".equals(type) && !"up".equals(type) && !"down".equals(type)) {//置顶
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("参数错误");
				return;
			} 
			
			//查询当前奖品是否存在进行中的抽奖并更新 需不影响前端数据
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("sorttype", type);
			searchMap.put("luckygameGoods", lg.getUuid());
			String[] statusArr = {LuckygameGoodsIssueStatus.BETTING,LuckygameGoodsIssueStatus.LOTTERY};
			searchMap.put("status", statusArr);
			searchMap.put("sort", lg.getSort());
			this.luckygameGoodsDao.updateSort(lg, searchMap);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常");
			return;
		}
		
	}

	/**
	 * 删除
	 * 是否需要对每期抽奖进行删除？
	 * 目前是全部逻辑删除
	 */
	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();		
		try {
			LuckygameGoods lg = luckygameGoodsDao.get(uuid);
			if(lg == null || LuckygameGoodsStatus.DELETE.equals(lg.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("奖品不存在");
				return;
			}
			
			lg.setStatus(LuckygameGoodsStatus.DELETE);
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("luckygameGoods", lg.getUuid());
//			List<LuckygameGoodsIssue> list = this.luckygameGoodsIssueDao.getListByParams(searchMap);
			List<LuckygameGoodsIssue> list = null;
			
			this.luckygameGoodsDao.updateStatus(lg,list);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("操作成功！");
		} catch (Exception e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常");
			return;
		}
	}
}
