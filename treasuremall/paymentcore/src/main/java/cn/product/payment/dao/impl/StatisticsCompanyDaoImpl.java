package cn.product.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.StatisticsCompanyDao;
import cn.product.payment.entity.StatisticsCompany;
import cn.product.payment.mapper.StatisticsCompanyMapper;

@Component
public class StatisticsCompanyDaoImpl implements StatisticsCompanyDao{
	
	@Autowired
	private StatisticsCompanyMapper statisticsCompanyMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return statisticsCompanyMapper.getCountByParams(params);
	}
	
	@Override
	public List<StatisticsCompany> getListByParams(Map<String, Object> params) {
		return statisticsCompanyMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="statisticsCompany",key="'statisticsCompany:' + #key")
	public StatisticsCompany get(String key) {
		return statisticsCompanyMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(StatisticsCompany statisticsCompany) {
		return statisticsCompanyMapper.insert(statisticsCompany);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "statisticsCompany", key = "'statisticsCompany:' + #key")})
	public int delete(String key) {
		return statisticsCompanyMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "statisticsCompany", key = "'statisticsCompany:' + #statisticsCompany.uuid")})
	public int update(StatisticsCompany statisticsCompany) {
		return statisticsCompanyMapper.updateByPrimaryKey(statisticsCompany);
	}

	@Override
	public void statisticsDaily(String dailyDate) {
		statisticsCompanyMapper.statisticsDaily(dailyDate);
	}
	
	/**
	 * 每日统计
	 */
	@Override
	public List<Map<String, Object>> getDailyStatisticsByParams(Map<String, Object> params) {
		return statisticsCompanyMapper.getDailyStatisticsByParams(params);
	}

	/**
	 * 按商户统计
	 */
	@Override
	public List<Map<String, Object>> getCompanyStatisticsByParams(Map<String, Object> params) {
		return statisticsCompanyMapper.getCompanyStatisticsByParams(params);
	}
}
