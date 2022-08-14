package cn.product.worldmall.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeRateGetUtil {
	public static final Logger log= LoggerFactory.getLogger(ExchangeRateGetUtil.class);
	
//	private static final String link_url = "http://127.0.0.1/treasuremallweb/socket/websocket";
	private static final String link_url = "https://openexchangerates.org/api";//汇率接口，每小时更新一次
	private static final String link_url_latest = "/latest.json";//汇率接口，每小时更新一次
	private static final String link_url_currencies = "/currencies.json";//货币币种接口，初始化货币数据用
	private static final String link_url_history = "/historical";//格式：historical/2013-02-16.json
	private static final String app_id = "9ddb03d49f504125a7f299502adc78c2";//身份识别码
	
	/**
	 * 汇率获取接口
	 * @return
	 */
	public static Map<String, Object> latestInfo() {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean request = false;
		String message = "失败";
		String resultStr = "";
		try {
//			resultStr = HttpClientUtil.post(link_url + link_url_latest + "?app_id=" + app_id, params);
			resultStr = HttpClientUtil.get_ssl(link_url + link_url_latest + "?app_id=" + app_id);
        	log.info("-----------------请求成功--回收消息-------------------："+resultStr);
//        	Map<String, Object> requestMap = JSONUtils.json2map(resultStr);
        	if(!Utlity.checkStringNull(resultStr)) {//成功
        		request = true;
        		message = "成功";
        	}
		} catch (Exception e) {
			e.printStackTrace();
			request = false;
    		message = "失败异常";
    		log.info("-----------------请求异常--回收消息-------------------："+resultStr);
		}
		result.put("result", resultStr);
		result.put("request", request);
		result.put("message", message);
		return result;
	}
	
	/**
	 * 货币信息获取接口
	 */
	public static Map<String, Object> currenciesInfo() {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean request = false;
		String message = "失败";
		String resultStr = "";
		try {
//			resultStr = HttpClientUtil.post(link_url + link_url_latest + "?app_id=" + app_id, params);
			resultStr = HttpClientUtil.get_ssl(link_url + link_url_currencies + "?app_id=" + app_id);
        	log.info("-----------------请求成功--回收消息-------------------："+resultStr);
//        	Map<String, Object> requestMap = JSONUtils.json2map(resultStr);
        	if(!Utlity.checkStringNull(resultStr)) {//成功
        		request = true;
        		message = "成功";
        	}
		} catch (Exception e) {
			e.printStackTrace();
			request = false;
    		message = "失败异常";
    		log.info("-----------------请求异常--回收消息-------------------："+resultStr);
		}
		
		//失败就重发
		result.put("request", request);
		result.put("message", message);
		return result;
	}
}
