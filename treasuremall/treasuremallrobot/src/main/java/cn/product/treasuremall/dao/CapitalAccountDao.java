package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.CapitalAccount;

public interface CapitalAccountDao extends IDao<CapitalAccount>{
	
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
	public List<CapitalAccount> getListByParams(Map<String, Object> params);
	
	/**
	 * 添加账户信息
	 * @param capitalAccount
	 */
	public void insertWithStatistics(CapitalAccount capitalAccount);
}
