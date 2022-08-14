/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IQcbEmployeeService extends IBaseService<QcbEmployee, String> {
	
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
	public QcbEmployee getByMobile(String mobile);
	
	/**
	 * 根据openId获取信息
	 * @param openId
	 * @return
	 */
	public QcbEmployee getByOpenId(String openId);
	
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
	 * 登录保存信息
	 * @param mc
	 * @param qe
	 * @param employee
	 */
	public void updateLogin(MobileCode mc, QcbEmployee qe, QcbEmployee employee);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<Entity> getListForNTBPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	 
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCountForNTB(Map<String, String> inputParams);
	
	/**
	 * 状态查询
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	List<Entity> getStatusListForNTB(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	public void updateBatch(List<QcbEmployee> listUpdate);
	
	/**
	 * 余额自动转入活期理财
	 */
	public void updateCurrentAccount();
	
	/**
	 * 更新活期理财
	 */
	public void updateCurrentPay(QcbEmployee qe, QcbEmployeeHistory qeh);
	
	/**
	 * 更新昨日余额
	 */
	public void updateYesterdayAccount();
	
	/**
	 * 获取用户总余额
	 * @return
	 */
	public BigDecimal getTotalBalacne();
}
