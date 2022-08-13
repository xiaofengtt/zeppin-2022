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
public class PayoutType {
	
	public static String CONSUME = "CONSUME";

	public static String DEPOSIT = "DEPOSIT";
	
	public static String ROLLBACK = "ROLLBACK";

	public static String OTHER = "OTHER";

	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(CONSUME))
			return "����";
		else if (type != null && type.equals(DEPOSIT))
			return "���";
		else if (type != null && type.equals(ROLLBACK))
			return "�˿�";
		else if (type != null && type.equals(OTHER))
			return "��������";
		else
			return "";
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(CONSUME);
		list.add(DEPOSIT);
		list.add(ROLLBACK);
		list.add(OTHER);
		return list;
	}

}
