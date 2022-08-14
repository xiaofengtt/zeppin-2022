package cn.product.payment.service;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.CompanyChannel;

public interface CompanyChannelService extends IService<CompanyChannel>{
	
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
}
