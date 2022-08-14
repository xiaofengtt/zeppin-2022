package cn.zeppin.product.score.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.Category;
import cn.zeppin.product.score.mapper.CategoryMapper;
import cn.zeppin.product.score.service.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return categoryMapper.getCountByParams(params);
	}
	
	@Override
	public List<Category> getListByParams(Map<String, Object> params) {
		return categoryMapper.getListByParams(params);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "category", key = "'category:' + #category.uuid")})
	public void deleteCategory(Category category) {
		categoryMapper.deleteCategory(category);
	}

	@Override
	public void insertCategory(Category category) {
		Category parent = this.get(category.getParent());
		
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("parent", category.getParent());
		Integer count = this.categoryMapper.getCountByParams(searchMap);
		String scode = String.format("%03d", count);
		
		category.setScode(parent.getScode() + scode);
		categoryMapper.insert(category);
	}

	@Override
	@Cacheable(cacheNames="category",key="'category:' + #key")
	public Category get(String key) {
		return categoryMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Category category) {
		return categoryMapper.insert(category);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "category", key = "'category:' + #key")})
	public int delete(String key) {
		return categoryMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "category", key = "'category:' + #category.uuid")})
	public int update(Category category) {
		return categoryMapper.updateByPrimaryKey(category);
	}
	
}
