package com.product.worldpay.controller.base;

import java.util.Map;

import com.product.worldpay.util.api.ApiResultUtlity.ApiResultCode;



/**
 * Result : 响应的结果对象
 *
 */
public class ApiResult implements Result {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6821384652042859148L;
	
	public enum ApiResultStatus {
    	SUCCESS("success"), FAIL("fail"), ERROR("error");
    	
    	private String name;
    	 
    	private ApiResultStatus(String name) {
    		this.name = name;
    	}
    	 
		@Override
		public String toString() {
			return this.name;
		}
    }
	
	/**
	 * 设置内容
	 * @param data
	 * @param status
	 * @param code
	 * @return
	 */
	public void setData(Map<String, String> data, ApiResultStatus status, ApiResultCode code) {
		this.data = data;
		this.status = status;
		this.code = code.name();
		this.message = code.toString();
	}
	/**
     * 状态
     */
    private ApiResultStatus status;
    
	/**
     * 信息
     */
    private String message;
    
    /**
     * 状态码
     */
    private String code;
    
    /**
     * 数据
     */
    private Map<String, String> data;
    
	public ApiResult() {

    }
    
    public ApiResultStatus getStatus() {
		return status;
	}

	public void setStatus(ApiResultStatus status) {
		this.status = status;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
}
