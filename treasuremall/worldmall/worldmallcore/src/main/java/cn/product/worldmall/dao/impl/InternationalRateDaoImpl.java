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

import cn.product.worldmall.dao.InternationalRateDao;
import cn.product.worldmall.dao.InternationalRateHistoryDao;
import cn.product.worldmall.entity.InternationalRate;
import cn.product.worldmall.entity.InternationalRate.InternationalRateStatus;
import cn.product.worldmall.entity.InternationalRateHistory;
import cn.product.worldmall.mapper.InternationalRateMapper;

@Component
public class InternationalRateDaoImpl implements InternationalRateDao{

	@Autowired
    private InternationalRateMapper internationalRateMapper;

	@Autowired
    private InternationalRateHistoryDao internationalRateHistoryDao;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return internationalRateMapper.getCountByParams(params);
	}
	
    @Override
    public List<InternationalRate> getListByParams(Map<String, Object> params){
        return internationalRateMapper.getListByParams(params);
    }
    
	@Override
	@Cacheable(cacheNames="internationalRate",key="'internationalRate:' + #key")
	public InternationalRate get(String key) {
		return internationalRateMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listPageInternationalRatelist", allEntries = true, beforeInvocation = true)
	,@CacheEvict(value = "internationalCurrencyRate", key = "'internationalCurrencyRate:' + #internationalRate.currencyCode", beforeInvocation = true)})
	public int insert(InternationalRate internationalRate) {
		return internationalRateMapper.insert(internationalRate);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalRate", key = "'internationalRate:' + #key")
	,@CacheEvict(value = "internationalCurrencyRate", allEntries = true)
	,@CacheEvict(value = "listPageInternationalRatelist", allEntries = true)})
	public int delete(String key) {
		return internationalRateMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalRate", key = "'internationalRate:' + #internationalRate.uuid")
	,@CacheEvict(value = "internationalCurrencyRate", key = "'internationalCurrencyRate:' + #internationalRate.currencyCode")
	,@CacheEvict(value = "listPageInternationalRatelist", allEntries = true)})
	public int update(InternationalRate internationalRate) {
		return internationalRateMapper.updateByPrimaryKey(internationalRate);
	}

	@Override
	@Cacheable(value = "listPageInternationalRatelist")
	public List<InternationalRate> getCurrencyListByParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", InternationalRateStatus.PUBLISH);
		
        return internationalRateMapper.getListByParams(params);
	}

	@Override
	@Cacheable(cacheNames="internationalCurrencyRate",key="'internationalCurrencyRate:' + #currencyCode")
	public InternationalRate getByCurrency(String currencyCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("currencyCode", currencyCode);
        List<InternationalRate> list = internationalRateMapper.getListByParams(params);
        if(list != null && list.size() > 0) {
        	return list.get(0);
        }
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Caching(evict={@CacheEvict(value = "internationalRate", allEntries = true)
	,@CacheEvict(value = "internationalCurrencyRate", allEntries = true)
	,@CacheEvict(value = "listPageInternationalRatelist", allEntries = true)})
	@Transactional
	public void updateDaily(Map<String, Object> params) {
		List<InternationalRate> listUpdate = params.get("listUpdate") == null ? null : (List<InternationalRate>)params.get("listUpdate");
		if(listUpdate != null && listUpdate.size() > 0) {
			for(InternationalRate ir : listUpdate) {
				this.internationalRateMapper.updateByPrimaryKey(ir);
			}
		}
		
		List<InternationalRate> listInsert = params.get("listInsert") == null ? null : (List<InternationalRate>)params.get("listInsert");
		if(listInsert != null && listInsert.size() > 0) {
			for(InternationalRate ir : listInsert) {
				this.internationalRateMapper.insert(ir);
			}
		}
		
		Map<String, InternationalRate> mapDelete = params.get("mapDelete") == null ? null : (Map<String, InternationalRate>)params.get("mapDelete");
		if(mapDelete != null && mapDelete.isEmpty()) {
			for(InternationalRate ir : mapDelete.values()) {
				ir.setStatus(InternationalRateStatus.NORMAL);
				this.internationalRateMapper.updateByPrimaryKey(ir);
			}
		}
		
		InternationalRateHistory irh = params.get("internationalRateHistory") == null ? null : (InternationalRateHistory)params.get("internationalRateHistory");
		if(irh != null) {
			this.internationalRateHistoryDao.update(irh);
		}
	}
}
