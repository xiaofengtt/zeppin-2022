package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.ActivityInfoBuyfreeSharesnumDao;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeSharesnum;
import cn.product.treasuremall.mapper.ActivityInfoBuyfreeSharesnumMapper;

@Component
public class ActivityInfoBuyfreeSharesnumDaoImpl implements ActivityInfoBuyfreeSharesnumDao {
	
	@Autowired
    private ActivityInfoBuyfreeSharesnumMapper activityInfoBuyfreeSharesnumMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return activityInfoBuyfreeSharesnumMapper.getCountByParams(params);
	}
	
    @Override
    public List<ActivityInfoBuyfreeSharesnum> getListByParams(Map<String, Object> params){
        return activityInfoBuyfreeSharesnumMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="activityInfoBuyfreeSharesnum",key="'activityInfoBuyfreeSharesnum:' + #key")
	public ActivityInfoBuyfreeSharesnum get(String key) {
		return activityInfoBuyfreeSharesnumMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(ActivityInfoBuyfreeSharesnum activityInfoBuyfreeSharesnum) {
		return activityInfoBuyfreeSharesnumMapper.insert(activityInfoBuyfreeSharesnum);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfreeSharesnum", key = "'activityInfoBuyfreeSharesnum:' + #key")})
	public int delete(String key) {
		return activityInfoBuyfreeSharesnumMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfreeSharesnum", key = "'activityInfoBuyfreeSharesnum:' + #activityInfoBuyfreeSharesnum.activityInfoBuyfree")})
	public int update(ActivityInfoBuyfreeSharesnum activityInfoBuyfreeSharesnum) {
		return activityInfoBuyfreeSharesnumMapper.updateByPrimaryKey(activityInfoBuyfreeSharesnum);
	}
	
}
