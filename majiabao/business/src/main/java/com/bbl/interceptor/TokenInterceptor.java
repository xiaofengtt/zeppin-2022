package com.bbl.interceptor;

import com.alibaba.fastjson.JSON;
import com.bbl.util.encrypt.MD5Util;
import com.bbl.util.RequestUtil;
import com.bbl.util.ratelimiter.RatelimiterUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {


	/**
	 * 默认key
	 */
	private static final String default_key = "fb2356ddf5scc5d4d2s9e@2scwu7io2c";


	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object arg) throws Exception {
		resp.setHeader("Content-type", "text/html;charset=UTF-8");


		double waitSeconde = RatelimiterUtil.rateLimiter.acquire ();
		log.info ("获取限流令牌时间："+waitSeconde);


		AuthInterceptor authUrl = null;
		if (arg instanceof HandlerMethod) {
			authUrl = ((HandlerMethod) arg).getMethodAnnotation(AuthInterceptor.class);
			//未标记注解的类无需拦截
			if (authUrl == null) {
				return true;
			}
			return true; //不拦截所有
		}

		try {
			String pathReq = req.getServletPath();
			String str = RequestUtil.getOrderParam(req);
			log.debug("请求路径  :{},排序后的参数：{}", pathReq, str);


			//如果是不需要登录的接口，又没有传参，则无须拦截
			if (!authUrl.needAuthTokenVerify()&& StringUtils.isBlank(str)) {
				return true;
			}

			String sign = req.getParameter("sign");//获取签名
			if(sign==null){
				Map<String,Object> map=new HashMap<>();
				map.put("errno","-1");
				map.put("errmsg","参数不正确");
				String data = JSON.toJSONString(map);
				resp.getWriter().println(data);
				resp.getWriter().close();
				return false;
			}

			//一定需要用户登录才能调用的接口
//			if (authUrl.needAuthTokenVerify()) {
//				String userToken = req.getParameter("user_token");//代表用户id
//				if(StringUtils.isBlank(userToken)){
//					Map<String,Object> map=new HashMap<>();
//					map.put("errno","-1");
//					map.put("errmsg","用户未登入");
//					String data = JSON.toJSONString(map);
//					resp.getWriter().println(data);
//					resp.getWriter().close();
//					return false;
//				}
//				//如果客户端传递user_token,redis没有取到user,则返回20012（会话失效）
//				UserInfo userinfo = (UserInfo) StaticJedisUtils.getObject("user_" + userToken);
//				if(userinfo==null){
//					Map<String,Object> map=new HashMap<>();
//					map.put("errno","-1");
//					map.put("errmsg","用户未登入,未找到用户token");
//					String data = JSON.toJSONString(map);
//					resp.getWriter().println(data);
//					resp.getWriter().close();
//					return false;
//				}
//				str = str + userinfo.getMd5Salt();
//			} else {//用户登录和不登录都可以调用的接口
//				String userToken = req.getParameter("user_token");//代表用户id
//				if (StringUtils.isNotBlank(userToken)) {
//
////					PlatformUser user = pfmUserUtil.getUserFromCacheJedis(userToken);
////					str = str + user.getMd5Salt();
//				} else {
//					str = str + default_key;
//				}
//			}

			if (!MD5Util.string2MD5(str, null).equals(sign)) {
				Map<String,Object> map=new HashMap<>();
				map.put("errno","-1");;
				map.put("errmsg","签名不合法");
				String data = JSON.toJSONString(map);
				resp.getWriter().println(data);
				resp.getWriter().close();
				return false;
			}
		} catch (Exception e) {
			Map<String,Object> map=new HashMap<> ();
			map.put("errno","-1");
			map.put("errmsg","请求被拦截,请稍后");
			String data = JSON.toJSONString(map);
			resp.getWriter().println(data);
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
