package cn.product.payment.dao;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.CompanyChannelAccount;

public interface CompanyChannelAccountDao extends IDao<CompanyChannelAccount>{
	
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
	public List<CompanyChannelAccount> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据商户渠道删除
	 * @param companyChannel
	 * @return
	 */
	public void deleteByCompanyChannel(String companyChannel);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<ChannelAccount> getChannelAccountListByParams(Map<String, Object> params);
}
