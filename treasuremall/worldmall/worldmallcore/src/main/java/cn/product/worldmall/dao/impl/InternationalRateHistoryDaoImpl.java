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

import cn.product.worldmall.dao.InternationalRateHistoryDao;
import cn.product.worldmall.entity.InternationalRateHistory;
import cn.product.worldmall.entity.InternationalRateHistory.InternationalRateHistoryStatus;
import cn.product.worldmall.mapper.InternationalRateHistoryMapper;

@Component
public class InternationalRateHistoryDaoImpl implements InternationalRateHistoryDao{

	@Autowired
    private InternationalRateHistoryMapper internationalRateHistoryMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return internationalRateHistoryMapper.getCountByParams(params);
	}
	
    @Override
    public List<InternationalRateHistory> getListByParams(Map<String, Object> params){
        return internationalRateHistoryMapper.getListByParams(params);
    }

	@Override
	public List<InternationalRateHistory> getLessInfoListByParams(Map<String, Object> params) {
		return internationalRateHistoryMapper.getLessInfoListByParams(params);
	}
    
	@Override
	@Cacheable(cacheNames="internationalRateHistory",key="'internationalRateHistory:' + #key")
	public InternationalRateHistory get(String key) {
		return internationalRateHistoryMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalRateDateHistory", allEntries = true)})
	public int insert(InternationalRateHistory internationalRateHistory) {
		return internationalRateHistoryMapper.insert(internationalRateHistory);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalRateHistory", key = "'internationalRateHistory:' + #key")
	,@CacheEvict(value = "internationalRateDateHistory", allEntries = true)})
	public int delete(String key) {
		return internationalRateHistoryMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalRateHistory", key = "'internationalRateHistory:' + #internationalRateHistory.uuid")
	,@CacheEvict(value = "internationalRateDateHistory", allEntries = true)})
	public int update(InternationalRateHistory internationalRateHistory) {
		return internationalRateHistoryMapper.updateByPrimaryKey(internationalRateHistory);
	}

	@Override
	@Cacheable(cacheNames="internationalRateDateHistory",key="'internationalRateDateHistory:' + #dailyDate")
	public InternationalRateHistory getByDailyDate(String dailyDate) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("dailyDate", dailyDate);
		List<InternationalRateHistory> list = this.internationalRateHistoryMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalRateHistory", allEntries = true, beforeInvocation = true)
	,@CacheEvict(value = "internationalRateDateHistory", allEntries = true, beforeInvocation = true)})
	@Transactional
	public void dailyUpdate(Map<String, Object> params) {
		boolean isUpdate = (boolean) params.get("isUpdate");
		InternationalRateHistory irh = (InternationalRateHistory) params.get("internationalRateHistory");
		
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("nuuid", irh.getUuid());
		updateMap.put("status", InternationalRateHistoryStatus.NORMAL);
		updateMap.put("updateStatus", InternationalRateHistoryStatus.DISABLE);
		
		this.internationalRateHistoryMapper.updateInfo(updateMap);
		
		if(isUpdate) {
			this.internationalRateHistoryMapper.updateByPrimaryKey(irh);
		} else {
			this.internationalRateHistoryMapper.insert(irh);
		}
	}
}
