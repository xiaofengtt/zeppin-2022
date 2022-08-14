/**
 * 
 */
package cn.zeppin.product.ntb.qcb.dao.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IQcbEmployeeDAO extends IBaseDAO<QcbEmployee, String> {
	
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
	 * 根据openId获取信息
	 * @param openId
	 * @return
	 */
	public QcbEmployee getByOpenId(String openId);
	
	/**
	 * 根据mobile获取信息
	 * @param mobile
	 * @return
	 */
	public QcbEmployee getByMobile(String mobile);
	
	/**
	 * 验证同手机号的用户是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistQcbEmployeeByMobile(String mobile, String uuid);
	
	/**
	 * 验证同身份证号的用户是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistQcbEmployeeByIdcard(String idcard, String uuid);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	public List<Entity> getListForNTBPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	 
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	public Integer getCountForNTB(Map<String, String> inputParams);
	
	/**
	 * 状态查询
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusListForNTB(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 更新昨日余额
	 */
	public void updateYesterdayAccount();
	
	/**
	 * 获取活期募集总额
	 */
	public Double getTotalCurrentAccount();
	
	/**
	 * 获取用户总余额
	 * @return
	 */
	public BigDecimal getTotalBalacne();
}
