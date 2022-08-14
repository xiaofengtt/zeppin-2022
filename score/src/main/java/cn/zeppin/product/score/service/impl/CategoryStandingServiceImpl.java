package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zeppin.product.score.entity.CategoryStanding;
import cn.zeppin.product.score.mapper.CategoryStandingMapper;
import cn.zeppin.product.score.service.CategoryStandingService;

@Service("CategoryStandingService")
public class CategoryStandingServiceImpl implements CategoryStandingService{
	
	@Autowired
	private CategoryStandingMapper categoryStandingMapper;
	
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return categoryStandingMapper.getCountByParams(params);
	}
	
	@Override
	public List<CategoryStanding> getListByParams(Map<String, Object> params) {
		return categoryStandingMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="categoryStanding",key="'categoryStanding:' + #key")
	public CategoryStanding get(String key) {
		return categoryStandingMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CategoryStanding categoryStanding) {
		return categoryStandingMapper.insert(categoryStanding);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "categoryStanding", key = "'categoryStanding:' + #key")})
	public int delete(String key) {
		return categoryStandingMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "categoryStanding", key = "'categoryStanding:' + #categoryStanding.uuid")})
	public int update(CategoryStanding categoryStanding) {
		return categoryStandingMapper.updateByPrimaryKey(categoryStanding);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "categoryStanding", allEntries = true)})
	public void updateByCategory(String category, String season, List<CategoryStanding> dataList) {
		this.categoryStandingMapper.deleteByCategory(category);
		for(CategoryStanding cs : dataList){
			this.categoryStandingMapper.insert(cs);
		}
	}
}
