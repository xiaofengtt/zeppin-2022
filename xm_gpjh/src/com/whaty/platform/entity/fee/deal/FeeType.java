/**
 * 
 */
package com.whaty.platform.entity.fee.deal;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

/**
 * @author chenjian
 *
 */
public class FeeType {

	public static String CREDIT = "CREDIT";

	public static String BOOK = "BOOK";
	
	public static String TEST = "TEST";

	public static String OTHER = "OTHER";

	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(CREDIT))
			return "ѧ��";
		else if (type != null && type.equals(BOOK))
			return "�̲ķ�";
		else if (type != null && type.equals(TEST))
			return "���Է�";
		else if (type != null && type.equals(OTHER))
			return "�ӷ�";
		else
			return "";
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(CREDIT);
		list.add(OTHER);
		return list;
	}
}
