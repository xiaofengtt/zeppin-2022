package com.whaty.platform.vote.basic;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

public class VotePaperType {
	public static String NORMAL = "NORMAL";

	public static String COURSE = "COURSE";

	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(NORMAL))
			return "普通调查";
		else if (type != null && type.equals(COURSE))
			return "课程调查";
		else
			throw new TypeErrorException("VotePaperType error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(NORMAL);
		list.add(COURSE);
		return list;
	}
}
