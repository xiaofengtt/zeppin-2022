package cn.product.payment.util.api;


public class StringUtils {
	public static boolean isEmpty(String str) {
		return (str == null || "".equals(str));
	}
	
	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}
}
