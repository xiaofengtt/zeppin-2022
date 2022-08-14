/**
 * 
 */
package com.product.worldpay.controller.base;

import java.util.Map;

import com.product.worldpay.controller.base.ApiResult.ApiResultStatus;
import com.product.worldpay.util.ApiResultUtlity.ApiResultCode;

public class ResultManager {
	
	
	
	public ResultManager() {
		
	}
	
	/**
	 * API接口统一返回对象封装
	 * @param data
	 * @param status
	 * @param code
	 * @param message
	 * @return
	 */
	public static ApiResult createApiResult(Map<String, String> data, ApiResultStatus status, ApiResultCode code) {
		ApiResult result = new ApiResult();
		result.setData(data);
		result.setStatus(status);
		result.setCode(code.name());
		result.setMessage(code.toString());
		return result;
	}
	
	/**
	 * API接口统一返回对象封装
	 * @return
	 */
	public static ApiResult createApiResult() {
		ApiResult result = new ApiResult();
		return result;
	}
	
	/**
	 * 创建接口请求成功返回对象
	 * @param message
	 * @return
	 */
	public static SuccessResult createSuccessResult(String message) {
		SuccessResult successResult = new SuccessResult();
		successResult.setMessage(message);
		return successResult;
	}
	
	/**
	 * 创建接口请求警告返回对象
	 * @param data
	 * @param error
	 * @return
	 */
	public static <T> WarningResult<T> createWarningResult(T data, T error) {
		WarningResult<T> result = new WarningResult<T>();
		result.setData(data);
		result.setError(error);
		return result;
	}
	
	/**
	 * 创建接口数据返回对象,包括数据、消息、分页信息
	 * @param data
	 * @param message
	 * @param pageNum
	 * @param pageSize
	 * @param totalPageCount
	 * @param totalResultCount
	 * @return DataResult<T>
	 */
	public static <T> DataResult<T> createDataResult(T data, String message, Integer pageNum, Integer pageSize, 
			Integer totalResultCount) {
		DataResult<T> result = new DataResult<T>();
		result.setData(data);
		result.setMessage(message);
		if(pageNum != null && pageNum > 0){
			result.setPageNum(pageNum);
		}else{
			result.setPageNum(0);
		}
		if(pageSize != null && pageSize > 0){
			result.setPageSize(pageSize);
		}else{
			result.setPageSize(0);
		}
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize >0){
			result.setTotalPageCount((int) Math.ceil((double)totalResultCount / pageSize));
		}else{
			result.setTotalPageCount(0);
		}
		result.setTotalResultCount(totalResultCount);
		return result;
	}
	
	/**
	 * 创建接口数据返回对象,包括数据、分页信息
	 * @param data
	 * @param pageNum
	 * @param pageSize
	 * @param totalPageCount
	 * @param totalResultCount
	 * @return DataResult<T>
	 */
	public static <T> DataResult<T> createDataResult(T data, Integer pageNum, Integer pageSize, 
			Integer totalResultCount) {
		DataResult<T> result = new DataResult<T>();
		result.setData(data);
		if(pageNum != null && pageNum > 0){
			result.setPageNum(pageNum);
		}else{
			result.setPageNum(0);
		}
		if(pageSize != null && pageSize > 0){
			result.setPageSize(pageSize);
		}else{
			result.setPageSize(0);
		}
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize >0){
			result.setTotalPageCount((int) Math.ceil((double)totalResultCount / pageSize));
		}else{
			result.setTotalPageCount(0);
		}
		result.setTotalResultCount(totalResultCount);
		return result;
	}
	
	/**
	 * 创建接口数据返回对象,包括数据、消息信息
	 * @param data
	 * @param message
	 * @return DataResult<T>
	 */
	public static <T> DataResult<T> createDataResult(T data, String message) {
		DataResult<T> result = new DataResult<T>();
		result.setData(data);
		result.setMessage(message);
		return result;
	}
	
	/**
	 * 创建接口数据返回对象,包括数据信息
	 * @param data
	 * @param message
	 * @return DataResult<T>
	 */
	public static <T> DataResult<T> createDataResult(T data, Integer totalResultCount) {
		DataResult<T> result = new DataResult<T>();
		result.setData(data);
		result.setTotalResultCount(totalResultCount);
		return result;
	}

	/**
	 * 创建接口数据返回对象,包括数据信息
	 * @param data
	 * @param message
	 * @return DataResult<T>
	 */
	public static <T> DataResult<T> createDataResult(T data) {
		DataResult<T> result = new DataResult<T>();
		result.setData(data);
		return result;
	}
	
	/**
	 * 创建接口调用错误返回对象,包括错误码和错误信息
	 * @param errorCode
	 * @param message
	 * @return ErrorResult
	 */
	public static ErrorResult createErrorResult(String errorCode, String message) {
		ErrorResult result = new ErrorResult();
		result.setErrorCode(errorCode);
		result.setMessage(message);
		return result;
	}
	
	/**
	 * 创建接口业务调用失败返回对象，返回提示信息
	 * @param message
	 * @return FailResult
	 */
	public static FailResult createFailResult(String message){
		FailResult result = new FailResult();
		result.setMessage(message);
		return result;
	}

}
