package cn.product.worldmall.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.entity.AdminOffsetOrder;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfoReceive;

public interface FrontUserHistoryDao extends IDao<FrontUserHistory>{
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<FrontUserHistory> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<FrontUserHistory> getLeftListByParams(Map<String, Object> params);
	
	/**
	 * 用户充值入库
	 * @param fuh
	 * @param type
	 * @return
	 */
	public void recharge(FrontUserHistory fuh, String type);
	
	/**
	 * 用户提现入库
	 * @param fuh
	 * @return
	 */
	public void withdraw(FrontUserHistory fuh);
	
	/**
	 * 用户充值入库
	 * @param fuh
	 * @param fua
	 */
	public void recharge(List<AdminOffsetOrder> aooList, List<FrontUserHistory> fuhList, List<FrontUserAccount> fuaList);
	
	/**
	 * 聚合支付宝充值异步消息存储
	 * @param paramsls
	 * @return
	 */
	public Map<String, Object> rechargeNoticeByUnion(Map<String, String> paramsls);
	
	/**
	 * 聚合支付宝充值异步消息存储
	 * @param paramsls
	 * @return
	 */
	public Map<String, Object> rechargeNoticeByAcicpay(Map<String, String> paramsls);
	
	/**
	 * 金樽充值异步消息存储
	 * @param paramsls
	 * @return
	 */
	public Map<String, Object> rechargeNoticeByJinzun(Map<String, String> paramsls);
	
	/**
	 * Stripe充值异步消息存储
	 * @param paramsls
	 * @return
	 */
	public Map<String, Object> rechargeNoticeByStripe(Map<String, String> paramsls);
	
	/**
	 * 聚合支付宝充值异步消息处理
	 * @param paramsls
	 * @return
	 */
	public Map<String, Object> rechargeByUnion(Map<String, Object> params) throws TransactionException, ParseException;
	
	/**
	 * 聚合支付宝充值异步消息处理
	 * @param paramsls
	 * @return
	 */
	public Map<String, Object> rechargeByAcicpay(Map<String, Object> params) throws TransactionException, ParseException;
	
	/**
	 * 金樽支付充值异步消息处理
	 * @param paramsls
	 * @return
	 */
	public Map<String, Object> rechargeByJinzun(Map<String, Object> params) throws TransactionException, ParseException;
	
	/**
	 * Stripe支付充值异步消息处理
	 * @param paramsls
	 * @return
	 */
	public Map<String, Object> rechargeByStripe(Map<String, Object> params) throws TransactionException, ParseException;
	
	/**
	 * 聚合支付宝提现异步消息存储
	 * @param paramsls
	 * @return
	 */
	public Map<String, Object> withdrawNoticeByUnion(Map<String, String> paramsls);
	
	
	/**
	 * 聚合支付宝提现异步消息处理
	 * @param paramsls
	 * @return
	 */
	public Map<String, Object> withdrawByUnion(Map<String, Object> params) throws TransactionException;
	
	/**
	 * 机器人自动兑奖
	 */
	public void receiveByRobot(Map<String, FrontUserAccount> accountMap, List<WinningInfo> updateWin, List<WinningInfoReceive> insertReceive
			, List<FrontUserHistory> insertHistory);

	/**
	 * 定时处理订单
	 * @param params
	 */
	public void rechargeTaskByUnion(Map<String, Object> params);
	
	/**
	 * 定时处理订单
	 * @param params
	 */
	public void withdrawTaskByUnion(Map<String, Object> params);

	/**
	 * 根据订单查询流水
	 * @param orderId
	 * @return
	 */
	public FrontUserHistory getByOrderId(String orderId); 
	
	/**
	 * 根据参数取总额
	 * @param dateStr
	 * @param orderType
	 * @return
	 */
	public BigDecimal getAmountByParams(String dateStr, String orderType);
	
	/**
	 * 获取用户有记录的月份列表
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getMonthListByParams(Map<String, Object> params);
	

	/**
	 * 处理订单
	 * @param userRecharge
	 * @param status
	 * @return
	 */
	public void rechargeByWorldpay(FrontUserRechargeOrder frontUserRechargeOrder, String status) throws TransactionException, ParseException;
}
