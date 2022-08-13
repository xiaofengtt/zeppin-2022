/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.activity.score.ElectiveScoreType;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 *
 */
public interface ScoreActivity {
	
	/**Ϊ��ѧ��������3ɼ�,teachClassMap��keyΪteachClass,valueΪElectiveScoreGen(��ʾƽʱ�ɼ��Ϳ��Գɼ��ı���
	 * @param teachClassList
	 * @throws PlatformException
	 */
	public void generateTotalScore(Map teachClassMap) throws PlatformException;
	
	/**�޸Ķ��ѡ�ε���سɼ���electiveScoreMap��keyΪelective,valueΪScoreDef���б�
	 * @param electiveScores
	 * @throws PlatformException
	 */
	public void updateCoreScores(Map electiveScoreMap) throws PlatformException;
	
	public Map getCoreScores(Page page,ElectiveScoreType scoreType,String open_course_id) throws PlatformException;
	

}
