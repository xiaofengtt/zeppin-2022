package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.ActivityInfoScorelotteryPrizeDao;
import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize;
import cn.product.worldmall.mapper.ActivityInfoScorelotteryPrizeMapper;

@Component
public class ActivityInfoScorelotteryPrizeDaoImpl implements ActivityInfoScorelotteryPrizeDao {
	
	@Autowired
    private ActivityInfoScorelotteryPrizeMapper activityInfoScorelotteryPrizeMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return activityInfoScorelotteryPrizeMapper.getCountByParams(params);
	}
	
    @Override
    public List<ActivityInfoScorelotteryPrize> getListByParams(Map<String, Object> params){
        return activityInfoScorelotteryPrizeMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="activityInfoScorelotteryPrize",key="'activityInfoScorelotteryPrize:' + #key")
	public ActivityInfoScorelotteryPrize get(String key) {
		return activityInfoScorelotteryPrizeMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoScorelotteryPrizeList", allEntries = true)})
	public int insert(ActivityInfoScorelotteryPrize activityInfoScorelotteryPrize) {
		return activityInfoScorelotteryPrizeMapper.insert(activityInfoScorelotteryPrize);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoScorelotteryPrize", key = "'activityInfoScorelotteryPrize:' + #key"),
			@CacheEvict(value = "activityInfoScorelotteryPrizeList", allEntries = true)})
	public int delete(String key) {
		return activityInfoScorelotteryPrizeMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoScorelotteryPrize", key = "'activityInfoScorelotteryPrize:' + #activityInfoScorelotteryPrize.uuid"),
			@CacheEvict(value = "activityInfoScorelotteryPrizeList", allEntries = true)})
	public int update(ActivityInfoScorelotteryPrize activityInfoScorelotteryPrize) {
		return activityInfoScorelotteryPrizeMapper.updateByPrimaryKey(activityInfoScorelotteryPrize);
	}

	@Override
	@Cacheable(cacheNames="activityInfoScorelotteryPrizeList",key="'activityInfoScorelotteryPrizeList:' + #activity")
	public List<ActivityInfoScorelotteryPrize> getListByActivityInfo(String activity) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("activityInfo", activity);
		return this.activityInfoScorelotteryPrizeMapper.getListByParams(searchMap);
	}
}
