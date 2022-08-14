package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.PayNotifyInfo;
import cn.product.worldmall.util.MyMapper;

public interface PayNotifyInfoMapper extends MyMapper<PayNotifyInfo> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<PayNotifyInfo> getListByParams(Map<String, Object> params);
}