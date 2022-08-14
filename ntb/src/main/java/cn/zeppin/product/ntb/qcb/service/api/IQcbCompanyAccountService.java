/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IQcbCompanyAccountService extends IBaseService<QcbCompanyAccount, String> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);
	
	
	/**
	 * 根据mobile获取信息
	 * @param mobile
	 * @return
	 */
	public QcbCompanyAccount getByMerchantId(String merchantId);
	
	/**
	 * 验证同ID的企财宝企业是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistQcbCompanyAccountByMerchantId(String merchantId, String uuid);
	
	/**
	 * 验证同名称的企财宝企业是否已经存在
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
	public List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 更新绑定虚拟账户
	 * @param qcbCompanyAccount
	 * @param qcbVirtualAccounts
	 */
	public void updateVirtualAccount(QcbCompanyAccount qca, QcbVirtualAccounts qva);
	
	/**
	 * 发起认证
	 * @param qca
	 * @param qco
	 */
	public void update(QcbCompanyAccount qca, QcbCompanyOperate qco);
	
	/**
	 * 获取分状态统计数据
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	List<Entity> getCheckStatusList(Class<? extends Entity> resultClass);
	
	/**
	 * 获取企业员工用户总余额
	 * @param inputParams
	 * @return
	 */
	public BigDecimal getTotalBalacneByEmp(Map<String, String> inputParams);
}
