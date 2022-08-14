package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize;
import cn.product.worldmall.mapper.ActivityInfoCheckinPrizeMapper;

@Component
public class ActivityInfoCheckinPrizeDaoImpl implements ActivityInfoCheckinPrizeDao {
	
	@Autowired
    private ActivityInfoCheckinPrizeMapper activityInfoCheckinPrizeMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return activityInfoCheckinPrizeMapper.getCountByParams(params);
	}
	
    @Override
    public List<ActivityInfoCheckinPrize> getListByParams(Map<String, Object> params){
        return activityInfoCheckinPrizeMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="activityInfoCheckinPrize",key="'activityInfoCheckinPrize:' + #key")
	public ActivityInfoCheckinPrize get(String key) {
		return activityInfoCheckinPrizeMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoCheckinPrizeList", allEntries = true)})
	public int insert(ActivityInfoCheckinPrize activityInfoCheckinPrize) {
		return activityInfoCheckinPrizeMapper.insert(activityInfoCheckinPrize);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoCheckinPrize", key = "'activityInfoCheckinPrize:' + #key"),
			@CacheEvict(value = "activityInfoCheckinPrizeList", allEntries = true)})
	public int delete(String key) {
		return activityInfoCheckinPrizeMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoCheckinPrize", key = "'activityInfoCheckinPrize:' + #activityInfoCheckinPrize.uuid"),
			@CacheEvict(value = "activityInfoCheckinPrizeList", allEntries = true)})
	public int update(ActivityInfoCheckinPrize activityInfoCheckinPrize) {
		return activityInfoCheckinPrizeMapper.updateByPrimaryKey(activityInfoCheckinPrize);
	}

	@Override
	@Cacheable(cacheNames="activityInfoCheckinPrizeList",key="'activityInfoCheckinPrizeList:' + #activity")
	public List<ActivityInfoCheckinPrize> getListByActivityInfo(String activity) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("activityInfo", activity);
		return this.activityInfoCheckinPrizeMapper.getListByParams(searchMap);
	}
}
