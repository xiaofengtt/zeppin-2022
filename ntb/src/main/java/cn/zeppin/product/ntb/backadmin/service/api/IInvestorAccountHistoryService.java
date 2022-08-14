/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon;
import cn.zeppin.product.ntb.core.entity.InvestorInformation;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.InvestorRedPacket;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author hehe
 *
 */
public interface IInvestorAccountHistoryService extends IBaseService<InvestorAccountHistory, String> {
	
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
	 * 充值统一下单
	 * @param token
	 * @return
	 */
	public void insertwechart(OrderinfoByThirdparty obt, InvestorAccountHistory iah);
	
	/**
	 * 充值统一下单（融宝支付）
	 * @param obt
	 * @param iah
	 * @param ib
	 */
	public void insertwechart(OrderinfoByThirdparty obt, InvestorAccountHistory iah, InvestorBankcard ib);
	
	/**
	 * 充值微信通知
	 * @param token
	 * @return
	 * @throws ParseException 
	 * @throws TransactionException 
	 */
	public HashMap<String, Object> insertWechartNotice(Map<String, Object> map) throws ParseException, TransactionException;
	
	/**
	 * 购买统一下单(余额)
	 * @param token
	 * @return
	 * @throws TransactionException 
	 */
	public void insertProductBuy4Balance(MobileCode omc, BankFinancialProductPublish bfpp, Investor investor, InvestorAccountHistory iah, InvestorProductBuyAgreement ipba, InvestorProductBuy ipb, Boolean isUpdate, Boolean isUpdate2, PlatformAccount pai, MobileCode mc, InvestorInformation iii, InvestorCoupon ic) throws TransactionException;
	
	/**
	 * 购买统一下单(微信)
	 * @param token
	 * @return
	 */
	public void insertProductBuy4Wechart(BankFinancialProductPublish bfpp, OrderinfoByThirdparty obt, InvestorAccountHistory iah, InvestorAccountHistory iahBuy);
	
	/**
	 * 购买微信通知
	 * @param token
	 * @return
	 * @throws ParseException 
	 * @throws TransactionException 
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public HashMap<String, Object> insertWechartNotice4Buy(Map<String, Object> map) throws ParseException, TransactionException, NumberFormatException, Exception;
	
	/**
	 * 购买畅捷支付通知
	 * @param token
	 * @return
	 * @throws ParseException 
	 * @throws TransactionException 
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public HashMap<String, Object> insertChanPayNotice4Buy(Map<String, String> map) throws ParseException, TransactionException, NumberFormatException, Exception;

	
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
	 * 用户提现
	 * @param token
	 * @return
	 */
	public void insertWithdraw(Investor investor, OrderinfoByThirdparty obt, InvestorAccountHistory iah);
	
	
	public void updateBatch(List<InvestorAccountHistory> listUpdate);
	
	public void insertBatch(List<InvestorAccountHistory> listHistory);
	
	/**
	 * 畅捷支付-转账到卡通知接口
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertChanPayNotice4Pay(Map<String, String> map) throws ParseException,TransactionException, NumberFormatException, Exception;
	
	public HashMap<String, Object> insertChanPayNotice4Recharge(Map<String, String> map) throws ParseException,TransactionException, NumberFormatException, Exception;
	
	
	public void insertTestRecharge(OrderinfoByThirdparty obt, InvestorAccountHistory iah, Investor investor, CompanyAccount ca, CompanyAccountHistory cah, PlatformAccount pa, PlatformAccount paf, PlatformAccount pai);
	
	public void insertTestWithdraw(OrderinfoByThirdparty obt, InvestorAccountHistory iah, Investor investor, CompanyAccount ca, CompanyAccountHistory cah, CompanyAccountHistory cahp, PlatformAccount pa, PlatformAccount paf, PlatformAccount pai);

	public List<Entity> getListForWithdrawPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass);
	
	/**
	 * 获取操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	public HashMap<String, Object> updateWithdrawBatch(List<InvestorAccountHistory> iahList) throws TransactionException, Exception;
	
	/**
	 * 根据订单编号获取账单信息
	 * @param orderNum
	 * @param resultClass
	 * @return
	 */
	public InvestorAccountHistory getByOrderNum(String orderNum, Class<? extends Entity> resultClass);
	
	public void updateWithdraw(List<InvestorAccountHistory> iahList) throws TransactionException, Exception;
	
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

	public void updateOrderinfoAliTransferStatus();
	
	/**
	 * 发送验证码
	 * @param mobile
	 * @param content
	 */
	public void insertSmsInfo(MobileCode mc) throws TransactionException;
	
	public void insertActive(List<InvestorCoupon> listCoupon, List<InvestorRedPacket> listRed, Investor investor) throws TransactionException;
	
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
	 * 根据参数查询用户投资总数
	 * @param inputParams
	 * @return Integer
	 */
	public Integer getCountByUser(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询用户投资列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	public List<Entity> getListForPageByUser(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	/**
	 * 充值操作--失败处理
	 * @param obt
	 * @param iah
	 */
	public void updatewechart(OrderinfoByThirdparty obt, OrderinfoByThirdparty obtN, InvestorAccountHistory iah);
}
