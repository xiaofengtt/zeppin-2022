package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.Category;
import cn.product.score.util.MyMapper;

public interface CategoryMapper extends MyMapper<Category> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Category> getListByParams(Map<String, Object> params);
	
	public void deleteCategory(Category category);
}