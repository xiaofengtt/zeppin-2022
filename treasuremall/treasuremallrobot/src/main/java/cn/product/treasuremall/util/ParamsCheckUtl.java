package cn.product.treasuremall.util;

import cn.product.treasuremall.controller.base.ActionParam.DataType;

public class ParamsCheckUtl {

	public static boolean checkDataType(String data, DataType dataType) {
		boolean isRight = true;
		switch (dataType) {
		/**
		 * 如下参数值验证
		 * 1、货币（含有二位小数）  
		 * 2、正数货币（含有二位小数）
		 * 3、正整数货币
		 * 4、十倍数正整数货币值
		 * 5、百倍数正整数货币
		 * 6、千倍数正整数货币
		 * 7、万倍数正整数货币
		 * 8、货币值验证（无限制小数位数，无限制正负值）
		 * 
		 * NUMBER_ARRAY: 数值数组，主要用于提交表单时有一些字段是多个值，当参数数值类型为NUMBER_ARRAY时，则数组中每一个值都需要经过验证
		 * STRING_ARRAY: 字符串数组，主要用于提交表单时有一些字段是多个值（如UUID），当参数数值类型为STRING_ARRAY时，则数组中每一个值都需要经过验证
		 */
		case NUMBER:
			if (!Utlity.isNumeric(data)) {
				isRight = false;
			}
			break;
		//整数
		case INTEGER:
			if (!Utlity.isInteger(data)) {
				isRight = false;
			}
			break;
		//正整数
		case POSITIVE_INTEGER:
			if (!Utlity.isPositiveInteger(data)) {
				isRight = false;
			}
			break;
		//货币（含有二位小数）
		case CURRENCY:
			if (!Utlity.isCurrency(data)) {
				isRight = false;
			}
			break;
		//正数货币（含有二位小数）
		case POSITIVE_CURRENCY:
			if (!Utlity.isPositiveCurrency(data)) {
				isRight = false;
			}
			break;
		//十倍正整数
		case TEN_TIMES_POSITIVE_INTEGER:
			if (!Utlity.isTenTimesPositiveInteger(data)) {
				isRight = false;
			}
			break;
		//百倍正整数
		case HUNDRED_TIMES_POSITIVE_INTEGER:
			if (!Utlity.isHundredTimesPositiveInteger(data)) {
				isRight = false;
			}
			break;
		//千倍正整数
		case THOUSAND_TIMES_POSITIVE_INTEGER:
			if (!Utlity.isThousandTimesPostitveInteger(data)) {
				isRight = false;
			}
			break;
		//万倍正整数
		case TENTHOUSAND_TIMES_POSITIVE_INTEGER:
			if (!Utlity.isTenThousandTimesPositiveInteger(data)) {
				isRight = false;
			}
			break;
		//电话
		case PHONE:
			if (!Utlity.isMobileNO(data)) {
				isRight = false;
			}
			break;
		//邮箱
		case EMAIL:
			if (!Utlity.isEmail(data)) {
				isRight = false;
			}
			break;
		//身份证
		case IDCARD:
			if (!Utlity.checkIdCard(data)) {
				isRight = false;
			}
			break;
		//字符串
		case STRING:
			if (data == null || !data.getClass().equals(String.class)) {
				isRight = false;
			}
			break;
		//布尔值
		case BOOLEAN:
			if ((data == null || (!"true".equals(data) && !"false".equals(data))) &&  !"".equals(data)) {
				isRight = false;
			}
			break;
		//日期
		case DATE:
			if ((data == null || !Utlity.isDateFormat(data)) && !"".equals(data)) {
				isRight = false;
			}
		break;
		//其他
		default:
			break;
		}
		return isRight;

	}
}
