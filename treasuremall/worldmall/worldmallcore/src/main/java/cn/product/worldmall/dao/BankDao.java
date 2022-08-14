/**
 * 
 */
package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Bank;

/**
 *
 */
public interface BankDao extends IDao<Bank> {
	
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
