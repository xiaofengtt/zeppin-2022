package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.CapitalAccount;

public interface CapitalAccountService extends IService<CapitalAccount>{
	
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
	 * 清空日累计量
	 */
	public void dailyRefrush();
}
