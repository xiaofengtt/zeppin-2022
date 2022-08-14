package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.NoticeInfo;
import cn.product.payment.util.MyMapper;

public interface NoticeInfoMapper extends MyMapper<NoticeInfo> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<NoticeInfo> getListByParams(Map<String, Object> params);
}