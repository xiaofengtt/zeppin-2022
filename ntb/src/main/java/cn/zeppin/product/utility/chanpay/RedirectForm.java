package cn.zeppin.product.utility.chanpay;

import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * <p>跳转请求对象</p>
 * @author leelun
 * @version $Id: RedirectForm.java, v 0.1 2013-11-12 下午5:50:46 lilun Exp $
 */
public class RedirectForm {
	public static final String GET = "get";
	public static final String POST = "post";
	private String actionUrl = RedirectForm.POST;
	private String method;
	private Map<String, String> parameters;

	public RedirectForm() {
		super();
	}

	public RedirectForm(String actionUrl, String method, Map<String, String> parameters) {
		super();
		this.actionUrl = actionUrl;
		this.method = method;
		this.parameters = parameters;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("parameters", this.parameters).append("actionUrl", this.actionUrl)
				.append("method", this.method).toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-218222427, 1629322091).append(this.method).append(this.parameters)
				.append(this.actionUrl).toHashCode();
	}

}
