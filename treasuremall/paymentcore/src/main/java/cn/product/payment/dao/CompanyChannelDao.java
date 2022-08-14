package cn.product.payment.dao;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.CompanyChannel;
import cn.product.payment.entity.CompanyChannelAccount;

public interface CompanyChannelDao extends IDao<CompanyChannel>{
	
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
	public List<CompanyChannel> getListByParams(Map<String, Object> params);
	
	/**
	 * 新增商户渠道
	 * @param companyChannel
	 * @param companyChannelAccountList
	 * @return
	 */
	public void insertCompanyChannel(CompanyChannel companyChannel, List<CompanyChannelAccount> companyChannelAccountList);
	
	/**
	 * 修改商户渠道
	 * @param companyChannel
	 * @param companyChannelAccountList
	 * @return
	 */
	public void updateCompanyChannel(CompanyChannel companyChannel, List<CompanyChannelAccount> companyChannelAccountList);
}
