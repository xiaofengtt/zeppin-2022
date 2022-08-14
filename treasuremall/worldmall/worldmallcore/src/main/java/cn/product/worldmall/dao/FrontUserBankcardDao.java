package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserBankcard;
import cn.product.worldmall.entity.MobileCode;

public interface FrontUserBankcardDao extends IDao<FrontUserBankcard>{
	
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
	public List<FrontUserBankcard> getListByParams(Map<String, Object> params);
	
	/**
	 * 绑卡
	 * @param fub
	 * @param mc
	 */
	public void insertFrontUserBankcard(FrontUserBankcard fub, MobileCode mc);
	
	/**
	 * 获取用户paypol账号
	 * @param frontUser
	 * @return
	 */
	public FrontUserBankcard getPaypal(String frontUser);
}
