package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.MobileCode;
import com.product.worldpay.util.MyMapper;

public interface MobileCodeMapper extends MyMapper<MobileCode> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<MobileCode> getListByParams(Map<String, Object> params);
	
	/**
	 * 批量处理数据
	 * @param paras
	 */
	public void insert(List<Object[]> paras);
}