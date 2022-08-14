package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.ActivityInfoRechargePrizeDao;
import cn.product.worldmall.entity.ActivityInfoRechargePrize;
import cn.product.worldmall.mapper.ActivityInfoRechargePrizeMapper;

@Component
public class ActivityInfoRechargePrizeDaoImpl implements ActivityInfoRechargePrizeDao {
	
	@Autowired
    private ActivityInfoRechargePrizeMapper activityInfoRechargePrizeMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return activityInfoRechargePrizeMapper.getCountByParams(params);
	}
	
    @Override
    public List<ActivityInfoRechargePrize> getListByParams(Map<String, Object> params){
        return activityInfoRechargePrizeMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="activityInfoRechargePrize",key="'activityInfoRechargePrize:' + #key")
	public ActivityInfoRechargePrize get(String key) {
		return activityInfoRechargePrizeMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoRechargePrizeList", allEntries = true)})
	public int insert(ActivityInfoRechargePrize activityInfoRechargePrize) {
		return activityInfoRechargePrizeMapper.insert(activityInfoRechargePrize);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoRechargePrize", key = "'activityInfoRechargePrize:' + #key"),
			@CacheEvict(value = "activityInfoRechargePrizeList", allEntries = true)})
	public int delete(String key) {
		return activityInfoRechargePrizeMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoRechargePrize", key = "'activityInfoRechargePrize:' + #activityInfoRechargePrize.uuid"),
			@CacheEvict(value = "activityInfoRechargePrizeList", allEntries = true)})
	public int update(ActivityInfoRechargePrize activityInfoRechargePrize) {
		return activityInfoRechargePrizeMapper.updateByPrimaryKey(activityInfoRechargePrize);
	}

	@Override
	@Cacheable(cacheNames="activityInfoRechargePrizeList",key="'activityInfoRechargePrizeList:' + #activity")
	public List<ActivityInfoRechargePrize> getListByActivityInfo(String activity) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("activityInfo", activity);
		return this.activityInfoRechargePrizeMapper.getListByParams(searchMap);
	}
}
