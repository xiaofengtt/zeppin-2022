package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SsoUserPay;

public interface ISsoUserPayDAO extends IBaseDAO<SsoUserPay, Integer> {

	/**
	 * 检查用户是否购买学科
	 * @param searchMap
	 * @return count
	 */
	Integer searchSsoUserPaySubject(Map<String, Object> searchMap);

	/**
	 * 获取用户购买试卷列表
	 * @param searchMap
	 * @return supList
	 */
	List<SsoUserPay> searchSsoUserPayPaper(Map<String, Object> searchMap);

}
