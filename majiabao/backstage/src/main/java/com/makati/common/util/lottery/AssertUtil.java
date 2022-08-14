package com.makati.common.util.lottery;


import com.makati.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

/**
 * 校验参数
 * @author jason
 *
 */
public class AssertUtil {

	/**
	 * 校验参数不为空，如果为空，则报异常,参数不限
	 * @param str...
	 * @throws BusinessException
	 */
	public static void isNotBlank(String ... str) throws BusinessException {
		for (int i = 0; i < str.length; i++) {
			if(StringUtils.isBlank(str[i])){
				throw new BusinessException(ErrorCodeUtil.ILEGAL_PARAM_ERROR_CODE,"缺失必选参数");
			}
		}
		
	}

	/**
	 * 校验参数不为空，如果为空，则报异常,参数不限
	 * @param info
	 * @param str...
	 * @throws BusinessException
	 */
	public static void isNotBlankAndInfo(String errorInfo,String ... str) throws BusinessException{
		for (int i = 0; i < str.length; i++) {
			if(StringUtils.isBlank(str[i])){
				throw new BusinessException(ErrorCodeUtil.ILEGAL_PARAM_ERROR_CODE,errorInfo);
			}
		}

	}
	
}
