package cn.zeppin.product.score.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.controller.base.TransactionException;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistoryCheck;
import cn.zeppin.product.score.vo.back.StatusCountVO;

public interface FrontUserHistoryCheckService extends IService<FrontUserHistoryCheck>{
	
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
	public List<FrontUserHistoryCheck> getListByParams(Map<String, Object> params);
	
	/**
	 * 用户充值审核通过
	 * @param fuhc
	 */
	public void checkRecharge(FrontUserHistoryCheck fuhc);
	
	/**
	 * 用户提现审核通过
	 * @param fuhc
	 */
	public void checkWithdraw(FrontUserHistoryCheck fuhc);
	
	/**
	 * 获取分状态列表
	 * @param params
	 * @return
	 */
	public List<StatusCountVO> getStatusList(Map<String, Object> params);
	
	/**
	 * 阿里云异步通知处理
	 * @param params
	 * @return
	 * @throws TransactionException 
	 */
	public HashMap<String, Object> rechargeNoticeByAlipay(Map<String, String> params) throws UnsupportedEncodingException, TransactionException;
	
	/**
	 * 定时批处理订单信息
	 * @param params
	 */
	public void rechargeTask4Alipay(Map<String, Object> params);
	
	/**
	 * 批量提现
	 * @param qehList
	 * @return
	 */
	public HashMap<String, Object> updateWithdrawBatch(List<FrontUserHistory> list) throws Exception;
	
	/**
	 * 融宝支付-代付通知接口信息处理
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertReapalNotice4Pay(Map<String, Object> map) throws ParseException,TransactionException, NumberFormatException, Exception;

}
