/**
 * 
 */
package com.zeppin.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zeppin.entiey.Teacher;

/**
 * @author Administrator
 * 
 */
public class LoginInterceptor extends AbstractInterceptor
{
    @Override
    public String intercept(ActionInvocation invocation) throws Exception
    {

	// 取得请求相关的ActionContext实例
	ActionContext ctx = invocation.getInvocationContext();
	Map session = ctx.getSession();
	if (session.containsKey("academic"))
	{
	    Teacher teacher = (Teacher) session.get("academic");
	    if (teacher.getManageType() == 2)
	    {

		return invocation.invoke();
	    }

	}
	return "login";

    }

}
