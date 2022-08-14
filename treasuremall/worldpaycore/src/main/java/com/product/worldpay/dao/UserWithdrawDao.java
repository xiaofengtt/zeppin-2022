package com.product.worldpay.dao;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.UserWithdraw;
import com.product.worldpay.util.api.PaymentException;
import com.product.worldpay.vo.system.StatusCountVO;

public interface UserWithdrawDao extends IDao<UserWithdraw>{
	
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
	public List<UserWithdraw> getListByParams(Map<String, Object> params);
	
	/**
	 * 请求提现
	 * @param userWithdraw
	 * @return
	 */
	public void insertWithdraw(UserWithdraw userWithdraw);
	
	/**
	 * 处理提现
	 * @param userWithdraw
	 * @param status
	 * @return
	 */
	public void processOrder(UserWithdraw userWithdraw, String status) throws PaymentException;
	
	/**
	 * 获取待处理数据量
	 * @return
	 */
	public List<StatusCountVO> getCheckingChannelList();
}
