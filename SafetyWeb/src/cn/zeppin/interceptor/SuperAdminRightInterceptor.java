/**
 * This class is used for 对超级管理员权限做相关检查
 * 
 * @author suijing
 * @version 1.0, 2014年7月22日 上午8:45:40
 */
package cn.zeppin.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.entity.User;
import cn.zeppin.service.api.IUserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * @author sj
 * 
 */
public class SuperAdminRightInterceptor extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2698400025235658420L;

	private IUserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#doIntercept
	 * (com.opensymphony.xwork2.ActionInvocation)
	 */
	@SuppressWarnings("unused")
	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception {
		ActionContext actionContext = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);

		User user = (User) actionContext.getSession().get("userphone");

		if (user == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				String username = null;
				String password = null;
				for (Cookie cook : cookies) {
					if (cook.getName().equals("username")) {
						username = cook.getValue();
					} else if (cook.getName().equals("password")) {
						password = cook.getValue();
					}
				}
				if (username != null && password != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("account", username);
					map.put("password", password);

					User usertmp = this.getUserService().getUserByMap(map);
					if (usertmp != null) {
						// 重新保存会话状态
						request.getSession().setAttribute("userphone", usertmp);
					}
				}

			}
		}

		return ai.invoke();

	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
