package cn.product.payment.dao;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.ChannelAccount;

public interface ChannelAccountDao extends IDao<ChannelAccount>{
	
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
	public List<ChannelAccount> getListByParams(Map<String, Object> params);
	
	/**
	 * 重启暂停账号
	 * @return
	 */
	public void rebootAllSuspend();
}
