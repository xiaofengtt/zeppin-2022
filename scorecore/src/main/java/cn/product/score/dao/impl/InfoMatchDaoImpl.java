package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.InfoMatchDao;
import cn.product.score.entity.InfoMatch;
import cn.product.score.mapper.InfoMatchMapper;

@Component
public class InfoMatchDaoImpl implements InfoMatchDao{
	
	@Autowired
	private InfoMatchMapper infoMatchMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return infoMatchMapper.getCountByParams(params);
	}
	
	@Override
	public List<InfoMatch> getListByParams(Map<String, Object> params) {
		return infoMatchMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="infoMatch",key="'infoMatch:' + #key")
	public InfoMatch get(String key) {
		return infoMatchMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(InfoMatch infoMatch) {
		return infoMatchMapper.insert(infoMatch);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoMatch", key = "'infoMatch:' + #key")})
	public int delete(String key) {
		return infoMatchMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoMatch", key = "'infoMatch:' + #infoMatch.uuid")})
	public int update(InfoMatch infoMatch) {
		return infoMatchMapper.updateByPrimaryKey(infoMatch);
	}
	
}
