package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.AdminOffsetOrder;
import cn.product.worldmall.util.MyMapper;

public interface AdminOffsetOrderMapper extends MyMapper<AdminOffsetOrder> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<AdminOffsetOrder> getListByParams(Map<String, Object> params);
	
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	public List<AdminOffsetOrder> getLeftListByParams(Map<String, Object> params);
}