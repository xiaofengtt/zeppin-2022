package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Admin;
import cn.product.treasuremall.util.MyMapper;

public interface AdminMapper extends MyMapper<Admin> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Admin> getListByParams(Map<String, Object> params);
}