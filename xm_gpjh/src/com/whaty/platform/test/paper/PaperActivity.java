package com.whaty.platform.test.paper;

import java.util.HashMap;
import java.util.List;

import com.whaty.platform.database.oracle.standard.test.question.OraclePaperQuestion;
import com.whaty.platform.database.oracle.standard.test.question.OracleStoreQuestion;

public interface PaperActivity {

	/**
	 * 根据xmlPolicyCore，组织一组question,返回这组question的ID的HashMap
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
	 * 根据查询到的符合条件的题目数count和所要出的题目数,返回类似'1','3'的rownums字符串
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
