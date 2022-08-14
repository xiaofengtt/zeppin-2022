package com.cmos.chinamobile.media.action;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.cmos.chinamobile.media.action.base.ActionResult;
import com.cmos.chinamobile.media.control.IControlService;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.bean.xml.Output;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.ControlConstants;
import com.cmos.core.util.JsonUtil;
import com.cmos.core.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * 
 */
public abstract class BaseAction extends ActionSupport implements BeanFactoryAware{
	private static final long serialVersionUID = 1581119741116374826L;
	private static final Logger logger = LoggerFactory.getActionLog(BaseAction.class);
	private IControlService controlService; // 前后工程调用服务
	private InputObject inputObject;
	private BeanFactory factory;

	/** Get the request Object **/
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/** Get the response Object **/
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/** Get the current Session **/
	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/** Get the current Session **/
	public HttpSession getSession(boolean arg0) {
		return getRequest().getSession(arg0);
	}

	/** Get the Servlet Context **/
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	/** Get the Value Stack of Struts2 **/
	public ValueStack getValueStack() {
		return ServletActionContext.getValueStack(getRequest());
	}
	
	/** Set the Bean Factory **/
	public void setBeanFactory(BeanFactory factory) {
		this.factory = factory;
	}

	/** Get the IControlService Object **/
	public IControlService getControlService() {
		return controlService;
	}

	public void setControlService(IControlService controlService) {
		this.controlService = controlService;
	}

	/** Print OutputStream to the Browser **/
	public void sendJson(String json) {
		try {
			getResponse().setContentType("application/json");
			getResponse().getWriter().print(json);
			logger.info("sendJson", json);
		} catch (IOException e) {
			logger.error("sendJson", "Exception Occured When Send Json to Client !", e);
		}
		long start = logger.getLoggerParam().getStart();
		long end = System.currentTimeMillis();
		logger.info("INVOKE SECCESS! COST=" + (end - start) + "ms");
	}

	/** Get InputObject Object Encapsulated **/
	public InputObject getInputObject() {
		inputObject = (InputObject) ServletActionContext.getContext().get(ControlConstants.INPUTOBJECT);
		String clientIP = getIpAddr();
		inputObject.getParams().put("ip", clientIP);
		return inputObject;
	}

	@SuppressWarnings("unchecked")
	public ActionResult<Object> getActionResult() {
		ActionResult<Object> result = new ActionResult<Object>();
		OutputObject output = getOutputObject();
		if("0".equals(output.getReturnCode())){
			if(output.getBean() != null && !output.getBean().isEmpty()){
				result.setData(output.getBean());
			}else{
				result.setData(output.getBeans());
			}
//			result.setData(output.getBean());
			result.setStatus("success");
			result.setMessage("获取成功");
		}else{
			result.setStatus("fail");
			result.setMessage(output.getReturnMessage());
		}
		if(output.getObject() != null){
			Map<String, Object> resultMap = (Map<String, Object>) output.getObject();
			try {
				if(resultMap.containsKey("pageNum") && resultMap.get("pageNum") != null){
					if(!"".equals(resultMap.get("pageNum").toString())){
						result.setPageNum(Integer.parseInt(resultMap.get("pageNum").toString()));
					}
				}
				if(resultMap.containsKey("pageSize") && resultMap.get("pageSize") != null){
					if(!"".equals(resultMap.get("pageSize").toString())){
						result.setPageSize(Integer.parseInt(resultMap.get("pageSize").toString()));
					}
				}
				
				if(resultMap.containsKey("totalPageCount") && resultMap.get("totalPageCount") != null){
					if(!"".equals(resultMap.get("totalPageCount").toString())){
						result.setTotalPageCount(Integer.parseInt(resultMap.get("totalPageCount").toString()));
					}
				}
				
				if(resultMap.containsKey("totalResultCount") && resultMap.get("totalResultCount") != null){
					if(!"".equals(resultMap.get("totalResultCount").toString())){
						result.setTotalResultCount(Integer.parseInt(resultMap.get("totalResultCount").toString()));
					}
				}
			} catch (Exception e) {
				logger.error("", "分页数据有误", e);
				result.setStatus("fail");
				result.setMessage("分页数据获取错误");
				result.setData("");
			}
			
		}
		return result;
	}
	/** Call Services and Get OutputObject Object **/
	public OutputObject getOutputObject() {
		return getOutputObject(getInputObject());
	}

