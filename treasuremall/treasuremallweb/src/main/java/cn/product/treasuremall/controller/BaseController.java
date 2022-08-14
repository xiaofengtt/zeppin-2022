/**
 * 
 */
package cn.product.treasuremall.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.control.TransferService;
import cn.product.treasuremall.entity.Admin;
import cn.product.treasuremall.vo.front.FrontUserVO;

public abstract class BaseController implements Controller{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/**
	 * (interfaceClass = ControlService.class, version="${treasuremall.service.version}", group="${treasuremall.service.group}")
	 * (url="${embedded.dubbo.url}", interfaceClass = ControlService.class, version="${treasuremall.service.version}")
	 */
//	@Reference(interfaceClass = ControlService.class, version="${treasuremall.service.version}", group="${treasuremall.service.group}")
	@Autowired
	private TransferService transferService;
	
//	@Autowired
//	private BeanFactory factory;
//
//	@Override
//	public void setBeanFactory(BeanFactory factory) throws BeansException {
//		this.factory = factory;
//	}
	
	public Result execute(InputParams params) {
		try {
			return this.transferService.execute(params);
		} catch (Exception e) {
			logger.error("", "Invoke Service Error.", params.getService() + "." + params.getMethod(), e);
		}
		return null;
	}

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
	protected FrontUserVO getFrontUser(HttpServletRequest request) {
		return (FrontUserVO) request.getAttribute("frontUser");
	}
}
