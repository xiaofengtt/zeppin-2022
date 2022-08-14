/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IQcbCompanyAccountDAO extends IBaseDAO<QcbCompanyAccount, String> {
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	 
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 根据mobile获取信息
	 * @param mobile
	 * @return
	 */
	public QcbCompanyAccount getByMerchantId(String merchantId);
	
	/**
	 * 验证同ID的企业用户是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistQcbCompanyAccountByMerchantId(String merchantId, String uuid);
	
	/**
	 * 验证同名称的企业用户是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistQcbCompanyAccountByName(String name, String uuid);
	
	/**
	 * 获取分状态统计数据
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 获取分状态统计数据
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	List<Entity> getCheckStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 获取企业员工用户总余额
	 * @return
	 */
	public BigDecimal getTotalBalacneByEmp(Map<String, String> inputParams);
}
