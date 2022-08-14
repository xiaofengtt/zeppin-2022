package cn.product.payment.dao;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.CompanyAdmin;

public interface CompanyAdminDao extends IDao<CompanyAdmin>{
	
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
	public List<CompanyAdmin> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据手机号获取商户管理员
	 * @param mobile
	 * @return
	 */
	public CompanyAdmin getByMobile(String mobile);
}
