package cn.product.treasuremall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.ActivityInfoFirstchargePrizeDao;
import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize;
import cn.product.treasuremall.mapper.ActivityInfoFirstchargePrizeMapper;

@Component
public class ActivityInfoFirstchargePrizeDaoImpl implements ActivityInfoFirstchargePrizeDao {
	
	@Autowired
    private ActivityInfoFirstchargePrizeMapper activityInfoFirstchargePrizeMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return activityInfoFirstchargePrizeMapper.getCountByParams(params);
	}
	
    @Override
    public List<ActivityInfoFirstchargePrize> getListByParams(Map<String, Object> params){
        return activityInfoFirstchargePrizeMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="activityInfoFirstchargePrize",key="'activityInfoFirstchargePrize:' + #key")
	public ActivityInfoFirstchargePrize get(String key) {
		return activityInfoFirstchargePrizeMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoFirstchargePrizeList", allEntries = true)})
	public int insert(ActivityInfoFirstchargePrize activityInfoFirstchargePrize) {
		return activityInfoFirstchargePrizeMapper.insert(activityInfoFirstchargePrize);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoFirstchargePrize", key = "'activityInfoFirstchargePrize:' + #key"),
			@CacheEvict(value = "activityInfoFirstchargePrizeList", allEntries = true)})
	public int delete(String key) {
		return activityInfoFirstchargePrizeMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoFirstchargePrize", key = "'activityInfoFirstchargePrize:' + #activityInfoFirstchargePrize.uuid"),
			@CacheEvict(value = "activityInfoFirstchargePrizeList", allEntries = true)})
	public int update(ActivityInfoFirstchargePrize activityInfoFirstchargePrize) {
		return activityInfoFirstchargePrizeMapper.updateByPrimaryKey(activityInfoFirstchargePrize);
	}

	@Override
	@Cacheable(cacheNames="activityInfoFirstchargePrizeList",key="'activityInfoFirstchargePrizeList:' + #activity")
	public List<ActivityInfoFirstchargePrize> getListByActivityInfo(String activity) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("activityInfo", activity);
		return this.activityInfoFirstchargePrizeMapper.getListByParams(searchMap);
	}
}
