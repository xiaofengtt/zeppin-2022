/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.RealpalNoticeInfo;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IRealpalNoticeInfoService extends IBaseService<RealpalNoticeInfo, String>{
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass);
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);
	
	public boolean isExistRealpalNoticeInfo(String name, String uuid);
	
	/**
	 * 批量更新
	 * @param listUpdate
	 */
	public void updateAll(List<RealpalNoticeInfo> listUpdate);
	
	/**
	 * 融宝支付-牛投理财用户提现异步通知信息
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertReapalNotice4PayInvestor(
			Map<String, Object> map) throws ParseException,
			TransactionException, NumberFormatException, Exception;
	
	/**
	 * 融宝支付-企财宝企业用户提现异步通知信息
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertReapalNotice4PayQcb(
			Map<String, Object> map) throws ParseException,
			TransactionException, NumberFormatException, Exception;
	
	/**
	 * 融宝支付-企财宝员工用户提现异步通知信息
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertReapalNotice4PayQcbEmp(
			Map<String, Object> map) throws ParseException,
			TransactionException, NumberFormatException, Exception;
	
	/**
	 * 融宝支付-用户提现异步通知信息
	 * @param map
	 * @param type
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertReapalNotice4Pay(
			Map<String, Object> map, String type) throws ParseException,
			TransactionException, NumberFormatException, Exception;
	
	/**
	 * 
	 * @param map
	 * @param type
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertReapalNotice4Recharge(
			Map<String, Object> map, String type) throws ParseException,
			TransactionException, NumberFormatException, Exception;
}
