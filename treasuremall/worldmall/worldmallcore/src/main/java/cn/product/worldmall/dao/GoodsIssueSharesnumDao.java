package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.GoodsIssueSharesnum;

public interface GoodsIssueSharesnumDao extends IDao<GoodsIssueSharesnum> {
	
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
    public List<GoodsIssueSharesnum> getListByParams(Map<String, Object> params);
}
