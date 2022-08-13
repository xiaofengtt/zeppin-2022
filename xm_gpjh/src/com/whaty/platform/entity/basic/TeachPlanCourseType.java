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
			return "����������";
		else if (type != null && type.equals(PUBOPTION))
			return "����ѡ�޿�";
		else if (type != null && type.equals(MAJORMUST))
			return "רҵ��";
		else if (type != null && type.equals(MAJOROPTION))
			return "רҵѡ�޿�";
		else if (type != null && type.equals(MAJORBASIC))
			return "רҵ������";
		else if (type != null && type.equals(PRACTICE))
			return "ʵ����";
		else
			throw new TypeErrorException("�޴˿γ�����!");
	}

	public static String typeConvert(String type) throws TypeErrorException {
		if (type != null && type.equals("����������"))
			return "PUBMUST";
		else if (type != null && type.equals("����ѡ�޿�"))
			return "PUBOPTION";
		else if (type != null && type.equals("רҵ��"))
			return "MAJORMUST";
		else if (type != null && type.equals("רҵѡ�޿�"))
			return "MAJOROPTION";
		else if (type != null && type.equals("רҵ������"))
			return "MAJORBASIC";
		else if (type != null && type.equals("ʵ����"))
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
