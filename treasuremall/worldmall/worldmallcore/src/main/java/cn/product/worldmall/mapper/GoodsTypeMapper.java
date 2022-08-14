package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.GoodsType;
import cn.product.worldmall.util.MyMapper;

public interface GoodsTypeMapper extends MyMapper<GoodsType> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<GoodsType> getListByParams(Map<String,Object> params);
}