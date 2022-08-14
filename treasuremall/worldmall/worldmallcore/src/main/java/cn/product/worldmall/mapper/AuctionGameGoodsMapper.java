package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.AuctionGameGoods;
import cn.product.worldmall.util.MyMapper;

public interface AuctionGameGoodsMapper extends MyMapper<AuctionGameGoods> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<AuctionGameGoods> getListByParams(Map<String, Object> params);
}