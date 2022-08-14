/**
 * 
 */
package cn.product.score.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.api.service.ControlService;

public abstract class BaseController implements Controller{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/**
	 * (interfaceClass = ControlService.class, version="${scoreserver.service.version}", group="${scoreserver.service.group}")
	 * (url="${embedded.dubbo.url}", interfaceClass = ControlService.class, version="${scoreserver.service.version}")
	 */
	@Reference(interfaceClass = ControlService.class, version="${scoreserver.service.version}", group="${scoreserver.service.group}")
	private ControlService controlService;
	
//	@Autowired
//	private BeanFactory factory;
//
//	@Override
//	public void setBeanFactory(BeanFactory factory) throws BeansException {
//		this.factory = factory;
//	}
	
	public Result execute(InputParams params) {
		try {
			return this.controlService.execute(params);
		} catch (Exception e) {
			logger.error("", "Invoke Service Error.", params.getService() + "." + params.getMethod(), e);
		}
		return null;
	}

	public ControlService getControlService() {
		return controlService;
	}

	public void setControlService(ControlService controlService) {
		this.controlService = controlService;
	}
}
