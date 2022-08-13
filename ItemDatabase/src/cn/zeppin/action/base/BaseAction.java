/**
 * Project Name:ItemDatabase File Name:BaseAction.java Package
 * Name:cn.zeppin.action.base Copyright (c) 2014, Zeppin All Rights Reserved.
 * 
 */
package cn.zeppin.action.base;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ClassName: BaseAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月18日 下午3:54:20 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public abstract class BaseAction extends ActionSupport

{
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
	 * @date: 2014年7月15日 上午11:45:53 <br/>
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

	// /**
	// * @category 校验传参是否正确,同时对参数进行校验
	// * @param hMap
	// * @return ture：通过,false:报错
	// * @author suijing 2014年7月21日 下午4:59:51
	// */
	// protected boolean containsParameter(HashMap<String, Object> hMap,
	// ActionResult actionResult)
	// {
	// // TODO Auto-generated method stub
	// Iterator<Entry<String, Object>> iterator = hMap.entrySet().iterator();
	//
	// while (iterator.hasNext())
	// {
	// Entry<String, Object> entry = iterator.next();
	// String keyString = entry.getKey().toString();
	// ParaProperty paraProperty = (ParaProperty) hMap.get(keyString);
	// boolean isEnabaleEmpty = paraProperty.getIsEmputy();
	// if (!isEnabaleEmpty)
	// {
	// if (!request.getParameterMap().containsKey(keyString))
	// {
	// actionResult.init(FAIL_STATUS, "缺少参数：" + keyString, null);
	// return false;
	// }
	// else if (request.getParameter(keyString).equals(""))
	// {
	//
	// actionResult.init(FAIL_STATUS, "参数" + keyString + "：不能为空", null);
	// return false;
	//
	// }
	// else if (!DataTypeCheck.checkDataType(request.getParameter(keyString),
	// paraProperty.getDataType()))
	// {
	//
	// actionResult.init(FAIL_STATUS, "参数" + keyString + "校验错误", null);
	// return false;
	// }
	// }
	// else
	// {
	// if (request.getParameterMap().containsKey(keyString))
	// {
	// /*
	// * if (!request.getParameter(keyString).equals("")) {
	// */
	// if (!DataTypeCheck.checkDataType(request.getParameter(keyString),
	// paraProperty.getDataType()))
	// {
	//
	// actionResult.init(FAIL_STATUS, "参数" + keyString + "校验错误", null);
	// return false;
	// }
	// // }
	// }
	//
	// }
	//
	// }
	// return true;
	// }

	/**
	 * @category 获取分页排序参数
	 * @return
	 * @author suijing 2014年7月22日 上午10:38:07
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

	/**
	 * @category 获取分页排序参数（指定参数名称）
	 * @param pageStartName
	 * @param pageSizeName
	 * @param sortName
	 * @return
	 * @author suijing 2014年7月22日 上午10:42:15
	 */
	/*
	 * protected HashMap<String, Object> getPageAndSortParas(String
	 * pageStartName, String pageSizeName, String sortName) { HashMap<String,
	 * Object> hashMap = new HashMap<>(); return hashMap; }
	 */
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

	// /**
	// * @return the actionResult
	// */
	// public ActionResult getActionResult()
	// {
	// return actionResult;
	// }
	//
	// /**
	// * @param actionResult
	// * the actionResult to set
	// */
	// public void setActionResult(ActionResult actionResult)
	// {
	// this.actionResult = actionResult;
	// }

	/**
	 * 如果值不为空则返回值，若为空则返回默认值
	 * 
	 * @author Clark
	 * @date: 2014年7月25日 下午4:35:15 <br/>
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
	 * @date: 2014年7月25日 下午4:35:45 <br/>
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
