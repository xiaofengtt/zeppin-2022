package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.InfoPlayersDao;
import cn.product.score.entity.InfoPlayers;
import cn.product.score.mapper.InfoPlayersMapper;

@Component
public class InfoPlayersDaoImpl implements InfoPlayersDao{
	
	@Autowired
	private InfoPlayersMapper infoPlayersMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return infoPlayersMapper.getCountByParams(params);
	}
	
	@Override
	public List<InfoPlayers> getListByParams(Map<String, Object> params) {
		return infoPlayersMapper.getListByParams(params);
	}

	@Override
	@Cacheable(cacheNames="infoPlayers",key="'infoPlayers:' + #key")
	public InfoPlayers get(String key) {
		return infoPlayersMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(InfoPlayers infoPlayers) {
		return infoPlayersMapper.insert(infoPlayers);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoPlayers", key = "'infoPlayers:' + #key")})
	public int delete(String key) {
		return infoPlayersMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoPlayers", key = "'infoPlayers:' + #infoPlayers.uuid")})
	public int update(InfoPlayers infoPlayers) {
		return infoPlayersMapper.updateByPrimaryKey(infoPlayers);
	}
}
