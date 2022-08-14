package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.VersionDao;
import cn.product.score.entity.Version;
import cn.product.score.mapper.VersionMapper;

@Component
public class VersionDaoImpl implements VersionDao{
	
	@Autowired
	private VersionMapper versionMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return versionMapper.getCountByParams(params);
	}
	
	@Override
	public List<Version> getListByParams(Map<String, Object> params) {
		return versionMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="version",key="'version:' + #key")
	public Version get(String key) {
		return versionMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Version version) {
		return versionMapper.insert(version);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "version", key = "'version:' + #key")})
	public int delete(String key) {
		return versionMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "version", key = "'version:' + #version.uuid")})
	public int update(Version version) {
		return versionMapper.updateByPrimaryKey(version);
	}
}
