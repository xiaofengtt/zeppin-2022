package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.GoodsCoverImage;

public interface GoodsDao extends IDao<Goods> {
	
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
    public List<Goods> getListByParams(Map<String, Object> params);
    
    /**
	 * 添加商品
	 * @param goods
	 * @param gciList
	 */
    public void insertGoods(Goods goods, List<GoodsCoverImage> gciList);
    
    /**
	 * 修改商品
	 * @param goods
	 * @param gciList
	 */
    public void updateGoods(Goods goods, List<GoodsCoverImage> gciList);
}
