package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.treasuremall.entity.GoodsCoverImage;
import cn.product.treasuremall.util.MyMapper;

public interface GoodsCoverImageMapper extends MyMapper<GoodsCoverImage> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<GoodsCoverImage> getListByParams(Map<String,Object> params);
    
    public void deleteByBelongs(@Param("belongs")String belongs);
}