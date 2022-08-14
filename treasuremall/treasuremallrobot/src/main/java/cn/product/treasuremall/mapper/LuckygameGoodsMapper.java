package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.util.MyMapper;

public interface LuckygameGoodsMapper extends MyMapper<LuckygameGoods> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<LuckygameGoods> getListByParams(Map<String,Object> params);
}