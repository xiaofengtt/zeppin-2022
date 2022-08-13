/**
 * 
 */
package com.whaty.platform.entity.basic;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

/**
 * @author chenjian
 * 
 */
public class TeachPlanCourseType {

	public static String PUBMUST = "PUBMUST";

	public static String PUBOPTION = "PUBOPTION";

	public static String MAJORMUST = "MAJORMUST";

	public static String MAJOROPTION = "MAJOROPTION";

	public static String MAJORBASIC = "MAJORBASIC";

	public static String PRACTICE = "PRACTICE";

	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(PUBMUST))
			return "公共基础课";
		else if (type != null && type.equals(PUBOPTION))
			return "基础选修课";
		else if (type != null && type.equals(MAJORMUST))
			return "专业课";
		else if (type != null && type.equals(MAJOROPTION))
			return "专业选修课";
		else if (type != null && type.equals(MAJORBASIC))
			return "专业基础课";
		else if (type != null && type.equals(PRACTICE))
			return "实践课";
		else
			throw new TypeErrorException("无此课程类型!");
	}

	public static String typeConvert(String type) throws TypeErrorException {
		if (type != null && type.equals("公共基础课"))
			return "PUBMUST";
		else if (type != null && type.equals("基础选修课"))
			return "PUBOPTION";
		else if (type != null && type.equals("专业课"))
			return "MAJORMUST";
		else if (type != null && type.equals("专业选修课"))
			return "MAJOROPTION";
		else if (type != null && type.equals("专业基础课"))
			return "MAJORBASIC";
		else if (type != null && type.equals("实践课"))
			return "PRACTICE";
		else
			return type;
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(PUBMUST);
		list.add(PUBOPTION);
		list.add(MAJORMUST);
		list.add(MAJOROPTION);
		list.add(MAJORBASIC);
		list.add(PRACTICE);
		return list;
	}
}
