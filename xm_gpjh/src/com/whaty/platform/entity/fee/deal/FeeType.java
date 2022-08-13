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
			return "学费";
		else if (type != null && type.equals(BOOK))
			return "教材费";
		else if (type != null && type.equals(TEST))
			return "考试费";
		else if (type != null && type.equals(OTHER))
			return "杂费";
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
