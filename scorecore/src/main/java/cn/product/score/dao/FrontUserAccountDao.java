package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.FrontUserAccount;

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
	 * 根据用户获取账户
	 * @param frontUser
	 * @return
	 */
	public FrontUserAccount getByFrontUser(String frontUser);
}
