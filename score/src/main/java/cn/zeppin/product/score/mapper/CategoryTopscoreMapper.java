package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.CategoryTopscore;
import cn.zeppin.product.score.util.MyMapper;

public interface CategoryTopscoreMapper extends MyMapper<CategoryTopscore> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CategoryTopscore> getListByParams(Map<String, Object> params);
	
	public void deleteByCategory(String category);
}