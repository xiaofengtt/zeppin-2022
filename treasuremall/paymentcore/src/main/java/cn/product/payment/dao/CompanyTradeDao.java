package cn.product.payment.dao;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.CompanyTrade;
import cn.product.payment.util.api.PaymentException;

public interface CompanyTradeDao extends IDao<CompanyTrade>{
	
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
	public List<CompanyTrade> getListByParams(Map<String, Object> params);
	
	/**
	 * 处理
	 * @param companyTrade
	 * @return
	 */
	public void insertTrade(CompanyTrade companyTrade) throws PaymentException;
	
	/**
	 * 处理
	 * @param companyTrade
	 * @param status
	 * @return
	 */
	public void processOrder(CompanyTrade companyTrade, String status) throws PaymentException;
}
