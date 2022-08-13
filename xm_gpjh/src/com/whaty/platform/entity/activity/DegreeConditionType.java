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
	 * 返回的内容根据实际条件填写
	 * 
	 * @param type
	 * @return
	 * @throws TypeErrorException
	 */
	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(CONA))
			return "条件A";
		else
			throw new TypeErrorException("Cashout Type error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(CONA);
		return list;
	}
}
