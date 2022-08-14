package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.VersionDao;
import cn.product.worldmall.entity.Version;
import cn.product.worldmall.mapper.VersionMapper;

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
	@Caching(evict={@CacheEvict(value = "version", key = "'version:' + #version.uuid")
	,@CacheEvict(value = "versionInfo", key = "'versionInfo:' + #version.channel + '_' + #version.name")})
	public int insert(Version version) {
		return versionMapper.insert(version);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "version", key = "'version:' + #key")
	,@CacheEvict(value = "versionInfo", allEntries = true)})
	public int delete(String key) {
		return versionMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "version", key = "'version:' + #version.uuid")
	,@CacheEvict(value = "versionInfo", key = "'versionInfo:' + #version.channel + '_' + #version.name")})
	public int update(Version version) {
		return versionMapper.updateByPrimaryKey(version);
	}

	@Override
	@Cacheable(cacheNames="versionInfo",key="'versionInfo:' + #channel + '_' + #name")
	public Version getByChannelVersion(String channel, String name) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", channel);
		searchMap.put("name", name);
		List<Version> list = this.versionMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
