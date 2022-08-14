/**
 * 
 */
package cn.zeppin.product.score.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.entity.FrontUser;

public class BaseController implements Controller{
	
	/**
	 * 取公共缓存中的管理员
	 */
	protected Admin getCurrentOperator() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return (Admin) session.getAttribute("backAdmin");
	}
	
	/**
	 * 取公共缓存中的前端用户
	 */
	protected FrontUser getFrontUser(HttpServletRequest request) {
		return (FrontUser) request.getAttribute("frontUser");
	}
}
