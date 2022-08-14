package cn.product.treasuremall.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserRechargeOrder;

public interface FrontUserRechargeOrderDao extends IDao<FrontUserRechargeOrder>{
	
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
	public List<FrontUserRechargeOrder> getListByParams(Map<String, Object> params);
	
	/**
	 * 人工处理
	 * @param furo
	 * @param fua
	 * @param fuh
	 */
	public void check(FrontUserRechargeOrder furo, FrontUserAccount fua, FrontUserHistory fuh) throws ParseException;
	
	/**
	 * 获取首冲统计数据
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getFirstListByParams(Map<String, Object> params);
	
	/**
	 * 是否参与过首充
	 * @param frontUser
	 * @return
	 */
	public Boolean isPartakeFirstcharge(String frontUser);
	
	/**
	 * 根据日期取充值总额
	 * @param dateStr
	 * @return
	 */
	public BigDecimal getAmountByParams(String dateStr);
	
	/**
	 * 是否七天内首充
	 * @param frontUser
	 * @return
	 */
	public Boolean isInSevenDayFirstcharge(FrontUser frontUser);
	
	/**
	 * 根据参数获取总数,查询指定天的记录数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams4Days(String frontUser, String capitalAccount, Integer days);
}
