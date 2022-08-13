package com.whaty.platform.test.question;

import java.util.ArrayList;
import java.util.List;

public class QuestionType {
	public static String DANXUAN = "DANXUAN";

	public static String DUOXUAN = "DUOXUAN";

	public static String PANDUAN = "PANDUAN";

	public static String TIANKONG = "TIANKONG";

	public static String WENDA = "WENDA";

	public static String YUEDU = "YUEDU";

	public static String typeShow(String type) {
		if (type != null && type.equals(DANXUAN))
			return "单选题";
		else if (type != null && type.equals(DUOXUAN))
			return "多选题";
		else if (type != null && type.equals(PANDUAN))
			return "判断题";
		else if (type != null && type.equals(TIANKONG))
			return "填空题";
		else if (type != null && type.equals(WENDA))
			return "问答题";
		else if (type != null && type.equals(YUEDU))
			return "阅读理解题";
		else
			return "未知题型";
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(DANXUAN);
		list.add(DUOXUAN);
		list.add(PANDUAN);
		list.add(TIANKONG);
		list.add(WENDA);
		list.add(YUEDU);
		return list;
	}
}
