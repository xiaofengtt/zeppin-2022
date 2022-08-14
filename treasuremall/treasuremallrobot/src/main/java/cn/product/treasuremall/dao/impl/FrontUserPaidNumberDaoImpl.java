package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.FrontUserPaidNumberDao;
import cn.product.treasuremall.entity.FrontUserPaidNumber;
import cn.product.treasuremall.mapper.FrontUserPaidNumberMapper;

@Component
public class FrontUserPaidNumberDaoImpl implements FrontUserPaidNumberDao{
	
	@Autowired
	private FrontUserPaidNumberMapper frontUserPaidNumberMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserPaidNumberMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserPaidNumber> getListByParams(Map<String, Object> params) {
		return frontUserPaidNumberMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserPaidNumber",key="'frontUserPaidNumber:' + #key")
	public FrontUserPaidNumber get(String key) {
		return frontUserPaidNumberMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserPaidNumber frontUserPaidNumber) {
		return frontUserPaidNumberMapper.insert(frontUserPaidNumber);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserPaidNumber", key = "'frontUserPaidNumber:' + #key")})
	public int delete(String key) {
		return frontUserPaidNumberMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserPaidNumber", key = "'frontUserPaidNumber:' + #frontUserPaidNumber.orderId")})
	public int update(FrontUserPaidNumber frontUserPaidNumber) {
		return frontUserPaidNumberMapper.updateByPrimaryKey(frontUserPaidNumber);
	}
}
