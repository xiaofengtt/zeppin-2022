/**
 * 
 */
package cn.product.payment.service;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.Bank;

/**
 *
 */
public interface BankService extends IService<Bank> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<Bank> getListByParams(Map<String, Object> params);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 验证同名的银行信息是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	public boolean isExistBankByName(String name, String uuid);

}
