package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.PayNotifyInfo;
import cn.zeppin.product.score.util.MyMapper;

public interface PayNotifyInfoMapper extends MyMapper<PayNotifyInfo> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<PayNotifyInfo> getListByParams(Map<String, Object> params);
}