package com.makati.common.util.lottery;

import com.makati.common.header.HeaderInfo;
import com.makati.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class RequestUtil {

	 /** 
	 * 从request中获得参数Map，并返回可读的Map 
	 *  
	 * @param request 
	 * @return 
	 */  
	@SuppressWarnings("unchecked")  
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
	    // 参数Map  
	    Map<String, String[]>  properties = request.getParameterMap();  
	    // 返回值Map  
	    Map<String, String>  returnMap = new TreeMap<String, String>();  
	    Iterator<Entry<String, String[]>> entries = properties.entrySet().iterator();  
	    Entry<String, String> entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Entry) entries.next();
	        name = (String) entry.getKey();  
	        Object valueObj = entry.getValue();  
	        if(null == valueObj){  
	            value = "";  
	        }else if(valueObj instanceof String[]){  
	            String[] values = (String[])valueObj;  
	            for(int i=0;i<values.length;i++){  
	                value = values[i] + ",";  
	            }  
	            value = value.substring(0, value.length()-1);  
	        }else{  
	            value = valueObj.toString();  
	        }  
	        returnMap.put(name, value);  
	    }  
	    return returnMap;  
	}
	
	/**
	 * 从req中获取参数并按照自然排序
	 * 去掉键值对  sign
	 * @param request
	 * @return
	 */
	public static String getOrderParam(HttpServletRequest request){
		Map<String, String> map=RequestUtil.getParameterMap(request);
		Iterator its=map.entrySet().iterator();
		StringBuffer sb=new StringBuffer();
		while(its.hasNext()){
			Entry<String,String> entry=(Entry<String, String>) its.next();
			if("sign".equals(entry.getKey())){
				continue;
			}
			sb.append(entry.getKey()+"=").append(entry.getValue()).append("&");
		}
		String str=sb.toString();
		if(StringUtils.isNotBlank(str)){
			str=str.substring(0, str.length()-1);
		}
		
		return str;
	}

	/**
	 *  获取ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(StringUtils.isNotBlank(ip)) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	public static String getOrderParam(TreeMap<String, String> map){
		Iterator its=map.entrySet().iterator();
		StringBuffer sb=new StringBuffer();
		while(its.hasNext()){
			Entry<String,String> entry=(Entry<String, String>) its.next();
			if("sign".equals(entry.getKey())){
				continue;
			}
			sb.append(entry.getKey()+"=").append(entry.getValue()).append("&");
		}
		String str=sb.toString();
		if(StringUtils.isNotBlank(str)){
			str=str.substring(0, str.length()-1);
		}

		return str;
	}

	/**
	 * 获取header信息
	 * @param request
	 * @return
	 */
	public static HeaderInfo getHeaderInfo(HttpServletRequest request){
		String platform = request.getHeader("platform");
		String deviceModel  = request.getHeader("deviceModel");
		String version   = request.getHeader("version");
		String userToken  = request.getHeader("userToken");
		String md5Salt   = request.getHeader("md5Salt");
		return new HeaderInfo(platform,deviceModel,version,userToken,md5Salt);
	}

	public static void main(String[] args) {
		TreeMap map = new TreeMap();
		map.put("aaa","222");
		map.put("zzz", StringUtil.object2String(null));
		map.put("bbb",StringUtil.object2String(333));
	    System.out.println(getOrderParam(map));;
	}
}
