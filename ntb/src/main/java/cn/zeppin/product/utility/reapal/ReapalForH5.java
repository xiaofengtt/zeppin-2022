package cn.zeppin.product.utility.reapal;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

public class ReapalForH5 {

	public static String BuildFormH5(String merchant_id, String order_no, String bind_id, String member_id, String terminal_type) throws Exception {

		Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("merchant_id", merchant_id);
		sPara.put("terminal_type", terminal_type);
		sPara.put("order_no", order_no);
		sPara.put("bind_id", bind_id);
		sPara.put("member_id", member_id);
		sPara.put("return_url", ReapalConfig.notify_url_pay);
		sPara.put("notify_url", ReapalConfig.notify_url_pay);
		sPara.put("version", ReapalConfig.version);

		String mysign = Md5Utils.BuildMysign(sPara, ReapalConfig.key);// 生成签名结果

		System.out.println("=====mysign====" + mysign);
		
		sPara.put("sign", mysign);
		
		System.out.println("======sPara==toString()=====" + sPara.toString());

		String json = JSON.toJSONString(sPara);

		Map<String, String> maps = Decipher.encryptData(json);
		
		System.out.println("======maps==toString()=====" + maps.toString());

		StringBuffer sbHtml = new StringBuffer();

		// post方式传递
		sbHtml.append(
				"<form id=\"rongpaysubmit\" name=\"rongpaysubmit\" action=\"")
				.append(ReapalConfig.notify_url_pay).append("\" method=\"post\">");

		sbHtml.append("<input type=\"hidden\" name=\"merchant_id\" value=\"")
				.append(ReapalConfig.merchant_id).append("\"/>");
		sbHtml.append("<input type=\"hidden\" name=\"data\" value=\"")
				.append(maps.get("data")).append("\"/>");
		sbHtml.append("<input type=\"hidden\" name=\"encryptkey\" value=\"")
				.append(maps.get("encryptkey")).append("\"/>");

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" class=\"button_p2p\" value=\"确认提交\"></form>");
		
		System.out.println("======生成请求卡密form表单=======" + sbHtml.toString());
		return sbHtml.toString();

	}
	
	
	@SuppressWarnings("rawtypes")
	public static Map<String,String> parseParam(HttpServletRequest request) {
        Map<String,String> paramsMap = new HashMap<String,String>();
        Enumeration paramsEnum = request.getParameterNames();
        while (paramsEnum.hasMoreElements()) {
            String paramName = (String) paramsEnum.nextElement();
            String paramValue = request.getParameter(paramName);
            paramsMap.put(paramName, paramValue);
        }
        return paramsMap;
    }
}
