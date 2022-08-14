package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.InfoTeamPlayersDao;
import cn.product.score.entity.InfoTeamPlayers;
import cn.product.score.mapper.InfoTeamPlayersMapper;

@Component
public class InfoTeamPlayersDaoImpl implements InfoTeamPlayersDao{
	
	@Autowired
	private InfoTeamPlayersMapper infoTeamPlayersMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return infoTeamPlayersMapper.getCountByParams(params);
	}
	
	@Override
	public List<InfoTeamPlayers> getListByParams(Map<String, Object> params) {
		return infoTeamPlayersMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="infoTeamPlayers",key="'infoTeamPlayers:' + #key")
	public InfoTeamPlayers get(String key) {
		return infoTeamPlayersMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(InfoTeamPlayers infoTeamPlayers) {
		return infoTeamPlayersMapper.insert(infoTeamPlayers);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoTeamPlayers", key = "'infoTeamPlayers:' + #key")})
	public int delete(String key) {
		return infoTeamPlayersMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoTeamPlayers", key = "'infoTeamPlayers:' + #infoTeamPlayers.uuid")})
	public int update(InfoTeamPlayers infoTeamPlayers) {
		return infoTeamPlayersMapper.updateByPrimaryKey(infoTeamPlayers);
	}

	@Override
	public void deleteByTeam(String team) {
		this.infoTeamPlayersMapper.deleteByTeam(team);
	}
}
