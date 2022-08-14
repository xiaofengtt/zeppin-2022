package com.makati.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.makati.business.user.entity.User;
import com.makati.common.annotion.AuthInterceptor;
import com.makati.common.entity.PlatformUser;
import com.makati.common.exception.BusinessException;
import com.makati.common.response.Response;
import com.makati.common.util.MD5Util;
import com.makati.common.util.lottery.AssertUtil;
import com.makati.common.util.lottery.ErrorCodeUtil;
import com.makati.common.util.lottery.PfmUserUtil;
import com.makati.common.util.lottery.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

	@Autowired
	private PfmUserUtil pfmUserUtil;

	/**
	 * 登陆和注册的key值
	 */
	private static final String default_key = "fb2356ddf5scc5d4d2s9e@2scwu7io2c";

	/**
	 * 拦截所有的方法，用于校验请求是否合法
	 * 若检验不合法；返回20011
	 * 若user_token无效，返回20012
	 * 若需要user_token,但却没传，则返回 20010
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object arg) throws Exception {

		String pathReq1 = req.getServletPath();
		User user1 = (User) req.getSession().getAttribute("USER");
		if (user1 == null){
			resp.sendRedirect(req.getContextPath()+"/");
			return false;
		}

		AuthInterceptor authUrl = null;
		if (arg instanceof HandlerMethod) {
			authUrl = ((HandlerMethod) arg).getMethodAnnotation(AuthInterceptor.class);
			if (authUrl == null) {
				return true;
			}
		}

		try {
			String pathReq = req.getServletPath();
			if(authUrl == null){
				logger.error("错误的请求路径 {}",pathReq);
				return true;
			}
			String str = RequestUtil.getOrderParam(req);
			logger.debug("请求路径  :{},排序后的参数：{}", pathReq, str);

			if (authUrl == null) {
				return true;
			}
			//如果是不需要登录的接口，又没有传参，则无须拦截
			if (!authUrl.needAuthTokenVerify()&& StringUtils.isBlank(str)) {
				return true;
			}

			String sign = req.getParameter("sign");//获取签名
			AssertUtil.isNotBlank(sign);
			//一定需要用户登录才能调用的接口
			if (authUrl.needAuthTokenVerify()) {
				String userToken = req.getParameter("user_token");//代表用户id
				if(StringUtils.isBlank(userToken)){
					throw new BusinessException(ErrorCodeUtil.USER_NOT_LOGIN,"用户未登录");
				}
				PlatformUser user = pfmUserUtil.getUserFromCacheJedis(userToken);
				str = str + user.getMd5Salt();
			} else {//用户登录和不登录都可以调用的接口
				String userToken = req.getParameter("user_token");//代表用户id
				if (StringUtils.isNotBlank(userToken)) {
					//如果客户端传递user_token,redis没有取到user,则返回20012（会话失效）
					PlatformUser user = pfmUserUtil.getUserFromCacheJedis(userToken);
					str = str + user.getMd5Salt();
				} else {
					str = str + default_key;
				}
			}

			if (!MD5Util.string2MD5(str, null).equals(sign)) {
				throw new BusinessException(ErrorCodeUtil.ILEGAL_SIGN_ERROR_CODE);
			}
		} catch (Exception e) {
//			String result = null;
			Response result = null;
			if (e instanceof BusinessException) {
				logger.info("TokenInterceptor preHandle errorCode  :{}", e.getMessage());
				BusinessException businessException=(BusinessException)e;
//				if(StringUtils.isNotBlank(businessException.getMessage())){
//
//					result = JSON.toJSONString(ErrorResponse.createErrorResponse(e.getMessage(),businessException.getMessage()));
//				}else{
//
//					result = JSON.toJSONString(ErrorResponse.errorMap.get(e.getMessage()));
//				}
				result = Response.failResponce(businessException.getCode(),businessException.getMessage());
			} else {
				logger.error("TokenInterceptor preHandle :", e);
//				result = JSON.toJSONString(ErrorCodeUtil.SYSTEM_ERROR_CODE);
				result = Response.failResponce(ErrorCodeUtil.SYSTEM_ERROR_CODE,"网络错误");
			}
			resp.setHeader("Content-type", "text/html;charset=UTF-8");
			resp.getWriter().println(JSON.toJSONString(result));
			resp.getWriter().close();
			return false;
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	}
}
