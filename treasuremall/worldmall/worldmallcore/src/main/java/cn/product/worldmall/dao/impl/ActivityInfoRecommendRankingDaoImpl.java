package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.ActivityInfoRecommendRankingDao;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking.ActivityInfoRecommendRankingStatus;
import cn.product.worldmall.mapper.ActivityInfoRecommendRankingMapper;

@Component
public class ActivityInfoRecommendRankingDaoImpl implements ActivityInfoRecommendRankingDao {
	
	@Autowired
    private ActivityInfoRecommendRankingMapper activityInfoRecommendRankingMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return activityInfoRecommendRankingMapper.getCountByParams(params);
	}
	
    @Override
    public List<ActivityInfoRecommendRanking> getListByParams(Map<String, Object> params){
        return activityInfoRecommendRankingMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="activityInfoRecommendRanking",key="'activityInfoRecommendRanking:' + #key")
	public ActivityInfoRecommendRanking get(String key) {
		return activityInfoRecommendRankingMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoRecommendRankingList", allEntries = true), 
			@CacheEvict(value = "activityInfoRecommendRankingFrontUser", key = "'activityInfoRecommendRankingFrontUser:' + #activityInfoRecommendRanking.frontUser")})
	public int insert(ActivityInfoRecommendRanking activityInfoRecommendRanking) {
		return activityInfoRecommendRankingMapper.insert(activityInfoRecommendRanking);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoRecommendRanking", key = "'activityInfoRecommendRanking:' + #key")})
	public int delete(String key) {
		return activityInfoRecommendRankingMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoRecommendRanking", key = "'activityInfoRecommendRanking:' + #activityInfoRecommendRanking.uuid"),
			@CacheEvict(value = "activityInfoRecommendRankingFrontUser", key = "'activityInfoRecommendRankingFrontUser:' + #activityInfoRecommendRanking.frontUser")})
	public int update(ActivityInfoRecommendRanking activityInfoRecommendRanking) {
		return activityInfoRecommendRankingMapper.updateByPrimaryKey(activityInfoRecommendRanking);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoRecommendRanking", allEntries = true)})
	@Transactional
	public void updateRanking(List<ActivityInfoRecommendRanking> addList, List<ActivityInfoRecommendRanking> editList,
			List<ActivityInfoRecommendRanking> deleteList) {
		for(ActivityInfoRecommendRanking addData : addList){
			this.activityInfoRecommendRankingMapper.insert(addData);
		}
		for(ActivityInfoRecommendRanking editData : editList){
			this.activityInfoRecommendRankingMapper.updateByPrimaryKey(editData);
		}
		for(ActivityInfoRecommendRanking deleteData : deleteList){
			this.activityInfoRecommendRankingMapper.deleteByPrimaryKey(deleteData.getUuid());
		}
	}

	@Override
	@Cacheable(cacheNames="activityInfoRecommendRankingFrontUser",key="'activityInfoRecommendRankingFrontUser:' + #frontUser")
	public ActivityInfoRecommendRanking getByFrontUser(String frontUser) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("status", ActivityInfoRecommendRankingStatus.NORMAL);
		List<ActivityInfoRecommendRanking> list = this.activityInfoRecommendRankingMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
