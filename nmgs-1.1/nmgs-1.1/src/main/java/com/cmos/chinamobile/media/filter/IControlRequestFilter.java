package com.cmos.chinamobile.media.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmos.chinamobile.media.control.impl.ValidateResultImpl;
import com.cmos.core.bean.xml.Control;
import com.cmos.core.bean.xml.ValidateResult;
import com.cmos.core.filter.IControlRequest;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.ControlFactory;
import com.cmos.core.util.ControlRequestUtil;

@SuppressWarnings("rawtypes")
public class IControlRequestFilter implements Filter {
	private static Logger logger = LoggerFactory.getServiceLog(IControlRequestFilter.class);
	private static IControlRequest controlRequest = null;
	private static Control control = null;
	private static String errorUrl = null;

	public void destroy() {
		control = null;
		ControlFactory.clearControl();
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		ValidateResultImpl result = null;

		String uid = request.getParameter("uid");
		if ((uid == null) || ("".equals(uid.trim()))) {
			System.err.println("传入的uid为空，参数非法!");
			result = new ValidateResultImpl("-9999", "参数非法！");
		} else if (controlRequest != null) {
			String path = request.getRequestURI();
			if (path.startsWith(request.getContextPath())) {
				path = path.substring(request.getContextPath().length(),
						path.length());
			}
			try {
				ValidateResult results = controlRequest.execute(request, control, path, uid,
						getRequestParamters(request));
				result = new ValidateResultImpl(results.getReturnCode(), results.getReturnMessage());
			} catch (Exception e) {
				logger.warn("返回错误",e);
				String requestType = request.getHeader("X-Requested-With");
				if ((requestType == null) && (errorUrl != null)
						&& (!"".equals(errorUrl.trim()))) {
					RequestDispatcher rd = request
							.getRequestDispatcher(errorUrl);
					rd.forward(request, response);
					return;
				}
				result = new ValidateResultImpl("-9999", e.getMessage());
			}
		}

		if ((result != null) && (!"success".equals(result.getStatus())))
			try {
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().print(result.toJson());
				return;
			} catch (IOException e) {
				logger.warn("返回错误",e);
			}
		chain.doFilter(arg0, arg1);
	}

	public void init(FilterConfig config) throws ServletException {
		String controlRequestImpl = "com.cmos.core.filter.ControlRequestDefaultImpl";
		Locale locale = new Locale("zh", "CN");

		String controlImplParam = config.getInitParameter("controlRequestImpl");

		String controlPathParam = config.getInitParameter("controlFilePath");

		String localParam = config.getInitParameter("locale");

		errorUrl = config.getInitParameter("errorUrl");

		if ((controlImplParam != null) && (!"".equals(controlImplParam.trim()))) {
			controlRequestImpl = controlImplParam;
		}
		if ((controlPathParam == null) || ("".equals(controlPathParam.trim()))) {
			controlPathParam = "config/control.xml";
		}
		if ("en_US".equalsIgnoreCase(localParam)) {
			locale = new Locale("en", "US");
		}
		try {
			controlRequest = (IControlRequest) Class
					.forName(controlRequestImpl).newInstance();

			control = ControlFactory.getControl(controlPathParam);
			ControlRequestUtil.setLocale(locale);
		} catch (Exception e) {
			logger.warn("返回错误",e);
			System.err.println("ControlFactory.init Error:" + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, String[]> getRequestParamters(HttpServletRequest request) {
		Map params = new HashMap();
		Enumeration enums = request.getParameterNames();
		while (enums.hasMoreElements()) {
			String name = (String) enums.nextElement();
			String[] value = request.getParameterValues(name);
			params.put(name, value);
		}
		return params;
	}
}