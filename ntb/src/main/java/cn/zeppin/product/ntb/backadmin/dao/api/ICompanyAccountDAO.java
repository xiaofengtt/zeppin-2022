/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 *
 */
public interface ICompanyAccountDAO extends IBaseDAO<CompanyAccount, String> {
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
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 验证企业账户号是否已经存在
	 * @param accountNum
	 * @param uuid
	 * @return
	 */
	boolean isExistAccountNum(String accountNum, String uuid);
}
