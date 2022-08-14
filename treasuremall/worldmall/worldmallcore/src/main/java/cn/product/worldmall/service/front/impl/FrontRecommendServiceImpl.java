package cn.product.worldmall.service.front.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.ActivityInfoRecommendRankingDao;
import cn.product.worldmall.dao.AdminOffsetOrderDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking;
import cn.product.worldmall.entity.AdminOffsetOrder;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking.ActivityInfoRecommendRankingStatus;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.service.front.FrontRecommendService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.front.ActivityInfoRecommendRankingVO;
import cn.product.worldmall.vo.front.FrontUserAgentVO;
import cn.product.worldmall.vo.front.FrontUserRecommendHistoryVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("frontRecommendService")
public class FrontRecommendServiceImpl implements FrontRecommendService{
	
	@Autowired
	private ActivityInfoRecommendRankingDao activityInfoRecommendRankingDao;

	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private AdminOffsetOrderDao adminOffsetOrderDao;
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Override
	public void rankingList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", ActivityInfoRecommendRankingStatus.NORMAL);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		Integer totalResultCount = activityInfoRecommendRankingDao.getCountByParams(searchMap);
		List<ActivityInfoRecommendRanking> list = activityInfoRecommendRankingDao.getListByParams(searchMap);
		
		List<ActivityInfoRecommendRankingVO> listvo = new ArrayList<ActivityInfoRecommendRankingVO>();
		for(ActivityInfoRecommendRanking airr : list) {
			ActivityInfoRecommendRankingVO vo = new ActivityInfoRecommendRankingVO(airr);
			
			FrontUser fu = this.frontUserDao.get(airr.getFrontUser());
			if(fu != null){
				vo.setFrontUserName(fu.getNickname());
				vo.setFrontUserShowId(fu.getShowId());
				
				SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
				String pathUrl = "";
				if(sp != null) {
					pathUrl = sp.getParamValue();
				} else {
					pathUrl = Utlity.IMAGE_PATH_URL;
				}
				if(!Utlity.checkStringNull(fu.getImage())) {
		    		Resource re = this.resourceDao.get(fu.getImage());
		    		if(re != null) {
		    			vo.setImageURL(pathUrl + re.getUrl());
		    		}
		    	}
			}
			
			listvo.add(vo);
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}
	
	@Override
	public void agentList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		if(frontUser == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The user is not logged in!");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("agent", frontUser);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalResultCount = this.frontUserDao.getCountByParams(searchMap);
		List<FrontUser> agentList = this.frontUserDao.getListByParams(searchMap);
		
		List<FrontUserAgentVO> voList = new ArrayList<FrontUserAgentVO>();
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		
		for(FrontUser fu : agentList){
			FrontUserAgentVO vo = new FrontUserAgentVO(fu);
			
			if(!Utlity.checkStringNull(fu.getImage())) {
	    		Resource re = this.resourceDao.get(fu.getImage());
	    		if(re != null) {
	    			vo.setImageURL(pathUrl + re.getUrl());
	    		}
	    	}
			
			voList.add(vo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}

	@Override
	public void awardList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String starttime = paramsMap.get("starttime") == null ? "" : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		
		if(frontUser == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The user is not logged in!");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("orderType", Constants.ORDER_TYPE_USER_RECOMMEND);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", "createtime desc");
		
		List<FrontUserHistory> list = this.frontUserHistoryDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserHistoryDao.getCountByParams(paramsMap);
		List<FrontUserRecommendHistoryVO> listvo = new ArrayList<FrontUserRecommendHistoryVO>();
		if(list != null && list.size() > 0) {
			//获取金币汇率
			SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sprate != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
			}
			
			//地址链接
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			
			for(FrontUserHistory fuh : list) {
				FrontUserRecommendHistoryVO furhvo = new FrontUserRecommendHistoryVO(fuh);
				furhvo.setAwardAmount(fuh.getdAmount().divide(rate));//换算单位（元）
				AdminOffsetOrder aoo = this.adminOffsetOrderDao.get(fuh.getOrderId());
				if(aoo != null) {
					if(!Utlity.checkStringNull(aoo.getRechargeOrder())) {
						FrontUserRechargeOrder furo = this.frontUserRechargeOrderDao.get(aoo.getRechargeOrder());
						if(furo != null) {
							FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
							furhvo.setNickname(fu.getNickname());
							furhvo.setRecommendFrontUser(fu.getUuid());
							furhvo.setRecommendShowId(fu.getShowId());
							if(!Utlity.checkStringNull(fu.getImage())) {
					    		Resource re = this.resourceDao.get(fu.getImage());
					    		if(re != null) {
					    			furhvo.setImageURL(pathUrl + re.getUrl());
					    		}
					    	}
							furhvo.setRechargeAmount(furo.getAmount());
						}
					}
				}
				listvo.add(furhvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}
}
