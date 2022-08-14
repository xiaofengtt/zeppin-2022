package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Admin;
import cn.product.worldmall.util.MyMapper;

public interface AdminMapper extends MyMapper<Admin> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Admin> getListByParams(Map<String, Object> params);
}