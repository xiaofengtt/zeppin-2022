package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserAccount;

public interface FrontUserAccountDao extends IDao<FrontUserAccount>{
	
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
	public List<FrontUserAccount> getListByParams(Map<String, Object> params);
	
	/**
	 * 更新用户账户状态
	 * @param params
	 * @return
	 */
	public void updateStatus(Map<String, Object> params);
	
	/**
	 * 根据ID更新对应字段信息
	 * @param frontUser
	 * @param params
	 */
	public void updateInfo(FrontUserAccount frontUserAccount, Map<String, Object> params);
}
