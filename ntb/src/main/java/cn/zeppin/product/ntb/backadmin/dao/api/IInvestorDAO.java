/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IInvestorDAO extends IBaseDAO<Investor, String> {
	
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
	 * 获取银行理财产品分状态列表
	 * @param resultClass
	 * @return
	 */
	List<Entity> getStatusList(Class<? extends Entity> resultClass);
	
	public Investor getByOpenID(String openID, Class<? extends Entity> resultClass);
	
	/**
	 * 根据username获取信息
	 * @param openID
	 * @return
	 */
	public Investor getByMobile(String mobile, Class<? extends Entity> resultClass);
	
	/**
	 * 根据支付宝账号获取用户信息
	 * @param openID
	 * @param resultClass
	 * @return
	 */
	public Investor getByAliUserid(String userid, Class<? extends Entity> resultClass);
	
	/**
	 * 验证同手机号的用户是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	public boolean isExistInvestorByMobile(String mobile, String uuid);
	
	/**
	 * 获取状态列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getNotuploadCount(Class<? extends Entity> resultClass);
	
	/**
	 * 更新昨日余额
	 */
	public void updateYesterdayAccount();
	
	/**
	 * 获取活期募集总额
	 */
	public Double getTotalCurrentAccount();
}
