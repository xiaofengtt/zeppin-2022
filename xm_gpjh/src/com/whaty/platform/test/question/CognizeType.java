package com.whaty.platform.test.question;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

public class CognizeType {
	public static String LIAOJIE = "LIAOJIE";

	public static String LIJIE = "LIJIE";

	public static String YINGYONG = "YINGYONG";

	public static String FENXI = "FENXI";

	public static String ZONGHE = "ZONGHE";

	public static String PINGJIAN = "PINGJIAN";

	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(LIAOJIE))
			return "了解";
		else if (type != null && type.equals(LIJIE))
			return "理解";
		else if (type != null && type.equals(YINGYONG))
			return "应用";
		else if (type != null && type.equals(FENXI))
			return "分析";
		else if (type != null && type.equals(ZONGHE))
			return "综合";
		else if (type != null && type.equals(PINGJIAN))
			return "评鉴";
		else
			throw new TypeErrorException("Cashout Type error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(LIAOJIE);
		list.add(LIJIE);
		list.add(YINGYONG);
		list.add(FENXI);
		list.add(ZONGHE);
		list.add(PINGJIAN);
		return list;
	}
}
