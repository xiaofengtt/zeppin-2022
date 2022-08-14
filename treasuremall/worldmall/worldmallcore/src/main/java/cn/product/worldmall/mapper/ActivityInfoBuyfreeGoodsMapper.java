package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods;
import cn.product.worldmall.util.MyMapper;

public interface ActivityInfoBuyfreeGoodsMapper extends MyMapper<ActivityInfoBuyfreeGoods> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoBuyfreeGoods> getListByParams(Map<String,Object> params);
}