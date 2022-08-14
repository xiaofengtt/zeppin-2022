package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.CapitalPlatform;
import cn.zeppin.product.score.mapper.CapitalPlatformMapper;
import cn.zeppin.product.score.service.CapitalPlatformService;

@Service("capitalPlatformService")
public class CapitalPlatformServiceImpl implements CapitalPlatformService{
	
	@Autowired
	private CapitalPlatformMapper capitalPlatformMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return capitalPlatformMapper.getCountByParams(params);
	}
	
	@Override
	public List<CapitalPlatform> getListByParams(Map<String, Object> params) {
		return capitalPlatformMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="capitalPlatform",key="'capitalPlatform:' + #key")
	public CapitalPlatform get(String key) {
		return capitalPlatformMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CapitalPlatform capitalPlatform) {
		return capitalPlatformMapper.insert(capitalPlatform);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalPlatform", key = "'capitalPlatform:' + #key")})
	public int delete(String key) {
		return capitalPlatformMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalPlatform", key = "'capitalPlatform:' + #capitalPlatform.uuid")})
	public int update(CapitalPlatform capitalPlatform) {
		return capitalPlatformMapper.updateByPrimaryKey(capitalPlatform);
	}
}
