package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Banner;
import cn.product.treasuremall.util.MyMapper;

public interface BannerMapper extends MyMapper<Banner> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Banner> getListByParams(Map<String, Object> params);
}