package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.InfoMatch;
import cn.zeppin.product.score.mapper.InfoMatchMapper;
import cn.zeppin.product.score.service.InfoMatchService;

@Service("infoMatchService")
public class InfoMatchServiceImpl implements InfoMatchService{
	
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
