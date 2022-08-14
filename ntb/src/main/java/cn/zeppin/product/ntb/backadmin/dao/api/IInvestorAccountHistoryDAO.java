/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IInvestorAccountHistoryDAO extends IBaseDAO<InvestorAccountHistory, String> {
	/**
	 * 根据参数查询InvestorAccountHistory总数
	 * @param inputParams
	 * @return Integer
	 */
	public Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	 
		/**
	 * 计算月收入
	 * @param token
	 * @return
	 */
	public Double getTotalReturnByMonth4Investor(String uuid);
	
	/**
	 * 计算年收入
	 * @param token
	 * @return
	 */
	public Double getTotalReturnByYear4Investor(String uuid);
	
	/**
	 * 校验订单号是否已存在
	 * @param orderNum
	 * @return
	 */
	public Boolean getCheckOrderNum(String orderNum);
	
	/**
	 * 提现列表查询
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForWithdrawPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass);
	
	/**
	 * 获取操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 根据订单编号获取账单信息
	 * @param orderNum
	 * @param resultClass
	 * @return
	 */
	public InvestorAccountHistory getByOrderNum(String orderNum, Class<? extends Entity> resultClass);
	
	/**
	 * 提现列表查询
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForCountPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass);
	
	/**
	 * 根据参数查询InvestorAccountHistory总数
	 * @param inputParams
	 * @return Integer
	 */
	public Integer getCountByInvestor(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	public List<Entity> getListForPageByInvestor(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	/**
	 * 批量处理数据
	 * @param paras
	 */
	public void insert(List<Object[]> paras);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	public List<Entity> getListForPageByUser(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	/**
	 * 获取活期募集总额
	 */
	public Double getCurrentTotalAmount();
}
