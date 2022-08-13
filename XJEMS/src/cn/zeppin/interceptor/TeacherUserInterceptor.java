package cn.zeppin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 微信教师登录校验
 * 
 * @author Administrator
 *
 */
public class TeacherUserInterceptor extends MethodFilterInterceptor {

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
		HttpSession session = request.getSession();
		// 获取session，检查用户是否已经登录，注意此处的return null;很主要，防止页面多次访问
		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");
		String methodName = ai.getProxy().getConfig().getMethodName();
		if (methodName != null && "WeixinAccount".equals(methodName)) {
			return ai.invoke();
		}
		if (methodName != null && "Add".equals(methodName)) {
			return ai.invoke();
		}
		if (methodName != null && "Login".equals(methodName)) {
			return ai.invoke();
		}
		if (session == null || session.getAttribute("teachersession") == null) {
			result.init(BaseAction.UNLOGIN_STATUS, "用户未登录！", null);
			Utlity.ResponseWrite(result, dataType, response);
			return null;
		}

		return ai.invoke();
	}

}
