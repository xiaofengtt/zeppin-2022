package cn.zeppin.project.chinamobile.media.web.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.utility.DataTypeCheck;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionParam;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionParams;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionResult;
import cn.zeppin.project.chinamobile.media.web.controller.base.BaseResult.ActionResultStatusType;

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
			ActionResult<List<Entity>> result = new ActionResult<List<Entity>>();
			for (Annotation annotation : actionParams) {
				if (annotation.annotationType().equals(ActionParams.class)) {
					ActionParams params = (ActionParams) annotation;
					for (ActionParam param : params.value()) {
						if (!checkParam(request, response, param, result)) {
							return false;
						}

					}
				} else if (annotation.annotationType().equals(ActionParam.class)) {
					ActionParam param = (ActionParam) annotation;
					if (!checkParam(request, response, param, result)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean checkParam(HttpServletRequest request, HttpServletResponse response, ActionParam param,
			ActionResult<List<Entity>> result) {
		// TODO Auto-generated method stub
		if (!param.nullable()) {
			// 违反数据nullable约定
			if (!request.getParameterMap().containsKey(param.key())) {
				result.setStatus(ActionResultStatusType.FAILED);
				result.setMessage("缺少参数：" + param.key());
				result.setData(null);
				return false;
			}
			// 违反数据emptyable约定
			else if (!param.emptyable() && request.getParameter(param.key()).length() == 0) {
				result.setStatus(ActionResultStatusType.FAILED);
				result.setMessage("参数" + param.key() + "：不能为空");
				result.setData(null);
				return false;
			}
			// 数据类型校验不通过
			else if (request.getParameter(param.key()).length() > 0
					&& !DataTypeCheck.checkDataType(request.getParameter(param.key()), param.type())) {
				result.setStatus(ActionResultStatusType.FAILED);
				result.setMessage("参数" + param.key() + "校验错误");
				result.setData(null);
				return false;
			}
		} else {
			// 可空参数如果传参了才进行数据校验
			if (request.getParameterMap().containsKey(param.key())) {
				if (!param.emptyable() && request.getParameter(param.key()).length() == 0) {
					result.setStatus(ActionResultStatusType.FAILED);
					result.setMessage("参数" + param.key() + "：不能为空");
					result.setData(null);
					return false;
				}
				// 传数据了才校验
				else if (request.getParameter(param.key()).length() > 0
						&& (!DataTypeCheck.checkDataType(request.getParameter(param.key()), param.type()))) {
					result.setStatus(ActionResultStatusType.FAILED);
					result.setMessage("参数" + param.key() + "校验错误");
					result.setData(null);
					return false;
				}
			}
		}
		return true;
	}
}
