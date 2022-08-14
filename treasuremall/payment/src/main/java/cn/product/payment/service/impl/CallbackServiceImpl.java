package cn.product.payment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.product.payment.entity.Callback;
import cn.product.payment.mapper.CallbackMapper;
import cn.product.payment.service.CallbackService;

@Service("callbackService")
public class CallbackServiceImpl implements CallbackService{
	
	@Autowired
	private CallbackMapper callbackMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return callbackMapper.getCountByParams(params);
	}
	
	@Override
	public List<Callback> getListByParams(Map<String, Object> params) {
		return callbackMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="callback",key="'callback:' + #key")
	public Callback get(String key) {
		return callbackMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Callback callback) {
		return callbackMapper.insert(callback);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "callback", key = "'callback:' + #key")})
	public int delete(String key) {
		return callbackMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "callback", key = "'callback:' + #callback.uuid")})
	public int update(Callback callback) {
		return callbackMapper.updateByPrimaryKey(callback);
	}
}
