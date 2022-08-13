/**
 * 
 */
package cn.zeppin.product.ntb.core.controller.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import cn.zeppin.product.ntb.core.entity.BkOperator;

/**
 * @author Clark.R 2016年3月29日
 *
 */
public class BaseController implements Controller{

	/**
	 * 
	 */

	protected BkOperator getCurrentOperator() {
		/**
		 * 取公共缓存中的Session，确定
		 */
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return (BkOperator) session.getAttribute("currentOperator");
	}

}
