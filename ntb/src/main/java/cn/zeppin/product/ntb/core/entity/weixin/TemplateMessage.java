package cn.zeppin.product.ntb.core.entity.weixin;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title:TemplateMessage</p>
 * <p>Description:消息模板</p>
 * @author geng
 * @date 2017年8月4日 下午3:54:39
 */
public class TemplateMessage {
	private String touser; // 接收者openid

	private String template_id; // 模板ID

	private String url; // 模板跳转链接

	private Map<String, Map<String, String>> data; // data数据

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Map<String, String>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String, String>> data) {
		this.data = data;
	}

	/**
	 * 参数
	 * 
	 * @param value
	 * @param color    
	 * @return
	 */
	public static Map<String, String> item(String value, String color) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("value", value);
		params.put("color", color);
		return params;
	}
	/**
	 * 参数
	 * 
	 * @param value
	 * @param color    可不填
	 * @return
	 */
	public static Map<String, String> item(String value) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("value", value);
		params.put("color", "#000000");
		return params;
	}

	@Override
	public String toString() {
		return "TemplateMessage [touser=" + touser + ", template_id=" + template_id + ", url=" + url + ", data=" + data
				+ "]";
	}
	
	
}
