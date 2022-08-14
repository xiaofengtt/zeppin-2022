package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.Admin;
import cn.product.payment.util.MyMapper;

public interface AdminMapper extends MyMapper<Admin> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Admin> getListByParams(Map<String, Object> params);
}