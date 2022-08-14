package com.cmos.chinamobile.media.interceptor;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cmos.chinamobile.media.util.Constants;
import com.cmos.chinamobile.media.util.ExceptionUtil;
import com.cmos.chinamobile.media.util.PropertiesUtil;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.xml.Action;
import com.cmos.core.bean.xml.Input;
import com.cmos.core.bean.xml.Parameter;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.logger.ThreadLoggerParam;
import com.cmos.core.util.ControlConstants;
import com.cmos.core.util.ControlRequestUtil;
import com.cmos.core.util.ConvertUtil;
import com.cmos.core.util.StringUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 */
public class AuthorityInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 8259007565097878289L;
	private static final Logger logger = LoggerFactory.getUtilLog(AuthorityInterceptor.class);

	public String intercept(ActionInvocation invocation) throws Exception {
		// 初始化loggerParam
		initLogParam();
		logger.info("INVOKE START!");
		// 创建InputObject对象
		createInputObject();

		String namespace = invocation.getProxy().getNamespace();
		if (Constants.NAMESPACE_SHARE.equals(namespace)) {
			return invocation.invoke();
		}

		return invocation.invoke();
	}

	private void createInputObject() throws ExceptionUtil {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String path = request.getRequestURI();

		// 处理请求的URI
		if (path.endsWith(".action")) {
			path = path.substring(0, path.indexOf(".action"));
		}

		if (path.startsWith(request.getContextPath())) {
			path = path.substring(request.getContextPath().length(),
					path.length());
		}
		
		// 根据请求的路径取得配置的Action
		Action action;
		try {
			action = ControlRequestUtil.getActionByPath(path);
		} catch (Exception e) {
			logger.error("错误", e);
			throw new ExceptionUtil(e.getMessage());
		}

		// 获取特定的请求参数UID
		String uid = request.getParameter(ControlConstants.UID);
		if (StringUtil.isEmpty(uid)) {
			uid = null;
		}
		
		// 根据请求组装InputObject对象
		InputObject inputObject = new InputObject();

		if (action != null) {
			Input input;
			try {
				input = ControlRequestUtil.getInputByUID(uid, action);
			} catch (Exception e) {
				logger.error("错误", e);
				throw new ExceptionUtil(e.getMessage());
			}
			processRequestParamters(request, inputObject, input);

			if (input != null) {
				if (StringUtil.isNotEmpty(input.getCode())) {
					inputObject.getParams().put("busiCode", input.getCode());
				}

				inputObject.setService(input.getService());
				inputObject.setMethod(input.getMethod());
			}
			ServletActionContext.getContext().put(ControlConstants.OUTPUT, ControlRequestUtil.getOutputByUID(uid, action));
		}
		
		inputObject.getLogParams().put(ControlConstants.OP_REQST_NO, logger.getLoggerParam().getOpReqstNo());
		inputObject.getLogParams().put("OP_USER_NO", getUserNo(request));
		// 
		ServletActionContext.getContext().put(ControlConstants.INPUTOBJECT, inputObject);
	}

	/**
	 * 处理参数
	 */
	private void processRequestParamters(
			HttpServletRequest request, InputObject inputObject, Input input) {
		List<Parameter> parameters = this.getParameters(request, input);
		StringBuilder cacheKey = new StringBuilder();
		for (Parameter p : parameters) {
			Object object;
			String key = p.getKey();
			String toKey = p.getToKey();
			String scope = p.getScope();
			if (StringUtil.isEmpty(key)) {
				continue;
			}
			try {
				String[] values = request.getParameterValues(key);
				if (values == null || values.length == 1) {
					if (scope != null && ControlConstants.PARAM_SCOPE.SESSION.equals(scope)) {// 从Session读取参数
						String[] keys = key.split("[.]");
						//TODO 待优化
//						if ("user".equals(keys[0])) {
//							object = SecurityUtils.getSubject().getPrincipal();
//						}else {
							object = request.getSession().getAttribute(keys[0]);
//						}
						for (int i = 1; i < keys.length; i++) {
							if (object == null) {
								break;
							}
							Method method = object.getClass().getMethod(ConvertUtil.getMethodByField(keys[i]));
							object = method.invoke(object);
						}
					} else if (scope != null
							&& ControlConstants.PARAM_SCOPE.CONSTANTS.equals(scope)) {// 从常量类中读�?
						Field field = Class.forName("com.cmos.chinamobile.media.util.Constants").getDeclaredField(key);
						object = field.get(field.getName());
					} else if (scope != null && ControlConstants.PARAM_SCOPE.PROPERTIES.equals(scope)) {// 从配置文件中读取
						object = PropertiesUtil.getString(key);
					} else if (scope != null && ControlConstants.PARAM_SCOPE.FILE.equals(scope)) {// 上传文件
						Map<String, Object> fileMap = ServletActionContext.getContext().getParameters();
						File[] files = (File[]) fileMap.get(key);
						if (files == null || files.length == 0) {
							continue;
						}
						
						String[] contentTypes = (String[]) fileMap.get(key + ControlConstants.FILE.CONTENTTYPE);
						String[] fileNames = (String[]) fileMap.get(key + ControlConstants.FILE.FILENAME);
						if (files.length == 1) {// 单个文件
							inputObject.getParams().put(key, files[0]);
							inputObject.addParams(key + ControlConstants.FILE.CONTENTTYPE, 
									toKey == null ? null : toKey + ControlConstants.FILE.CONTENTTYPE, contentTypes[0]);
							inputObject.addParams(key + ControlConstants.FILE.FILENAME, 
									toKey == null ? null : toKey + ControlConstants.FILE.FILENAME, fileNames[0]);
						} else {// 多个文件
							for (int i = 0; i < files.length; i++) {
								object = ConvertUtil.file2String(files[i]);
								inputObject.addBeans(key, toKey, i, String.valueOf(object));
								inputObject.addBeans(key + ControlConstants.FILE.CONTENTTYPE,
										toKey == null ? null : toKey + ControlConstants.FILE.CONTENTTYPE,
										i, contentTypes[i]);
								inputObject.addBeans(key + ControlConstants.FILE.FILENAME,
										toKey == null ? null : toKey + ControlConstants.FILE.FILENAME,
										i, fileNames[i]);
							}
						}
						continue;
					} else {// 从request获取数据
						object = request.getParameter(key);
					}
					String value = object == null? "" : String.valueOf(object);
					inputObject.addParams(key, toKey, String.valueOf(value));//单行数据封装到Map<String,String> param
					cacheKey.append(key).append(String.valueOf(value));
					inputObject.addBeans(key, toKey, 0, String.valueOf(value));
				} else {// 多行数据提交
					for (int i = 0; i < values.length; i++) {
						inputObject.addBeans(key, toKey, i, values[i]);
					}
				}
				if (input.isCache()){
					inputObject.addParams(ControlConstants.CACHE_KEY, null, cacheKey.toString());
				}
			} catch (Exception e) {
				logger.error("processRequestParamters", "", e);
			}
		}
	}
	
	private List<Parameter> getParameters(HttpServletRequest request, Input input) {
		List<Parameter> params = new ArrayList<Parameter>();
		if (ControlConstants.INPUT_SCOPE.REQUEST.equals(input.getScope())) {// 从request中取key
			Enumeration<String> enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();
//				if (!ControlConstants.UID.equals(key))
				params.add(new Parameter(key, input.getToKeyByKey(key)));
			}
			for (Parameter param : input.getParameters()) {
				if (param.getScope() != null && !ControlConstants.PARAM_SCOPE.REQUEST.equals(param.getScope())){
					params.add(param);
				}
			}
		} else if (input.getScope() == null || ControlConstants.INPUT_SCOPE.CONTROL.equals(input.getScope())) {
			params.add(new Parameter(ControlConstants.UID, null));
			params.addAll(input.getParameters());
		}
		return params;
	}
	
	/**
	 * 初始化ThreadLoggerParam对象
	 */
	private void initLogParam(){
		HttpServletRequest request = ServletActionContext.getRequest();
		ThreadLoggerParam loggerParam = logger.getLoggerParam();
		loggerParam.setStart(System.currentTimeMillis());
		loggerParam.setService(getPath(request));
		loggerParam.setMethod(getUID(request));
		loggerParam.setOpReqstNo(UUID.randomUUID().toString());
		loggerParam.setUserNo(getUserNo(request));
	}
	
	/** 获取请求地址*/
	protected String getPath(HttpServletRequest request) {
		String path = request.getRequestURI();
		if (path.startsWith(request.getContextPath())) {// 去除工程上下文路径
			path = path.substring(request.getContextPath().length(), path.length());
		}
		return path;
	}
	
	/** 获取请求唯一标识 */
	protected String getUID(HttpServletRequest request) {
		return request.getParameter(ControlConstants.UID);
	}
	
	/** 获取当前的用户编码*/
	protected String getUserNo(HttpServletRequest request) {
		// 目前是对Session的管理管理是两种方式：1、Servlet的Session; 2、Shiro的Session
		// TODO 获取当前用户的用户名 待完成
		/*Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){//如果用户已登录
			Object object = subject.getPrincipal();
			if (object != null) {
				User user = (User)object;
				return user.getLoginNm();
			}
		}*/
		return "";
	}
}