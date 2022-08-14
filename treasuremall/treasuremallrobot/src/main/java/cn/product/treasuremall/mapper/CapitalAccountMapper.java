package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.util.MyMapper;

public interface CapitalAccountMapper extends MyMapper<CapitalAccount> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalAccount> getListByParams(Map<String, Object> params);
}