package cn.zeppin.product.score.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.mapper.ResourceMapper;
import cn.zeppin.product.score.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService{
	
	@Autowired
	private ResourceMapper resourceMapper;
	
	@Override
	@Cacheable(cacheNames="resource",key="'resource:' + #key")
	public Resource get(String key) {
		return resourceMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Resource resource) {
		return resourceMapper.insert(resource);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "resource", key = "'resource:' + #key")})
	public int delete(String key) {
		return resourceMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "resource", key = "'resource:' + #resource.uuid")})
	public int update(Resource resource) {
		return resourceMapper.updateByPrimaryKey(resource);
	}
}
