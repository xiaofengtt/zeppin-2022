/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author Clark.R 2016年9月21日
 *
 */
public interface IBkOperatorDAO extends IBaseDAO<BkOperator, String> {

	/**
	 * 通过登录账号得到Operator对象，登录账号可能是账号、手机号或邮箱
	 * @param loginname
	 * @return BkOperator
	 */
	BkOperator getByLoginname(String loginname);
	
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
	 * 测试
	 * @param user
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @param resultClass
	 * @return
	 */
	List<Entity> getRoleCount(BkOperator user, String sorts, int offset, int pagesize, Class<? extends Entity> resultClass);

	/**
	 * 验证同名的管理员是否已经存在
	 * @param name
	 * @param uuid
	 * @return
	 */
	boolean isExistOperatorByName(String name, String uuid);
	
	/**
	 * 验证同手机号的管理员是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	boolean isExistOperatorByMobile(String mobile, String uuid);
}
