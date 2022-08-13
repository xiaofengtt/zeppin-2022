package com.whaty.platform.config;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

public class CardType {

	public static String IDCARD = "IDCARD";

	public static String OFFICERCARD = "OFFICERCARD";

	public static String PASSPORT = "PASSPORT";

	public static String OTHER = "OTHER";

	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(IDCARD))
			return "���֤";
		else if (type != null && type.equals(OFFICERCARD))
			return "����֤";
		else if (type != null && type.equals(PASSPORT))
			return "����";
		else if (type != null && type.equals(OTHER))
			return "����֤��";
		else
			throw new TypeErrorException("Cashout Type error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(IDCARD);
		list.add(OFFICERCARD);
		list.add(PASSPORT);
		list.add(OTHER);
		return list;
	}

}
