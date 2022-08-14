package cn.product.payment.service;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.UserWithdraw;
import cn.product.payment.util.api.PaymentException;

public interface UserWithdrawService extends IService<UserWithdraw>{
	
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
}
