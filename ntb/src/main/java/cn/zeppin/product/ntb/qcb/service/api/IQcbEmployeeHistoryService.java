/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCoupon;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IQcbEmployeeHistoryService extends IBaseService<QcbEmployeeHistory, String> {
	
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
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCountForWithdraw(Map<String, String> inputParams);
	
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
	 * 企财宝员工提现
	 * @param qcbEmployee
	 * @param qcbOrderinfoByThirdparty
	 * @param qcbEmployeeHistory
	 * @return
	 */
	public void insertWithdraw(QcbEmployee qe, QcbOrderinfoByThirdparty obt, QcbEmployeeHistory qeh);
	
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

	public void insertSmsInfo(MobileCode mc) throws TransactionException;
	
	/**
	 * 企财宝员工提现状态列表
	 * @param searchMap
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getWithdrawStatusList(Map<String, String> searchMap, Class<? extends Entity> resultClass);
	
	/**
	 * 企财宝员工提现批量重试
	 * @param qehList
	 * @return
	 */
	public HashMap<String, Object> updateWithdrawBatch(List<QcbEmployeeHistory> qehList) throws Exception;
	
	/**
	 * 企财宝员工提现批量设置失败
	 * @param qehList
	 * @return
	 */
	public void updateWithdrawBatchRevoke(List<QcbEmployeeHistory> qehList) throws Exception;

	/**
	 * 批量更新操作
	 * @param qehList
	 */
	public void updateBatch(List<QcbEmployeeHistory> qehList);
	
	/**
	 * 批量提交处理成功
	 * @param pa
	 * @param paf
	 * @param qehList
	 * @param caList
	 * @param cahList
	 * @param codeList
	 */
	public void updateWithdraw(List<QcbEmployeeHistory> qehList) throws TransactionException, Exception;
	
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
	 * 购买统一下单(余额)
	 * @param token
	 * @return
	 * @throws TransactionException 
	 */
	public void insertProductBuy4Balance(MobileCode omc, BankFinancialProductPublish bfpp, QcbEmployee qe, QcbEmployeeHistory qeh, QcbEmployeeProductBuyAgreement qepba, QcbEmployeeProductBuy qepb, Boolean isUpdate, Boolean isUpdate2, MobileCode mc, QcbEmployeeCoupon qec) throws TransactionException;

	/**
	 * 融宝支付-代付通知接口(还款)
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertReapalNotice4RePay(Map<String, Object> map) throws ParseException,TransactionException, NumberFormatException, Exception;
	
	public void insertwechart(QcbOrderinfoByThirdparty qobt, QcbEmployeeHistory qeh,  QcbEmployeeBankcard qeb);
	
	public void insertwechart(QcbOrderinfoByThirdparty qobt, QcbEmployeeHistory qeh);
	
	/**
	 * 融宝支付-快捷通知接口
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertReapalNotice4Recharge(Map<String, Object> map) throws ParseException,TransactionException, NumberFormatException, Exception;
	
	/**
	 * 员工充值-失败处理
	 * @param obt
	 * @param obtN
	 * @param qeh
	 */
	public void updateWithdraw(QcbOrderinfoByThirdparty obt, QcbOrderinfoByThirdparty obtN, QcbEmployeeHistory qeh);
}
