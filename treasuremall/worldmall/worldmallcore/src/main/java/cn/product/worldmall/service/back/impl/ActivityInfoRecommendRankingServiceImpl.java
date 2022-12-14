package cn.product.worldmall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.util.api.JSONUtils;
import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.util.Utlity;
import cn.product.worldmall.dao.ActivityInfoRecommendRankingDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking.ActivityInfoRecommendRankingStatus;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking.ActivityInfoRecommendRankingType;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.service.back.ActivityInfoRecommendRankingService;
import cn.product.worldmall.vo.back.ActivityInfoRecommendRankingVO;

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
		
		//????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", type);
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//??????????????????????????????????????????
		Integer totalResultCount = activityInfoRecommendRankingDao.getCountByParams(searchMap);
		//???????????????????????????????????????
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
				result.setMessage("????????????????????????");
				return;
			}
			if(ActivityInfoRecommendRankingStatus.DELETE.equals(airr.getStatus())){
				if(airr.getUuid() == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("????????????????????????");
					return;
				}
				
				ActivityInfoRecommendRanking deleteData = this.activityInfoRecommendRankingDao.get(airr.getUuid());
				
				if(deleteData == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("????????????????????????");
					return;
				}
				
				deleteList.add(deleteData);
			}else{
				if(Utlity.checkStringNull(airr.getFrontUser()) || airr.getAward() == null || airr.getRecommend() == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("????????????????????????");
					return;
				}
				
				if(!ActivityInfoRecommendRankingStatus.NORMAL.equals(airr.getStatus()) && !ActivityInfoRecommendRankingStatus.DISABLE.equals(airr.getStatus())){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("????????????????????????");
					return;
				}
				
				if(Utlity.checkStringNull(airr.getUuid())){
					FrontUser fu = this.frontUserDao.get(airr.getFrontUser());
					if(fu == null || !FrontUserType.ROBOT.equals(fu.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("????????????????????????");
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
						result.setMessage("????????????????????????");
						return;
					}
					
					FrontUser fu = this.frontUserDao.get(airr.getFrontUser());
					if(fu == null || !FrontUserType.ROBOT.equals(fu.getType())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("????????????????????????");
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
			result.setMessage("????????????????????????");
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}
}
