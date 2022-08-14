/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IQcbCompanyAccountHistoryService extends IBaseService<QcbCompanyAccountHistory, String> {
	
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
	 * 根据参数查询提现结果列表
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForWithdrawPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	
	
	/**
	 * 校验订单号是否已存在
	 * @param orderNum
	 * @return
	 */
	public Boolean checkOrderNum(String orderNum);
	
	/**
	 * 企业账户提现
	 * @param qcbCompanyAccount
	 * @param qcbOrderinfoByThirdparty
	 * @param qcbCompanyAccountHistory
	 */
	public void insertWithdraw(QcbCompanyAccount qca, QcbOrderinfoByThirdparty qobt, QcbCompanyAccountHistory qcah) throws ParseException, TransactionException;

	/**
	 * 融宝支付-代付通知接口
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertReapalNotice4Pay(Map<String, Object> map) throws ParseException,TransactionException, NumberFormatException, Exception;
	
	/**
	 * 统计用查询语句
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForCountPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	
	/**
	 * 企财宝企业提现状态列表
	 * @param searchMap
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getWithdrawStatusList(Map<String, String> searchMap, Class<? extends Entity> resultClass);
	
	/**
	 * 企财宝企业提现批量重试
	 * @param qehList
	 * @return
	 */
	public HashMap<String, Object> updateWithdrawBatch(List<QcbCompanyAccountHistory> qehList) throws Exception;
	
	/**
	 * 企财宝企业提现批量设置失败
	 * @param qehList
	 * @return
	 */
	public void updateWithdrawBatchRevoke(List<QcbCompanyAccountHistory> qehList) throws Exception;

	/**
	 * 批量更新操作
	 * @param qehList
	 */
	public void updateBatch(List<QcbCompanyAccountHistory> qehList);
	
	/**
	 * 批量提交处理成功
	 * @param pa
	 * @param paf
	 * @param qehList
	 * @param caList
	 * @param cahList
	 * @param codeList
	 */
	public void updateWithdraw(List<QcbCompanyAccountHistory> qehList) throws TransactionException, Exception;

}
