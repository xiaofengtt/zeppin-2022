package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISsoUserPayDAO;
import cn.zeppin.entity.SsoUserPay;
import cn.zeppin.service.api.ISsoUserPayService;

public class SsoUserPayService implements ISsoUserPayService {
	private ISsoUserPayDAO ssoUserPayDAO;

	public ISsoUserPayDAO getSsoUserPayDAO() {
		return ssoUserPayDAO;
	}

	public void setSsoUserPayDAO(ISsoUserPayDAO ssoUserPayDAO) {
		this.ssoUserPayDAO = ssoUserPayDAO;
	}
	
	/**
	 * 添加用户支付数据
	 * @param ssoUserPay
	 */
	public void addSsoUserPay(SsoUserPay ssoUserPay){
		this.getSsoUserPayDAO().save(ssoUserPay);
	}
	
	/**
	 * 检查用户是否购买学科
	 * @param searchMap
	 * @return count
	 */
	public Integer searchSsoUserPaySubject(Map<String, Object> searchMap){
		return this.getSsoUserPayDAO().searchSsoUserPaySubject(searchMap);
	}
	
	/**
	 * 获取用户购买试卷列表
	 * @param searchMap
	 * @return supList
	 */
	public List<SsoUserPay> searchSsoUserPayPaper(Map<String, Object> searchMap){
		return this.getSsoUserPayDAO().searchSsoUserPayPaper(searchMap);
	}
}
