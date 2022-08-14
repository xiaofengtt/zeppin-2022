package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.LuckygameGoods;
import cn.product.worldmall.util.MyMapper;

public interface LuckygameGoodsMapper extends MyMapper<LuckygameGoods> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<LuckygameGoods> getListByParams(Map<String,Object> params);
}