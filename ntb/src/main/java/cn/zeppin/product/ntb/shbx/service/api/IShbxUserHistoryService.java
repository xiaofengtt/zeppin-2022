/**
 * 
 */
package cn.zeppin.product.ntb.shbx.service.api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.ShbxOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.ShbxUserBankcard;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IShbxUserHistoryService extends IBaseService<ShbxUserHistory, String> {
	
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
	
//	/**
//	 * 社保熊用户提现
//	 * @param qcbEmployee
//	 * @param qcbOrderinfoByThirdparty
//	 * @param qcbEmployeeHistory
//	 * @return
//	 */
//	public void insertWithdraw(ShbxUser qe, ShbxOrderinfoByThirdparty obt, ShbxUserHistory qeh);
	
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
	 * 社保熊用户提现状态列表
	 * @param searchMap
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getWithdrawStatusList(Map<String, String> searchMap, Class<? extends Entity> resultClass);
	
	/**
	 * 社保熊用户提现批量重试
	 * @param qehList
	 * @return
	 */
	public HashMap<String, Object> updateWithdrawBatch(List<ShbxUserHistory> qehList) throws Exception;
	
	/**
	 * 社保熊用户提现批量设置失败
	 * @param qehList
	 * @return
	 */
	public void updateWithdrawBatchRevoke(List<ShbxUserHistory> qehList) throws Exception;

	/**
	 * 批量更新操作
	 * @param qehList
	 */
	public void updateBatch(List<ShbxUserHistory> qehList);
	
	/**
	 * 批量提交处理成功
	 * @param pa
	 * @param paf
	 * @param qehList
	 * @param caList
	 * @param cahList
	 * @param codeList
	 */
	public void updateWithdraw(List<ShbxUserHistory> qehList) throws TransactionException, Exception;
	
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
	
//	/**
//	 * 购买统一下单(余额)
//	 * @param token
//	 * @return
//	 * @throws TransactionException 
//	 */
//	public void insertProductBuy4Balance(MobileCode omc, BankFinancialProductPublish bfpp, ShbxUser qe, ShbxUserHistory qeh, ShbxUserProductBuyAgreement qepba, ShbxUserProductBuy qepb, Boolean isUpdate, Boolean isUpdate2, MobileCode mc, ShbxUserCoupon qec) throws TransactionException;

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
	
	public void insertwechart(ShbxOrderinfoByThirdparty qobt, ShbxUserHistory qeh,  ShbxUserBankcard qeb, ShbxSecurityOrder sso);
	
	public void insertwechart(ShbxOrderinfoByThirdparty qobt, ShbxUserHistory qeh, ShbxSecurityOrder sso);
	
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
	public void updateWithdraw(ShbxOrderinfoByThirdparty obt, ShbxOrderinfoByThirdparty obtN, ShbxUserHistory qeh, ShbxSecurityOrder sso);
	
	/**
	 * 融宝支付-快捷通知接口(社保缴费)
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertReapalNotice4RechargeShbx(Map<String, Object> map) throws ParseException,TransactionException, NumberFormatException, Exception;

}
