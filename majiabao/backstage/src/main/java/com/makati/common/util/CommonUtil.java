package com.makati.common.util;

import com.makati.common.exception.BusinessException;
import com.makati.common.response.ErrorResponse;
import com.makati.common.util.lottery.ErrorCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class CommonUtil {
    /**
     * 返回用户的IP地址
     *
     * @return
     */
    public static String toIpAddr() {
        HttpServletRequest request=getRequest();
        if(null==request){
            return "未获取ip";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取当前请求对象
     * @return
     */
    public static HttpServletRequest getRequest(){
        try{
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 统一异常处理
     * @param flag：类名+方法标识
     * @param e：EXCEPTION
     * @return
     */
    public static String doHandlerException(String flag, Exception e){
        String result=null;
        if(e instanceof BusinessException){
            log.info(flag +" {} ", e.getMessage());
            BusinessException businessException=(BusinessException)e;
            if(StringUtils.isNotBlank(businessException.getMessage())){
                result=JsonMapper.toJsonString(ErrorResponse.createErrorResponse(e.getMessage(),businessException.getMessage()));
            }else{
                result=JsonMapper.toJsonString(ErrorResponse.errorMap.get(e.getMessage()));
            }
        }else{
            log.error(flag, e);
            result=JsonMapper.toJsonString(ErrorResponse.errorMap.get(ErrorCodeUtil.SYSTEM_ERROR_CODE));
        }
        return result;
    }
}
