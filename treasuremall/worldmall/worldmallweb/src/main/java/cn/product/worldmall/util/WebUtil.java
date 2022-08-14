package cn.product.worldmall.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WebUtil {

	/**
	 * 判断请求是否为ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request){
		if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
			return true;
		}
		return false;
	}

	/**
	 * 返回JSON到页面中
	 * @param httpResponse
	 * @param jsonString
	 * @throws IOException 
	 */
	public static void sendJson(HttpServletResponse httpResponse, String jsonString) throws IOException {
		httpResponse.setContentType("application/json");
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = httpResponse.getWriter();  
		writer.write(jsonString);
		writer.flush();
		writer.close();
	}

    /**
     * 获取目标主机的ip
     * @param request
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
