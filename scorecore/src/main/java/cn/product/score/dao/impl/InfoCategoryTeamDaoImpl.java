package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.InfoCategoryTeamDao;
import cn.product.score.entity.InfoCategoryTeam;
import cn.product.score.mapper.InfoCategoryTeamMapper;

@Component
public class InfoCategoryTeamDaoImpl implements InfoCategoryTeamDao{
	
	@Autowired
	private InfoCategoryTeamMapper infoCategoryTeamMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return infoCategoryTeamMapper.getCountByParams(params);
	}
	
	@Override
	public List<InfoCategoryTeam> getListByParams(Map<String, Object> params) {
		return infoCategoryTeamMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="infoCategoryTeam",key="'infoCategoryTeam:' + #key")
	public InfoCategoryTeam get(String key) {
		return infoCategoryTeamMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(InfoCategoryTeam infoCategoryTeam) {
		return infoCategoryTeamMapper.insert(infoCategoryTeam);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoCategoryTeam", key = "'infoCategoryTeam:' + #key")})
	public int delete(String key) {
		return infoCategoryTeamMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "infoCategoryTeam", key="'infoCategoryTeam:' + #infoCategoryTeam.uuid")})
	public int update(InfoCategoryTeam infoCategoryTeam) {
		return infoCategoryTeamMapper.updateByPrimaryKey(infoCategoryTeam);
	}

	@Override
	public void deleteByTeam(String team) {
		this.infoCategoryTeamMapper.deleteByTeam(team);
	}
}
