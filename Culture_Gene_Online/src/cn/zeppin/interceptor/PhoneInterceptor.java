package cn.zeppin.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class PhoneInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 8139734739596650793L;

	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception {
		return ai.invoke();
	}

}
