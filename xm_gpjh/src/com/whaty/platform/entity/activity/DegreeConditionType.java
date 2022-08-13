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
public class DegreeConditionType {
	public static String CONA = "CONA";

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
		else
			throw new TypeErrorException("Cashout Type error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(CONA);
		return list;
	}
}
