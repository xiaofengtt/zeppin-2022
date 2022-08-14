package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.util.MyMapper;

public interface GoodsMapper extends MyMapper<Goods> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<Goods> getListByParams(Map<String,Object> params);
}