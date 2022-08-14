package cn.zeppin.action.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ClassName: BaseAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public abstract class BaseAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5526331148953347172L;

	/**
	 * STATUS返回值类型
	 */
	public final static String FAIL_STATUS = "fail";
	public final static String SUCCESS_STATUS = "success";
	public final static String ERROR_STATUS = "error";
	public final static String WARNING_STATUS = "warning";
	public final static String INFO_STATUS = "info";
	public final static String EMPTY_STATUS = "empty";
	public final static String END_STATUS = "end";
	public final static String UNREGISTERED_STATUS = "unregistered";
	public final static String UNLOGIN_STATUS = "unlogin";

	/**
	 * OPERATION返回值类型
	 */
	public final static String FLUSH = "flush";
	public final static String DONOT = "donot";

	/**
	 * 
	 */
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected String dataType;

	// protected ActionResult actionResult;

	protected BaseAction() {
		super();
		request = ServletActionContext.getRequest();
		response = (ServletActionContext.getResponse());
		session = request.getSession();
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		if (request.getParameterMap().containsKey("")) {
			dataType = request.getParameter("datatype");
		}
	}

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the session
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * 校验传参的key是否正确
	 * 
	 * @author Clark
	 * @param params
	 * @return
	 */
	protected Boolean containsParameter(String... params) {
		for (String param : params) {
			if (!request.getParameterMap().containsKey(param)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 如果值不为空则返回值，若为空则返回默认值
	 * 
	 * @author Clark
	 * @param value
	 * @param defaultvalue
	 * @return value
	 */
	protected String getStrValue(String value) {
		// TODO Auto-generated method stub
		return getStrValue(value, null);
	}

	protected String getStrValue(String value, String defaultvalue) {
		// TODO Auto-generated method stub
		if (value == null) {
			return defaultvalue;
		}
		return value;
	}

	/**
	 * 如果值不为空则返回值，若为空则返回默认值
	 * 
	 * @author Clark
	 * @param value
	 * @param defaultvalue
	 * @return
	 */
	protected Integer getIntValue(String value) {
		// TODO Auto-generated method stub
		return getIntValue(value, -1);
	}

	protected Integer getIntValue(String value, Integer defaultvalue) {
		// TODO Auto-generated method stub
		if (value == null || value.length() == 0) {
			return defaultvalue;
		}
		return Integer.valueOf(value);
	}
	
	

	protected Integer getIntValue(Object value) {
		// TODO Auto-generated method stub
		return getIntValue(value, -1);
	}

	protected Integer getIntValue(Object value, Integer defaultvalue) {
		// TODO Auto-generated method stub
		if (value == null) {
			return defaultvalue;
		}
		return Integer.valueOf(value.toString());
	}
	
	
	protected Long getLongValue(String value) {
		// TODO Auto-generated method stub
		return getLongValue(value, Long.valueOf(-1));
	}

	protected Long getLongValue(String value, Long defaultvalue) {
		// TODO Auto-generated method stub
		if (value == null || value.length() == 0) {
			return defaultvalue;
		}
		return Long.valueOf(value);
	}

	/**
	 * 返回布尔型的参数
	 * 
	 * @param parameter
	 * @param b
	 * @return
	 */
	protected Boolean getBoolValue(String value) {
		// TODO Auto-generated method stub
		return getBoolValue(value, true);
	}

	protected Boolean getBoolValue(String value, Boolean defaultvalue) {
		// TODO Auto-generated method stub
		if (value == null || value.length() == 0) {
			return defaultvalue;
		} else if (Integer.valueOf(value) != 0) {
			return true;
		}
		return false;
	}

}
