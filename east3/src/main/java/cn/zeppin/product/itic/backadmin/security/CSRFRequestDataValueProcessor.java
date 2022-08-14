/**
 * 
 */
package cn.zeppin.product.itic.backadmin.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestDataValueProcessor;


/**
 * Spring <form> 预防CSRF攻击隐藏域生成器
 */
@Component(value = "requestDataValueProcessor")
public class CSRFRequestDataValueProcessor implements RequestDataValueProcessor {


	
	@Override
	public String processAction(HttpServletRequest request, String action, String httpMethod) {
		return action;
	}


	
	@Override
	public String processFormFieldValue(HttpServletRequest request, String name, String value, String type) {
		return value;
	}

	
	/**
	 * 此处是当使用spring的taglib标签<form:form>创建表单时候，增加的隐藏域参数
	 */
	@Override
	public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
        Map<String, String> hiddenFields = new HashMap<String, String> ();
        hiddenFields.put(CSRFTokenManager.CSRF_PARAM_NAME, CSRFTokenManager.createTokenForSession(request.getSession()));
        return hiddenFields;
	}

	@Override
	public String processUrl(HttpServletRequest request, String url) {
		return url;
	}

}
