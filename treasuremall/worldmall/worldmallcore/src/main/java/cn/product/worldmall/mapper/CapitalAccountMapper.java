package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.util.MyMapper;

public interface CapitalAccountMapper extends MyMapper<CapitalAccount> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalAccount> getListByParams(Map<String, Object> params);
}