	/** Call Services and Get OutputObject Object **/
	public OutputObject getOutputObject(InputObject inputObject) {
		OutputObject outputObject = null;
		String cacheKey = inputObject.getValue(ControlConstants.CACHE_KEY);
		if (cacheKey == null) {
			outputObject = this.execute(inputObject);
		} else {// Cache
//			try {
//				outputObject = getCacheService().getFromCache(cacheKey, OutputObject.class);
//				if (outputObject == null) {
//					outputObject = this.execute(inputObject);
					// Need Cache
//					getCacheService().put2Cache(cacheKey, outputObject, 30*60);
//				}
//			} catch (Exception e) {
//				logger.error("获取缓存异常", "", e);
//			}
		}
		return outputObject;
	}
	
	private OutputObject execute(InputObject inputObject) {
		OutputObject outputObject = null;
		try {
			if (ControlConstants.CMS_SERVICE.equalsIgnoreCase(inputObject.getService())) {//CMS
				Object object = factory.getBean(inputObject.getService());
				Method mth = object.getClass().getMethod(inputObject.getMethod(),
						InputObject.class);
				outputObject = (OutputObject) mth.invoke(object, inputObject);
			} else {// WEBSERVICE
				outputObject = getControlService().execute(inputObject);
			}
		} catch (Exception e) {
			logger.error("", "Invoke Service Error.", inputObject.getService() + "." + inputObject.getMethod(), e);
		}
		return outputObject;
	}
	
	/**
	 * Json String Unified Conversion Method
	 * @param outputObject
	 * @return Json
	 */
	public String convertOutputObject2Json(OutputObject outputObject) {
		return convertOutputObject2Json(getInputObject(), outputObject);
	}

	/**
	 * Json String Unified Conversion Method
	 * @param outputObject
	 * @return Json
	 */
	public String convertOutputObject2Json(InputObject inputObject,OutputObject outputObject) {
		String json = "";
		if (outputObject == null || inputObject == null){
			return json;
		}

		Output output = (Output) ServletActionContext.getContext().get(ControlConstants.OUTPUT);
		try {
			// 如果配置了相应的IConvertor则执行，否则执行默认的Json转换功能
			if (output != null && StringUtil.isNotEmpty(output.getConvertor())) {
				json = JsonUtil.outputObject2Json(output.getConvertor(),
						output.getMethod(), inputObject, outputObject);
			}else{
				json = JsonUtil.outputObject2Json(outputObject);
			}
		} catch (Exception e) {
			logger.error("convertOutputObject", "Convert Output Error.", "", e);
		}
		return json;
	}
	
	private String getIpAddr() {   
		String ipAddress ;   
		//ipAddress = this.getRequest().getRemoteAddr();   
		ipAddress = this.getRequest().getHeader("x-forwarded-for");   
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
			ipAddress = this.getRequest().getHeader("Proxy-Client-IP");   
		}   
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
			ipAddress = this.getRequest().getHeader("WL-Proxy-Client-IP");   
		}   
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
			ipAddress = this.getRequest().getRemoteAddr();  
			String ip = "127.0.0."+"1";
			if(ipAddress.equals(ip)){   
			//根据网卡取本机配置的IP   
				InetAddress inet=null;   
				try {   
					inet = InetAddress.getLocalHost();  
					ipAddress= inet.getHostAddress();  
				} catch (Exception e) {   
					logger.error("getIpAddr", e); 
			    }   
				 
			}   
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
		if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
			if(ipAddress.indexOf(',')>-1){   
				ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
			}   
		}   
		return ipAddress;    
	}  
}
