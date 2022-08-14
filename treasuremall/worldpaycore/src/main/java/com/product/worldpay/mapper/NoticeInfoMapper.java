package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.NoticeInfo;
import com.product.worldpay.util.MyMapper;

public interface NoticeInfoMapper extends MyMapper<NoticeInfo> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<NoticeInfo> getListByParams(Map<String, Object> params);
}