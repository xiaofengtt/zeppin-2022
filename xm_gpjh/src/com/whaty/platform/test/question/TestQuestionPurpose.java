/**
 * 
 */
package com.whaty.platform.test.question;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PurposeErrorException;

;

/**
 * @author wq
 * 
 */
public class TestQuestionPurpose {
	public static String KAOSHI = "KAOSHI";

	public static String ZUOYE = "ZUOYE";

	public static String ZICE = "ZICE";

	public static String EXAM = "EXAM";

	public static String EXPERIMENT = "EXPERIMENT";

	public static String purposeShow(String purpose)
			throws PurposeErrorException {
		if (purpose != null && purpose.equals(KAOSHI))
			return "�����Բ�";
		else if (purpose != null && purpose.equals(ZUOYE))
			return "������ҵ";
		else if (purpose != null && purpose.equals(ZICE))
			return "�Բ�";
		else if (purpose != null && purpose.equals(EXAM))
			return "���߿���";
		else if (purpose != null && purpose.equals(EXPERIMENT))
			return "ʵ��";
		else
			throw new PurposeErrorException("Cashout Purpose error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(KAOSHI);
		list.add(ZUOYE);
		list.add(ZICE);
		list.add(EXAM);
		list.add(EXPERIMENT);
		return list;
	}
}
