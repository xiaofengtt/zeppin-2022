package cn.zeppin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.SysUser;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class AdminInterceptor extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception {
		// TODO Auto-generated method stub
		ActionContext actionContext = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
		String path = request.getContextPath();
		String servletPath = request.getServletPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + servletPath.substring(0, servletPath.lastIndexOf("/") + 1);

		// 获取session，检查用户是否已经登录，注意此处的return null;很主要，防止页面多次访问

		SysUser user = (SysUser) actionContext.getSession().get("usersession");
		if (user == null) {
			// return "login" 如果没有用户登录则转向此页面，basePath用来防止路径出错
			String dataType = request.getParameter("datatype");
			ActionResult result = new ActionResult();
			result.init(BaseAction.FAIL_STATUS, "用户未登录！", null);
			Utlity.ResponseWrite(result, dataType, response);
			response.sendRedirect(basePath);
			return null;
		}

		return ai.invoke();
	}

}
