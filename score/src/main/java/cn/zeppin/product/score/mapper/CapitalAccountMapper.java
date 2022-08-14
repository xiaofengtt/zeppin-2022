package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.util.MyMapper;

public interface CapitalAccountMapper extends MyMapper<CapitalAccount> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalAccount> getListByParams(Map<String, Object> params);
	
	public void dailyRefrush();
}