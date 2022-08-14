package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.StatisticsFinanceDailyDao;
import cn.product.treasuremall.entity.StatisticsFinanceDaily;
import cn.product.treasuremall.mapper.StatisticsFinanceDailyMapper;

@Component
public class StatisticsFinanceDailyDaoImpl implements StatisticsFinanceDailyDao{
	
	@Autowired
	private StatisticsFinanceDailyMapper statisticsFinanceDailyMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return statisticsFinanceDailyMapper.getCountByParams(params);
	}
	
	@Override
	public List<StatisticsFinanceDaily> getListByParams(Map<String, Object> params) {
		return statisticsFinanceDailyMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="statisticsFinanceDaily",key="'statisticsFinanceDaily:' + #key")
	public StatisticsFinanceDaily get(String key) {
		return statisticsFinanceDailyMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(StatisticsFinanceDaily statisticsFinanceDaily) {
		return statisticsFinanceDailyMapper.insert(statisticsFinanceDaily);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "statisticsFinanceDaily", key = "'statisticsFinanceDaily:' + #key")})
	public int delete(String key) {
		return statisticsFinanceDailyMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "statisticsFinanceDaily", key = "'statisticsFinanceDaily:' + #statisticsFinanceDaily.uuid")})
	public int update(StatisticsFinanceDaily statisticsFinanceDaily) {
		return statisticsFinanceDailyMapper.updateByPrimaryKey(statisticsFinanceDaily);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "statisticsFinanceDaily", allEntries = true)})
	public void insert(List<StatisticsFinanceDaily> listUpdate, List<StatisticsFinanceDaily> listInsert) {
		if(listUpdate != null && listUpdate.size() > 0) {
			for(StatisticsFinanceDaily sfd : listUpdate) {
				this.statisticsFinanceDailyMapper.updateByPrimaryKey(sfd);
			}
		}
		if(listInsert != null && listInsert.size() > 0) {
			for(StatisticsFinanceDaily sfd : listInsert) {
				this.statisticsFinanceDailyMapper.insert(sfd);
			}
		}
	}
}
