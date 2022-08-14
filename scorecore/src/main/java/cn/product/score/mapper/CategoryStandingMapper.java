package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.score.entity.CategoryStanding;
import cn.product.score.util.MyMapper;

public interface CategoryStandingMapper extends MyMapper<CategoryStanding> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CategoryStanding> getListByParams(Map<String, Object> params);
	
	public void deleteByCategory(@Param("category")String category);
}