/**
 * This class is used for ... 校验常用数据
 * 
 * @author suijing
 * @version 1.0, 2014年7月24日 下午2:36:45
 */
package cn.zeppin.project.chinamobile.media.utility;

import cn.zeppin.project.chinamobile.media.web.controller.base.ActionParam.ValueType;

/**
 * @author sj
 * 
 */
public class DataTypeCheck {

	public static boolean checkDataType(String data, ValueType dataType) {
		boolean isRight = true;
		switch (dataType) {
		case NUMBER:
			if (!Utlity.isNumeric(data)) {
				isRight = false;
			}
			break;
		case PHONE:
			if (!Utlity.isMobileNO(data)) {
				isRight = false;
			}
			break;
		case EMAIL:
			if (!Utlity.isEmail(data)) {
				isRight = false;
			}
			break;
		case IDCARD:
			if (!Utlity.checkIdCard(data)) {
				isRight = false;
			}
			break;
		case STRING:
			if (data == null || !data.getClass().equals(String.class)) {
				isRight = false;
			}
			break;
		default:
			break;
		}
		return isRight;

	}
}
