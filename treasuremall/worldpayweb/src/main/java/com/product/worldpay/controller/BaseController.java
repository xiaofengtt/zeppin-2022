/**
 * 
 */
package com.product.worldpay.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.product.worldpay.control.TransferService;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;
import com.product.worldpay.entity.Admin;
import com.product.worldpay.entity.CompanyAdmin;

public abstract class BaseController implements Controller{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private TransferService transferService;
	
	public Result execute(InputParams params) {
		try {
			return this.transferService.execute(params);
		} catch (Exception e) {
			logger.error("", "Invoke Service Error.", params.getService() + "." + params.getMethod(), e);
		}
		return null;
	}
	
	public Result apiExecute(InputParams params) {
		try {
			return this.transferService.apiExecute(params);
		} catch (Exception e) {
			logger.error("", "Invoke Service Error.", params.getService() + "." + params.getMethod(), e);
		}
		return null;
	}
	/**
	 * 取公共缓存中的管理员
	 */
	protected Admin getSystemAdmin() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return (Admin) session.getAttribute("systemAdmin");
	}
	
	/**
	 * 取公共缓存中的商户管理员
	 */
	protected CompanyAdmin getCompanyAdmin() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return (CompanyAdmin) session.getAttribute("companyAdmin");
	}
}
