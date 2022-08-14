package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.PayNotifyInfo;
import cn.product.treasuremall.util.MyMapper;

public interface PayNotifyInfoMapper extends MyMapper<PayNotifyInfo> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<PayNotifyInfo> getListByParams(Map<String, Object> params);
}