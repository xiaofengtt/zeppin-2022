package cn.zeppin.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParams;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityCheck;
import cn.zeppin.authority.AuthorityInfo;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.authority.UserGroup;
import cn.zeppin.entity.User;
import cn.zeppin.utility.DataTypeCheck;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class SuperAdminRightInterceptor extends MethodFilterInterceptor
{
	
	private static final long serialVersionUID = -2698400025235658420L;
	
	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception
	{
		ActionContext actionContext = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
		
		String methodName = ai.getProxy().getConfig().getMethodName();
		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");
		
		Method method = ai.getProxy().getAction().getClass().getMethod(methodName);
		Annotation[] actionParams = method.getAnnotations();
		for (Annotation annotation : actionParams)
		{
			
			/**
			 * 权限标签解析及权限判断
			 */
			if (annotation.annotationType().equals(AuthorityParas.class))
			{
				User user = (User) actionContext.getSession().get("usersession");
				
				if (user == null)
				{
					result.init(BaseAction.FAIL_STATUS, "用户未登录！", null);
					Utlity.ResponseWrite(result, dataType, response);
					return null;
				}
				Integer roleId = user.getRole().getId();
				
				AuthorityParas aps = (AuthorityParas) annotation;
				Field field = UserGroup.class.getField(aps.userGroupName());
				int[] userGroup = (int[]) field.get(UserGroup.class);
				AuthorityInfo authorityInfo = new AuthorityInfo(aps.user(), userGroup, aps.denyUser(), aps.errMsg());
				if (!AuthorityCheck.CheckUser(authorityInfo, roleId, result))
				{
					Utlity.ResponseWrite(result, dataType, response);
					return null;
				}
			}
			
			else if (annotation.annotationType().equals(ActionParams.class))
			{
				ActionParams params = (ActionParams) annotation;
				for (ActionParam param : params.value())
				{
					if (!checkParam(request, response, param, result, dataType))
					{
						return null;
					}
					
				}
			}
			else if (annotation.annotationType().equals(ActionParam.class))
			{
				ActionParam param = (ActionParam) annotation;
				if (!checkParam(request, response, param, result, dataType))
				{
					return null;
				}
			}
		}
		return ai.invoke();
		
	}
	
	private boolean checkParam(HttpServletRequest request, HttpServletResponse response, ActionParam param, ActionResult result, String dataType)
	{
		if (!param.nullable())
		{
			// 违反数据nullable约定
			if (!request.getParameterMap().containsKey(param.key()))
			{
				result.init(BaseAction.FAIL_STATUS, "缺少参数：" + param.key(), null);
				Utlity.ResponseWrite(result, dataType, response);
				return false;
			}
			// 违反数据emptyable约定
			else if (!param.emptyable() && request.getParameter(param.key()).length() == 0)
			{
				result.init(BaseAction.FAIL_STATUS, "参数" + param.key() + "：不能为空", null);
				Utlity.ResponseWrite(result, dataType, response);
				return false;
			}
			// 数据类型校验不通过
			else if (request.getParameter(param.key()).length() > 0 && !DataTypeCheck.checkDataType(request.getParameter(param.key()), param.type()))
			{
				result.init(BaseAction.FAIL_STATUS, "参数" + param.key() + "校验错误", null);
				Utlity.ResponseWrite(result, dataType, response);
				return false;
			}
		}
		else
		{
			// 可空参数如果传参了才进行数据校验
			if (request.getParameterMap().containsKey(param.key()))
			{
				if (!param.emptyable() && request.getParameter(param.key()).length() == 0)
				{
					result.init(BaseAction.FAIL_STATUS, "参数" + param.key() + "：不能为空", null);
					Utlity.ResponseWrite(result, dataType, response);
					return false;
				}
				// 传数据了才校验
				else if (request.getParameter(param.key()).length() > 0 && (!DataTypeCheck.checkDataType(request.getParameter(param.key()), param.type())))
				{
					result.init(BaseAction.FAIL_STATUS, "参数" + param.key() + "校验错误", null);
					Utlity.ResponseWrite(result, dataType, response);
					return false;
				}
			}
		}
		return true;
	}
	
}
