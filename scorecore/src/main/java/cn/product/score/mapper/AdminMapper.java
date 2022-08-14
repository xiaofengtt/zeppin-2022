package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.Admin;
import cn.product.score.util.MyMapper;

public interface AdminMapper extends MyMapper<Admin> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Admin> getListByParams(Map<String, Object> params);
}