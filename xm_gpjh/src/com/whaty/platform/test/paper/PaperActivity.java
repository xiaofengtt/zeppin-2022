package com.whaty.platform.test.paper;

import java.util.HashMap;
import java.util.List;

import com.whaty.platform.database.oracle.standard.test.question.OraclePaperQuestion;
import com.whaty.platform.database.oracle.standard.test.question.OracleStoreQuestion;

public interface PaperActivity {

	/**
	 * ����xmlPolicyCore����֯һ��question,��������question��ID��HashMap
	 * 
	 * @param xmlPolicyCore
	 * @return HashMap
	 */
	public HashMap getQuestionsByPaperPolicy(String xmlPolicyCore);

	public List getDANXUAN(HashMap item);

	public List getDUOXUAN(HashMap item);

	public List getPANDUAN(HashMap item);

	public List getTIANKONG(HashMap item);

	public List getWENDA(HashMap item);

	public List getYUEDU(HashMap item);

	/**
	 * ���ݲ�ѯ���ķ�����������Ŀ��count����Ҫ������Ŀ��,��������'1','3'��rownums�ַ���
	 * 
	 * @param num
	 * @param count
	 * @return
	 */
	public String getRandomIds(int num, int count);

	public OraclePaperQuestion StoreQuestionToPaperQuestion(
			OracleStoreQuestion storeQuestion, String score);

	public List StoreQuestionsToPaperQuestions(List storeQuestionList,
			String score);
}
