package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.ActivityInfoBuyfreeGoods;
import cn.product.treasuremall.util.MyMapper;

public interface ActivityInfoBuyfreeGoodsMapper extends MyMapper<ActivityInfoBuyfreeGoods> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoBuyfreeGoods> getListByParams(Map<String,Object> params);
}