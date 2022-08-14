package cn.product.worldmall.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.util.ParamsCheckUtl;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.WebUtil;
import cn.product.worldmall.api.base.ActionParams;
import cn.product.worldmall.api.base.ErrorResult;

public class CheckParamInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 检查Request请求的参数的合法性
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Annotation[] actionParams = method.getAnnotations();
			ErrorResult errorResult = new ErrorResult();
			for (Annotation annotation : actionParams) {
				if (annotation.annotationType().equals(ActionParams.class)) {
					ActionParams params = (ActionParams) annotation;
					for (ActionParam param : params.value()) {
						if (!checkParam(request, response, param, errorResult)) {
							String json = JSON.toJSONString(errorResult, true);
							WebUtil.sendJson(response, json);
							return false;
						}
					}
				} else if (annotation.annotationType().equals(ActionParam.class)) {
					ActionParam param = (ActionParam) annotation;
					if (!checkParam(request, response, param, errorResult)) {
						String json = JSON.toJSONString(errorResult, true);
						WebUtil.sendJson(response, json);
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean checkParam(HttpServletRequest request, HttpServletResponse response, ActionParam param,	ErrorResult result) {
		String paramValue = request.getParameter(param.key());
		/**
		 * 如果请求中不带接口中要求必须的参数，则返回错误信息
		 */
		if (!request.getParameterMap().containsKey(param.key())) {
			if (param.required()) {
				result.setErrorCode("090");
				result.setMessage("缺少参数：" + param.message() + "!");
				return false;
			}
			//【重要】如果实际请求没有传此参数，而此参数又不是required，则返回true通过验证。
			else return true;
		}
		/**
		 * 如果请求中参数含有接口上注解的参数，则进行请求中的参数的数据合法性验证
		 */
		else {
			if (param.required() && Utlity.checkStringNull(paramValue)) {
				result.setErrorCode("091");
				result.setMessage("参数值：" + param.message() + "不能为空！");
				return false;
			}
			//最小长度验证
			if (paramValue.length() < param.minLength()){
				result.setErrorCode("099");
				result.setMessage("参数长度：" + param.message() + "小于长度下限值（" + param.minLength() + "）!");
				return false;
			}
			//最大长度验证
			if  (paramValue.length() > param.maxLength()){
				result.setErrorCode("100");
				result.setMessage("参数长度：" + param.message() + "大于长度上限值（" + param.maxLength() + "）!");
				return false;
			}
			//参数值类型验证（正则表达式）
			if (!ParamsCheckUtl.checkDataType(paramValue, param.type())) {
				result.setErrorCode("101");
				result.setMessage("参数类型" + param.message() + "校验错误，需要" + param.type() + "类型的值！");
				return false;
			}
			//数值型的参数取值范围验证
			if (Utlity.isNumeric(paramValue) && !DataType.STRING.equals(param.type())) {
				if ((new BigDecimal(paramValue)).compareTo(BigDecimal.valueOf(param.maxValue())) > 0) {
					//参数值超过上限
					result.setErrorCode("102");
					result.setMessage("参数值：" + param.message() + "数值过大!");
					return false;
				}
				
				if ((new BigDecimal(paramValue)).compareTo(BigDecimal.valueOf(param.minValue())) < 0) {
					//参数值超过上限
					result.setErrorCode("103");
					result.setMessage("参数值：" + param.message() + "数值过小!");
					return false;
				}
			}
			//validate
		}
			
		return true;
	}
}
