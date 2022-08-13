package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SsoUserPay;

public interface ISsoUserPayService {
	/**
	 * 添加用户支付
	 * @param ssoUserPay
	 */
	public void addSsoUserPay(SsoUserPay ssoUserPay);

	
	/**
	 * 检查用户是否购买学科
	 * @param searchMap
	 * @return count
	 */
	public Integer searchSsoUserPaySubject(Map<String, Object> searchMap);

	/**
	 * 获取用户购买试卷列表
	 * @param searchMap
	 * @return supList
	 */
	public List<SsoUserPay> searchSsoUserPayPaper(Map<String, Object> searchMap);
}
