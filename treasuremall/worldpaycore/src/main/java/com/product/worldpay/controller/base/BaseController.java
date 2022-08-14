/**
 * 
 */
package com.product.worldpay.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.product.worldpay.entity.Admin;
import com.product.worldpay.entity.CompanyAdmin;

public class BaseController implements Controller{
	
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
	protected CompanyAdmin getCompanyAdmin(HttpServletRequest request) {
		return (CompanyAdmin) request.getAttribute("companyAdmin");
	}
}
