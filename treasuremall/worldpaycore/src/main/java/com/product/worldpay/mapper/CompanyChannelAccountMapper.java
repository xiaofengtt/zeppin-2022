/**
 * 
 */
package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.entity.CompanyChannelAccount;
import com.product.worldpay.util.MyMapper;

/**
 *
 */
public interface CompanyChannelAccountMapper extends MyMapper<CompanyChannelAccount> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CompanyChannelAccount> getListByParams(Map<String, Object> params);
	
	public void deleteByCompanyChannel(@Param("companyChannel")String companyChannel);
	
	public List<ChannelAccount> getChannelAccountListByParams(Map<String, Object> params);
}
