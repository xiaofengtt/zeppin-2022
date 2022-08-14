package cn.product.treasuremall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.util.api.JSONUtils;
import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.util.Utlity;
import cn.product.treasuremall.dao.ActivityInfoRecommendRankingDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.entity.ActivityInfoRecommendRanking;
import cn.product.treasuremall.entity.ActivityInfoRecommendRanking.ActivityInfoRecommendRankingStatus;
import cn.product.treasuremall.entity.ActivityInfoRecommendRanking.ActivityInfoRecommendRankingType;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.service.back.ActivityInfoRecommendRankingService;
import cn.product.treasuremall.vo.back.ActivityInfoRecommendRankingVO;

@Service("activityInfoRecommendRankingService")
public class ActivityInfoRecommendRankingServiceImpl implements ActivityInfoRecommendRankingService{
	
	@Autowired
	private ActivityInfoRecommendRankingDao activityInfoRecommendRankingDao;

	@Autowired
	private FrontUserDao frontUserDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", type);
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的活动信息的总数
		Integer totalResultCount = activityInfoRecommendRankingDao.getCountByParams(searchMap);
		//查询符合条件的活动信息列表
		List<ActivityInfoRecommendRanking> list = activityInfoRecommendRankingDao.getListByParams(searchMap);
		List<ActivityInfoRecommendRankingVO> volist = new ArrayList<ActivityInfoRecommendRankingVO>();
		
		if(list != null && list.size() > 0) {
			for(ActivityInfoRecommendRanking airr : list) {
				ActivityInfoRecommendRankingVO vo = new ActivityInfoRecommendRankingVO(airr);
				
				FrontUser fu = this.frontUserDao.get(airr.getFrontUser());
				if(fu != null){
					vo.setFrontUserName(fu.getNickname());
					vo.setFrontUserShowId(fu.getShowId());
				}
				
				volist.add(vo);
			}
		}
		result.setData(volist);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String data = paramsMap.get("data") == null ? "" : paramsMap.get("data").toString();
		
		List<ActivityInfoRecommendRanking> dataList = JSONUtils.json2list(data, ActivityInfoRecommendRanking.class);
		List<ActivityInfoRecommendRanking> addList = new ArrayList<ActivityInfoRecommendRanking>();
		List<ActivityInfoRecommendRanking> editList = new ArrayList<ActivityInfoRecommendRanking>();
		List<ActivityInfoRecommendRanking> deleteList = new ArrayList<ActivityInfoRecommendRanking>();
		for(ActivityInfoRecommendRanking airr : dataList){
			if(Utlity.checkStringNull(airr.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("排行榜数据有误！");
				return;
			}
			if(ActivityInfoRecommendRankingStatus.DELETE.equals(airr.getStatus())){
				if(airr.getUuid() == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("排行榜数据有误！");
					return;
				}
				
				ActivityInfoRecommendRanking deleteData = this.activityInfoRecommendRankingDao.get(airr.getUuid());
				
				if(deleteData == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("排行榜数据有误！");
					return;
				}
				
				deleteList.add(deleteData);
			}else{
				if(Utlity.checkStringNull(airr.getFrontUser()) || airr.getAward() == null || airr.getRecommend() == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("排行榜数据有误！");
					return;
				}
				
				if(!ActivityInfoRecommendRankingStatus.NORMAL.equals(airr.getStatus()) && !ActivityInfoRecommendRankingStatus.DISABLE.equals(airr.getStatus())){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("排行榜数据有误！");
					return;
				}
				
				if(Utlity.checkStringNull(airr.getUuid())){
					FrontUser fu = this.frontUserDao.get(airr.getFrontUser());
					if(fu == null || !FrontUserType.ROBOT.equals(fu.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("排行榜用户有误！");
						return;
					}
					
					ActivityInfoRecommendRanking addData = new ActivityInfoRecommendRanking();
					addData.setUuid(UUID.randomUUID().toString());
					addData.setFrontUser(fu.getUuid());
					addData.setType(ActivityInfoRecommendRankingType.ROBOT);
					addData.setAward(airr.getAward());
					addData.setRecommend(airr.getRecommend());
					addData.setStatus(airr.getStatus());
					addData.setCreatetime(new Timestamp(System.currentTimeMillis()));
					
					addList.add(addData);
				}else{
					ActivityInfoRecommendRanking editData = this.activityInfoRecommendRankingDao.get(airr.getUuid());
					if(editData == null || !ActivityInfoRecommendRankingType.ROBOT.equals(editData.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("排行榜数据有误！");
						return;
					}
					
					FrontUser fu = this.frontUserDao.get(airr.getFrontUser());
					if(fu == null || !FrontUserType.ROBOT.equals(fu.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("排行榜用户有误！");
						return;
					}
					
					editData.setFrontUser(fu.getUuid());
					editData.setAward(airr.getAward());
					editData.setRecommend(airr.getRecommend());
					editData.setStatus(airr.getStatus());
					
					editList.add(editData);
				}
			}
		}
		
		try{
			this.activityInfoRecommendRankingDao.updateRanking(addList, editList, deleteList);
		}catch (Exception e){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("排行榜变更错误！");
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}
}
