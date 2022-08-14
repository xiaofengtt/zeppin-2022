package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.AuctionGameGoods;

public interface AuctionGameGoodsDao extends IDao<AuctionGameGoods> {
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<AuctionGameGoods> getListByParams(Map<String, Object> params);
}
