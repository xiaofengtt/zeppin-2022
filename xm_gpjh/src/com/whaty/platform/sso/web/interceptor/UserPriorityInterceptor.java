package com.whaty.platform.sso.web.interceptor;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.junit.runner.Request;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.whaty.platform.entity.bean.PePriority;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class UserPriorityInterceptor extends MethodFilterInterceptor {
	/**
	 * Logger for this class
	 */

	private GeneralService<WhatyuserLog4j> generalService;
	public GeneralService<WhatyuserLog4j> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService<WhatyuserLog4j> generalService) {
		this.generalService = generalService;
	}
	
	private static final Log logger = LogFactory.getLog(UserPriorityInterceptor.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 1857640901215440713L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception{
		ActionContext ac = invocation.getInvocationContext();

		
		setExcludeMethods(SsoConstant.INTERCEPTOR_EXCLUDEMETHOD);

		String actionName = "/" + ac.getName();
		//xxx_xx.action 的形式
		int index = actionName.indexOf("_");
		if(index != -1 && index != 0){
			actionName = actionName.substring(0,index);
		}
		
		
		String namespace = invocation.getProxy().getNamespace();
		
		String method = "_"+ invocation.getProxy().getMethod();

		String saction = "";

		Map map = ac.getParameters();

		boolean havePermission = false;
		
		
		ServletContext servletContext = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		
		ResourceBundleMessageSource messageSource = (ResourceBundleMessageSource) wc.getBean("messageSource");
		
		if (wc == null) {
			logger.error("ApplicationContext could not be found.");
			return "error";
		} else {

			UserSession us = (UserSession) ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			
			if(namespace.indexOf("workspace")>=0){
				havePermission = true;
			}else{
				PePriority priority = (PePriority) us.getUserPriority().get(namespace + actionName + "_*.action");
				if (priority != null) {
					havePermission = true;
				} else {
					priority = (PePriority) us.getUserPriority().get(namespace + actionName+method + ".action");
					if (priority != null) {
						havePermission = true;
					} else {
						havePermission = false;
					}
				}
			}
			if (havePermission) {
				String result = invocation.invoke();
				
				//记录日志 (如果参数不是请求 _excute , _abstractList)
//				if(method!=null && !method.equals("_execute") && !method.equals("_abstractList")){
//					WhatyuserLog4j log = new WhatyuserLog4j();
//					log.setIp(ServletActionContext.getRequest().getRemoteAddr());
//					log.setBehavior(actionName+method);	//动作？
//					
//					StringBuffer req_parm = new StringBuffer();
//					Map req_map = ServletActionContext.getRequest().getParameterMap();
//					Iterator iterator = req_map.entrySet().iterator();
//					while(iterator.hasNext()){
//						java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
//						req_parm.append(entry.getKey().toString()+":"+((String[])entry.getValue())[0]+";");
//					}
//					log.setNotes(req_parm.toString());//说明；
//					String json;
//					try{
//						json = ((MyBaseAction)invocation.getAction()).getJsonString();
//					}catch(Exception e){
//						json = null;
//					}
//					
//					log.setStatus("????");
//					if(json!=null){
//						if(json.indexOf("\"success\":\"true\"")>0){
//							log.setStatus("true");
//						}else if(json.indexOf("\"success\":\"false\"")>0){
//							log.setStatus("false");
//						}
//					}else{
//						if(result.equals("msg")){
//							log.setNotes(((MyBaseAction)invocation.getAction()).getMsg());
//							if(log.getNotes()!=null&&log.getNotes().length()>2000){
//								log.setNotes(log.getNotes().substring(0, 2000));
//							}
//						}
//					}
//					
//					log.setOperateTime(new Date());	//时间
//					String logtype ="";
//					
//					log.setUserid(us.getLoginId());	//SSO LOGINID
//					log.setLogtype(us. getUserLoginType());
//					generalService.save(log);
//				}
				//记录日志
				return result;
			} else{
				String errorMsg = messageSource.getMessage("error.noPermission", null, ac.getLocale());
//				ac.getValueStack().set("interceptError", "对不起，您没有操作权限!");
				ac.getValueStack().set("interceptError", errorMsg);
				return "error";
			}
		}
	}

}
