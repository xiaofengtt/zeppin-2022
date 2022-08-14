package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Banner;
import cn.product.worldmall.util.MyMapper;

public interface BannerMapper extends MyMapper<Banner> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Banner> getListByParams(Map<String, Object> params);
}