/**
 * 
 */
package cn.zeppin.product.itic.core.controller.base;


/**
 * @author Clark.R 2016年9月27日
 *
 */
public class ResultManager {
	
	
	
	public ResultManager() {
		
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
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalPageCount((int) Math.ceil(totalResultCount / pageSize));
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
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalPageCount((int) Math.ceil(totalResultCount / pageSize));
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
