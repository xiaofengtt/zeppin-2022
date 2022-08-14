package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.CapitalAccount;
import cn.product.score.util.MyMapper;

public interface CapitalAccountMapper extends MyMapper<CapitalAccount> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalAccount> getListByParams(Map<String, Object> params);
	
	public void dailyRefrush();
}