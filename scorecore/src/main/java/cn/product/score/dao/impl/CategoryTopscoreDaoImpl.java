package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.score.dao.CategoryTopscoreDao;
import cn.product.score.entity.CategoryTopscore;
import cn.product.score.mapper.CategoryTopscoreMapper;

@Component
public class CategoryTopscoreDaoImpl implements CategoryTopscoreDao{
	
	@Autowired
	private CategoryTopscoreMapper categoryTopscoreMapper;
	
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return categoryTopscoreMapper.getCountByParams(params);
	}
	
	@Override
	public List<CategoryTopscore> getListByParams(Map<String, Object> params) {
		return categoryTopscoreMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="categoryTopscore",key="'categoryTopscore:' + #key")
	public CategoryTopscore get(String key) {
		return categoryTopscoreMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CategoryTopscore categoryTopscore) {
		return categoryTopscoreMapper.insert(categoryTopscore);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "categoryTopscore", key = "'categoryTopscore:' + #key")})
	public int delete(String key) {
		return categoryTopscoreMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "categoryTopscore", key = "'categoryTopscore:' + #categoryTopscore.uuid")})
	public int update(CategoryTopscore categoryTopscore) {
		return categoryTopscoreMapper.updateByPrimaryKey(categoryTopscore);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "categoryTopscore", allEntries = true)})
	public void updateByCategory(String category, List<CategoryTopscore> dataList) {
		this.categoryTopscoreMapper.deleteByCategory(category);
		for(CategoryTopscore ct : dataList){
			this.categoryTopscoreMapper.insert(ct);
		}
	}
}
