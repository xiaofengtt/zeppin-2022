/**
 * 
 */
package com.zeppin.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zeppin.entiey.Student;

/**
 * @author Administrator
 * 
 */
public class studentInterceptor extends AbstractInterceptor
{
    /**
     * 
     */
    private static final long serialVersionUID = -8445332584333780560L;
    String hqlString;
    Student student;
    String backString;

    /*
     * (non-Javadoc)
     * @see
     * com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com
     * .opensymphony.xwork2.ActionInvocation)
     */
    public String intercept(ActionInvocation invocation) throws Exception
    {
	// 取得请求相关的ActionContext实例
	backString = "login";
	ActionContext ctx = invocation.getInvocationContext();
	HttpServletRequest res = (HttpServletRequest) ctx
		.get(StrutsStatics.HTTP_REQUEST);
	Map session = ctx.getSession();
	if (session.containsKey("student"))
	{
	    student = (Student) session.get("student");
	    if (student != null)
	    {

		backString = invocation.invoke();
	    }

	}

	return backString;

    }

}
