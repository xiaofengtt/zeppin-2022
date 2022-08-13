/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

/**
 * @author chenjian
 * 
 */
public class GraduateConditionType {
	public static String CONA = "CONA";

	public static String CONB = "CONB";

	public static String CONC = "CONC";

	/**
	 * ���ص����ݸ���ʵ��������д
	 * 
	 * @param type
	 * @return
	 * @throws TypeErrorException
	 */
	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(CONA))
			return "����A";
		else if (type != null && type.equals(CONB))
			return "����B";
		else if (type != null && type.equals(CONC))
			return "����C";
		else
			throw new TypeErrorException("Cashout Type error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(CONA);
		list.add(CONB);
		list.add(CONC);
		return list;
	}
}
