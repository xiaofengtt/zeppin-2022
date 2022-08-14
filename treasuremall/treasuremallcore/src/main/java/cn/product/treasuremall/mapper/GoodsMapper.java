package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.util.MyMapper;

public interface GoodsMapper extends MyMapper<Goods> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<Goods> getListByParams(Map<String,Object> params);
}