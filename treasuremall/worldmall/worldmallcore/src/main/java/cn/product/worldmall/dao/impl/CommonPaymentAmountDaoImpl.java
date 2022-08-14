package cn.product.worldmall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.CommonPaymentAmountDao;
import cn.product.worldmall.entity.CommonPaymentAmount;
import cn.product.worldmall.mapper.CommonPaymentAmountMapper;

@Component
public class CommonPaymentAmountDaoImpl implements CommonPaymentAmountDao{

    @Autowired
    private CommonPaymentAmountMapper commonPaymentAmountMapper;
    
    @Override
	public Integer getCountByParams(Map<String, Object> params) {
		return commonPaymentAmountMapper.getCountByParams(params);
	}
	
	@Override
	public List<CommonPaymentAmount> getListByParams(Map<String, Object> params) {
		return commonPaymentAmountMapper.getListByParams(params);
	}

	@Override
	@Cacheable(cacheNames="commonPaymentAmount",key="'commonPaymentAmount:' + #key")
	public CommonPaymentAmount get(String key) {
		return commonPaymentAmountMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CommonPaymentAmount commonPaymentAmount) {
		return commonPaymentAmountMapper.insert(commonPaymentAmount);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "commonPaymentAmount", key = "'commonPaymentAmount:' + #key")})
	public int delete(String key) {
		return commonPaymentAmountMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "commonPaymentAmount", key = "'commonPaymentAmount:' + #commonPaymentAmount.uuid")})
	public int update(CommonPaymentAmount commonPaymentAmount) {
		return commonPaymentAmountMapper.updateByPrimaryKey(commonPaymentAmount);
	}
}
