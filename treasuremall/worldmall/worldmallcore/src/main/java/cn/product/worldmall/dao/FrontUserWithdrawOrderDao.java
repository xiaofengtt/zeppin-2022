package cn.product.worldmall.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserWithdrawOrder;

public interface FrontUserWithdrawOrderDao extends IDao<FrontUserWithdrawOrder>{
	
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
	public List<FrontUserWithdrawOrder> getListByParams(Map<String, Object> params);
	
	/**
	 * 审核入库
	 * @param fuwo
	 * @param fua
	 * @param fuh
	 * @return
	 */
	public void check(FrontUserWithdrawOrder fuwo, FrontUserAccount fua, FrontUserHistory fuh);
	
	/**
	 * 提现下单
	 * @param fuwo
	 */
	public void withdraw(FrontUserWithdrawOrder fuwo);
	
	/**
	 * 根据日期取充值总额
	 * @param dateStr
	 * @return
	 */
	public BigDecimal getAmountByParams(String dateStr);
}
