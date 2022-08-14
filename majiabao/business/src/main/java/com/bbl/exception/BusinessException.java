package com.bbl.exception;


import lombok.Data;

/**
 * 自定义异常
 * 
 * @author jason
 *
 */
@Data
public class BusinessException extends RuntimeException{

	private String code;
	private String message;

	public BusinessException(String code) {
		this.code = code;
		this.message = "网络错误";
	}

	public BusinessException(String code, String message) {
		super( code + " : " + message);
		this.code = code;
		this.message = message;
	}

	public BusinessException(Throwable cause, String code, String message) {
		super(message, cause);
		this.code = code;
		this.message = message;
	}



    public BusinessException(ErrorResultEnums errorResponse) {
        super(errorResponse.getMsg());
        this.code = errorResponse.getCode().toString();
        this.message = errorResponse.getMsg();
    }

	@Override
	public String toString() {
		return "BusinessException{" +
				"code='" + code + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
