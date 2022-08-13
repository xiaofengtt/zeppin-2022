package cn.zeppin.action.base;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport

{
	private static final long serialVersionUID = -5526331148953347172L;

	/**
	 * STATUS返回值类型
	 */
	public final static String FAIL_STATUS = "fail";
	public final static String SUCCESS_STATUS = "success";
	public final static String ERROR_STATUS = "error";
	public final static String WARNING_STATUS = "warning";
	public final static String INFO_STATUS = "info";

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

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * 校验传参的key是否正确
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
	 * 获取分页排序参数
	 */
	protected HashMap<String, Object> getPageAndSortParas() {
		HashMap<String, Object> hashMap = new HashMap<>();
		// 排序

		String sorts = "";
		if (request.getParameter("sorts") != null) {
			sorts = request.getParameter("sorts").replaceAll("-", " ");
		}
		// 分页
		int pagenum = 1;
		int pagesize = Dictionary.PAGESIZE_DEFAULT;

		if (Utlity.isNumeric(request.getParameter("pagenum"))) {
			pagenum = Integer.valueOf(request.getParameter("pagenum"));
		}
		if (Utlity.isNumeric(request.getParameter("pagesize"))) {
			pagesize = Integer.valueOf(request.getParameter("pagesize"));
		}
		int offset = (pagenum - 1) * pagesize;
		hashMap.put("sorts", sorts);
		hashMap.put("pagenum", pagenum);
		hashMap.put("pagesize", pagesize);
		hashMap.put("offset", offset);
		return hashMap;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 如果值不为空则返回值，若为空则返回默认值
	 */
	protected String getStrValue(String value) {
		return getStrValue(value, null);
	}

	protected String getStrValue(String value, String defaultvalue) {
		if (value == null) {
			return defaultvalue;
		}
		return value;
	}

	/**
	 * 如果值不为空则返回值，若为空则返回默认值
	 */
	protected Integer getIntValue(String value) {
		return getIntValue(value, -1);
	}

	protected Integer getIntValue(String value, Integer defaultvalue) {
		if (value == null || value.length() == 0) {
			return defaultvalue;
		}
		return Integer.valueOf(value);
	}

	protected Integer getIntValue(Object value) {
		return getIntValue(value, -1);
	}

	protected Integer getIntValue(Object value, Integer defaultvalue) {
		if (value == null) {
			return defaultvalue;
		}
		return Integer.valueOf(value.toString());
	}

	/**
	 * 返回布尔型的参数
	 */
	protected Boolean getBoolValue(String value) {
		return getBoolValue(value, true);
	}

	protected Boolean getBoolValue(String value, Boolean defaultvalue) {
		if (value == null || value.length() == 0) {
			return defaultvalue;
		} else if (Integer.valueOf(value) != 0) {
			return true;
		}
		return false;
	}

}
