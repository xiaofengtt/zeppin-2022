package cn.product.score.api.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入参公用对象
 */
public class InputParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1590619301471475585L;
	
	private String service;// 调用后台服务类的名称
	private String method;// 服务类中的方法名
	private String busiCode;// 接口编码
	private Map<String, Object> params = new HashMap<String, Object>();// 参数信息
	private List<Map<String, Object>> beans = new ArrayList<Map<String, Object>>();// 参数集

	/** 无参构造器 **/
	public InputParams() {
	}

	/** 构造器 **/
	public InputParams(String service, String method) {
		this.service = service;
		this.method = method;
	}

	/** 构造器 **/
	public InputParams(String service, String method, Map<String, Object> params) {
		this.service = service;
		this.method = method;
		this.params = params;
	}

	/** 构造器 **/
	public InputParams(String service, String method, Map<String, Object> params, Map<String, String> logParams) {
		this.service = service;
		this.method = method;
		this.params = params;
	}
	

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, Object> getParams() {
		if (null == params) {
			params = new HashMap<String, Object>();
		}
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	public List<Map<String, Object>> getBeans() {
		return null == beans ? new ArrayList<Map<String, Object>>() : beans;
	}

	public void setBeans(List<Map<String, Object>> beans) {
		this.beans = beans;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	/**
	 * 往bean属性中放入键值对
	 */
	public void addParams(String key, String toKey, Object value) {
		if (toKey != null && !"".equals(toKey)) {
			getParams().put(toKey, value);
		} else {
			getParams().put(key, value);
		}
	}

	/**
	 * 往beans中的index处的Map中放入键值对
	 */
	public void addBeans(String key, String toKey, int index,
			Object value) {
		if (getBeans().size() <= index) {
			getBeans().add(new HashMap<String, Object>());
		}
		if (toKey != null && !"".equals(toKey)) {
			getBeans().get(index).put(toKey, value);
		} else {
			getBeans().get(index).put(key, value);
		}
	}
	
	/**
	 * 往bean属性中放入键值对
	 * 
	 */
	public String getValue(String key) {
		Object value = getParams().get(key);
		if (value != null && (value instanceof Number || value instanceof String)) {
			return String.valueOf(value);
		}
		return (String)getParams().get(key);
	}
	
	public String getBeansValue(int index , String key) {
		// 越界
		if (getBeans().size() <= index) {
			return null;
		}
		return (String)getBeans().get(index).get(key);
	}
}
