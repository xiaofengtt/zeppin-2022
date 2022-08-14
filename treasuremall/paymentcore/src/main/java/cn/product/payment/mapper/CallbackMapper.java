package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.Callback;
import cn.product.payment.util.MyMapper;

public interface CallbackMapper extends MyMapper<Callback> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Callback> getListByParams(Map<String, Object> params);
}