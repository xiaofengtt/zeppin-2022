package cn.product.payment.service;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.Company;

public interface CompanyService extends IService<Company>{
	
	/**
	 * 根据code获取
	 * @param code
	 * @return
	 */
	public Company getByCode(String code);
	
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
	public List<Company> getListByParams(Map<String, Object> params);
	
	/**
	 * 新建商户
	 * @param company
	 * @return
	 */
	public void insertCompany(Company company);
	
	/**
	 * 获取商户支付密钥
	 * @param company
	 * @return
	 */
	public Map<String, String> getCompanyKeys(Company company);
}
