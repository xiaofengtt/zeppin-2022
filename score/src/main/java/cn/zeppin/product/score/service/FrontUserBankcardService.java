package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.FrontUserBankcard;
import cn.zeppin.product.score.entity.MobileCode;

public interface FrontUserBankcardService extends IService<FrontUserBankcard>{
	
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
}
