package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.InfoCoachesDao;
import cn.product.score.entity.InfoCoaches;
import cn.product.score.mapper.InfoCoachesMapper;

/**
 */
@Component
public class InfoCoachesDaoImpl implements InfoCoachesDao{
	
	@Autowired
	private InfoCoachesMapper infoCoachesMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return infoCoachesMapper.getCountByParams(params);
	}
	
	@Override
	public List<InfoCoaches> getListByParams(Map<String, Object> params) {
		return infoCoachesMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="infoCoaches",key="'infoCoaches:' + #key")
	public InfoCoaches get(String key) {
		return infoCoachesMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(InfoCoaches infoCoaches) {
		return infoCoachesMapper.insert(infoCoaches);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoCoaches", key = "'infoCoaches:' + #key")})
	public int delete(String key) {
		return infoCoachesMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoCoaches", key = "'infoCoaches:' + #infoCoaches.uuid")})
	public int update(InfoCoaches infoCoaches) {
		return infoCoachesMapper.updateByPrimaryKey(infoCoaches);
	}
}
