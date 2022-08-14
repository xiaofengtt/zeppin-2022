package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.InfoPlayers;
import cn.zeppin.product.score.mapper.InfoPlayersMapper;
import cn.zeppin.product.score.service.InfoPlayersService;

@Service("infoPlayersService")
public class InfoPlayersServiceImpl implements InfoPlayersService{
	
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
