package cn.product.payment.service;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.NoticeInfo;
import cn.product.payment.entity.UserRecharge;

public interface UserRechargeService extends IService<UserRecharge>{
	
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
	public List<UserRecharge> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据订单号获取
	 * @param orderNum
	 * @return
	 */
	public UserRecharge getByOrderNum(String orderNum);
	
	/**
	 * 处理订单
	 * @param userRecharge
	 * @param noticeInfo
	 * @param status
	 * @return
	 */
	public void processOrder(UserRecharge userRecharge, NoticeInfo noticeInfo, String status);
	
}
