package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.InfoTeamCoachesDao;
import cn.product.score.entity.InfoTeamCoaches;
import cn.product.score.mapper.InfoTeamCoachesMapper;

/**
 */
@Component
public class InfoTeamCoachesDaoImpl implements InfoTeamCoachesDao{
	
	@Autowired
	private InfoTeamCoachesMapper infoTeamCoachesMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return infoTeamCoachesMapper.getCountByParams(params);
	}
	
	@Override
	public List<InfoTeamCoaches> getListByParams(Map<String, Object> params) {
		return infoTeamCoachesMapper.getListByParams(params);
	}

	@Override
	@Cacheable(cacheNames="infoTeamCoaches",key="'infoTeamCoaches:' + #key")
	public InfoTeamCoaches get(String key) {
		return infoTeamCoachesMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(InfoTeamCoaches infoTeamCoaches) {
		return infoTeamCoachesMapper.insert(infoTeamCoaches);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoTeamCoaches", key = "'infoTeamCoaches:' + #key")})
	public int delete(String key) {
		return infoTeamCoachesMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoTeamCoaches", key = "'infoTeamCoaches:' + #infoTeamCoaches.uuid")})
	public int update(InfoTeamCoaches infoTeamCoaches) {
		return infoTeamCoachesMapper.updateByPrimaryKey(infoTeamCoaches);
	}
	
}
