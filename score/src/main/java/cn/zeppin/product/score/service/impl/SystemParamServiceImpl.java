package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.SystemParam;
import cn.zeppin.product.score.mapper.SystemParamMapper;
import cn.zeppin.product.score.service.SystemParamService;

@Service("systemParamService")
public class SystemParamServiceImpl implements SystemParamService{
	
	@Autowired
	private SystemParamMapper systemParamMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return systemParamMapper.getCountByParams(params);
	}
	
	@Override
	public List<SystemParam> getListByParams(Map<String, Object> params) {
		return systemParamMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="systemParam",key="'systemParam:' + #key")
	public SystemParam get(String key) {
		return systemParamMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(SystemParam systemParam) {
		return systemParamMapper.insert(systemParam);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "systemParam", key = "'systemParam:' + #key")})
	public int delete(String key) {
		return systemParamMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "systemParam", key = "'systemParam:' + #systemParam.paramKey")})
	public int update(SystemParam systemParam) {
		return systemParamMapper.updateByPrimaryKey(systemParam);
	}
}
