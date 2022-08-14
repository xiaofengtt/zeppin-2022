package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.InfoCategoryTeam;
import cn.zeppin.product.score.mapper.InfoCategoryTeamMapper;
import cn.zeppin.product.score.service.InfoCategoryTeamService;

@Service("infoCategoryTeamService")
public class InfoCategoryTeamServiceImpl implements InfoCategoryTeamService{
	
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
